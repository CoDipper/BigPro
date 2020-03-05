package store.extending;

public class Category{
	
	//variables
	private String nameCategory;
	private Subcategory[] subcategories;
	
	//constructors
	public Category(String nameCategory, Subcategory[] subcategories) {
		super();
		this.nameCategory = nameCategory;
		this.subcategories = subcategories;
	}
	public Category() {
		
	}

	//getters & setters
	public String getNameCategory() {
		return nameCategory;
	}
	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}
	public Subcategory[] getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(Subcategory[] subcategories) {
		this.subcategories = subcategories;
	}
}
