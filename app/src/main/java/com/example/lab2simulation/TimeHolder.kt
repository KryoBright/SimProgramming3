package com.example.lab2simulation

class TimeHolder {
    private var timeInStep=10f
    private var stepLen=20
    private var paused=false
    var currentTime=0f

    fun getTimeInStep():Float
    {
        return timeInStep
    }

    fun countTimeInStep(totalTime:Float,stepsAmount:Float)
    {
        timeInStep=totalTime/stepsAmount
    }

    fun getStepLen():Int
    {
        return stepLen
    }

    fun isPaused():Boolean
    {
        return paused
    }

    fun pauseUnpause()
    {
        paused=!paused
    }
}