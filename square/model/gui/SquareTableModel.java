package square.model.gui;

import javax.swing.table.AbstractTableModel;

import square.model.appli.ISquareGameModel;
import square.model.appli.SquareGameModel;
import square.util.Coord;
import square.util.Player;

public class SquareTableModel extends AbstractTableModel {
	
	private ISquareGameModel model;
	
	public SquareTableModel(ISquareGameModel sqm) {
		model = sqm;
	}
	
	@Override
	public Class<?> getColumnClass(int i) {
		return Player.class;
	}
	
	@Override
	public int getColumnCount() {
		return model.size();
	}

	@Override
	public int getRowCount() {
		return model.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return model.getPlayerAt(new Coord(arg0, arg1));
	}

}
