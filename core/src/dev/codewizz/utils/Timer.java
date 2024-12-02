package dev.codewizz.utils;

public class Timer {

    private float time;
    private final float maxTime;
    private boolean finished;

    private boolean repeat;

    public Timer(float maxTime) {
        this.time = maxTime;
        this.maxTime = maxTime;

        repeat = false;
    }

    public void update(float d) {
        if (time > 0) {
            time -= d;
        } else if (!finished) {
            if (repeat) {
                time = maxTime;
            } else {
                finished = true;
            }

            timer();
        }
    }

    public void cancel() {
        finished = true;
        repeat = false;
    }

    public void reset() {
        time = maxTime;
        finished = false;
    }

    public float getTime() {
        return time;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void timer() {
    }
}
