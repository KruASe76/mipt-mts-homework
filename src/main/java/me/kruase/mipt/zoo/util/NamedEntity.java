package me.kruase.mipt.zoo.util;


public class NamedEntity implements Named {
    private String name;

    public NamedEntity(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
