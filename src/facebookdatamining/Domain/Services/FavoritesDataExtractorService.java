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
public class FavoritesDataExtractorService{

    public Map getFavoritesInfo(HtmlPage htmlPage) {
        List<DomNode> favorites = htmlPage.querySelectorAll("div.allFavorites table.uiInfoTable tbody");
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
