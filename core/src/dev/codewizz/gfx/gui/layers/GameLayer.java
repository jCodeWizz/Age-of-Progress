package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.codewizz.gfx.gui.menus.*;
import dev.codewizz.main.Main;
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
}
