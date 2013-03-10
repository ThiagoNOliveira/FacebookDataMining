package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Thiago N. Oliveira
 */
public class FavoritesDataExtractorService {

    private HtmlPage favoritesPage;

    public FavoritesDataExtractorService(HtmlPage favoritesPage) {
        this.favoritesPage = favoritesPage;
    }

    public Map getFavoritesInfo() {
        List<DomNode> favorites = favoritesPage.querySelectorAll("div.allFavorites table.uiInfoTable tbody");
        Map favoritesMap = new HashMap();
        for (DomNode node : favorites) {
            if (node.querySelector("div.mediaPortrait div.mediaPageName") != null) {
                List<DomNode> data = node.querySelectorAll("li");
                String value = "";
                for (DomNode item : data) {
                    value += item.querySelector("div.mediaPortrait div.mediaPageName").getTextContent() + "|";
                }
                favoritesMap.put(node.querySelector("th.label").getTextContent(), value);
            }
        }
        return favoritesMap;
    }
}
