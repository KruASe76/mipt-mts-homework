package me.kruase.mipt;


import me.kruase.mipt.zoo.animals.Dolphin;
import me.kruase.mipt.zoo.animals.Eagle;
import me.kruase.mipt.zoo.animals.Horse;
import me.kruase.mipt.zoo.food.Beef;
import me.kruase.mipt.zoo.food.Fish;
import me.kruase.mipt.zoo.food.Grass;


public class Main {
    public static void main(String[] args) {
        Horse horse = new Horse();
        horse.walk(5);
        horse.eat(new Grass(3));

        Dolphin dolphin = new Dolphin("GigaDolphin");
        dolphin.swim(8);
        dolphin.eat(new Fish(2));

        Eagle eagle = new Eagle("Balloon");
        eagle.fly(10);
        eagle.eat(new Beef(1, "air"));
    }
}
