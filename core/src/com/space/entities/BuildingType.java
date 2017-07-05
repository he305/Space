package com.space.entities;

public enum BuildingType
{
    Mine;

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
}
