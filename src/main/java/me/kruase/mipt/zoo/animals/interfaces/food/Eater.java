package me.kruase.mipt.zoo.animals.interfaces.food;


import me.kruase.mipt.zoo.food.abc.Food;
import me.kruase.mipt.zoo.util.Named;


public interface Eater<F extends Food> extends Named {
    default void eat(F food) {
        System.out.println(getName() + " is eating " + food.toString());
    }
}
