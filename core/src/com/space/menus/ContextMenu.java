package com.space.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.space.tools.Constants;

import java.util.ArrayList;

public abstract class ContextMenu extends Menu
{
    protected final Stage stage;
    protected final ArrayList<List> itemList;
    protected int currentIndex = 0;

    protected final Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
    protected final TextureRegion background = new TextureRegion(new Texture("background.png"), 0, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
    protected final Image backgroundImage = new Image(background);
    protected int currentListIndex = 0;

    protected double menuPerList;
    //protected Table tableNames;

    public ContextMenu(Menu parentMenu)
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        if (Gdx.graphics.getWidth() == 800)
            menuPerList = Constants.MAX_MENU_ITEMS_800x600;

        itemList = new ArrayList<>();

        stage.addActor(backgroundImage);
        backgroundImage.setPosition(2 * Gdx.graphics.getWidth() / 3, 0);

        this.parentMenu = parentMenu;
    }

    protected void addList(ArrayList<String> items)
    {

        int listCount = (int) Math.ceil(items.size() / menuPerList);
        for (int i = 0, k = 0; i < listCount; i++)
        {
            ArrayList<String> currentList = new ArrayList<>();
            for (int j = 0; j < menuPerList; j++, k++) {
                currentList.add(items.get(k));
                if (k == items.size() - 1)
                    break;
            }

            List list = new List(skin);

            list.setPosition(2 * Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight()/20);
            list.setBounds(2 * Gdx.graphics.getWidth() / 3,Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth() / 3,18*Gdx.graphics.getHeight()/20);
            list.setCullingArea(new Rectangle(2 * Gdx.graphics.getWidth() / 3,Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth() / 3,18*Gdx.graphics.getHeight()/20));

            list.setItems(currentList.toArray());

            itemList.add(list);
        }
    }

    protected void changeCurrentList(int previousIndex)
    {
        itemList.get(previousIndex).remove();

        stage.addActor(itemList.get(currentListIndex));
    }

    public void draw()
    {
        if (childMenu == null)
        {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
        else
            childMenu.draw();
    }

    public void handleInput() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                currentIndex--;
                if (currentIndex < 0)
                {
                    int temp = currentListIndex;
                    currentListIndex--;
                    if (currentListIndex < 0)
                        currentListIndex = itemList.size() - 1;

                    changeCurrentList(temp);

                    currentIndex = itemList.get(currentListIndex).getItems().size - 1;
                }

                itemList.get(currentListIndex).setSelectedIndex(currentIndex);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                currentIndex++;

                if (currentIndex > itemList.get(currentListIndex).getItems().size - 1) {
                    currentIndex = 0;

                    int temp = currentListIndex;
                    currentListIndex++;
                    if (currentListIndex > itemList.size() - 1)
                        currentListIndex = 0;

                    changeCurrentList(temp);

                }

                itemList.get(currentListIndex).setSelectedIndex(currentIndex);

            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
                if (parentMenu == null)
                    return;
                parentMenu.setChildMenu(null);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                act(itemList.get(currentListIndex).getSelected().toString());
            }

            //INFO
            if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                showInfo(itemList.get(currentListIndex).getSelected().toString());
            }
    }

    protected abstract void act(String command);

    protected abstract void showInfo(String command);

    public ArrayList<List> getItemList()
    {
        return itemList;
    }

    public int getCurrentListIndex()
    {
        return currentListIndex;
    }
}
