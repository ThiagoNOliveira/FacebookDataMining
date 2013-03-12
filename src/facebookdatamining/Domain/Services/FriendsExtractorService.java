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

    private String friendsData;
    private Set friends = new LinkedHashSet();
    private Pattern pattern;
    private Matcher matcher;
    private String digits;

    public FriendsExtractorService(String frinendsData) {
        this.friendsData = frinendsData;
    }

    public Set getFriends() {
        pattern = Pattern.compile("\\/user\\.php\\?id=(.*?)\\\\");
        matcher = pattern.matcher(friendsData);
        while (matcher.find()) {
            digits = matcher.group(1);
            friends.add(digits);
        }
        return friends;
    }
}
