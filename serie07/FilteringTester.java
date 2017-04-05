package serie07;

import java.awt.BorderLayout;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;

import serie07.event.ResourceLoaderListener;
import serie07.gui.FilterablePane;
import serie07.model.application.FilteringModel;
import serie07.model.filters.Filterable;
import serie07.util.ValueTranslator;

public abstract class FilteringTester<E extends Filterable<V>, V> {
   
    // ATTRIBUTS
    
    private JFrame frame;
    private FilterablePane<E, V> filterablePane;
    private FilteringModel<E, V> model;

    // CONSTRUCTEURS
    
    public FilteringTester(ValueTranslator<V> vt) {
        model = createModel();
        createView(vt);
        placeComponents();
        createController();
    }
    
    // COMMANDES
    
    public void display() {
        filterablePane.setFilters(model.getFilters());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        model.populate();
    }
    
    // REQUETES
    
    protected final FilterablePane<E, V> getFilterablePane() {
        return filterablePane;
    }
    
    // OUTILS
    
    protected abstract FilteringModel<E, V> createModel();
    
    protected abstract ResourceLoaderListener<E> createResourceLoaderListener();

    private void createView(ValueTranslator<V> vt) {
        frame = new JFrame("Filterable List");
        filterablePane = new FilterablePane<E, V>(vt);
    }
    
    private void placeComponents() {
        frame.add(filterablePane, BorderLayout.CENTER);
    }
    
    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model.addResourceLoaderListener(createResourceLoaderListener());
    }
    
    protected static File getInputResource(String relativePath) {
        URL url = FilteringTester.class.getResource(relativePath);
        if (url == null) {
            throw new IllegalArgumentException("Aucune ressource de nom : "
                    + relativePath);
        }
        URI uri = null;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new InternalError("l'URL " + url + " n'est pas strictement"
                    + " formatée selon la norme RFC2396 et ne peut donc pas"
                    + " être convertie en URI");
        }
        return new File(uri.getPath());
    }
}
