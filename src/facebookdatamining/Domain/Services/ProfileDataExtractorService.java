package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileDataExtractorService {

    private HtmlPage profilePage;

    public ProfileDataExtractorService(HtmlPage profilePage) {
        this.profilePage = profilePage;
    }

    public int getQuantityOfFriends() {
        return Integer.parseInt(profilePage.querySelector("div#pagelet_timeline_friends_nav_top div.modify a.tab div.title span.count").asText().replace(",", ""));
    }

    public int getQuantityOfPhotos() {
       return Integer.parseInt(profilePage.querySelector("div#pagelet_timeline_photos_nav_top div.modify a.tab div.title span.count").asText().replace(",", ""));
    }
}
