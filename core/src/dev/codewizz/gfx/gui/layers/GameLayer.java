package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.menus.*;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;

import java.util.ArrayList;

public class GameLayer extends Layer {

    public ArrayList<Menu> menus = new ArrayList<>();

    public TileMenu tileMenu;
    public ObjectMenu objectMenu;
    public StructureMenu structureMenu;
    public PeopleMenu peopleMenu;
    public SettlementMenu settlementMenu;

    private float updateTimer = 0.5f;

    private Table main;

    @Override
    public void open(Stage stage) {
        tileMenu = new TileMenu(Main.inst.renderer.uiStage, this);
        objectMenu = new ObjectMenu(Main.inst.renderer.uiStage, this);
        structureMenu = new StructureMenu(Main.inst.renderer.uiStage, this);
        peopleMenu = new PeopleMenu(Main.inst.renderer.uiStage, this);
        settlementMenu = new SettlementMenu(Main.inst.renderer.uiStage, this);

        menus.add(tileMenu);
        menus.add(objectMenu);
        menus.add(structureMenu);
        menus.add(peopleMenu);
        menus.add(settlementMenu);

        setup();
    }

    @Override
    public void update(float d) {
        if(updateTimer > 0) {
            updateTimer -= d;
        } else {
            updateTimer = 0.5f;
            for(Menu m : menus) {
                if(m instanceof IUpdateDataMenu && m.isOpen()) {
                    ((IUpdateDataMenu) m).updateData();
                }
            }
        }
    }

    @Override
    public void close(Stage stage) {

    }

    public boolean menusClosed() {
        for(Menu m : menus) {
            if(m.isOpen()) {
                return false;
            }
        }
        return true;
    }

    private void setup() {
        Table backGround = new Table();
        backGround.setFillParent(true);
        Main.inst.renderer.uiStage.addActor(backGround);
        Table backGroundImage = new Table();
        backGroundImage.setBackground(new Image(Assets.getSprite("icon-board-extension")).getDrawable());
        backGround.add(backGroundImage).expand().fillX().height(30 * Layer.scale).bottom();

        main = new Table();
        main.setFillParent(true);
        Main.inst.renderer.uiStage.addActor(main);

        Table board = new Table();
        main.add(board).expand().size(146 * Layer.scale, 30 * Layer.scale).bottom();
        board.setBackground(new Image(Assets.getSprite("icon-board")).getDrawable());

        UIIconButton settlementIcon = UIIconButton.create("manage-icon");
        UIIconButton tileIcon = UIIconButton.create("path-icon");
        UIIconButton constructionIcon = UIIconButton.create("construction-icon");
        UIIconButton peopleIcon = UIIconButton.create("people-icon");
        UIIconButton toolIcon = UIIconButton.create("tool-icon");

        board.add(settlementIcon).size(22 * Layer.scale, 24 * Layer.scale).pad(0, 0, 0, 3 * Layer.scale);
        board.add(tileIcon).size(22 * Layer.scale, 24 * Layer.scale).pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(constructionIcon).size(22 * Layer.scale, 24 * Layer.scale).pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(peopleIcon).size(22 * Layer.scale, 24 * Layer.scale).pad(0, 3 * Layer.scale, 0, 3 * Layer.scale);
        board.add(toolIcon).size(22 * Layer.scale, 24 * Layer.scale).pad(0, 3 * Layer.scale, 0, 0);



    }
}
