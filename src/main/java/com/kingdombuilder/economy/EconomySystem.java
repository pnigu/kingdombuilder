package com.kingdombuilder.economy;

import com.kingdombuilder.country.Country;
import com.kingdombuilder.city.City;
import java.util.List;

public class EconomySystem {
    
    public void processTurn(List<Country> countries) {
        for (Country country : countries) {
            int income = 0;
            for (City city : country.getCities()) {
                income += city.calculateIncome();
            }
            country.addMoney(income);
            
            // Phase 1: Simple expenses. e.g. 1 money per 100 population as administration costs
            int expenses = country.getTotalPopulation() / 100;
            country.subtractMoney(expenses);
        }
    }
}
