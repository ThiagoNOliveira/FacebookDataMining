package facebookdatamining.Controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
            ProfileDataExtractorService profileDataExtractorService = new ProfileDataExtractorService(webClient);
            HtmlPage page = webClient.getPage("https://www.facebook.com");

            profileDataExtractorService.logon(page);

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/info");
            profileDataExtractorService.getName(page);
            profileDataExtractorService.getAbout(page);
            profileDataExtractorService.getBasicInfo(page);
            profileDataExtractorService.getCityInfo(page);
            profileDataExtractorService.getFamilyInfo(page);
            profileDataExtractorService.getEmployersInfo(page);
            profileDataExtractorService.getContactInfo(page);
            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/favorites");
            profileDataExtractorService.getFavoritesInfo(page);
            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            profileDataExtractorService.getQuantityOfFriends(page);
            profileDataExtractorService.getQuantityOfPhotos(page);
            profileDataExtractorService.getFriends(462, Id);
            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            profileDataExtractorService.logout(page);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
