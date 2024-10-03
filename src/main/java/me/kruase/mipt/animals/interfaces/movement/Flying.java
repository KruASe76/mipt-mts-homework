package me.kruase.mipt.animals.interfaces.movement;

public interface Flying {
    default void fly(int distance) {
        System.out.println(this.getClass().getSimpleName() + " is flying " + distance + "m");
    }
}
