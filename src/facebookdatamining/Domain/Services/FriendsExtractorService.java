package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thiago N. Oliveira
 */
public class FriendsExtractorService {

    public Set getFriends(WebClient webClient, int number, long id) throws IOException {
        Set friends = new LinkedHashSet();
        for (int i = 0; i <= number; i++) {
            String url = "http://www.facebook.com/ajax/browser/list/allfriends/?uid=@&start=" + i + "&__a=1";
            url = url.replace("@", Long.toString(id));
            JavaScriptPage page = webClient.getPage(url);
            String pageS = page.getContent();
            Pattern pattern = Pattern.compile("\\/user\\.php\\?id=(.*?)\\\\");
            Matcher matcher = pattern.matcher(pageS);
            while (matcher.find()) {
                String digits = matcher.group(1);
                friends.add(digits);
            }
            if (i == 0) {
                i = 23;
            } else {
                i = i + 23;
            }
        }
        return friends;

    }
}
