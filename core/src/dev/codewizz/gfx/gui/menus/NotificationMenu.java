package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationMenu extends Menu {

    private static List<Notification> notifications;

    private static Table main;

    public NotificationMenu(Stage stage, GameLayer layer) {
        super(stage, layer);

        this.shouldClose = false;
    }

    public static void makeNotification(Sprite sprite, String title, String text) {
        Notification no = new Notification(sprite, title, text);

        if (notifications.size() >= 5) {
            notifications.remove(0);
        }

        notifications.add(no);

        refresh();
    }

    @Override
    public void render(SpriteBatch b) {
        for (Notification no : notifications) {
            no.time -= Gdx.graphics.getDeltaTime();
            if (no.time < 0) {
                notifications.remove(no);
            }
        }


        refresh();
    }

    @Override
    protected void setup() {
        notifications = new CopyOnWriteArrayList<>();

        main = new Table();
        base.add(main).expand().right().top().pad(10 * Layer.scale, 0, 0, 10 * Layer.scale);

        refresh();
    }

    private static void refresh() {
        main.clear();

        for (Notification notification : notifications) {
            main.add(notification.table).size(256 * Layer.scale, 36 * Layer.scale).padBottom(3 * Layer.scale);
            main.row();
        }
    }
}
class Notification {

    Table table;

    float time = 15f;

    public Notification(Sprite sprite, String title, String text) {
        table = new Table();
        table.setBackground(new Image(Assets.getSprite("notification")).getDrawable());

        Image image  = new Image(sprite);
        table.add(image).pad(2 * Layer.scale).size(32 * Layer.scale);

        Table textTable = new Table();
        table.add(textTable).expand().fill();

        UILabel titleLabel = UILabel.create(title, UILabel.defaultStyle);
        textTable.add(titleLabel).expand().left().padLeft(6 * Layer.scale);

        textTable.row();

        UILabel textLabel = UILabel.create(text, UILabel.mediumStyle);
        textTable.add(textLabel).expand().left().padLeft(Layer.scale);
    }
}
