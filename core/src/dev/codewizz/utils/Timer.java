package dev.codewizz.utils;

public class Timer {

    private float time;
    private float maxTime;
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
            timer();
            if (repeat) {
                time = maxTime;
            } else {
                finished = true;
            }
        }
    }

    public float getTime() {
        return time;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void timer() {}
}
