package com.space.menus;

import com.space.entities.BuildingType;
import com.space.gamestates.SystemState;

import java.util.ArrayList;

public class SystemContextMenu extends ContextMenu {


    private SystemContextMenuName name;

    private final ArrayList<String> topMenu;

    private SystemState state;

    public SystemContextMenu(SystemState state, SystemContextMenuName name, Menu parentMenu)
    {
        super(parentMenu);
        this.state = state;
        this.name = name;

        topMenu = new ArrayList<>();

        topMenu.add("[i] SystemInfo");
        topMenu.add("Build");

        switch (name)
        {
            case TopMenu:
                addList(topMenu);
                //itemList.get(currentListIndex).setItems(topMenu.toArray());
                break;
            case SystemObjects:
                ArrayList<String> objectNames = state.getObjectNames();
                addList(objectNames);
                break;
            case HabitableObjects:
                ArrayList<String> names = state.getHabitalObjects();
                addList(names);
                break;
        }

        //tableNames.add(itemList);

        stage.addActor(itemList.get(0));
        currentIndex = 0;
    }

    protected void act(String command)
    {
        System.out.println(command);

        switch (name)
        {
            case TopMenu:
                switch (command)
                {
                    case "[i] SystemInfo":
                        SystemContextMenu systemObject = new SystemContextMenu(state, SystemContextMenuName.SystemObjects, this);
                        this.childMenu = systemObject;
                        break;
                    case "Build":
                        SystemContextMenu habitableObjet  = new SystemContextMenu(state, SystemContextMenuName.HabitableObjects, this);
                        this.childMenu = habitableObjet;
                        break;
                    default:
                        break;
                }
                break;
            case SystemObjects:
                state.getSun().center(command.replaceAll("\\s+",""));
                break;
            case HabitableObjects:
                state.getSun().getChildrenByName(command).addBuilding(BuildingType.Mine);
        }
    }

    protected void showInfo(String command)
    {
        switch (name)
        {
            case SystemObjects:
                InfoMenu infoMenu = new InfoMenu(state.getSun().getChildrenByName(command.replaceAll("\\s+","")), this);
                setChildMenu(infoMenu);
                break;
            default:
                break;
        }
    }
}
