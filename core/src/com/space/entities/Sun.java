package com.space.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

public class Sun extends SpaceObject {

    //Функция зависимости массы от радиуса
    //0,97x^0.676 x - масса
    //x = 1 = масса солнца = 1.9885 * 10^30
    //y = 1 = радиус солнца = 6,9551 * 10^8


    public Sun(Vector2 position, float radius, String name) {
        super(position, radius, name);
        color = Color.GOLD;
        this.mass = (float) (0.97 * Math.pow(this.radius, 0.676));
        this.radius *= Constants.sunToEarthRadius;
        this.mass *= Constants.sunToEarthMass;
        System.out.println(this.radius);
    }
}