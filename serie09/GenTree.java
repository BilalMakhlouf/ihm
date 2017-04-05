package serie09;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class GenTree extends JTree {
	
	private JPopupMenu jpm;
	private JMenuItem rootItem;
	private JMenuItem brothBeforeItem;
	private JMenuItem brothAfterItem;
	private JMenuItem newSonItem;
	private JMenuItem deleteItem;
	
	
	public GenTree() {
		super(null, false);
		GenCellRenderer gcr = new GenCellRenderer();
		this.setCellRenderer(gcr);
		this.setEditable(true);
		this.setCellEditor(
				new DefaultTreeCellEditor2(this, 
						gcr, new GenCellEditor()));
		handlePopup();
		this.setComponentPopupMenu(jpm);
	}
	
	private void handlePopup() {
		jpm = new JPopupMenu();
		rootItem = new JMenuItem("Créer une racine");
		jpm.add(rootItem);
		rootItem.setHorizontalTextPosition(JMenuItem.RIGHT);
		rootItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (getModel().getRoot() == null) {
					DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode();
					dmtn.setUserObject(new Person(Gender.MALE));
//					setModel(new DefaultTreeModel(dmtn));
					DefaultTreeModel dtm = (DefaultTreeModel) getModel();
					dtm.setRoot(dmtn);
				}
			}
		});
		
		brothBeforeItem = new JMenuItem("Ajouter un frère avant");
		jpm.add(brothBeforeItem);
		brothBeforeItem.setHorizontalTextPosition(JMenuItem.RIGHT);
		brothBeforeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Object[] sel = getSelectionPath().getPath();
				DefaultMutableTreeNode dmtn = 
						(DefaultMutableTreeNode) sel[sel.length - 1];
				DefaultMutableTreeNode child =
						new DefaultMutableTreeNode(new Person(Gender.MALE));
				DefaultMutableTreeNode parent = 
						(DefaultMutableTreeNode) dmtn.getParent();
				((DefaultTreeModel) getModel())
					.insertNodeInto(child, parent, parent.getIndex(dmtn));
			}
		});
		
		brothAfterItem = new JMenuItem("Ajouter un frère après");
		jpm.add(brothAfterItem);
		brothAfterItem.setHorizontalTextPosition(JMenuItem.RIGHT);
		brothAfterItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Object[] sel = getSelectionPath().getPath();
				DefaultMutableTreeNode dmtn = 
						(DefaultMutableTreeNode) sel[sel.length - 1];
				DefaultMutableTreeNode child =
						new DefaultMutableTreeNode(new Person(Gender.MALE));
				DefaultMutableTreeNode parent = 
						(DefaultMutableTreeNode) dmtn.getParent();
				((DefaultTreeModel) getModel())
					.insertNodeInto(child, parent, parent.getIndex(dmtn) + 1);
			}
		});
		
		newSonItem = new JMenuItem("Ajouter un fils");
		jpm.add(newSonItem);
		newSonItem.setHorizontalTextPosition(JMenuItem.RIGHT);
		newSonItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Object[] sel = getSelectionPath().getPath();
				DefaultMutableTreeNode dmtn = 
						(DefaultMutableTreeNode) sel[sel.length - 1];
				DefaultMutableTreeNode child =
						new DefaultMutableTreeNode(new Person(Gender.MALE));
				((DefaultTreeModel) getModel())
					.insertNodeInto(child, dmtn, dmtn.getChildCount());
			}
		});
		
		deleteItem = new JMenuItem("Supprimer la selection");
		jpm.add(deleteItem);
		deleteItem.setHorizontalTextPosition(JMenuItem.RIGHT);
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Object[] sel = getSelectionPath().getPath();
				DefaultMutableTreeNode dmtn = 
						(DefaultMutableTreeNode) sel[sel.length - 1];
				DefaultMutableTreeNode parent = 
						(DefaultMutableTreeNode) dmtn.getParent();
				((DefaultTreeModel) getModel()).removeNodeFromParent(dmtn);
			}
		});
		
		jpm.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				//RIEN
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				//RIEN
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				checkMenu();
			}
			
		});
	}
	
	private void checkMenu() {
		if (this.getModel().getRoot() == null) {
			rootItem.setEnabled(true);
			brothBeforeItem.setEnabled(false);
			brothAfterItem.setEnabled(false);
			newSonItem.setEnabled(false);
			deleteItem.setEnabled(false);
		} else {
			if (((DefaultMutableTreeNode) 
					this.getSelectionPath().getLastPathComponent()) 
					== this.getModel().getRoot()) {
				rootItem.setEnabled(false);
				brothBeforeItem.setEnabled(false);
				brothAfterItem.setEnabled(false);
				newSonItem.setEnabled(true);
				deleteItem.setEnabled(true);
			} else {
				rootItem.setEnabled(false);
				brothBeforeItem.setEnabled(true);
				brothAfterItem.setEnabled(true);
				newSonItem.setEnabled(true);
				deleteItem.setEnabled(true);
			}
		}
	}
	
}
