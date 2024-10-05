package me.kruase.mipt.zoo.animals.interfaces.movement;


import me.kruase.mipt.zoo.util.Named;


public interface Waterfowl extends Named {
    default void swim(int distance) {
        System.out.println(getName() + " is swimming " + distance + "m");
    }
}
