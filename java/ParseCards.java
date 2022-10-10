/**
 * Example Code for parsing XML file
 * part: Dr. Moushumi Sharmin
 * CS 345
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ParseCards {

	private MovieCard[] movieCards;
	// building a document from the XML file
	// returns a Document object after loading the card.xml file.
	public Document getDocFromFile(String filename) throws ParserConfigurationException {
		{

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = null;

			try {
				doc = db.parse(filename);
			} catch (Exception ex) {

				ex.printStackTrace();
			}
			return doc;
		} // exception handling
	}

	// reads data from XML file and prints data
	public void readCardData(Document d) {
		String cardName;
		int cardBudget;
		String cardImg;
		int sceneNumber = -1;
		String sceneDescription = null;
		String[] partName = {};
		int[] partRank = {};
		String[] partDescription = {};
		int[] xCoord = {};
		int[] yCoord = {};
		int[] wSize = {};
		int[] dSize = {};

		Element root = d.getDocumentElement();

		NodeList cards = root.getElementsByTagName("card");
		movieCards = new MovieCard[cards.getLength()];
		for (int i = 0; i < cards.getLength(); i++) {


			//reads data from the nodes
			Node card = cards.item(i);
			cardName = card.getAttributes().getNamedItem("name").getNodeValue();
			cardBudget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
			cardImg = card.getAttributes().getNamedItem("img").getNodeValue();

			//reads data

			NodeList children = card.getChildNodes();
			int parts = 0;
			for (int j = 1; j < children.getLength()-1; j=j+2) {

				Node sub = children.item(j);

				if ("scene".equals(sub.getNodeName())) {
					sceneNumber = Integer.parseInt(sub.getAttributes().getNamedItem("number").getNodeValue());
					sceneDescription = sub.getTextContent();
		
				}
				else if ("part".equals(sub.getNodeName())) {
					String[] tempPartName = new String[parts+1];
					int[] tempPartRank = new int[parts+1];
					String[] tempPartDescription = new String[parts+1];
					int[] tempXcoord = new int[parts + 1];
					int[] tempYcoord = new int[parts + 1];
					int[] tempWsize = new int[parts + 1];
					int[] tempDsize = new int[parts + 1];

					for (int k = 0; k < parts; k++) {
						tempPartName[k] = partName[k];
						tempPartRank[k] = partRank[k];
						tempPartDescription[k] = partDescription[k];
						tempXcoord[k] = xCoord[k];
						tempYcoord[k] = yCoord[k];
						tempDsize[k] = dSize[k];
						tempWsize[k] = wSize[k];
					}
					tempPartName[parts] = sub.getAttributes().getNamedItem("name").getNodeValue();
					tempPartRank[parts] = Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue());
					NodeList subsChildren = sub.getChildNodes();
					for (int l = 1; l < subsChildren.getLength()-1; l=l+2) {
						Node subChild = subsChildren.item(l);
						if ("area".equals(subChild.getNodeName())) {
							tempXcoord[parts] = Integer.parseInt(subChild.getAttributes().getNamedItem("x").getNodeValue());
							tempYcoord[parts] = Integer.parseInt(subChild.getAttributes().getNamedItem("y").getNodeValue());
							tempDsize[parts] = Integer.parseInt(subChild.getAttributes().getNamedItem("h").getNodeValue());
							tempWsize[parts] = Integer.parseInt(subChild.getAttributes().getNamedItem("w").getNodeValue());
						}
						else if ("line".equals(subChild.getNodeName())) {
							tempPartDescription[parts] = subChild.getTextContent();
						}
					}
					partName = tempPartName;
					partRank = tempPartRank;
					partDescription = tempPartDescription;
					xCoord = tempXcoord;
					yCoord = tempYcoord;
					dSize = tempDsize;
					wSize = tempWsize;
					parts++;

				}

			} // for childnodes

			movieCards[i] = new MovieCard(cardName, cardBudget, sceneNumber, sceneDescription, cardImg, partName, partRank, partDescription, xCoord, yCoord, wSize, dSize);

		}//for card nodes

	}// method
	public MovieCard[] getCards() {
		return movieCards;
	}

}//class
