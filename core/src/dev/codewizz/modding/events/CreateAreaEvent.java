package dev.codewizz.modding.events;

import dev.codewizz.world.settlement.FarmArea;

public class CreateAreaEvent extends Event {

    private final FarmArea area;

    public CreateAreaEvent(FarmArea area) {
        this.area = area;
    }

    public FarmArea getArea() {
        return area;
    }
}
