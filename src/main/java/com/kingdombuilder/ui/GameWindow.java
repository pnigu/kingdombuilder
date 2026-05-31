package com.kingdombuilder.ui;

import com.kingdombuilder.core.GameState;
import com.kingdombuilder.core.TurnSystem;
import com.kingdombuilder.country.Country;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    
    public GameWindow(GameState gameState, TurnSystem turnSystem, Country playerCountry) {
        setTitle("Kingdom Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        InfoPanel infoPanel = new InfoPanel(gameState, turnSystem, playerCountry);
        MapPanel mapPanel = new MapPanel(gameState, infoPanel);
        infoPanel.setMapPanel(mapPanel);

        add(mapPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null); // Center on screen
    }
}
