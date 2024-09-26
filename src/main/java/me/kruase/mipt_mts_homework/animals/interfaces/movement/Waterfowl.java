package me.kruase.mipt_mts_homework.animals.interfaces.movement;

public interface Waterfowl {
    default void swim(int distance) {
        System.out.println(this.getClass().getSimpleName() + " is swimming " + distance + "m");
    }
}
