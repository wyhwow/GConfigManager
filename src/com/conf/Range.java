package com.conf;

/**
 *
 * Created by chenmj on 2017/1/15.
 */
public class Range<T> {
    private boolean eqMin = false;
    private boolean eqMax = false;
    private T min;
    private T max;
    public Range(T min, T max, boolean eqMin, boolean eqMax) throws IllegalArgumentException {
        if (min instanceof Integer) {   // 只有int型支持闭区间
            this.eqMin = eqMin;
            this.eqMax = eqMax;
        } else if (!(min instanceof Double)) {
            throw new IllegalArgumentException("Only int and double support \"range\"!");
        }
        this.min = min;
        this.max = max;
    }

    public boolean canEqMin() {
        return eqMin;
    }

    public boolean canEqMax() {
        return eqMax;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
