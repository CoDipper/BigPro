package store.extending;

public class Item {
	
	//variables
	private String nameItem;
	private double price;
	private String[] sizes;
	private String[] colors;
	
	//constructors
	public Item(String nameItem, double price, String[] size, String[] color) {
		super();
		this.nameItem = nameItem;
		this.price = price;
		this.sizes = size;
		this.colors = color;
	}
	public Item() {
		
	}

	//getters & setters 
	public String getNameItem() {
		return nameItem;
	}
	public void setNameItem(String nameItem) {
		this.nameItem = nameItem;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String[] getSize() {
		return sizes;
	}
	public void setSize(String[] size) {
		this.sizes = size;
	}
	public String[] getColor() {
		return colors;
	}
	public void setColor(String[] color) {
		this.colors = color;
	}
}
