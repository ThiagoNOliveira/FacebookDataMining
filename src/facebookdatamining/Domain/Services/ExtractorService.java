package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Entities.Profile;
import facebookdatamining.Domain.Repository.ProfileRepository;
import facebookdatamining.InfraStructure.ExtractProfileInfoTask;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ExtractorService {

    private WebClient webClient;
    private final String urlBase = "http://www.facebook.com";
    private ProfileRepository profiles;

    public ExtractorService(WebClient webClient, ProfileRepository profiles) {
        this.profiles = profiles;
        this.webClient = webClient;
    }

    private HtmlPage getProfilePage(Profile profile) {
        try {
            HtmlPage htmlPage = webClient.getPage(urlBase + "/profile.php?id=" + profile.getId());
            //System.out.println(htmlPage.getTitleText());
            //if (htmlPage.getTitleText().contains("Facebook")) {
            //  LoginService loginService = new LoginService((HtmlPage) webClient.getPage("https://www.facebook.com"));
            //  loginService.logon();
            //    return getProfilePage(profile);
            //}
            return htmlPage;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return null;
        }
    }

    private HtmlPage getAboutPage(String profileURL) {
        try {
            if (profileURL.contains("profile.php?id=")) {
                return webClient.getPage(profileURL + "&sk=info");
            } else {
                return webClient.getPage(profileURL + "/info");
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return null;
        }
    }

    private HtmlPage getFavoritesPage(String profileURL) {
        try {
            if (profileURL.contains("profile.php?id=")) {
                return webClient.getPage(profileURL + "&sk=favorites");
            } else {
                return webClient.getPage(profileURL + "/favorites");
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return null;
        }
    }

    public void getInfo(Profile profile) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        try {
            es.awaitTermination(2, TimeUnit.SECONDS);

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
        }
        CompletionService<Object> cs = new ExecutorCompletionService<>(es);

        HtmlPage profilePage = getProfilePage(profile);

        if (!profilePage.getTitleText().contains("Page Not Found")) {

            String profileURL = profilePage.getUrl().toString();
            HtmlPage aboutPage = getAboutPage(profileURL);
            HtmlPage favoritesPage = getFavoritesPage(profileURL);
            String friends = getFriendsData(profile);

            final ExtractProfileInfoTask task = new ExtractProfileInfoTask(profile, profilePage, aboutPage, favoritesPage, friends, profiles);

            cs.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    task.extractProfileInfo();
                    return null;
                }
            });

            es.shutdown();
        }
    }

    private String getFriendsData(Profile profile) {
        String friends = "";
        if (profile.getCrawllerLevel() <= 3) {
            for (int i = 0; i <= profile.getQuantityOfFriends(); i++) {
                try {
                    JavaScriptPage page = webClient.getPage("http://www.facebook.com/ajax/browser/list/allfriends/?uid=" + Long.toString(profile.getId()) + "&start=" + i + "&__a=1");
                    friends += page.getContent();
                } catch (Exception ex) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    System.out.println(sw.toString());
                }
                if (i == 0) {
                    i = 23;
                } else {
                    i = i + 23;
                }
            }
        }
        return friends;
    }
}
