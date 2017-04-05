package serie09;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class GenCellRenderer extends DefaultTreeCellRenderer {
	
	public static String FONT_NAME = "Verdana";
	public static int FONT_SIZE = 20;
	
	public GenCellRenderer() {
		setTextSelectionColor(Color.white);
		this.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
	}
	public Component getTreeCellRendererComponent(
			JTree tree, Object value, boolean sel,
			boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		Person pers = (Person) ((DefaultMutableTreeNode) value).getUserObject();
		if (leaf) {
			super.setLeafIcon(pers.getGender().getImage());
		} else if (expanded) {
			super.setOpenIcon(pers.getGender().getImage());
		} else {
			super.setClosedIcon(pers.getGender().getImage());
		}
		Component cp = 
				super.getTreeCellRendererComponent(tree, value, sel, 
						expanded, leaf, row, hasFocus);
		this.setBackgroundSelectionColor(
				pers.getGender().getBackgroundSelectionColor());
		this.setBorderSelectionColor(
				pers.getGender().getBorderSelectionColor());
		return cp;
		
	}
	
}
