/*
 * Name: Adam Prieto
 * Partner: Angi Harke-Hoseman
 * CSCI 5828 -- Foundations of Software Engineering
 * Date: 31 December 2024
 * Notes: Angi and I worked together on this assignment, so there will be
 * similarities between our assignments; however, all work consists of our own
 * contributions.
 * */

package io.collective;

import java.time.Clock;

public class SimpleAgedCache {

    // Member variable
    private int size = 0;
    private String[] keys = new String[]{};
    private String[] values = new String[]{};
    private long[] deletionTimes = new long[100];
    private Clock tempClock = Clock.systemUTC();


    public SimpleAgedCache(Clock clock) {
        tempClock = clock;
    }

    public SimpleAgedCache() {
    }



    public void put(String key, String value, int retentionInMillis) {

        // Could not get this to dynamically update array.
        // Had to update with huge array
        long timeOfCreation = tempClock.millis();
        deletionTimes[size] = retentionInMillis + timeOfCreation;


        // Add all keys to array
        String[] newKeys = new String[keys.length + 1];
        for (int i = 0; i < keys.length; i++) {
            newKeys[i] = keys[i];
        }
        newKeys[newKeys.length - 1] = key;
        this.keys = newKeys;


        // Add all values to a new array
        String[] newValues = new String[values.length + 1];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = values[i];
        }
        newValues[newValues.length - 1] = value;
        this.values = newValues;

        size++;
    }

    public void clockOffset_Actual() {
        long tempTime = tempClock.millis();
        for (int i = 0; i < size; i++) {
            if (deletionTimes[i] < tempTime) {

                if(keys[0] == null)
                    return;
                else
                    updateVars(keys[i]);
            }
        }
    }

    public void updateVars(Object key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                keys[i] = null;
                values[i] = null;
                size--;
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        clockOffset_Actual();
        return size;
    }

    public Object get(Object key) {
        if (size == 0)
            return null;

        int testSize = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == key) {
                break;
            }
            testSize++;
        }
        return values[testSize];
    }
}