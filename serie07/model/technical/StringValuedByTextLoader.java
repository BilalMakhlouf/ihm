package serie07.model.technical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import serie07.util.StringValuedByText;

public class StringValuedByTextLoader
        extends ResourceLoader<StringValuedByText> {
    
    // ATTRIBUTS
    
    private final File inputFile;

    // CONSTRUCTEURS
    
    public StringValuedByTextLoader(File in) {
        inputFile = in;
    }
    
    // COMMANDES
    
    @Override
    public void loadResource() throws Exception {
        BufferedReader br = new BufferedReader(
                new FileReader(inputFile));
        try {
            String line = br.readLine();
            while (line != null) {
                delayAction(); // ça rame...
                if (!line.equals("")) {
                    fireResourceLoaded(new StringValuedByText(line));
                }
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }
}
