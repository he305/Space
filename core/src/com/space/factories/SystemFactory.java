package com.space.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.space.entities.Planet;
import com.space.entities.Sun;
import com.space.gamestates.SystemState;

import java.util.Random;

public class SystemFactory {
    //Зависимость времени обращения от расстояния
    //y = 0.738*x^0.7

    public static SystemState createRandomSystem(int maxPlanets) {
        SystemState state = new SystemState();

        Random random = new Random();

        Sun sun = new Sun(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), (float) (0.4 + random.nextFloat() * (15 - 0.4)), "");

        float previousPlanet = 0;
        for (int i = 0; i < maxPlanets; i++) {
            float distance = random.nextFloat() * (100000 * (i + 1)) + previousPlanet + 10000;
            float radius = (float) (random.nextFloat() * (10 - 0.5));
            double rotationSpeed = 0.738 * Math.pow(distance, 0.7);
            sun.addChildrenRelatively(new Planet(new Vector2(distance, 0), radius, ""), rotationSpeed);
            previousPlanet = distance;
        }

        state.setSun(sun);
        return state;
    }

    public static SystemState createSolarSystem() {
        SystemState state = new SystemState();

        Sun sun = new Sun(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 1f, "Sun");

        sun.addChildrenRelatively(new Planet(new Vector2(9079, 0), 0.3f, "Mercury"), 88);
        sun.addChildrenRelatively(new Planet(new Vector2(16933, 0), 0.8f, "Venus"), 243);
        sun.addChildrenRelatively(new Planet(new Vector2(35732, 0), 0.6f, "Mars"), 687);
        sun.addChildrenRelatively(new Planet(new Vector2(23518, 0), 1, "Earth"), 365);
        sun.getChildrenByName("Earth").addChildrenRelatively(new Planet(new Vector2(1000, 0), 0.272f, "Moon"), 28);
        sun.addChildrenRelatively(new Planet(new Vector2(122180, 0), 11.21f, "Jupiter"), 4343);
        sun.addChildren(new Planet(new Vector2(224051, 0), 8f, "Saturn"), 10753);
        sun.addChildren(new Planet(new Vector2(450768, 0), 7f, "Uranus"), 2600);
        sun.addChildren(new Planet(new Vector2(705079, 0), 7f, "Neptune"), 3100);
        sun.addChildren(new Planet(new Vector2(927093, 0), 0.1f, "Pluto"), 5125);
        sun.addChildren(new Planet(new Vector2(927093, 0), 0.1f, "Pluto"), 5125);

        sun.getChildrenByName("Jupiter").addChildrenRelatively(new Planet(new Vector2(700, 0), 0.2f, "Europe"), 124);
        sun.getChildrenByName("Jupiter").addChildrenRelatively(new Planet(new Vector2(1500, 0), 0.5f, "Jale"), 250);
        sun.getChildrenByName("Jupiter").addChildrenRelatively(new Planet(new Vector2(3000, 0), 0.1f, "Ato"), 391);

        state.setSun(sun);

        return state;
    }
}
