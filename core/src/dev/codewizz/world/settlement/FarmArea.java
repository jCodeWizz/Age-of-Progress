package dev.codewizz.world.settlement;

import dev.codewizz.main.Main;
import dev.codewizz.modding.events.CreateAreaEvent;
import dev.codewizz.modding.events.Event;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.Animal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FarmArea {

    public static final int MAX_SIZE = 60;

    private final List<Cell> area;
    private final List<Cell> fencing;
    private final List<Cell> entrances;

    private final List<Animal> animals;
    private String preferred = "";

    private boolean valid = false;

    public FarmArea() {
        area = new CopyOnWriteArrayList<>();
        fencing = new CopyOnWriteArrayList<>();
        entrances = new CopyOnWriteArrayList<>();
        animals = new CopyOnWriteArrayList<>();
    }


    public boolean checkArea(Cell cell) {
        Cell[] cells = cell.getAllNeighbours();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] != null && (cells[i].getObject() == null || (cells[i].getObject().getId()
                    .equals("aop:fence") || cells[i].getObject().getId()
                    .equals("aop:fence-post") || cells[i].getObject().getId()
                    .equals("aop:fence-gate")))) {
                checkCell(cells[i]);

                if (checkValid()) {
                    if (Event.dispatch(new CreateAreaEvent(this))) {
                        Main.inst.world.settlement.areas.add(this);
                        return true;
                    } else {
                        return false;
                    }
                }

                area.clear();
                fencing.clear();
                entrances.clear();
            }
        }

        return false;
    }

    public boolean checkValid() {
        if (area.size() > MAX_SIZE || area.size() == 0) { return false; }
        if (entrances.isEmpty()) { return false; }

        for (Cell cell : area) {

            Cell[] cells = cell.getAllNeighbours();

            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null) {
                    if (!contains(cells[i])) {
                        this.valid = false;
                        return false;
                    }
                } else {
                    this.valid = false;
                    return false;
                }
            }
        }
        this.valid = true;
        return true;
    }

    private void checkCell(Cell cell) {
        if (contains(cell) || area.size() > MAX_SIZE) {
            return;
        }

        boolean check = this.addCell(cell);

        if (check) {
            Cell[] cells = cell.getAllNeighbours();

            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null) {
                    checkCell(cells[i]);
                }
            }
        }
    }

    public boolean contains(Cell cell) {
        return area.contains(cell) || entrances.contains(cell) || fencing.contains(cell);
    }

    /**
     * This will both add and check a cell to add to the correct list of components of a farm area.
     *
     * @param cell the cell to be added to the correct list of components.
     * @return a boolean: true if the search should continue from this cell, false if it is an edge.
     */
    private boolean addCell(Cell cell) {
        if (cell.getObject() == null) {
            area.add(cell);
            return true;
        } else {
            if (cell.getObject().getId().equals("aop:fence-gate")) {
                entrances.add(cell);
                return false;
            } else if (cell.getObject().getId().equals("aop:fence") || cell.getObject().getId()
                    .equals("aop:fence-post")) {
                fencing.add(cell);
                return false;
            } else {
                area.add(cell);
                return true;
            }
        }
    }

    public static boolean anyAvailable() {
        List<FarmArea> options = new CopyOnWriteArrayList<FarmArea>(
                Main.inst.world.settlement.areas);

        for (FarmArea area : options) {
            boolean valid = area.checkValid();

            if (!valid) {
                if (!area.getEntrances().isEmpty()) {
                    valid = area.checkArea(area.entrances.get(0));
                }
            }

            if (valid && area.hasSpace()) {
                return true;
            }
        }

        return false;
    }

    public static FarmArea findArea(Animal animal) {

        List<FarmArea> options = new CopyOnWriteArrayList<FarmArea>(
                Main.inst.world.settlement.areas);

        for (FarmArea area : options) {
            boolean valid = area.checkValid();


            if (valid && area.hasSpace()) {

                if (area.getPreferred().equals(animal.getId())) {
                    return area;
                }
            } else {
                options.remove(area);
            }
        }

        for (FarmArea area : options) {
            if (area.getPreferred().equals("")) {
                area.setPreferred(animal.getId());
                return area;
            }
        }
        for (FarmArea area : options) {
            return area;
        }

        return null;
    }

    public boolean join(Animal animal) {
        if (animals.size() < area.size()) {
            animals.add(animal);
            return true;
        }

        return false;
    }

    public boolean hasSpace() {
        return animals.size() < area.size();
    }

    public void remove(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public String getPreferred() {
        return preferred;
    }

    public void setPreferred(String s) {
        this.preferred = s;
    }

    public List<Cell> getArea() {
        return area;
    }


    public List<Cell> getFencing() {
        return fencing;
    }


    public List<Cell> getEntrances() {
        return entrances;
    }

    public boolean isValid() {
        return valid;
    }
}
