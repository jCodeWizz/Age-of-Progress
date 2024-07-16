package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;

public class UIIconToggle extends UIIconButton {

    private final static Sprite ICON = Assets.getSprite("icon");
    private final static Sprite ICON_PRESSED = Assets.getSprite("icon-pressed");
    private final static Sprite ICON_UNAVAILABLE = Assets.getSprite("icon-unavailable");

    private boolean pressed = false;

    protected UIIconToggle(String icon, ButtonStyle style) {
        super(icon, style);
    }

    public static UIIconToggle create(String icon) {
        return new UIIconToggle(icon, defaultStyle);
    }

    public static UIIconToggle create(String icon, ButtonStyle style) {
        return new UIIconToggle(icon, style);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(pressed) {
            batch.draw(ICON_PRESSED, getX(), getY(), getWidth(), getHeight());
            batch.draw(icon, this.getX(), this.getY() - Layer.scale * 2, getWidth(), getHeight());
        } else if(isDisabled()) {
            batch.draw(ICON_UNAVAILABLE, getX(), getY(), getWidth(), getHeight());
            batch.draw(icon, this.getX(), this.getY(), getWidth(), getHeight());
        } else {
            batch.draw(ICON, getX(), getY(), getWidth(), getHeight());
            batch.draw(icon, this.getX(), this.getY(), getWidth(), getHeight());
        }
    }

    public void clicked() {
        if(!isDisabled()) {
            pressed = !pressed;
        }
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
