package finalyearproject;

import java.io.*;
import java.util.*;

public class FinalYearProject {

    public int FYP(String str, int pg) throws IOException {
        int cmd = 0;
        String dir = System.getProperty("user.dir");
        String currentpage = "page"+pg+".html";
        RandomAccessFile html = new RandomAccessFile(dir + "/src/finalyearproject/page1.html", "rw");
        RandomAccessFile currentobj = html;
        RandomAccessFile newpage = null;
        dir = System.getProperty("user.dir");
        Scanner sc = new Scanner(System.in);
        Tagger t = new Tagger(str);
        String[][] s = t.tag();
        int a[] = new int[3];
        a = t.x.returnAction();
        CodeGen c = new CodeGen(currentpage);
        if (a[0] == 1) {
            Draw d = new Draw(s, currentpage);
            String component = d.find("Noun");
            if (component.equalsIgnoreCase("Table")) {
                d.drawTable();
                if (currentobj.readLine() == null) {
                    c.createNewHTML();
                } else {
                    c.updateHTML();
                }
            } else if (component.equalsIgnoreCase("Page")) {
                pg = pg + 1;
                dir = dir + "/src/finalyearproject/page" + pg + ".html";
                newpage = new RandomAccessFile(dir, "rw");
                System.out.println("New Page created: Page " + pg);
            } else {
                d.drawAnything(component);
                if (currentobj.readLine() == null) {
                    c.createNewHTML();
                } else {
                    c.updateHTML();
                }
            }
        } else if (a[1] == 1) {
            Update u = new Update(s, currentpage);
            String component = u.find("ID", 0);
            //System.out.println(component);
            if (component != null && component.equalsIgnoreCase("Table")) {
                u.updateTable();
            } else if (component != null && component.substring(0, 4).equalsIgnoreCase("Page")) {
                currentpage = component + ".html";
                currentobj = new RandomAccessFile(dir + "/src/finalyearproject/" + currentpage, "rw");
                System.out.println("Selected: " + component);
            } else {
                u.updateAnything();
            }
        } else if (a[2] == 1) {
            Delete del = new Delete(s, currentpage);
            String component = del.find("ID", 0);
            del.deleteAnything();
        }
        pg = Integer.parseInt(currentpage.substring(4, 5));
        return pg;
    }
}
