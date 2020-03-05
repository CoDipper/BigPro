package store.extending;

public class Cart {
	private String Brand;
	private double price;
	private String size;
	private String color;
	private int number;
	
	//constructors
	public Cart(String brand, double price, String size, String color, int number) {
		super();
		Brand = brand;
		this.price = price;
		this.size = size;
		this.color = color;
		this.number = number;
	}
	public Cart() {
		
	}
	
	//getters and setters
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
