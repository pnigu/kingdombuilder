package com.kingdombuilder.simulation;

import com.kingdombuilder.city.City;
import com.kingdombuilder.core.GameState;
import com.kingdombuilder.core.TurnSystem;
import com.kingdombuilder.country.Country;
import com.kingdombuilder.map.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void testSimulationPhase1() {
        // 1. Setup Game State (10x10 Map)
        GameState gameState = new GameState(10, 10);
        TurnSystem turnSystem = new TurnSystem();

        // 2. Setup Countries
        Country playerCountry = new Country("PlayerLand", 100, 50);
        Country aiCountry = new Country("AILand", 100, 50);
        
        gameState.addCountry(playerCountry);
        gameState.addCountry(aiCountry);

        // 3. Setup Cities
        Tile cityTile1 = gameState.getWorldMap().getTile(2, 2);
        City city1 = new City("Capital", 1000, cityTile1);
        playerCountry.addCity(city1);

        Tile cityTile2 = gameState.getWorldMap().getTile(7, 7);
        City city2 = new City("AICapital", 800, cityTile2);
        aiCountry.addCity(city2);

        // Verify initial state
        assertEquals(0, gameState.getCurrentTurn());
        assertEquals(100, playerCountry.getMoney());
        assertEquals(1000, city1.getPopulation());

        // 4. Simulate 10 turns
        for (int i = 0; i < 10; i++) {
            System.out.println("--- Turn " + (i+1) + " ---");
            turnSystem.nextTurn(gameState);
            
            System.out.println("PlayerLand Money: " + playerCountry.getMoney() + " | Population: " + city1.getPopulation());
            System.out.println("AILand Money: " + aiCountry.getMoney() + " | Population: " + city2.getPopulation());
        }

        // Verify end state
        assertEquals(10, gameState.getCurrentTurn());
        
        // City 1 grows 5% per turn for 10 turns starting from 1000 -> approx 1628
        assertTrue(city1.getPopulation() > 1500, "City population should grow");
        assertTrue(playerCountry.getMoney() > 100, "Country money should increase from taxes");
        
        // Ensure that tile owner is set correctly via the city
        assertEquals(playerCountry, cityTile1.getOwner());
    }
}
