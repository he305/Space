package com.space.buildings;

import java.util.ArrayList;

public enum BuildingType
{
    Mine;

    //Requirements for 100% building speed
    public double getRequirePower()
    {
        switch (this)
        {
            case Mine:
                return 0.5;
        }
        return 0;
    }

    //Time for building (in days)
    public int getBuildingTime()
    {
        switch (this)
        {
            case Mine:
                return 20;
        }

        return 0;
    }

    public static BuildingType getBuildingByString(String build)
    {
        for (BuildingType buildingType : BuildingType.values())
        {
            if (buildingType.name().equals(build))
                return buildingType;
        }
        return null;
    }

    public static ArrayList<String> getBuildingNames()
    {
        ArrayList<String> names = new ArrayList<>();

        for (BuildingType buildingType : BuildingType.values())
        {
            names.add(buildingType.name());
        }
        return names;
    }
}
