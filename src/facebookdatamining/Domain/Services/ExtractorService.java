package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Controller.ProfileController;
import facebookdatamining.Domain.Entities.Profile;
import facebookdatamining.Domain.Repository.ProfileRepository;
import facebookdatamining.InfraStructure.ExtractProfileInfoTask;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void getInfo(Profile profile) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        CompletionService<Object> cs = new ExecutorCompletionService<>(es);

        HtmlPage profilePage = getProfilePage(profile);
        String profileURL = profilePage.getUrl().toString();
        HtmlPage aboutPage = getAboutPage(profileURL);
        HtmlPage favoritesPage = getFavoritesPage(profileURL);

        final ExtractProfileInfoTask task = new ExtractProfileInfoTask(profile, profilePage, aboutPage, favoritesPage, profiles);

        cs.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                task.extractProfileInfo();
                return null;
            }
        });
    }

    private void getFriends(Profile profile, FriendsExtractorService friendsService) {
        if (profile.getCrawllerLevel() <= 3) {
            Set friendsList = friendsService.getFriends();
            profiles.add(friendsList, profile);
            profiles.updateFriends(profile, friendsList);
        }
    }
}
