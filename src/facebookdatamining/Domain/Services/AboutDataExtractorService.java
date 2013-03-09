package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public Map getBasicInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("#pagelet_basic table.uiInfoTable.profileInfoTable.uiInfoTableFixed tbody");
        Map map = new HashMap();
        for (DomNode text : info) {
            map.put(text.querySelector("tr th.label").getTextContent(), text.querySelector("tr td.data").asText());
        }
        return map;
    }

    public Map getCityInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("td.vTop.plm");
        Map map = new HashMap();
        for (DomNode text : info) {
            map.put(text.querySelector("div.fsm.fwn.fcg").getTextContent(), text.querySelector("span.fwb a").getTextContent());
        }
        return map;
    }

    public Set getFamilyInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("div.familyItemBody._3dp._29k");
        Set family = new LinkedHashSet();
        for (DomNode text : info) {
            family.add(text.querySelector("div.fsm.fwn.fcg").getTextContent() + ": " + text.querySelector("div.fsl.fwb.fcb").getTextContent());
        }
        return family;
    }

    public String getAbout(HtmlPage htmlPage) {
        return (String) isNull(htmlPage.querySelector("#pagelet_bio  div.profileText").getTextContent());

    }

    public Map getContactInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("#pagelet_contact table tbody");
        Map map = new HashMap();
        for (DomNode text : info) {
            List<DomNode> tableRows = text.querySelectorAll("tr");
            for (DomNode line : tableRows) {
                if (line.querySelector("th.label") != null && line.querySelector("td.data") != null) {
                    map.put(line.querySelector("th.label").getTextContent(), line.querySelector("td.data").asText());
                }
            }
        }
        return map;
    }

    public Map getEmployersInfo(HtmlPage htmlPage) {
        List<DomNode> info = htmlPage.querySelectorAll("#pagelet_eduwork table.uiInfoTable.profileInfoTable.uiInfoTableFixed tbody");
        Map educationAndWork = new HashMap();
        for (DomNode text : info) {
            educationAndWork.put(text.querySelector("tr th.label").getTextContent(), text.querySelector("tr td.data").asText());
        }
        return educationAndWork;
    }

    private Object isNull(Object object) {
        if (object == "" || object == null) {
            return null;
        } else {
            return object;
        }
    }
}
