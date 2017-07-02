package com.space.menus;

import com.space.gamestates.GalaxyState;

import java.util.ArrayList;

public class GalaxyContextMenu extends ContextMenu
{

    GalaxyState state;
    GalaxyContextMenuName name;

    private final ArrayList<String> topMenu;

    public GalaxyContextMenu(GalaxyState state, GalaxyContextMenuName name, Menu parentMenu) {
        super(parentMenu);
        this.state = state;
        this.name = name;

        topMenu = new ArrayList<>();
        topMenu.add("Galaxy Info");

        switch (name)
        {
            case TopMenu:
                itemList.setItems(topMenu.toArray());
                break;
            case GalaxyObjects:
                ArrayList<String> objectNames = state.getObjectNames();
                itemList.setItems(objectNames.toArray());
                break;
        }

        tableNames.add(itemList);
        stage.addActor(tableNames);
        currentIndex = 0;

    }

    @Override
    protected void act(String command) {
        switch (name)
        {
            case TopMenu:
                switch (command)
                {
                    case "Galaxy Info":
                        GalaxyContextMenu galaxyContextMenu = new GalaxyContextMenu(state, GalaxyContextMenuName.GalaxyObjects, this);
                        this.childMenu = galaxyContextMenu;
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
