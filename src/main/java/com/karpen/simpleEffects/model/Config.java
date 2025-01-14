package com.karpen.simpleEffects.model;

public class Config {
    private int countCherry;
    private int countEndrod;
    private int countTotem;

    private String msgCherry;
    private String msgEndRod;
    private String msgTotem;

    private String msgDisCherry;
    private String msgDisEndRod;
    private String msgDisTotem;

    private String errConsole;
    private String errArgs;
    private String errCommand;

    public int getCountCherry() {
        return countCherry;
    }

    public int getCountEndrod() {
        return countEndrod;
    }

    public int getCountTotem() {
        return countTotem;
    }

    public void setCountCherry(int countCherry) {
        this.countCherry = countCherry;
    }

    public void setCountEndrod(int countEndrod) {
        this.countEndrod = countEndrod;
    }

    public void setCountTotem(int countTotem) {
        this.countTotem = countTotem;
    }

    public String getErrConsole() {
        return errConsole;
    }

    public String getMsgCherry() {
        return msgCherry;
    }

    public String getMsgDisCherry() {
        return msgDisCherry;
    }

    public String getMsgDisEndRod() {
        return msgDisEndRod;
    }

    public String getErrArgs() {
        return errArgs;
    }

    public String getMsgDisTotem() {
        return msgDisTotem;
    }

    public String getMsgEndRod() {
        return msgEndRod;
    }

    public String getMsgTotem() {
        return msgTotem;
    }

    public String getErrCommand() {
        return errCommand;
    }

    public void setMsgCherry(String msgCherry) {
        this.msgCherry = msgCherry;
    }

    public void setMsgDisCherry(String msgDisCherry) {
        this.msgDisCherry = msgDisCherry;
    }

    public void setMsgDisEndRod(String msgDisEndRod) {
        this.msgDisEndRod = msgDisEndRod;
    }

    public void setMsgDisTotem(String msgDisTotem) {
        this.msgDisTotem = msgDisTotem;
    }

    public void setMsgEndRod(String msgEndRod) {
        this.msgEndRod = msgEndRod;
    }

    public void setMsgTotem(String msgTotem) {
        this.msgTotem = msgTotem;
    }

    public void setErrArgs(String errArgs) {
        this.errArgs = errArgs;
    }

    public void setErrConsole(String errConsole) {
        this.errConsole = errConsole;
    }

    public void setErrCommand(String errCommand) {
        this.errCommand = errCommand;
    }
}
