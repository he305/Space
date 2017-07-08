package com.space.menus;

import java.util.ArrayList;

public enum SystemContextMenuName
{
    TopMenu,
    SystemObjects,
    HabitableObjects,
    BuildingMenu,
    ActionsWithHabit;

    public enum ActionsWithHabitEnum
    {
        Build,
        PlanetInfo,
        Buildings;

        public static ArrayList<String> getActionsStrings()
        {
            ArrayList<String> actions = new ArrayList<>();

            for (ActionsWithHabitEnum actionsWithHabit : ActionsWithHabitEnum.values())
            {
                actions.add(actionsWithHabit.name());
            }
            return actions;
        }
    }
}
