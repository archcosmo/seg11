package core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	public static boolean defaultXMLFileExists() {
		return new File("airportInfo.xml").isFile();
	}
	
	public static Airport readAirportInfoFromXML() throws IOException, SAXException, ParserConfigurationException {
		return readAirportInfoFromXML("airportInfo.xml");
	}
	
	//IOException - File Not Found
	//SAXException - Invalid format of input file
	public static Airport readAirportInfoFromXML(String filename) throws IOException, SAXException, ParserConfigurationException {
		//Load and parse XML file
		DocumentBuilder db;
		db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse(new File(filename));
		doc.getDocumentElement().normalize();
		
		//Check validity of the file
		Element root = doc.getDocumentElement();
		if(!root.getNodeName().equals("airport"))
			throw new SAXException("Misnamed root node.");
		
		//Build Airport Object
		Airport airport = new Airport(root.getAttribute("name"));
		
		//Build Runways
		NodeList runways = root.getElementsByTagName("runway");
		for(int i = 0; i < runways.getLength(); i++) {
			Node nRunway = runways.item(i);
			if(nRunway.getNodeType() == Node.ELEMENT_NODE) {
				Element eRunway = (Element) nRunway;
				Runway runway = new Runway(eRunway.getAttribute("name"));
				
				//Build Thresholds
				NodeList thresholds = eRunway.getElementsByTagName("threshold");
				for(int j = 0; j < thresholds.getLength(); j++) {
					Node nThreshold = thresholds.item(j);
					if(nThreshold.getNodeType() == Node.ELEMENT_NODE) {
						Element eThreshold = (Element) nThreshold;
						String thresholdDesignator = eThreshold.getAttribute("designator");
						
						//Designator is an important attribute and must be checked for file validity
						if(thresholdDesignator.isEmpty())
							throw new SAXException("Invalid file format: Threshold Designator undefined.");
						
						int tora, toda, asda, lda;
						
						tora = getAttributeValue(eThreshold.getElementsByTagName("tora"));
						if(tora == -1)
							throw new SAXException("TORA value not specified.");
						
						toda = getAttributeValue(eThreshold.getElementsByTagName("toda"));
						if(toda == -1)
							throw new SAXException("TODA value not specified.");
						
						asda = getAttributeValue(eThreshold.getElementsByTagName("asda"));
						if(asda == -1)
							throw new SAXException("ASDA value not specified.");
						
						lda = getAttributeValue(eThreshold.getElementsByTagName("lda"));
						if(lda == -1)
							throw new SAXException("value not specified.");
						
						runway.addThreshold(thresholdDesignator, tora, toda, asda, lda);
					}
					else
						throw new SAXException("Invalid file format.");
				}
				
				airport.addRunway(runway);
			}
			else
				throw new SAXException("Invalid file format.");
		}
		
		return airport;
	}
	
	/**
	 * 
	 * @param nodeList
	 * @return Value of given attribute, -1 if attribute not specified in XML document.
	 * @throws SAXException - when attribute has been defined more than once.
	 */
	private static int getAttributeValue(NodeList nodeList) throws SAXException {
		if(nodeList.getLength() == 0)
			return -1;

		Node node = nodeList.item(0);

		//Check only one of TODA, TORA, ASDA, and LDA have been defined.
		if(nodeList.getLength() > 1)
			throw new SAXException("Multiple " + node.getNodeName() + " values specified.");

		try {
			return Integer.parseInt(node.getTextContent());
		}
		catch(NumberFormatException e) {
			throw new SAXException("Invaid value for " + node.getNodeName() + " attribute.");
		}
	}
}
