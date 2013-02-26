package facebookdatamining;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thiago N. Oliveira
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        WebClient webClient = new WebClient();
        webClient.waitForBackgroundJavaScriptStartingBefore(10000);
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        Main main = new Main();
        page = main.logon(page);

        //Get info of profile
        page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52/info");
        main.getName(page);
        main.getAbout(page);
        main.getBasicInfo(page);
        main.getCityInfo(page);
        main.getFamilyInfo(page);
        main.getEmployersInfo(page);
        main.getContactInfo(page);
        page = webClient.getPage("http://www.facebook.com/luana.pereirasilva.52");
        page = main.logout(page);

    }

    public HtmlPage logon(HtmlPage htmlPage) {
        HtmlForm loginForm = getLoginForm(getFormsOf(htmlPage));
        loginForm.getInputByName("email").setValueAttribute("pereirasilvaluana@yahoo.com.br");
        loginForm.getInputByName("pass").setValueAttribute("leavemealone");
        try {
            return htmlPage = (HtmlPage) loginForm.getInputByValue("Log In").click();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public HtmlPage logout(HtmlPage htmlPage) throws IOException {
        HtmlForm logout = (HtmlForm) htmlPage.querySelector("#logout_form");
        return logout.getInputByValue("Sair").click();
    }

    public HtmlForm getLoginForm(List<HtmlForm> forms) {
        for (HtmlForm form : forms) {
            if (form.getId().equalsIgnoreCase("login_form")) {
                return form;
            }
        }
        return null;
    }

    public List<HtmlForm> getFormsOf(HtmlPage htmlPage) {
        return htmlPage.getForms();
    }

    public void getName(HtmlPage htmlPage) {
        System.out.println(htmlPage.querySelector("a.nameButton span").getTextContent() + "\n");
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

    public void getAbout(HtmlPage htmlPage) {
        System.out.println(htmlPage.querySelector("#pagelet_bio  div.profileText").getTextContent() + "\n");
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
}
