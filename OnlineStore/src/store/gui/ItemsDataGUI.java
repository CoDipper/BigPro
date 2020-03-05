package store.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import store.comparators.ItemsComparator;
import store.comparators.NameComparator;
import store.comparators.PriceComparator;
import store.extending.Cart;
import store.extending.Category;
import store.extending.Item;
import store.util.CustomFileWriter;
import store.util.FileReader;
import store.util.LeftRightSpinnerUI;


public class ItemsDataGUI {
	public static Category[] categories;
	private static ArrayList<Cart> itemsList = new ArrayList<>();
	double SUM=0;
	JFrame f = new JFrame("Dress your princess");
	static JButton cart= new JButton("View Cart");
	static JButton sort= new JButton("Sorting");
    static JButton home= new JButton("Home");
    JPanel WorkPanel = new JPanel(new GridLayout());
    
	JTabbedPane tabbedPane;
	JTable Tcart;
	
	public static void main(String[] args) {
		
		FileReader file= new FileReader();
		categories = file.readCategories();
		
		ItemsDataGUI gui = new ItemsDataGUI();
		gui.createAndShowGUI();
	}

	private void createAndShowGUI() {
        JPanel p1 = new JPanel(new GridLayout()); 
        JPanel p2 = new JPanel(); 
        
        //add tree
        DefaultMutableTreeNode over=new DefaultMutableTreeNode("Menu");
        for(int i=0; i < categories.length ; i++) {
        	DefaultMutableTreeNode category=new DefaultMutableTreeNode(categories[i].getNameCategory());
        	over.add(category);
        	for(int j=0; j< categories[i].getSubcategories().length; j++) {
        		DefaultMutableTreeNode subcategory=new DefaultMutableTreeNode(categories[i].getSubcategories()[j].getNameSubcategory());
        		//subcategory
        		category.add(subcategory);
        	}
        }
        
        JTree jt=new JTree(over); 
        ImageIcon leafIcon = new ImageIcon("pics/under.jpg");
        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer = 
                new DefaultTreeCellRenderer();
            renderer.setLeafIcon(leafIcon);
            jt.setCellRenderer(renderer);
        }
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane jsp = new JScrollPane(jt, v, h);
        jsp.setSize(220, 200);
        p1.add(jsp);
        
