package com.kingdombuilder.map;

public class WorldMap {
    private final int width;
    private final int height;
    private final Tile[][] tiles;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        initializeMap();
    }

    private void initializeMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Default to plains for now
                tiles[x][y] = new Tile(x, y, TerrainType.PLAINS);
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return null;
    }
}
