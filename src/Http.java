import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Http {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String ip) throws Exception {

        Http http = new Http();

        String xml = http.sendGet(ip);
        File xmlFile = new File("myFile.xml");
        PrintWriter printWriter = new PrintWriter(xmlFile);
        printWriter.write(xml);
        printWriter.flush();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();


        NodeList nList = doc.getElementsByTagName("administrativeContact");


        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                try {
                    Element eElement = (Element) nNode;
                    String name = getAtr("name", eElement);
                    System.out.println("name: " + getAtr("name", eElement));
                    String street = getAtr("street1", eElement);
                    System.out.println("street: " + street);
                    String city = getAtr("city", eElement);
                    System.out.println("city: " + city);
                    String postalCode = getAtr("postalCode", eElement);
                    System.out.println("postalcode: " + postalCode);
                    String telephone = getAtr("telephone", eElement);
                    System.out.println("phone: " + telephone);
                    String fax = getAtr("fax", eElement);
                    System.out.println("fax: " + fax);
                } catch (Exception e) {
                    System.out.println("Информация отсутствует");
                }
            }
        }

    }

    private static String getAtr(String s, Element eElement) {
        NodeList name = eElement.getElementsByTagName(s);

        Element fstNmElmnt = (Element) name.item(0);
        NodeList fstNm = fstNmElmnt.getChildNodes();
        return ((Node) fstNm.item(0)).getNodeValue();
    }

    // HTTP GET request
    private String sendGet(String ip) throws Exception {

        String url = "http://www.whoisxmlapi.com/whoisserver/WhoisService?domainName=" +
                ip +
                "&username=ragnsaman&password=crash1994";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append("\n");
        }
        in.close();

        //print result
        return response.toString();
    }

    // HTTP POST request


}