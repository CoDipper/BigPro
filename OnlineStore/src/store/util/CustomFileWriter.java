package store.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import store.extending.Cart;

public class CustomFileWriter {

	private static final String FILE_EXTENSION = ".file";
	private static final String FILE_NAME = "Orders";
	private static final String FILE_PATH = "src/store/files/";
	private static final String FULL_PATH = FILE_PATH + FILE_NAME + FILE_EXTENSION;
	
	//private static ArrayList<Person> peopleList = new ArrayList<>();
	public void createFile() {
		File file = new File(FULL_PATH);
		file.getParentFile().mkdirs();
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.err.println("Файлът не може да бъде създаден!");
			e.printStackTrace();
		}
	}
	public void writePay(Cart[] items, String mail, String pass, String sum) {
		Writer writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(FULL_PATH, true)); // enable append
				writer.append(mail + "\t");
				writer.append("pass"+ "\n");
			for (Cart item : items) {
				writer.append(item.getBrand() + "\t");
				writer.append(item.getColor() + "\t");
				writer.append(item.getSize() + "\t");
				writer.append(item.getNumber() + "\t");
				writer.append(item.getPrice() + "\t");
				writer.append("\n");
				
				System.out.printf("%s е успешно добавен във файла! \n", item.getBrand());
			}	writer.append(sum + "\n \n");
		} catch (IOException e) {
			System.err.println("Записът не може да бъде добавен във файла!");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					System.out.println("Записвачът не може да бъде затворен правилно!");
					e.printStackTrace();
				}
			}
		}
	}
	public void writeCar(Cart[] items, String type, String num, 
						String hold, String date, String cv, String sum) {
		Writer writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(FULL_PATH, true)); // enable append
				
				writer.append(hold + "\t");
				writer.append(type + "\t");
				writer.append(num + "\t");
				writer.append(date + "\t");
				writer.append(cv + "\n");
			for (Cart item : items) {
				writer.append(item.getBrand() + "\t");
				writer.append(item.getColor() + "\t");
				writer.append(item.getSize() + "\t");
				writer.append(item.getNumber() + "\t");
				writer.append(item.getPrice() + "\t");
				writer.append("\n");
				
				System.out.printf("%s е успешно добавен във файла! \n", item.getBrand());
			}	writer.append(sum + "\n \n");
		} catch (IOException e) {
			System.err.println("Записът не може да бъде добавен във файла!");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					System.out.println("Записвачът не може да бъде затворен правилно!");
					e.printStackTrace();
				}
			}
		}
	}
}

