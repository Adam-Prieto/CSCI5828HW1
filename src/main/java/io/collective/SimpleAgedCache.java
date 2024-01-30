package io.collective;

import java.time.Clock;

public class SimpleAgedCache {

    private boolean isEmpty = true;
    private int size = 0;

    private Object[] keys;
    private Object[] values;
    private int[] times;

    public SimpleAgedCache(Clock clock) {
    }

    public SimpleAgedCache() {
    }

    public void put(Object key, Object value, int retentionInMillis) {

        if (isEmpty) {
            isEmpty = false;
            this.size++;


            Object[] newKeys={key};
            Object[] newValues = {value};
            int[] newTimes = {retentionInMillis};

            this.keys = newKeys;
            this.values = newValues;
            this.times = newTimes;
        }



        else {
            // Add all keys to array
            Object[] newKeys = new Object[keys.length + 1];
            for (int i = 0; i < keys.length; i++) {
                newKeys[i] = keys[i];
            }
            newKeys[newKeys.length - 1] = key;
            this.keys = newKeys;


            // Add all values to a new array
            Object[] newValues = new Object[values.length + 1];
            for (int i = 0; i < values.length; i++) {
                newValues[i] = values[i];
            }
            newValues[newValues.length - 1] = value;
            this.values = newValues;

            // Add retentionInMillis to newArray
            int[] newTimes = new int[times.length + 1];
            for (int i = 0; i < times.length; i++) {
                newTimes[i] = times[i];
            }
            newTimes[newTimes.length - 1] = retentionInMillis;
            this.times = newTimes;

            this.size++;
        }

    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int size() {
        return this.size;
    }

    public Object get(Object key) {

        if(values == null)
            return null;

        int testSize = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == key)
            {
                break;
            }
            testSize++;
        }
        return values[testSize];

    }

    public Object get() {
        return null;
    }
}