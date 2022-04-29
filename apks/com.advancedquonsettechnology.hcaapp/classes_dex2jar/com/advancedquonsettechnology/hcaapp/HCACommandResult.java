package com.advancedquonsettechnology.hcaapp;

public class HCACommandResult
{
  private int command;
  private String resmsg;
  
  public HCACommandResult(int paramInt, String paramString)
  {
    setCommand(paramInt);
    setResmsg(paramString);
  }
  
  public int getCommand()
  {
    return this.command;
  }
  
  public String getResmsg()
  {
    return this.resmsg;
  }
  
  public void setCommand(int paramInt)
  {
    this.command = paramInt;
  }
  
  public void setResmsg(String paramString)
  {
    this.resmsg = paramString;
  }
}
