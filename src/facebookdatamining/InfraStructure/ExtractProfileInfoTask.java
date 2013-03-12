package facebookdatamining.InfraStructure;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Entities.Profile;
import facebookdatamining.Domain.Repository.ProfileRepository;
import facebookdatamining.Domain.Services.AboutDataExtractorService;
import facebookdatamining.Domain.Services.FavoritesDataExtractorService;
import facebookdatamining.Domain.Services.FriendsExtractorService;
import facebookdatamining.Domain.Services.ProfileDataExtractorService;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ExtractProfileInfoTask {

    private Profile profile;
    private HtmlPage profilePage;
    private HtmlPage aboutPage;
    private HtmlPage favoritesPage;
    private String friendsData;
    private ProfileRepository profiles;

    public ExtractProfileInfoTask(Profile profile, HtmlPage profilePage, HtmlPage aboutPage, HtmlPage favoritesPage, String friendsData, ProfileRepository profiles) {
        this.profile = profile;
        this.profilePage = profilePage;
        this.aboutPage = aboutPage;
        this.favoritesPage = favoritesPage;
        this.friendsData = friendsData;
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

        FriendsExtractorService friendsExtractorService = new FriendsExtractorService(friendsData);
        extractProfileFriends(friendsExtractorService);

        Thread.currentThread().interrupt();
    }

    private void extractAboutInfo(Profile profile, AboutDataExtractorService aboutService) {
        profile.setName(aboutService.getName());
        profile.setRecorded(true);
        profile.setAbout(aboutService.getAbout());

        Map basicInfo = aboutService.getBasicInfo();

        profile.setInterestedIn(isNull(basicInfo.get("Interested In")));
        profile.setPoliticalViews(isNull(basicInfo.get("Political Views")));
        profile.setReligiousView(isNull(basicInfo.get("Religion")));
        profile.setReltionshipStatus(isNull(basicInfo.get("Relationship Status")));
        profile.setSex(isNull(basicInfo.get("Sex")));
        profile.setBirthday(isNull(basicInfo.get("Birthday")));

        Map cityInfo = aboutService.getCityInfo();

        profile.setCurrentCity(isNull(cityInfo.get("Current City")));
        profile.setHometown(isNull(cityInfo.get("Hometown")));
        profile.setFamily(isNull(aboutService.getFamilyInfo()));

        Map educationAndWorkInfo = aboutService.getEmployersInfo();

        profile.setJob(isNull(educationAndWorkInfo.get("Employers")));
        profile.setCollege(isNull(educationAndWorkInfo.get("College")));
        profile.setHighSchool(isNull(educationAndWorkInfo.get("High School")));

        Map contactInfo = aboutService.getContactInfo();

        profile.setAddress(isNull(contactInfo.get("Address")));
        profile.setEmails(isNull(contactInfo.get("Emails")));
        profile.setIMScreenNames(isNull(contactInfo.get("IM Screen Names")));
        profile.setMobilePhones(isNull(contactInfo.get("Mobile Phones")));
        profile.setNetworks(isNull(contactInfo.get("Networks")));
        profile.setOtherPhones(isNull(contactInfo.get("Other Phones")));
        profile.setWebSites(isNull(contactInfo.get("Website")));
    }

    private void extractFavoritesInfo(FavoritesDataExtractorService favoritesService, Profile profile) {
        Map favoritesInfo = favoritesService.getFavoritesInfo();

        profile.setActivities(isNull(favoritesInfo.get("Activities")));
        profile.setBooks(isNull(favoritesInfo.get("Books")));
        profile.setInterests(isNull(favoritesInfo.get("Interests")));
        profile.setMovies(isNull(favoritesInfo.get("Movies")));
        profile.setMusic(isNull(favoritesInfo.get("Music")));
        profile.setTelevisions(isNull(favoritesInfo.get("Televisions")));
    }

    private void extractProfileInfo(Profile profile, ProfileDataExtractorService profileService) {
        profile.setQuantityOfFriends(profileService.getQuantityOfFriends());
        profile.setQuantityOfPhotos(profileService.getQuantityOfPhotos());
    }

    private void extractProfileFriends(FriendsExtractorService friendsExtractorService) {
        Set friendsList = friendsExtractorService.getFriends();
        profiles.add(friendsList, profile);
        profiles.updateFriends(profile, friendsList);
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
