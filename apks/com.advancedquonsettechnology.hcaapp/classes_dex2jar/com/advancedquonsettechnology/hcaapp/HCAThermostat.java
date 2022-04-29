package com.advancedquonsettechnology.hcaapp;

public class HCAThermostat
  extends HCADevice
{
  public int coolset = 0;
  public int coolsethigh = 0;
  public int coolsetlow = 0;
  public int currenthumid = 0;
  public int currenttemp = 0;
  public String errorText;
  public boolean fanmode = false;
  public int heatset = 0;
  public int heatsethigh = 0;
  public int heatsetlow = 0;
  public int modemode = 0;
  public String units;
  
  public HCAThermostat(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, int paramInt4, String paramString4, int paramInt5, int paramInt6)
  {
    super(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramInt3, paramInt4, paramString4, paramInt5, paramInt6);
  }
}
