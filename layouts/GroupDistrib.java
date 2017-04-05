package layouts;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GroupDistrib extends Distrib {

	public GroupDistrib(String title) {
		super(title);
	}

	@Override
	protected void placeComponents() {
		
		BKey.CHOCO.jbutton()
			.setPreferredSize(BKey.ORANGE.jbutton().getPreferredSize());
		BKey.COFFEE.jbutton()
			.setPreferredSize(BKey.ORANGE.jbutton().getPreferredSize());
		BKey.CHOCO.jbutton()
			.setMinimumSize(BKey.ORANGE.jbutton().getMinimumSize());
		BKey.COFFEE.jbutton()
			.setMinimumSize(BKey.ORANGE.jbutton().getMinimumSize());
		
		JPanel jp = new JPanel();
		GroupLayout gl = new GroupLayout(jp);
		jp.setLayout(gl);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);
		
		gl.setHorizontalGroup(
			gl.createParallelGroup(GroupLayout.Alignment.CENTER, true)
				.addComponent(LKey.BACK.jlabel())
				.addComponent(LKey.CREDIT.jlabel())
				.addGroup(gl.createSequentialGroup()
					.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, true)
						.addGroup(gl.createSequentialGroup()
							.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(BKey.ORANGE.jbutton())
								.addComponent(BKey.CHOCO.jbutton())
								.addComponent(BKey.COFFEE.jbutton())
							)
							.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, true)
								.addComponent(PKey.ORANGE.jlabel())
								.addComponent(PKey.CHOCO.jlabel())
								.addComponent(PKey.COFFEE.jlabel())
							)
						)
						.addGroup(gl.createSequentialGroup()
							.addComponent(LKey.DRINK.jlabel())
							.addComponent(FKey.DRINK.jtextfield())
						)
					)
					.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, true)
						.addGroup(gl.createSequentialGroup()
							.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(BKey.INS.jbutton())
								.addComponent(BKey.CANCEL.jbutton())
							)
							.addComponent(FKey.INS.jtextfield())
							.addComponent(PKey.INS.jlabel())
						)
						.addGroup(gl.createSequentialGroup()
							.addComponent(LKey.MONEY.jlabel())
							.addComponent(FKey.MONEY.jtextfield())
							.addComponent(PKey.MONEY.jlabel())
						)
					)
				)
				.addComponent(BKey.TAKE.jbutton())
		);
		
		gl.setVerticalGroup(
			gl.createSequentialGroup()
				.addComponent(LKey.BACK.jlabel())
				.addComponent(LKey.CREDIT.jlabel())
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false)
					.addGroup(gl.createSequentialGroup()
						.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
							.addComponent(BKey.ORANGE.jbutton())
							.addComponent(PKey.ORANGE.jlabel())
						)
						.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
							.addComponent(BKey.CHOCO.jbutton())
							.addComponent(PKey.CHOCO.jlabel())
						)
						.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
							.addComponent(BKey.COFFEE.jbutton())
							.addComponent(PKey.COFFEE.jlabel())
						)
					)
					.addGroup(gl.createSequentialGroup()
						.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
							.addComponent(BKey.INS.jbutton())
							.addComponent(FKey.INS.jtextfield())
							.addComponent(PKey.INS.jlabel())
						)
						.addComponent(BKey.CANCEL.jbutton())
					)
				)
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, true)
					.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
						.addComponent(LKey.DRINK.jlabel())
						.addComponent(FKey.DRINK.jtextfield())
					)
					.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
						.addComponent(LKey.MONEY.jlabel())
						.addComponent(FKey.MONEY.jtextfield())
						.addComponent(PKey.MONEY.jlabel())
					)
				)
				.addComponent(BKey.TAKE.jbutton())
		);
		
		getFrame().add(jp);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				(new GroupDistrib("GroupDistributor")).display();
			}
			
		});
	}

}
