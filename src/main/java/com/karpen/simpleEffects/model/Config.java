package com.karpen.simpleEffects.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
    private boolean oldVer;

    private String notAvailableMsg;
    private String unsupportedName;

    private int countCherry;
    private int countEndrod;
    private int countTotem;
    private int countHeart;
    private int countPale;
    private int countNotes;
    private int countPurple;

    private boolean rightsUsing;

    private String rightsCherry;
    private String rightsEndRod;
    private String rightsTotem;
    private String rightsHeart;
    private String rightsPale;
    private String rightsNotes;
    private String rightsPurple;
    private String rightsCloud;
    private String rightsTotemSpiral;

    private boolean doubleUsing;

    private String method;

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    private String menuName;
    private String itemsEnable;
    private String itemsDisable;

    private String itemCherryName;
    private String itemEndRodName;
    private String itemTotemName;
    private String itemHeartName;
    private String itemPaleName;
    private String itemNotesName;
    private String itemPurpleName;
    private String itemCloudName;
    private String itemTotemSpiralName;

    private String warning;

    private String msgEnable;
    private String msgDisable;

    private String errConsole;
    private String errPerms;
    private String errArgs;
    private String errCommand;
}
