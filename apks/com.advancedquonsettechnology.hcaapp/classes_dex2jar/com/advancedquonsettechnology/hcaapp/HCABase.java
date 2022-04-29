package com.advancedquonsettechnology.hcaapp;

public abstract class HCABase
{
  public static final int HCA_CONTROLLER = 3;
  public static final int HCA_DEVICE = 0;
  public static final int HCA_DISPLAY = 4;
  public static final int HCA_GROUP = 2;
  public static final int HCA_INERT = 12;
  public static final int HCA_IRDEVICE = 11;
  public static final int HCA_PROGRAM = 1;
  public static final int HCA_THERMOSTAT = 10;
  public static final int TAP_ACTION_NOTHING = 0;
  public static final int TAP_ACTION_POPUP = 2;
  public static final int TAP_ACTION_TOGGLE = 1;
  protected String currentDisplay;
  protected String currentIconLabel;
  protected String currentIconName;
  protected int currentIconState;
  protected int doNotShow;
  protected String folderName;
  protected String iconname;
  private int inErrorState;
  protected int index;
  protected int kind;
  protected String layoutname;
  protected int longTapAction;
  protected String name;
  protected int objectId;
  protected int shortTapAction;
  protected int state;
  private int suspended;
  
  public HCABase() {}
  
  public HCABase(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, int paramInt4, String paramString4)
  {
    this.objectId = paramInt1;
    this.folderName = paramString2;
    this.name = paramString1;
    this.layoutname = paramString4;
    setKind(paramInt4);
    this.iconname = paramString3;
    this.state = paramInt2;
    this.suspended = paramInt3;
  }
  
  private String getTwoPartName()
  {
    String str = getFolderName();
    if ((str != null) && (getName().indexOf("-") == -1)) {
      return str + " - " + getName();
    }
    return getName();
  }
  
  public String getCommandName()
  {
    if (getFolderName() == null) {
      return Integer.toString(this.objectId);
    }
    return getTwoPartName();
  }
  
  public String getCurrentDisplay()
  {
    return this.currentDisplay;
  }
  
  public String getCurrentIconLabel(boolean paramBoolean)
  {
    if ((this.currentIconLabel != null) && (this.currentIconLabel.length() > 0)) {
      return this.currentIconLabel;
    }
    if (isDevice()) {}
    for (;;)
    {
      return getDisplayName(paramBoolean);
      paramBoolean = false;
    }
  }
  
  public String getCurrentIconName()
  {
    return this.currentIconName;
  }
  
  public int getCurrentIconState()
  {
    return this.currentIconState;
  }
  
  public String[] getDimDownCommand(int paramInt)
  {
    switch (getKind())
    {
    default: 
      String[] arrayOfString5 = new String[4];
      arrayOfString5[0] = "HCAObject";
      arrayOfString5[1] = "Device.DimDownPercent";
      arrayOfString5[2] = getCommandName();
      arrayOfString5[3] = ("" + paramInt);
      return arrayOfString5;
    case 0: 
      String[] arrayOfString4 = new String[4];
      arrayOfString4[0] = "HCAObject";
      arrayOfString4[1] = "Device.DimDownPercent";
      arrayOfString4[2] = getCommandName();
      arrayOfString4[3] = ("" + paramInt);
      return arrayOfString4;
    case 1: 
      String[] arrayOfString3 = new String[4];
      arrayOfString3[0] = "HCAObject";
      arrayOfString3[1] = "Program.DimDownPercent";
      arrayOfString3[2] = getCommandName();
      arrayOfString3[3] = ("" + paramInt);
      return arrayOfString3;
    case 2: 
      String[] arrayOfString2 = new String[4];
      arrayOfString2[0] = "HCAObject";
      arrayOfString2[1] = "Group.DimDownPercent";
      arrayOfString2[2] = getCommandName();
      arrayOfString2[3] = ("" + paramInt);
      return arrayOfString2;
    }
    String[] arrayOfString1 = new String[4];
    arrayOfString1[0] = "HCAObject";
    arrayOfString1[1] = "Controller.DimDownPercent";
    arrayOfString1[2] = getCommandName();
    arrayOfString1[3] = ("" + paramInt);
    return arrayOfString1;
  }
  
