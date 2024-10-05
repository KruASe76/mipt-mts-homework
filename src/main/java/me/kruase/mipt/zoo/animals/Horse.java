package me.kruase.mipt.zoo.animals;


import me.kruase.mipt.zoo.animals.abc.Animal;
import me.kruase.mipt.zoo.animals.interfaces.food.Herbivore;
import me.kruase.mipt.zoo.animals.interfaces.movement.Land;


public class Horse extends Animal implements Land, Herbivore {
    private static final String DEFAULT_NAME = "Horse";

    public Horse(String name) {
        super(name);
    }

    public Horse() {
        super(DEFAULT_NAME);
    }
}
