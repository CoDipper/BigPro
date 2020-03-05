package store.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener  {
	private static final long serialVersionUID = 1L;
	
	//private String typedText;
	private ItemsDataGUI parentgui;
	private JOptionPane optionPane;
	private String nameLabel = "Brand name";
	private String priceLabel = "Price";
	
	public CustomDialog(String text, ItemsDataGUI parent) {
		setTitle("Sorting choice");
		this.parentgui= parent;
		
		Object[] options = {nameLabel, priceLabel};
		optionPane = new JOptionPane(text, JOptionPane.QUESTION_MESSAGE, 
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		setContentPane(optionPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});
		
		optionPane.addPropertyChangeListener(this);
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

		if (isVisible() && (evt.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(propertyName) || 
						JOptionPane.INPUT_VALUE_PROPERTY.equals(propertyName))) {
			
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				return;
			}

			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (value.equals(nameLabel)) {
				parentgui.sort(1, parentgui.tabbedPane, ItemsDataGUI.categories);
				clearAndHide();
			}else{
				parentgui.sort(2, parentgui.tabbedPane, ItemsDataGUI.categories);
				clearAndHide();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(nameLabel);
		optionPane.setValue(priceLabel);
	}
	private void clearAndHide() {
		setVisible(false);
	}
}
