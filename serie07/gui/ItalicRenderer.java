package serie07.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import serie07.model.filters.Filterable;
import serie07.model.gui.MarkableFilterableListModel;

public class ItalicRenderer<E extends Filterable<V>, V> 
	implements ListCellRenderer<E> {

	private Font italic;
	private Font origin;
	private DefaultListCellRenderer delegate;
	
	public ItalicRenderer() {
		delegate = new DefaultListCellRenderer();
		origin = delegate.getFont();
		italic = new Font(origin.getName(), Font.ITALIC, origin.getSize());
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends E> list,
			E value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component result =
				delegate.getListCellRendererComponent(list,
						value, 
						index, 
						isSelected,
						cellHasFocus);
		MarkableFilterableListModel model = 
				(MarkableFilterableListModel) list.getModel();
		if (model.isMarked(value)) {
			result.setFont(italic);
		} else {
			result.setFont(origin);
		}
		return result;
	}

}
