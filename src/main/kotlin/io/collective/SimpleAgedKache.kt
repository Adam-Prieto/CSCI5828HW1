package io.collective

import java.time.Clock

class SimpleAgedKache {


    // Member variable
    private var size = 0
    private var keys = arrayOf<String>()
    private var values = arrayOf<String>()
    private val deletionTimes = LongArray(100)
    private var tempClock: Clock = Clock.systemUTC()


    constructor(clock: Clock?) {
        tempClock = clock!!
    }

    constructor() {
    }




    fun put(key: Any?, value: Any?, retentionInMillis: Int) {


        // Could not get this to dynamically update array.
        // Had to update with huge array
        val timeOfCreation = tempClock.millis()
        deletionTimes[size] = retentionInMillis + timeOfCreation


        // Add all keys to array
        val newKeys = keys.toMutableList()
        newKeys.add(key.toString())
        this.keys = newKeys.toTypedArray()


        // Add all values to a new array
        val newValues = values.toMutableList()

        newValues.add(value.toString())
        this.values = newValues.toTypedArray()

        size++

    }

    fun isEmpty(): Boolean { return size == 0
    }

    fun size(): Int {
        clockOffset_Actual()
        return size
    }

    fun get(key: Any?): Any? {
        if (size == 0) return null

        var testSize = 0
        for (i in keys.indices) {
            if (keys[i] === key) {
                break
            }
            testSize++
        }
        return values[testSize]
    }

    fun updateVars(key: Any) {
        for (i in 0 until size) {
            if (keys[i] == key) {
                keys[i] = null.toString()
                values[i] = null.toString()
                size--
            }
        }
    }

    fun clockOffset_Actual() {
        val tempTime = tempClock.millis()
        for (i in 0 until size) {
            if (deletionTimes[i] < tempTime) {
                if (keys[0] == null) return
                else updateVars(keys[i])
            }
        }
    }
}