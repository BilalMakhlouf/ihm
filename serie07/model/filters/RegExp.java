package serie07.model.filters;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import util.Contract;


/**
 * Implantation du filtre (basé sur des valeurs de type String) :
 *   "getValue() est une regexp reconnaissant la valeur filtrable associée à e".
 * @inv <pre>
 *     forall e:E :
 *         isValid(e) ==
 *             l'automate associé à getValue()
 *             reconnaît e.filtrableValue() </pre>
 */
public class RegExp<E extends Filterable<String>>
        extends AbstractFilter<E, String> implements Filter<E, String> {
    
    // ATTRIBUTS
    
    private static final Pattern EMPTY = Pattern.compile("");
    private Pattern pattern;

    // CONSTRUCTEURS
    
    public RegExp() {
        super("");
        pattern = EMPTY;
    }

    // REQUETES
    
    public boolean isValid(E e) {
        Contract.checkCondition(e != null && e.filterableValue() != null);

        return pattern.matcher(e.filterableValue()).matches();
    }
    
    @Override
    public String toString() {
        return "RegExp";
    }
    
    // COMMANDES
    
    @Override
    public void setValue(String value) {
        Contract.checkCondition(value != null);

        String v = getValue();
        if (v.equals(value)) {
            return;
        }
        if ("".equals(value)) {
            pattern = EMPTY;
        } else if (!pattern.pattern().equals(value)) {
            try {
                pattern = Pattern.compile(value);
            } catch (PatternSyntaxException pse) {
                pattern = EMPTY;
                value = "";
            }
        }
        super.setValue(value);
    }
}
