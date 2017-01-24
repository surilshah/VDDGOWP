package finalyearproject;

import java.io.*;
import java.util.*;

public class Update {

    String[][] sentence;
    String currentpage;

    public Update(String[][] s, String x) {
        sentence = s;
        currentpage = x;
    }

    public String find(String x, int y) {
        String n = null;
        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i][1].equalsIgnoreCase(x)) {
                if (y == 1) {
                    if (sentence[i][0].equalsIgnoreCase("Pixels")) {
                        n = sentence[i - 1][0] + "px";
                    } else if (sentence[i][0].equalsIgnoreCase("Percent") || sentence[i][0].equalsIgnoreCase("Percentage") || sentence[i][0].equalsIgnoreCase("%")) {
                        n = sentence[i - 1][0] + "%";
                    } else {
                        n = sentence[i][0];
                    }

                } else if (y == 0) {
                    if (sentence[i][0].substring(0, 4).equalsIgnoreCase("Page")) {
                        n = sentence[i][0];
                    } else {
                        n = sentence[i][0].substring(0, (sentence[i][0].length()) - 1);
                    }
                }
            }
        }
        return n;
    }

    public String getProperty() {
        String n = null;
        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i][1].equalsIgnoreCase("Property")) {
                n = sentence[i][0];
                sentence[i][0] = sentence[i][1] = "";
                return n;
            }
        }
        return n;
    }

    public String rowsColumns1() {
        String a = "";
        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i][1].equalsIgnoreCase("NNS")) {
                a = sentence[i + 1][0];
                a += sentence[i][0];
                sentence[i][0] = "";
                sentence[i + 1][0] = "";
                sentence[i][1] = "";
                sentence[i + 1][1] = "";
                break;
            } else if (sentence[i][1].equalsIgnoreCase("Number")) {
                a = sentence[i][0];
                a += sentence[i + 1][0];
                sentence[i][0] = "";
                sentence[i + 1][0] = "";
                sentence[i][1] = "";
                sentence[i + 1][1] = "";
                break;
            }
        }
        return a;
    }

    public void updateTable() throws IOException {

        String x = "", y = "", id = "", line = "", a[], b[], f = "";
        long pointer = 0, tempindex = 0, t = 0;
        int flag = 0;
        int rows = 0, columns = 0, rw = 0, cl = 0, z = 0, r = 0;
        RandomAccessFile index = null, temp = null, draw = null;
        FileReader d = null;
        Scanner sc = new Scanner(System.in);
        try {
            String dir = System.getProperty("user.dir");
            String fin = "", str = "", str1 = "", str2 = "", property = "", data = "";
            index = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
            draw = new RandomAccessFile(dir + "/src/finalyearproject/draw.txt", "rw");
            temp = new RandomAccessFile(dir + "/src/finalyearproject/temp.txt", "rw");
            id = find("ID", 1);
            x = rowsColumns1();
            y = rowsColumns1();

            if ((str1 = getProperty()) != null) {                               //table CSS

                str2 = getProperty();
                if (str2 != null) {
                    property = str1 + "-" + str2;
                } else {
                    property = str1;
                }

                data = find("Data", 1);

                int cd1 = 0, cd2 = 0, c;
                long p = 0, q = 0, s = 0;

                while (!(line = index.readLine()).equalsIgnoreCase("</style>")) {
                    if (line.equalsIgnoreCase("#" + id + " {")) {
                        p = index.getFilePointer();
                        break;
                    } else if (line.equalsIgnoreCase("<style>")) {
                        q = index.getFilePointer();
                    }
                }

                if (p != 0) {
                    s = p;
                } else {
                    s = q;
                }

                if (p != 0) {
                    index.seek(s);
                    while (!((line = index.readLine()).equalsIgnoreCase("}"))) //check if an attribute exists already
                    {
                        a = line.split(" ");
                        for (int i = 0; i < a.length; i++) {
                            if (a[i].equalsIgnoreCase(property + ":")) {
                                pointer = index.getFilePointer();
                                tempindex = pointer - line.length() - 1;
                                break;
                            }
                        }
                    }

                    if (tempindex == 0) //attribute does not exist, create new attribute
                    {
                        temp.seek(0);
                        index.seek(s);
                        while ((c = index.read()) != -1) {
                            temp.write(c);
                        }

                        index.seek(s);
                        index.writeBytes(property + ": " + data + ";");

                        index.writeByte('\n');
                        temp.seek(0);
                        while ((c = temp.read()) != -1) {
                            index.write(c);
                        }
                    } else //attribute exists already, modify that attribute
                    {
                        temp.seek(0);
                        index.seek(0);
                        while (index.getFilePointer() < tempindex) {
                            line = index.readLine();
                            temp.writeBytes(line + "\n");
                        }
                        temp.writeBytes(property + ": " + data + ";\n");

                        index.seek(pointer);
                        while ((c = index.read()) != -1) {
                            temp.write(c);
                        }

                        temp.seek(0);
                        index.seek(0);
                        PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/" + currentpage);
                        del1.print(" ");
                        del1.close();
                        while ((c = temp.read()) != -1) {
                            index.write(c);
                        }
                    }
                } else //first attribute 
                {
                    temp.seek(0);
                    index.seek(s);
                    while ((c = index.read()) != -1) {
                        temp.write(c);
                    }

                    index.seek(s);
                    index.writeBytes("#" + id + " {\n" + property + ": " + data + ";\n}");

                    index.writeByte('\n');
                    temp.seek(0);
                    while ((c = temp.read()) != -1) {
                        index.write(c);
                    }
                }

                PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
                PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/index.txt");
                del1.print(" ");
                del2.print(" ");
                del1.close();
                del2.close();
            } else if (find("data", 1) != null) {                                 // Add data to specific row and column
                if (x.substring(1).equalsIgnoreCase("rows") || x.substring(1).equalsIgnoreCase("row")) //Agar add m rows and n columns pucha toh
                {
                    rows = Character.getNumericValue(x.charAt(0));
                    columns = Character.getNumericValue(y.charAt(0));
                } else if (x.substring(1).equalsIgnoreCase("columns") || x.substring(1).equalsIgnoreCase("column")) //Agar add with m columns and n rows pucha toh
                {
                    rows = Character.getNumericValue(y.charAt(0));
                    columns = Character.getNumericValue(x.charAt(0));
                }
                while ((line = index.readLine()) != null) {
                    a = line.split(" ");
                    for (int i = 0; i < a.length; i++) {
                        if (a[i].equalsIgnoreCase("id=\"" + id + "\">")) {
                            //System.out.println(line);
                            for (int k = 0; k < ((2 * rows) + (rows - 1)) - 1; k++) {
                                index.readLine();
                            }
                            tempindex = index.getFilePointer();
                            line = index.readLine();
                            pointer = index.getFilePointer();

                            for (int s = 0; s < sentence.length; s++) {
                                if (sentence[s][1].equalsIgnoreCase("Data")) {
                                    sentence[s][1] = "";
                                    f += sentence[s][0] + " ";
                                }
                            }

                            b = line.split("</td>");
                            b[columns - 1] = "<td>" + f;

                            for (z = 0; z < b.length; z++) {
                                fin += b[z] + "</td>";
                            }

                            int c = 0;
                            index.seek(0);
                            temp.seek(0);
                            while (index.getFilePointer() != tempindex) {
                                line = index.readLine();
                                temp.writeBytes(line + "\n");
                            }

                            temp.writeBytes(fin + "\n");

                            index.seek(pointer);
                            while ((c = index.read()) != -1) {
                                temp.write(c);
                            }

                            PrintWriter del = new PrintWriter(dir + "/src/finalyearproject/" + currentpage);
                            del.print(" ");
                            del.close();

                            temp.seek(0);
                            index.seek(0);
                            while ((c = temp.read()) != -1) {
                                index.write(c);
                            }
                        }
                    }
                }
                temp.close();
                PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
                del2.print(" ");
                del2.close();
            } else if ((find("data", 1) == null) && find("Words", 1) != null) //Filling the entire table
            {
                while ((line = index.readLine()) != null) {                       //Calc. rows and cols using table id
                    a = line.split(" ");
                    for (int i = 0; i < a.length; i++) {
                        if (a[i].equalsIgnoreCase("id=\"" + id + "\">")) {
                            tempindex = index.getFilePointer();
                            tempindex = tempindex - line.length() - 1;
                            while (true) {
                                line = index.readLine();
                                if (line.equalsIgnoreCase("</table>")) {
                                    pointer = index.getFilePointer();
                                    break;
                                } else {
                                    rw++;
                                }
                                line = index.readLine();
                                b = line.split("</td>");
                                cl = b.length;
                                line = index.readLine();
                            }
                        }
                    }
                }
                /*index.seek(tempindex);
                 for(z=0;z<6;z++)
                 System.out.println((char)index.readByte());*/

                for (int i = 0; i < rw; i++) //Write table+data to draw.txt
                {
                    draw.writeBytes("<tr>\n");
                    for (int j = 0; j < cl; j++) {
                        draw.writeBytes("<td>");
                        System.out.print("Data for row " + (i + 1) + " and column " + (j + 1) + ": ");
                        f = sc.nextLine();
                        draw.writeBytes(f);
                        draw.writeBytes("</td>");
                    }
                    draw.writeBytes("\n</tr>\n");
                }
                draw.writeBytes("</table>");

                int c = 0;

                index.seek(0);
                temp.seek(0);
                while (index.getFilePointer() <= tempindex) {
                    line = index.readLine();
                    temp.writeBytes(line + "\n");
                }

                draw.seek(0);
                while ((c = draw.read()) != -1) {
                    temp.write(c);
                }

                index.seek(pointer);
                temp.writeByte('\n');
                while ((c = index.read()) != -1) {
                    temp.write(c);
                }

                PrintWriter del = new PrintWriter(dir + "/src/finalyearproject/" + currentpage);
                del.print(" ");
                del.close();

                temp.seek(0);
                index.seek(0);
                while ((c = temp.read()) != -1) {
                    index.write(c);
                }

                PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/draw.txt");
                PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
                del1.print(" ");
                del2.print(" ");
                del1.close();
                del2.close();

            } else if (find("data", 1) == null) {                                  //Add rows and columns to a previously existing table

                while ((line = index.readLine()) != null) {                       //Calc. rows and cols using table id
                    a = line.split(" ");
                    for (int i = 0; i < a.length; i++) {
                        if (a[i].equalsIgnoreCase("id=\"" + id + "\">")) {
                            tempindex = index.getFilePointer();
                            tempindex = tempindex - line.length() - 1;
                            while (true) {
                                line = index.readLine();
                                if (line.equalsIgnoreCase("</table>")) {
                                    pointer = index.getFilePointer();
                                    pointer = pointer - line.length() - 1;
                                    break;
                                } else {
                                    rw++;
                                }
                                line = index.readLine();
                                b = line.split("</td>");
                                cl = b.length;
                                line = index.readLine();
                            }
                        }
                    }
                }
                int g = 0;
                if ((x.substring(1).equalsIgnoreCase("rows") || x.substring(1).equalsIgnoreCase("row")) && y.equals("")) {
                    y = Integer.toString(cl) + "columns";
                }

                if ((x.substring(1).equalsIgnoreCase("columns") || x.substring(1).equalsIgnoreCase("column")) && y.equals("")) {
                    y = Integer.toString(rw) + "rows";
                    g = 1;
                }

                if (x.substring(1).equalsIgnoreCase("rows") || x.substring(1).equalsIgnoreCase("row")) //Agar add m rows and n columns pucha toh
                {
                    rows = Character.getNumericValue(x.charAt(0));
                    columns = Character.getNumericValue(y.charAt(0));

                } else if (x.substring(1).equalsIgnoreCase("columns") || x.substring(1).equalsIgnoreCase("column")) //Agar add with m columns and n rows pucha toh
                {
                    rows = Character.getNumericValue(y.charAt(0));
                    columns = Character.getNumericValue(x.charAt(0));
                }

                int c = 0, h = 0;

                if (g == 1) {
                    index.seek(0);
                    temp.seek(0);
                    while ((line = index.readLine()) != null) {
                        a = line.split(" ");
                        for (int i = 0; i < a.length; i++) {
                            if (a[i].equalsIgnoreCase("id=\"" + id + "\">")) {
                                temp.writeBytes(line + "\n");
                                while (!((line = index.readLine()).equalsIgnoreCase("</table>"))) {
                                    line = index.readLine();
                                    temp.writeBytes("<tr>\n" + line);
                                    for (int q = 0; q < columns; q++) {
                                        temp.writeBytes("<td> </td>");
                                    }
                                    line = index.readLine();
                                    temp.writeBytes("\n</tr>\n");
                                }
                            } else {
                                h = 1;
                            }
                        }
                        if (h == 1) {
                            temp.writeBytes(line + "\n");
                            h = 0;
                        }
                    }

                    PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/index.txt");
                    del1.print(" ");
                    del1.close();

                    temp.seek(0);
                    index.seek(0);
                    while ((c = temp.read()) != -1) {
                        index.write(c);
                    }
                } else if (g == 0) {
                    temp.seek(0);
                    index.seek(pointer);
                    while ((c = index.read()) != -1) {
                        temp.write(c);
                    }

                    index.seek(pointer);
                    for (int i = 0; i < rows; i++) {
                        index.writeBytes("<tr>\n");
                        for (int j = 0; j < columns; j++) {
                            index.writeBytes("<td> </td>");
                        }
                        index.writeBytes("\n</tr>\n");
                    }

                    temp.seek(0);
                    while ((c = temp.read()) != -1) {
                        index.write(c);
                    }
                }

                PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
                del2.print(" ");
                del2.close();
            }
        } finally {
            if (d != null) {
                d.close();
            }
            if (draw != null) {
                draw.close();
            }
            if (index != null) {
                index.close();
            }
            if (temp != null) {
                temp.close();
            }
        }
    }

    public void updateAnything() throws FileNotFoundException, IOException {
        RandomAccessFile index = null, temp = null, draw = null;
        String dir = System.getProperty("user.dir");
        String line, a[], id, b, str = "", d = "", str1 = "", str2 = "", property = "", data = "", f = "";
        int x, c, y = 0;
        long p = 0, q = 0, s = 0, pointer = 0, tempindex = 0;
        char m;
        try {
            index = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
            temp = new RandomAccessFile(dir + "/src/finalyearproject/temp.txt", "rw");
            id = find("ID", 1);
            data = find("Data", 1);

            if ((id != null) && (str1 = getProperty()) != null) //update css of any component except body tag
            {
                str2 = getProperty();
                if (str2 != null) {
                    property = str1 + "-" + str2;
                } else {
                    property = str1;
                }

                data = find("Data", 1);

                int cd1 = 0, cd2 = 0;

                while (!(line = index.readLine()).equalsIgnoreCase("</style>")) {
                    if (line.equalsIgnoreCase("#" + id + " {")) {
                        p = index.getFilePointer();
                        break;
                    } else if (line.equalsIgnoreCase("<style>")) {
                        q = index.getFilePointer();
                    }
                }

                if (p != 0) {
                    s = p;
                } else {
                    s = q;
                }

                if (p != 0) {
                    index.seek(s);
                    while (!((line = index.readLine()).equalsIgnoreCase("}"))) //check if an attribute exists already
                    {
                        a = line.split(" ");
                        for (int i = 0; i < a.length; i++) {
                            if (a[i].equalsIgnoreCase(property + ":")) {
                                pointer = index.getFilePointer();
                                tempindex = pointer - line.length() - 1;
                                break;
                            }
                        }
                    }

                    if (tempindex == 0) //attribute does not exist, create new attribute
                    {
                        temp.seek(0);
                        index.seek(s);
                        while ((c = index.read()) != -1) {
                            temp.write(c);
                        }

                        index.seek(s);
                        index.writeBytes(property + ": " + data + ";");

                        index.writeByte('\n');
                        temp.seek(0);
                        while ((c = temp.read()) != -1) {
                            index.write(c);
                        }
                    } else //attribute exists already, modify that attribute
                    {
                        temp.seek(0);
                        index.seek(0);
                        while (index.getFilePointer() < tempindex) {
                            line = index.readLine();
                            temp.writeBytes(line + "\n");
                        }
                        temp.writeBytes(property + ": " + data + ";\n");

                        index.seek(pointer);
                        while ((c = index.read()) != -1) {
                            temp.write(c);
                        }

                        temp.seek(0);
                        index.seek(0);
                        PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/" + currentpage);
                        del1.print(" ");
                        del1.close();
                        while ((c = temp.read()) != -1) {
                            index.write(c);
                        }
                    }
                } else //first attribute 
                {
                    temp.seek(0);
                    index.seek(s);
                    while ((c = index.read()) != -1) {
                        temp.write(c);
                    }

                    index.seek(s);
                    index.writeBytes("#" + id + " {\n" + property + ": " + data + ";\n}");

                    index.writeByte('\n');
                    temp.seek(0);
                    while ((c = temp.read()) != -1) {
                        index.write(c);
                    }
                }

                PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
                PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/index.txt");
                del1.print(" ");
                del2.print(" ");
                del1.close();
                del2.close();
            } else if ((id == null) && (str1 = getProperty()) != null) //css for body tag only
            {
                str2 = getProperty();
                if (str2 != null) {
                    property = str1 + "-" + str2;
                } else {
                    property = str1;
                }

                data = find("Data", 1);

                int cd1 = 0, cd2 = 0;

                if (index.readLine() == null) {
                    index.writeBytes("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style>\n"
                            + "</style>\n" + "</head>\n" + "<body>\n" + "</body>\n" + "</html>");
                }

                index.seek(0);
                while (!(line = index.readLine()).equalsIgnoreCase("</style>")) {
                    if (line.equalsIgnoreCase("body {")) {
                        p = index.getFilePointer();
                        break;
                    } else if (line.equalsIgnoreCase("<style>")) {
                        q = index.getFilePointer();
                    }
                }

                if (p != 0) {
                    s = p;
                } else {
                    s = q;
                }

                if (p != 0) {
                    index.seek(s);
                    while (!((line = index.readLine()).equalsIgnoreCase("}"))) //check if an attribute exists already
                    {
                        a = line.split(" ");
                        for (int i = 0; i < a.length; i++) {
                            if (a[i].equalsIgnoreCase(property + ":")) {
                                pointer = index.getFilePointer();
                                tempindex = pointer - line.length() - 1;
                                break;
                            }
                        }
                    }

                    if (tempindex == 0) //attribute does not exist, create new attribute
                    {
                        temp.seek(0);
                        index.seek(s);
                        while ((c = index.read()) != -1) {
                            temp.write(c);
                        }

                        index.seek(s);
                        index.writeBytes(property + ": " + data + ";");

                        index.writeByte('\n');
                        temp.seek(0);
                        while ((c = temp.read()) != -1) {
                            index.write(c);
                        }
                    } else //attribute exists already, modify that attribute
                    {
                        temp.seek(0);
                        index.seek(0);
                        while (index.getFilePointer() < tempindex) {
                            line = index.readLine();
                            temp.writeBytes(line + "\n");
                        }
                        temp.writeBytes(property + ": " + data + ";\n");

                        index.seek(pointer);
                        while ((c = index.read()) != -1) {
                            temp.write(c);
                        }

                        temp.seek(0);
                        index.seek(0);
                        PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/" + currentpage);
                        del1.print(" ");
                        del1.close();
                        while ((c = temp.read()) != -1) {
                            index.write(c);
                        }
                    }
                } else //first attribute 
                {
                    temp.seek(0);
                    index.seek(s);
                    while ((c = index.read()) != -1) {
                        temp.write(c);
                    }

                    index.seek(s);
                    index.writeBytes("body {\n" + property + ": " + data + ";\n}");

                    index.writeByte('\n');
                    temp.seek(0);
                    while ((c = temp.read()) != -1) {
                        index.write(c);
                    }
                }

                PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
                PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/index.txt");
                del1.print(" ");
                del2.print(" ");
                del1.close();
                del2.close();
            } else {                                                               //add data to any component
                index = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
                temp = new RandomAccessFile(dir + "/src/finalyearproject/temp.txt", "rw");
                /*for (int k = 0; k < sentence.length; k++) 
                 for (int m = 0; m < 2; m++) 
                 System.out.println(sentence[k][m]);*/
                id = find("ID", 1);
                while ((line = index.readLine()) != null) {
                    a = line.split(" ");
                    for (int i = 0; i < a.length; i++) {
                        if (a[i].equalsIgnoreCase("id=\"" + id + "\">") || a[i].equalsIgnoreCase("id=\"" + id + "\"")) {
                            pointer = index.getFilePointer();
                            tempindex = pointer - line.length() - 1;

                            temp.seek(0);
                            index.seek(0);
                            while (index.getFilePointer() < tempindex) {
                                line = index.readLine();
                                temp.writeBytes(line + "\n");
                            }
                            for (int t = 0; t < sentence.length; t++) {
                                if (sentence[t][1].equalsIgnoreCase("Data")) {
                                    sentence[t][1] = "";
                                    f += sentence[t][0] + " ";
                                }
                            }

                            if (id.substring(0, id.length() - 1).equalsIgnoreCase("Link")) {
                                temp.writeBytes(a[0] + " " + a[1] + " " + a[2] + " " + f + a[a.length - 1] + "\n");
                            } else {
                                temp.writeBytes(a[0] + " " + a[1] + " " + f + a[a.length - 1] + "\n");
                            }

                            index.seek(pointer);
                            while ((c = index.read()) != -1) {
                                temp.write(c);
                            }

                            PrintWriter del = new PrintWriter(dir + "/src/finalyearproject/" + currentpage);
                            del.print(" ");
                            del.close();

                            temp.seek(0);
                            index.seek(0);
                            while ((c = temp.read()) != -1) {
                                index.write(c);
                            }
                            break;
                        }
                    }
                }
                PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
                del1.print(" ");
                del1.close();
            }
        } finally {
            if (index != null) {
                index.close();
            }
            if (temp != null) {
                temp.close();
            }
        }
    }
}
