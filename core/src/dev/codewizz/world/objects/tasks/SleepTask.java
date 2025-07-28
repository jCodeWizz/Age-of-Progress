package dev.codewizz.world.objects.tasks;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.saving.TaskData;
import dev.codewizz.utils.saving.TaskDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.Nature;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.buildings.Building;
import dev.codewizz.world.objects.hermits.Hermit;
import java.util.UUID;

public class SleepTask extends Task {

    private Hermit hermit;
    private boolean onGround = false;

    private float sleepNeedStart;

    @Override
    public void finish() {
        hermit.finishCurrentTask();
        hermit.setSleepNeed(Nature.DAY_TIME + Nature.TRANSITION_TIME);
        hermit.addTask(new ConsumeTask(), true);
    }

    @Override
    public void stop() {
        hermit.getAgent().stop();
        hermit.finishCurrentTask();
    }

    @Override
    public void start(TaskableObject object) {
        this.nutritionMultiplier = 0f;
        hermit = (Hermit) object;

        Building b = hermit.getHome();

        if (b != null) {
            Cell cell = Main.inst.world.getCell(b.getX(), b.getY() + 32);
            object.getAgent().setGoal(cell);
        } else {
            //TODO: maybe add different ways/spaces to sleep.
            onGround = true;
        }

        sleepNeedStart = hermit.getSleepNeed();
        started = true;
    }

    @Override
    public void reach() {
        hermit.getHome().enter(hermit);
    }

    @Override
    public void update(float d) {

        if (onGround && started) {
            if (hermit.getSleepNeed() <= 0f) {
                finish();
            }
            hermit.setSleepNeed(hermit.getSleepNeed() - d);
        }
    }

    @Override
    public String getName() {
        //return "Getting sleep " + (int) ((1f - (hermit.getSleepNeed() / sleepNeedStart)) * 100f) + "%";
        return "Sleep: " + (int)hermit.getSleepNeed();
    }

    @Override
    public void reset() {

    }

    @Override
    public void load(TaskDataLoader loader, TaskData taskData) {
        super.load(loader, taskData);

        byte[] data = taskData.take();

        UUID uuid = ByteUtils.toUUID(data, 0);
        hermit = (Hermit) loader.getObjects().getLoadedObject(uuid);

        sleepNeedStart = ByteUtils.toFloat(data, 20);
        onGround = ByteUtils.toBoolean(data[24], 0);
    }

    @Override
    public TaskData save(TaskData data) {
        super.save(data);

        data.addArray(ByteUtils.toBytes(hermit.getUUID()));
        data.addFloat(sleepNeedStart);
        data.addByte(ByteUtils.toByte((byte) 0, onGround, 0));

        data.end();
        return data;
    }
}
