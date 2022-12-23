package Settings;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SimpleConfig {

    private static final Gson g = new Gson();
    public int width = 200;
    public int height = 200;
    public int startEnergy = 100;
    public int moveEnergy = 1;
    public int plantEnergy = 20;
    public double jungleRatio = 0.3;

    static SimpleConfig fromFile(String file) throws FileNotFoundException {
        var reader = new FileReader(file);
        return g.fromJson(reader, SimpleConfig.class);
    }


}
