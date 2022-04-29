package com.advancedquonsettechnology.hcaapp;

public class HCADevice
  extends HCABase
{
  private int buttonCount;
  private String[] buttonNames = null;
  private int[] buttonStates = null;
  private int category;
  private int rockercount;
  private String[] rockernames = null;
  
  public HCADevice(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, int paramInt4, String paramString4, int paramInt5, int paramInt6)
  {
    super(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramInt3, paramInt4, paramString4);
    this.rockercount = paramInt5;
    this.buttonCount = paramInt6;
    if (this.rockercount > 0) {
      this.rockernames = new String[this.rockercount];
    }
    if (this.buttonCount > 0)
    {
      this.buttonNames = new String[this.buttonCount];
      this.buttonStates = new int[this.buttonCount];
    }
  }
  
  public int getButtonCount()
  {
    return this.buttonCount;
  }
  
  public String getButtonName(int paramInt)
  {
    String str = "";
    if (paramInt < this.buttonCount) {
      str = this.buttonNames[paramInt];
    }
    return str;
  }
  
  public int getButtonState(int paramInt)
  {
    int i = -1;
    if (paramInt < this.buttonCount)
    {
      if (this.buttonCount != 5) {
        break label79;
      }
      if (paramInt != 0) {
        break label31;
      }
      i = this.buttonStates[0];
    }
    label31:
    do
    {
      return i;
      if (paramInt == 1) {
        return this.buttonStates[2];
      }
      if (paramInt == 2) {
        return this.buttonStates[3];
      }
      if (paramInt == 3) {
        return this.buttonStates[4];
      }
    } while (paramInt != 4);
    return this.buttonStates[1];
    label79:
    return this.buttonStates[paramInt];
  }
  
  public int getCategory()
  {
    return this.category;
  }
  
  public int getRockerCount()
  {
    return this.rockercount;
  }
  
  public String getRockerName(int paramInt)
  {
    String str = "";
    if (paramInt < this.rockercount) {
      str = this.rockernames[paramInt];
    }
    return str;
  }
  
  public void setButtonName(int paramInt, String paramString)
  {
    if (paramInt < this.buttonCount) {
      this.buttonNames[paramInt] = paramString;
    }
  }
  
  public void setButtonState(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.buttonCount) {
      this.buttonStates[paramInt1] = paramInt2;
    }
  }
  
  public void setCategory(int paramInt)
  {
    this.category = paramInt;
  }
  
  public void setRockerName(int paramInt, String paramString)
  {
    if (paramInt < this.rockercount) {
      this.rockernames[paramInt] = paramString;
    }
  }
}
