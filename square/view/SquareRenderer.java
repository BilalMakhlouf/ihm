package square.view;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import square.util.Player;

public class SquareRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object arg1,
			boolean arg2, boolean arg3, int arg4,
			int arg5) {
		TableCellRenderer tcr = table.getDefaultRenderer(Object.class);
		Component res =
				tcr.getTableCellRendererComponent(table, null, arg2, arg3, 
						arg4, arg5);
		res.setBackground(GameColor.forPlayer((Player) arg1));
		return res;
	}

}
