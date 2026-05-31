package com.kingdombuilder.core;

import com.kingdombuilder.economy.EconomySystem;
import com.kingdombuilder.city.City;
import com.kingdombuilder.country.Country;
import com.kingdombuilder.simulation.AILogic;

public class TurnSystem {
    private EconomySystem economySystem;
    private AILogic aiLogic;

    public TurnSystem() {
        this.economySystem = new EconomySystem();
        this.aiLogic = new AILogic();
    }

    public void nextTurn(GameState gameState) {
        // 1. Wirtschaft wird berechnet (Einkommen generieren, Kosten abziehen)
        economySystem.processTurn(gameState.getCountries());

        // 2. Städte wachsen
        for (Country country : gameState.getCountries()) {
            for (City city : country.getCities()) {
                city.grow();
            }
        }
        
        // 3. KI handelt
        aiLogic.processAITurn(gameState);

        // Runden-Zähler erhöhen
        gameState.incrementTurn();
    }
}
