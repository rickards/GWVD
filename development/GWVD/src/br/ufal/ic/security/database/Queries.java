package br.ufal.ic.security.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries {
	public void executeQuery(String query){
		java.sql.Connection conector = null;
        try {
            conector = DriverManager.getConnection("jdbc:mysql://localhost/security", "root", "admin");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Statement stm = conector.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                String MFSA = rs.getString("ID_ADVISORIES");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        } finally {
            try {
                conector.close();
            } catch (SQLException ex) {
                System.out.println("ex = " + ex);
                ex.printStackTrace();
            }
        }
	}
}
