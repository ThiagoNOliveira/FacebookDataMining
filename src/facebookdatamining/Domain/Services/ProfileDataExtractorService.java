package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileDataExtractorService {

    private HtmlPage profilePage;

    public ProfileDataExtractorService(HtmlPage profilePage) {
        this.profilePage = profilePage;
    }

    public int getQuantityOfFriends() {
        List<DomNode> nodes = profilePage.querySelectorAll("code.hidden_elem");
        int quantityOfFriends = 0;
        try {
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
                    quantityOfFriends = Integer.parseInt(startPage.querySelector("li.friends span.count").getTextContent().replace(".", ""));
                }
                tempFile.deleteOnExit();
            }
            return quantityOfFriends;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return quantityOfFriends;
        }
    }

    public int getQuantityOfPhotos() {
        List<DomNode> nodes = profilePage.querySelectorAll("code.hidden_elem");
        int quantityOfPhotos = 0;

        try {
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
                    quantityOfPhotos = Integer.parseInt(startPage.querySelector("li.photos span.count").getTextContent().replace(".", ""));
                }
                tempFile.deleteOnExit();
            }
            return quantityOfPhotos;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            return quantityOfPhotos;
        }
    }
}
