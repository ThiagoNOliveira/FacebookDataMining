package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thiago N. Oliveira
 */
public class FriendsExtractorService {

    private long id;
    private int number;
    private WebClient webClient;
    private Set friends = new LinkedHashSet();
    private JavaScriptPage page;
    private String pageS;
    private Pattern pattern;
    private Matcher matcher;
    private String digits;
    private final String url = "http://www.facebook.com/ajax/browser/list/allfriends/?uid=";

    public FriendsExtractorService(WebClient webClient, int number, long id) {
        this.id = id;
        this.number = number;
        this.webClient = webClient;
    }

    public Set getFriends() {
        try {
            for (int i = 0; i <= number; i++) {
                page = webClient.getPage(url + Long.toString(id) + "&start=" + i + "&__a=1");
                pageS = page.getContent();
                pattern = Pattern.compile("\\/user\\.php\\?id=(.*?)\\\\");
                matcher = pattern.matcher(pageS);
                while (matcher.find()) {
                    digits = matcher.group(1);
                    friends.add(digits);
                }
                if (i == 0) {
                    i = 23;
                } else {
                    i = i + 23;
                }
            }
            return friends;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return friends;
        }
    }
}
