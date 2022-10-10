public class JobSpace {
	private String partName;
	private int rank, x, y, h, w;
	private String partLine;
	private Player playerWorking;
	private boolean isExtra;
	//constructor
	public JobSpace(String partName, int level, String partLine, int x, int y, int h, int w) {
		this.partName = partName;
		this.rank = level;
		this.partLine = partLine;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	//added getters and setters for location and size
	public int[] getLocation() {
		return new int[] {x, y};
	}
	public int[] getSize() {
		return new int[] {w, h};
	}
	//getters and setters
	public String getPartName() {
		return partName;
	}
	public int getRank() {
		return rank;
	}
	public String getPartLine() {
		return partLine;
	}
	public void setPlayerWorking(Player player) {
		playerWorking = player;
	}
	public Player getPlayerWorking() {
		return playerWorking;
	}
	public void setIsExtra(boolean isExtra) {
		this.isExtra = isExtra;
	}
	public boolean getIsExtra() {
		return isExtra;
	}
}