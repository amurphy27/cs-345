import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
public class ParseBoard {
	private MovieSetSpace[] boardSpaces;
	public Document getDocFromFile(String filename) throws ParserConfigurationException {
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;

            try {
                doc = db.parse(filename);
            } catch (Exception ex) {
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
        } // exception handling
    }
	public void readBoardData(Document doc) {
		Element root = doc.getDocumentElement();
		NodeList sets = root.getElementsByTagName("set");
		boardSpaces = new MovieSetSpace[sets.getLength()+2];
		for (int i = 0; i < sets.getLength(); i++) {
			Node space = sets.item(i);
			String setName = space.getAttributes().getNamedItem("name").getNodeValue();
			String[] neighborsList = null;
			int x = 0;
			int y = 0;
			int h = 0;
			int w = 0;
			int[] takesList = null;
			int[] takesXList = null;
			int[] takesYList = null;
			int[] takesHList = null;
			int[] takesWList = null;
			int[] partsLevelList = null;
			String[] partsNameList = null;
			String[] partsLineList = null;
			int[] partsXList = null;
			int[] partsYList = null;
			int[] partsHList = null;
			int[] partsWList = null;
			NodeList children = space.getChildNodes();
			for (int j = 1; j < children.getLength()-1; j=j+2) {
				Node sub = children.item(j);
				if ("neighbors".equals(sub.getNodeName())) {
					NodeList neighbors = sub.getChildNodes();
					neighborsList = new String[(neighbors.getLength()-1)/2];
					int correctIndex = 0;
					for (int k = 1; k < neighbors.getLength()-1; k = k+2) {
						Node neighbor = neighbors.item(k);
						neighborsList[correctIndex] = neighbor.getAttributes().getNamedItem("name").getNodeValue();
						correctIndex++;
					}
				}
				else if ("area".equals(sub.getNodeName())) {
					x = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
					y = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
					h = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
					w = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
				}
				else if ("takes".equals(sub.getNodeName())) {
					NodeList takes = sub.getChildNodes();
					takesList = new int[(takes.getLength()-1)/2];
					takesXList = new int[takes.getLength()];
					takesYList = new int[takes.getLength()];
					takesHList = new int[takes.getLength()];
					takesWList = new int[takes.getLength()];
					int correctIndex = 0;
					for (int k = 1; k < takes.getLength()-1; k=k+2) {
						Node take = takes.item(k);
						takesList[correctIndex] = Integer.parseInt(take.getAttributes().getNamedItem("number").getNodeValue());
						Node area = take.getChildNodes().item(0);
						takesXList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("x").getNodeValue());
						takesYList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("y").getNodeValue());
						takesHList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("h").getNodeValue());
						takesWList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("w").getNodeValue());
						correctIndex++;
					}
				}
				else if ("parts".equals(sub.getNodeName())) {
					NodeList parts = sub.getChildNodes();
					partsNameList = new String[(parts.getLength()-1)/2];
					partsLevelList = new int[(parts.getLength()-1)/2];
					partsXList = new int[parts.getLength()];
					partsYList = new int[parts.getLength()];
					partsHList = new int[parts.getLength()];
					partsWList = new int[parts.getLength()];
					partsLineList = new String[(parts.getLength()-1)/2];
					int correctIndex = 0;
					for (int k = 1; k < parts.getLength()-1; k=k+2) {
						Node part = parts.item(k);
						partsNameList[correctIndex] = part.getAttributes().getNamedItem("name").getNodeValue();
						partsLevelList[correctIndex] = Integer.parseInt(part.getAttributes().getNamedItem("level").getNodeValue());
						NodeList partChildren = part.getChildNodes();
						for (int l = 0; l < partChildren.getLength(); l++) {
							Node partChild = partChildren.item(l);
							if ("area".equals(partChild.getNodeName())) {
								partsXList[correctIndex] = Integer.parseInt(partChild.getAttributes().getNamedItem("x").getNodeValue());
								partsYList[correctIndex] = Integer.parseInt(partChild.getAttributes().getNamedItem("y").getNodeValue());
								partsHList[correctIndex] = Integer.parseInt(partChild.getAttributes().getNamedItem("h").getNodeValue());
								partsWList[correctIndex] = Integer.parseInt(partChild.getAttributes().getNamedItem("w").getNodeValue());
							}
							else if ("line".equals(partChild.getNodeName())) {
								partsLineList[correctIndex] = partChild.getTextContent();
							}
						}
						correctIndex++;
					}
				}
			}
			boardSpaces[i] = new MovieSetSpace(setName, neighborsList, x, y, h, w, takesList, takesXList, takesYList, takesHList, takesWList, partsNameList, partsLevelList, partsLineList, partsXList, partsYList, partsHList, partsWList);
		}

		String[] trailerNeighborList = null;
		int trailerX = 0;
		int trailerY = 0;
		int trailerH = 0;
		int trailerW = 0;
		Node trailer = root.getElementsByTagName("trailer").item(0);
		NodeList trailerChildren = trailer.getChildNodes();
		for (int i = 1; i < trailerChildren.getLength()-1; i=i+2) {
			Node trailerChild = trailerChildren.item(i);
			if ("neighbors".equals(trailerChild.getNodeName())) {
				NodeList trailerNeighbors = trailerChild.getChildNodes();
				trailerNeighborList = new String[(trailerNeighbors.getLength()-1)/2];
				int correctIndex = 0;
				for (int j = 1; j < trailerNeighbors.getLength()-1; j=j+2) {
					Node trailerNeighbor = trailerNeighbors.item(j);
					trailerNeighborList[correctIndex] = trailerNeighbor.getAttributes().getNamedItem("name").getNodeValue();
					correctIndex++;
				}
			}
			else if ("area".equals(trailerChild.getNodeName())) {
				trailerX = Integer.parseInt(trailerChild.getAttributes().getNamedItem("x").getNodeValue());
				trailerY = Integer.parseInt(trailerChild.getAttributes().getNamedItem("y").getNodeValue());
				trailerH = Integer.parseInt(trailerChild.getAttributes().getNamedItem("h").getNodeValue());
				trailerW = Integer.parseInt(trailerChild.getAttributes().getNamedItem("w").getNodeValue());
			}
		}
		boardSpaces[10] = new MovieSetSpace("trailer", trailerNeighborList,trailerX, trailerY, trailerH, trailerW);

		String[] officeNeighborList = null;
		int officeX = 0;
		int officeY = 0;
		int officeH = 0;
		int officeW = 0;
		Node office = root.getElementsByTagName("office").item(0);
		NodeList children = office.getChildNodes();
		for (int i = 1; i < children.getLength()-1; i=i+2) {
			Node child = children.item(i);
			if ("neighbors".equals(child.getNodeName())) {
				NodeList neighbors = child.getChildNodes();
				officeNeighborList = new String[(neighbors.getLength()-1)/2];
				int correctIndex = 0;
				for (int j = 1; j < neighbors.getLength()-1; j=j+2) {
					Node neighbor = neighbors.item(j);
					officeNeighborList[correctIndex] = neighbor.getAttributes().getNamedItem("name").getNodeValue();
					correctIndex++;
				}
			}
			else if ("area".equals(child.getNodeName())) {
				officeX = Integer.parseInt(child.getAttributes().getNamedItem("x").getNodeValue());
				officeY = Integer.parseInt(child.getAttributes().getNamedItem("y").getNodeValue());
				officeH = Integer.parseInt(child.getAttributes().getNamedItem("h").getNodeValue());
				officeW = Integer.parseInt(child.getAttributes().getNamedItem("w").getNodeValue());
			}
			else if ("upgrade".equals(child.getNodeName())) {
				/*NodeList upgrades = child.getChildNodes();
				int correctIndex = 0;
				for (int j = 1; j < upgrades.getLength()-1; j=j+2) {
					Node upgrade = upgrades.item(j);
					int[] upgradeLevelList = new int[(upgrades.getLength()-1)/2];
					String[] upgradeCurrencyList = new String[(upgrades.getLength()-1)/2];
					int[] upgradeAmountList = new int[(upgrades.getLength()-1)/2];
					int[] upgradeXList = new int[(upgrades.getLength()-1)/2];
					int[] upgradeYList = new int[(upgrades.getLength()-1)/2];
					int[] upgradeHList = new int[(upgrades.getLength()-1)/2];
					int[] upgradeWList = new int[(upgrades.getLength()-1)/2];
					upgradeLevelList[correctIndex] = Integer.parseInt(upgrade.getAttributes().getNamedItem("level").getNodeValue());
					upgradeCurrencyList[correctIndex] = upgrade.getAttributes().getNamedItem("currency").getNodeValue();
					upgradeAmountList[correctIndex] = Integer.parseInt(upgrade.getAttributes().getNamedItem("amt").getNodeValue());
					Node area = upgrade.getChildNodes().item(0);
					upgradeXList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("x").getNodeValue());
					upgradeYList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("y").getNodeValue());
					upgradeHList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("h").getNodeValue());
					upgradeWList[correctIndex] = Integer.parseInt(area.getAttributes().getNamedItem("w").getNodeValue());
				}*/
			}
		}
		boardSpaces[11] = new MovieSetSpace("office", officeNeighborList, officeX, officeY, officeH, officeW);
	}
	public MovieSetSpace[] getBoard() {
		return boardSpaces;
	}
}