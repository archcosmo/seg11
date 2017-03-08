package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeNotFoundException;
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
		try {
			if(nRunway.getNodeType() == Node.ELEMENT_NODE) {
				Element eRunway = (Element) nRunway;
				Runway runway = new Runway(Integer.parseInt(eRunway.getAttribute("resa")), Integer.parseInt(eRunway.getAttribute("blastAllowance")), Integer.parseInt(eRunway.getAttribute("stripEnd")));

				//Build Thresholds
				NodeList thresholds = eRunway.getElementsByTagName("threshold");
				runway.shortAngleLogicalRunway = unmarshallLogicalRunway(runway, thresholds.item(0));
				runway.shortAngleLogicalRunway = unmarshallLogicalRunway(runway, thresholds.item(1));
				return runway;
			}
			else
				throw new SAXException("Invalid file format.");
		} catch(NumberFormatException e) {
			throw new SAXException("Invalid file format.");
		}
	}
	
	private static Node marshallRunway(Runway runway, Document doc) {
		Element nRunway = doc.createElement("runway");
		nRunway.setAttribute("name", runway.designator);
		nRunway.setAttribute("resa", "" + runway.RESA);
		nRunway.setAttribute("blastAllowance", "" + runway.blastAllowance);
		nRunway.setAttribute("stripEnd", "" + runway.stripEnd);

		nRunway.appendChild(marshallLogicalRunway(runway.shortAngleLogicalRunway, doc));
		nRunway.appendChild(marshallLogicalRunway(runway.longAngleLogicalRunway, doc));
		
		return nRunway;
	}
	
	private static LogicalRunway unmarshallLogicalRunway(Runway parentRunway, Node nLogicalRunway) throws SAXException {
		if(nLogicalRunway.getNodeType() == Node.ELEMENT_NODE) {
			Element eLogicalRunway = (Element) nLogicalRunway;
			String logicalRunwayDesignator = eLogicalRunway.getAttribute("designator");
			
			//Designator is an important attribute and must be checked for file validity
			if(logicalRunwayDesignator.isEmpty())
				throw new SAXException("Invalid file format: Threshold Designator undefined.");

			int tora, toda, asda, lda, stopwayLength;
			
			try {
				tora = getAttributeValue(eLogicalRunway.getElementsByTagName("tora"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("TORA value not specified.");
			}
			
			try {
				toda = getAttributeValue(eLogicalRunway.getElementsByTagName("toda"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("TODA value not specified.");
			}
			
			try {
				asda = getAttributeValue(eLogicalRunway.getElementsByTagName("asda"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("ASDA value not specified.");
			}
			
			try {
				lda = getAttributeValue(eLogicalRunway.getElementsByTagName("lda"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("LDA value not specified.");
			}
			
			try {
				stopwayLength = getAttributeValue(eLogicalRunway.getElementsByTagName("stopwayLength"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("Stopway Length value not specified.");
			}
			
			return new LogicalRunway(logicalRunwayDesignator, parentRunway, tora, toda, asda, lda, stopwayLength);
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
		
		Element stopwayLength = doc.createElement("stopwayLength");
		stopwayLength.setTextContent("" + logicalRunway.stopwayLength);
		nThreshold.appendChild(stopwayLength);
		
		return nThreshold;
	}
	
	public static boolean saveObstacleInfoToXML(List<Obstacle> obstacles) throws IOException {
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = db.newDocument();
			Element eObstacleList = doc.createElement("obstaclelist");
			doc.appendChild(eObstacleList);
			
			for(Obstacle obstacle: obstacles)
				eObstacleList.appendChild(marshallObstacle(obstacle, doc));
			
			DOMSource xmlSource = new DOMSource(doc);
			
			FileWriter fw = new FileWriter(new File("xml/obstaclelist.xml"));
			StreamResult outputTarget = new StreamResult(fw);
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(xmlSource, outputTarget);
			
			return true;
			
		} catch (ParserConfigurationException | TransformerException e) {
			return false;
		}
	}
	
	private static Node marshallObstacle(Obstacle obstacle, Document doc) {
		Element eObstacle = doc.createElement("obstacle");
		eObstacle.setAttribute("name", obstacle.name);
		
		Element height = doc.createElement("height");
		height.setTextContent("" + obstacle.height);
		eObstacle.appendChild(height);
		
		Element width = doc.createElement("width");
		width.setTextContent("" + obstacle.width);
		eObstacle.appendChild(width);
		
		Element length = doc.createElement("length");
		length.setTextContent("" + obstacle.length);
		eObstacle.appendChild(length);
		
		return eObstacle;
	}
	
	public static List<Obstacle> readObstacleInfoFromXML() throws IOException, SAXException, ParserConfigurationException {
		//Load and parse XML file
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse(new File("xml/obstaclelist.xml"));
		doc.getDocumentElement().normalize();

		Element eObstacleList = doc.getDocumentElement();
		
		//Check validity of the file
		if(!eObstacleList.getNodeName().equals("obstaclelist"))
			throw new SAXException("Misnamed root node, expect obstaclelist.");
		
		List<Obstacle> obstacleList = new ArrayList<Obstacle>();
		NodeList obstacles = eObstacleList.getElementsByTagName("obstacle");
		
		for(int i = 0; i < obstacles.getLength(); i++)
			obstacleList.add(unmarshallObstacle(obstacles.item(i)));
		
		return obstacleList;
	}
	
	private static Obstacle unmarshallObstacle(Node nObstacle) throws SAXException {
		if(nObstacle.getNodeType() == Node.ELEMENT_NODE) {
			Element eObstacle = (Element) nObstacle;
			String obstacleName = eObstacle.getAttribute("name");
			
			//Designator is an important attribute and must be checked for file validity
			if(obstacleName.isEmpty())
				throw new SAXException("Invalid file format: Obstacle name undefined.");
			
			int width, length, height;
			
			try {
				width = getAttributeValue(eObstacle.getElementsByTagName("width"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("Width value not specified.");
			}
			
			try {
				length = getAttributeValue(eObstacle.getElementsByTagName("length"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("Length value not specified.");
			}
			
			try {
				height = getAttributeValue(eObstacle.getElementsByTagName("height"));
			} catch(AttributeNotFoundException e) {
				throw new SAXException("Height value not specified.");
			}
			
			return new Obstacle(obstacleName, width, length, height);
		}
		else
			throw new SAXException("Invalid file format.");
	}
	
	/**
	 * 
	 * @param nodeList
	 * @return Value of given attribute, -1 if attribute not specified in XML document.
	 * @throws SAXException - when attribute has been defined more than once.
	 * @throws AttributeNotFoundException - when attribute not found.
	 */
	private static int getAttributeValue(NodeList nodeList) throws SAXException, AttributeNotFoundException {
		if(nodeList.getLength() == 0)
			throw new AttributeNotFoundException();

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
