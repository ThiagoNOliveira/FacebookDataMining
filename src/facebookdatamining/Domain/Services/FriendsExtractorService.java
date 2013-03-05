package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thiago N. Oliveira
 */
public class FriendsExtractorService {

    public void getFriends(WebClient webClient, int number, long id) throws IOException {
        for (int i = 0; i <= number; i++) {
            String url = "http://www.facebook.com/ajax/browser/list/allfriends/?uid=@&start=" + i + "&__a=1";
            url = url.replace("@", Long.toString(id));
            JavaScriptPage page = webClient.getPage(url);
            String pageS = page.getContent();
            Pattern pattern = Pattern.compile("\\/user\\.php\\?id=(.*?)\\\\");
            Matcher matcher = pattern.matcher(pageS);
            while (matcher.find()) {
                String digits = matcher.group(1);
                System.out.println(digits);
            }
            if (i == 0) {
                i = 23;
            } else {
                i = i + 23;
            }
        }

    }
}
