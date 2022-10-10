import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View{
	private ViewObserver ob;
	private JFrame frame;
	private JPanel eastPanel;
	private JPanel infoPanel;
	private JPanel buttonPanel;
	private JLabel resultDie;
	private JPanel invalidInputPanel = new JPanel();
	private JLayeredPane boardPanel = new JLayeredPane();
	private JLabel takesLabel = new JLabel();;

	private JLabel youRolled = new JLabel("You rolled");
	private JLabel successMsg = new JLabel("Success!");
	private JLabel failMsg = new JLabel("Failed...");
	private JButton back = new JButton("Back to main menu");


	private JLabel invalidInputMsg = new JLabel("Invalid input...");


	private JLabel player;
	private JLabel icon;
	private JLabel dollars;
	private JLabel credits;
	private JLabel tokens;

	private JButton moveButton;
	private JButton workButton;
	private JButton actButton;
	private JButton rehearseButton;
	private JButton upgradeButton;
	private JButton endButton;


	//Panels of buttonPannel
	private JPanel movePanel;
	private JButton neighbor1 = new JButton("-");
	private JButton neighbor2 = new JButton("-");
	private JButton neighbor3 = new JButton("-");
	private JButton neighbor4 = new JButton("-");

	private JPanel workPanel;
	private JPanel mainRolePanel;
	private JPanel gotJobPanel;
	private JLabel hiredMsg = new JLabel("Hired!");
	private JLabel roleName;
	private JLabel roleDescription;

	private JButton extra1 = new JButton("-");
	private JButton extra2 = new JButton("-");
	private JButton extra3 = new JButton("-");
	private JButton extra4 = new JButton("-");
	private JButton role1 = new JButton("-");
	private JButton role2 = new JButton("-");
	private JButton role3 = new JButton("-");



	private JPanel actSuccessPanel;
	private JLabel partLevelLabel;
	private JLabel totalSumLabel;
	private JLabel totalEarned;
	private JPanel actFailPanel;


	private JPanel rehearsePanel;
	private JLabel tokenEarned;

	private JPanel upgradePanel;
	private JButton dollar;
	private JButton credit;


	private JButton dLv2 = new JButton("lv.2 => $4");
	private JButton dLv3 = new JButton("lv.3 => $10");
	private JButton dLv4 = new JButton("lv.4 => $18");
	private JButton dLv5 = new JButton("lv.5 => $28");
	private JButton dLv6 = new JButton("lv.6 => $40");
	private JPanel upgradeDollarPanel;
	private JLabel upgradeMsg = new JLabel("                                      Upgrade");

	private JPanel upgradeCreditPanel;
	private JButton cLv2 = new JButton("lv.2 => 5 credits");
	private JButton cLv3 = new JButton("lv.3 => 10 credits");
	private JButton cLv4 = new JButton("lv.4 => 15 credits");
	private JButton cLv5 = new JButton("lv.5 => 20 credits");
	private JButton cLv6 = new JButton("lv.6 => 25 credits");

	private JPanel upgradeSuccessPanel;

	private JLabel trainStation;
	private JLabel jail;
	private JLabel mainStreet;
	private JLabel generalStore;
	private JLabel saloon;
	private JLabel ranch;
	private JLabel bank;
	private JLabel hotel;
	private JLabel secretHideout;
	private JLabel church;
	private JLabel[] players;






	public void register(ViewObserver ob){
		this.ob = ob;
	}

	public void run(){

		frame = new JFrame();
		frame.setSize(new Dimension(1500, 940));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardPanel.setLayout(null);
		boardPanel.setSize(new Dimension(1200, 900));
		JLabel boardImage = new JLabel(new ImageIcon("../resources/img/board.png"));
		boardImage.setLocation(0, 0);
		boardImage.setSize(1200,900);
		boardPanel.add(boardImage, 0);
		boardPanel.add(trainStation, 0);
		boardPanel.add(jail, 0);
		boardPanel.add(mainStreet, 0);
		boardPanel.add(generalStore, 0);
		boardPanel.add(saloon, 0);
		boardPanel.add(ranch, 0);
		boardPanel.add(bank, 0);
		boardPanel.add(hotel, 0);
		boardPanel.add(secretHideout, 0);
		boardPanel.add(church, 0);
		for (int i = 0; i < players.length; i++) {
			boardPanel.add(players[i], 0);
		}
		frame.getContentPane().add(BorderLayout.CENTER, boardPanel);


		takesLabel.setSize(new Dimension(1200, 900));
		takesLabel.setLayout(null);

		//InfoPanel
		eastPanel = new JPanel(new GridLayout(2 , 1));
		infoPanel = new JPanel(new GridLayout(5, 1));
		infoPanel.setPreferredSize(new Dimension(286, 500));
		infoPanel.setBackground(new Color(245, 238, 211));
		Border border = BorderFactory.createMatteBorder(5, 0, 5, 0, new Color(128, 94, 28));
		infoPanel.setBorder(border);

		player = new JLabel();
		icon = new JLabel();
		dollars = new JLabel();
		credits = new JLabel();
		tokens = new JLabel();

		infoPanel.add(player);
		infoPanel.add(icon);
		infoPanel.add(dollars);
		infoPanel.add(credits);
		infoPanel.add(tokens);
		eastPanel.add(infoPanel);

		//buttonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(6, 1));
		buttonPanel.setBackground(new Color(245, 238, 211));

		buttonPanel.setPreferredSize(new Dimension(286, 400));
		eastPanel.add(buttonPanel);
		frame.getContentPane().add(BorderLayout.EAST, eastPanel);

		//Add buttons to buttonPanel
		moveButton = new JButton("MOVE");
		moveButton.addActionListener(new clickButtonListener());
		buttonPanel.add(moveButton);

		workButton = new JButton("WORK");
		workButton.addActionListener(new clickButtonListener());
		buttonPanel.add(workButton);

		actButton = new JButton("ACT");
		actButton.addActionListener(new clickButtonListener());
		buttonPanel.add(actButton);

		rehearseButton = new JButton("REHEARSE");
		rehearseButton.addActionListener(new clickButtonListener());
		buttonPanel.add(rehearseButton);

		upgradeButton = new JButton("UPGRADE");
		upgradeButton.addActionListener(new clickButtonListener());
		buttonPanel.add(upgradeButton);

		endButton = new JButton("END TURN");
		endButton.addActionListener(new clickButtonListener());
		buttonPanel.add(endButton);


		frame.setVisible(true);
	}


	public void updateInfo(Player p) {
        player.setText("Player "+(p.getPlayerID()+1));
        player.setHorizontalAlignment(SwingConstants.CENTER);
        player.setFont(new Font("TimeRoman", Font.BOLD, 30));
        icon.setIcon(new ImageIcon(p.getDiceFile()));

        players[p.getPlayerID()].setIcon(new ImageIcon(p.getDiceFile()));

        icon.setSize(new Dimension(40, 40));
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        dollars.setText("Dollars   :   "+p.getMoney());
        dollars.setHorizontalAlignment(SwingConstants.CENTER);
        dollars.setFont(new Font("TimeRoman", Font.PLAIN, 20));
        credits.setText("Credits   :   "+p.getCredit());
        credits.setHorizontalAlignment(SwingConstants.CENTER);
        credits.setFont(new Font("TimeRoman", Font.PLAIN, 20));
        tokens.setText("Tokens   :   "+p.getShotTokens());
        tokens.setHorizontalAlignment(SwingConstants.CENTER);
        tokens.setFont(new Font("TimeRoman", Font.PLAIN, 20));
    }

	public void setupBoard(Board b) {
		MovieSetSpace temp;
		temp = b.getMovieSetSpace("Train Station");
		trainStation = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		trainStation.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		trainStation.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Jail");
		jail = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		jail.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		jail.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Main Street");
		mainStreet = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		mainStreet.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		mainStreet.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("General Store");
		generalStore = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		generalStore.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		generalStore.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Saloon");
		saloon = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		saloon.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		saloon.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Ranch");
		ranch = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		ranch.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		ranch.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Bank");
		bank = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		bank.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		bank.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Hotel");
		hotel = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		hotel.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		hotel.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Secret Hideout");
		secretHideout = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		secretHideout.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		secretHideout.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));

		temp = b.getMovieSetSpace("Church");
		church = new JLabel(new ImageIcon("../resources/img/cardback.png"));
		church.setLocation(temp.getLocation()[0], temp.getLocation()[1]);
		church.setSize(new Dimension(temp.getDimensions()[0], temp.getDimensions()[1]));
	}

	public void flipCard(String card, String image) {
		JLabel[] list = new JLabel[] {trainStation, jail, mainStreet, generalStore, saloon, ranch, bank, hotel, secretHideout, church};
		String[] strings = new String[] {"Train Station", "Jail", "Main Street", "General Store", "Saloon", "Ranch", "Bank", "Hotel", "Secret Hideout", "Church"};
		for (int i = 0; i < list.length; i++) {
			if (strings[i].equals(card)) {
				list[i].setIcon(new ImageIcon(image));
			}
		}
	}

	public void initializePlayerDice(Player[] players) {
		this.players = new JLabel[players.length];
		for (int i = 0; i < players.length; i++) {
			this.players[i] = new JLabel(new ImageIcon(players[i].getDiceFile()));
			this.players[i].setLocation(players[i].getCurrentLocation().getLocation()[0] + i * 20,players[i].getCurrentLocation().getLocation()[1]);
			this.players[i].setSize(new Dimension(40, 40));
		}
	}
	public void setPlayerDie(Player p) {
		if(p.getCurrentJob() == null) {
			players[p.getPlayerID()].setLocation(p.getCurrentLocation().getLocation()[0]+(p.getPlayerID()*20), p.getCurrentLocation().getLocation()[1]);
		}
		else if(p.getCurrentJob().getIsExtra()){
			players[p.getPlayerID()].setLocation(p.getCurrentJob().getLocation()[0], p.getCurrentJob().getLocation()[1]);
		}
		else {
			players[p.getPlayerID()].setLocation(p.getCurrentJob().getLocation()[0] + p.getCurrentLocation().getLocation()[0], p.getCurrentJob().getLocation()[1] + p.getCurrentLocation().getLocation()[1]);
		}
	}

	public void setPlayerJob(int x, int y) {

	}

	public void addTake(String x, String y) {
		JLabel shot = new JLabel(new ImageIcon("../resources/img/shot.png"));
		shot.setSize(new Dimension(42, 42));
		shot.setLocation(Integer.parseInt(x), Integer.parseInt(y));
		takesLabel.add(shot);
		boardPanel.add(takesLabel, 0);
	}



	//When button in buttonPanel is clicked, change the panel
	public void replacePanel(JPanel basePanel, JPanel original, JPanel target) {
		basePanel.remove(original);
		basePanel.add(target);
		target.add(back);
		frame.getContentPane().add(BorderLayout.EAST, basePanel);
		frame.validate();
		frame.repaint();
	}



	public void updateView(){

	}


	public void updateButton() {


	}

	public void buttonPanelDefault() {
		eastPanel.removeAll();
		eastPanel.add(infoPanel);
		eastPanel.add(buttonPanel);
		frame.getContentPane().add(BorderLayout.EAST, eastPanel);

	}

	public void getMovePanel() {
		//move panel
		movePanel = new JPanel();
		movePanel.setLayout(new GridLayout(5, 1));
		movePanel.setPreferredSize(new Dimension(286, 400));

		movePanel.add(neighbor1);
		neighbor1.addActionListener(new clickMoveButtonListener());
		movePanel.add(neighbor2);
		neighbor2.addActionListener(new clickMoveButtonListener());
		movePanel.add(neighbor3);
		neighbor3.addActionListener(new clickMoveButtonListener());
		movePanel.add(neighbor4);
		neighbor4.addActionListener(new clickMoveButtonListener());

		back = new JButton("Back to main menu");
		back.addActionListener(new clickButtonListener());


		replacePanel(eastPanel, buttonPanel, movePanel);
	}

	public void getWorkPanel() {
		//work panel
		workPanel = new JPanel();
		workPanel.setLayout(new GridLayout(3, 1));
		workPanel.setPreferredSize(new Dimension(286, 400));

		//extraPanel with part names
		JPanel extraPanel = new JPanel();
		extraPanel.setLayout(new GridLayout(4, 1));
		extraPanel.setPreferredSize(new Dimension(286, 200));
		//Add buttons to extraPanel
		this.extra1 = new JButton("-");
		extraPanel.add(extra1);
		extra1.addActionListener(new clickWorkButtonListener());
		this.extra2 = new JButton("-");
		extraPanel.add(extra2);
		extra2.addActionListener(new clickWorkButtonListener());
		this.extra3 = new JButton("-");
		extraPanel.add(extra3);
		extra3.addActionListener(new clickWorkButtonListener());
		this.extra4 = new JButton("-");
		extraPanel.add(extra4);
		extra4.addActionListener(new clickWorkButtonListener());

		workPanel.add(extraPanel);

		//mainRolePanel
		mainRolePanel = new JPanel();
		mainRolePanel.setLayout(new GridLayout(3, 1));
		mainRolePanel.setPreferredSize(new Dimension(286, 200));
		//Add buttons to mainRolePanel
		this.role1 = new JButton("-");
		mainRolePanel.add(role1);
		role1.addActionListener(new clickWorkButtonListener());
		this.role2 = new JButton("-");
		mainRolePanel.add(role2);
		role2.addActionListener(new clickWorkButtonListener());
		this.role3 = new JButton("-");
		mainRolePanel.add(role3);
		role3.addActionListener(new clickWorkButtonListener());


		workPanel.add(mainRolePanel);

		workPanel.add(back);
		back.addActionListener(new clickButtonListener());

		replacePanel(eastPanel, buttonPanel, workPanel);


	}

	public void getGotJobPanel(JobSpace job) {
		//gotJobPanel
		gotJobPanel = new JPanel();
		gotJobPanel.setBackground(new Color(245, 238, 211));
		gotJobPanel.setPreferredSize(new Dimension(286, 400));
		gotJobPanel.setLayout(new GridLayout(4, 1));

		hiredMsg.setHorizontalAlignment(SwingConstants.CENTER);
		hiredMsg.setFont(new Font("TimeRoman", Font.BOLD, 30));
		gotJobPanel.add(hiredMsg);

		roleName = new JLabel( "Part Name : " + job.getPartName());
		roleName.setHorizontalAlignment(SwingConstants.CENTER);
		roleName.setFont(new Font("TimeRoman", Font.PLAIN, 15));
		gotJobPanel.add(roleName);

		roleDescription = new JLabel("Line : " + job.getPartLine());
		roleDescription.setHorizontalAlignment(SwingConstants.CENTER);
		roleDescription.setFont(new Font("TimeRoman", Font.PLAIN, 15));
		gotJobPanel.add(roleDescription);

		gotJobPanel.add(back);
		back.addActionListener(new clickButtonListener());

		replacePanel(eastPanel, buttonPanel, gotJobPanel);
	}

	public void getActSuccessPanel(String[] result, Player p, String budget) {
		//actSuccessPanel
		actSuccessPanel = new JPanel();
		actSuccessPanel.setBackground(new Color(245, 238, 211));
		actSuccessPanel.setLayout(new GridLayout(7, 1));
		actSuccessPanel.setPreferredSize(new Dimension(286, 400));

		actSuccessPanel.add(successMsg);
		actSuccessPanel.add(youRolled);

		resultDie = new JLabel(new ImageIcon("../resources/img/dice_w" + result[0] + ".png"));

		actSuccessPanel.add(resultDie);

		partLevelLabel = new JLabel("Part level is : " + budget);
		actSuccessPanel.add(partLevelLabel);
		totalSumLabel = new JLabel("Total is : " + String.valueOf(result[0]) + " + " + String.valueOf(p.getShotTokens()) + " = " + (Integer.parseInt(result[0]) + p.getShotTokens()));
		actSuccessPanel.add(totalSumLabel);


		totalEarned = new JLabel(result[1]);
		actSuccessPanel.add(totalEarned);
		actSuccessPanel.add(back);
		back.addActionListener(new clickButtonListener());


		replacePanel(eastPanel, buttonPanel, actSuccessPanel);

	}

	public void getActFailPanel(String[] result, Player p) {
		//actFailPanel

		actFailPanel = new JPanel();
		actFailPanel.setBackground(new Color(245, 238, 211));
		actFailPanel.setLayout(new GridLayout(7, 1));
		actFailPanel.setPreferredSize(new Dimension(286, 400));


		actFailPanel.add(failMsg);
		actFailPanel.add(youRolled);

		resultDie = new JLabel(new ImageIcon("../resources/img/dice_w" + result[0] + ".png"));
		actFailPanel.add(resultDie);

		partLevelLabel = new JLabel("Part level is : " + String.valueOf(p.getCurrentLocation().getMovieCard().getCardBudget()));
		actFailPanel.add(partLevelLabel);
		totalSumLabel = new JLabel("Total is : " + String.valueOf(result[0]) + " + " + String.valueOf(p.getShotTokens()) + " = " + (Integer.parseInt(result[0]) + p.getShotTokens()));
		actFailPanel.add(totalSumLabel);

		totalEarned = new JLabel(result[1]);
		actFailPanel.add(totalEarned);
		actFailPanel.add(back);
		back.addActionListener(new clickButtonListener());

		replacePanel(eastPanel, buttonPanel, actFailPanel);
	}

	public void getRehearsePanel() {

		//rehearsePanel
		rehearsePanel = new JPanel();
		rehearsePanel.setBackground(new Color(245, 238, 211));
		rehearsePanel.setPreferredSize(new Dimension(286, 400));
		rehearsePanel.setLayout(new GridLayout(3, 1));
		JLabel rehearseMsg = new JLabel("Rehearse");
		rehearseMsg.setHorizontalAlignment(SwingConstants.CENTER);
		rehearseMsg.setFont(new Font("TimeRoman", Font.BOLD, 30));
		rehearsePanel.add(BorderLayout.NORTH, rehearseMsg);

		tokenEarned = new JLabel("+1 Token");
		tokenEarned.setHorizontalAlignment(SwingConstants.CENTER);
		tokenEarned.setFont(new Font("TimeRoman", Font.PLAIN, 20));
		rehearsePanel.add(tokenEarned);
		rehearsePanel.add(back);
		back.addActionListener(new clickButtonListener());

		replacePanel(eastPanel, buttonPanel, rehearsePanel);
	}

	public void getUpgradePanel() {
		//upgradePanel
		upgradePanel = new JPanel();
		upgradePanel.setBackground(new Color(245, 238, 211));
		upgradePanel.setLayout(new GridLayout(5, 1));
		upgradePanel.setPreferredSize(new Dimension(286, 400));

		upgradePanel.add(upgradeMsg);
		JLabel chooseCurr = new JLabel("Choose your currency");
		upgradePanel.add(chooseCurr);

		dollar = new JButton("Dollar");
		dollar.addActionListener(new clickButtonListener());
		upgradePanel.add(dollar);

		credit = new JButton("Credit");
		credit.addActionListener(new clickButtonListener());
		upgradePanel.add(credit);

		upgradePanel.add(back);
		back.addActionListener(new clickButtonListener());
		replacePanel(eastPanel, buttonPanel, upgradePanel);
	}

	public void getUpgradeDollarPanel() {
		upgradeDollarPanel = new JPanel();
		upgradeDollarPanel.setBackground(new Color(245, 238, 211));
		upgradeDollarPanel.setPreferredSize(new Dimension(286, 400));
		upgradeDollarPanel.setLayout(new GridLayout(7, 1));

		dLv2.addActionListener(new clickUpgradeButtonListener());
		dLv3.addActionListener(new clickUpgradeButtonListener());
		dLv4.addActionListener(new clickUpgradeButtonListener());
		dLv5.addActionListener(new clickUpgradeButtonListener());
		dLv6.addActionListener(new clickUpgradeButtonListener());

		upgradeDollarPanel.add(upgradeMsg);
		upgradeDollarPanel.add(dLv2);
		upgradeDollarPanel.add(dLv3);
		upgradeDollarPanel.add(dLv4);
		upgradeDollarPanel.add(dLv5);
		upgradeDollarPanel.add(dLv6);
		upgradeDollarPanel.add(back);
		back.addActionListener(new clickButtonListener());

		replacePanel(eastPanel, buttonPanel, upgradeDollarPanel);

	}

	public void getUpgradeCreditPanel() {
		upgradeCreditPanel = new JPanel();
		upgradeCreditPanel.setBackground(new Color(245, 238, 211));
		upgradeCreditPanel.setPreferredSize(new Dimension(286, 400));
		upgradeCreditPanel.setLayout(new GridLayout(7, 1));

		cLv2.addActionListener(new clickUpgradeButtonListener());
		cLv3.addActionListener(new clickUpgradeButtonListener());
		cLv4.addActionListener(new clickUpgradeButtonListener());
		cLv5.addActionListener(new clickUpgradeButtonListener());
		cLv6.addActionListener(new clickUpgradeButtonListener());

		upgradeCreditPanel.add(upgradeMsg);
		upgradeCreditPanel.add(cLv2);
		upgradeCreditPanel.add(cLv3);
		upgradeCreditPanel.add(cLv4);
		upgradeCreditPanel.add(cLv5);
		upgradeCreditPanel.add(cLv6);
		upgradeCreditPanel.add(back);
		back.addActionListener(new clickButtonListener());
		replacePanel(eastPanel, buttonPanel, upgradeCreditPanel);
	}

	public void getUpgradeSuccessPanel() {
		upgradeSuccessPanel = new JPanel();
		upgradeSuccessPanel.setBackground(new Color(245, 238, 211));
		upgradeSuccessPanel.setPreferredSize(new Dimension(286, 400));
		upgradeSuccessPanel.setLayout(new GridLayout(2, 1));

		successMsg.setHorizontalAlignment(SwingConstants.CENTER);
		successMsg.setVerticalAlignment(SwingConstants.CENTER);
		successMsg.setFont(new Font("TimeRoman", Font.BOLD, 30));
		upgradeSuccessPanel.add(successMsg);

		upgradeSuccessPanel.add(back);
		back.addActionListener(new clickButtonListener());
		replacePanel(eastPanel, buttonPanel, upgradeSuccessPanel);

	}

	public void getInvalidInputPanel() {

		invalidInputPanel = new JPanel();
		invalidInputPanel.setBackground(new Color(245, 238, 211));
		invalidInputPanel.setLayout(new GridLayout(3, 1));
		failMsg.setHorizontalAlignment(SwingConstants.CENTER);
		failMsg.setFont(new Font("TimeRoman", Font.BOLD, 30));
		invalidInputPanel.add(failMsg);

		invalidInputMsg.setHorizontalAlignment(SwingConstants.CENTER);
		invalidInputMsg.setFont(new Font("TimeRoman", Font.PLAIN, 20));
		invalidInputPanel.add(invalidInputMsg);
		invalidInputPanel.add(back);
		back.addActionListener(new clickButtonListener());
		replacePanel(eastPanel, buttonPanel, invalidInputPanel);

	}

	public void setNeighborsButtonText(String[] s) {
		JButton[] buttonList = new JButton[]{neighbor1, neighbor2, neighbor3, neighbor4};
		for(int i = 0; i < s.length; i++) {
			buttonList[i].setText(s[i]);
		}
	}

	public void setWorkButtonText(JobSpace[] extras, JobSpace[] mainRoles) {
		JButton[] extraList = new JButton[] {extra1, extra2, extra3, extra4};
		JButton[] mainRoleList = new JButton[] {role1, role2, role3};
		for(int i = 0; i < extras.length; i++) {
			extraList[i].setText(extras[i].getPartName());
		}
		for(int j = 0; j < mainRoles.length; j++) {
			mainRoleList[j].setText(mainRoles[j].getPartName());
		}
	}

	public void setEndOfDayFrame(String day) {
		JFrame endOfDayFrame = new JFrame();
		endOfDayFrame.setSize(new Dimension(600, 600));
		JLabel endOfDayMsg = new JLabel("End of day " + day);
		endOfDayMsg.setHorizontalAlignment(SwingConstants.CENTER);
		endOfDayMsg.setVerticalAlignment(SwingConstants.CENTER);
		endOfDayMsg.setFont(new Font("TimeRoman", Font.BOLD, 60));

		endOfDayFrame.add(endOfDayMsg);
		endOfDayFrame.setVisible(true);

	}


	public void setGameOverFrame(Player[] players) {
		JFrame gameOverFrame = new JFrame();
		gameOverFrame.setSize(new Dimension(1200, 800));
		gameOverFrame.setLayout(new GridLayout(3, 1));
		gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel gameOverMsg = new JLabel("GAME OVER");
		gameOverMsg.setHorizontalAlignment(SwingConstants.CENTER);
		gameOverMsg.setVerticalAlignment(SwingConstants.CENTER);
		gameOverMsg.setFont(new Font("TimeRoman", Font.BOLD, 90));

		JLabel winnerMsg = new JLabel("The Winner(s)");
		winnerMsg.setHorizontalAlignment(SwingConstants.CENTER);
		winnerMsg.setFont(new Font("TimeRoman", Font.BOLD, 70));

		JLabel displayWinner = new JLabel();
		displayWinner.setHorizontalAlignment(SwingConstants.CENTER);
		displayWinner.setFont(new Font("TimeRoman", Font.BOLD, 70));

		String[] winners = new String[players.length];
		int winnersCounter = 0;
		int highestScore = 0;
		String winnerString = "";

		for (int i = 0; i < players.length; i++) {
			players[i].changeMoney(players[i].getCredit()+(5*players[i].getRank()));
			if (highestScore < players[i].getMoney()) {
				highestScore = players[i].getMoney();
			}
		}
		for (int i = 0; i < players.length; i++) {
			if (highestScore == players[i].getMoney()) {
				winners[winnersCounter] = "Player " + String.valueOf(players[i].getPlayerID() + 1);
				winnersCounter++;
			}
		}



		for (int i = 0; i < winnersCounter; i++) {
			winnerString += winners[i] + " ";
		}

		displayWinner.setText(winnerString);

		gameOverFrame.add(gameOverMsg);
		gameOverFrame.add(winnerMsg);
		gameOverFrame.add(displayWinner);
		gameOverFrame.setVisible(true);

	}


	public void cardDoneFrame(int[] diceRolled) {
		JFrame cardDoneFrame = new JFrame();
		cardDoneFrame.setSize(new Dimension(600, 600));
		cardDoneFrame.setLayout(new GridLayout(3, 1));

		JLabel cardDoneMsg = new JLabel("Scene Complete!");
		cardDoneMsg.setHorizontalAlignment(SwingConstants.CENTER);
		cardDoneMsg.setFont(new Font("TimeRoman", Font.BOLD, 40));


		JLabel diceRolledMsg = new JLabel("Dice Rolled");
		diceRolledMsg.setHorizontalAlignment(SwingConstants.CENTER);
		diceRolledMsg.setFont(new Font("TimeRoman", Font.PLAIN, 30));

		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new GridLayout(1, 6));
		cardDoneFrame.add(cardDoneMsg);
		cardDoneFrame.add(diceRolledMsg);
		for(int i = 0; i < diceRolled.length; i++) {
			JLabel dieLabel = new JLabel(new ImageIcon("../resources/img/dice_w" + diceRolled[i] + ".png"));
			dicePanel.add(dieLabel);
		}
		cardDoneFrame.add(dicePanel);


		cardDoneFrame.setVisible(true);

	}

	public void resetBoard() {

		trainStation.setIcon(new ImageIcon("../resources/img/cardback.png"));

		jail.setIcon(new ImageIcon("../resources/img/cardback.png"));

		mainStreet.setIcon(new ImageIcon("../resources/img/cardback.png"));

		generalStore.setIcon(new ImageIcon("../resources/img/cardback.png"));

		saloon.setIcon(new ImageIcon("../resources/img/cardback.png"));

		ranch.setIcon(new ImageIcon("../resources/img/cardback.png"));

		bank.setIcon(new ImageIcon("../resources/img/cardback.png"));

		hotel.setIcon(new ImageIcon("../resources/img/cardback.png"));

		secretHideout.setIcon(new ImageIcon("../resources/img/cardback.png"));

		church.setIcon(new ImageIcon("../resources/img/cardback.png"));

		takesLabel.removeAll();


	}



	private class clickButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			String input = button.getText();
			ob.update(input);
			frame.repaint();
		}
	}

	private class clickMoveButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			String input = button.getText();
			if(!input.equals("-")) {
				ob.move(input);
				frame.repaint();
			}

		}
	}

	private class clickWorkButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			String input = button.getText();
			if(!input.equals("-")) {
				ob.work(input);
			}
		}
	}

	private class clickUpgradeButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			String input = button.getText();
			ob.upgrade(input);

		}
	}
}
