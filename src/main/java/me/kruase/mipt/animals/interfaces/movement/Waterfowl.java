package me.kruase.mipt.animals.interfaces.movement;

public interface Waterfowl {
    default void swim(int distance) {
        System.out.println(this.getClass().getSimpleName() + " is swimming " + distance + "m");
    }
}
