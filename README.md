# Simulation-of-Life
Map-based simulation of animals' behavior. This program allows users to observe animals moving, feeding, and reproducing, while presenting statistical data through charts. The project was designed for the UST AGH object-oriented programming class.

## Game rules
- Animals have to move each day. The position change costs energy.
- Consuming grass allows gathering some energy for the future.
- A pair of animals on the same field can reproduce if sufficient resources are available.
- Way of moving is based on the parent's genome and random factors.
- Animals in energy deficit die.

## Development Technologies
- Java with object-oriented concepts
- JavaFX for the user interface
- Functional programming techniques to work with streams
- Basic design patterns
## Options
### Map variants
- Earth 
- Hell

### Growing variant
- Equator - plants in the center of the map has more % to spawn. 
- Toxic corpses - plants prefer to grow on tills where fewer animals have dead. 

### Mutation Variant
#### The genomes are from 0 to 7. They map direction of the world
- Full random  - Mutations are not predictable 
- Slight Correction - Mutations are more predictable because the genome can only mutate one step up or down( in example genome is set to 6, so after a mutation it could be 5 or 7)

### Behaviour Variant of animals
- Full predictable - Animals move according to their genomes.  
- Crazy moves - There is a change that animal will move in random direction without following his genomes. 

# How to run
```sh 
./gradlew run 
```

# Preview of main features 
## Main menu
![](https://github.com/xMOROx/Simulation-of-Life/blob/main/readme_images/start_menu.png)

## Create new simulation tab
![](https://github.com/xMOROx/Simulation-of-Life/blob/main/readme_images/options.png)

## Simulation 
### First day 
![](https://github.com/xMOROx/Simulation-of-Life/blob/main/readme_images/firstday.png)

### 100 day
![](https://github.com/xMOROx/Simulation-of-Life/blob/main/readme_images/100day.png)

