package me.kruase.mipt.zoo.food;


import me.kruase.mipt.zoo.food.abc.Meat;


public class Fish extends Meat {
    private static final String DEFAULT_NAME = "fish";

    public Fish(int mass, String name) {
        super(mass, name);
    }

    public Fish(int mass) {
        super(mass, DEFAULT_NAME);
    }
}
