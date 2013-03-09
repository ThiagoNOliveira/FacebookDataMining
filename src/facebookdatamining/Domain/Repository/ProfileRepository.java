package facebookdatamining.Domain.Repository;

import facebookdatamining.Domain.Entities.Profile;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileRepository extends BaseRepository {

    private Connection conn;
    private PreparedStatement ps;
    private String query;

    public void add(Profile profile) {
        conn = getConnection();
        try {
            query = "Select Id FROM profile where Id = ?;";
            ps = conn.prepareStatement(query);
            ps.setLong(1, profile.getId());
            ResultSet retorno = ps.executeQuery();
            if (!retorno.next()) {
                query = "INSERT INTO `profile`(`Id`, `CrawllerLevel`) VALUES (?,?);";
                try {
                    ps = conn.prepareStatement(query);
                    ps.setLong(1, profile.getId());
                    ps.setLong(2, profile.getCrawllerLevel());
                    ps.execute();
                    ps.close();
                    conn.close();
                } catch (Exception ex) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    System.out.println(sw.toString());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepository.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void update(Profile profile) {
        conn = getConnection();
        query = "UPDATE `profile` SET `Recorded`=" + (boolean) isNull(profile.isRecorded())
                + ",`Name`='" + (String) isNull(profile.getName()) + "',`About`='" + (String) isNull(profile.getAbout())
                + "',`Birthday`='" + (String) isNull(profile.getBirthday()) + "',`Sex`='" + (String) isNull(profile.getSex())
                + "',`InterestedIn`='" + (String) isNull(profile.getInterestedIn()) + "',`ReltionshipStatus`='" + (String) isNull(profile.getReltionshipStatus())
                + "',`ReligiousView`='" + (String) isNull(profile.getReligiousView()) + "',`PoliticalViews`='" + (String) isNull(profile.getPoliticalViews())
                + "',`Emails`='" + (String) isNull(profile.getEmails()) + "',`MobilePhones`='" + (String) isNull(profile.getMobilePhones())
                + "',`OtherPhones`='" + (String) isNull(profile.getOtherPhones()) + "',`IMScreenNames`='" + (String) isNull(profile.getIMScreenNames())
                + "',`Address`='" + (String) isNull(profile.getAddress()) + "',`WebSites`='" + (String) isNull(profile.getWebSites())
                + "',`Networks`='" + (String) isNull(profile.getNetworks()) + "',`Job`='" + (String) isNull(profile.getJob()) + "',`College`='" + (String) isNull(profile.getCollege())
                + "',`HighSchool`='" + (String) isNull(profile.getHighSchool()) + "',`CurrentCity`='" + (String) isNull(profile.getCurrentCity())
                + "',`Hometown`='" + (String) isNull(profile.getHometown()) + "',`Family`='" + (String) isNull(profile.getFamily())
                + "',`Books`='" + (String) isNull(profile.getBooks()) + "',`Music`='" + (String) isNull(profile.getMusic()) + "',`Movies`='" + (String) isNull(profile.getMovies())
                + "',`Televisions`='" + (String) isNull(profile.getTelevisions()) + "',`Activities`='" + (String) isNull(profile.getActivities())
                + "',`Interests`='" + (String) isNull(profile.getInterests()) + "',`QuantityOfPhotos`=" + (int) isNull(profile.getQuantityOfPhotos())
                + ",`QuantityOfFriends`=" + (int) isNull(profile.getQuantityOfFriends()) + ",`Friends`='" + (String) isNull(profile.getFriends())
                + "' WHERE `Id` =" + profile.getId() + ";";
        try {
            ps = conn.prepareStatement(query);
            ps.execute();
            ps.close();
            conn.close();
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
        }
    }

    public Set getNext() {
        try {
            conn = getConnection();
            query = "Select Id FROM profile where Recorded = 0;";
            ps = conn.prepareStatement(query);
            ResultSet retorno = ps.executeQuery();
            Set list = new HashSet();
            while (retorno.next()) {
                list.add(retorno.getLong("Id"));
            }
            ps.close();
            conn.close();
            return list;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
        }
        return null;
    }

    private Object isNull(Object object) {
        if (object == "" || object == null) {
            return null;
        } else {
            return object;
        }
    }
}
