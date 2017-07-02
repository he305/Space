package com.space.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.space.entities.Sun;
import com.space.entities.System;
import com.space.gamestates.GalaxyState;
import com.space.gamestates.SystemState;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GalaxyFactory
{

    private final static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String numbers = "0123456789";

    public static String getRandomGalaxyName() {
        Random random = new Random();
        int charCount = random.nextInt(5) + 1;
        int numbersCount = random.nextInt(5) + 1;

        StringBuilder sb = new StringBuilder(charCount + numbersCount);

        for (int i = 0; i < charCount; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        sb.append('-');

        for (int i = 0; i < numbersCount; i++) {
            sb.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return sb.toString();
    }
    public static GalaxyState createGalaxy(int countSystems)
    {
        String name = getRandomGalaxyName();
        GalaxyState galaxyState = new GalaxyState(name, countSystems);
        ArrayList<System> systems = new ArrayList<>(countSystems);

        for (int i = 0; i < countSystems; i++)
        {
            float distance_from_center;
            SystemState systemState = SystemFactory.createRandomSystem(ThreadLocalRandom.current().nextInt(1, 8));

            Sun sun = systemState.getSun();

            Vector2 position = new Vector2();

            if (i == 0)
            {
                distance_from_center = 0;
                position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            }
            else
            {
                distance_from_center = sun.getRadius() * 2 + ThreadLocalRandom.current().nextInt((int) sun.getRadius() * 2, (int) sun.getRadius() * 2 * 2)
                        + systems.get(i - 1).getDistance_from_center() + systems.get(i-1).getRadius()*2;

                int alpha = ThreadLocalRandom.current().nextInt(0, 361);

                position.x = systems.get(0).getPosition().x + (float)(distance_from_center * Math.cos(Math.toRadians(alpha)));
                position.y = systems.get(0).getPosition().y + (float)(distance_from_center * Math.sin(Math.toRadians(alpha)));
            }

            systems.add(new System(position, sun.getColor(), sun.getRadius(), distance_from_center, systemState));
        }

        galaxyState.addSystems(systems);
        return galaxyState;
    }
}
