package serie07.model.filters;

import util.Contract;


/**
 * Implantation du filtre (basé sur des valeurs de type String) :
 *      "getValue() est un préfixe de la valeur filtrable associée à e".
 * @inv <pre>
 *     forall e:E :
 *         isValid(e) == e.filtrableValue().startsWith(getValue()) </pre>
 */
public class Prefix<E extends Filterable<String>>
        extends AbstractFilter<E, String> {
    
    // CONSTRUCTEURS
    
    public Prefix() {
        super("");
    }

    // REQUETES
    
    public boolean isValid(E e) {
        Contract.checkCondition(e != null && e.filterableValue() != null);

        return e.filterableValue().startsWith(getValue());
    }
    
    @Override
    public String toString() {
        return "Préfixe";
    }
}
