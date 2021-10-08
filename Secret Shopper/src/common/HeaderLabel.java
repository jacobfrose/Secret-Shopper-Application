package common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HeaderLabel extends JLabel {
	public HeaderLabel(Dimension dim, String text, Font font, Color color, ImageIcon icon)
	{
		setPreferredSize(dim);
		setText(text);
		setFont(font);
		setForeground(color);
		setIcon(icon);
	}
	public HeaderLabel(String text, Font font, Color color)
	{
		setText(text);
		setFont(font);
		setForeground(color);
	}
}
