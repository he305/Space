package com.space.tools;

public class Constants {
    public final static double G = 6.67E-5;
    public final static double sunMass = 2E+30;
    public final static double sunRadius = 695700;
    public final static double earthRadius = 6378;
    public final static double earthMass = 5.97E+24;

    //used only for stars
    public final static double sunToEarthRadius = sunRadius / earthRadius;
    public final static double sunToEarthMass = sunMass / earthMass;

    public final static int radiusScale = 10;
    public final static int planetRadiusScale = 50;

    //MENU
    public final static int MAX_MENU_ITEMS_800x600 = 27;

    public final static double STEFAN_BOLTZMANN_CONST = 5.6703E-8;

    public final static double earthToSunDistance = 149600000;

}
