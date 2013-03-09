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
    public void extractInfo() {
        String url = null;
        try {
            HtmlPage page = webClient.getPage("https://www.facebook.com");
            LoginService loginService = new LoginService();
            loginService.logon(page);

            while (true) {
                ProfileRepository profiles = new ProfileRepository();
                for (Object id : profiles.getNext()) {
                    Profile profile = new Profile();
                    profile.setId(Long.parseLong(id.toString()));
                    profile.setCrawllerLevel(1L);

                    page = webClient.getPage("http://www.facebook.com/people/-/" + profile.getId());
                    url = page.getUrl().toString();
                    page = webClient.getPage(url + "/info");
                    AboutDataExtractorService aboutService = new AboutDataExtractorService(webClient);

                    profiles.add(profile);
                    profile.setName(aboutService.getName(page));
                    profile.setRecorded(true);
                    profile.setAbout(aboutService.getAbout(page));

                    Map basicInfo = aboutService.getBasicInfo(page);
                    profile.setInterestedIn(isNull(basicInfo.get("Interessado em")));
                    profile.setPoliticalViews(isNull(basicInfo.get("Preferência política")));
                    profile.setReligiousView(isNull(basicInfo.get("Religião")));
                    profile.setReltionshipStatus(isNull(basicInfo.get("Status de relacionamento")));
                    profile.setSex(isNull(basicInfo.get("Gênero")));
                    profile.setBirthday(isNull(basicInfo.get("Data de nascimento")));

                    Map cityInfo = aboutService.getCityInfo(page);
                    profile.setCurrentCity(isNull(cityInfo.get("Cidade atual")));
                    profile.setHometown(isNull(cityInfo.get("Cidade natal")));
                    profile.setFamily(isNull(aboutService.getFamilyInfo(page)));

                    Map educationAndWorkInfo = aboutService.getEmployersInfo(page);
                    profile.setJob(isNull(educationAndWorkInfo.get("Empregadores")));
                    profile.setCollege(isNull(educationAndWorkInfo.get("Ensino superior")));
                    profile.setHighSchool(isNull(educationAndWorkInfo.get("Ensino médio")));

                    Map contactInfo = aboutService.getContactInfo(page);
                    profile.setAddress(isNull(contactInfo.get("Endereço")));
                    profile.setEmails(isNull(contactInfo.get("E-mail")));
                    profile.setIMScreenNames(isNull(contactInfo.get("Outros contatos")));
                    profile.setMobilePhones(isNull(contactInfo.get("Celulares")));
                    profile.setNetworks(isNull(contactInfo.get("Redes")));
                    profile.setOtherPhones(isNull(contactInfo.get("Outros telefones")));
                    profile.setWebSites(isNull(contactInfo.get("Site")));


                    page = webClient.getPage(url + "/favorites");
                    FavoritesDataExtractorService favoritesService = new FavoritesDataExtractorService();
                    Map favoritesInfo = favoritesService.getFavoritesInfo(page);
                    profile.setActivities(isNull(favoritesInfo.get("Atividades")));
                    profile.setBooks(isNull(favoritesInfo.get("Livros")));
                    profile.setInterests(isNull(favoritesInfo.get("Interesses")));
                    profile.setMovies(isNull(favoritesInfo.get("Filmes")));
                    profile.setMusic(isNull(favoritesInfo.get("Música")));
                    profile.setTelevisions(isNull(favoritesInfo.get("Televisão")));

                    page = webClient.getPage(url);
                    ProfileDataExtractorService profileService = new ProfileDataExtractorService();
                    profile.setQuantityOfFriends(profileService.getQuantityOfFriends(page));
                    profile.setQuantityOfPhotos(profileService.getQuantityOfPhotos(page));

                    FriendsExtractorService friendsService = new FriendsExtractorService();
                    Set friendsList = friendsService.getFriends(webClient, profile.getQuantityOfFriends(), profile.getId());
                    for (Object item : friendsList) {
                        Profile profile1 = new Profile();
                        profile1.setId(Long.parseLong(item.toString()));
                        profile1.setCrawllerLevel(profile.getCrawllerLevel() + 1);
                        profiles.add(profile1);
                    }
                    profile.setFriends(friendsList.toString());
                    profiles.update(profile);
                }
                page = webClient.getPage(url);
                loginService.logout(page);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static <T> String isNull(T object) {
        if (object == "" || object == null) {
            return null;
        } else {
            if (object instanceof String) {
                return (String) object;
            }
            return null;
        }
    }
}
