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
import dev.codewizz.world.Tile;
import dev.codewizz.world.tiles.*;

import java.util.ArrayList;
import java.util.List;

public class TileMenu extends Menu {

    private final static List<Tile> natureTiles = new ArrayList<>();
    private final static List<Tile> tiledTiles = new ArrayList<>();

    static {
        natureTiles.add(new GrassTile());
        natureTiles.add(new FlowerTile());
        natureTiles.add(new DirtTile());
        natureTiles.add(new MudTile());
        natureTiles.add(new ClayTile());
        natureTiles.add(new SandTile());
        natureTiles.add(new FarmTile());
        natureTiles.add(new EmptyTile());
        natureTiles.add(new WaterTile());
        natureTiles.add(new DeepWaterTile());

        tiledTiles.add(new TiledTile());
        tiledTiles.add(new TiledTile2());
        tiledTiles.add(new TiledTile3());
        tiledTiles.add(new TiledTile4());
        tiledTiles.add(new TiledTile5());
        tiledTiles.add(new TiledTile6());
        tiledTiles.add(new TiledTile7());
        tiledTiles.add(new TiledTile8());
    }

    private Image image;
    private Tile tile;
    private Label name;
    private Table showTable;
    private Table scrollTable;
    private ScrollPane scrollPane;

    public TileMenu(Stage uiStage, Layer layer) {
        super(uiStage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().fill().size(384, 780).left().padLeft(10);
        main.setBackground(new Image(Assets.getSprite("path-menu")).getDrawable());

        Label title = UILabel.create("Tile Menu");
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

        TextButton tab1Button = UITextButton.create("Nature", UITextButton.smallStyle);
        tab1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fillScrollTable(natureTiles);
            }
        });
        tabTable.add(tab1Button).height(24).padBottom(10).spaceRight(5);

        TextButton tab2Button = UITextButton.create("Tiled", UITextButton.smallStyle);
        tab2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fillScrollTable(tiledTiles);
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
        fillScrollTable(natureTiles);

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

    public void fillScrollTable(List<Tile> tiles) {
        scrollTable.clear();
        scrollTable.top();

        int buttonsPerRow = 4;
        float tableWidth = 375;

        float buttonSize = tableWidth / buttonsPerRow;

        int i = 0;

        for (Tile t : tiles) {
            i++;
            ImageButton button = UIImageButton.create(UIImageButton.buySlotStyle, t.getCurrentSprite());
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    tile = t;

                    MouseInput.object = false;
                    MouseInput.currentlyDrawingTileId = t.getId();
                }
            });
            button.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    changeTile(t);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (tile != null) {
                        changeTile(tile);
                    }
                }
            });

            scrollTable.add(button).size(buttonSize, buttonSize).top();  // Align each button to the top

            if (i % buttonsPerRow == 0) {
                scrollTable.row();
            }
        }
    }

    public void changeTile(Tile tile) {
        showTable.clear();

        name.setText(tile.getName());
        showTable.add(name).expand().top().left().padLeft(10);
        showTable.row();

        image = new Image(tile.getCurrentSprite());

        float w = tile.getCurrentSprite().getWidth();
        float h = tile.getCurrentSprite().getHeight();

        float r = 280 / w;
        w = 280;
        h *= r;

        if (h > 300) {
            w = tile.getCurrentSprite().getWidth();
            h = tile.getCurrentSprite().getHeight();

            r = 300 / h;
            h = 300;
            w *= r;
        }

        showTable.add(image).expand().bottom().padBottom(10).size(w, h);
    }

    @Override
    public void onClose() {
        MouseInput.object = true;
        MouseInput.currentlyDrawingTileId = "aop:base-tile";

        stage.setKeyboardFocus(null);
        stage.setScrollFocus(null);
    }
}