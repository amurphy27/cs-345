import java.util.Random;
public class Board {
	//boardState[10] is trailer and boardSpace[11] is office
	private MovieSetSpace[] boardState = new MovieSetSpace[12];
	private int day, movieCardsLeft, activePlayer, numPlayers;
	private MovieCard[] movieCards;
	private Player[] players;


	//constructor
	public Board(MovieSetSpace[] board, MovieCard[] cards, int numPlayers) {
		this.numPlayers = numPlayers;
		day = 0;
		boardState = board;
		movieCards = new MovieCard[cards.length];
		movieCards = shuffleCards(cards);

		resetBoard();
	}
	//puts movie cards onto the movie set spaces
	public void resetBoard() {
		//i<10 because there are 10 movie set spaces and the list has trailer
		//and office at the end to the list's length wouldn't work
		for (int i = 0; i < 10; i++) {
			boardState[i].setMovieCard(movieCards[(day*10)+i]);
			boardState[i].resetTakes();
			if(day != 0) {
				for(int j = 0; j < players.length; j++) {
					players[j].setLocation(boardState[10]);
				}
			}
		}
		movieCardsLeft = 10;
		day++;
	}


	//randomizes the array of movie cards as if they were shuffled
	private MovieCard[] shuffleCards(MovieCard[] cards) {
		Random rand = new Random();
		for (int i = 0; i < cards.length; i++) {
			int randomIndexToSwap = rand.nextInt(cards.length);
			MovieCard temp = cards[randomIndexToSwap];
			cards[randomIndexToSwap] = cards[i];
			cards[i] = temp;

		}
		return cards;
	}
	//added getter for active player and all players
	public Player getActivePlayer() {
		return players[activePlayer];
	}

	public int getDay() {
		return day;
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getMovieCardsLeft() {
		return movieCardsLeft;
	}
	//main game loop
	public void startGame() {
		//active player is the index in the players list for whoever's turn it is
		activePlayer = 0;
	}


	public void updateMovieCardsLeft() {
		movieCardsLeft = 0;
		for (int i = 0; i < 10; i++) {
			if (boardState[i].getMovieCard().getJobSpaces() != null) {
				movieCardsLeft++;
			}
		}
	}

	//welcomes players and asks for their names
	public void initializePlayers() {
		String[] colors = {"b","o","g","p","r","c","v","y"};
		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(i, boardState[10], "../resources/img/dice_"+colors[i]+"1.png");
			switch(numPlayers) {
			case 5 :
				players[i].changeCredit(2);
				break;
			case 6 :
				players[i].changeCredit(4);
				break;
			case 7 :
				players[i].changeRank(1);
				break;
			case 8 :
				players[i].changeRank(1);
				break;
			}

		}
	}
	//gets a movie set space based on the name of the set space
	public MovieSetSpace getMovieSetSpace(String setName) {
		int indexOfSetSpace = -1;
		for (int i = 0; i < boardState.length; i++) {
			if (setName.equals(boardState[i].getSetName())) {
				indexOfSetSpace = i;
			}
		}
		if (indexOfSetSpace != -1) {
			return boardState[indexOfSetSpace];
		}
		else {
			return null;
		}
	}

	public String[] getCurrentNeighbors(Player current) {
		String[] s = current.getCurrentLocation().getNeighbors();
		return s;
	}

	public void endTurn() {
		players[activePlayer].resetActions();
		activePlayer++;
		if (activePlayer == players.length) {
			activePlayer = 0;
		}
	}
}
