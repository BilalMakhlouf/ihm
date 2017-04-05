package serie07.model.filters;

import util.Contract;


/**
 * Implantation du filtre (basé sur des valeurs de type String) :
 *      "getValue() est un facteur de la valeur filtrable associée à e".
 * @inv <pre>
 *     forall e:E :
 *         isValid(e) == e.filtrableValue().contains(getValue()) </pre>
 */
public class Factor<E extends Filterable<String>>
        extends AbstractFilter<E, String> {
    
    // CONSTRUCTEURS
    
    public Factor() {
        super("");
    }

    // REQUETES
    
    public boolean isValid(E e) {
        Contract.checkCondition(e != null && e.filterableValue() != null);

        return e.filterableValue().contains(getValue());
    }
    
    @Override
    public String toString() {
        return "Facteur";
    }
}
