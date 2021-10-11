package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class DynamicCombobox {
	/**
	 * Updates specified DefaultComboBoxModel "model" with the query determined by "columns", "tableName", and "conditions".
	 * @param model
	 * @param columns
	 * @param tableName
	 * @param conditions
	 */
	public static void updateComboBox(DefaultComboBoxModel model, JComboBox comboBox, String columns, String tableName, String conditions, Connection con)
	{
		model = (DefaultComboBoxModel) comboBox.getModel();
		model.removeAllElements();

				try {
					ResultSet result = con.createStatement().executeQuery("SELECT "+ columns + " FROM " + tableName + " WHERE "+ conditions);
					ResultSetMetaData meta = result.getMetaData();
					while(result.next())
					{
						String item = "";
						for(int i = 1; i <= meta.getColumnCount(); i++)
						{
							try
							{
								item += result.getString(i);
								if(i < meta.getColumnCount())
								{
									item += " | ";
								}
							}
							catch(NullPointerException n)
							{
								
							}
						}
						model.addElement(item);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				comboBox.setSelectedIndex(-1);
	}
}