  public String[] getDimToCommand(int paramInt)
  {
    switch (getKind())
    {
    default: 
      String[] arrayOfString5 = new String[4];
      arrayOfString5[0] = "HCAObject";
      arrayOfString5[1] = "Device.DimToPercent";
      arrayOfString5[2] = getCommandName();
      arrayOfString5[3] = ("" + paramInt);
      return arrayOfString5;
    case 0: 
      String[] arrayOfString4 = new String[4];
      arrayOfString4[0] = "HCAObject";
      arrayOfString4[1] = "Device.DimToPercent";
      arrayOfString4[2] = getCommandName();
      arrayOfString4[3] = ("" + paramInt);
      return arrayOfString4;
    case 1: 
      String[] arrayOfString3 = new String[4];
      arrayOfString3[0] = "HCAObject";
      arrayOfString3[1] = "Program.DimToPercent";
      arrayOfString3[2] = getCommandName();
      arrayOfString3[3] = ("" + paramInt);
      return arrayOfString3;
    case 2: 
      String[] arrayOfString2 = new String[4];
      arrayOfString2[0] = "HCAObject";
      arrayOfString2[1] = "Group.DimToPercent";
      arrayOfString2[2] = getCommandName();
      arrayOfString2[3] = ("" + paramInt);
      return arrayOfString2;
    }
    String[] arrayOfString1 = new String[4];
    arrayOfString1[0] = "HCAObject";
    arrayOfString1[1] = "Controller.DimToPercent";
    arrayOfString1[2] = getCommandName();
    arrayOfString1[3] = ("" + paramInt);
    return arrayOfString1;
  }
  
  public String[] getDimUpCommand(int paramInt)
  {
    switch (getKind())
    {
    default: 
      String[] arrayOfString5 = new String[4];
      arrayOfString5[0] = "HCAObject";
      arrayOfString5[1] = "Device.DimUpPercent";
      arrayOfString5[2] = getCommandName();
      arrayOfString5[3] = ("" + paramInt);
      return arrayOfString5;
    case 0: 
      String[] arrayOfString4 = new String[4];
      arrayOfString4[0] = "HCAObject";
      arrayOfString4[1] = "Device.DimUpPercent";
      arrayOfString4[2] = getCommandName();
      arrayOfString4[3] = ("" + paramInt);
      return arrayOfString4;
    case 1: 
      String[] arrayOfString3 = new String[4];
      arrayOfString3[0] = "HCAObject";
      arrayOfString3[1] = "Program.DimUpPercent";
      arrayOfString3[2] = getCommandName();
      arrayOfString3[3] = ("" + paramInt);
      return arrayOfString3;
    case 2: 
      String[] arrayOfString2 = new String[4];
      arrayOfString2[0] = "HCAObject";
      arrayOfString2[1] = "Group.DimUpPercent";
      arrayOfString2[2] = getCommandName();
      arrayOfString2[3] = ("" + paramInt);
      return arrayOfString2;
    }
    String[] arrayOfString1 = new String[4];
    arrayOfString1[0] = "HCAObject";
    arrayOfString1[1] = "Controller.DimUpPercent";
    arrayOfString1[2] = getCommandName();
    arrayOfString1[3] = ("" + paramInt);
    return arrayOfString1;
  }
  
  public String getDisplayName(boolean paramBoolean)
  {
    if (paramBoolean) {
      return getTwoPartName();
    }
    return getName();
  }
  
  public boolean getDoNotShow()
  {
    return this.doNotShow != 0;
  }
  
  public String getFolderName()
  {
    if (this.folderName != null) {
      return this.folderName;
    }
    return this.currentDisplay;
  }
  
  public String getIconName()
  {
    if ((this.currentIconName != null) && (this.currentIconName.length() > 0)) {
      return this.currentIconName;
    }
    return this.iconname;
  }
  
  public int getIconState()
  {
    if ((this.currentIconName != null) && (this.currentIconName.length() > 0) && (this.currentIconState >= 0)) {
      return this.currentIconState;
    }
    return this.state;
  }
  
  public int getInErrorState()
  {
    return this.inErrorState;
  }
  
  public int getIndex()
  {
    return this.index;
  }
  
  public int getKind()
  {
    return this.kind;
  }
  
  public String getLayoutName()
  {
    return this.layoutname;
  }
  
