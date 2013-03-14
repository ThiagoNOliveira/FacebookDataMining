package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 *
 * @author Thiago N. Oliveira
 */
public class LoginService {

    private HtmlPage loginPage;
    private HtmlPage homePage;
    private String login;
    private String password;

    public LoginService(HtmlPage loginPage, String login, String password) {
        this.loginPage = loginPage;
        this.login = login;
        this.password = password;
    }

    public void logon() {
        System.out.println(loginPage.getTitleText());
        HtmlForm loginForm = getLoginForm(getFormsOf(loginPage));
        loginForm.getInputByName("email").setValueAttribute(login);
        loginForm.getInputByName("pass").setValueAttribute(password);
        try {
            homePage = (HtmlPage) loginForm.getInputByValue("Log In").click();
            // if (homePage.getTitleText().contains("Log In | Facebook")) {
            //   loginForm = getLoginForm(getFormsOf(homePage));
            //loginForm.getInputByName("email").setValueAttribute("pereirasilvaluana@yahoo.com.br");
            // loginForm.getInputByName("pass").setValueAttribute("leavemealone1");
            // homePage = (HtmlPage) loginForm.getInputByValue("Log In").click();
            // if (homePage.getTitleText().contains("Log In | Facebook")) {
            //    logon();
            //}
            // }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            logon();
        }
    }

    public HtmlPage logout() {
        HtmlForm logout = (HtmlForm) homePage.querySelector("#logout_form");
        try {
            return logout.getInputByValue("Log Out").click();
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
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
