package Spawners;

import Settings.Config;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//Nieskończona implementacja wczytywania z pliku JSON

public class Builder {
    private static Map<String, Function<JsonElement, Spawner>> spawners = new HashMap<>();

    public static void register(Class type, Function<JsonElement, Spawner> spawner) {
        spawners.put(type.getSimpleName(), spawner);
    }

    public static Spawner buildFromConfig(Config.SpawnerConfig spawnerConfig) throws IllegalArgumentException {
        Function<JsonElement, Spawner> function = spawners.get(spawnerConfig.type);
        if (function == null) {
            throw new IllegalArgumentException("Spawner type" + spawnerConfig.type + " not registered!");
        }
        return function.apply(spawnerConfig.config);
    }
}
