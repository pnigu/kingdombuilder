package com.kingdombuilder.map;

import com.kingdombuilder.country.Country;
import com.kingdombuilder.city.City;
import com.kingdombuilder.military.ArmyUnit;

public class Tile {
    private final int x;
    private final int y;
    private TerrainType terrainType;
    private Country owner;
    private City city;
    private ArmyUnit armyUnit;
    
    // Buildings
    private boolean hasFort;

    public Tile(int x, int y, TerrainType terrainType) {
        this.x = x;
        this.y = y;
        this.terrainType = terrainType;
        this.hasFort = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public TerrainType getTerrainType() { return terrainType; }
    public void setTerrainType(TerrainType terrainType) { this.terrainType = terrainType; }

    public Country getOwner() { return owner; }
    public void setOwner(Country owner) { this.owner = owner; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
    
    public boolean hasCity() { return city != null; }

    public ArmyUnit getArmyUnit() { return armyUnit; }
    public void setArmyUnit(ArmyUnit armyUnit) { this.armyUnit = armyUnit; }
    
    public boolean hasArmyUnit() { return armyUnit != null; }
    
    public boolean hasFort() { return hasFort; }
    public void setHasFort(boolean hasFort) { this.hasFort = hasFort; }
}
