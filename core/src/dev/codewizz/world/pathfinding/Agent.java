package dev.codewizz.world.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.Animal;
import dev.codewizz.world.objects.TaskableObject;

public class Agent {

    private Cell previousCell;
    public Queue<Cell> path = new Queue<Cell>();
    public boolean moving = false;
    private CellGraph graph;
    private Vector2 dir;

    public Cell goal;
    public GameObject goalObject;

    private final TaskableObject object;

    public Agent(TaskableObject object) {
        dir = new Vector2();
        this.object = object;

        graph = Main.inst.world.cellGraph;
    }

    public void update(float d, Vector2 loc) {

        if (graph == null) {
            graph = Main.inst.world.cellGraph;
        }

        checkDistance(loc);
        if (moving) {
            setSpeedToNextCell(loc);
        }
    }

    private void checkDistance(Vector2 loc) {
        if (path.size > 0) {
            Vector2 target = path.first().getMiddlePoint();
            if (Vector2.dst(loc.x, loc.y, target.x, target.y) < 2.5f) {
                reachGoal(loc);
            }
        }
    }

    private void reachGoal(Vector2 loc) {
        Cell nextCell = path.first();

        this.previousCell = nextCell;
        path.removeFirst();

        if (path.size == 0 || (goalObject != null && loc.dst2(goalObject.getX(),
                goalObject.getY()) < 2.5f)) {
            reach();
        } else {
            if (goalObject != null) {
                setGoal(Main.inst.world.getCell(goalObject.getX(), goalObject.getY()));
            } else {
                if (Main.inst.world.cellGraph.getConnections(nextCell).size < 8) {
                    Cell goal = path.last();
                    stop();
                    setGoal(goal);
                }
            }
        }
    }

    public Vector2 getDir() {
        return dir;
    }

    private void reach() {
        stop();
        onReach();
    }

    public void stop() {
        path.clear();
        dir.x = 0;
        dir.y = 0;
        moving = false;
        this.goalObject = null;
    }

    public void onReach() {

    }

    public void followObject(GameObject object) {
        this.goalObject = object;
        setGoal(Main.inst.world.getCell(goalObject.getX(), goalObject.getY()));
    }

    public boolean setGoal(Cell goal) {
        stop();
        this.goal = goal;

        previousCell = Main.inst.world.getCell(object.getX(), object.getY());
        GraphPath<Cell> graphPath = null;
        try {
            graphPath = graph.findPath(previousCell, goal);
        } catch (Exception e) {
            Logger.error("Error during pathfinding: ");
            e.printStackTrace();
            stop();
            return false;
        }
        for (int i = 1; i < graphPath.getCount(); i++) {

            if (object instanceof Animal) {
                if (graphPath.get(i).getObject() != null) {

                    if (graphPath.get(i).getObject().getId()
                            .equals("aop:fence-gate") && !object.isTasked()) {
                        stop();
                        return false;
                    }
                }
            }

            path.addLast(graphPath.get(i));
        }

        int s = graph.getConnections(goal).size;

        if (!path.isEmpty() && s > 0) {
            setSpeedToNextCell(object.getFootPoint());
            moving = true;

            return true;
        }

        return false;
    }

    private void setSpeedToNextCell(Vector2 loc) {
        Cell nextCell = path.first();
        float angle = MathUtils.atan2(nextCell.getMiddlePoint().y - loc.y,
                nextCell.getMiddlePoint().x - loc.x);
        dir.x = MathUtils.cos(angle);
        dir.y = MathUtils.sin(angle);
    }
}
