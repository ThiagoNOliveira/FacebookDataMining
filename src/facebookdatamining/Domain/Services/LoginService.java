package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Main;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thiago N. Oliveira
 */
public class LoginService {

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

    public HtmlPage logout(HtmlPage htmlPage) {
        HtmlForm logout = (HtmlForm) htmlPage.querySelector("#logout_form");
        try {
            return logout.getInputByValue("Sair").click();
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
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