  public int getLongTapAction()
  {
    return this.longTapAction;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getObjectId()
  {
    return this.objectId;
  }
  
  public String[] getOffCommand()
  {
    switch (getKind())
    {
    default: 
      String[] arrayOfString5 = new String[3];
      arrayOfString5[0] = "HCAObject";
      arrayOfString5[1] = "Device.Off";
      arrayOfString5[2] = getCommandName();
      return arrayOfString5;
    case 0: 
      String[] arrayOfString4 = new String[3];
      arrayOfString4[0] = "HCAObject";
      arrayOfString4[1] = "Device.Off";
      arrayOfString4[2] = getCommandName();
      return arrayOfString4;
    case 1: 
      String[] arrayOfString3 = new String[3];
      arrayOfString3[0] = "HCAObject";
      arrayOfString3[1] = "Program.Off";
      arrayOfString3[2] = getCommandName();
      return arrayOfString3;
    case 2: 
      String[] arrayOfString2 = new String[3];
      arrayOfString2[0] = "HCAObject";
      arrayOfString2[1] = "Group.Off";
      arrayOfString2[2] = getCommandName();
      return arrayOfString2;
    }
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "HCAObject";
    arrayOfString1[1] = "Controller.Off";
    arrayOfString1[2] = getCommandName();
    return arrayOfString1;
  }
  
  public String[] getOnCommand()
  {
    switch (getKind())
    {
    default: 
      String[] arrayOfString5 = new String[3];
      arrayOfString5[0] = "HCAObject";
      arrayOfString5[1] = "Device.On";
      arrayOfString5[2] = getCommandName();
      return arrayOfString5;
    case 0: 
      String[] arrayOfString4 = new String[3];
      arrayOfString4[0] = "HCAObject";
      arrayOfString4[1] = "Device.On";
      arrayOfString4[2] = getCommandName();
      return arrayOfString4;
    case 1: 
      String[] arrayOfString3 = new String[3];
      arrayOfString3[0] = "HCAObject";
      arrayOfString3[1] = "Program.On";
      arrayOfString3[2] = getCommandName();
      return arrayOfString3;
    case 2: 
      String[] arrayOfString2 = new String[3];
      arrayOfString2[0] = "HCAObject";
      arrayOfString2[1] = "Group.On";
      arrayOfString2[2] = getCommandName();
      return arrayOfString2;
    }
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "HCAObject";
    arrayOfString1[1] = "Controller.On";
    arrayOfString1[2] = getCommandName();
    return arrayOfString1;
  }
  
  public int getShortTapAction()
  {
    return this.shortTapAction;
  }
  
  public int getState()
  {
    return this.state;
  }
  
  public int getSuspended()
  {
    return this.suspended;
  }
  
  public boolean isDevice()
  {
    return this.kind != 4;
  }
  
  public boolean isDisplay()
  {
    return this.kind == 4;
  }
  
  public boolean isIRDevice()
  {
    return this.kind == 11;
  }
  
  public boolean isInert()
  {
    return this.kind == 12;
  }
  
  public boolean isProgram()
  {
    return this.kind == 1;
  }
  
  public boolean isThermostat()
  {
    return this.kind == 10;
  }
  
  public void setCurrentDisplay(String paramString)
  {
    this.currentDisplay = paramString;
  }
  
  public void setCurrentIconLabel(String paramString)
  {
    this.currentIconLabel = paramString;
  }
  
  public void setCurrentIconName(String paramString)
  {
    this.currentIconName = paramString;
  }
  
  public void setCurrentIconState(int paramInt)
  {
    this.currentIconState = paramInt;
  }
  
  public void setDoNotShow(int paramInt)
  {
    this.doNotShow = paramInt;
  }
  
  public void setFolderName(String paramString)
  {
    this.folderName = paramString;
  }
  
  public void setInErrorState(int paramInt)
  {
    this.inErrorState = paramInt;
  }
  
  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }
  
  public void setKind(int paramInt)
  {
    if ((this.layoutname == null) || (this.layoutname.length() == 0) || (this.layoutname.equals("None")))
    {
      this.kind = 12;
      return;
    }
    if (this.layoutname.startsWith("thermostat"))
    {
      this.kind = 10;
      return;
    }
    if (this.layoutname.startsWith("irdevice"))
    {
      this.kind = 11;
      return;
    }
    this.kind = paramInt;
  }
  
  public void setLongTapAction(int paramInt)
  {
    this.longTapAction = paramInt;
  }
  
  public void setObjectId(int paramInt)
  {
    this.objectId = paramInt;
  }
  
  public void setShortTapAction(int paramInt)
  {
    this.shortTapAction = paramInt;
  }
  
  public void setState(int paramInt)
  {
    this.state = paramInt;
  }
  
  public void setSuspended(int paramInt)
  {
    this.suspended = paramInt;
  }
  
  public String toString()
  {
    return this.name;
  }
}
