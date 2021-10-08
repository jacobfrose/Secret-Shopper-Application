package common;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FormatedTable extends JTable{
	final Color cardinal = new Color(200,16,46);
	final Color dark_cardinal = new Color(124, 37, 41);
	final Color gold = new Color(241, 190, 72);
	public FormatedTable()
	{
		DefaultTableModel tableModel = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		setModel(tableModel);
		setBackground(Color.white);
		setForeground(Color.black);
		setFont(new Font("Arial", Font.PLAIN, 11));
		getTableHeader().setOpaque(false);
		getTableHeader().setBackground(cardinal);
		getTableHeader().setForeground(gold);
		getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		getTableHeader().setReorderingAllowed(false);
		setEditingRow(0);
		setCellSelectionEnabled(true);
	}

}
