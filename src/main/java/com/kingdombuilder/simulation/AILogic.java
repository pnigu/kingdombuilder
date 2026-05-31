package com.kingdombuilder.simulation;

import com.kingdombuilder.core.GameState;
import com.kingdombuilder.country.Country;
import com.kingdombuilder.map.Tile;
import com.kingdombuilder.city.City;
import com.kingdombuilder.military.ArmyUnit;

import java.util.ArrayList;
import java.util.List;

public class AILogic {
    public void processAITurn(GameState gameState) {
        for (Country country : gameState.getCountries()) {
            if (country.getName().equals("Spieler-Land")) continue; // Skip player
            
            // AI Logic
            // 1. If money > 200, try to build a city on an owned empty tile
            if (country.getMoney() > 200) {
                Tile emptyOwned = findEmptyOwnedTile(gameState, country);
                if (emptyOwned != null) {
                    country.subtractMoney(200);
                    country.addCity(new City("KI-Stadt", 50, emptyOwned));
                }
            }
            
            // 2. If money > 100, try to recruit an army on a city without one
            if (country.getMoney() > 100) {
                for (City city : country.getCities()) {
                    if (!city.getTile().hasArmyUnit()) {
                        country.subtractMoney(50);
                        city.getTile().setArmyUnit(new ArmyUnit("KI-Miliz", 10, country));
                        break; // Recruit one per turn
                    }
                }
            }
            
            // 3. If money > 50, try to expand
            if (country.getMoney() > 50) {
                Tile adjacent = findAdjacentUnownedTile(gameState, country);
                if (adjacent != null) {
                    country.subtractMoney(50);
                    adjacent.setOwner(country);
                }
            }
        }
    }
    
    private Tile findEmptyOwnedTile(GameState gameState, Country country) {
        for (int x = 0; x < gameState.getWorldMap().getWidth(); x++) {
            for (int y = 0; y < gameState.getWorldMap().getHeight(); y++) {
                Tile t = gameState.getWorldMap().getTile(x, y);
                if (t.getOwner() == country && !t.hasCity() && !t.hasFort()) {
                    return t;
                }
            }
        }
        return null;
    }
    
    private Tile findAdjacentUnownedTile(GameState gameState, Country country) {
        List<Tile> owned = new ArrayList<>();
        for (int x = 0; x < gameState.getWorldMap().getWidth(); x++) {
            for (int y = 0; y < gameState.getWorldMap().getHeight(); y++) {
                Tile t = gameState.getWorldMap().getTile(x, y);
                if (t.getOwner() == country) {
                    owned.add(t);
                }
            }
        }
        
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        for (Tile t : owned) {
            for (int[] dir : dirs) {
                Tile neighbor = gameState.getWorldMap().getTile(t.getX() + dir[0], t.getY() + dir[1]);
                if (neighbor != null && neighbor.getOwner() == null) {
                    return neighbor;
                }
            }
        }
        return null;
    }
}
