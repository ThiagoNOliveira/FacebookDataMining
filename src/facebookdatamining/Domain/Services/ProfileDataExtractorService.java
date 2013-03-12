package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileDataExtractorService {

    private HtmlPage profilePage;
    private Pattern pattern;
    private Matcher matcher;
    private String digits;
    private String nodeString;

    public ProfileDataExtractorService(HtmlPage profilePage) {
        this.profilePage = profilePage;
    }

    public int getQuantityOfFriends() {
        List<DomNode> nodes = profilePage.querySelectorAll("code.hidden_elem");
        int quantityOfFriends = 0;
        pattern = Pattern.compile("<span class=\"count\">(.*?)<\\/span>");
        try {

            for (DomNode node : nodes) {
                nodeString = node.asXml().toString();
                if (nodeString.contains("<span class=\"text\">Friends</span><span class=\"count\">")) {

                    matcher = pattern.matcher(nodeString);
                    while (matcher.find()) {
                        digits = matcher.group(1);
                        quantityOfFriends = Integer.parseInt(digits.replace(",", ""));
                    }
                }
            }
            return quantityOfFriends;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return quantityOfFriends;
        }
    }

    public int getQuantityOfPhotos() {
        List<DomNode> nodes = profilePage.querySelectorAll("code.hidden_elem");
        int quantityOfPhotos = 0;
        pattern = Pattern.compile("<span class=\"count\">(.*?)<\\/span>");
        try {
            for (DomNode node : nodes) {
                nodeString = node.asXml().toString();
                if (nodeString.contains("<span class=\"text\">Photos</span><span class=\"count\">")) {
                    matcher = pattern.matcher(nodeString);
                    while (matcher.find()) {
                        digits = matcher.group(1);
                        quantityOfPhotos = Integer.parseInt(digits.replace(",", ""));
                    }
                }
            }
            return quantityOfPhotos;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return quantityOfPhotos;
        }
    }
}
