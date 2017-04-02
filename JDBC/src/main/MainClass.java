package main;

import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

class Database {

    Connection conn = null;
    OraclePreparedStatement ps = null;
    OracleResultSet rs = null;

    public Database() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Ashmeet", "system");

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void fetchData() {
        try {
            String query = "SELECT * FROM user2";
            ps = (OraclePreparedStatement) conn.prepareStatement(query);

            rs = (OracleResultSet) ps.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID : " + rs.getString("id"));
                System.out.println("NAME : " + rs.getString("name"));
                System.out.println("ADDRESS : " + rs.getString("address"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertData() {
        try {
            String id, name, address;
            Scanner kb = new Scanner(System.in);

            System.out.println("ENTER ID : ");
            id = kb.next();
            System.out.println("ENTER Name : ");
            name = kb.next();
            System.out.println("ENTER Address : ");
            address = kb.next();

            String query = "Insert INTO user2 values (?,?,?)";
            ps = (OraclePreparedStatement) conn.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, address);

            ps.executeQuery();
            System.out.println("DATA inserted Successfully");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readByID(int id) {
        try {
            String query = "SELECT * FROM user2 WHERE id = " + id;
            ps = (OraclePreparedStatement) conn.prepareStatement(query);

            rs = (OracleResultSet) ps.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID : " + rs.getString("id"));
                System.out.println("NAME : " + rs.getString("name"));
                System.out.println("ADDRESS : " + rs.getString("address"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteById(int id) {
        try {
            String query = "DELETE FROM user2 where id = " + id;
            ps = (OraclePreparedStatement) conn.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("User with " + id + " Deleted Successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateName(int id, String name) {
        try {
            String query = "UPDATE user2 SET name=? where id= " + id;
            ps = (OraclePreparedStatement) conn.prepareStatement(query);
            ps.setString(1, name);

            ps.executeUpdate();
            System.out.println("DAtabase executed Successfully");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

public class MainClass {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int id;
        String name;
        System.out.println("ENTER id : ");
        id = kb.nextInt();
        System.out.println("ENTER Name : ");
        name = kb.next();
        Database obj = new Database();
        obj.updateName(id, name);
//        obj.deleteById(id);
        obj.fetchData();
    }

}
