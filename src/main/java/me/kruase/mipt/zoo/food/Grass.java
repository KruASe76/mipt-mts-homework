package me.kruase.mipt.zoo.food;


import me.kruase.mipt.zoo.food.abc.Food;


public class Grass extends Food {
    private static final String DEFAULT_NAME = "grass";

    public Grass(int mass, String name) {
        super(mass, name);
    }

    public Grass(int mass) {
        super(mass, DEFAULT_NAME);
    }
}
