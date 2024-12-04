package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UIIconMenu;
import dev.codewizz.gfx.gui.elements.UIImageButton;
import dev.codewizz.gfx.gui.elements.UIToggle;
import dev.codewizz.gfx.gui.menus.*;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.World;

import java.util.ArrayList;

public class GameLayer extends Layer {

    public ArrayList<Menu> menus = new ArrayList<>();

    public TileMenu tileMenu;
    public ObjectMenu objectMenu;
    public StructureMenu structureMenu;
    public ManageStructureMenu manageStructureMenu;
    public PeopleMenu peopleMenu;
    public SettlementMenu settlementMenu;
    public PauseMenu pauseMenu;
    public UIIconMenu constructionMenu;
    public UIIconMenu toolMenu;
    public UIIconMenu areaMenu;
    public DebugMenu debugMenu;
    public SelectMenu selectMenu;

    private final Timer updateTimer;

    public Table main;
    private UIIconButton constructionMenuButton;
    private UIIconButton toolMenuButton;
    private UIIconButton areaMenuButton;

    public UIToggle speed0;
    public UIToggle speed1;
    public UIToggle speed2;
    public UIToggle speed3;

    public GameLayer() {
        updateTimer = new Timer(0.2f) {
            @Override
            public void timer() {
                for (Menu m : menus) {
                    if (m.isOpen() && m instanceof IUpdateDataMenu) {
                        ((IUpdateDataMenu) m).updateData();
                    }
                }
            }
        };
        updateTimer.setRepeat(true);
    }

    @Override
    public void open(Stage stage) {
        setup();

        tileMenu = new TileMenu(Main.inst.renderer.uiStage, this);
        objectMenu = new ObjectMenu(Main.inst.renderer.uiStage, this);
        structureMenu = new StructureMenu(Main.inst.renderer.uiStage, this);
        manageStructureMenu = new ManageStructureMenu(Main.inst.renderer.uiStage, this);
        peopleMenu = new PeopleMenu(Main.inst.renderer.uiStage, this);
        settlementMenu = new SettlementMenu(Main.inst.renderer.uiStage, this);
        pauseMenu = new PauseMenu(Main.inst.renderer.uiStage, this);
        debugMenu = new DebugMenu(Main.inst.renderer.uiStage, this);
        selectMenu = new SelectMenu(Main.inst.renderer.uiStage, this);

        constructionMenu = new ConstructionMenu(Main.inst.renderer.uiStage, this, constructionMenuButton);
        toolMenu = new ToolMenu(Main.inst.renderer.uiStage, this, toolMenuButton);
        areaMenu = new AreaMenu(Main.inst.renderer.uiStage, this, areaMenuButton);

        menus.add(tileMenu);
        menus.add(objectMenu);
        menus.add(structureMenu);
        menus.add(manageStructureMenu);
        menus.add(peopleMenu);
        menus.add(settlementMenu);
        menus.add(pauseMenu);
        menus.add(constructionMenu);
        menus.add(toolMenu);
        menus.add(areaMenu);
        menus.add(selectMenu);
    }

    @Override
    public void update(float d) {
        if (debugMenu.isOpen()) { debugMenu.updateData(); }
        updateTimer.update(d);
    }

    @Override
    public void render(SpriteBatch b) {
        for (Menu m : menus) {
            if (m.isOpen()) {
                m.render(b);
            }
        }

        if(debugMenu.isOpen()) debugMenu.render(b);
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

        areaMenuButton = UIIconButton.create("area-icon");
        areaMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu(areaMenu);
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
        board.add(areaMenuButton).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(constructionMenuButton).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(peopleIcon).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(toolMenuButton).size(22 * Layer.scale, 24 * Layer.scale)
                .pad(0, 3 * Layer.scale, 0, 0);

        // Add new buttons
        Table bottomRightTable = new Table();
        bottomRightTable.bottom().right();
        bottomRightTable.setFillParent(true);

        speed0 = UIToggle.create(UIToggle.speed0Style);
        speed0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                World.gameSpeed = 0;
            }
        });

        speed1 = UIToggle.create(UIToggle.speed1Style);
        speed1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                World.gameSpeed = 1;
            }
        });

        speed2 = UIToggle.create(UIToggle.speed2Style);
        speed2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                World.gameSpeed = 3;
            }
        });

        speed3 = UIToggle.create(UIToggle.speed3Style);
        speed3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                World.gameSpeed = 5;
            }
        });

        ButtonGroup<UIToggle> buttonGroup = new ButtonGroup<>();
        buttonGroup.add(speed1);
        buttonGroup.add(speed0);
        buttonGroup.add(speed2);
        buttonGroup.add(speed3);

        bottomRightTable.add(speed0).size(9 * Layer.scale, 10 * Layer.scale).pad(0, 2 * Layer.scale, 5 * Layer.scale, 0);
        bottomRightTable.add(speed1).size(9 * Layer.scale, 10 * Layer.scale).pad(0, 2 * Layer.scale, 5 * Layer.scale, 0);
        bottomRightTable.add(speed2).size(15 * Layer.scale, 10 * Layer.scale).pad(0, 2 * Layer.scale, 5 * Layer.scale, 0);
        bottomRightTable.add(speed3).size(21 * Layer.scale, 10 * Layer.scale).pad(0, 2 * Layer.scale, 5 * Layer.scale, 50 * Layer.scale);

        Main.inst.renderer.uiStage.addActor(bottomRightTable);
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
