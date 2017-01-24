package finalyearproject;

import java.io.*;
import java.util.*;

public class Draw {

    String[][] sentence;
    String currentpage;

    public Draw(String[][] s, String x) {
        sentence = s;
        currentpage = x;
    }

    public String find(String x) {
        String n = "";
        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i][1].equalsIgnoreCase(x)) {
                n = sentence[i][0];
                sentence[i][0] = sentence[i][1] = "";
                break;
            }
        }
        return n;
    }

    public String rowsColumns() {
        String a = "";
        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i][1].equalsIgnoreCase("Number")) {
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

    public void drawTable() throws FileNotFoundException, IOException {
        String tag = "", x = "", y = "", subtag1 = "", subtag2 = "";
        String dir = System.getProperty("user.dir");
        int rows = 0, columns = 0;
        DBManager db = new DBManager();
        FileWriter out = null;
        Scanner sc = new Scanner(System.in);
        tag = db.getCodeFromTags("table");
        x = rowsColumns();
        y = rowsColumns();
        if (x.equals("") && y.equals("")) {
            System.out.print("Enter Number of rows:");
            rows = sc.nextInt();
            System.out.print("Enter Number of columns:");
            columns = sc.nextInt();
            x = Integer.toString(rows) + "rows";
            y = Integer.toString(columns) + "columns";
        } else if ((x.substring(1).equalsIgnoreCase("rows") || x.substring(1).equalsIgnoreCase("row")) && y.equals("")) {
            System.out.print("Enter number of columns:");
            columns = sc.nextInt();
            y = Integer.toString(columns) + "columns";
        } else if ((x.substring(1).equalsIgnoreCase("columns") || x.substring(1).equalsIgnoreCase("column")) && y.equals("")) {
            System.out.print("Enter number of rows:");
            rows = sc.nextInt();
            y = Integer.toString(rows) + "rows";
        }
        if (x.substring(1).equalsIgnoreCase("rows") || x.substring(1).equalsIgnoreCase("row")) //Agar draw table with m rows and n columns pucha toh
        {
            rows = Character.getNumericValue(x.charAt(0));
            columns = Character.getNumericValue(y.charAt(0));
            subtag1 = db.getCodeFromSubTags(x.substring(1));
            subtag2 = db.getCodeFromSubTags(y.substring(1));
        } else if (x.substring(1).equalsIgnoreCase("columns") || x.substring(1).equalsIgnoreCase("column")) //Agar draw table with m columns and n rows pucha toh
        {
            rows = Character.getNumericValue(y.charAt(0));
            columns = Character.getNumericValue(x.charAt(0));
            subtag2 = db.getCodeFromSubTags(x.substring(1));
            subtag1 = db.getCodeFromSubTags(y.substring(1));
        }
        try {
            out = new FileWriter(dir + "/src/finalyearproject/draw.txt");
            String z;
            out.write(tag, 0, 33);
            z = " id=\"table" + Integer.toString(idGenerator("Table", tag)) + "\"";
            out.write(z);
            out.write('>');
            for (int i = 0; i < rows; i++) {
                out.write("\n");
                out.write(subtag1, 0, 4);
                out.write("\n");
                for (int j = 0; j < columns; j++) {
                    out.write(subtag2);
                }
                out.write("\n");
                out.write(subtag1, 5, 5);
            }
            out.write("\n");
            out.write(tag, 35, 8);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        db.close();
    }

    public void drawAnything(String component) throws IOException {
        String tag = "", href = "";
        Scanner sc = new Scanner(System.in);
        String dir = System.getProperty("user.dir");
        FileWriter out = null;
        DBManager db = new DBManager();
        tag = db.getCodeFromTags(component);
        String[] a = tag.split(">");
        try {
            out = new FileWriter(dir + "/src/finalyearproject/draw.txt");
            out.write(a[0]);
            out.write(" id=\"" + component + Integer.toString(idGenerator(component, tag)) + "\"");

            if (component.equalsIgnoreCase("link")) {
                if (find("Noun") == "") {
                    System.out.print("Href:");
                    href = sc.nextLine();
                    if (href.substring(0, href.length() - 1).equalsIgnoreCase("Page")) {
                        href = href + ".html";
                    }
                    out.write(" href=\"" + href + "\"");
                }
                /*else if(find("Noun").equalsIgnoreCase("Page"))
                 {
                 System.out.println("mod");
                 href="page"+find("Number")+".html";
                 out.write(" href=\"" + href + "\"");
                 }*/
            }
            out.write('>');

            if (a.length != 1) {
                out.write(a[1]);
                out.write('>');
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
        db.close();
    }

    public int idGenerator(String component, String tag) throws FileNotFoundException, IOException {
        String line = "";
        String[] a;
        RandomAccessFile index;
        a = tag.split(">");
        if (component.equalsIgnoreCase("Table")) {
            component = '<' + component;
        } else {
            component = a[0];
        }
        String dir = System.getProperty("user.dir");
        index = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
        int c = 1;
        while ((line = index.readLine()) != null) {
            a = line.split(" ");
            for (int i = 0; i < a.length; i++) {
                if (a[i].equalsIgnoreCase(component)) {
                    c++;
                }
            }
        }
        return c;
    }
}
