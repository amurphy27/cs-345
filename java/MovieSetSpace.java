public class MovieSetSpace {
	private String setName;
	private String[] neighbors;
	private int[] takesList, takesX, takesY, takesH, takesW, takesMax;
	private MovieCard movieCard;
	private JobSpace[] jobSpaces;
	private int x, y, h, w;
	//constructor
	public MovieSetSpace(String setName, String[] neighbors, int x, int y, int h, int w, int[] takesList, int[] takesX, int[] takesY, int[] takesH, int[] takesW, String[] partsNameList, int[] partsLevelList, String[] partsLineList, int[] partsX, int[] partsY, int[]partsH, int[] partsW) {
		this.setName = setName;
		this.neighbors = neighbors;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.takesList = takesList;
		this.takesX = takesX;
		this.takesY = takesY;
		this.takesH = takesH;
		this.takesW = takesW;
		takesMax = takesList;
		jobSpaces = new JobSpace[partsNameList.length];
		for (int i = 0; i < partsNameList.length; i++) {
			jobSpaces[i] = new JobSpace(partsNameList[i], partsLevelList[i], partsLineList[i], partsX[i], partsY[i], partsH[i], partsW[i]);
		}
	}
	//Constructor for trailer and office for now
	public MovieSetSpace(String setName, String[] neighbors, int x, int y, int h, int w) {
		this.setName = setName;
		this.neighbors = neighbors;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	//added getters location (x,y) and dimensions (w, h)
	public int[] getLocation() {
		return new int[] {x, y};
	}
	public int[] getDimensions() {
		return new int[] {w, h};
	}
	//getters and setters
	public String getSetName() {
		return setName;
	}
	public String[] getNeighbors() {
		return neighbors;
	}
	public int[] getTakesList() {
		return takesList;
	}
	public void setMovieCard(MovieCard movieCard) {
		this.movieCard = movieCard;
	}
	public MovieCard getMovieCard() {
		return movieCard;
	}
	public JobSpace[] getJobSpaces() {
		return jobSpaces;
	}
	//gets job space by name
		public JobSpace getJobSpace(String partName) {
			JobSpace correctSpace = null;
			for (int i = 0; i < jobSpaces.length; i++) {
				if (partName.equals(jobSpaces[i].getPartName())) {
					correctSpace = jobSpaces[i];
				}
			}
			return correctSpace;
		}
	//updates the takes array to be one index shorter and if all takes are gone then pay actors,
	//sets jobs to null and stops players from working
	public String[] removeTake() {
		String tempX = String.valueOf(takesX[takesList.length-1]);
		String tempY = String.valueOf(takesY[takesList.length-1]);
		if (takesList.length - 1 == 0) {
			movieCard.payActors();
			Player[] playersOnCard = new Player[jobSpaces.length];
			for (int i = 0; i < jobSpaces.length; i++) {
				playersOnCard[i] = jobSpaces[i].getPlayerWorking();
			}
			for (int l = 0; l < playersOnCard.length; l++) {
				if (playersOnCard[l] != null) {
					playersOnCard[l].work(null);
					playersOnCard[l].resetShotTokens();
				}
			}
			takesList = null;
		}
		else {
			int[] temp = new int[takesList.length-1];
			for (int i = 0; i < takesList.length - 1; i++) {
				temp[i] = takesList[i];
			}
			takesList = temp;
		}
		return new String[] {tempX, tempY};
	}
	
	public void resetTakes() {
		takesList = takesMax;
	}
}