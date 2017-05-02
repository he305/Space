package com.space.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        gameStates.add(new SystemState());
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
