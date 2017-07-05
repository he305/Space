package com.space.entities;

public class Building
{
    private BuildingType type;

    private boolean isBuilt;
    private double daysToBuild;
    private double currentBuildingProgress;
    private SpaceObject object;

    public Building(BuildingType type, SpaceObject object)
    {
        this.type = type;
        daysToBuild = type.getBuildingTime();
        this.object = object;
        currentBuildingProgress = 0;
    }

    public void update()
    {
        if (!isBuilt)
        {
            currentBuildingProgress += 1.0 / 60;
            if (currentBuildingProgress >= daysToBuild)
                isBuilt = true;
        }

        else
            act();
    }

    private void act()
    {
        switch (type)
        {
            case Mine:
                object.decreaseMinerals(20.0/60);
                break;
        }
    }

    public double getCurrentBuildingProgressDelta()
    {
        return daysToBuild - currentBuildingProgress;
    }
}
