package com.space.buildings;

import com.space.entities.SpaceObject;
import com.space.tools.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class BuildingController
{
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayDeque<Building> buildingQueue = new ArrayDeque<>();
    private final ArrayList<Building> buildingsOnConstruct = new ArrayList<>();

    protected double availableConstructionPower = 1.2; //TEST
    protected double currentConstructionPower;
    private SpaceObject object;

    public BuildingController(SpaceObject object)
    {
        this.object = object;
        currentConstructionPower = 0;
    }

    public void addBuilding(BuildingType buildingType)
    {

        Building building = new Building(buildingType, this);
        buildingQueue.add(building);

        updateConstruction();

        Logger.getInstance().showInfo(buildingType.name() + " added to building queue on " + object.getName() + ". Place in queue: " + buildingQueue.size());
    }

    public void updateConstruction()
    {
        //TODO: test this area
        /*if (buildingsOnConstruct.size() != 0)
        {
            for (Building building : buildingsOnConstruct)
            {
                double requireBuild = building.getType().getRequirePower();
                if (requireBuild <= availableConstructionPower - currentConstructionPower)
                {
                    currentConstructionPower += requireBuild - building.getAvailableConstructPower();
                    building.setAvailableConstructPower(requireBuild);
                }

                else
                {
                    building.setAvailableConstructPower(availableConstructionPower - currentConstructionPower);
                    currentConstructionPower = availableConstructionPower;
                }

            }
        }*/

        for (Building building : buildingQueue)
        {
            if (currentConstructionPower < availableConstructionPower)
            {
                double requireBuild = building.getType().getRequirePower();
                if (requireBuild <= availableConstructionPower - currentConstructionPower)
                {
                    buildingsOnConstruct.add(building);
                    currentConstructionPower += requireBuild;
                    building.setAvailableConstructPower(requireBuild);
                    buildingQueue.remove(building);

                    Logger.getInstance().showInfo(building + " on " + object.getName() + " started to build");
                }

                else
                {
                    buildingsOnConstruct.add(building);
                    building.setAvailableConstructPower(availableConstructionPower - currentConstructionPower);
                    currentConstructionPower = availableConstructionPower;
                    buildingQueue.remove(building);

                    Logger.getInstance().showInfo(building + " on " + object.getName() + " started to build");
                }
            }
            else return;
        }
    }

    public void update()
    {
        //TODO: change this
        if (buildingsOnConstruct.size() != 0)
        {
            for (int i = 0; i < buildingsOnConstruct.size(); i++)
            {
                buildingsOnConstruct.get(i).update();
            }
            if (buildingsOnConstruct.size() == 0)
                Logger.getInstance().showInfo("All buildings on " + object.getName() + " were built");
        }

        for (Building building : buildings)
            building.update();
    }

    public SpaceObject getObject()
    {
        return object;
    }

    public ArrayList<Building> getBuildings()
    {
        return buildings;
    }

    public ArrayDeque<Building> getBuildingQueue() {return buildingQueue;}

    public ArrayList<Building> getBuildingsOnConstruct() {return buildingsOnConstruct;}

    public void decreaseCurrentConstructionPower(double constructionPower)
    {
        this.currentConstructionPower -= constructionPower;
    }
}
