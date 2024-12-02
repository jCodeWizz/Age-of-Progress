package dev.codewizz.world.objects.tasks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import dev.codewizz.gfx.Animation;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.Timer;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;
import dev.codewizz.world.settlement.Crop;
import dev.codewizz.world.tiles.FarmTile;

public class PlantCropTask extends Task {

    private static final Sprite frame0 = Assets.getSprite("hermit-axe-1");
    private static final Sprite frame1 = Assets.getSprite("hermit-axe-2");

    private Hermit hermit;
    private final Cell cell;
    private final Animation animation = new Animation(-10f, 0, 0.1f, frame0, frame1);
    private boolean reached = false;

    private final Timer timer;

    public PlantCropTask(Cell cell) {
        super(Jobs.Farmer);

        this.cell = cell;

        timer = new Timer(2f) {
            @Override
            public void timer() {
                finish();
            }
        };
    }

    @Override
    public void finish() {
        cell.setTile(new FarmTile());

        Crop crop = Crop.cropTypes.get("aop:carrot").get(cell);
        Main.inst.world.settlement.crops.add(crop);
        cell.tile.setCurrentSprite(crop.getCurrentSprite());

        hermit.setTaskAnimation(null);
        hermit.finishCurrentTask();
    }

    @Override
    public void stop() {
        hermit.getAgent().stop();
        hermit.setTaskAnimation(null);
        hermit.finishCurrentTask();

        timer.cancel();
    }

    @Override
    public void start(TaskableObject object) {
        this.hermit = (Hermit) object;

        hermit.getAgent().setGoal(cell);
        if (hermit.getAgent().path.isEmpty()) { reach(); }

        started = true;

    }

    @Override
    public void reach() {
        hermit.setFacing(Direction.West);
        hermit.setTaskAnimation(animation);
        reached = true;
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(float d) {
        if (reached) {
            timer.update(d);
        }
    }

    @Override
    public String getName() {
        return "Planting Crop";
    }
}
