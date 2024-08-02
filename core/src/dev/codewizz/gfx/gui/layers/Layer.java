package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.codewizz.gfx.gui.elements.*;

public abstract class Layer {

    public static float scale = 3f;

    public static void reload() {
        UILabel.reload();
        UIIconButton.reload();
        UIIconToggle.reload();
        UIImageButton.reload();
        UISlider.reload();
        UITextButton.reload();
        UITextField.reload();
    }

    public abstract void open(Stage stage);

    public abstract void update(float d);

    public abstract void close(Stage stage);

    public void render(SpriteBatch b) {}


}
