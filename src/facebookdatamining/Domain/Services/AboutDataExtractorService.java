package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.List;

/**
 *
 * @author Thiago N. Oliveira
 */
public class AboutDataExtractorService {

    private WebClient webClient;

    public AboutDataExtractorService(WebClient webClient) {
        this.webClient = webClient;
        this.webClient.waitForBackgroundJavaScriptStartingBefore(10000);
    }

    public String getName(HtmlPage htmlPage) {
        return (String) isNull(htmlPage.querySelector("a.nameButton span").getTextContent());
    }

    public void getBasicInfo(HtmlPage htmlPage) {

        List<DomNode> info = htmlPage.querySelectorAll("#pagelet_basic table.uiInfoTable.profileInfoTable.uiInfoTableFixed tbody");
        for (DomNode text : info) {
            System.out.println(text.querySelector("tr th.label").getTextContent() + ": " + text.querySelector("tr td.data").asText() + "\n");
        }
    }

    public void getCityInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("td.vTop.plm");
        for (DomNode text : info) {
            System.out.println(text.querySelector("div.fsm.fwn.fcg").getTextContent() + ": " + text.querySelector("span.fwb a").getTextContent() + "\n");
        }
    }

    public void getFamilyInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("div.familyItemBody._3dp._29k");
        for (DomNode text : info) {
            System.out.println(text.querySelector("div.fsm.fwn.fcg").getTextContent() + ": " + text.querySelector("div.fsl.fwb.fcb").getTextContent() + "\n");
        }
    }

    public String getAbout(HtmlPage htmlPage) {
        return (String) isNull(htmlPage.querySelector("#pagelet_bio  div.profileText").getTextContent());

    }

    public void getContactInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("#pagelet_contact table tbody");
        for (DomNode text : info) {
            List<DomNode> tableRows = text.querySelectorAll("tr");
            for (DomNode line : tableRows) {
                if (line.querySelector("th.label") != null && line.querySelector("td.data") != null) {
                    System.out.println(line.querySelector("th.label").getTextContent() + ": " + line.querySelector("td.data").asText() + "\n");
                }
            }
        }
    }

    public void getEmployersInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("#pagelet_eduwork table.uiInfoTable.profileInfoTable.uiInfoTableFixed tbody");
        for (DomNode text : info) {
            System.out.println(text.querySelector("tr th.label").getTextContent() + ": " + text.querySelector("tr td.data").asText() + "\n");
        }
    }

    private Object isNull(Object object) {
        if (object == "" || object == null) {
            return null;
        } else {
            return object;
        }
    }
}
