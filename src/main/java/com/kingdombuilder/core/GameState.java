package com.kingdombuilder.core;

import com.kingdombuilder.map.WorldMap;
import com.kingdombuilder.country.Country;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private WorldMap worldMap;
    private List<Country> countries;
    private int currentTurn;

    public GameState(int width, int height) {
        this.worldMap = new WorldMap(width, height);
        this.countries = new ArrayList<>();
        this.currentTurn = 0;
    }

    public WorldMap getWorldMap() { return worldMap; }
    
    public List<Country> getCountries() { return countries; }
    public void addCountry(Country country) { this.countries.add(country); }

    public int getCurrentTurn() { return currentTurn; }
    public void incrementTurn() { this.currentTurn++; }
}
