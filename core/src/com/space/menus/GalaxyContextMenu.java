package com.space.menus;

import com.space.gamestates.GalaxyState;

import java.util.ArrayList;

public class GalaxyContextMenu extends ContextMenu
{

    final GalaxyState state;
    final GalaxyContextMenuName name;

    public GalaxyContextMenu(GalaxyState state, GalaxyContextMenuName name, Menu parentMenu) {
        super(parentMenu);
        this.state = state;
        this.name = name;

        ArrayList<String> topMenu = new ArrayList<>();
        topMenu.add("Galaxy Info");

        switch (name)
        {
            case TopMenu:
                addList(topMenu);
                break;
            case GalaxyObjects:
                ArrayList<String> objectNames = state.getObjectNames();
                addList(objectNames);
                break;
        }
        stage.addActor(itemList.get(0));
        currentIndex = 0;

    }

    @Override
    protected void act(String command) {
        System.out.println(command);
        switch (name)
        {
            case TopMenu:
                switch (command)
                {
                    case "Galaxy Info":
                        this.childMenu = new GalaxyContextMenu(state, GalaxyContextMenuName.GalaxyObjects, this);
                        break;
                    default:
                        break;
                }
                break;
            case GalaxyObjects:
                state.getIntoSystem(command);
                break;
        }
    }

    @Override
    protected void showInfo(String command) {

    }
}
