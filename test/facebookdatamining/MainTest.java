package facebookdatamining;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.List;
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

    /**
     * Test of logon method, of class Main.
     */
    @Test
    public void logon_in_facebook() throws IOException {
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        Main main = new Main();
        Assert.assertEquals(main.logon(page).getTitleText(), "Facebook");
    }

    /**
     * Test of getLoginForm method, of class Main.
     */
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

    /**
     * Test of getFormsOf method, of class Main.
     */
    @Test
    public void get_forms_of_page() throws IOException {
        HtmlPage page = webClient.getPage("https://www.facebook.com");
        List<HtmlForm> forms = page.getForms();
        Main main = new Main();
        
        Assert.assertEquals(page.getForms(), main.getFormsOf(page));
       
    }
}