        // create a splitpane 
        JSplitPane sl = new JSplitPane(SwingConstants.VERTICAL, p1, WorkPanel); 
        JSplitPane sl2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sl, p2); 
        // set Orientation for slider 
        sl2.setOrientation(SwingConstants.HORIZONTAL); 
        sl.setResizeWeight(0.3);
        Home();
        //Cart
        sort.setEnabled(false);
        home.setEnabled(false);
        p2.add(home);
        p2.add(sort);
        p2.add(cart);
        // add panel 
        f.add(sl2, BorderLayout.CENTER);
        // set the size of frame 
        f.setSize(700, 500);
        JTextField jtf = new JTextField("", 20);
        jtf.setEnabled(false);
        f.add(jtf, BorderLayout.SOUTH);
        
        // events
        cart.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(itemsList.isEmpty()) {
            		JLabel messg= new JLabel("INFO");
					JOptionPane.showMessageDialog(messg, "Cart is empty.");
            	}else {
            		sort.setEnabled(false);
            		home.setEnabled(true);
            		GoToCart();
            	}
            }

			private void GoToCart() {
				WorkPanel.removeAll();
				WorkPanel.setLayout(new BorderLayout());
				JPanel buttonPane= new JPanel(new GridLayout(1,3));
				JPanel chartPane= new JPanel(new BorderLayout());
				Cart[] items= new Cart[itemsList.size()];
				itemsList.toArray(items);
				CartDataModel cartData= new CartDataModel(items);
				Tcart= new JTable(cartData);
				JScrollPane scrollPane = new JScrollPane(Tcart);
				JLabel carlab= new JLabel("Items:");
				carlab.setFont(new Font("Serif", Font.BOLD, 20));
				JButton PeyPal= new JButton("PeyPal");
				JButton Credit= new JButton("Credit Cart");
				carlab.setVerticalAlignment(JLabel.NORTH);
				carlab.setHorizontalAlignment(JLabel.LEFT);
				DecimalFormat df = new DecimalFormat("###.###");
				String[]helper;
				SUM=0;
				double front, end;
				for(int i=0; i< itemsList.size(); i++) {
					helper=String.valueOf(Tcart.getValueAt(i, 5)).split(",");
					front=Double.parseDouble(helper[0]);
					end=(Double.parseDouble(helper[1]))/100;
					SUM+=front+end;
				}
				carlab.setText("Summary: "+(df.format(SUM)).toString());
				buttonPane.add(carlab);
				buttonPane.add(PeyPal);
				buttonPane.add(Credit);
				chartPane.add(scrollPane);
				JSplitPane splitCart= new JSplitPane(JSplitPane.VERTICAL_SPLIT,buttonPane , chartPane);
				WorkPanel.add(splitCart);
				WorkPanel.revalidate();
				Tcart.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						int r=Tcart.getSelectedRow();
						if(itemsList.get(r).getNumber()>1) {
							itemsList.get(r).setNumber(itemsList.get(r).getNumber()-1);
						}else {
							itemsList.remove(r);
						}
						GoToCart();
					}
				});
				Credit.addMouseListener(new MouseAdapter() {
		            public void mouseClicked(MouseEvent e) {
		            	if(itemsList.isEmpty()) {
		            		JLabel messg= new JLabel("INFO");
							JOptionPane.showMessageDialog(messg, "Cart is empty.");
		            	}else {
		            		Credit();
		            	}
		            }
		        });
				PeyPal.addMouseListener(new MouseAdapter() {
		            public void mouseClicked(MouseEvent e) {
		            	if(itemsList.isEmpty()) {
		            		JLabel messg= new JLabel("INFO");
							JOptionPane.showMessageDialog(messg, "Cart is empty.");
		            	}else {
		            		PeyPal();
		            	}
		            }
		        });
			}
        });
        final JDialog sortDialog = new CustomDialog(getSortText(), this);
        sort.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(!sort.isEnabled()) {
            		sort.setToolTipText("Select subcategory to unlock.");
            		JLabel messg= new JLabel("INFO");
					JOptionPane.showMessageDialog(messg, "Tabs are empty.");
            	}else {
            		sortDialog.pack();
            		sortDialog.setVisible(true);
            	}
            	
            }
        });
        home.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	Home();
            }
        });
        
        jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                	sort.setToolTipText(null);
                    TreePath tp = jt.getPathForLocation(e.getX(), e.getY());
                    if (tp == null) return;
                    // Cast Info to object
                    jtf.setText(tp.toString());
                    DoubleClicked(tp.toString());
                }
            }
        });
        
        f.setVisible(true); 
	}
	
	protected void Home() {
		home.setEnabled(false);
        sort.setEnabled(false);
        WorkPanel.removeAll();
        JLabel homelab= new JLabel("Welcome to Dress your princess", JLabel.CENTER);
        homelab.setFont(new Font("Serif", Font.BOLD, 30));
        WorkPanel.add(homelab);
        WorkPanel.revalidate();
	}

	void DoubleClicked(String Helper) {
		home.setEnabled(true);
		sort.setEnabled(true);
		WorkPanel.removeAll();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		String[] help= Helper.split(",");
		String category= help[1].substring(1);
		String subcategory= help[2].substring(1, help[2].length()-1);
		String namelab;
		double pricelab;
		for(int i=0; i < categories.length; i++) {
			if(categories[i].getNameCategory().equals(category)) {
				for(int j=0; j < categories[i].getSubcategories().length; j++) {
					if(categories[i].getSubcategories()[j].getNameSubcategory().equals(subcategory)) {
						tabbedPane.setName(subcategory);
						for(int p=0; p < categories[i].getSubcategories()[j].getItems().length; p++) {
							namelab= categories[i].getSubcategories()[j].getItems()[p].getNameItem();
							pricelab= categories[i].getSubcategories()[j].getItems()[p].getPrice();
							tabbedPane.addTab(namelab,makePanel(namelab,String.valueOf(pricelab),
									categories[i].getSubcategories()[j].getItems()[p].getSize(),
									categories[i].getSubcategories()[j].getItems()[p].getColor()));
							tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
						}
					}
				}
			}
		}
		WorkPanel.add(tabbedPane);
		WorkPanel.revalidate();
	}

	private static JPanel makePanel(String name, String text, String[] sizes, String[] colors) {
        JButton toCart= new JButton("Add to Cart");
		JPanel p = new JPanel(new GridLayout(14,1));
        p.add(toCart);
        JLabel prtxt= new JLabel("Price: ");
        prtxt.setHorizontalAlignment(JLabel.LEFT);
        p.add(prtxt);
        JLabel pricelab= new JLabel(text + " Euro");
        pricelab.setHorizontalAlignment(JLabel.LEFT);
        pricelab.setFont(new Font("Serif", Font.BOLD, 20));
        p.add(pricelab);
        JLabel sizelab= new JLabel("Sizes: ");
        sizelab.setHorizontalAlignment(JLabel.LEFT);
        p.add(sizelab);
        JLabel colorlab= new JLabel("Colors: ");
        colorlab.setHorizontalAlignment(JLabel.LEFT);
        JComboBox<String> sizeBox = new JComboBox<>(sizes);
        p.add(sizeBox);
        p.add(colorlab);
        JComboBox<String> colorBox = new JComboBox<>(colors);
        p.add(colorBox);
        JLabel countlab= new JLabel("Number: ");
        countlab.setHorizontalAlignment(JLabel.LEFT);
        p.add(countlab);
        
        SpinnerModel model = new SpinnerNumberModel(1, 1, 15, 1);     
        JSpinner spinner = new JSpinner(model);
        //spinner.setLayout(new GridLayout());
        JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tf.setEditable(false);
        tf.setBackground(Color.LIGHT_GRAY);
        spinner.setUI(new LeftRightSpinnerUI());
        spinner.setBounds(70, 70, 50, 40);
        p.add(spinner);
        
        
        Cart a= new Cart();
        toCart.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	a.setBrand(name);
            	a.setPrice(Double.parseDouble(text));
            	a.setSize(sizeBox.getSelectedItem().toString());
            	a.setColor(colorBox.getSelectedItem().toString());		
            	a.setNumber(Integer.parseInt(spinner.getValue().toString()));
            	if(itemsList.isEmpty()) {
            		cart.setToolTipText("Items in cart " + (itemsList.size()+1));
            		itemsList.add(a);
            	}else if(!SameCheck(a)){
            		cart.setToolTipText("Items in cart " + (itemsList.size()+1));
            		itemsList.add(a);
            	}
            }
            boolean SameCheck(Cart a) {
            	boolean flag= false;
            	for(int i=0; i<itemsList.size(); i++) {
            		String compareItem= itemsList.get(i).getBrand() + 
            				itemsList.get(i).getSize() + 
            				itemsList.get(i).getColor();
            		String compareA= a.getBrand() +
            				a.getSize()+
            				a.getColor();
        			if(compareItem.equals(compareA)) {
        				int NumList= itemsList.get(i).getNumber();
        				int NumA= a.getNumber();
        				itemsList.get(i).setNumber(NumList+NumA);
        				flag=true;
        				break;
        			}
        		}
            	return flag;
            }
        });
        return p;
    }
	public void Credit() {
		JFrame payf= new JFrame("Pay frame");
		payf.setSize(600, 250);
		payf.setLayout(new GridLayout(1,2));
		JPanel labels = new JPanel(new GridLayout(7,1));
		JPanel fields = new JPanel(new GridLayout(7,1));
		JButton toFile= new JButton("Print Fac");
		JLabel price= new JLabel("FINAL PRICE: "+String.valueOf(SUM),JLabel.CENTER);
		price.setFont(new Font("Serif", Font.BOLD, 15));
		JLabel typelab=new JLabel("Card type: ",JLabel.CENTER);
		JLabel numlab=new JLabel("Card number: ",JLabel.CENTER);
		JLabel holdlab=new JLabel("Card holder: ",JLabel.CENTER);
		JLabel datelab=new JLabel("Release date: ",JLabel.CENTER);
		JLabel cvlab=new JLabel("Card CVV: ",JLabel.CENTER);
		String[] types= {"MasterCard","VisaCard"};
		JComboBox<String> type= new JComboBox<>(types);
		JTextField numtxt=new JTextField();
		JTextField holdtxt=new JTextField();
		JTextField datetxt=new JTextField();
		JTextField cvtxt=new JTextField();
		labels.add(typelab);	fields.add(type);
		labels.add(numlab);		fields.add(numtxt);
		labels.add(holdlab);	fields.add(holdtxt);
		labels.add(datelab);	fields.add(datetxt);
		labels.add(cvlab);		fields.add(cvtxt);
		labels.add(price);		fields.add(toFile);
		payf.add(labels);
		payf.add(fields);
		payf.setVisible(true);
		toFile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(String.valueOf(numtxt)==null&&
            			String.valueOf(holdtxt)==null) {
            		JLabel messg= new JLabel("INFO");
            		JOptionPane.showMessageDialog(messg, "Fields are empty.");
            		
            	}else if(String.valueOf(datetxt)==null&&
                			String.valueOf(cvtxt)==null){
            		JLabel messg= new JLabel("INFO");
        			JOptionPane.showMessageDialog(messg, "Fields are empty.");
            		
            	}else {
            		String stype= String.valueOf(type.getSelectedItem());
            		String num= String.valueOf(numtxt.getText());
            		String hold= String.valueOf(holdtxt.getText());
            		String date= String.valueOf(datetxt.getText());
            		String cv= String.valueOf(cvtxt.getText());
            		Cart[] items= new Cart[itemsList.size()];
    				itemsList.toArray(items);
    				CustomFileWriter file= new CustomFileWriter();
            		file.writeCar(items, stype,  num, 
    						 hold,  date, cv, String.valueOf(SUM));
            		
            		payf.removeAll();
            		payf.dispose();
            	}
            }
        });
	}
	public void PeyPal() {
		JFrame payf= new JFrame("Pay frame");
		payf.setSize(450, 200);
		payf.setLayout(new GridLayout(1,2));
		JPanel labels = new JPanel(new GridLayout(5,1));
		JPanel fields = new JPanel(new GridLayout(5,1));
		JButton toFile= new JButton("Print Fac");
		JLabel price= new JLabel("FINAL PRICE: "+String.valueOf(SUM),JLabel.CENTER);
		price.setFont(new Font("Serif", Font.BOLD, 14));
		JLabel maillab=new JLabel("E-mail: ",JLabel.CENTER);
		JLabel tab=new JLabel("		 ");
		JLabel tab1=new JLabel("		 ");
		JLabel passlab=new JLabel("Password: ",JLabel.CENTER);
		JPasswordField passtxt = new JPasswordField(10);
		JTextField mailtxt= new JTextField();
		labels.add(tab);		fields.add(tab1);
		labels.add(maillab);	fields.add(mailtxt);
		labels.add(passlab);	fields.add(passtxt);
		labels.add(price);		fields.add(toFile);
		payf.add(labels);
		payf.add(fields);
		payf.setVisible(true);
		toFile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(String.valueOf(mailtxt)==null&&
            			String.valueOf(passtxt)==null) {
            		JLabel messg= new JLabel("INFO");
					JOptionPane.showMessageDialog(messg, "Fields are empty.");
            	}else {
            		String mail= String.valueOf(mailtxt.getText());
            		String pass= String.valueOf(passtxt);
            		Cart[] items= new Cart[itemsList.size()];
    				itemsList.toArray(items);
    				CustomFileWriter file= new CustomFileWriter();
            		file.writePay(items, mail, pass, String.valueOf(SUM));
            		payf.removeAll();
            		payf.dispose();
            	}
            }
        });
	}
	public void sort(int intValue, JTabbedPane tabs, Category[] categories) {
		Item[] items = null;
		String subName= tabs.getName();
		for(int i=0; i<categories.length; i++) {
			for(int j=0; j<categories[i].getSubcategories().length; j++) {
				if(subName.equals(categories[i].getSubcategories()[j].getNameSubcategory())) {
					items=categories[i].getSubcategories()[j].getItems();
				}
			}
		}
		ItemsComparator comparator = null;
		
		switch (intValue) {
			case 1: 
				comparator = new NameComparator(); 
				break;
			case 2: 
				comparator = new PriceComparator();
				break;
		}

		if (comparator == null) {
			Arrays.sort(items);
		} else {
			Arrays.sort(items, comparator);
		}
		tabs.removeAll();
		String namelab;
		double pricelab;
		for(int i=0; i<items.length; i++) {
			namelab= items[i].getNameItem();
			pricelab= items[i].getPrice();
			tabbedPane.setName(subName);
			tabbedPane.addTab(namelab,makePanel(namelab,String.valueOf(pricelab),
					items[i].getSize(),
					items[i].getColor()));
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		}
		tabs.repaint();	
	}
	private static String getSortText() {
		return "Pick a sorting method:"; 
	}
}