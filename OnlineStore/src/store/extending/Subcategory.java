package store.extending;

public class Subcategory{
	
	//variables
	private String nameSubcategory;
	private String imagePath;
	private Item[] items;
	
	//constructors
	public Subcategory(String nameSubcategory,String imagePath, Item[] items) {
		super();
		this.nameSubcategory = nameSubcategory;
		this.imagePath = imagePath;
		this.items = items;
	}
	public Subcategory() {
		
	}

	//getters & setters
	public String getNameSubcategory() {
		return nameSubcategory;
	}
	public void setNameSubcategory(String nameSubcategory) {
		this.nameSubcategory = nameSubcategory;
	}
	public Item[] getItems() {
		return items;
	}
	public void setItems(Item[] items) {
		this.items = items;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
