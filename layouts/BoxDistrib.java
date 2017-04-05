package layouts;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class BoxDistrib extends Distrib {
	
	public BoxDistrib(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void placeComponents() {
		
		for (FKey fk : FKey.values()) {
			fk.jtextfield().setMaximumSize(
					new Dimension(Integer.MAX_VALUE, 
							fk.jtextfield().getPreferredSize().height));
		}
		
		Box box = new Box(BoxLayout.Y_AXIS); {
			box.add(Box.createVerticalStrut(10));
			LKey.BACK.jlabel().setAlignmentX(0.5f);
			LKey.CREDIT.jlabel().setAlignmentX(0.5f);
			box.add(LKey.BACK.jlabel());
			box.add(LKey.CREDIT.jlabel());
			box.add(Box.createVerticalStrut(10));
			
			Box b2 = new Box(BoxLayout.X_AXIS); {
				b2.setAlignmentX(0.5f);
				
				b2.add(Box.createHorizontalStrut(5));
				
				Box b3 = new Box(BoxLayout.Y_AXIS); {
					Box b4 = new Box(BoxLayout.X_AXIS); {
						BKey.ORANGE.jbutton().setMaximumSize(
								BKey.ORANGE.jbutton().getPreferredSize());
						BKey.ORANGE.jbutton().setAlignmentX(0f);
						b4.add(BKey.ORANGE.jbutton());
						b4.add(Box.createHorizontalStrut(10));
						PKey.ORANGE.jlabel().setAlignmentX(0f);
						b4.add(PKey.ORANGE.jlabel());
						b4.add(Box.createHorizontalGlue());
					}
					
					b3.add(b4);
					
					b3.add(Box.createVerticalStrut(5));
					
					b4 = new Box(BoxLayout.X_AXIS); {
						BKey.CHOCO.jbutton().setPreferredSize(
								BKey.ORANGE.jbutton().getPreferredSize());

						BKey.CHOCO.jbutton().setMaximumSize(
								BKey.ORANGE.jbutton().getPreferredSize());
						BKey.CHOCO.jbutton().setAlignmentX(0f);
						b4.add(BKey.CHOCO.jbutton());
						b4.add(Box.createHorizontalStrut(10));
						PKey.CHOCO.jlabel().setAlignmentX(0f);
						b4.add(PKey.CHOCO.jlabel());
						b4.add(Box.createHorizontalGlue());
					}
					
					b3.add(b4);
					
					b3.add(Box.createVerticalStrut(5));
					
					b4 = new Box(BoxLayout.X_AXIS); {
						BKey.COFFEE.jbutton().setPreferredSize(
								BKey.ORANGE.jbutton().getPreferredSize());
						BKey.COFFEE.jbutton().setMaximumSize(
								BKey.ORANGE.jbutton().getPreferredSize());
						BKey.COFFEE.jbutton().setAlignmentX(0f);
						b4.add(BKey.COFFEE.jbutton());
						b4.add(Box.createHorizontalStrut(10));
						PKey.COFFEE.jlabel().setAlignmentX(0f);
						b4.add(PKey.COFFEE.jlabel());
						b4.add(Box.createHorizontalGlue());
					}
					
					b3.add(b4);
					
					b3.add(Box.createVerticalStrut(5));
					
					b4 = new Box(BoxLayout.X_AXIS); {
						b4.add(LKey.DRINK.jlabel());
						b4.add(Box.createHorizontalStrut(5));
						b4.add(FKey.DRINK.jtextfield());
					}
					
					b3.add(b4);
					
					b3.add(Box.createVerticalStrut(5));
				}
				
				b2.add(b3);
				
				b2.add(Box.createHorizontalStrut(10));
				
				b3 = new Box(BoxLayout.Y_AXIS); {
					
					b3.add(Box.createVerticalStrut(12));
					
					Box b4 = new Box(BoxLayout.X_AXIS); {
						BKey.INS.jbutton().setPreferredSize(
								BKey.CANCEL.jbutton().getPreferredSize());
						BKey.INS.jbutton().setMaximumSize(
								BKey.CANCEL.jbutton().getPreferredSize());
						b4.add(BKey.INS.jbutton());
						b4.add(Box.createRigidArea(new Dimension(
								5, BKey.INS.jbutton().getPreferredSize().height
								)));
						b4.add(FKey.INS.jtextfield());
						b4.add(Box.createRigidArea(new Dimension(
								5, BKey.INS.jbutton().getPreferredSize().height
								)));
						b4.add(PKey.INS.jlabel());
					}
					
					b3.add(b4);
					
					b3.add(Box.createVerticalStrut(5));
					
					b4 = new Box(BoxLayout.X_AXIS); {
						b4.add(BKey.CANCEL.jbutton());
						b4.add(Box.createHorizontalGlue());
					}
					
					b3.add(b4);
					
					b3.add(Box.createVerticalStrut(25));
					
					b4 = new Box(BoxLayout.X_AXIS); {
						b4.add(LKey.MONEY.jlabel());
						b4.add(Box.createHorizontalStrut(5));
						b4.add(FKey.MONEY.jtextfield());
						b4.add(Box.createHorizontalStrut(5));
						b4.add(PKey.MONEY.jlabel());
					}
					
					b3.add(b4);
				}
				
				b2.add(b3);
				

				b2.add(Box.createHorizontalStrut(10));
			}
			
			box.add(b2);
			
			BKey.TAKE.jbutton().setAlignmentX(0.5f);
			
			box.add(BKey.TAKE.jbutton());
		
			box.add(Box.createVerticalStrut(10));
		}
		
		this.getFrame().add(box, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				(new BoxDistrib("BoxDistributor")).display();
			}
			
		});
	}

}
