package sw;

import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class Configuration {
	protected Document dom;
	protected Element doc;
	public Configuration(String xmlFile) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(xmlFile);
			doc = dom.getDocumentElement();
		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
      } catch (SAXException se) {
         System.out.println(se.getMessage());
      } catch (IOException ioe) {
         System.err.println(ioe.getMessage());
      }
	}

	public String getPropertyValue(String path, String propertyName) {
		Element el = getElementByPath(doc,path);
		return el.getAttribute(propertyName);
	}

	public Element getElementByPath(Element parent, String path) {
		String name;
		boolean end = false;
		if(path.indexOf(".") == -1) {
			name = path;
			end = true;
		} else {
			name = path.substring(0,path.indexOf(".")-1);
			path = path.substring(path.indexOf(".")+1);
		}

		for(Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child instanceof Element && name.equals(child.getNodeName())) {
				if(end == true) {
					return (Element) child;
				} else {
					return getElementByPath((Element)child,path);
				}
			}
		}
		
		return null;
	}
}
