package finalyearproject;

public class Identifier {

    int[] a = new int[3];

    public void selectAction(String verb) {
        DBManager db = new DBManager();
        int create = 0, update = 0, delete = 0;
        if (db.getVerb(verb) == 1) {
            create = 1;
        } else if (db.getVerb(verb) == 2) {
            update = 1;
        } else if (db.getVerb(verb) == 3) {
            delete = 1;
        }
        a[0] = create;
        a[1] = update;
        a[2] = delete;
        db.close();
    }

    public int[] returnAction() {
        return a;
    }
}
