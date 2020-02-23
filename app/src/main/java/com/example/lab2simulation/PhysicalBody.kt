package com.example.lab2simulation

import kotlin.math.*

class PhysicalBody(x0:Float,y0:Float,v0:Float,a0:Float,size:Float,mass:Float) {
    private var x = x0
    private var y = y0
    private var vx=v0*cos(a0)
    private var vy=v0*sin(a0)
    private var g=9.87.toFloat()
    private var C=0.15f
    private var rho=1.29f
    private var size=size
    private var mass=mass

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
        var k=0.5f*C*rho*size/mass
        x += vx*time
        y += (vy*time)
        var v=sqrt(vx*vx+vy*vy)
        vx-=k*vx*v*time
        vy -= (g+k*vy*v)*time
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