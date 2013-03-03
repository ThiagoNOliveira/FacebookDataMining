package facebookdatamining;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Services.ProfileDataExtractorService;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author Thiago N. Oliveira
 */
public class Main {

    private static WebClient webClient = new WebClient();
    private static long Id = 100003104984663L;

    public static void main(String[] args) throws IOException, InterruptedException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

        ProfileDataExtractorService profileDataExtractorService = new ProfileDataExtractorService(new WebClient());
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        profileDataExtractorService.logon(page);

        //Get info of profile
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
    }
}
