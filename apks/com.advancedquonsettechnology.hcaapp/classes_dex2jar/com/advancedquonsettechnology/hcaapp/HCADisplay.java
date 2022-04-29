package com.advancedquonsettechnology.hcaapp;

public class HCADisplay
  extends HCABase
{
  public static final int DYNAMIC_HTML = 2;
  public static final int GRAPH = 3;
  public static final int ICONS = 0;
  public static final int STATIC_HTML = 1;
  public static final int TEXT = 4;
  private int displayType;
  private String html;
  private int idCount;
  private int[] mIds;
  private int showTwoPartIcons;
  private int showTwoPartName;
  
  public HCADisplay(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, int paramInt4, int paramInt5, String paramString3, String paramString4, int paramInt6, int paramInt7, int paramInt8, int[] paramArrayOfInt)
  {
    this.name = paramString1;
    this.objectId = paramInt1;
    this.state = paramInt2;
    this.shortTapAction = paramInt3;
    this.longTapAction = paramInt4;
    this.doNotShow = paramInt5;
    this.currentIconName = paramString3;
    this.currentIconLabel = paramString4;
    this.iconname = paramString2;
    this.displayType = paramInt6;
    this.showTwoPartName = paramInt8;
    this.showTwoPartIcons = paramInt7;
    this.mIds = paramArrayOfInt;
    this.idCount = paramArrayOfInt.length;
    this.kind = 4;
  }
  
  public HCADisplay(String paramString1, String paramString2, int[] paramArrayOfInt)
  {
    this.name = paramString1;
    this.iconname = paramString2;
    this.mIds = paramArrayOfInt;
    this.idCount = paramArrayOfInt.length;
    this.kind = 4;
    this.displayType = 0;
    this.showTwoPartName = 0;
    this.showTwoPartIcons = 0;
  }
  
  public int getCount()
  {
    return this.idCount;
  }
  
  public String[] getDimDownCommand(int paramInt)
  {
    return null;
  }
  
  public String[] getDimToCommand(int paramInt)
  {
    return null;
  }
  
  public String[] getDimUpCommand(int paramInt)
  {
    return null;
  }
  
  public int getDisplayType()
  {
    return this.displayType;
  }
  
  public String getHtml()
  {
    return this.html;
  }
  
  public int getId(int paramInt)
  {
    return this.mIds[paramInt];
  }
  
  public String[] getOffCommand()
  {
    return null;
  }
  
  public String[] getOnCommand()
  {
    return null;
  }
  
  public boolean getShowTwoPartIcons()
  {
    return this.showTwoPartIcons != 0;
  }
  
  public boolean getShowTwoPartName()
  {
    return this.showTwoPartName != 0;
  }
  
  public boolean isHTMLDisplay()
  {
    return (this.displayType == 1) || (this.displayType == 2);
  }
  
  public void setDisplayType(int paramInt)
  {
    this.displayType = paramInt;
  }
  
  public void setHtml(String paramString)
  {
    this.html = paramString;
  }
  
  public void setShowTwoPartIcons(int paramInt)
  {
    this.showTwoPartIcons = paramInt;
  }
  
  public void setShowTwoPartName(int paramInt)
  {
    this.showTwoPartName = paramInt;
  }
}
