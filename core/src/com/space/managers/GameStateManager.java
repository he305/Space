package com.space.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.factories.GalaxyFactory;
import com.space.factories.SystemFactory;
import com.space.gamestates.GameState;
import com.space.gamestates.SystemState;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager
{
    private static List<GameState> gameStates = new ArrayList<>();
    private static int currentIndex;

    private static SpriteBatch batch;


    public static void init(SpriteBatch spriteBatch)
    {
        batch = spriteBatch;
        currentIndex = 0;
        gameStates.add(GalaxyFactory.createGalaxy(100));
        //gameStates.add(SystemFactory.createSolarSystem());
        //gameStates.add(SystemFactory.createRandomSystem(5));
    }

    //TEST
    public static void resetLevel(boolean isSolar) {
        if (!isSolar) {
            gameStates.remove(0);
            gameStates.add(SystemFactory.createRandomSystem(5));
        }
        else {
            gameStates.remove(0);
            gameStates.add(SystemFactory.createSolarSystem());
        }
    }

    public static void getIntoSystem(SystemState state)
    {
        gameStates.add(state);
        currentIndex++;
    }

    public static void getIntoGalaxy()
    {
        gameStates.remove(gameStates.get(gameStates.size()-1));
        currentIndex--;
    }

    public static void draw()
    {
        gameStates.get(currentIndex).draw(batch);
    }

    public static void update()
    {
        gameStates.get(currentIndex).update();
    }
}
