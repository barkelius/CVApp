package com.x.swag.swag.util.log;

import com.x.swag.swag.activities.util.SystemUiHider;

/**
 * Created by axel on 2016-11-24.
 */

public class Timing {

    private final String message;
    private final long start;
    private long elapsed = 0;

    public Timing start(String message) {
        return new Timing(message);
    }

    private Timing(String message) {
        this.message = message;
        this.start = System.currentTimeMillis();
    }

    public Timing stop() {
        long curr = System.currentTimeMillis();
        Logger.timing(message + ": " + (start-curr));
        return this;
    }
    public Timing reset() {
        return start(message);
    }
}
