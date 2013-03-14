package facebookdatamining.Controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Entities.Profile;
import facebookdatamining.Domain.Repository.ProfileRepository;
import facebookdatamining.Domain.Services.ExtractorService;
import facebookdatamining.Domain.Services.LoginService;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileController implements IBaseController {

    private LoginService loginService;
    private ProfileRepository profiles;
    private WebClient webClient;
    private HtmlPage loginPage;

    public ProfileController() {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        profiles = new ProfileRepository();
        webClient = new WebClient();
        webClient.waitForBackgroundJavaScript(10000);
        webClient.setJavaScriptTimeout(10000);
        webClient.setThrowExceptionOnFailingStatusCode(false);
        webClient.setThrowExceptionOnScriptError(false);
        webClient.setCssEnabled(false);
        webClient.setRedirectEnabled(true);
        webClient.setJavaScriptEnabled(true);
        loginPage = getLoginPage();
    }

    @Override
    public void extractInfo(String login, String password) {

        loginService = new LoginService(loginPage, login, password);
        loginService.logon();

        while (true) {
            for (Profile profile : profiles.getNext()) {

                Profile profileActual = profile;
                ExtractorService extractorService = new ExtractorService(webClient, profiles);
                extractorService.getInfo(profileActual);

                webClient.getCache().clear();
            }
            if (profiles.getNext().isEmpty()) {
                loginService.logout();
                break;
            }
        }
    }

    private HtmlPage getLoginPage() {
        try {
            return webClient.getPage("https://www.facebook.com");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return null;
        }
    }
}
