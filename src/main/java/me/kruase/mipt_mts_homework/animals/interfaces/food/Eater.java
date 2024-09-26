package me.kruase.mipt_mts_homework.animals.interfaces.food;

import me.kruase.mipt_mts_homework.food.abc.Food;


public interface Eater<F extends Food> {
    default void eat(F food) {
        System.out.println(this.getClass().getSimpleName() + " is eating " + food.toString());
    }
}
