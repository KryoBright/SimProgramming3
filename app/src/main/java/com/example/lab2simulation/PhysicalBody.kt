package com.example.lab2simulation

import kotlin.math.*

class PhysicalBody(x0:Float,y0:Float,v0:Float,a0:Float) {
    private var x = x0
    private var y = y0
    private var vx=v0*cos(a0)
    private var vy=v0*sin(a0)
    private var g=9.87.toFloat()

    fun getMaxY():Float
    {
        return y+vy*vy/(2*g)
    }

    fun getMaxX():Float
    {
        return vx*getMaxTime()
    }

    fun moveFor(time:Float)
    {
        x += vx*time
        y += (vy*time)
        vy -= g*time
    }

    fun getX():Float
    {
        return x
    }

    fun getY():Float
    {
        return y
    }

    fun isTouchingGround():Boolean
    {
        return y <= 0
    }

    fun getMaxTime():Float
    {
        return (vy+sqrt(2*g*y+vy*vy))/g
    }
}