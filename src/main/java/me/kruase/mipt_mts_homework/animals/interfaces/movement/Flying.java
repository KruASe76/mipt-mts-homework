package me.kruase.mipt_mts_homework.animals.interfaces.movement;

public interface Flying {
    default void fly(int distance) {
        System.out.println(this.getClass().getSimpleName() + " is flying " + distance + "m");
    }
}
