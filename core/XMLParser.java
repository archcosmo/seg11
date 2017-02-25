package core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLParser {

	public boolean defaultXMLFileExists() {
		return new File("airportInfo.xml").isFile();
	}
	
	public void readAirportInfoFromXML() throws IOException, SAXException, ParserConfigurationException {
		readAirportInfoFromXML("airportInfo.xml");
	}
	
	//IOException - File Not Found
	//SAXException - Invalid format of input file
	public void readAirportInfoFromXML(String filename) throws IOException, SAXException, ParserConfigurationException {
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
		
	}
}
