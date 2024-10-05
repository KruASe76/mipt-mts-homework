package me.kruase.mipt.zoo.animals;


import me.kruase.mipt.zoo.animals.abc.Animal;
import me.kruase.mipt.zoo.animals.interfaces.food.Herbivore;
import me.kruase.mipt.zoo.animals.interfaces.movement.Land;


public class Camel extends Animal implements Land, Herbivore {
    private static final String DEFAULT_NAME = "Camel";

    public Camel(String name) {
        super(name);
    }

    public Camel() {
        super(DEFAULT_NAME);
    }
}
