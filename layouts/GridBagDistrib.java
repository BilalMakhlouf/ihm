package layouts;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GridBagDistrib extends Distrib {

	public GridBagDistrib(String title) {
		super(title);
	}

	@Override
	protected void placeComponents() {
		JPanel jp = new JPanel(new GridBagLayout());
		
		GBC constraints;
		
		constraints = new GBC(1, 0, 5, 1);
		constraints.anchor(GBC.CENTER);
		constraints.insets(5, 0, 5, 0);
		jp.add(LKey.BACK.jlabel(), constraints);
		
		constraints = new GBC(1, 1, 5, 1);
		constraints.anchor(GBC.CENTER);
		constraints.insets(0, 0, 5, 5);
		jp.add(LKey.CREDIT.jlabel(), constraints);
		
		constraints = new GBC(0, 2, 2, 2);
		constraints.fill(GBC.HORIZONTAL);
		constraints.insets(0, 10, 2, 5);
		jp.add(BKey.ORANGE.jbutton(), constraints);
		
		constraints = new GBC(2, 2, 1, 2);
		constraints.anchor(GBC.WEST);
		constraints.insets(0, 5, 2, 5);
		jp.add(PKey.ORANGE.jlabel(), constraints);
		
		constraints = new GBC(0, 4, 2, 2);
		constraints.fill(GBC.HORIZONTAL);
		constraints.insets(0, 10, 2, 5);
		jp.add(BKey.CHOCO.jbutton(), constraints);
		
		constraints = new GBC(2, 4, 1, 2);
		constraints.anchor(GBC.WEST);
		constraints.insets(0, 5, 2, 5);
		jp.add(PKey.CHOCO.jlabel(), constraints);
		
		constraints = new GBC(0, 6, 2, 2);
		constraints.fill(GBC.HORIZONTAL);
		constraints.insets(0, 10, 2, 5);
		jp.add(BKey.COFFEE.jbutton(), constraints);
		
		constraints = new GBC(2, 6, 1, 2);
		constraints.anchor(GBC.WEST);
		constraints.insets(0, 5, 2, 5);
		jp.add(PKey.COFFEE.jlabel(), constraints);
		
		constraints = new GBC(3, 3, 2, 2);
		constraints.insets(2);
		jp.add(BKey.INS.jbutton(), constraints);
		
		constraints = new GBC(5, 3, 1, 2);
		constraints.fill(GBC.HORIZONTAL);
		constraints.weight(1, 0);
		jp.add(FKey.INS.jtextfield(), constraints);
		
		constraints = new GBC(6, 3, 1, 2);
		constraints.insets(2);
		jp.add(PKey.INS.jlabel(), constraints);
		
		constraints = new GBC(3, 5, 2, 2);
		constraints.insets(2);
		jp.add(BKey.CANCEL.jbutton(), constraints);
		
		constraints = new GBC(0, 8, 1, 1);
		constraints.insets(10);
		jp.add(LKey.DRINK.jlabel(), constraints);
		
		constraints = new GBC(1, 8, 2, 1);
		constraints.fill(GBC.HORIZONTAL);
		constraints.weight(1, 0);
		jp.add(FKey.DRINK.jtextfield(), constraints);
		
		constraints = new GBC(3, 8, 1, 1);
		constraints.insets(10);
		jp.add(LKey.MONEY.jlabel(), constraints);
		
		constraints = new GBC(4, 8, 2, 1);
		constraints.fill(GBC.HORIZONTAL);
		constraints.weight(1, 0);
		jp.add(FKey.MONEY.jtextfield(), constraints);
		
		constraints = new GBC(6, 8, 1, 1);
		constraints.insets(5);
		jp.add(PKey.MONEY.jlabel(), constraints);
		
		constraints = new GBC(1, 9, 5, 1);
		constraints.anchor(GBC.CENTER);
		jp.add(BKey.TAKE.jbutton(), constraints);
		
		constraints = new GBC(0, 10, 9, 0);
		constraints.weight(1, 1);
		jp.add(new JLabel(), constraints);
		
		constraints = new GBC(3, 2, 4, 1);
		constraints.ipad(0, 15);
		jp.add(new JLabel(), constraints);
		
		getFrame().add(jp, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				(new GridBagDistrib("GridBagDistributor")).display();
			}
			
		});
	}

}
