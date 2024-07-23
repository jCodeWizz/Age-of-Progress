package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UIIconMenu;
import dev.codewizz.gfx.gui.menus.*;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;

import java.util.ArrayList;

public class GameLayer extends Layer {

    public ArrayList<Menu> menus = new ArrayList<>();

    public TileMenu tileMenu;
    public ObjectMenu objectMenu;
    public StructureMenu structureMenu;
    public PeopleMenu peopleMenu;
    public SettlementMenu settlementMenu;
    public PauseMenu pauseMenu;
    public UIIconMenu constructionMenu;
    public UIIconMenu toolMenu;
    public DebugMenu debugMenu;

    private float updateTimer = 0.25f;

    public Table main;
    private UIIconButton constructionMenuButton;
    private UIIconButton toolMenuButton;

    @Override
    public void open(Stage stage) {
        setup();

        tileMenu = new TileMenu(Main.inst.renderer.uiStage, this);
        objectMenu = new ObjectMenu(Main.inst.renderer.uiStage, this);
        structureMenu = new StructureMenu(Main.inst.renderer.uiStage, this);
        peopleMenu = new PeopleMenu(Main.inst.renderer.uiStage, this);
        settlementMenu = new SettlementMenu(Main.inst.renderer.uiStage, this);
        pauseMenu = new PauseMenu(Main.inst.renderer.uiStage, this);
        debugMenu = new DebugMenu(Main.inst.renderer.uiStage, this);

        constructionMenu = new ConstructionMenu(Main.inst.renderer.uiStage, this,
                                                constructionMenuButton);
        toolMenu = new ToolMenu(Main.inst.renderer.uiStage, this, toolMenuButton);

        menus.add(tileMenu);
        menus.add(objectMenu);
        menus.add(structureMenu);
        menus.add(peopleMenu);
        menus.add(settlementMenu);
        menus.add(pauseMenu);
        menus.add(constructionMenu);
        menus.add(toolMenu);
    }

    @Override
    public void update(float d) {

        if (debugMenu.isOpen()) { debugMenu.updateData(); }


        if (updateTimer > 0) {
            updateTimer -= d;
        } else {
            updateTimer = 0.25f;
            for (Menu m : menus) {
                if (m instanceof IUpdateDataMenu && m.isOpen()) {
                    ((IUpdateDataMenu) m).updateData();
                }
            }
        }
    }

    @Override
    public void close(Stage stage) {

    }

    private void setup() {
        Table backGround = new Table();
        backGround.setFillParent(true);
        Main.inst.renderer.uiStage.addActor(backGround);
        Table backGroundImage = new Table();
        backGroundImage.setBackground(
                new Image(Assets.getSprite("icon-board-extension")).getDrawable());
        backGround.add(backGroundImage).expand().fillX().height(30 * Layer.scale).bottom();

        main = new Table();
        main.setFillParent(true);
        Main.inst.renderer.uiStage.addActor(main);

        Table board = new Table();
        main.add(board).expand().size(146 * Layer.scale, 30 * Layer.scale).bottom();
        board.setBackground(new Image(Assets.getSprite("icon-board")).getDrawable());

        UIIconButton settlementIcon = UIIconButton.create("manage-icon");
        settlementIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu(settlementMenu);
            }
        });

        UIIconButton tileIcon = UIIconButton.create("path-icon");
        tileIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu(tileMenu);
            }
        });

        constructionMenuButton = UIIconButton.create("build-icon");
        constructionMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu(constructionMenu);
            }
        });

        UIIconButton peopleIcon = UIIconButton.create("people-icon");
        peopleIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu(peopleMenu);
            }
        });

        toolMenuButton = UIIconButton.create("tool-icon");
        toolMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu(toolMenu);
            }
        });

        board.add(settlementIcon).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 0, 0, 3 * Layer.scale);
        board.add(tileIcon).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(constructionMenuButton).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(peopleIcon).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(toolMenuButton).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 0);
    }

    public void openMenu(Menu menu) {
        for (Menu m : menus) {
            if (m.isOpen()) {
                if (m.equals(menu)) {
                    m.close();
                    return;
                }

                m.close();
            }
        }

        menu.open();
        if (menu instanceof IUpdateDataMenu) {
            ((IUpdateDataMenu) menu).updateData();
        }
    }

    public void closeMenus() {
        for (Menu m : menus) {
            if (m.isOpen()) {
                m.close();
            }
        }
    }

    public boolean menusClosed() {
        for (Menu m : menus) {
            if (m.isOpen()) {
                return false;
            }
        }
        return true;
    }
}
