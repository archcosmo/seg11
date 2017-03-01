package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	
	public static boolean defaultXMLFileExists() {
		return new File("airportInfo.xml").isFile();
	}
	
	private static List<String> getAirportFileNames() {
		List<String> fileNames = new ArrayList<String>();
		
		File airportDir = new File("xml/airports/");
		
		if(!airportDir.exists())
			airportDir.mkdirs();
		
		//Get all files in default directory
		File[] airportDirFiles = airportDir.listFiles();
		for(File f : airportDirFiles) {
			String[] fNameSplit = f.getName().split("\\.");
			
			//Get XML Files
			if(fNameSplit[fNameSplit.length -1].equals("xml")) {
				fileNames.add("xml/airports/" + f.getName());
			}
				
		}
		
		return fileNames;
	}
	
	public static List<Airport> readAirportInfoFromXML() throws IOException, SAXException, ParserConfigurationException {
		List<String> fileNames = getAirportFileNames();
		List<Airport> airports = new ArrayList<Airport>();
		for(String name : fileNames) {
			airports.add(readAirportInfoFromXML(name));
		}
		return airports;
	}
	
	//IOException - File Not Found
	//SAXException - Invalid format of input file
	public static Airport readAirportInfoFromXML(String filename) throws IOException, SAXException, ParserConfigurationException {
		//Load and parse XML file
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse(new File(filename));
		doc.getDocumentElement().normalize();
		
		//Check validity of the file
		return unmarshallAirport(doc.getDocumentElement());
		
	}

	public static boolean saveAirportInfoToXML(Airport airport) throws IOException {
		String fileName = airport.name.toLowerCase().replaceAll("\\s+", "");
		return saveAirportInfoToXML("xml/airports/" + fileName + ".xml", airport);
	}
	
	public static boolean saveAirportInfoToXML(String filename, Airport airport) throws IOException{
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = db.newDocument();
			doc.appendChild(marshallAirport(airport, doc));
			
			DOMSource xmlSource = new DOMSource(doc);
			
			FileWriter fw = new FileWriter(new File(filename));
			StreamResult outputTarget = new StreamResult(fw);
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(xmlSource, outputTarget);
			
			return true;
			
		} catch (ParserConfigurationException | TransformerException e) {
			return false;
		}
	}
	
	private static Airport unmarshallAirport(Element eAirport) throws SAXException {
		if(!eAirport.getNodeName().equals("airport"))
			throw new SAXException("Misnamed root node.");
		
		//Build Airport Object
		Airport airport = new Airport(eAirport.getAttribute("name"));
		
		//Build Runways
		NodeList runways = eAirport.getElementsByTagName("runway");
		for(int i = 0; i < runways.getLength(); i++)
			airport.addRunway(unmarshallRunway(runways.item(i)));
		
		return airport;
	}
	
	private static Node marshallAirport(Airport airport, Document doc) {
		Element nAirport = doc.createElement("airport");
		nAirport.setAttribute("name", airport.name);

		for(Runway runway : airport.runways)
			nAirport.appendChild(marshallRunway(runway, doc));
		
		return nAirport;
	}
	
	private static Runway unmarshallRunway(Node nRunway) throws SAXException {
		if(nRunway.getNodeType() == Node.ELEMENT_NODE) {
			Element eRunway = (Element) nRunway;
			Runway runway = new Runway(eRunway.getAttribute("name"), -1, -1);
			
			//Build Thresholds
			NodeList thresholds = eRunway.getElementsByTagName("threshold");
			for(int j = 0; j < thresholds.getLength(); j++)
				runway.addLogicalRunway(unmarshallLogicalRunway(thresholds.item(j)));
			
			return runway;
		}
		else
			throw new SAXException("Invalid file format.");
	}
	
	private static Node marshallRunway(Runway runway, Document doc) {
		Element nRunway = doc.createElement("runway");
		nRunway.setAttribute("name", runway.name);
		
		for(LogicalRunway logicalRunway: runway.logicalRunways)
			nRunway.appendChild(marshallLogicalRunway(logicalRunway, doc));
		
		return nRunway;
	}
	
	private static LogicalRunway unmarshallLogicalRunway(Node nLogicalRunway) throws SAXException {
		if(nLogicalRunway.getNodeType() == Node.ELEMENT_NODE) {
			Element eLogicalRunway = (Element) nLogicalRunway;
			String logicalRunwayDesignator = eLogicalRunway.getAttribute("designator");
			
			//Designator is an important attribute and must be checked for file validity
			if(logicalRunwayDesignator.isEmpty())
				throw new SAXException("Invalid file format: Threshold Designator undefined.");

			Runway runway;
			int tora, toda, asda, lda;

			//TODO: get Runway and throw SAXException (might have to pass runway into method?)
			runway = getAttributeValue(eLogicalRunway.getElementsByTagName("runway"));
			
			tora = getAttributeValue(eLogicalRunway.getElementsByTagName("tora"));
			if(tora == -1)
				throw new SAXException("TORA value not specified.");
			
			toda = getAttributeValue(eLogicalRunway.getElementsByTagName("toda"));
			if(toda == -1)
				throw new SAXException("TODA value not specified.");
			
			asda = getAttributeValue(eLogicalRunway.getElementsByTagName("asda"));
			if(asda == -1)
				throw new SAXException("ASDA value not specified.");
			
			lda = getAttributeValue(eLogicalRunway.getElementsByTagName("lda"));
			if(lda == -1)
				throw new SAXException("value not specified.");
			
			return new LogicalRunway(logicalRunwayDesignator, runway, tora, toda, asda, lda);
		}
		else
			throw new SAXException("Invalid file format.");
	}
	
	private static Node marshallLogicalRunway(LogicalRunway logicalRunway, Document doc) {
		Element nThreshold = doc.createElement("threshold");
		nThreshold.setAttribute("designator", logicalRunway.designator);
		
		Element tora = doc.createElement("tora");
		tora.setTextContent("" + logicalRunway.tora);
		nThreshold.appendChild(tora);
		
		Element toda = doc.createElement("toda");
		toda.setTextContent("" + logicalRunway.toda);
		nThreshold.appendChild(toda);
		
		Element asda = doc.createElement("asda");
		asda.setTextContent("" + logicalRunway.asda);
		nThreshold.appendChild(asda);
		
		Element lda = doc.createElement("lda");
		lda.setTextContent("" + logicalRunway.lda);
		nThreshold.appendChild(lda);
		
		return nThreshold;
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
