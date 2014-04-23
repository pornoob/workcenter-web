package workcenter.util.pojo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author colivares
 */
public class StaticContext {

    public static String obtenerIPServidor() {
        try {
            InetAddress[] ip = InetAddress.getAllByName("servidor");
            return ip[0].getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(StaticContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Integer obtenerPuertoServidor() {
        Integer port = null;
        try {
            String path = System.getProperty("catalina.base") + "/conf/server.xml";
            File archivo = new File(path);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("/Server/Service[@name='Catalina']/Connector[count(@scheme)=0]/@port[1]");
            String result = (String) expr.evaluate(doc, XPathConstants.STRING);
            port = result != null && result.length() > 0 ? Integer.valueOf(result) : null;
        } catch (XPathExpressionException ex) {
            Logger.getLogger(StaticContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(StaticContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(StaticContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StaticContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return port;
    }
    
    public static String obtenerUrlServidor() {
        return "http://" + obtenerIPServidor() + ":" + obtenerPuertoServidor();
    }
}
