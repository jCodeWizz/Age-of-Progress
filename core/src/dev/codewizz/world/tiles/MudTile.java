package dev.codewizz.world.tiles;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public class MudTile extends Tile {

    public MudTile() {
        this.name = "Mud Tile";
        this.texture = Assets.getSprite("mud-tile");
        this.id = "aop:mud-tile";
        this.cost = 5;
    }
}
