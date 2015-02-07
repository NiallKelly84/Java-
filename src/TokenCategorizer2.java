


/*
 * 
 * NOTE: For some reason my File tab in the menu bar doesn't appear sometimes when the program is run
 * You may need to run it 4 or 5 times before it displays(maybe this is just a glitch on my version of java)
 * 
 */

	
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TokenCategorizer2 extends TokenCategorizer { // this class extends the class containing the solution to part1 (and the main method)

	// declaration of variables
	private static final long serialVersionUID = 1L;
	protected DefaultListModel<String> listModel;
	protected JTable table;
	protected JScrollPane tableContainer;
	protected JPanel panel;
	protected JFrame frame;
	protected JMenuBar menuBar;
	protected JMenu menu;
	protected JMenuItem menuItem1;
	protected JMenuItem menuItem2;
	protected JList<String> myList;
	protected String col[] = { "Integral numbers", "Real numbers",
			"Other tokens" };
	protected TableModel dataModel;
	protected String filePath;

	public TokenCategorizer2(String filename, Locale locale)
			throws FileNotFoundException { // Constructor for class
		FileInput(filename, locale);
		// methods to create the datatable and associated elements
		createTableModel();
		createJPanel();
		createTable();
		createJScrollPane();
		createJList();
		createJFrame();
		createMenuBar();
		createMenu();
		createMenuItem1();
		createMenuItem2();
		generateListPermutations(); // method to generate the permutations
	}

	public JMenuBar createMenuBar() { //method to create menu bar
		menuBar = new JMenuBar();	//create new instance of menubar
		frame.setJMenuBar(menuBar);	//set the menu bar into the frame
		menuBar.setVisible(true);
		return menuBar;	
	}

	public JMenu createMenu() {	//method to create menu
		menu = new JMenu("File");	//create instance of menu
		menuBar.add(menu);	//add menu to menuBar
		return menu;
	}

	public JMenuItem createMenuItem1() {	//method to create first menuItem
		menuItem1 = new JMenuItem("Open");	//create instance of Jmenuitem with with display name "Open"
		menu.add(menuItem1);	//add the menuItem to the menu

		menuItem1.addActionListener(new ActionListener() {	//add actionlistener to the menu item

			@Override
			public void actionPerformed(ActionEvent arg0) {	//method for action that occurs
				JFileChooser chooser = new JFileChooser(); // Create new JFileChooser Object
				chooser.showOpenDialog(null); // Shows the File Chooser Dialog box
				File selectedFile = chooser.getSelectedFile(); // Creates a new File object and assigns the file choosen by the user to this object
				filePath = selectedFile.getAbsolutePath();	//get the filepath

				try {
					//clear the arraylist
					listOfLongs.clear();
					listOfDoubles.clear();
					listOfStrings.clear();

					FileInput(filePath, locale);	//call the file inout method

					table.updateUI();	//update the table display

				} catch (FileNotFoundException e) {

					e.printStackTrace();	//exception
				}
			}

		});

		return menuItem1;	//return the menuItem1
	}

	public JMenuItem createMenuItem2() {	//method to createMenuItem2

		menuItem2 = new JMenuItem("Quit");	//create Quit menu item
		menu.add(menuItem2);	//att it to the menu
		menuItem2.addActionListener(new ActionListener() {		//add an action listener

			@Override
			public void actionPerformed(ActionEvent arg0) {	//create action event
				System.exit(0);	//exit the program
			}
		});

		return menuItem2;	// return the menu item
	}

	public TableModel createTableModel() {	//createTableModel
		dataModel = new AbstractTableModel() {	//create abstract datamodel
			public int getRowCount() {	//method to get the number of rows
				//get the length of the three arraylists
				int a = listOfLongs.size();
				int b = listOfDoubles.size();
				int c = listOfStrings.size();
				return Math.max(a, Math.max(b, c));	//return the max length
			}

			public Object getValueAt(int row, int column) {	//getValue the data elements
				String value = "";	//initialise value variable

				if (column == 0) {	//if first column
					if (listOfLongs.size() > row) {	//if row is less than the length of the listOfLongs
						value = listOfLongs.get(row).toString();	//value is the string value from the arraylist element
					} else
						value = "";	//otherwise value is null
				}

				else if (column == 1) {	//if second column 
					if (listOfDoubles.size() > row) {   //if row is less than the length of the listOfDoubles
						value = listOfDoubles.get(row).toString();	//value is the string value from the arraylist element
					} else
						value = "";	//otherwise value is null
				}

				else if (column == 2) {	//if third column
					if (listOfStrings.size() > row) {	//if row is less than the length of the listOfStrings
						value = listOfStrings.get(row);	//value is the string value from the arraylist element
					} else
						value = "";	//otherwise value is null
				}

				return value;	//return value from above
			}

			@Override
			public int getColumnCount() {	//method to get the number of columns
				return col.length;	//length of the col array
			}

			public String getColumnName(int column) {	//method for column name
				return col[column];	//return the col elements
			}
		};

		return dataModel;	//return the datamodel
	}

	public JTable createTable() {	//createTable
		table = new JTable(dataModel) {	//table declaration with the data model passed
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				ListSelectionModel rowSelectionModel = table	
						.getSelectionModel();	//create row selection object
				rowSelectionModel
						.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//set it to songle selection

				return false;
			}
		};

		return table;	//return the table

	}

	public JScrollPane createJScrollPane() {	//method to create scroll pane
		JScrollPane tableContainer = new JScrollPane(table);	//create object with table passed
		panel.add(tableContainer, BorderLayout.CENTER);	//add it to the panel

		return tableContainer;	//return the scrollpane
	}

	public JList<String> createJList() {	//method to create jlist
		myList = new JList<String>();	//create list object
		panel.add(myList, BorderLayout.EAST);	//add it to the panel

		return myList;	//return the list
	}

	public JPanel createJPanel() {	//method to create JPanel
		panel = new JPanel();	//create panel object
		panel.setLayout(new GridLayout(1, 2, 7, 5));  //set the layout of the panel

		return panel;	//return the panel object
	}

	public JFrame createJFrame() {	//create the jframe
		frame = new JFrame("Sorted Data");	//create object
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//set the method to close the frame
		frame.getContentPane().add(panel);	//add the panel to the pane
		frame.pack();
		frame.setVisible(true);	//set to be visible

		return frame;
	}

	public void generateListPermutations() {

		table.getSelectionModel().addListSelectionListener(	//add listener to the table
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent e) {	

						ListSelectionModel lsm = (ListSelectionModel) e
								.getSource();	//create list selection model object
						int selectedIndex = lsm.getLeadSelectionIndex();	//get row index

						String col1 = (String) table.getModel().getValueAt(
								selectedIndex, 0); //assign value to variable from column 1 in selected row
						String col2 = (String) table.getModel().getValueAt(
								selectedIndex, 1);  //assign value to variable from column 2 in selected row
						String col3 = (String) table.getModel().getValueAt(
								selectedIndex, 2);  //assign value to variable from column 3 in selected row

						//if all cells in row are not empty
						if ((!col1.equals("")) && (!col2.equals(""))
								&& (!col3.equals(""))) {
							String[] listData = {		//create array
									"" + col1 + ", " + col2 + ", " + col3 + "",
									"" + col1 + ", " + col3 + ", " + col2 + "",
									"" + col3 + ", " + col2 + ", " + col1 + "",
									"" + col3 + ", " + col1 + ", " + col2 + "",
									"" + col2 + ", " + col1 + ", " + col3 + "",
									"" + col2 + ", " + col3 + ", " + col1 + "" };
							myList.setListData(listData);	//set array to the list
						}

						//if all cell 1 is empty
						else if ((col1.equals("")) && (!col2.equals(""))	
								&& (!col3.equals(""))) {
							String[] listData = { "" + col2 + ", " + col3,	//create array
									"" + col3 + ", " + col2, };
							myList.setListData(listData);	//set array to the list
						}

						//if all cell 2 is empty
						else if ((col2.equals("")) && (!col1.equals(""))
								&& (!col3.equals(""))) {
							String[] listData = { "" + col1 + ", " + col3 + "",
									"" + col3 + ", " + col1, };
							myList.setListData(listData);
						}

						//if all cell 3 is empty
						else if ((col3.equals("")) && (!col1.equals(""))
								&& (!col2.equals(""))) {
							String[] listData = { "" + col1 + ", " + col3 + "",
									"" + col1 + ", " + col2,
									"" + col2 + ", " + col1, };
							myList.setListData(listData);
						}

						//if all cell 1 and 2 are empty
						else if ((col1.equals("")) && (col2.equals(""))
								&& (!col3.equals(""))) {
							String[] listData = { "" + col3 };
							myList.setListData(listData);
						}

						//if all cell 1 and 3 are empty
						else if ((col1.equals("")) && (col3.equals(""))
								&& (!col2.equals(""))) {
							String[] listData = { "" + col2 };
							myList.setListData(listData);
						}

						//if all cell 2 and 3 are empty
						else if ((col2.equals("")) && (col3.equals(""))
								&& (!col1.equals(""))) {
							String[] listData = { "" + col1 };
							myList.setListData(listData);
						}
					}
				});
	}
	//end method

}//end class
