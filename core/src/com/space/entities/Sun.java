package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

public class Sun extends SpaceObject {

    //Функция зависимости массы от радиуса
    //0,97x^0.676 x - масса
    //x = 1 = масса солнца = 1.9885 * 10^30
    //y = 1 = радиус солнца = 6,9551 * 10^8


    public Sun(Vector2 position, float radius, float mass, String name) {
        super(position, radius, mass, name);
        color = Color.GOLD;
        this.radius *= Constants.sunToEarthRadius * 10;
        this.mass *= Constants.sunToEarthMass;
        System.out.println(this.radius);
    }
}