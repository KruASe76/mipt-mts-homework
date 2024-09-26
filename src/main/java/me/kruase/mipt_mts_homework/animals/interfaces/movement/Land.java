package me.kruase.mipt_mts_homework.animals.interfaces.movement;

public interface Land {
    default void walk(int distance) {
        System.out.println(this.getClass().getSimpleName() + " is walking " + distance + "m");
    }
}
