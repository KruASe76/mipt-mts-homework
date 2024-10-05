package me.kruase.mipt.zoo.animals;


import me.kruase.mipt.zoo.animals.abc.Animal;
import me.kruase.mipt.zoo.animals.interfaces.food.FishEater;
import me.kruase.mipt.zoo.animals.interfaces.movement.Waterfowl;


public class Dolphin extends Animal implements Waterfowl, FishEater {
    private static final String DEFAULT_NAME = "Dolphin";

    public Dolphin(String name) {
        super(name);
    }

    public Dolphin() {
        super(DEFAULT_NAME);
    }
}
