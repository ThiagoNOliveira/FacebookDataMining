package facebookdatamining;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Thiago N. Oliveira
 */
public class MainTest {

    WebClient webClient;

    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
    }
/*
    @Test
    public void logon_in_facebook() throws IOException {
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        Main main = new Main();
        Assert.assertEquals(main.logon(page).getTitleText(), "Facebook");
    }

    @Test
    public void get_login_form() throws IOException {
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        List<HtmlForm> forms = page.getForms();
        HtmlForm loginform = null;

        for (HtmlForm form : forms) {
            if (form.getId().equalsIgnoreCase("login_form")) {
                loginform = form;
            }
        }

        Main main = new Main();
        Assert.assertEquals(loginform, main.getLoginForm(page.getForms()));
    }

    @Test
    public void get_forms_of_page() throws IOException {
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        List<HtmlForm> forms = page.getForms();
        Main main = new Main();

        Assert.assertEquals(page.getForms(), main.getFormsOf(page));

    }

    @Test
    public void get_city_info() throws IOException {
        HtmlPage htmlPage = logonHelper();
        htmlPage = webClient.getPage("http://www.facebook.com/nay.francine/info");
        Main main = new Main();
        Map citiesInfo = new HashMap();
        citiesInfo.put("Current City", "Contagem");
        Assert.assertEquals(citiesInfo, main.getCityInfo(htmlPage));
    }
*/
    public HtmlPage logonHelper() throws IOException {
        HtmlPage htmlPage = webClient.getPage("https://www.facebook.com");
        List<HtmlForm> forms = htmlPage.getForms();
        HtmlForm loginForm = null;
        for (HtmlForm form : forms) {
            if (form.getId().equalsIgnoreCase("login_form")) {
                loginForm = form;
            }
        }

        loginForm.getInputByName("email").setValueAttribute("eno.thiago@gmail.com");
        loginForm.getInputByName("pass").setValueAttribute("@880apocalypsw510@");

        return htmlPage = (HtmlPage) loginForm.getInputByValue("Log In").click();
    }
}
