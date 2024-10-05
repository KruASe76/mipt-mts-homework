package me.kruase.mipt.zoo.food;


import me.kruase.mipt.zoo.food.abc.Meat;


public class Beef extends Meat {
    private static final String DEFAULT_NAME = "beef";

    public Beef(int mass, String name) {
        super(mass, name);
    }

    public Beef(int mass) {
        super(mass, DEFAULT_NAME);
    }
}
