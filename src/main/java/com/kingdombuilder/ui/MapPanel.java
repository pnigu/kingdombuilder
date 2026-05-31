package com.kingdombuilder.ui;

import com.kingdombuilder.core.GameState;
import com.kingdombuilder.map.Tile;
import com.kingdombuilder.map.TerrainType;
import com.kingdombuilder.city.City;
import com.kingdombuilder.military.ArmyUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends JPanel {
    private GameState gameState;
    private final int tileSize = 50;
    private InfoPanel infoPanel;

    public MapPanel(GameState gameState, InfoPanel infoPanel) {
        this.gameState = gameState;
        this.infoPanel = infoPanel;
        
        int width = gameState.getWorldMap().getWidth() * tileSize;
        int height = gameState.getWorldMap().getHeight() * tileSize;
        setPreferredSize(new Dimension(width, height));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tileX = e.getX() / tileSize;
                int tileY = e.getY() / tileSize;
                Tile clickedTile = gameState.getWorldMap().getTile(tileX, tileY);
                if (clickedTile != null && infoPanel != null) {
                    infoPanel.updateSelectedTileInfo(clickedTile);
                }
            }
        });
    }

    public void updateState(GameState newState) {
        this.gameState = newState;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int x = 0; x < gameState.getWorldMap().getWidth(); x++) {
            for (int y = 0; y < gameState.getWorldMap().getHeight(); y++) {
                Tile tile = gameState.getWorldMap().getTile(x, y);
                drawTile(g, tile, x * tileSize, y * tileSize);
            }
        }
    }

    private void drawTile(Graphics g, Tile tile, int px, int py) {
        // Draw terrain
        if (tile.getTerrainType() == TerrainType.WATER) {
            g.setColor(new Color(64, 164, 223));
        } else if (tile.getTerrainType() == TerrainType.FOREST) {
            g.setColor(new Color(34, 139, 34));
        } else if (tile.getTerrainType() == TerrainType.MOUNTAINS) {
            g.setColor(new Color(139, 137, 137));
        } else {
            g.setColor(new Color(124, 205, 124)); // PLAINS
        }
        g.fillRect(px, py, tileSize, tileSize);
        
        // Draw grid lines
        g.setColor(new Color(0, 0, 0, 50));
        g.drawRect(px, py, tileSize, tileSize);
        
        // Draw owner territory color (semi-transparent)
        if (tile.getOwner() != null) {
            if (tile.getOwner().getName().equals("Spieler-Land")) {
                g.setColor(new Color(0, 0, 255, 60)); // Blue for player
            } else {
                g.setColor(new Color(255, 0, 0, 60)); // Red for AI
            }
            g.fillRect(px, py, tileSize, tileSize);
        }
        
        // Draw city
        if (tile.hasCity()) {
            City city = tile.getCity();
            g.setColor(Color.DARK_GRAY);
            g.fillOval(px + 10, py + 10, tileSize - 20, tileSize - 20);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString(city.getName(), px + 2, py + tileSize / 2 + 15);
            
            // Draw barracks indicator
            if (city.hasBarracks()) {
                g.setColor(Color.ORANGE);
                g.fillRect(px + 5, py + 5, 5, 5);
            }
        }
        
        // Draw Fort
        if (tile.hasFort()) {
            g.setColor(new Color(105, 105, 105)); // Dim gray
            int[] xPoints = {px + 10, px + 40, px + 40, px + 10};
            int[] yPoints = {px + 10, px + 10, px + 40, px + 40}; // This was a bug in standard drawing, fixing:
            g.fillRect(px + 10, py + 10, 30, 30);
            g.setColor(Color.BLACK);
            g.drawRect(px + 10, py + 10, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString("Fort", px + 15, py + 30);
        }

        // Draw ArmyUnit
        if (tile.hasArmyUnit()) {
            g.setColor(Color.BLACK);
            g.fillRect(px + 35, py + 5, 10, 10);
            if (tile.getArmyUnit().getOwner() != null && tile.getArmyUnit().getOwner().getName().equals("Spieler-Land")) {
                g.setColor(Color.CYAN); // Player army
            } else {
                g.setColor(Color.MAGENTA); // AI army
            }
            g.drawRect(px + 35, py + 5, 10, 10);
        }
    }
}
