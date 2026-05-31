package com.kingdombuilder.military;

import com.kingdombuilder.country.Country;

public class ArmyUnit {
    private String name;
    private int strength;
    private Country owner;

    public ArmyUnit(String name, int strength, Country owner) {
        this.name = name;
        this.strength = strength;
        this.owner = owner;
    }

    public String getName() { return name; }
    public int getStrength() { return strength; }
    public Country getOwner() { return owner; }
}
