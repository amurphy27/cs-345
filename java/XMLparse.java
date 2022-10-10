
import org.w3c.dom.Document;
//from example code for parsing xml file
public class XMLparse {
	private ParseBoard boardParsing = new ParseBoard();
    private ParseCards cardParsing = new ParseCards();
    public void parseXML(String boardFile, String cardFile) {
        Document doc = null;
        try {
        	doc = boardParsing.getDocFromFile(boardFile);
            boardParsing.readBoardData(doc);
            doc = cardParsing.getDocFromFile(cardFile);
            cardParsing.readCardData(doc);
        } catch (NullPointerException e) {
            System.out.println("Error = " + e);
            return;
        } catch (Exception e) {
            System.out.println("Error = " + e);
            return;
        }
    }
    public MovieSetSpace[] getBoard() {
    	return boardParsing.getBoard();
    }
    public MovieCard[] getCards() {
    	return cardParsing.getCards();
    }
}