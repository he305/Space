package com.space.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.space.entities.Planet;
import com.space.entities.SpaceObject;
import com.space.entities.Sun;
import com.space.gamestates.SystemState;
import com.space.tools.Constants;

import java.util.Random;

public class SystemFactory {
    //Зависимость времени обращения от расстояния
    //y = 0.738*x^0.7 - old
    //y = 0.0373 * x - 336.994

    private final static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String numbers = "0123456789";

    public static String getRandomSystemName() {
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

    public static SystemState createRandomSystem(int maxPlanets) {

        String name = getRandomSystemName();
        Random random = new Random();

        Sun sun = new Sun(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), random.nextFloat() * 4, name);

        float previousPlanet = 0;
        float previousRadius = 0;
        for (int i = 0; i < maxPlanets; i++) {
            float distance = random.nextFloat() * (10000 * (i + 1)) + previousPlanet * 2f + previousRadius * 2f + sun.getRadius();
            float radius = (float) (random.nextFloat() * (9.5) + 0.5);
            double rotationSpeed = Math.abs(0.0373 * distance - 336.994);
            sun.addChildrenRelatively(new Planet(new Vector2(distance, 0), radius, name + "-" + (i + 1)), rotationSpeed);

            previousPlanet = distance;
            previousRadius = radius;

            if (random.nextFloat() * 10 > 1 && radius > 0.8) {
                int countMoons;

                if (radius <= 5)
                    countMoons = random.nextInt(3) + 1;
                else
                    countMoons = random.nextInt(5) + 1;

                SpaceObject object = sun.getLastChild();

                float previousMoon = 0;
                for (int j = 0; j < countMoons; j++) {
                    float radiusMoon = random.nextFloat() * (radius / 2);
                    float distanceMoon = random.nextFloat() * (object.getRadius() * (i + 1)) + previousMoon + radiusMoon;

                    //Math.abs(parentObject.position.x + parentObject.radius - position.x)
                    //Может предотвратит наложение орбит (скорее нет)
                    SpaceObject parentOfParent = object.getParent();
                    if (parentOfParent.getChildren().size() != 1) {
                        if (Math.abs(distance - distanceMoon) /
                                Math.abs(parentOfParent.getChildren().get(parentOfParent.getChildren().size() - 2).getDistanceFromParent()) < 1.5)
                            break;
                    }
                    else
                    {
                        if (Math.abs(distance - distanceMoon) /
                                Math.abs(distance) < 1.5)
                            break;
                    }

                    double rotationSpeedMoon = 0.738 * Math.pow(distanceMoon, 0.7);
                    object.addChildrenRelatively(new Planet(new Vector2(distanceMoon, 0), radiusMoon, object.getName() + "-" + (j + 1)), rotationSpeedMoon);

                    previousMoon = distanceMoon + radiusMoon * 2 * Constants.planetRadiusScale * Constants.radiusScale;
                }
            }
        }

        return new SystemState(name, sun);
    }

    public static SystemState createSolarSystem() {
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

        return new SystemState("Solar system", sun);
    }
}
