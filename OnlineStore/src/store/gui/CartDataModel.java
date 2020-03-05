package store.gui;

import java.text.DecimalFormat;

import javax.swing.table.AbstractTableModel;

import store.extending.Cart;


public class CartDataModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	
	private Cart[] cart;

	public CartDataModel(Cart[] cart) {
		this.cart = cart;
	}
	
	@Override
	public int getRowCount() {
		return cart.length;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DecimalFormat df = new DecimalFormat("###.###");
		switch (columnIndex) {
		case 0:
			return cart[rowIndex].getBrand();
		case 1:
			return cart[rowIndex].getSize();
		case 2:
			return cart[rowIndex].getColor();
		case 3:
			return cart[rowIndex].getNumber();
		case 4:
			return cart[rowIndex].getPrice();
		case 5:
			return df.format(cart[rowIndex].getPrice()*cart[rowIndex].getNumber());
		}
	return null;
	}
	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Brand";
			case 1:
				return "Size";
			case 2:
				return "Color";
			case 3:
				return "Number";
			case 4:
				return "Price";
			case 5:
				return "Final";
			default:
				return super.getColumnName(column);
		}
	}
}
