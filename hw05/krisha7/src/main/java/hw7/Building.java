package hw7;

public class Building implements Comparable<Building> {

	private String Name;
	private String ID;
	private Double xcoord;
	private Double ycoord;

	/**
		@param: String designating name of building
		@param: String designating ID of building
		@param: Double designating x coordinate of building
		@param: Double designating y coordinate of building
		@effects: Constructs new Building following specifications
	 */
	public Building(String name, String id, Double x, Double y) {
		xcoord = x;
		ycoord = y;
		Name = new String(name);
		ID = new String(id);
	}
	
	/** 
		@param: other Building to be compared
		@requires: other isn't non empty
		@return: 0 if this == other, a positive number if this > other or a negative number if this < other.
	 */
	@Override
	public int compareTo(Building other) { 
		if (Name.equals(other.Name)) {
			return ID.compareTo(other.ID);
			
		} else {
			return Name.compareTo(other.Name);
		}
	}
	
	/**
		@return: this Building object's ID
	 */
	public String id() {
		return new String(ID);
	}
	
	/**
		@return: this Building object's y coordinate
	 */
	public Double y() {
		return ycoord;
	}
	
	/**
		@return: this Building object's x coordinate
	 */
	public Double x() {
		return xcoord;
	}
	
	/**
		@return: this Building object's name
	 */
	public String name() {
		return new String(Name);
	}
}