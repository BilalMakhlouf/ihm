package serie02;

import java.awt.Color;
import java.awt.Graphics;

public enum AnimalColor implements Drawable {
    LION(Color.ORANGE), ELEPHANT(Color.GRAY), BEAR(Color.WHITE),
    MOUSE(Color.GREEN), BULL(Color.RED), PANTHER(Color.PINK),
    TIGER(Color.CYAN), HORSE(Color.YELLOW), COW(Color.MAGENTA),
    CAT(Color.BLUE);
    
    private Color color;
    
    private AnimalColor(Color c) {
        color = c;
    }

    public void draw(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, ELEM_WIDTH, ELEM_HEIGHT);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, ELEM_WIDTH - 1, ELEM_HEIGHT - 1);
    }
}
