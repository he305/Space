package com.space.menus;

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

        switch (name)
        {
            case TopMenu:
                itemList.setItems(topMenu.toArray());
                break;
            case SystemObjects:
                ArrayList<String> objectNames = new ArrayList<>();
                objectNames.add(state.getSun().getName());
                state.getObjectNames(state.getSun(), objectNames, 1);
                itemList.setItems(objectNames.toArray());
                break;
        }

        tableNames.add(itemList);

        stage.addActor(tableNames);

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
                        SystemContextMenu systemContextMenu = new SystemContextMenu(state, SystemContextMenuName.SystemObjects, this);
                        this.childMenu = systemContextMenu;
                        break;
                    default:
                        break;
                }
                break;
            case SystemObjects:
                state.getSun().center(command.replaceAll("\\s+",""));
                break;
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
