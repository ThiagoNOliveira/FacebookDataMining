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
import java.util.Map;
import java.util.Set;
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
            profile.setAbout(aboutService.getAbout(page));

            Map basicInfo = aboutService.getBasicInfo(page);
            profile.setInterestedIn(basicInfo.get("Interessado em").toString());
            profile.setPoliticalViews(basicInfo.get("Preferência política").toString());
            profile.setReligiousView(basicInfo.get("Religião").toString());
            profile.setReltionshipStatus(basicInfo.get("Status de relacionamento").toString());
            profile.setSex(basicInfo.get("Gênero").toString());
            profile.setBirthday(basicInfo.get("Data de nascimento").toString());

            Map cityInfo = aboutService.getCityInfo(page);
            profile.setCurrentCity(cityInfo.get("Cidade atual").toString());
            profile.setHometown(cityInfo.get("Cidade natal").toString());
            profile.setFamily(aboutService.getFamilyInfo(page).toString());

            Map educationAndWorkInfo = aboutService.getEmployersInfo(page);
            profile.setJob((String)educationAndWorkInfo.get("Empregadores"));
            profile.setCollege((String)educationAndWorkInfo.get("Ensino superior"));
            profile.setHighSchool((String)educationAndWorkInfo.get("Ensino médio"));
            
            Map contactInfo = aboutService.getContactInfo(page);
            profile.setAddress((String) contactInfo.get("Endereço"));
            profile.setEmails((String) contactInfo.get("E-mail"));
            profile.setIMScreenNames((String) contactInfo.get("Outros contatos"));
            profile.setMobilePhones((String) contactInfo.get("Celulares"));
            profile.setNetworks((String) contactInfo.get("Redes"));
            profile.setOtherPhones((String) contactInfo.get("Outros telefones"));
            profile.setWebSites((String) contactInfo.get("Site"));


            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/favorites");
            FavoritesDataExtractorService favoritesService = new FavoritesDataExtractorService();
            Map favoritesInfo = favoritesService.getFavoritesInfo(page);
            profile.setActivities((String) favoritesInfo.get("Atividades"));
            profile.setBooks((String) favoritesInfo.get("Livros"));
            profile.setInterests((String) favoritesInfo.get("Interesses"));
            profile.setMovies((String) favoritesInfo.get("Filmes"));
            profile.setMusic((String) favoritesInfo.get("Música"));
            profile.setTelevisions((String) favoritesInfo.get("Televisão"));

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            ProfileDataExtractorService profileService = new ProfileDataExtractorService();
            profile.setQuantityOfFriends(profileService.getQuantityOfFriends(page));
            profile.setQuantityOfPhotos(profileService.getQuantityOfPhotos(page));

            FriendsExtractorService friendsService = new FriendsExtractorService();
            Set friendsList = friendsService.getFriends(webClient, 462, Id);
            profile.setFriends(friendsList.toString());
            profiles.update(profile);

            page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
            loginService.logout(page);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
