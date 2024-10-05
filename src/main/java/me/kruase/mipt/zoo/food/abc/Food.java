package me.kruase.mipt.zoo.food.abc;


import me.kruase.mipt.zoo.util.NamedEntity;


public abstract class Food extends NamedEntity {
    private final int mass;

    public Food(int mass, String name) {
        super(name);
        this.mass = mass;
    }

    @Override
    public String toString() {
        return mass + "kg of " + getName();
    }
}
