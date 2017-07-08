package com.space.menus;

import com.space.buildings.BuildingController;
import com.space.buildings.BuildingType;
import com.space.gamestates.SystemState;
import com.space.tools.Logger;

import java.util.ArrayList;

public class SystemContextMenu extends ContextMenu {

    private SystemContextMenuName name;

    private SystemState state;

    public SystemContextMenu(SystemState state, SystemContextMenuName name, Menu parentMenu)
    {
        super(parentMenu);
        this.state = state;
        this.name = name;

        ArrayList<String> topMenu = new ArrayList<>();

        topMenu.add("[i] SystemInfo");
        topMenu.add("Habits");

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
            case BuildingMenu:
                ArrayList<String> buildingList = BuildingType.getBuildingNames();
                addList(buildingList);
                break;
            case HabitableObjects:
                ArrayList<String> habitalObjects = state.getHabitalObjects();
                if (habitalObjects.size() == 0)
                {
                    parentMenu.setChildMenu(null);
                    return;
                }
                addList(habitalObjects);
                break;
            case ActionsWithHabit:
                ArrayList<String> actions = SystemContextMenuName.ActionsWithHabitEnum.getActionsStrings();
                addList(actions);
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
                        setChildMenu(systemObject);
                        break;
                    case "Habits":
                        SystemContextMenu habitableObjet  = new SystemContextMenu(state, SystemContextMenuName.HabitableObjects, this);
                        setChildMenu(habitableObjet);
                        break;
                    default:
                        break;
                }
                break;
            case SystemObjects:
                state.getSun().center(command.replaceAll("\\s+",""));
                break;
            case HabitableObjects:
                SystemContextMenu actionMenu = new SystemContextMenu(state, SystemContextMenuName.ActionsWithHabit, this);
                setChildMenu(actionMenu);
                break;
            case ActionsWithHabit:
                if (command.equals(SystemContextMenuName.ActionsWithHabitEnum.Build.name()))
                {
                    SystemContextMenu buildingMenu = new SystemContextMenu(state, SystemContextMenuName.BuildingMenu, this);
                    setChildMenu(buildingMenu);
                }
                else if (command.equals(SystemContextMenuName.ActionsWithHabitEnum.PlanetInfo.name()))
                {
                    SystemContextMenu parent = (SystemContextMenu) parentMenu;
                    InfoMenu infoMenu = new InfoMenu(state.getSun().getChildrenByName(parent.getItemList().get(parent.getCurrentListIndex())
                            .getSelected().toString().replaceAll("\\s+","")), this);
                    setChildMenu(infoMenu);
                }
                else if (command.equals(SystemContextMenuName.ActionsWithHabitEnum.Buildings.name()))
                {
                    SystemContextMenu parent = (SystemContextMenu) parentMenu;
                    BuildingController buildingController = state.getSun().getChildrenByName(parent.getItemList().get(parent.getCurrentListIndex())
                            .getSelected().toString().replaceAll("\\s+","")).getBuildingController();
                    if (buildingController.getBuildings().size() == 0)
                    {
                        Logger.getInstance().showInfo("No building found on " + parent.getItemList().get(parent.getCurrentListIndex()).getSelected().toString()
                            .replaceAll("\\s+",""));
                        return;
                    }
                    InfoMenu menu = new InfoMenu(buildingController, this);
                    setChildMenu(menu);
                }
                break;
            case BuildingMenu:
                SystemContextMenu parentOfParent = (SystemContextMenu) parentMenu.getParentMenu();
                state.getSun().getChildrenByName(parentOfParent.getItemList().get(parentOfParent.getCurrentListIndex()).getSelected().toString())
                        .addBuilding(BuildingType.getBuildingByString(command));
                parentMenu.setChildMenu(null);
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
