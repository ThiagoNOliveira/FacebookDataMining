package facebookdatamining.Controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Services.AboutDataExtractorService;
import facebookdatamining.Domain.Services.FavoritesDataExtractorService;
import facebookdatamining.Domain.Services.FriendsExtractorService;
import facebookdatamining.Domain.Services.LoginService;
import facebookdatamining.Domain.Services.ProfileDataExtractorService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileController implements IBaseController {

    private WebClient webClient;

    public ProfileController() {
        webClient = new WebClient();
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
    }

    @Override
    public void extractInfo(long Id) {
        try {
            LoginService loginService = new LoginService();
            AboutDataExtractorService aboutService = new AboutDataExtractorService(webClient);
            FavoritesDataExtractorService favoritesService = new FavoritesDataExtractorService();
            FriendsExtractorService friendsService = new FriendsExtractorService();
            ProfileDataExtractorService profileService = new ProfileDataExtractorService();


            HtmlPage page = webClient.getPage("https://www.facebook.com");

            loginService.logon(page);

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/info");
            aboutService.getName(page);
            aboutService.getAbout(page);
            aboutService.getBasicInfo(page);
            aboutService.getCityInfo(page);
            aboutService.getFamilyInfo(page);
            aboutService.getEmployersInfo(page);
            aboutService.getContactInfo(page);
            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/favorites");
            favoritesService.getFavoritesInfo(page);
            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            profileService.getQuantityOfFriends(page);
            profileService.getQuantityOfPhotos(page);
            friendsService.getFriends(webClient, 462, Id);
            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            loginService.logout(page);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
