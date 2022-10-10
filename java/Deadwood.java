//import java.util.Scanner;

public class Deadwood {
	public static void main(String[] args) {
		//below is command line version
		
		XMLparse xml = new XMLparse();
		xml.parseXML(args[0], args[1]);
		Controller controller = new Controller(xml.getBoard(), xml.getCards(), Integer.parseInt(args[2]));
		
		
		/*
		XMLparse xml = new XMLparse();
		xml.parseXML("resources/xml/board.xml", "resources/xml/cards.xml");
		Controller controller = new Controller(xml.getBoard(), xml.getCards(), 2);
		*/
	}
}
