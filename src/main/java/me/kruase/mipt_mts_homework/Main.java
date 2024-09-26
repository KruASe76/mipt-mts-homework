package me.kruase.mipt_mts_homework;


import me.kruase.mipt_mts_homework.animals.Dolphin;
import me.kruase.mipt_mts_homework.animals.Eagle;
import me.kruase.mipt_mts_homework.animals.Horse;
import me.kruase.mipt_mts_homework.food.Beef;
import me.kruase.mipt_mts_homework.food.Fish;
import me.kruase.mipt_mts_homework.food.Grass;

public class Main {
    public static void main(String[] args) {
        Horse horse = new Horse();
        horse.walk(5);
        horse.eat(new Grass(3));

        Dolphin dolphin = new Dolphin();
        dolphin.swim(8);
        dolphin.eat(new Fish(2));

        Eagle eagle = new Eagle();
        eagle.fly(10);
        eagle.eat(new Beef(1));
    }
}
