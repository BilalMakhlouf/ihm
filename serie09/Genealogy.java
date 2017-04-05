package serie09;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class Genealogy {
	
	private JTree tree;
	
	private DefaultMutableTreeNode dmtn;
	
	private JFrame frame;
	
	public Genealogy() {
		createModel();
		createView();
		placeComponents();
		createController();
	}
	
	private void createModel() {
//		dmtn = new DefaultMutableTreeNode();
//		dmtn.setUserObject(new Person(Gender.FEMALE));
//		int gd = 0;
//		for (int i = 0; i < 40; i++) {
//			DefaultMutableTreeNode rac = new DefaultMutableTreeNode(
//					new Person(
//							Gender.values()[(gd++) % Gender.values().length]));
//			for (int j = 0; j < 3; j++) {
//				rac.add(new DefaultMutableTreeNode(new Person(
//						Gender.values()[(gd++) % Gender.values().length])));
//			}
//			dmtn.add(rac);
//		}
	}
	
	private void createView() {
		frame = new JFrame("LA GENEALOGIE C'EST KOOL");
		tree = new GenTree();
		
		tree.setPreferredSize(new Dimension(400, 
				(int) tree.getPreferredSize().getHeight()));
	}
	
	private void placeComponents() {
		frame.add(new JScrollPane(tree));
	}
	
	private void createController() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				TreePath tp = arg0.getPath();
				updateTitle(tp);
			}
			
		});
		
		tree.getModel().addTreeModelListener(new TreeModelListener() {

			@Override
			public void treeNodesChanged(TreeModelEvent arg0) {
				updateTitle(tree.getSelectionPath());
			}

			@Override
			public void treeNodesInserted(TreeModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void treeNodesRemoved(TreeModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void treeStructureChanged(TreeModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private void display() {
		frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	private void updateTitle(TreePath tp) {
		String res = "";
		Object[] nodes = tp.getPath();
		for (int i = nodes.length - 1; i > 0; i--) {
			Person pers = 
					(Person) 
							((DefaultMutableTreeNode) nodes[i])
							.getUserObject();
			res += pers.getName() + ", " + pers.getGender().getDesc() + " de ";
		}
		Person pers = 
				(Person) ((DefaultMutableTreeNode) nodes[0])
						.getUserObject();
		res += pers.getName();
		frame.setTitle(res);
	}
	
	public static void main(String[] args) {
		final Genealogy gen = new Genealogy();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gen.display();
			}
		});
	}
	
}
