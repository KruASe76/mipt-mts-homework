package me.kruase.mipt.animals.interfaces.movement;

public interface Land {
    default void walk(int distance) {
        System.out.println(this.getClass().getSimpleName() + " is walking " + distance + "m");
    }
}
