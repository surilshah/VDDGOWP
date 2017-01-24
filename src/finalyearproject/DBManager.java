package finalyearproject;

import java.sql.*;

public class DBManager {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dir = System.getProperty("user.dir");
            con = DriverManager.getConnection("jdbc:sqlite:" + dir + "/src/finalyearproject/database");
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {

            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {

            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {

            }
        }
    }

    public String getCodeFromTags(String x) {
        String s = new String();
        try {
            String query = "select Code from MainTag where Noun='" + x + "'";
            rs = st.executeQuery(query);
            s = rs.getString("Code");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return s;
    }

    public int getVerb(String x) {
        int s = 0;
        try {
            String query = "select Type from Verbs where VB='" + x + "'";
            rs = st.executeQuery(query);
            s = rs.getInt("Type");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return s;
    }

    public String getCodeFromSubTags(String x) {
        String s = new String();
        try {
            String query = "select Code from SubTag where NNS='" + x + "'";
            rs = st.executeQuery(query);
            s = rs.getString("Code");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return s;
    }

    public String getTag(String x) {
        String s = new String();
        try {
            String query = "select * from Tagger where Word='" + x + "'";
            rs = st.executeQuery(query);
            s = rs.getString("POS");
        } catch (Exception ex) {
            //System.out.println(ex);
            return "data";
        }
        return s;
    }
    /*public boolean login(String uname,String passwd){
     try{
     String query="select * from login";
     rs=st.executeQuery(query);
     while(rs.next()){
     String n=rs.getString("user");
     String p=rs.getString("psswd");
     if(uname.equals(n)&&passwd.equals(p))
     return true;
     }
     }
     catch(Exception ex){
     System.out.println(ex);
     }
     return false;
     }
     public ResultSet getTag()
     {
     try{
     String query="select * from main";
     rs=st.executeQuery(query);
     }
     catch(Exception ex){
     System.out.println(ex);
     }
     return rs;
     }
     public void addData(String s)
     {
     try {
     st.executeUpdate(s);
     } catch (SQLException ex) {
     Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
}
