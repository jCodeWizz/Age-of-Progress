package dev.codewizz.world.settlement;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class CropStage {

    public float time;
    public Sprite sprite;

    public CropStage(float time, Sprite sprite) {
        this.time = time;
        this.sprite = sprite;
    }
}