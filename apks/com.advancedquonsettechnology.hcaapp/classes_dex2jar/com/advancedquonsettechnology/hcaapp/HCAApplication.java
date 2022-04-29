package com.advancedquonsettechnology.hcaapp;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.AbsoluteLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HCAApplication
  extends Application
{
  public static final int AR_HELP = 1;
  public static final int AR_MODES = 2;
  public static final int AR_PREFS = 3;
  public static final int AR_SHOW_DEVICE = 4;
  public static final int AR_SHOW_DISPLAY = 5;
  public static final int BG_COLOR_DEFAULT = 9408;
  public static final String BG_COLOR_KEY = "app_bg_color";
  public static final int FG_COLOR_DEFAULT = -1;
  public static final String FG_COLOR_KEY = "app_fg_color";
  public static final int FOLDER_ON_COLOR_DEFAULT = -256;
  public static final String FOLDER_ON_COLOR_KEY = "folder_on_color";
  public static boolean IsSecondary = false;
  private static final int PROGRESS_CONNECTION_LOST = 3;
  private static final int PROGRESS_CONNECT_FAIL = 2;
  private static final int PROGRESS_CONNECT_OK = 1;
  private static final int PROGRESS_IR_KEYPAD = 5;
  private static final int PROGRESS_PING_FAIL = 4;
  private static final int PROGRESS_SET_MODE = 6;
  private static final int PROGRESS_UPDATE_MESSAGE = 0;
  public static final int SUSPEND_MODE_BG_COLOR_DEFAULT = -16711936;
  public static final String SUSPEND_MODE_BG_COLOR_KEY = "suspend_mode_bg_color";
  public static final int SUSPEND_USER_BG_COLOR_DEFAULT = -65536;
  public static final String SUSPEND_USER_BG_COLOR_KEY = "suspend_user_bg_color";
  private static List<String> _HomeModes;
  private static List<HCADisplay> _HouseDisplays;
  private static List<HCABase> _HouseObjects;
  private static IOCommTask _IOCommTask = null;
  public static List<IRKeys> _IRKeys;
  private static HCAApplication _this;
  public static AppState appState = new AppState();
  public static int bg_color;
  public static HCAClient client;
  public static List<HCAConnectListener> connectListener;
  public static boolean devMode = true;
  public static boolean enablePing;
  public static int fg_color;
  public static int folder_on_color;
  public static AbsoluteLayout irdeviceview;
  public static String mClientName;
  public static String mErrorText;
  public static String mIPAddress;
  public static String mIPAddressSecondary;
  public static String mIPPort;
  public static String mIPTimeout;
  public static String mRemotePassword;
  public static long pingTimeTimeout;
  public static SharedPreferences prefs;
  public static Integer serverProtocol;
  public static Integer serverProtocolMinor;
  public static int socketReadTimeout;
  public static int suspend_mode_bg_color;
  public static int suspend_user_bg_color;
  public static int svrversion;
  public static String svrversionbuild;
  public static Integer svrversionbuildmin;
  public static String svrversionmajor;
  public static Integer svrversionmajormin;
  public static String svrversionminor;
  public static Integer svrversionminormin;
  public static boolean usesounds;
  public static String versionbuild;
  public static String versionmajor;
  public static String versionminor;
  public static int vibration = 20;
  public static List<HCAUpdateListener> visibleUpdateListener;
  private List<HCACommandResult> _CommandResults = null;
  private List<HCACommand> _Commands = null;
  private List<HCACommand> _CommandsWaitingResult = null;
  private ConnectivityManager _conMgr;
  private NetworkInfo _curNetwork;
  private long _pingTime = 0L;
  private boolean _pinged = false;
  private int _usedCommands = 0;
  private BroadcastReceiver networkConnectivityChanged = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (!HCAApplication.appState.areConnected) {}
      do
      {
        return;
        if (HCAApplication.devMode)
        {
          Log.d("HCA", "networkConnectivityChanged: URI = " + paramAnonymousIntent.getDataString());
          if (HCAApplication.this._curNetwork == null) {}
          for (String str1 = "null";; str1 = HCAApplication.this._curNetwork.getTypeName() + " - " + HCAApplication.this._curNetwork.getSubtypeName())
          {
            Log.d("HCA", "_curNetwork = " + str1);
            Log.d("HCA", "ConnectivityManager Intent contains:");
            Bundle localBundle = paramAnonymousIntent.getExtras();
            if (localBundle == null) {
              break;
            }
            Iterator localIterator = localBundle.keySet().iterator();
            while (localIterator.hasNext())
            {
              String str2 = (String)localIterator.next();
              if (HCAApplication.devMode) {
                Log.d("HCA", "key = " + str2 + " type = " + localBundle.get(str2).toString());
              }
            }
          }
        }
        if (HCAApplication.devMode) {
          Log.d("HCA", "Checking for network reconnect.");
        }
        if (HCAApplication.this._curNetwork == null) {
          break label387;
        }
        NetworkInfo localNetworkInfo2 = HCAApplication.this._conMgr.getNetworkInfo(HCAApplication.this._curNetwork.getType());
        if ((localNetworkInfo2 == null) || (!localNetworkInfo2.isConnected())) {
          break;
        }
        HCAApplication.access$002(HCAApplication.this, localNetworkInfo2);
      } while (!HCAApplication.devMode);
      Log.d("HCA", "Current network is OK: " + HCAApplication.this._curNetwork.getTypeName() + " - " + HCAApplication.this._curNetwork.getSubtypeName());
      return;
      HCAApplication.access$002(HCAApplication.this, null);
      if (HCAApplication.devMode) {
        Log.d("HCA", "Current network was disconnected.");
      }
      label387:
      NetworkInfo localNetworkInfo1 = HCAApplication.this._conMgr.getActiveNetworkInfo();
      if ((localNetworkInfo1 != null) && (localNetworkInfo1.isConnected()))
      {
        if (HCAApplication.devMode) {
          Log.d("HCA", "Disconnecting before Reconnecting after network connectivity change");
        }
        HCAApplication.this.disconnectFromHCAServer(false);
        if (HCAApplication.devMode) {
          Log.d("HCA", "Reconnecting network to " + localNetworkInfo1.getTypeName() + " - " + localNetworkInfo1.getSubtypeName());
        }
        HCAApplication.this.connectToHCAServer();
        return;
      }
      if (HCAApplication.devMode) {
        Log.d("HCA", "Disconnecting after network connectivity loss");
      }
      HCAApplication.this.disconnectFromHCAServer(false);
    }
  };
  
  static
  {
    usesounds = false;
    enablePing = false;
    fg_color = -1;
    bg_color = 9408;
    folder_on_color = 65280;
    suspend_user_bg_color = -65536;
    suspend_mode_bg_color = -16711936;
    visibleUpdateListener = null;
    connectListener = null;
    client = null;
    socketReadTimeout = 200;
    mClientName = "Android Device";
    mErrorText = "";
    versionmajor = "014";
    versionminor = "000";
    versionbuild = "002";
    svrversion = 0;
    svrversionmajor = "000";
    svrversionminor = "000";
    svrversionbuild = "000";
    svrversionmajormin = Integer.valueOf(11);
    svrversionminormin = Integer.valueOf(0);
    svrversionbuildmin = Integer.valueOf(0);
    serverProtocol = Integer.valueOf(14);
    serverProtocolMinor = Integer.valueOf(0);
    _HouseObjects = null;
    _HouseDisplays = null;
    _HomeModes = null;
    _IRKeys = null;
  }
  
  public HCAApplication() {}
  
  public static List<String> HomeModes()
  {
    return _HomeModes;
  }
  
  public static HCADisplay findHCADisplay(String paramString)
  {
    if (_HouseDisplays != null) {
      for (int i = 0; i < _HouseDisplays.size(); i++)
      {
        HCADisplay localHCADisplay = (HCADisplay)_HouseDisplays.get(i);
        if (localHCADisplay.getName().equalsIgnoreCase(paramString) == true) {
          return localHCADisplay;
        }
      }
    }
    return null;
  }
  
  public static HCABase findHCAObject(int paramInt)
  {
    if (_HouseObjects != null) {
      for (int j = 0; j < _HouseObjects.size(); j++)
      {
        HCABase localHCABase2 = (HCABase)_HouseObjects.get(j);
        if (localHCABase2.getObjectId() == paramInt) {
          return localHCABase2;
        }
      }
    }
    if (_HouseDisplays != null) {
      for (int i = 0; i < _HouseDisplays.size(); i++)
      {
        HCABase localHCABase1 = (HCABase)_HouseDisplays.get(i);
        if (localHCABase1.getObjectId() == paramInt) {
          return localHCABase1;
        }
      }
    }
    return null;
  }
  
  public static HCABase findHCAObjectByName(String paramString)
  {
    for (int i = 0; i < _HouseObjects.size(); i++)
    {
      HCABase localHCABase2 = (HCABase)_HouseObjects.get(i);
      if (localHCABase2.getDisplayName(true).equalsIgnoreCase(paramString)) {
        return localHCABase2;
      }
    }
    for (int j = 0; j < _HouseDisplays.size(); j++)
    {
      HCABase localHCABase1 = (HCABase)_HouseDisplays.get(j);
      if (localHCABase1.getDisplayName(true).equalsIgnoreCase(paramString)) {
        return localHCABase1;
      }
    }
    return null;
  }
  
  public static String getCommandResult(int paramInt)
  {
    int i = 30;
    for (;;)
    {
      i--;
      if ((i > 0) && (_IOCommTask != null) && (!_IOCommTask.CommandIsDone(paramInt))) {
        try
        {
          Thread.sleep(100L);
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }
    while ((i <= 0) || (_IOCommTask == null)) {
      return null;
    }
    return _IOCommTask.CommandResultGet(paramInt);
  }
  
  public static List<HCABase> getDisplayObjectList(String paramString)
  {
    ArrayList localArrayList;
    int i;
    if ((paramString == null) || (paramString.equals("All Displays") == true))
    {
      localArrayList = new ArrayList();
      if (_HouseDisplays != null) {
        i = 0;
      }
    }
    else
    {
      while (i < _HouseDisplays.size())
      {
        HCADisplay localHCADisplay1 = (HCADisplay)_HouseDisplays.get(i);
        if (!localHCADisplay1.getDoNotShow()) {
          localArrayList.add(localHCADisplay1);
        }
        i++;
        continue;
        HCADisplay localHCADisplay2 = findHCADisplay(paramString);
        if (localHCADisplay2 == null) {
          return null;
        }
        localArrayList = new ArrayList();
        int j = 0;
        if (j < localHCADisplay2.getCount())
        {
          int m;
          if (_HouseObjects != null)
          {
            m = 0;
            label120:
            if (m < _HouseObjects.size())
            {
              if (localHCADisplay2.getId(j) != ((HCABase)_HouseObjects.get(m)).getObjectId()) {
                break label247;
              }
              localArrayList.add(_HouseObjects.get(m));
            }
          }
          if (_HouseDisplays != null) {}
          for (int k = 0;; k++) {
            if (k < _HouseDisplays.size())
            {
              if (localHCADisplay2.getId(j) == ((HCADisplay)_HouseDisplays.get(k)).getObjectId()) {
                localArrayList.add(_HouseDisplays.get(k));
              }
            }
            else
            {
              j++;
              break;
              label247:
              m++;
              break label120;
            }
          }
        }
      }
    }
    return localArrayList;
  }
  
  public static boolean loadDisplayHTML(HCADisplay paramHCADisplay)
  {
    String str = "";
    if (paramHCADisplay.getDisplayType() == 1) {}
    String[] arrayOfString;
    int j;
    for (int i = 3;; i = 4)
    {
      arrayOfString = new String[5];
      arrayOfString[0] = "HCAApp";
      arrayOfString[1] = "GetDisplayHTML";
      arrayOfString[2] = paramHCADisplay.getName();
      arrayOfString[3] = "";
      arrayOfString[4] = "";
      IOCommTask localIOCommTask = _IOCommTask;
      j = 0;
      if (localIOCommTask != null) {
        break;
      }
      return false;
    }
    for (;;)
    {
      j++;
      int k;
      if (_IOCommTask != null)
      {
        arrayOfString[3] = ("" + j);
        k = _IOCommTask.postCommand(arrayOfString, "GetDisplayHTML");
        if (k >= 0) {}
      }
      else
      {
        label116:
        paramHCADisplay.setHtml(str);
        return true;
      }
      while (!_IOCommTask.CommandIsDone(k)) {
        try
        {
          Thread.sleep(100L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
          return false;
        }
      }
      try
      {
        localResponse = new Response(_IOCommTask.CommandResultGet(k));
      }
      catch (Exception localException1)
      {
        try
        {
          Response localResponse;
          if (localResponse.get(0).equals("0"))
          {
            if (localResponse.get(1).equals("HCAApp")) {
              str = str + localResponse.get(i);
            }
            int m = paramHCADisplay.getDisplayType();
            if (m != 1) {
              continue;
            }
            break label116;
          }
        }
        catch (Exception localException2)
        {
          for (;;) {}
        }
        localException1 = localException1;
      }
    }
    localException1.printStackTrace();
    return false;
  }
  
  private boolean loadHomeModes()
  {
    _HomeModes.clear();
    client.sendCommand("HCAApp", "GetHomeModeNames", "", "", "", "");
    Response localResponse;
    try
    {
      localResponse = client.waitForPacket(true);
      if (localResponse == null) {
        return false;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    if ((localResponse.get(0).equals("0")) && (localResponse.get(1).equals("HCAApp")) && (localResponse.get(2).equals("GetHomeModeNames"))) {
      for (int i = 3; i < localResponse.getCount(); i++) {
        _HomeModes.add(localResponse.get(i));
      }
    }
    return true;
  }
  
  private boolean loadHouseDisplays()
  {
    if (serverProtocol.intValue() >= 11) {
      return loadHouseDisplaysBlock();
    }
    return loadHouseDisplaysSingle();
  }
  
  private boolean loadHouseDisplaysBlock()
  {
    _HouseDisplays.clear();
    int i = 0;
    int j = 0;
    while (j == 0)
    {
      client.sendCommand("HCAApp", "GetDisplays", "" + i, "", "", "");
      Response localResponse1;
      try
      {
        localResponse1 = client.waitForPacket(true);
        if (localResponse1 == null) {
          return false;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      if (localResponse1.get(0).equals("0"))
      {
        if ((localResponse1.get(1).equals("HCAApp")) && (localResponse1.get(2).equals("GetDisplays")))
        {
          Response localResponse2 = new Response(localResponse1.get(3));
          for (int k = 0;; k++)
          {
            int m = localResponse2.getCount();
            if (k >= m) {
              break;
            }
            String str = localResponse2.get(k);
            Response localResponse3 = new Response(str);
            Response localResponse4 = new Response(localResponse3.get(12));
            int[] arrayOfInt = new int[localResponse4.getCount()];
            for (int n = 0;; n++)
            {
              int i1 = localResponse4.getCount();
              if (n >= i1) {
                break;
              }
              arrayOfInt[n] = Integer.parseInt(localResponse4.get(n));
            }
            HCADisplay localHCADisplay = new HCADisplay(localResponse3.get(0), Integer.parseInt(localResponse3.get(1)), Integer.parseInt(localResponse3.get(2)), localResponse3.get(3).toLowerCase().replace(" ", "_"), Integer.parseInt(localResponse3.get(4)), Integer.parseInt(localResponse3.get(5)), Integer.parseInt(localResponse3.get(6)), localResponse3.get(8), localResponse3.get(9), Integer.parseInt(localResponse3.get(10)), Integer.parseInt(localResponse3.get(7)), Integer.parseInt(localResponse3.get(11)), arrayOfInt);
            _HouseDisplays.add(localHCADisplay);
          }
        }
      }
      else {
        j = 1;
      }
      i++;
    }
    return true;
  }
  
  private boolean loadHouseDisplaysSingle()
  {
    _HouseDisplays.clear();
    client.sendCommand("HCAApp", "GetDisplays", "", "", "", "");
    Response localResponse1;
    try
    {
      localResponse1 = client.waitForPacket(true);
      if (localResponse1 == null) {
        return false;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    if ((localResponse1.get(0).equals("0")) && (localResponse1.get(1).equals("HCAApp")) && (localResponse1.get(2).equals("GetDisplays")) && (!localResponse1.get(3).equals("")))
    {
      Response localResponse2 = new Response(localResponse1.get(3));
      int i = 0;
      int j = localResponse2.getCount();
      if (i < j)
      {
        String str = localResponse2.get(i);
        Response localResponse3 = new Response(str);
        int[] arrayOfInt1;
        if (serverProtocol.intValue() >= 11)
        {
          Response localResponse4 = new Response(localResponse3.get(12));
          arrayOfInt1 = new int[localResponse4.getCount()];
          for (int k = 0;; k++)
          {
            int m = localResponse4.getCount();
            if (k >= m) {
              break;
            }
            arrayOfInt1[k] = Integer.parseInt(localResponse4.get(k));
          }
        }
        int[] arrayOfInt2;
        for (HCADisplay localHCADisplay = new HCADisplay(localResponse3.get(0), Integer.parseInt(localResponse3.get(1)), Integer.parseInt(localResponse3.get(2)), localResponse3.get(3).toLowerCase().replace(" ", "_"), Integer.parseInt(localResponse3.get(4)), Integer.parseInt(localResponse3.get(5)), Integer.parseInt(localResponse3.get(6)), localResponse3.get(8), localResponse3.get(9), Integer.parseInt(localResponse3.get(10)), Integer.parseInt(localResponse3.get(7)), Integer.parseInt(localResponse3.get(11)), arrayOfInt1);; localHCADisplay = new HCADisplay(localResponse3.get(0), localResponse3.get(1).toLowerCase().replace(" ", "_"), arrayOfInt2))
        {
          _HouseDisplays.add(localHCADisplay);
          i++;
          break;
          int n = -2 + str.indexOf("    ") / 4;
          arrayOfInt2 = new int[n];
          for (int i1 = 0; i1 < n; i1++) {
            arrayOfInt2[i1] = Integer.parseInt(localResponse3.get(i1 + 2));
          }
        }
      }
    }
    return true;
  }
  
  private boolean loadHouseObjects()
  {
    _HouseObjects.clear();
    int i = 0;
    int j = 0;
    Object localObject1 = null;
    if (j == 0)
    {
      client.sendCommand("HCAApp", "GetDesign", "" + i, "", "", "");
      Response localResponse1;
      try
      {
        localResponse1 = client.waitForPacket(true);
        if (localResponse1 == null) {
          return false;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      if (localResponse1.get(0).equals("0"))
      {
        if ((!localResponse1.get(1).equals("HCAApp")) || (!localResponse1.get(2).equals("GetDesign"))) {
          break label868;
        }
        Response localResponse2 = new Response(localResponse1.get(3));
        int k = 0;
        localObject2 = localObject1;
        int m = localResponse2.getCount();
        if (k < m)
        {
          Response localResponse3 = new Response(localResponse2.get(k));
          int n = _HouseObjects.size();
          int i1 = Integer.parseInt(localResponse3.get(0));
          String str1 = localResponse3.get(3);
          int i2 = Integer.parseInt(localResponse3.get(4));
          int i3 = Integer.parseInt(localResponse3.get(5));
          int i4 = Integer.parseInt(localResponse3.get(6));
          int i5 = Integer.parseInt(localResponse3.get(7));
          int i6 = Integer.parseInt(localResponse3.get(8));
          int i7;
          if (i4 == 1)
          {
            localObject2 = new HCAProgram(i1, localResponse3.get(1), null, localResponse3.get(2).toLowerCase().replace(" ", "_"), i2, i3, i4, str1);
            i7 = i5 + 9 + i6 * 2;
            label325:
            ((HCABase)localObject2).setIndex(n);
            if ((serverProtocol.intValue() < 11) || (localResponse3.getCount() < i7 + 4)) {
              break label837;
            }
            int i8 = i7 + 1;
            ((HCABase)localObject2).setShortTapAction(Integer.parseInt(localResponse3.get(i7)));
            int i9 = i8 + 1;
            ((HCABase)localObject2).setLongTapAction(Integer.parseInt(localResponse3.get(i8)));
            int i10 = i9 + 1;
            ((HCABase)localObject2).setFolderName(localResponse3.get(i9));
            int i11 = i10 + 1;
            ((HCABase)localObject2).setCurrentIconName(localResponse3.get(i10).toLowerCase().replace(" ", "_"));
            int i12 = i11 + 1;
            ((HCABase)localObject2).setCurrentIconLabel(localResponse3.get(i11));
            int i13 = i12 + 1;
            ((HCABase)localObject2).setCurrentIconState(Integer.parseInt(localResponse3.get(i12)));
            if (serverProtocol.intValue() >= 14)
            {
              int i14 = 1 + (1 + (i13 + 1));
              (i14 + 1);
              ((HCABase)localObject2).setInErrorState(Integer.parseInt(localResponse3.get(i14)));
            }
          }
          for (;;)
          {
            _HouseObjects.add(localObject2);
            k++;
            break;
            localObject2 = new HCADevice(i1, localResponse3.get(1), null, localResponse3.get(2).toLowerCase().replace(" ", "_"), i2, i3, i4, str1, i5, i6);
            if (((HCABase)localObject2).isThermostat()) {
              localObject2 = new HCAThermostat(i1, localResponse3.get(1), null, localResponse3.get(2).toLowerCase().replace(" ", "_"), i2, i3, i4, str1, i5, i6);
            }
            i7 = 9;
            if (((HCADevice)localObject2).getRockerCount() > 0) {
              for (int i20 = 0;; i20++)
              {
                int i21 = ((HCADevice)localObject2).getRockerCount();
                if (i20 >= i21) {
                  break;
                }
                HCADevice localHCADevice3 = (HCADevice)localObject2;
                String str3 = localResponse3.get(i7);
                localHCADevice3.setRockerName(i20, str3);
                i7++;
              }
            }
            if (((HCADevice)localObject2).getButtonCount() <= 0) {
              break label325;
            }
            for (int i15 = 0;; i15++)
            {
              int i16 = ((HCADevice)localObject2).getButtonCount();
              if (i15 >= i16) {
                break;
              }
              HCADevice localHCADevice2 = (HCADevice)localObject2;
              String str2 = localResponse3.get(i7);
              localHCADevice2.setButtonName(i15, str2);
              i7++;
            }
            for (int i17 = 0;; i17++)
            {
              int i18 = ((HCADevice)localObject2).getButtonCount();
              if (i17 >= i18) {
                break;
              }
              HCADevice localHCADevice1 = (HCADevice)localObject2;
              int i19 = Integer.parseInt(localResponse3.get(i7));
              localHCADevice1.setButtonState(i17, i19);
              i7++;
            }
            label837:
            ((HCABase)localObject2).setShortTapAction(1);
            ((HCABase)localObject2).setLongTapAction(2);
          }
        }
      }
      else
      {
        j = 1;
      }
    }
    label868:
    for (Object localObject2 = localObject1;; localObject2 = localObject1)
    {
      i++;
      localObject1 = localObject2;
      break;
      return true;
    }
  }
  
  public static int serverCommand(String[] paramArrayOfString, String paramString)
  {
    if (_IOCommTask != null) {
      return _IOCommTask.postCommand(paramArrayOfString, paramString);
    }
    return -1;
  }
  
  public static void setPreferences()
  {
    prefs = _this.getSharedPreferences("com.advancedquonsettechnology.hcaapp_preferences", 0);
    mIPAddress = prefs.getString("ip_address_preference", "");
    mIPAddressSecondary = prefs.getString("ip_address_preference_secondary", "");
    mIPPort = prefs.getString("ip_port_preference", "2000");
    mIPTimeout = prefs.getString("timeout_preferences", "10000");
    mRemotePassword = prefs.getString("password_preference", "");
    enablePing = prefs.getBoolean("ping_enable_preference", false);
    mClientName = prefs.getString("client_name_preference", "");
    pingTimeTimeout = 60000 * Integer.parseInt(prefs.getString("ping_preference", "3"));
    fg_color = prefs.getInt("app_fg_color", -1);
    bg_color = prefs.getInt("app_bg_color", 9408);
    folder_on_color = prefs.getInt("folder_on_color", 65280);
    suspend_user_bg_color = prefs.getInt("suspend_user_bg_color", -65536);
    suspend_mode_bg_color = prefs.getInt("suspend_mode_bg_color", -16711936);
    vibration = Integer.parseInt(prefs.getString("ui_vibrator", "20"));
  }
  
  public void InitApplication()
  {
    Log.d("HCA", "HCAApplication - InitApplication()");
    this._conMgr = ((ConnectivityManager)getSystemService("connectivity"));
    registerReceiver(this.networkConnectivityChanged, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    setPreferences();
    _HouseObjects = new ArrayList();
    _HouseDisplays = new ArrayList();
    _HomeModes = new ArrayList();
    this._Commands = new ArrayList();
    this._CommandsWaitingResult = new ArrayList();
    this._CommandResults = new ArrayList();
    visibleUpdateListener = new ArrayList();
    connectListener = new ArrayList();
    try
    {
      client = new HCAClient();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public boolean connectToHCAServer()
  {
    boolean bool = false;
    for (;;)
    {
      try
      {
        if (devMode) {
          Log.d("HCA", "connectToHCAServer");
        }
        if (!haveDataConnection())
        {
          notifyConnectListeners(HCAConnectListener.ConnectStatus.NO_NETWORK);
          return bool;
        }
        if (mIPAddress.equals(""))
        {
          notifyConnectListeners(HCAConnectListener.ConnectStatus.NO_SERVER);
          bool = false;
          continue;
        }
        if (_IOCommTask != null) {
          break label93;
        }
      }
      finally {}
      _IOCommTask = (IOCommTask)new IOCommTask().execute(new Void[0]);
      label93:
      bool = true;
    }
  }
  
  public void disconnectFromHCAServer(boolean paramBoolean)
  {
    try
    {
      if (devMode) {
        Log.d("HCA", "disconnectFromHCAServer(" + paramBoolean + ")");
      }
      if (paramBoolean)
      {
        appState.areConnected = false;
        appState.designLoaded = false;
      }
      this._curNetwork = null;
      if (_IOCommTask != null) {
        _IOCommTask.TaskCancel();
      }
      if (client.getConnected()) {
        client.closeClient();
      }
      notifyConnectListeners(HCAConnectListener.ConnectStatus.DISCONNECTED);
      return;
    }
    finally {}
  }
  
  public boolean haveDataConnection()
  {
    NetworkInfo localNetworkInfo = this._conMgr.getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (localNetworkInfo.isConnected());
  }
  
  public void notifyConnectListeners(HCAConnectListener.ConnectStatus paramConnectStatus)
  {
    if (connectListener != null)
    {
      Iterator localIterator = connectListener.iterator();
      while (localIterator.hasNext()) {
        ((HCAConnectListener)localIterator.next()).HCAServerStatus(paramConnectStatus);
      }
    }
  }
  
  public void notifyUpdateListeners(int paramInt)
  {
    if (visibleUpdateListener != null)
    {
      Iterator localIterator = visibleUpdateListener.iterator();
      while (localIterator.hasNext()) {
        ((HCAUpdateListener)localIterator.next()).onHCAUpdate(paramInt);
      }
    }
  }
  
  public void onCreate()
  {
    super.onCreate();
    _this = this;
    InitApplication();
  }
  
  public void reconnectToHCAServer()
  {
    try
    {
      disconnectFromHCAServer(false);
      connectToHCAServer();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static class AppState
  {
    public boolean areConnected = false;
    public boolean designLoaded = false;
    public String designTS = "";
    public boolean homeDisplayIsHTML;
    public String homeDisplayName;
    public String updateTS = "20010101000000";
    
    public AppState() {}
  }
  
  public class IOCommTask
    extends AsyncTask<Void, Integer, Void>
  {
    private boolean alldone = false;
    public String errorMessage = null;
    private final Object mLock = new Object();
    
    public IOCommTask() {}
    
    private void handleIRKeypadMsg(Response paramResponse)
    {
      HCAApplication._IRKeys.clear();
      Response localResponse1 = new Response(paramResponse.get(3));
      for (int i = 0; i < localResponse1.getCount(); i++)
      {
        Response localResponse2 = new Response(localResponse1.get(i));
        HCAApplication._IRKeys.add(new IRKeys(localResponse2.get(0), localResponse2.get(1), 3 * Integer.parseInt(localResponse2.get(2)), 3 * Integer.parseInt(localResponse2.get(3)), 3 * Integer.parseInt(localResponse2.get(4)), 3 * Integer.parseInt(localResponse2.get(5)), Integer.parseInt(localResponse2.get(6))));
      }
      Integer[] arrayOfInteger = new Integer[1];
      arrayOfInteger[0] = Integer.valueOf(5);
      publishProgress(arrayOfInteger);
    }
    
    private void handleKeypadButtonStateMsg(Response paramResponse)
    {
      HCABase localHCABase = HCAApplication.findHCAObjectByName(paramResponse.get(3));
      if (localHCABase != null)
      {
        HCADevice localHCADevice = (HCADevice)localHCABase;
        for (int i = 0; i < localHCADevice.getButtonCount(); i++) {
          localHCADevice.setButtonState(i, Integer.parseInt(paramResponse.get(i + 5)));
        }
        Integer[] arrayOfInteger = new Integer[2];
        arrayOfInteger[0] = Integer.valueOf(0);
        arrayOfInteger[1] = Integer.valueOf(localHCADevice.getObjectId());
        publishProgress(arrayOfInteger);
      }
    }
    
    private void handlePingResponseMsg()
    {
      HCAApplication.access$302(HCAApplication.this, SystemClock.uptimeMillis());
      HCAApplication.access$202(HCAApplication.this, false);
    }
    
    private void handleThermostatChangeMsg(Response paramResponse)
    {
      HCABase localHCABase = HCAApplication.findHCAObjectByName(paramResponse.get(3));
      if ((localHCABase != null) && (localHCABase.isThermostat()))
      {
        HCAThermostat localHCAThermostat = (HCAThermostat)localHCABase;
        localHCAThermostat.errorText = paramResponse.get(6);
        Integer[] arrayOfInteger = new Integer[2];
        arrayOfInteger[0] = Integer.valueOf(0);
        arrayOfInteger[1] = Integer.valueOf(localHCAThermostat.getObjectId());
        publishProgress(arrayOfInteger);
      }
    }
    
    private void handleThermostatStateMsg(Response paramResponse)
    {
      HCABase localHCABase = HCAApplication.findHCAObjectByName(paramResponse.get(3));
      HCAThermostat localHCAThermostat;
      if ((localHCABase != null) && (localHCABase.isThermostat()))
      {
        localHCAThermostat = (HCAThermostat)localHCABase;
        localHCAThermostat.heatset = Integer.parseInt(paramResponse.get(4));
        localHCAThermostat.heatsethigh = Integer.parseInt(paramResponse.get(5));
        localHCAThermostat.heatsetlow = Integer.parseInt(paramResponse.get(6));
        localHCAThermostat.coolset = Integer.parseInt(paramResponse.get(7));
        localHCAThermostat.coolsethigh = Integer.parseInt(paramResponse.get(8));
        localHCAThermostat.coolsetlow = Integer.parseInt(paramResponse.get(9));
        localHCAThermostat.currenttemp = Integer.parseInt(paramResponse.get(10));
        localHCAThermostat.modemode = Integer.parseInt(paramResponse.get(11));
        if (!paramResponse.get(12).equals("0")) {
          break label219;
        }
      }
      label219:
      for (localHCAThermostat.fanmode = false;; localHCAThermostat.fanmode = true)
      {
        if (HCAApplication.svrversion >= 13)
        {
          localHCAThermostat.units = paramResponse.get(13);
          localHCAThermostat.currenthumid = Integer.parseInt(paramResponse.get(14));
          localHCAThermostat.errorText = paramResponse.get(15);
        }
        Integer[] arrayOfInteger = new Integer[2];
        arrayOfInteger[0] = Integer.valueOf(0);
        arrayOfInteger[1] = Integer.valueOf(localHCAThermostat.getObjectId());
        publishProgress(arrayOfInteger);
        return;
      }
    }
    
    private void handleUpdateMsg(Response paramResponse)
    {
      int i = Integer.parseInt(paramResponse.get(3));
      int j = Integer.parseInt(paramResponse.get(4));
      int k = Integer.parseInt(paramResponse.get(5));
      int m = Integer.parseInt(paramResponse.get(6));
      int n = -1;
      int i1 = HCAApplication.serverProtocol.intValue();
      String str1 = null;
      String str2 = null;
      int i2 = 0;
      if (i1 >= 11)
      {
        str1 = paramResponse.get(m + 7).toLowerCase().replace(" ", "_");
        str2 = paramResponse.get(m + 8);
        n = Integer.parseInt(paramResponse.get(m + 9));
        HCAApplication.appState.updateTS = paramResponse.get(m + 10);
        int i4 = HCAApplication.serverProtocol.intValue();
        i2 = 0;
        if (i4 >= 12)
        {
          Integer.parseInt(paramResponse.get(m + 11));
          int i5 = HCAApplication.serverProtocol.intValue();
          i2 = 0;
          if (i5 >= 14) {
            i2 = Integer.parseInt(paramResponse.get(m + 12));
          }
        }
      }
      HCABase localHCABase = HCAApplication.findHCAObject(i);
      if (localHCABase == null) {
        return;
      }
      localHCABase.setSuspended(k);
      localHCABase.setInErrorState(i2);
      if ((localHCABase.isDevice()) && (m > 0))
      {
        HCADevice localHCADevice = (HCADevice)localHCABase;
        for (int i3 = 0; i3 < m; i3++) {
          localHCADevice.setButtonState(i3, Integer.parseInt(paramResponse.get(i3 + 7)));
        }
      }
      localHCABase.setState(j);
      if (str1 != null) {
        localHCABase.setCurrentIconName(str1.toLowerCase().replace(" ", "_"));
      }
      if (str2 != null) {
        localHCABase.setCurrentIconLabel(str2);
      }
      if (n >= 0) {
        localHCABase.setCurrentIconState(n);
      }
      Integer[] arrayOfInteger = new Integer[2];
      arrayOfInteger[0] = Integer.valueOf(0);
      arrayOfInteger[1] = Integer.valueOf(i);
      publishProgress(arrayOfInteger);
    }
    
    public int CommandCount()
    {
      synchronized (this.mLock)
      {
        int i = HCAApplication.this._Commands.size();
        return i;
      }
    }
    
    public HCACommand CommandGet(int paramInt)
    {
      synchronized (this.mLock)
      {
        int i = HCAApplication.this._Commands.size();
        HCACommand localHCACommand = null;
        if (i > paramInt) {
          localHCACommand = (HCACommand)HCAApplication.this._Commands.get(paramInt);
        }
        return localHCACommand;
      }
    }
    
    public boolean CommandIsDone(int paramInt)
    {
      Object localObject1 = this.mLock;
      for (int i = 0;; i++) {
        try
        {
          if (i < HCAApplication.this._CommandResults.size())
          {
            HCACommandResult localHCACommandResult = (HCACommandResult)HCAApplication.this._CommandResults.get(i);
            if (HCAApplication.devMode) {
              Log.d("HCA", "CommandIsDone cmd = " + localHCACommandResult.getCommand() + ", msg = " + localHCACommandResult.getResmsg());
            }
            if (localHCACommandResult.getCommand() == paramInt) {
              return true;
            }
          }
          else
          {
            return false;
          }
        }
        finally {}
      }
    }
    
    public HCACommand CommandRemove()
    {
      synchronized (this.mLock)
      {
        int i = HCAApplication.this._Commands.size();
        HCACommand localHCACommand = null;
        if (i > 0) {
          localHCACommand = (HCACommand)HCAApplication.this._Commands.remove(0);
        }
        return localHCACommand;
      }
    }
    
    public String CommandResultGet(int paramInt)
    {
      Object localObject1 = this.mLock;
      for (int i = 0;; i++) {
        try
        {
          int j = HCAApplication.this._CommandResults.size();
          String str = null;
          if (i < j)
          {
            HCACommandResult localHCACommandResult = (HCACommandResult)HCAApplication.this._CommandResults.get(i);
            if (localHCACommandResult.getCommand() == paramInt)
            {
              str = localHCACommandResult.getResmsg();
              HCAApplication.this._CommandResults.remove(i);
            }
          }
          else
          {
            return str;
          }
        }
        finally {}
      }
    }
    
    public void TaskCancel()
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "TaskCancel");
      }
      synchronized (this.mLock)
      {
        this.alldone = true;
        HCAApplication.this._Commands.clear();
      }
      try
      {
        HCAApplication._IOCommTask.get(2000L, TimeUnit.MILLISECONDS);
        HCAApplication.access$402(null);
        return;
        localObject2 = finally;
        throw localObject2;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          if (HCAApplication.devMode) {
            Log.d("HCA", "TaskCancel exception while canceling: " + localException.getMessage());
          }
        }
      }
    }
    
    String doClientConnect()
    {
      label51:
      int i;
      do
      {
        try
        {
          HCAApplication.client.openClient();
          if (HCAApplication.client.getConnected()) {
            break label51;
          }
          if (HCAApplication.mErrorText != "")
          {
            str = HCAApplication.mErrorText;
            return str;
          }
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          HCAApplication.client.closeClient();
          return "Failed to connect to HCA Server. Please check settings.";
        }
        return "Error Connecting to HCA Server. Check settings and try again";
        if (!HCAApplication.appState.designLoaded) {
          break;
        }
        i = HCAApplication.serverProtocol.intValue();
        String str = null;
      } while (i < 11);
      if (HCAApplication.devMode) {
        Log.d("HCA", "Refreshing HCA status as of " + HCAApplication.appState.updateTS);
      }
      HCAClient localHCAClient = HCAApplication.client;
      String[] arrayOfString = new String[3];
      arrayOfString[0] = "HCAApp";
      arrayOfString[1] = "RefreshState";
      arrayOfString[2] = HCAApplication.appState.updateTS;
      localHCAClient.sendCommand(arrayOfString);
      return null;
      boolean bool;
      do
      {
        try
        {
          bool = HCAApplication.this.loadHouseObjects();
          if (!bool) {
            continue;
          }
          bool = HCAApplication.this.loadHouseDisplays();
          if (!bool) {
            continue;
          }
          bool = HCAApplication.this.loadHomeModes();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          HCAApplication.client.closeClient();
          return "Error retrieving HCA design. Please reconnect.";
        }
        HCAApplication.appState.designLoaded = true;
        return null;
      } while (bool);
      return "Error retrieving HCA design. Please reconnect.";
    }
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      HCAApplication.access$002(HCAApplication.this, HCAApplication.this._conMgr.getActiveNetworkInfo());
      this.errorMessage = doClientConnect();
      if (this.errorMessage == null)
      {
        Integer[] arrayOfInteger2 = new Integer[1];
        arrayOfInteger2[0] = Integer.valueOf(1);
        publishProgress(arrayOfInteger2);
        HCAApplication.appState.areConnected = true;
      }
      for (;;)
      {
        Response localResponse;
        String str;
        try
        {
          this.alldone = false;
          if (!this.alldone)
          {
            if (HCAApplication.enablePing)
            {
              if (!HCAApplication.this._pinged) {
                break label413;
              }
              if (SystemClock.uptimeMillis() > 10000L + (HCAApplication.this._pingTime + HCAApplication.pingTimeTimeout))
              {
                this.alldone = true;
                Integer[] arrayOfInteger5 = new Integer[1];
                arrayOfInteger5[0] = Integer.valueOf(4);
                publishProgress(arrayOfInteger5);
                HCAApplication.access$202(HCAApplication.this, false);
              }
            }
            if (CommandCount() > 0)
            {
              HCACommand localHCACommand = CommandRemove();
              if (localHCACommand.getResultneeded() != null) {
                queueCommandForResult(localHCACommand);
              }
              String[] arrayOfString1 = localHCACommand.getCommands();
              HCAApplication.client.sendCommand(arrayOfString1);
            }
            localResponse = HCAApplication.client.waitForPacket(false);
            if (localResponse == null) {
              continue;
            }
            if (HCAApplication.devMode) {
              Log.d("HCA", "IOCommTask msg [" + localResponse.getRawMessage() + "] len " + localResponse.getRawMessage().length());
            }
            postCommandResult(localResponse);
            if (!localResponse.get(1).equals("HCAApp")) {
              break label634;
            }
            str = localResponse.get(2);
            if (!str.equals("Update")) {
              break label501;
            }
            handleUpdateMsg(localResponse);
            continue;
          }
          Integer[] arrayOfInteger3;
          arrayOfInteger1 = new Integer[1];
        }
        catch (Exception localException)
        {
          if (HCAApplication.devMode) {
            Log.d("HCA", "IOCommTask exiting due to exception: " + localException.getMessage());
          }
          arrayOfInteger3 = new Integer[1];
          arrayOfInteger3[0] = Integer.valueOf(3);
          publishProgress(arrayOfInteger3);
          localException.printStackTrace();
          HCAApplication.access$402(null);
          if (HCAApplication.devMode) {
            Log.d("HCA", "IOCommTask exiting");
          }
          return null;
        }
        Integer[] arrayOfInteger1;
        arrayOfInteger1[0] = Integer.valueOf(2);
        publishProgress(arrayOfInteger1);
        HCAApplication.appState.areConnected = false;
        return null;
        label413:
        if (SystemClock.uptimeMillis() > HCAApplication.this._pingTime + HCAApplication.pingTimeTimeout)
        {
          String[] arrayOfString2 = new String[3];
          arrayOfString2[0] = "HCAApp";
          arrayOfString2[1] = "Ping";
          arrayOfString2[2] = ("" + HCAApplication.pingTimeTimeout / 60000L);
          postCommand(arrayOfString2, null);
          HCAApplication.access$202(HCAApplication.this, true);
          continue;
          label501:
          if (str.equals("Ping"))
          {
            handlePingResponseMsg();
          }
          else if (str.equals("ThermostatState"))
          {
            handleThermostatStateMsg(localResponse);
          }
          else if (str.equals("KeypadButtonState"))
          {
            handleKeypadButtonStateMsg(localResponse);
          }
          else if (str.equals("ThermostatChange"))
          {
            handleThermostatChangeMsg(localResponse);
          }
          else if (str.equals("IRKeypad"))
          {
            handleIRKeypadMsg(localResponse);
          }
          else if (str.equals("GetHomeMode"))
          {
            Integer[] arrayOfInteger4 = new Integer[1];
            arrayOfInteger4[0] = Integer.valueOf(6);
            publishProgress(arrayOfInteger4);
            continue;
            label634:
            if (localResponse.get(1).equals("HCAObject")) {
              handleHCAObjectResponse(localResponse);
            }
          }
        }
      }
    }
    
    protected void handleHCAObjectResponse(Response paramResponse)
    {
      Integer.parseInt(paramResponse.get(0));
      paramResponse.get(3);
      Integer[] arrayOfInteger = new Integer[2];
      arrayOfInteger[0] = Integer.valueOf(0);
      arrayOfInteger[1] = Integer.valueOf(-1);
      publishProgress(arrayOfInteger);
    }
    
    protected void onProgressUpdate(Integer... paramVarArgs)
    {
      switch (paramVarArgs[0].intValue())
      {
      case 5: 
      default: 
      case 0: 
      case 1: 
        String[] arrayOfString;
        do
        {
          return;
          int i = paramVarArgs[1].intValue();
          HCAApplication.this.notifyUpdateListeners(i);
          return;
          arrayOfString = new String[4];
          arrayOfString[0] = "HCAApp";
          arrayOfString[1] = "SetClientOptions";
          arrayOfString[2] = "1";
          arrayOfString[3] = HCAApplication.mClientName;
          HCAApplication.access$302(HCAApplication.this, SystemClock.uptimeMillis());
          HCAApplication.access$202(HCAApplication.this, false);
        } while (HCAApplication._IOCommTask == null);
        HCAApplication._IOCommTask.postCommand(arrayOfString, null);
        HCAApplication.this.notifyConnectListeners(HCAConnectListener.ConnectStatus.CONNECT_OK);
        return;
      case 2: 
        String str = "";
        if (HCAApplication._IOCommTask != null)
        {
          str = HCAApplication._IOCommTask.errorMessage;
          HCAApplication._IOCommTask.TaskCancel();
        }
        HCAApplication.this.notifyConnectListeners(HCAConnectListener.ConnectStatus.CONNECT_FAILED);
        Toast.makeText(HCAApplication.this.getBaseContext(), str, 1).show();
        return;
      case 3: 
        HCAApplication.this.disconnectFromHCAServer(false);
        Toast.makeText(HCAApplication.this.getBaseContext(), "Connection to HCA Server was lost", 0).show();
        return;
      case 4: 
        HCAApplication.access$202(HCAApplication.this, false);
        HCAApplication.mErrorText = "Server Failed Ping";
        Toast.makeText(HCAApplication.this.getBaseContext(), HCAApplication.mErrorText, 0).show();
        return;
      }
      HCAApplication.this.notifyUpdateListeners(-2);
    }
    
    public int postCommand(String[] paramArrayOfString, String paramString)
    {
      if (!HCAApplication.client.getConnected()) {
        return -1;
      }
      synchronized (this.mLock)
      {
        HCAApplication.access$1008(HCAApplication.this);
        int i = HCAApplication.this._usedCommands;
        HCACommand localHCACommand = new HCACommand(i, paramArrayOfString, paramString);
        if (HCAApplication.devMode) {
          Log.d("HCA", "postCommand (" + i + ") = [" + paramArrayOfString[0] + "." + paramArrayOfString[1] + "]");
        }
        HCAApplication.this._Commands.add(localHCACommand);
        return i;
      }
    }
    
    public void postCommandResult(Response paramResponse)
    {
      String str = paramResponse.get(2);
      Object localObject1 = this.mLock;
      for (int i = 0;; i++) {
        try
        {
          if (i < HCAApplication.this._CommandsWaitingResult.size())
          {
            HCACommand localHCACommand = (HCACommand)HCAApplication.this._CommandsWaitingResult.get(i);
            if (localHCACommand.getResultneeded().equals(str))
            {
              HCAApplication.this._CommandsWaitingResult.remove(i);
              HCAApplication.this._CommandResults.add(new HCACommandResult(localHCACommand.getCommand(), paramResponse.getRawMessage()));
            }
          }
          else
          {
            return;
          }
        }
        finally {}
      }
    }
    
    public void queueCommandForResult(HCACommand paramHCACommand)
    {
      synchronized (this.mLock)
      {
        HCAApplication.this._CommandsWaitingResult.add(paramHCACommand);
        return;
      }
    }
  }
}
