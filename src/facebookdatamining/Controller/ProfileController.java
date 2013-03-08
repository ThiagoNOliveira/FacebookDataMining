package facebookdatamining.Controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Entities.Profile;
import facebookdatamining.Domain.Repository.ProfileRepository;
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
            Profile profile = new Profile();
            profile.setId(Id);
            profile.setCrawllerLevel(1L);
            ProfileRepository profiles = new ProfileRepository();
            
            HtmlPage page = webClient.getPage("https://www.facebook.com");

            LoginService loginService = new LoginService();
            loginService.logon(page);

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/info");
            AboutDataExtractorService aboutService = new AboutDataExtractorService(webClient);
            profiles.add(profile);
            profile.setName(aboutService.getName(page));
            profile.setRecorded(true);
            
            aboutService.getAbout(page);
            aboutService.getBasicInfo(page);
            aboutService.getCityInfo(page);
            aboutService.getFamilyInfo(page);
            aboutService.getEmployersInfo(page);
            aboutService.getContactInfo(page);

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/favorites");
            FavoritesDataExtractorService favoritesService = new FavoritesDataExtractorService();
            favoritesService.getFavoritesInfo(page);

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            ProfileDataExtractorService profileService = new ProfileDataExtractorService();
            profileService.getQuantityOfFriends(page);
            profileService.getQuantityOfPhotos(page);

            FriendsExtractorService friendsService = new FriendsExtractorService();
            friendsService.getFriends(webClient, 462, Id);
            profiles.update(profile);

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            loginService.logout(page);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
