package facebookdatamining.Domain.Repository;

import facebookdatamining.Domain.Entities.Profile;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileRepository extends BaseRepository {

    private Connection conn;
    private PreparedStatement ps;
    private String query;
    private String querySelect;

    public ProfileRepository() {
        conn = getConnection();
    }

    public void add(Set list, Profile profile) {
        query = "";
        try {
            if (conn.isClosed()) {
                conn = getConnection();
            }
            if (!list.isEmpty()) {
                query = "INSERT INTO `profile`(`Id`, `CrawllerLevel`) VALUES ";
                for (Object atual : list) {
                    querySelect = "Select Id FROM profile where Id = ?;";
                    ps = conn.prepareStatement(querySelect);
                    ps.setLong(1, Long.parseLong(atual.toString()));
                    ResultSet retorno = ps.executeQuery();
                    if (!retorno.next()) {
                        query += "(" + Long.parseLong(atual.toString()) + ", " + (profile.getCrawllerLevel() + 1) + "), ";
                    }
                }
                if (query.contains("),")) {
                    query = replaceLast(query, ",", ";");
                    ps = conn.prepareStatement(query);
                    ps.execute();
                    ps.close();
                    //conn.close();
                }
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
        }


    }

    public void update(Profile profile) {

        query = "UPDATE `profile` SET `Recorded`=" + (boolean) isNull(profile.isRecorded())
                + ",`Name`=\"" + replaceAspas((String) isNull(profile.getName())) + "\",`About`=\"" + replaceAspas((String) isNull(profile.getAbout()))
                + "\",`Birthday`=\"" + replaceAspas((String) isNull(profile.getBirthday())) + "\",`Sex`=\"" + replaceAspas((String) isNull(profile.getSex()))
                + "\",`InterestedIn`=\"" + replaceAspas((String) isNull(profile.getInterestedIn())) + "\",`ReltionshipStatus`=\"" + replaceAspas((String) isNull(profile.getReltionshipStatus()))
                + "\",`ReligiousView`=\"" + replaceAspas((String) isNull(profile.getReligiousView())) + "\",`PoliticalViews`=\"" + replaceAspas((String) isNull(profile.getPoliticalViews()))
                + "\",`Emails`=\"" + replaceAspas((String) isNull(profile.getEmails())) + "\",`MobilePhones`=\"" + replaceAspas((String) isNull(profile.getMobilePhones()))
                + "\",`OtherPhones`=\"" + replaceAspas((String) isNull(profile.getOtherPhones())) + "\",`IMScreenNames`=\"" + replaceAspas((String) isNull(profile.getIMScreenNames()))
                + "\",`Address`=\"" + replaceAspas((String) isNull(profile.getAddress())) + "\",`WebSites`=\"" + replaceAspas((String) isNull(profile.getWebSites()))
                + "\",`Networks`=\"" + replaceAspas((String) isNull(profile.getNetworks())) + "\",`Job`=\"" + replaceAspas((String) isNull(profile.getJob())) + "\",`College`=\"" + replaceAspas((String) isNull(profile.getCollege()))
                + "\",`HighSchool`=\"" + replaceAspas((String) isNull(profile.getHighSchool())) + "\",`CurrentCity`=\"" + replaceAspas((String) isNull(profile.getCurrentCity()))
                + "\",`Hometown`=\"" + replaceAspas((String) isNull(profile.getHometown())) + "\",`Family`=\"" + replaceAspas((String) isNull(profile.getFamily()))
                + "\",`Books`=\"" + replaceAspas((String) isNull(profile.getBooks())) + "\",`Music`=\"" + replaceAspas((String) isNull(profile.getMusic())) + "\",`Movies`=\"" + replaceAspas((String) isNull(profile.getMovies()))
                + "\",`Televisions`=\"" + replaceAspas((String) isNull(profile.getTelevisions())) + "\",`Activities`=\"" + replaceAspas((String) isNull(profile.getActivities()))
                + "\",`Interests`=\"" + replaceAspas((String) isNull(profile.getInterests())) + "\",`QuantityOfPhotos`=" + (int) isNull(profile.getQuantityOfPhotos())
                + ",`QuantityOfFriends`=" + (int) isNull(profile.getQuantityOfFriends()) + " WHERE `Id` = " + profile.getId() + ";";
        try {
            if (conn.isClosed()) {
                conn = getConnection();
            }
            ps = conn.prepareStatement(query);
            ps.execute();
            ps.close();
            //conn.close();
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
        }
    }

    public void updateFriends(Profile profile, Set friendsList) {
        query = "UPDATE `profile` SET `Friends`=\"" + friendsList.toString() + "\" WHERE `Id` = " + profile.getId() + ";";
        try {
            if (conn.isClosed()) {
                conn = getConnection();
            }
            ps = conn.prepareStatement(query);
            ps.execute();
            ps.close();
            //conn.close();
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
        }
    }

    public Set<Profile> getNext() {
        try {
            if (conn.isClosed()) {
                conn = getConnection();
            }
            query = "Select `Id`, `CrawllerLevel` FROM profile where Recorded = 0;";
            ps = conn.prepareStatement(query);
            ResultSet retorno = ps.executeQuery();
            Set list = new HashSet();
            while (retorno.next()) {
                Profile profile = new Profile();
                profile.setId(retorno.getLong("Id"));
                profile.setCrawllerLevel(retorno.getLong("CrawllerLevel"));
                list.add(profile);
            }
            ps.close();
            //conn.close();
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

    private String replaceLast(String string, String from, String to) {
        int lastIndex = string.lastIndexOf(from);
        if (lastIndex < 0) {
            return string;
        }
        String tail = string.substring(lastIndex).replaceFirst(from, to);
        return string.substring(0, lastIndex) + tail;
    }

    private String replaceAspas(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        } else {
            return string.replaceAll("\"", "");
        }
    }
}
