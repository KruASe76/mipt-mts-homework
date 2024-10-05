package me.kruase.mipt.zoo.animals.interfaces.movement;


import me.kruase.mipt.zoo.util.Named;


public interface Flying extends Named {
    default void fly(int distance) {
        System.out.println(getName() + " is flying " + distance + "m");
    }
}
