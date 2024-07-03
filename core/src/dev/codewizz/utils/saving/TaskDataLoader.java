package dev.codewizz.utils.saving;

public class TaskDataLoader {

    private GameObjectDataLoader objects;

    public TaskDataLoader(GameObjectDataLoader objects) {
        this.objects = objects;
    }

    public GameObjectDataLoader getObjects() {
        return objects;
    }

}
