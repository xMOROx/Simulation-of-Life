package settings;

import com.google.gson.Gson;
import com.google.gson.JsonElement;


import java.io.FileNotFoundException;
import java.io.FileReader;


public class Config {
    public WorldConfig world;
    public SpawnerConfig[] spawners;

    private static Gson g = new Gson();
    static Config fromFile(String path) throws FileNotFoundException {
        var fileReader = new FileReader(path);
        return g.fromJson(fileReader, Config.class);
    }

    public static class WorldConfig {
        public int width = 200;
        public int height = 200;
    }

    public static class SpawnerConfig {
        public String type;
        public JsonElement config;
    }
}
