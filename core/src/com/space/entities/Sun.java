package com.space.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.space.tools.Constants;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;

public class Sun extends SpaceObject {

    //Функция зависимости массы от радиуса
      //0,97x^0.676 x - масса
    //x = 1 = масса солнца = 1.9885 * 10^30
    //y = 1 = радиус солнца = 6,9551 * 10^8

    private Camera camera;
    private boolean isPaused;
    private double luminosity;

    public Sun(Vector2 position, float radius, String name) {
        super(position, radius, name);

        isPaused = false;
        this.realRadius = radius * Constants.sunRadius;

        if (radius < 0.9) {
            color = Color.RED;
            averageTemperature = ThreadLocalRandom.current().nextInt(1726, 3226);
        } else if (radius < 1.1) {
            color = Color.GOLD;
            averageTemperature = ThreadLocalRandom.current().nextInt(3226, 4726);
        } else if (radius < 1.3) {
            color = Color.YELLOW;
            averageTemperature = ThreadLocalRandom.current().nextInt(4726, 5726);
        } else if (radius < 2.1) {
            color = Color.YELLOW;
            averageTemperature = ThreadLocalRandom.current().nextInt(5726, 7226);
        } else if (radius < 7) {
            color = Color.WHITE;
            averageTemperature = ThreadLocalRandom.current().nextInt(7226, 9726);
        } else if (radius < 15) {
            color = Color.CYAN;
            averageTemperature = ThreadLocalRandom.current().nextInt(9726, 29726);
        } else {
            color = Color.BLUE;
            averageTemperature = ThreadLocalRandom.current().nextInt(29726, 59726);
        }


        this.mass = (float) (0.97 * Math.pow(this.radius, 0.676));
        this.radius *= Constants.sunToEarthRadius;
        this.mass *= Constants.sunToEarthMass;
        this.radius *= 10;
        luminosity = 4 * Math.PI * Math.pow(realRadius, 2) * Constants.STEFAN_BOLTZMANN_CONST * Math.pow(averageTemperature + 273, 4);
        out.println(realRadius);
        out.println(luminosity);
    }

    public void center(String name)
    {
        if (this.name.equals(name))
        {
            camera.position.set(position, 0);
        }
        else
        {
            for (SpaceObject child : children)
            {
                if (child.getName().equals(name))
                {
                    camera.position.set(child.getPosition(), 0);
                    break;
                }

                for (SpaceObject childOfChild : child.getChildren())
                {
                    if (childOfChild.getName().equals(name))
                    {
                        camera.position.set(childOfChild.getPosition(), 0);
                        break;
                    }
                }
            }
        }
    }

    public boolean getPause()
    {
        return isPaused;
    }

    public void setPause()
    {
        this.isPaused = !isPaused;

        for (SpaceObject child : children)
        {
            child.setUpdating(!isPaused);

            for (SpaceObject childOfChild : child.getChildren())
            {
                childOfChild.setUpdating(!isPaused);
            }
        }
    }

    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }

    public double getLuminosity()
    {
        return luminosity;
    }
}