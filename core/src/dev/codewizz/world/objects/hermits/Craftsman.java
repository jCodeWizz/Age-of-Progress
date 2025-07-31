package dev.codewizz.world.objects.hermits;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;
import dev.codewizz.gfx.Animation;
import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.objects.tasks.CraftTask;
import dev.codewizz.world.objects.tasks.Task;

public class Craftsman extends Job {

    public static int PLANKS_LIMIT = 30;
    public static int amountOfTasks = 0;

    private static final Sprite icon = Assets.getSprite("craftsman-icon");

    public Craftsman() {
        this.job = Jobs.Craftsman;

        createAnimations();
    }

    @Override
    public void update(float dt) {
        if (Main.inst.world.settlement.inventory.getSizeOf(ItemType.PLANKS) < PLANKS_LIMIT && amountOfTasks == 0) {
            CraftTask task = new CraftTask(Registers.recipes.get("aop:planks"));
            Main.inst.world.settlement.addTask(task, true);
            amountOfTasks++;
        }
    }

    @Override
    public void render(SpriteBatch b) {

    }

    @Override
    public Sprite getIcon() {
        return icon;
    }

    private void createAnimations() {
        Sprite[] S = new Sprite[3];
        S[0] = Assets.getSprite("down-1-craftsman");
        S[1] = Assets.getSprite("down-2-craftsman");
        S[2] = Assets.getSprite("down-3-craftsman");

        Sprite[] SW = new Sprite[3];
        SW[0] = Assets.getSprite("down-left-1-craftsman");
        SW[1] = Assets.getSprite("down-left-2-craftsman");
        SW[2] = Assets.getSprite("down-left-3-craftsman");

        Sprite[] W = new Sprite[3];
        W[0] = Assets.getSprite("left-1-craftsman");
        W[1] = Assets.getSprite("left-2-craftsman");
        W[2] = Assets.getSprite("left-3-craftsman");

        Sprite[] NW = new Sprite[3];
        NW[0] = Assets.getSprite("up-left-1-craftsman");
        NW[1] = Assets.getSprite("up-left-2-craftsman");
        NW[2] = Assets.getSprite("up-left-3-craftsman");

        Sprite[] N = new Sprite[3];
        N[0] = Assets.getSprite("up-1-craftsman");
        N[1] = Assets.getSprite("up-2-craftsman");
        N[2] = Assets.getSprite("up-3-craftsman");

        Sprite[] NE = new Sprite[3];
        NE[0] = Assets.getSprite("up-right-1-craftsman");
        NE[1] = Assets.getSprite("up-right-2-craftsman");
        NE[2] = Assets.getSprite("up-right-3-craftsman");

        Sprite[] E = new Sprite[3];
        E[0] = Assets.getSprite("right-1-craftsman");
        E[1] = Assets.getSprite("right-2-craftsman");
        E[2] = Assets.getSprite("right-3-craftsman");

        Sprite[] SE = new Sprite[3];
        SE[0] = Assets.getSprite("down-right-1-craftsman");
        SE[1] = Assets.getSprite("down-right-2-craftsman");
        SE[2] = Assets.getSprite("down-right-3-craftsman");

        animations.put(Direction.South, new Animation(Hermit.walkAnimSpeed, S));
        animations.put(Direction.SouthWest, new Animation(Hermit.walkAnimSpeed, SW));
        animations.put(Direction.West, new Animation(Hermit.walkAnimSpeed, W));
        animations.put(Direction.NorthWest, new Animation(Hermit.walkAnimSpeed, NW));
        animations.put(Direction.North, new Animation(Hermit.walkAnimSpeed, N));
        animations.put(Direction.NorthEast, new Animation(Hermit.walkAnimSpeed, NE));
        animations.put(Direction.East, new Animation(Hermit.walkAnimSpeed, E));
        animations.put(Direction.SouthEast, new Animation(Hermit.walkAnimSpeed, SE));

        directions.put(Direction.South, Assets.getSprite("down-2-craftsman"));
        directions.put(Direction.SouthWest, Assets.getSprite("down-left-2-craftsman"));
        directions.put(Direction.West, Assets.getSprite("left-2-craftsman"));
        directions.put(Direction.NorthWest, Assets.getSprite("up-left-2-craftsman"));
        directions.put(Direction.North, Assets.getSprite("up-2-craftsman"));
        directions.put(Direction.NorthEast, Assets.getSprite("up-right-2-craftsman"));
        directions.put(Direction.East, Assets.getSprite("right-2-craftsman"));
        directions.put(Direction.SouthEast, Assets.getSprite("down-right-2-craftsman"));
    }
}
