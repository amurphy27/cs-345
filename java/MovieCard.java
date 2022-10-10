import java.util.Arrays;
import java.util.Random;
public class MovieCard {
	private String cardName;
	private int cardBudget, sceneNumber;
	private boolean isFaceUp;
	private JobSpace[] mainJobSpaces;
	private String cardImg;
	private int[] diceRolled;

	public MovieCard (String cardName, int budget, int sceneNumber, String sceneDescription, String cardImg, String[] partName, int[] partRank, String[] partDescription, int[] partsX, int[] partsY, int[] partsW, int[]partsH){
		this.cardName = cardName;
		this.cardBudget = budget;
		this.sceneNumber = sceneNumber;
		this.cardImg = "../resources/img/" + cardImg;
		isFaceUp = false;
		mainJobSpaces = new JobSpace[partName.length];
		for (int i = 0; i < partName.length; i++) {
			mainJobSpaces[i] = new JobSpace(partName[i], partRank[i], partDescription[i], partsX[i], partsY[i], partsH[i], partsW[i]);
		}
	}
	public String getCardName() {
		return cardName;
	}
	public int getCardBudget() {
		return cardBudget;
	}
	public int getSceneNumber() {
		return sceneNumber;
	}
	public String flipCard() {
		this.isFaceUp = true;
		return cardImg;
	}
	public boolean getIsFaceUp() {
		return isFaceUp;
	}
	public JobSpace[] getJobSpaces() {
		return mainJobSpaces;
	}
	public JobSpace getJobSpace(String partName) {
		JobSpace correctSpace = null;
		for (int i = 0; i < mainJobSpaces.length; i++) {
			if (partName.equals(mainJobSpaces[i].getPartName())) {
				correctSpace = mainJobSpaces[i];
			}
		}
		return correctSpace;
	}
	public void payActors() {
		Random rand = new Random();
		int[] tempDiceRolled = new int[cardBudget];
		Player[] playersOnCard = new Player[mainJobSpaces.length];
		for (int i = 0; i < mainJobSpaces.length; i++) {
			playersOnCard[i] = mainJobSpaces[i].getPlayerWorking();
		}
		for (int j = 0; j < cardBudget; j++) {
			tempDiceRolled[j] = rand.nextInt(6)+1;
		}
		Arrays.sort(tempDiceRolled);
		int playerIndex = playersOnCard.length-1;
		for (int k = mainJobSpaces.length-1; k>-1; k--) {
			if (playersOnCard[playerIndex] != null) {
				playersOnCard[playerIndex].changeMoney(tempDiceRolled[k]);
			}
			playerIndex--;
			if (playerIndex == -1) {
				playerIndex = playersOnCard.length - 1;
			}
		}
		for (int l = 0; l < playersOnCard.length; l++) {
			if (playersOnCard[l] != null) {
				playersOnCard[l].work(null);
				playersOnCard[l].resetShotTokens();
			}

		}
		mainJobSpaces = null;
		this.diceRolled = tempDiceRolled;
	}

	public int[] getDiceRolled() {
		return diceRolled;
	}


}
