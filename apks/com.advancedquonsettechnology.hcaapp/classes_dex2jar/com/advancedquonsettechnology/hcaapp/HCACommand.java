package com.advancedquonsettechnology.hcaapp;

public class HCACommand
{
  private int command;
  private String[] commands;
  private String resultneeded;
  
  public HCACommand(int paramInt, String[] paramArrayOfString, String paramString)
  {
    setCommand(paramInt);
    setCommands(paramArrayOfString);
    setResultneeded(paramString);
  }
  
  public int getCommand()
  {
    return this.command;
  }
  
  public String[] getCommands()
  {
    return this.commands;
  }
  
  public String getResultneeded()
  {
    return this.resultneeded;
  }
  
  public void setCommand(int paramInt)
  {
    this.command = paramInt;
  }
  
  public void setCommands(String[] paramArrayOfString)
  {
    this.commands = paramArrayOfString;
  }
  
  public void setResultneeded(String paramString)
  {
    this.resultneeded = paramString;
  }
}
