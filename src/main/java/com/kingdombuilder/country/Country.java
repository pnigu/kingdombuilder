package com.kingdombuilder.country;

import com.kingdombuilder.city.City;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Country {
    private String name;
    private int money;
    private int stability; // e.g. 0 to 100
    private List<City> cities;
    private Set<Country> enemies;

    public Country(String name, int initialMoney, int initialStability) {
        this.name = name;
        this.money = initialMoney;
        this.stability = initialStability;
        this.cities = new ArrayList<>();
        this.enemies = new HashSet<>();
    }

    public String getName() { return name; }
    public int getMoney() { return money; }
    public void addMoney(int amount) { this.money += amount; }
    public void subtractMoney(int amount) { this.money -= amount; }

    public int getStability() { return stability; }
    public void setStability(int stability) { this.stability = Math.max(0, Math.min(100, stability)); }

    public List<City> getCities() { return cities; }
    public void addCity(City city) { 
        this.cities.add(city);
        city.setOwner(this);
    }

    public int getTotalPopulation() {
        return cities.stream().mapToInt(City::getPopulation).sum();
    }
    
    // Diplomacy
    public void declareWar(Country target) {
        this.enemies.add(target);
        target.enemies.add(this); // mutual war
    }
    
    public void makePeace(Country target) {
        this.enemies.remove(target);
        target.enemies.remove(this);
    }
    
    public boolean isAtWarWith(Country other) {
        return enemies.contains(other);
    }
}
