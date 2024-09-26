package me.kruase.mipt_mts_homework.food.abc;


public abstract class Food {
    private final int mass;

    public Food(int mass) {
        this.mass = mass;
    }

    @Override
    public String toString() {
        return mass + "kg of " + this.getClass().getSimpleName().toLowerCase();
    }
}
