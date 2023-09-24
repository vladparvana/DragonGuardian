package PaooGame.Tiles;

import PaooGame.Graphics.SpriteSheet;

import java.awt.image.BufferedImage;

public class TileFactory {
    public static final int TILE_SIZE = 16;
    public static final int NUM_TILES = 1035;

    private Tile[] tiles;

    public TileFactory(SpriteSheet spritesheet) {
        tiles = new Tile[NUM_TILES];

        //initialize the shared tiles
        for(int i=0;i<NUM_TILES;i++)
        {
            int row = i/(spritesheet.getWidth() / TILE_SIZE);
            int col = i%(spritesheet.getWidth() / TILE_SIZE);
            BufferedImage tileImage=spritesheet.crop(col,row);
            tiles[i]=new Tile(tileImage);
        }
    }

    public Tile getTile(int index)
    {
        if(index >=0 && index< NUM_TILES) {
            return tiles[index];
        }
        return null;
    }
}
