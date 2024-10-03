package me.kruase.mipt.animals.interfaces.food;

import me.kruase.mipt.food.abc.Food;


public interface Eater<F extends Food> {
    default void eat(F food) {
        System.out.println(this.getClass().getSimpleName() + " is eating " + food.toString());
    }
}
