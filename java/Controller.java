public class Controller implements ViewObserver{
	private View view;
	private Board model;
	private MovieSetSpace space;
	private int lastDay;
	

	public Controller(MovieSetSpace[] board, MovieCard[] cards, int numPlayers){
		model = new Board(board, cards, numPlayers);
		view = new View();
		view.register(this);
		view.setupBoard(model);
		model.initializePlayers();
		view.initializePlayerDice(model.getPlayers());
		view.run();
		view.setPlayerDie(model.getActivePlayer());
		updateInfo();
		model.startGame();
		if(numPlayers <= 3) {
			lastDay = 3;
		}
		else {
			lastDay = 4;
		}
		
	}
	

	public void update(String s){
		switch (s) {
		case "MOVE" :
			if(!model.getActivePlayer().hasMoved() && model.getActivePlayer().getCurrentJob() == null) {
				view.getMovePanel();
				view.setNeighborsButtonText(model.getActivePlayer().getCurrentLocation().getNeighbors());
			}
			else {
				view.buttonPanelDefault();
				view.getInvalidInputPanel();
			}

			
			break;
		
		case "WORK" :
			if(model.getActivePlayer().getCurrentLocation().getMovieCard() != null) {
				if(model.getActivePlayer().getCurrentLocation().getMovieCard().getJobSpaces() != null && !model.getActivePlayer().hasTakenAction() && model.getActivePlayer().getCurrentJob() == null) {
					view.getWorkPanel();
					view.setWorkButtonText(model.getActivePlayer().getCurrentLocation().getJobSpaces(), model.getActivePlayer().getCurrentLocation().getMovieCard().getJobSpaces());
				}
				else {
					view.getInvalidInputPanel();
				}
			}
			break;
			
			
		case "ACT" :
			if(model.getActivePlayer().getCurrentJob() != null && model.getActivePlayer().hasTakenAction() == false) {
				String tempBudget = String.valueOf(model.getActivePlayer().getCurrentLocation().getMovieCard().getCardBudget());
				MovieCard tempMovieCard = model.getActivePlayer().getCurrentLocation().getMovieCard();
				
				
				String[] result = model.getActivePlayer().act();
	
				if(result[1].equals("You got $1 and 1 credit") || result[1].equals("You got 2 credits")) {
					view.getActSuccessPanel(result, model.getActivePlayer(), tempBudget);
					view.addTake(result[2], result[3]);
					if(tempMovieCard.getJobSpaces() == null) {
						//Removes finished card
						view.flipCard(model.getActivePlayer().getCurrentLocation().getSetName(), "");
						view.cardDoneFrame(tempMovieCard.getDiceRolled());
						updateInfo();
						
						model.updateMovieCardsLeft();
						if(model.getMovieCardsLeft() == 1) {
							view.setEndOfDayFrame(String.valueOf(model.getDay()));
							
							if(model.getDay() == lastDay) {
								view.setGameOverFrame(model.getPlayers());
							
							}
							resetBoard();
							model.resetBoard();
							resetDieLocation();
						}
					}
				}

				else {
					view.getActFailPanel(result, model.getActivePlayer());
				}

			}
			else {
				view.getInvalidInputPanel();
			}
			model.getActivePlayer().resetShotTokens();
			updateInfo();
			break;
					
			
			
		case "REHEARSE" :
			if(!model.getActivePlayer().hasTakenAction() && model.getActivePlayer().getCurrentJob() != null && model.getActivePlayer().getShotTokens() < model.getActivePlayer().getCurrentLocation().getMovieCard().getCardBudget() - 1){
				model.getActivePlayer().rehearse();
				updateInfo();
				view.getRehearsePanel();
			}
			else {
				view.buttonPanelDefault();
				view.getInvalidInputPanel();
			}
			
			break;
		case "UPGRADE" :
			
			if(model.getActivePlayer().getCurrentLocation().getSetName().equals("office") && !model.getActivePlayer().hasTakenAction()) {
				view.getUpgradePanel();
			}
			else {
				view.getInvalidInputPanel();
			}

			break;
			
		case "Dollar" :
			view.buttonPanelDefault();
			view.getUpgradeDollarPanel();
			break;
			
		case "Credit" :
			view.buttonPanelDefault();
			view.getUpgradeCreditPanel();
			break;
			
		case "Back to main menu" :
			view.buttonPanelDefault();
			break;
			
		case "END TURN" :
			model.endTurn();
			updateInfo();
			break;
		}
	}
	
	public void updateInfo() {
		view.updateInfo(model.getActivePlayer());
	}
	
	public void resetDieLocation() {
		for(int i = 0; i < model.getPlayers().length; i++) {
			view.setPlayerDie(model.getPlayers()[i]);
		}
	}
	
	public void resetBoard() {
		view.resetBoard();
	}
	
	
	public void move(String s) {
		
		model.getActivePlayer().move(model.getMovieSetSpace(s));
		view.setPlayerDie(model.getActivePlayer());
		if(!s.equals("trailer") && !s.equals("office")) {
			if(!model.getActivePlayer().getCurrentLocation().getMovieCard().getIsFaceUp()) {
				view.flipCard(model.getActivePlayer().getCurrentLocation().getSetName(), model.getActivePlayer().getCurrentLocation().getMovieCard().flipCard());	
			}
		}
		view.buttonPanelDefault();
		

	}
	
	public void work(String s) {

		if(model.getActivePlayer().getCurrentJob() == null && !model.getActivePlayer().hasTakenAction()){
			JobSpace tempJobSpace = model.getActivePlayer().getCurrentLocation().getJobSpace(s);
			boolean isExtra;
			
			if(tempJobSpace == null) {
				tempJobSpace = model.getActivePlayer().getCurrentLocation().getMovieCard().getJobSpace(s);
				isExtra = false;
			}
			else {
				isExtra = true;
			}
			
			if(tempJobSpace != null && tempJobSpace.getPlayerWorking() == null) {
				if(tempJobSpace.getRank() <= model.getActivePlayer().getRank()) {
					tempJobSpace.setPlayerWorking(model.getActivePlayer());
					tempJobSpace.setIsExtra(isExtra);
					model.getActivePlayer().work(tempJobSpace);
					view.setPlayerDie(model.getActivePlayer());
					view.buttonPanelDefault();
					view.getGotJobPanel(model.getActivePlayer().getCurrentJob());
				}
				else {
					view.buttonPanelDefault();
					view.getInvalidInputPanel();
				}
			}
			else {
				view.buttonPanelDefault();
				view.getInvalidInputPanel();
			}
		}
	}
	
	public void upgrade(String s) {
		String currencyType = "credit";
		if(s.contains("$")) {
			currencyType = "dollar";
		}
		

		if(Integer.parseInt(String.valueOf(s.charAt(3))) < model.getActivePlayer().getRank()) {
					
			view.buttonPanelDefault();
			view.getInvalidInputPanel();
			updateInfo();
			
		}
		else {
			model.getActivePlayer().upgrade(Integer.parseInt(String.valueOf(s.charAt(3))), currencyType);
			view.buttonPanelDefault();
			view.getUpgradeSuccessPanel();
			updateInfo();
		}
	}
}