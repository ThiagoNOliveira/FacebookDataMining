package facebookdatamining.InfraStructure;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Entities.Profile;
import facebookdatamining.Domain.Repository.ProfileRepository;
import facebookdatamining.Domain.Services.AboutDataExtractorService;
import facebookdatamining.Domain.Services.FavoritesDataExtractorService;
import facebookdatamining.Domain.Services.ProfileDataExtractorService;
import java.util.Map;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ExtractProfileInfoTask {

    private Profile profile;
    private HtmlPage profilePage;
    private HtmlPage aboutPage;
    private HtmlPage favoritesPage;
    private ProfileRepository profiles;

    public ExtractProfileInfoTask(Profile profile, HtmlPage profilePage, HtmlPage aboutPage, HtmlPage favoritesPage, ProfileRepository profiles) {
        this.profile = profile;
        this.profilePage = profilePage;
        this.aboutPage = aboutPage;
        this.favoritesPage = favoritesPage;
        this.profiles = profiles;
    }

    public void extractProfileInfo() {
        ProfileDataExtractorService profileService = new ProfileDataExtractorService(profilePage);
        extractProfileInfo(profile, profileService);

        AboutDataExtractorService aboutService = new AboutDataExtractorService(aboutPage);
        extractAboutInfo(profile, aboutService);

        FavoritesDataExtractorService favoritesService = new FavoritesDataExtractorService(favoritesPage);
        extractFavoritesInfo(favoritesService, profile);

        profiles.update(profile);
    }

    private void extractAboutInfo(Profile profile, AboutDataExtractorService aboutService) {
        profile.setName(aboutService.getName());
        profile.setRecorded(true);
        profile.setAbout(aboutService.getAbout());

        Map basicInfo = aboutService.getBasicInfo();

        profile.setInterestedIn(isNull(basicInfo.get("Interessado em")));
        profile.setPoliticalViews(isNull(basicInfo.get("Preferência política")));
        profile.setReligiousView(isNull(basicInfo.get("Religião")));
        profile.setReltionshipStatus(isNull(basicInfo.get("Status de relacionamento")));
        profile.setSex(isNull(basicInfo.get("Gênero")));
        profile.setBirthday(isNull(basicInfo.get("Data de nascimento")));

        Map cityInfo = aboutService.getCityInfo();

        profile.setCurrentCity(isNull(cityInfo.get("Cidade atual")));
        profile.setHometown(isNull(cityInfo.get("Cidade natal")));
        profile.setFamily(isNull(aboutService.getFamilyInfo()));

        Map educationAndWorkInfo = aboutService.getEmployersInfo();

        profile.setJob(isNull(educationAndWorkInfo.get("Empregadores")));
        profile.setCollege(isNull(educationAndWorkInfo.get("Ensino superior")));
        profile.setHighSchool(isNull(educationAndWorkInfo.get("Ensino médio")));

        Map contactInfo = aboutService.getContactInfo();

        profile.setAddress(isNull(contactInfo.get("Endereço")));
        profile.setEmails(isNull(contactInfo.get("E-mail")));
        profile.setIMScreenNames(isNull(contactInfo.get("Outros contatos")));
        profile.setMobilePhones(isNull(contactInfo.get("Celulares")));
        profile.setNetworks(isNull(contactInfo.get("Redes")));
        profile.setOtherPhones(isNull(contactInfo.get("Outros telefones")));
        profile.setWebSites(isNull(contactInfo.get("Site")));
    }

    private void extractFavoritesInfo(FavoritesDataExtractorService favoritesService, Profile profile) {
        Map favoritesInfo = favoritesService.getFavoritesInfo();

        profile.setActivities(isNull(favoritesInfo.get("Atividades")));
        profile.setBooks(isNull(favoritesInfo.get("Livros")));
        profile.setInterests(isNull(favoritesInfo.get("Interesses")));
        profile.setMovies(isNull(favoritesInfo.get("Filmes")));
        profile.setMusic(isNull(favoritesInfo.get("Música")));
        profile.setTelevisions(isNull(favoritesInfo.get("Televisão")));
    }

    private void extractProfileInfo(Profile profile, ProfileDataExtractorService profileService) {
        profile.setQuantityOfFriends(profileService.getQuantityOfFriends());
        profile.setQuantityOfPhotos(profileService.getQuantityOfPhotos());
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
