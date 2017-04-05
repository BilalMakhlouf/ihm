package serie07;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import serie07.event.ResourceLoaderEvent;
import serie07.event.ResourceLoaderListener;
import serie07.model.application.FilteringModel;
import serie07.model.filters.Factor;
import serie07.model.filters.Filter;
import serie07.model.filters.Prefix;
import serie07.model.filters.RegExp;
import serie07.model.filters.Suffix;
import serie07.model.technical.StringValuedByTextLoader;
import serie07.util.StringTranslator;
import serie07.util.StringValuedByText;

public final class FilteringLinesByTextTester
        extends FilteringTester<StringValuedByText, String> {
    
    // ATTRIBUTS
    
    private static final File INPUT_FILE =
            getInputResource("data/text/contenu.txt");
    private static final List<Filter<StringValuedByText, String>> FILTERS;
    static {
        FILTERS = new ArrayList<Filter<StringValuedByText, String>>();
        FILTERS.add(new Prefix<StringValuedByText>());
        FILTERS.add(new Factor<StringValuedByText>());
        FILTERS.add(new Suffix<StringValuedByText>());
        FILTERS.add(new RegExp<StringValuedByText>());
    }

    // CONSTRUCTEURS
    
    public FilteringLinesByTextTester() {
        super(new StringTranslator());
    }

    // OUTILS
    
    @Override
    protected FilteringModel<StringValuedByText, String> createModel() {
        return new FilteringModel<StringValuedByText, String>(
                FILTERS, new StringValuedByTextLoader(INPUT_FILE));
    }
    
    @Override
    protected ResourceLoaderListener<StringValuedByText>
            createResourceLoaderListener() {
        return new ResourceLoaderListener<StringValuedByText>() {
            public void resourceLoaded(
                    ResourceLoaderEvent<StringValuedByText> e) {
                getFilterablePane().addElement(e.getResource());
            }
        };
    }

    // POINTS D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    new FilteringLinesByTextTester().display();
                }
            }
        );
    }
}
