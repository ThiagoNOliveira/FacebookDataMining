package facebookdatamining.Controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import facebookdatamining.Domain.Entities.Profile;
import facebookdatamining.Domain.Repository.ProfileRepository;
import facebookdatamining.Domain.Services.ExtractorService;
import facebookdatamining.Domain.Services.FriendsExtractorService;
import facebookdatamining.Domain.Services.LoginService;
import facebookdatamining.InfraStructure.ExtractProfileInfoTask;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        webClient.setThrowExceptionOnFailingStatusCode(false);
        loginPage = getLoginPage();
    }

    @Override
    public void extractInfo() {
        //ExecutorService es = Executors.newFixedThreadPool(50);
        //CompletionService<Object> cs = new ExecutorCompletionService<>(es);

        loginService = new LoginService(loginPage);
        loginService.logon();

        while (true) {
            for (Profile profile : profiles.getNext()) {

                final Profile profileActual = profile;
                final ExtractorService extractorService = new ExtractorService(webClient, profiles);

                //cs.submit(new Callable<Object>() {
                  //  @Override
                  //  public Object call() throws Exception {
                        extractorService.getInfo(profileActual);
                  //      return null;
                    }
               // });
          //  }

            loginService.logout();
            break;
        }
    }

    private HtmlPage getLoginPage() {
        try {
            return webClient.getPage("https://www.facebook.com");
        } catch (Exception ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
