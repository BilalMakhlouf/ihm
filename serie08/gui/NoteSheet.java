package serie08.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import serie08.model.application.DefaultNoteSheetModel;
import serie08.model.application.NoteSheetModel;
import serie08.model.gui.DefaultNoteTableModel;
import serie08.model.gui.NoteTableModel;
import square.util.Contract;

public class NoteSheet extends JPanel {
	
	private EventListenerList evlist;
	private NoteTableModel guiModel;
	private JScrollPane jsp;
	private JTable table;
	private NoteSheetModel appliModel;
	private JPopupMenu emptyPop;
	private JPopupMenu notEmptyPop;
	
	private int progress;
	
	public NoteSheet() {
		progress = 0;
		evlist = new EventListenerList();
		
		guiModel = new DefaultNoteTableModel();
		appliModel = new DefaultNoteSheetModel();
		
		placeComponents();
		
		popupMenus();
		
		createController();
		
	}
	
	private void placeComponents() {
		table = new JTable(guiModel);
		jsp = new JScrollPane(table);
		jsp.setRowHeaderView(table.getTableHeader());
		this.add(jsp);
	}
	
	private void popupMenus() {
		emptyPop = new JPopupMenu();
		notEmptyPop = new JPopupMenu();
		ActionListener emptyMenuListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				guiModel.addRow(guiModel.getEmptyDataRow());
			}
		};
		
		JMenuItem item;
		item = new JMenuItem("insert row");
		emptyPop.add(item);
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(emptyMenuListener);
		
		item = new JMenuItem("Insérer une ligne avant");
		notEmptyPop.add(item);
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				guiModel.insertRow(
						guiModel.getEmptyDataRow(), table.getSelectedRow());
			}
		});
		
		item = new JMenuItem("Insérer une ligne après");
		notEmptyPop.add(item);
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				guiModel.insertRow(
						guiModel.getEmptyDataRow(), table.getSelectedRow() + 1);
			}
		});
		
		item = new JMenuItem("Insérer une ligne à la fin");
		notEmptyPop.add(item);
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				guiModel.insertRow(
						guiModel.getEmptyDataRow(), guiModel.getRowCount());
				jsp.scrollRectToVisible(new Rectangle(table.getCellRect(guiModel.getRowCount() - 1, guiModel.getColumnCount() - 1, false)));
			}
		});
		
		item = new JMenuItem("Supprimer la ligne");
		notEmptyPop.add(item);
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				guiModel.removeRow(table.getSelectedRow());
			}
		});
		
		item = new JMenuItem("Supprimer toutes les lignes");
		notEmptyPop.add(item);
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				guiModel.clearRows();
			}
		});
	
	}
	
	private void createController() {
		
		PropertyChangeListener pcl = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				progress = (Integer) arg0.getNewValue();
				ChangeListener[] list = 
						evlist.getListeners(ChangeListener.class);
				for (int i = 0; i < list.length; i++) {
					list[i].stateChanged(new ChangeEvent(this));
				}
			}
			
		};
		
		PropertyChangeListener rcl = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				guiModel.addRow((List<Object>) arg0.getNewValue());
			}
		};
		
		PropertyChangeListener scl = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				JOptionPane.showMessageDialog(null, "Sauvegarde réussie !");
			}
		};
		
		PropertyChangeListener ecl = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				JOptionPane.showMessageDialog(null,
						arg0.getNewValue().toString());
			}
		};
		
		appliModel.addPropertyChangeListener("progress", pcl);
		
		appliModel.addPropertyChangeListener("row", rcl);
		
		appliModel.addPropertyChangeListener("saved", scl);
		
		appliModel.addPropertyChangeListener("error", ecl);
		
		guiModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
				if (guiModel.getRowCount() == 0) {
					//ATTACHER LISTENER AU SCROLL PANE
					jsp.setComponentPopupMenu(emptyPop);
				} else {
					//DETACHER LISTENER DU SCROLL PANE
					jsp.setComponentPopupMenu(null);
				}
			}
			
		});
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
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
				if (SwingUtilities.isRightMouseButton(e)) {
					final int row = table.rowAtPoint(e.getPoint());
					table.setRowSelectionInterval(row, row);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		table.setComponentPopupMenu(notEmptyPop);
	}
	
	public NoteTableModel getTableModel() {
		return guiModel;
	}
	
	public double getMean() {
		return appliModel.getMean(guiModel);
	}
	
	public double getPoints() {
		return appliModel.getPoints(guiModel);
	}
	
	public int getProgress() {
		return progress;
	}
	
	public void loadFile(File f) {
		guiModel.clearRows();
		appliModel.loadFile(f);
	}
	
	public void saveFile(File f) {
		appliModel.saveFile(f, guiModel);
	}
	
	public void addProgressListener(ChangeListener cl) {
		Contract.checkCondition(cl != null);
		evlist.add(ChangeListener.class, cl);
	}
	
	public void removeProgressListener(ChangeListener cl) {
		evlist.remove(ChangeListener.class, cl);
	}

}
