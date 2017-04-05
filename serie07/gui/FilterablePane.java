package serie07.gui;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import serie07.model.filters.Filter;
import serie07.model.filters.Filterable;
import serie07.model.gui.FilterableListModel;
import serie07.model.gui.MarkableFilterableListModel;
import serie07.util.ValueTranslator;

public class FilterablePane<E extends Filterable<V>, V> extends JPanel {
	
	private JTextField filterText;
	private JList<E> filterableList;
	private ValueTranslator<V> translator;
	private JComboBox filterTypes;
	private ItalicRenderer itr;
	private MarkableFilterableListModel<E, V> model;

	public FilterablePane(ValueTranslator<V> v) {
		translator = v;
		
		createModel();
		createView();
		createController();
		
	}
	
	
	private void createModel() {
		model = new MarkableFilterableListModel<E, V>(null);
		filterableList = new JList<E>(model);
		filterText = new JTextField();
		filterTypes = new JComboBox();
		itr = new ItalicRenderer();
		filterableList.setCellRenderer(itr);
	}
	
	private void createView() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(filterableList), BorderLayout.CENTER);
		
		JPanel jp = new JPanel(new BorderLayout()); {
			jp.add(filterTypes, BorderLayout.WEST);
			jp.add(filterText, BorderLayout.CENTER);
		}
		
		this.add(jp, BorderLayout.NORTH);
	}
	
	private void createController() {
		this.filterTypes.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Filter<E, V> f = ((Filter<E, V>) filterTypes.getSelectedItem());
				V v = translator.translateText(filterText.getText());
				f.setValue(v);
				model.setFilter(f);
			}
			
		});
		
		this.filterText.getDocument()
		.addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				Filter<E, V> f = 
						model.getFilter();
				try {
					String txt = filterText.getDocument()
					.getText(0, filterText.getDocument().getLength());
					V v = translator.translateText(txt);
					f.setValue(v);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.filterableList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					int index = filterableList.locationToIndex(arg0.getPoint());
					model.toggleMark(model.getElementAt(index), index);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public void addElement(E resource) {
		model.addElement(resource);
	}
	
	public void setFilters(List<Filter<E, V>> filters) {
		filterTypes.removeAllItems();
		for (Filter<E, V> f : filters) {
			filterTypes.addItem(f);
		}
	}
	
}
