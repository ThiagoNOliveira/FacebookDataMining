package facebookdatamining;

import com.gargoylesoftware.htmlunit.WebClient;
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

    public static void main(String[] args) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        Main main = new Main();
        page = main.logon(page);
        System.out.println(page.getTitleText());
    }

    public HtmlPage logon(HtmlPage htmlPage) {
        HtmlForm loginForm = getLoginForm(getFormsOf(htmlPage));
        loginForm.getInputByName("email").setValueAttribute("eno.thiago@gmail.com");
        loginForm.getInputByName("pass").setValueAttribute("@880apocalypsw510@");
        try {
            return htmlPage = (HtmlPage) loginForm.getInputByValue("Log In").click();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private HtmlForm getLoginForm(List<HtmlForm> forms) {
        for (HtmlForm form : forms) {
            if (form.getId().equalsIgnoreCase("login_form")) {
                return form;
            }
        }
        return null;
    }

    private List<HtmlForm> getFormsOf(HtmlPage htmlPage) {
        return htmlPage.getForms();
    }
}