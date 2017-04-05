package serie07.util;

import serie07.model.filters.Filterable;

public class StringValuedByText implements Filterable<String> {
    
    private final String data;

    public StringValuedByText(String s) {
        data = s;
    }

    public String filterableValue() {
        return data;
    }

    @Override
    public String toString() {
        return data;
    }
}
