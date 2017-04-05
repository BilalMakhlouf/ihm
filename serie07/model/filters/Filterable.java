package serie07.model.filters;

/**
 * Lorsqu'on souhaite filtrer des éléments par rapport à un filtre portant
 *  sur des valeurs d'un type V, on type ces éléments par
 *  <code>Filterable<V></code>.
 * Ainsi un élément filtrable est un élément dont on peut extraire une valeur
 *  du type V au moyen de <code>filterableValue</code>.
 */
public interface Filterable<V> {
    /**
     * La valeur par rapport à laquelle on filtre.
     */
    V filterableValue();
}
