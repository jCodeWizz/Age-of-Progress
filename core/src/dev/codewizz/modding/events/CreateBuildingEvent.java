package dev.codewizz.modding.events;

import dev.codewizz.world.building.Building;

public class CreateBuildingEvent extends Event {

    private final Building building;

    public CreateBuildingEvent(Building building) {
        this.building = building;
    }
    public Building getBuilding() {
        return building;
    }
}
