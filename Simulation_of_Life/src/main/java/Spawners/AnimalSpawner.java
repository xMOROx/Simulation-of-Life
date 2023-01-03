package Spawners;

import Entities.Animal;
import Logic.Mutation;
import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.MutationVariant;
import gui.render.World.Cell;
import Logic.Genome;
import Misc.Vector2D;
import World.Maps.WorldMap;
import com.google.gson.JsonElement;

public class AnimalSpawner extends Spawner {

    protected final AnimalSpawner.Config config;
    protected final Animal.DefaultConfiguration defaultConfig;

    public AnimalSpawner(Config config) {
        this.config = config;
        this.defaultConfig = new Animal.DefaultConfiguration() {{
            this.dailyEnergyLoss = config.dailyEnergyLoss;
            this.initialEnergy = config.startEnergy;
            this.mutationVariant = config.mutationVariant;
            this.animalBehaviorVariant = config.animalBehaviorVariant;
            this.energyToReproduce = config.energyToReproduce;
            this.energyConsumedWhenReproducing = config.energyConsumedWhenReproducing;
            this.genomeLength = config.genomeLength;

        }};
    }


    @Override
    public boolean register(WorldMap world) {
        super.register(world);
        for (int i = 0; i < config.initialPopulation; i++) {
            this.trySpawnOne(120);
        }
        return true;
    }

    protected boolean trySpawnOne(int attempts) {
        Vector2D position = findValidPosition(attempts);
        if (position == null) {
            return false;
        }
        Animal animal = new Animal(new Genome(defaultConfig.genomeLength, new Mutation(new Mutation.DefaultConfiguration(){{
            this.numberOfMinimumGenes = 0;
            this.numberOfMaximumGenes = defaultConfig.genomeLength / 2;
        }})), position, defaultConfig);
        this.world.addEntity(animal);
        return true;
    }

    @Override
    public void spawn() {
        if(world.getStatistics().animalCount < 10) {
            this.trySpawnOne(120);
        }
    }

    @Override
    public boolean canSpawn(Cell cell) {
        return Cell.isCellEmpty(cell);
    }

    public static Spawner fromConfig(JsonElement configJson) {
        Config config = GSON.fromJson(configJson, Config.class);
        return new AnimalSpawner(config);
    }

    public static class Config {
        protected int startEnergy = 100;
        protected int dailyEnergyLoss = 100;
        protected int initialPopulation = 100;
        protected  int energyToReproduce = 80;
        protected  int energyConsumedWhenReproducing = 50;
        protected int genomeLength = 32;
        protected MutationVariant mutationVariant = MutationVariant.FULL_RANDOM;
        protected AnimalBehaviorVariant animalBehaviorVariant = AnimalBehaviorVariant.FULL_PREDICTABLE;
    }
}
