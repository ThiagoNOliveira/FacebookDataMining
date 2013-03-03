package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileDataExtractorService {

    private WebClient webClient;

    public ProfileDataExtractorService(WebClient webClient) {
        this.webClient = webClient;
        this.webClient.waitForBackgroundJavaScriptStartingBefore(10000);
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

    public void getQuantityOfFriends(HtmlPage htmlPage) throws IOException {
        List<DomNode> nodes = htmlPage.querySelectorAll("code.hidden_elem");
        for (DomNode node : nodes) {
            WebClient webClientM = new WebClient();
            File tempFile = File.createTempFile("fragment", "html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write("<html><head></head><body>");
            bw.write(node.asXml().replace("<!--", "").replace("-->", ""));
            bw.write("</body></html>");
            bw.close();
            HtmlPage startPage = webClientM.getPage(tempFile.toURI().toURL().toString());
            if (startPage.querySelector("li.friends span.count") != null) {
                System.out.println(startPage.querySelector("li.friends span.text").getTextContent() + ": " + startPage.querySelector("li.friends span.count").getTextContent());
            }
            tempFile.deleteOnExit();
        }
    }

    public void getQuantityOfPhotos(HtmlPage htmlPage) throws IOException {
        List<DomNode> nodes = htmlPage.querySelectorAll("code.hidden_elem");
        for (DomNode node : nodes) {
            WebClient webClientM = new WebClient();
            File tempFile = File.createTempFile("fragment", "html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write("<html><head></head><body>");
            bw.write(node.asXml().replace("<!--", "").replace("-->", ""));
            bw.write("</body></html>");
            bw.close();
            HtmlPage startPage = webClientM.getPage(tempFile.toURI().toURL().toString());
            if (startPage.querySelector("li.photos span.count") != null) {
                System.out.println(startPage.querySelector("li.photos span.text").getTextContent() + ": " + startPage.querySelector("li.photos span.count").getTextContent());
            }
            tempFile.deleteOnExit();
        }
    }

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

    public void getFriends(int number, long id) throws IOException {
        for (int i = 0; i <= number; i++) {
            String url = "http://www.facebook.com/ajax/browser/list/allfriends/?uid=@&start=" + i + "&__a=1";
            url = url.replace("@", Long.toString(id));
            JavaScriptPage page = webClient.getPage(url);
            String pageS = page.getContent();
            Pattern pattern = Pattern.compile("\\/user\\.php\\?id=(.*?)\\\\");
            Matcher matcher = pattern.matcher(pageS);
            while (matcher.find()) {
                String digits = matcher.group(1);
                System.out.println(digits);
            }
            if (i == 0) {
                i = 23;
            } else {
                i = i + 23;
            }
        }

    }
    
    public HtmlPage logon(HtmlPage htmlPage) {
        HtmlForm loginForm = getLoginForm(getFormsOf(htmlPage));
        loginForm.getInputByName("email").setValueAttribute("pereirasilvaluana@yahoo.com.br");
        loginForm.getInputByName("pass").setValueAttribute("leavemealone1");
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
}
