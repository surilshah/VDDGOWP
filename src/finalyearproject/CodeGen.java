package finalyearproject;

import java.io.*;

public class CodeGen {

    String currentpage;

    public CodeGen(String x) {
        currentpage = x;
    }

    public void createHTML() throws FileNotFoundException, IOException {
        String dir = System.getProperty("user.dir");
        FileReader in = null;
        FileWriter out = null;
        try {
            in = new FileReader(dir + "/src/finalyearproject/draw.txt");
            out = new FileWriter(dir + "/src/finalyearproject/" + currentpage, true);
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

        /*try {
         RandomAccessFile file = new RandomAccessFile(dir + "/src/finalyearproject/index.html", "rw");
         long a = file.getFilePointer();
         System.out.println(a);
         byte b=file.readByte();
         System.out.println((char)b);
         a = file.getFilePointer();
         System.out.println(a);
         System.out.println(file.readLine());
         System.out.println(file.getFilePointer());
         System.out.println((char)file.readByte());
         System.out.println(file.readLine());
         System.out.println(file.getFilePointer());
            
         }
         catch (IOException ex) {
         }*/
    }

    public void createNewHTML() throws IOException {
        FileReader in = null;
        FileWriter out = null;
        PrintWriter del = null;
        String dir = System.getProperty("user.dir");
        try {
            in = new FileReader(dir + "/src/finalyearproject/draw.txt");
            out = new FileWriter(dir + "/src/finalyearproject/" + currentpage, true);
            out.write("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style>\n" + "</style>\n" + "</head>\n" + "<body>\n");
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.write("\n" + "</body>\n" + "</html>");
            del = new PrintWriter(dir + "/src/finalyearproject/draw.txt");
            del.print(" ");
            del.close();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public void updateHTML() throws IOException {
        FileReader draw = null;
        RandomAccessFile index = null, temp = null;
        String dir = System.getProperty("user.dir");
        try {
            temp = new RandomAccessFile(dir + "/src/finalyearproject/temp.txt", "rw");
            draw = new FileReader(dir + "/src/finalyearproject/draw.txt");
            index = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
            String line;
            long x = 0;
            while ((line = index.readLine()) != null) {
                if (line.equalsIgnoreCase("</body>")) {
                    x = index.getFilePointer();
                    break;
                }
            }
            int c;
            index.seek(x - 8);
            while ((c = index.read()) != -1) {
                temp.write(c);
            }

            index.seek(x - 8);
            while ((c = draw.read()) != -1) {
                index.write(c);
            }

            temp.seek(0);
            index.write('\n');
            while ((c = temp.read()) != -1) {
                index.write(c);
            }

            PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/draw.txt");
            PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
            del1.print(" ");
            del2.print(" ");
            del1.close();
            del2.close();
        } finally {
            if (temp != null) {
                temp.close();
            }
            if (index != null) {
                index.close();
            }
            if (draw != null) {
                draw.close();
            }
        }
    }
}
