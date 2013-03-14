package facebookdatamining.Domain.Services;

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

    private HtmlPage aboutPage;

    public AboutDataExtractorService(HtmlPage aboutPage) {
        this.aboutPage = aboutPage;
    }

    public String getName() {
        System.out.println("getname " + Thread.activeCount() + " " + aboutPage.getTitleText());
        DomNode name = aboutPage.querySelector("a.nameButton span");
        if (name != null) {
            return name.getTextContent();
        } else {
            return null;
        }
    }

    public Map getBasicInfo() {
        List<DomNode> info = aboutPage.querySelectorAll("#pagelet_basic table.uiInfoTable.profileInfoTable.uiInfoTableFixed tbody");
        Map map = new HashMap();
        for (DomNode text : info) {
            map.put(text.querySelector("tr th.label").getTextContent(), text.querySelector("tr td.data").asText());
        }
        return map;
    }

    public Map getCityInfo() {
        List<DomNode> info = aboutPage.querySelectorAll("td.vTop.plm");
        Map map = new HashMap();
        for (DomNode text : info) {
            DomNode key = text.querySelector("div.fsm.fwn.fcg");
            DomNode value = text.querySelector("span.fwb a");
            if (key != null && value != null) {
                map.put(key.getTextContent(), value.getTextContent());
            }
        }
        return map;
    }

    public Set getFamilyInfo() {
        List<DomNode> info = aboutPage.querySelectorAll("div.familyItemBody._3dp._29k");
        Set family = new LinkedHashSet();
        for (DomNode text : info) {
            family.add(text.querySelector("div.fsm.fwn.fcg").getTextContent() + ": " + text.querySelector("div.fsl.fwb.fcb").getTextContent());
        }
        return family;
    }

    public String getAbout() {
        if (aboutPage.querySelector("#pagelet_bio  div.profileText") != null) {
            return aboutPage.querySelector("#pagelet_bio  div.profileText").getTextContent();
        } else {
            return null;
        }
    }

    public Map getContactInfo() {
        List<DomNode> info = aboutPage.querySelectorAll("#pagelet_contact table tbody");
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

    public Map getEmployersInfo() {
        List<DomNode> info = aboutPage.querySelectorAll("#pagelet_eduwork table.uiInfoTable.profileInfoTable.uiInfoTableFixed tbody");
        Map educationAndWork = new HashMap();
        for (DomNode text : info) {
            educationAndWork.put(text.querySelector("tr th.label").getTextContent(), text.querySelector("tr td.data").asText());
        }
        return educationAndWork;
    }
}
