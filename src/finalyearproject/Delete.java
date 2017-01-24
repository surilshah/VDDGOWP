package finalyearproject;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Delete {

    String[][] sentence;
    String currentpage;

    public Delete(String[][] s, String x) {
        sentence = s;
        currentpage = x;
    }

    public String findNoun() {
        String noun = "";
        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i][1].equalsIgnoreCase("Noun")) {
                noun = sentence[i][0];
            }
        }
        return noun;
    }

    public String find(String x, int y) {
        String n = null;
        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i][1].equalsIgnoreCase(x)) {
                if (y == 1) {
                    n = sentence[i][0];
                } else {
                    n = sentence[i][0].substring(0, (sentence[i][0].length()) - 1);
                }
            }
        }
        return n;
    }

    public void deleteAnything() throws IOException {
        RandomAccessFile index = null, temp = null;
        String id = "", line = "", a[], b[], n = "";
        long pointer = 0;
        int ch = 1;
        try {
            String dir = System.getProperty("user.dir");
            index = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
            temp = new RandomAccessFile(dir + "/src/finalyearproject/temp.txt", "rw");
            id = find("ID", 1);
            n = find("ID", 0);
            while ((line = index.readLine()) != null) {
                a = line.split(" ");
                for (int k = 0; k < a.length; k++) {
                    if (a[k].equalsIgnoreCase("#" + id + "")) {
                        while (!line.equals("}")) {
                            line = index.readLine();
                        }
                        line = index.readLine();
                    }
                }
                for (int i = 0; i < a.length; i++) {
                    if (a[i].equalsIgnoreCase("id=\"" + id + "\">")) {
                        while (ch != 0) {
                            b = line.split(" ");
                            for (int j = 0; j < b.length; j++) {
                                if (b[j].equalsIgnoreCase("</" + n + ">")) {
                                    ch = 0;
                                }
                            }
                            if (ch != 0) {
                                line = index.readLine();
                            }
                        }
                        int c;
                        while ((c = index.read()) != -1) {
                            temp.write(c);
                        }
                        index.close();
                        PrintWriter del1 = new PrintWriter(dir + "/src/finalyearproject/" + currentpage);
                        del1.print(" ");
                        del1.close();
                        index = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
                        index.seek(0);
                        temp.seek(0);
                        int e;
                        while ((e = temp.read()) != -1) {
                            index.write(e);
                        }
                    }
                }
                temp.writeBytes(line);
                temp.write('\n');
                pointer = index.getFilePointer();
            }
            temp.close();
            PrintWriter del2 = new PrintWriter(dir + "/src/finalyearproject/temp.txt");
            del2.print(" ");
            del2.close();
        } catch (FileNotFoundException e) {

        }
    }
}
