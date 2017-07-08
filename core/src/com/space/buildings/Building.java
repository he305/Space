package com.space.buildings;

import com.space.tools.Logger;

public class Building
{
    private final BuildingType type;

    private boolean isBuilt;
    private final double daysToBuild;
    private double currentBuildingProgress;
    private final BuildingController buildingController;

    private double availableConstructPower;

    public Building(BuildingType type, BuildingController buildingController)
    {
        this.type = type;
        daysToBuild = type.getBuildingTime();
        this.buildingController = buildingController;
        currentBuildingProgress = 0;
        isBuilt = false;
    }

    public void update()
    {
        if (!isBuilt)
        {
            currentBuildingProgress += (availableConstructPower/type.getRequirePower()) * (buildingController.getObject().getAcceleration() / 60);
            //System.out.println(availableConstructPower/type.getRequirePower());
            if (currentBuildingProgress >= daysToBuild)
            {
                isBuilt = true;
                buildingController.getBuildings().add(this);
                buildingController.getBuildingsOnConstruct().remove(this);
                buildingController.decreaseCurrentConstructionPower(availableConstructPower);

                Logger.getInstance().showInfo(this.type.name() + " on " + buildingController.getObject().getName() + " was built");

                buildingController.updateConstruction();
            }
        }

        else
            act();
    }

    private void act()
    {
        switch (type)
        {
            case Mine:
                buildingController.getObject().decreaseMinerals(20.0/60);
                break;
        }
    }

    public String toString()
    {
        return type.name();
    }

    public double getCurrentBuildingProgressDelta()
    {
        return daysToBuild - currentBuildingProgress;
    }

    public BuildingController getObject()
    {
        return buildingController;
    }

    public BuildingType getType()
    {
        return type;
    }

    public void setAvailableConstructPower(double constructPower)
    {
        availableConstructPower = constructPower;
    }

    public double getAvailableConstructPower()
    {
        return availableConstructPower;
    }
}
