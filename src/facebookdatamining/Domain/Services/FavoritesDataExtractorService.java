package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.List;

/**
 *
 * @author Thiago N. Oliveira
 */
public class FavoritesDataExtractorService {

    public void getFavoritesInfo(HtmlPage htmlPage) {
        List<DomNode> favorites = htmlPage.querySelectorAll("div.allFavorites table.uiInfoTable tbody");
        for (DomNode node : favorites) {
            if (node.querySelector("div.mediaPortrait div.mediaPageName") != null) {
                System.out.print(node.querySelector("th.label").getTextContent() + ": ");
                List<DomNode> data = node.querySelectorAll("li");
                for (DomNode item : data) {
                    System.out.print(item.querySelector("div.mediaPortrait div.mediaPageName").getTextContent());
                }
            }
            System.out.println("");
        }
    }
}
