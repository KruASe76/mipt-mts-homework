package me.kruase.mipt.zoo.animals;


import me.kruase.mipt.zoo.animals.abc.Animal;
import me.kruase.mipt.zoo.animals.interfaces.food.BeefEater;
import me.kruase.mipt.zoo.animals.interfaces.movement.Land;


public class Tiger extends Animal implements Land, BeefEater {
    private static final String DEFAULT_NAME = "Tiger";

    public Tiger(String name) {
        super(name);
    }

    public Tiger() {
        super(DEFAULT_NAME);
    }
}
