package com.kingdombuilder.city;

import com.kingdombuilder.country.Country;
import com.kingdombuilder.map.Tile;

public class City {
    private String name;
    private int population;
    private Tile tile;
    private Country owner;
    
    // Buildings
    private boolean hasBarracks;

    public City(String name, int initialPopulation, Tile tile) {
        this.name = name;
        this.population = initialPopulation;
        this.tile = tile;
        this.hasBarracks = false;
        if (tile != null) {
            tile.setCity(this);
        }
    }

    public String getName() { return name; }
    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }
    
    public Tile getTile() { return tile; }
    
    public Country getOwner() { return owner; }
    public void setOwner(Country owner) { 
        this.owner = owner;
        if (this.tile != null) {
            this.tile.setOwner(owner); // City owner also owns the tile
        }
    }
    
    public boolean hasBarracks() { return hasBarracks; }
    public void setHasBarracks(boolean hasBarracks) { this.hasBarracks = hasBarracks; }

    public void grow() {
        int growth = (int) (population * 0.05); // 5% growth per turn
        if (growth < 1) growth = 1;
        population += growth;
    }

    public int calculateIncome() {
        // Base income per population unit
        return population / 10;
    }
}
