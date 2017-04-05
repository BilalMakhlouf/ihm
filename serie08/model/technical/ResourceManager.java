package serie08.model.technical;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import serie08.event.ResourceListener;
import serie08.event.ResourceSupport;

public class ResourceManager {
    
    // ATTRIBUTS
    
    private static final String NL = System.getProperty("line.separator");
    private static final int TOTAL = 100;

    private final ResourceSupport support;

    // CONSTRUCTEURS
    
    public ResourceManager() {
        support = new ResourceSupport(this);
    }

    // REQUETES

    // COMMANDES
    
    public void addResourceListener(ResourceListener lst) {
        support.add(lst);
    }
    
    public void removeResourceListener(ResourceListener lst) {
        support.remove(lst);
    }
    
    public void loadResource(File f) {
        long length = f.length();
        long bytesRead = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(f));
            String line = in.readLine();
            while (line != null) {
                delayAction(); // pour que ça rame...
                byte[] data = line.getBytes();
                bytesRead += data.length + NL.length();
                int percent = (int) (bytesRead * TOTAL / length);
                support.fireResourceLoaded(line);
                support.fireProgressUpdated(percent);
                line = in.readLine();
            }
        } catch (IOException e) {
            support.fireErrorOccured(e);
        } finally {
            closeIfNotNull(in);
        }
    }
    
    public void saveResource(File f, List<String> lines) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(f));
            int length = lines.size();
            for (int i = 0; i < lines.size(); i++) {
                delayAction(); // pour que ça rame...
                int percent = (int) ((i + 1) * TOTAL / length);
                out.write(lines.get(i));
                out.write(NL);
                support.fireProgressUpdated(percent);
            }
            support.fireResourceSaved(f.getName());
        } catch (IOException e) {
            support.fireErrorOccured(e);
        } finally {
            closeIfNotNull(out);
        }
    }
    
    // OUTILS
    
    // cette méthode sert à ralentir les actions pour qu'on aie le temps
    // de voir que ça rame...
    private void delayAction() {
        final int delay = 100;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void closeIfNotNull(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                support.fireErrorOccured(e);
            }
        }
    }
}
