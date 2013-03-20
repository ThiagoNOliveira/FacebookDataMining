package facebookdatamining.InfraStructure;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Thiago Oliveira
 */
public class Config {

    private NodeList nodeList;
    private Node nNode;
    private Element eElement;
    private HashMap accounts = new HashMap();
    private String connectionString;
    private ArrayList<Node> nodes;
    private Logger logg = Logger.getLogger(Config.class);

    public Config() {
        try {
            //File fXmlFile = new File(getApplicationPath() + "\\config\\Config.xml");
            File fXmlFile = new File("c:\\config\\Config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            // Client Integration
            nodeList = doc.getElementsByTagName("config");
            nNode = nodeList.item(0);
            eElement = (Element) nNode;
            
            this.connectionString = getTagValue("ConnectionString", eElement);

            nodeList = doc.getElementsByTagName("acconts");
            nNode = nodeList.item(0);
            nodes = getNodesInTree(nNode, new String[]{"login"});
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                eElement = (Element) node;
                this.accounts.put(eElement.getAttribute("user").toString(), eElement.getAttribute("password").toString());
            }

            // LOG
            //nodeList = doc.getElementsByTagName("log");
            //nNode = nodeList.item(0);
            //eElement = (Element) nNode;

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            logg.warn(sw.toString());
        }
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    public final String getApplicationPath() {
        String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
        File dir = new File(url).getParentFile();
        String path = null;
        if (dir.getPath().contains(".jar")) {
            path = findJarParentPath(dir);
        } else {
            path = dir.getPath();
        }
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (Exception e) {
            return path.replace("%20", " ");
        }
    }

    private String findJarParentPath(File jarFile) {
        while (jarFile.getPath().contains(".jar")) {
            jarFile = jarFile.getParentFile();
        }
        return jarFile.getPath().substring(6);
    }

    /**
     * Método que busca um Node com base em um nível hierarquico. (Método
     * recursivo)
     *
     * @param pai (Node elementoPai)
     * @param niveis (arvore hierárquica)
     * @return Lista de Nodes que possuem nome igual ao ultimo elemento do array
     */
    public static ArrayList<Node> getNodesInTree(Node pai, String[] niveis) {
        NodeList childs = pai.getChildNodes();
        ArrayList<Node> retorno = new ArrayList<Node>();
        for (int i = 0; i < childs.getLength(); i++) {
            Node child = childs.item(i);
            if (niveis.length == 1) {
                // buscando ultimo nível (tag procurada)
                if (child.getNodeName().equals(niveis[0])) {
                    retorno.add(child);
                }
            } else if (child.getNodeName().equals(niveis[0])) {
                niveis = Arrays.copyOfRange(niveis, 1, niveis.length);
                retorno = getNodesInTree(child, niveis);
            }
        }
        return retorno;
    }

    public HashMap getAccounts() {
        return accounts;
    }

    public String getConnectionString() {
        return connectionString;
    }
  
}