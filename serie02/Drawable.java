package serie02;

import java.awt.Graphics;

public interface Drawable {
    
    /**
     * La largeur (en pixels) d'un élément.
     */
    int ELEM_WIDTH = 40;

    /**
     * La hauteur (en pixels) d'un élément.
     */
    int ELEM_HEIGHT = 40;

    /**
     * L'élément se dessine sur g à partir de (x,y).
     * @pre <pre>
     *     g != null </pre>
     * @post <pre>
     *     L'élément s'est dessiné sur g dans un rectangle de csg (x, y), de
     *         largeur ELEM_WIDTH et de hauteur ELEM_HEIGHT </pre>
     */
    void draw(Graphics g, int x, int y);
}
