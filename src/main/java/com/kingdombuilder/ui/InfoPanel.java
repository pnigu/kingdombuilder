package com.kingdombuilder.ui;

import com.kingdombuilder.core.GameState;
import com.kingdombuilder.core.TurnSystem;
import com.kingdombuilder.country.Country;
import com.kingdombuilder.map.Tile;
import com.kingdombuilder.city.City;
import com.kingdombuilder.military.ArmyUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InfoPanel extends JPanel {
    private JLabel turnLabel;
    private JLabel moneyLabel;
    private JLabel popLabel;
    private JTextArea tileInfoArea;
    private JButton nextTurnButton;
    
    // Action Buttons
    private JButton buyLandBtn;
    private JButton buildCityBtn;
    private JButton recruitBtn;
    private JButton buildBarracksBtn;
    private JButton recruitSwordsmanBtn;
    private JButton buildFortBtn;
    private JButton diplomacyBtn;
    
    private GameState gameState;
    private TurnSystem turnSystem;
    private MapPanel mapPanel;
    private Country playerCountry;
    
    private Tile currentSelectedTile;

    public InfoPanel(GameState gameState, TurnSystem turnSystem, Country playerCountry) {
        this.gameState = gameState;
        this.turnSystem = turnSystem;
        this.playerCountry = playerCountry;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(280, 700));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        turnLabel = new JLabel("Runde: " + gameState.getCurrentTurn());
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        moneyLabel = new JLabel("Gold: " + playerCountry.getMoney());
        popLabel = new JLabel("Bevölkerung: " + playerCountry.getTotalPopulation());
        
        nextTurnButton = new JButton("Nächste Runde");
        nextTurnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextTurnButton.addActionListener(e -> {
            turnSystem.nextTurn(this.gameState);
            updateInfo();
            if (mapPanel != null) {
                mapPanel.updateState(this.gameState);
            }
            if (currentSelectedTile != null) {
                updateSelectedTileInfo(currentSelectedTile);
            }
        });
        
        diplomacyBtn = new JButton("Diplomatie");
        diplomacyBtn.addActionListener(e -> openDiplomacyDialog());
        
        tileInfoArea = new JTextArea(8, 20);
        tileInfoArea.setEditable(false);
        tileInfoArea.setLineWrap(true);
        tileInfoArea.setText("Klicke auf ein Tile für Details.");
        tileInfoArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Setup Action Buttons
        buyLandBtn = createButton("Land kaufen (50g)", 50, e -> {
            currentSelectedTile.setOwner(playerCountry);
            refreshTile();
        });
        
        buildCityBtn = createButton("Stadt gründen (200g)", 200, e -> {
            playerCountry.addCity(new City("Neue Stadt", 100, currentSelectedTile));
            refreshTile();
        });
        
        recruitBtn = createButton("Miliz rekrutieren (50g)", 50, e -> {
            currentSelectedTile.setArmyUnit(new ArmyUnit("Miliz", 10, playerCountry));
            refreshTile();
        });
        
        buildBarracksBtn = createButton("Kaserne bauen (100g)", 100, e -> {
            currentSelectedTile.getCity().setHasBarracks(true);
            refreshTile();
        });
        
        recruitSwordsmanBtn = createButton("Schwertkämpfer rekr. (100g)", 100, e -> {
            currentSelectedTile.setArmyUnit(new ArmyUnit("Schwertkämpfer", 20, playerCountry));
            refreshTile();
        });
        
        buildFortBtn = createButton("Fort bauen (150g)", 150, e -> {
            currentSelectedTile.setHasFort(true);
            refreshTile();
        });
        
        // Build layout
        add(turnLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("--- Spieler Info ---"));
        add(moneyLabel);
        add(popLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(diplomacyBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(nextTurnButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        
        add(new JLabel("--- Tile Info ---"));
        add(new JScrollPane(tileInfoArea));
        
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("--- Aktionen ---"));
        add(buyLandBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(buildCityBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(buildFortBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(buildBarracksBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(recruitBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(recruitSwordsmanBtn);
        
        updateInfo();
    }

    private JButton createButton(String text, int cost, java.util.function.Consumer<ActionEvent> action) {
        JButton btn = new JButton(text);
        btn.setVisible(false);
        btn.addActionListener(e -> {
            if (playerCountry.getMoney() >= cost) {
                playerCountry.subtractMoney(cost);
                action.accept(e);
            } else {
                JOptionPane.showMessageDialog(this, "Nicht genug Gold!");
            }
        });
        return btn;
    }

    private void refreshTile() {
        updateInfo();
        updateSelectedTileInfo(currentSelectedTile);
        if (mapPanel != null) mapPanel.repaint();
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public void updateInfo() {
        turnLabel.setText("Runde: " + gameState.getCurrentTurn());
        moneyLabel.setText("Gold: " + playerCountry.getMoney());
        popLabel.setText("Bevölkerung: " + playerCountry.getTotalPopulation());
    }

    public void updateSelectedTileInfo(Tile tile) {
        // Check for Army Movement
        if (this.currentSelectedTile != null 
            && this.currentSelectedTile.hasArmyUnit() 
            && this.currentSelectedTile.getArmyUnit().getOwner() == playerCountry
            && tile != this.currentSelectedTile) {
            
            if (isAdjacent(this.currentSelectedTile, tile)) {
                Country targetOwner = tile.getOwner();
                if (targetOwner != null && targetOwner != playerCountry && !playerCountry.isAtWarWith(targetOwner)) {
                    JOptionPane.showMessageDialog(this, "Du musst erst Krieg erklären (Diplomatie), um dieses Land zu betreten!");
                } else {
                    ArmyUnit myArmy = this.currentSelectedTile.getArmyUnit();
                    if (tile.hasArmyUnit()) {
                        ArmyUnit enemyArmy = tile.getArmyUnit();
                        if (myArmy.getStrength() >= enemyArmy.getStrength()) {
                            tile.setArmyUnit(myArmy);
                            this.currentSelectedTile.setArmyUnit(null);
                            if (targetOwner != null && targetOwner != playerCountry) {
                                tile.setOwner(playerCountry); // conquer
                            }
                            JOptionPane.showMessageDialog(this, "Sieg! Feindliche Armee besiegt.");
                        } else {
                            this.currentSelectedTile.setArmyUnit(null);
                            JOptionPane.showMessageDialog(this, "Niederlage! Deine Armee wurde vernichtet.");
                        }
                    } else {
                        tile.setArmyUnit(myArmy);
                        this.currentSelectedTile.setArmyUnit(null);
                        if (targetOwner != null && targetOwner != playerCountry) {
                            tile.setOwner(playerCountry); // conquer undefended
                        }
                    }
                    if (mapPanel != null) mapPanel.repaint();
                }
            }
        }
        
        this.currentSelectedTile = tile;
        StringBuilder sb = new StringBuilder();
        sb.append("Position: (").append(tile.getX()).append(", ").append(tile.getY()).append(")\n");
        sb.append("Terrain: ").append(tile.getTerrainType()).append("\n");
        if (tile.getOwner() != null) {
            sb.append("Besitzer: ").append(tile.getOwner().getName()).append("\n");
        } else {
            sb.append("Besitzer: Niemand\n");
        }
        
        if (tile.hasFort()) {
            sb.append("Gebäude: Fort\n");
        }
        
        if (tile.hasCity()) {
            City city = tile.getCity();
            sb.append("Stadt: ").append(city.getName()).append("\n");
            sb.append("Bevölkerung: ").append(city.getPopulation()).append("\n");
            if (city.hasBarracks()) {
                sb.append("Gebäude: Kaserne\n");
            }
        }
        
        if (tile.hasArmyUnit()) {
            ArmyUnit unit = tile.getArmyUnit();
            sb.append("Einheit: ").append(unit.getName()).append(" (Stärke: ").append(unit.getStrength()).append(")\n");
        }
        
        tileInfoArea.setText(sb.toString());
        
        // Reset buttons
        buyLandBtn.setVisible(false);
        buildCityBtn.setVisible(false);
        recruitBtn.setVisible(false);
        buildBarracksBtn.setVisible(false);
        recruitSwordsmanBtn.setVisible(false);
        buildFortBtn.setVisible(false);
        
        // Context logic
        if (tile.getOwner() == null && isAdjacentToPlayer(tile)) {
            buyLandBtn.setVisible(true);
        }
        
        if (tile.getOwner() == playerCountry) {
            if (!tile.hasCity() && !tile.hasFort()) {
                buildCityBtn.setVisible(true);
                buildFortBtn.setVisible(true);
            }
            if (tile.hasCity()) {
                City city = tile.getCity();
                if (!city.hasBarracks()) {
                    buildBarracksBtn.setVisible(true);
                }
                if (!tile.hasArmyUnit()) {
                    recruitBtn.setVisible(true);
                    if (city.hasBarracks()) {
                        recruitSwordsmanBtn.setVisible(true);
                    }
                }
            }
        }
        
        revalidate();
        repaint();
    }
    
    private boolean isAdjacentToPlayer(Tile tile) {
        int tx = tile.getX();
        int ty = tile.getY();
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        for (int[] dir : dirs) {
            Tile neighbor = gameState.getWorldMap().getTile(tx + dir[0], ty + dir[1]);
            if (neighbor != null && neighbor.getOwner() == playerCountry) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isAdjacent(Tile t1, Tile t2) {
        int dx = Math.abs(t1.getX() - t2.getX());
        int dy = Math.abs(t1.getY() - t2.getY());
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }
    
    private void openDiplomacyDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Diplomatie", true);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        
        for (Country c : gameState.getCountries()) {
            if (c == playerCountry) continue;
            
            JPanel p = new JPanel(new FlowLayout());
            p.add(new JLabel(c.getName() + " - "));
            
            if (playerCountry.isAtWarWith(c)) {
                JLabel warLabel = new JLabel("Im Krieg! ");
                warLabel.setForeground(Color.RED);
                p.add(warLabel);
                JButton peaceBtn = new JButton("Frieden schließen");
                peaceBtn.addActionListener(e -> {
                    playerCountry.makePeace(c);
                    dialog.dispose();
                    openDiplomacyDialog();
                });
                p.add(peaceBtn);
            } else {
                JLabel peaceLabel = new JLabel("Frieden ");
                peaceLabel.setForeground(new Color(0, 150, 0));
                p.add(peaceLabel);
                JButton warBtn = new JButton("Krieg erklären");
                warBtn.addActionListener(e -> {
                    playerCountry.declareWar(c);
                    dialog.dispose();
                    openDiplomacyDialog();
                });
                p.add(warBtn);
            }
            dialog.add(p);
        }
        
        dialog.setVisible(true);
    }
}
