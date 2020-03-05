package store.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import store.extending.Category;
import store.extending.Item;
import store.extending.Subcategory;

public class FileReader {
	private static final String PERSON_FILE_EXTENSION = ".file";
	private static final String PERSON_FILE_NAME = "manu";
	private static final String FILE_PATH = "src/store/files/";
	private static final String FULL_PATH = FILE_PATH + PERSON_FILE_NAME + PERSON_FILE_EXTENSION;
	
	private static ArrayList<Category> categoriesList = new ArrayList<>();
	private static ArrayList<Subcategory> subcategoriesList = new ArrayList<>();
	private static ArrayList<Item> itemsList = new ArrayList<>();
	
	public static boolean isFileExists() {
		File file = new File(FULL_PATH);
		return file.exists();
	}
	
	@SuppressWarnings("resource")
	public Category[] readCategories() {
		try {
			FileInputStream fileStream = new FileInputStream(FULL_PATH);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileStream));
			String stringLine;
			
			//Променливи за брой на: 
			int x=0;					//Категории
			int y=0; 					//Подкатегории
			int z=0;					//Продукти
			
			
			
			while((stringLine = bufferedReader.readLine()) != null) {
				
				if(stringLine.startsWith("<") && !stringLine.startsWith("</")) {
					Category cat= new Category();
					String data = stringLine.substring(1, stringLine.length()-1);
					cat.setNameCategory(data);
					categoriesList.add(cat);
					//check output
					//System.out.println(categoriesList.get(x).getNameCategory());
				}else if(stringLine.startsWith("_")) {
					Subcategory sub= new Subcategory();
					String getData= stringLine.substring(1);
					String[] data= getData.split(",");
					//check output
					//System.out.println(data);
					sub.setNameSubcategory(data[0]);
					sub.setImagePath(data[1]);
					subcategoriesList.add(sub);
					Subcategory[] subcategories= new Subcategory[subcategoriesList.size()];
					categoriesList.get(x).setSubcategories(subcategoriesList.toArray(subcategories));
					//check output
					//System.out.println(categoriesList.get(0).getSubcategories()[0].getNameSubcategory());
					itemsList.clear();
					z=0;
					y++;
				}else if(stringLine.startsWith("*")) {
					Item item= new Item();
					String getData= stringLine.substring(1);
					String[] data= getData.split(",");
					item.setNameItem(data[0]);
					item.setPrice(Double.parseDouble(data[1]));
					item.getPrice();
					String[] sizes= data[2].split("-");
					item.setSize(sizes);
					String[] colors= data[3].split("-");
					item.setColor(colors);
					itemsList.add(z, item);
					Item[] items = new Item[itemsList.size()];
					categoriesList.get(x).getSubcategories()[y -1].setItems(itemsList.toArray(items));
					//check output
					//System.out.println(categoriesList.get(x).getSubcategories()[y-1].getItems()[z].getNameItem());
					//System.out.println(categoriesList.get(x).getSubcategories()[y-1].getItems()[z].getPrice());
					z++;
				}else if(stringLine.startsWith("</")) {
					
					Subcategory[] subcategories= new Subcategory[subcategoriesList.size()];
					categoriesList.get(x).setSubcategories(subcategoriesList.toArray(subcategories));
					
					//Item[] items = new Item[itemsList.size()];
					//categoriesList.get(x).getSubcategories()[y].setItems(itemsList.toArray(items));
					
					subcategoriesList.clear();
					itemsList.clear();
					x++;
					y=0;
					z=0;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Category[] categories= new Category[categoriesList.size()];
		return categoriesList.toArray(categories);
	}
}
