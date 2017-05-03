package com.space.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.factories.SystemFactory;
import com.space.gamestates.GameState;

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
        //gameStates.add(SystemFactory.createSolarSystem());
        gameStates.add(SystemFactory.createRandomSystem(5));
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
