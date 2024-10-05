package me.kruase.mipt.zoo.animals.interfaces.movement;


import me.kruase.mipt.zoo.util.Named;


public interface Land extends Named {
    default void walk(int distance) {
        System.out.println(getName() + " is walking " + distance + "m");
    }
}
