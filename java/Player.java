import java.util.Random;
public class Player {
	private int playerID, rank, shotTokens, credit, money;
	private String playerName, diceFile;
	private MovieSetSpace currentLocation;
	private JobSpace currentJob;
	private boolean hasMoved;
	private boolean hasTakenAction;
	//Constructor to set up default values
	public Player(int playerID, MovieSetSpace movieSetSpace, String diceFile) {
		this.playerID = playerID;
		currentLocation = movieSetSpace;
		this.diceFile = diceFile;
		shotTokens = 0;
		credit = 0;
		rank = 1;
		money = 0;
		hasMoved = false;
		hasTakenAction = false;
	}
	public int getPlayerID() {
		return playerID;
	}
	public int getRank(){
		return rank;
	}
	public int getShotTokens() {
		return shotTokens;
	}
	public void resetShotTokens() {
		this.shotTokens = 0;
	}
	public int getMoney() {
		return this.money;
	}
	public void changeMoney(int i) {
		this.money += i;
	}
	public int getCredit() {
		return this.credit;
	}
	public void changeCredit(int i) {
		this.credit += i;
	}
	public String getPlayerName() {
		return playerName;
	}
	public MovieSetSpace getCurrentLocation() {
		return currentLocation;
	}
	public JobSpace getCurrentJob() {
		return currentJob;
	}
	public String getDiceFile() {
		return diceFile;
	}
	public void work(JobSpace job) {
		currentJob = job;
		hasTakenAction = true;
	}
	public boolean hasMoved() {
		return hasMoved;
	}
	public boolean hasTakenAction() {
		return hasTakenAction;
	}
	public void resetActions() {
		hasMoved = false;
		hasTakenAction = false;
	}
	public void move(MovieSetSpace destination){
		currentLocation = destination;
		hasMoved = true;
	}
	
	public void changeRank(int n) {
		diceFile = diceFile.split(String.valueOf(rank))[0]+(rank + n)+diceFile.split(String.valueOf(rank))[1];
		rank += n;
	}

	public String[] act() {
		hasTakenAction = true;
		Random rand = new Random();
		int n = shotTokens;
		int dieRolled = rand.nextInt(6) + 1;
		if (dieRolled + n >= currentLocation.getMovieCard().getCardBudget()) {
			if (currentJob.getIsExtra()) {
				credit++;
				money++;
				String[] takeList = currentLocation.removeTake();
				return new String[]{String.valueOf(dieRolled), "You got $1 and 1 credit", takeList[0], takeList[1]};
			}
			else {
				credit += 2;
				String[] takeList = currentLocation.removeTake();
				return new String[]{String.valueOf(dieRolled), "You got 2 credits", takeList[0], takeList[1]};
			}
		}
		else if (currentJob.getIsExtra()){
			money++;
			return new String[]{String.valueOf(dieRolled), "You got $1"};
		}
		else {
			return new String[]{String.valueOf(dieRolled), ""};
		}
		
	}
	public void rehearse() {
		shotTokens++;
		hasTakenAction = true;
	}
	
	public void setLocation(MovieSetSpace space) {
		currentLocation = space;
	}
	
	public void upgrade(int newRank, String currencyType) {
		if ((newRank > rank) && (currencyType.equals("credit") || currencyType.equals("dollar"))) {
			if (currencyType.equals("credit") && ((newRank-1)*5) <= credit) {
				credit -= (newRank - 1)*5;
				diceFile = diceFile.split(String.valueOf(rank))[0]+(newRank)+diceFile.split(String.valueOf(rank))[1];
				rank = newRank;
				hasTakenAction = true;
			}
			else if (currencyType.equals("dollar")) {
				int cost = 0;
				switch(newRank) {
				case 2:
					cost = 4;
					break;
				case 3:
					cost = 10;
					break;
				case 4:
					cost = 18;
					break;
				case 5:
					cost = 28;
					break;
				case 6:
					cost = 40;
					break;
				}
				if (money >= cost) {
					money -= cost;
					diceFile = diceFile.split(String.valueOf(rank))[0]+(newRank)+diceFile.split(String.valueOf(rank))[1];
					rank = newRank;
					hasTakenAction = true;
				}
			}
		}
	}
}
