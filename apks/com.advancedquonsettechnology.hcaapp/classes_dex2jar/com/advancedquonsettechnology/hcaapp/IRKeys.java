package com.advancedquonsettechnology.hcaapp;

public class IRKeys
{
  private int height;
  private boolean isbutton;
  private String label;
  private String name;
  private int width;
  private int x;
  private int y;
  
  public IRKeys(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.name = paramString1;
    this.label = paramString2;
    this.x = paramInt1;
    this.y = paramInt2;
    this.width = paramInt3;
    this.height = paramInt4;
    if (paramInt5 == 0)
    {
      this.isbutton = true;
      return;
    }
    this.isbutton = false;
  }
  
  public boolean IsButton()
  {
    return this.isbutton;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  public String getLabel()
  {
    return this.label;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
}
