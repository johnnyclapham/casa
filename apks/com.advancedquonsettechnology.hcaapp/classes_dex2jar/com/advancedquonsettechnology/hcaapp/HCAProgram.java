package com.advancedquonsettechnology.hcaapp;

public class HCAProgram
  extends HCABase
{
  public HCAProgram() {}
  
  public HCAProgram(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, int paramInt4, String paramString4)
  {
    super(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramInt3, paramInt4, paramString4);
  }
  
  public String[] getStartCommand()
  {
    String[] arrayOfString = new String[3];
    arrayOfString[0] = "HCAObject";
    arrayOfString[1] = "Program.Start";
    arrayOfString[2] = getCommandName();
    return arrayOfString;
  }
  
  public String[] getStopCommand()
  {
    String[] arrayOfString = new String[3];
    arrayOfString[0] = "HCAObject";
    arrayOfString[1] = "Program.Stop";
    arrayOfString[2] = getCommandName();
    return arrayOfString;
  }
  
  public int getSuspended()
  {
    return super.getSuspended();
  }
}
