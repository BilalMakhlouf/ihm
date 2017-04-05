package serie07.model.application;

import java.util.List;

import javax.swing.SwingUtilities;

import serie07.event.ResourceLoaderEvent;
import serie07.event.ResourceLoaderListener;
import serie07.event.ResourceLoaderSupport;
import serie07.model.filters.Filter;
import serie07.model.filters.Filterable;
import serie07.model.technical.ResourceLoader;

public class FilteringModel<E extends Filterable<V>, V> {
    
    // ATTRIBUTS
    
    private final ResourceLoaderSupport<E> resLoadSup;
    private final ResourceLoader<E> loader;
    private final List<Filter<E, V>> filters;

    // CONSTRUCTEURS
    
    public FilteringModel(List<Filter<E, V>> filters, ResourceLoader<E> rl) {
        this.filters = filters;
        loader = rl;
        loader.addResourceLoaderListener(new ResourceLoaderListener<E>() {
            public void resourceLoaded(final ResourceLoaderEvent<E> e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        resLoadSup.fireResourceLoaded(e.getResource());
                    }
                });
            }
        });
        resLoadSup = new ResourceLoaderSupport<E>(this);
    }
    
    // REQUETES
    
    public List<Filter<E, V>> getFilters() {
        return filters;
    }

    // COMMANDES
    
    public void addResourceLoaderListener(ResourceLoaderListener<E> lst) {
        resLoadSup.addResourceLoaderListener(lst);
    }
    
    public void removeResourceLoaderListener(ResourceLoaderListener<E> lst) {
        resLoadSup.removeResourceLoaderListener(lst);
    }
    
    public void populate() {
        Thread worker = new Thread(new Runnable() {
            public void run() {
                try {
                    loader.loadResource();
                } catch (Exception e) {
                    // TODO: il faudrait faire mieux que ça...
                    System.err.println(e.getMessage());
                }
            }
        });
        worker.start();
    }
}
