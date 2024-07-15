package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Layer {

    public static float scale = 3f;

    public abstract void open(Stage stage);
    public abstract void update(float d);
    public abstract void close(Stage stage);



}
