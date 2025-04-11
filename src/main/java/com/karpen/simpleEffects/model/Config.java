package com.karpen.simpleEffects.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
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

    private boolean doubleUsing;

    private String method;

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    private String msgEnable;
    private String msgDisable;

    private String errConsole;
    private String errPerms;
    private String errArgs;
    private String errCommand;
}
