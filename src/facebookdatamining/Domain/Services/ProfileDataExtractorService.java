package facebookdatamining.Domain.Services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Thiago N. Oliveira
 */
public class ProfileDataExtractorService {

    public int getQuantityOfFriends(HtmlPage htmlPage) throws IOException {
        List<DomNode> nodes = htmlPage.querySelectorAll("code.hidden_elem");
        int quantityOfFriends = 0;
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
                quantityOfFriends = Integer.parseInt(startPage.querySelector("li.friends span.count").getTextContent());
            }
            tempFile.deleteOnExit();
        }
        return quantityOfFriends;
    }

    public int getQuantityOfPhotos(HtmlPage htmlPage) throws IOException {
        List<DomNode> nodes = htmlPage.querySelectorAll("code.hidden_elem");
        int quantityOfPhotos = 0;
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
                quantityOfPhotos = Integer.parseInt(startPage.querySelector("li.photos span.count").getTextContent());
            }
            tempFile.deleteOnExit();
        }
        return quantityOfPhotos;
    }
}
