package facebookdatamining.Domain.Repository;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Thiago N. Oliveira
 */
public abstract class BaseRepository {

    public Connection getConnection(String connectionString) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                return DriverManager.getConnection("jdbc:mysql://"+ connectionString);
            } catch (SQLException ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                System.out.println(sw.toString());
                return null;
            }
        } catch (ClassNotFoundException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return null;
        }
    }
}
