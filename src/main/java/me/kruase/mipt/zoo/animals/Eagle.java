package me.kruase.mipt.zoo.animals;


import me.kruase.mipt.zoo.animals.abc.Animal;
import me.kruase.mipt.zoo.animals.interfaces.food.Predator;
import me.kruase.mipt.zoo.animals.interfaces.movement.Flying;


public class Eagle extends Animal implements Flying, Predator {
    private static final String DEFAULT_NAME = "Eagle";

    public Eagle(String name) {
        super(name);
    }

    public Eagle() {
        super(DEFAULT_NAME);
    }
}
