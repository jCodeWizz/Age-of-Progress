package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UIImageButton;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.input.MouseInput;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Tile;
import dev.codewizz.world.objects.*;
import dev.codewizz.world.objects.buildings.Building;
import dev.codewizz.world.tiles.*;

import java.util.ArrayList;
import java.util.List;

public class ObjectMenu extends Menu {

    private final static List<GameObject> settlement = new ArrayList<>();
    private final static List<GameObject> deco = new ArrayList<>();

    static {
        settlement.add(new Fence());
        settlement.add(new FenceGate());
        settlement.add(new FencePost());
        settlement.add(new Stump());
    }

    private Image image;
    private GameObject object;
    private Label name;
    private Table showTable;
    private Table scrollTable;
    private ScrollPane scrollPane;

    public ObjectMenu(Stage uiStage, Layer layer) {
        super(uiStage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().fill().size(384, 780).left().padLeft(10);
        main.setBackground(new Image(Assets.getSprite("path-menu")).getDrawable());

        Label title = UILabel.create("Object Menu");
        main.add(title).top().left().padLeft(10).padBottom(4);

        Button closeButton = UIIconButton.create("close-icon", UIIconButton.smallStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        main.add(closeButton).top().right().size(33, 36).pad(1);

        main.row();
        Table tabTable = new Table();

        TextButton tab1Button = UITextButton.create("Settlement", UITextButton.smallStyle);
        tab1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fillScrollTable(settlement);
            }
        });
        tabTable.add(tab1Button).height(24).padBottom(10).spaceRight(5);

        TextButton tab2Button = UITextButton.create("Decoration", UITextButton.smallStyle);
        tab2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fillScrollTable(deco);
            }
        });
        tabTable.add(tab2Button).height(24).padBottom(10).spaceRight(5);

        main.add(tabTable).expand().fill().top().width(384).height(32).colspan(2);

        main.row();
        scrollTable = new Table();
        scrollPane = new ScrollPane(scrollTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setOverscroll(false, true);
        scrollPane.setScrollbarsVisible(true);
        main.add(scrollPane).expand().fill().top().width(384).height(378).padTop(0).colspan(2);
        fillScrollTable(settlement);

        scrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setKeyboardFocus(scrollPane);
                stage.setScrollFocus(scrollPane);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stage.setKeyboardFocus(null);
                stage.setScrollFocus(null);
            }

        });

        main.row();
        showTable = new Table();
        main.add(showTable).expand().fill().bottom().width(384).height(324).colspan(2);
        name = UILabel.create("");
        showTable.add(name).expand().left().top().padLeft(10);
        showTable.row();

        image = new Image();

        showTable.add(image).expand().bottom();
    }

    public void fillScrollTable(List<GameObject> objects) {
        scrollTable.clear();
        scrollTable.top();

        int buttonsPerRow = 4;
        float tableWidth = 375;

        float buttonSize = tableWidth / buttonsPerRow;

        int i = 0;

        for (GameObject o : objects) {
            i++;
            ImageButton button = UIImageButton.create(UIImageButton.buySlotStyle, ((IBuy)o).getMenuSprite());
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    object = o;
                    MouseInput.currentlyDrawingObject = o;
                    MouseInput.object = true;
                }
            });
            button.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    changeTile(o);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (object != null) {
                        changeTile(object);
                    }
                }
            });

            scrollTable.add(button).size(buttonSize, buttonSize).top();  // Align each button to the top

            if (i % buttonsPerRow == 0) {
                scrollTable.row();
            }
        }
    }

    public void changeTile(GameObject object) {
        showTable.clear();

        IBuy info = (IBuy) object;

        name.setText(info.getMenuName());
        showTable.add(name).expand().top().left().padLeft(10);
        showTable.row();

        image = new Image(info.getMenuSprite());

        float w = info.getMenuSprite().getWidth();
        float h = info.getMenuSprite().getHeight();

        float r = 280 / w;
        w = 280;
        h *= r;

        if (h > 300) {
            w = info.getMenuSprite().getWidth();
            h = info.getMenuSprite().getHeight();

            r = 300 / h;
            h = 300;
            w *= r;
        }

        showTable.add(image).expand().bottom().padBottom(10).size(w, h);
    }

    @Override
    public void onClose() {
        MouseInput.object = true;
        MouseInput.currentlyDrawingObject = null;

        stage.setKeyboardFocus(null);
        stage.setScrollFocus(null);
    }

    @Override
    public void onOpen() {
        MouseInput.object = true;
    }
}