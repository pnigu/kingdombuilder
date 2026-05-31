package com.kingdombuilder;

import com.kingdombuilder.city.City;
import com.kingdombuilder.core.GameState;
import com.kingdombuilder.core.TurnSystem;
import com.kingdombuilder.country.Country;
import com.kingdombuilder.map.Tile;
import com.kingdombuilder.ui.GameWindow;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        System.out.println("Starte Kingdom Builder UI...");
        
        // 1. Setup Game State (10x10 Map)
        GameState gameState = new GameState(10, 10);
        TurnSystem turnSystem = new TurnSystem();

        // 2. Setup Countries
        Country playerCountry = new Country("Spieler-Land", 100, 50);
        Country aiCountry = new Country("KI-Reich", 100, 50);
        
        gameState.addCountry(playerCountry);
        gameState.addCountry(aiCountry);

        // 3. Setup Cities
        Tile cityTile1 = gameState.getWorldMap().getTile(2, 2);
        City city1 = new City("Hauptstadt", 1000, cityTile1);
        playerCountry.addCity(city1);

        Tile cityTile2 = gameState.getWorldMap().getTile(7, 7);
        City city2 = new City("KI-Zentrum", 800, cityTile2);
        aiCountry.addCity(city2);

        // 4. UI im Event Dispatch Thread starten
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow(gameState, turnSystem, playerCountry);
            window.setVisible(true);
        });
    }
}
