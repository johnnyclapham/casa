package com.activehomevista.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v7.app.NotificationCompat.Builder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RemoteViews;
import com.activehomevista.ActiveHomeVista;
import com.activehomevista.ActiveHomeVistaWidgetProvider;
import com.activehomevista.ConfigureWidget;
import com.activehomevista.Device;
import com.activehomevista.ListPreferenceMultiSelect;
import com.activehomevista.Room;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class ActiveHomeVistaService
  extends Service
{
  public static final byte All_lights_off = 6;
  public static final byte All_lights_on = 1;
  public static final byte All_units_off = 0;
  public static final byte All_units_on = 16;
  public static final int Authorized = 0;
  public static final byte Bright = 5;
  public static final int ClientNewerThanServer = 2;
  public static final int ClientOlderThanServer = 3;
  private static final int DeviceCmd = 1;
  public static final byte Dim = 4;
  private static final byte EmulationDimMemoryCap = 18;
  public static final byte ExtendedCode = 7;
  private static final int GetConnectedCmd = 19;
  private static final byte GetConnectedCommandSize = 4;
  private static final int GetFileSmallCmd = 13;
  private static final int GetInterfaceTypeCmd = 8;
  private static final int GetMonitoredHouseCodeCmd = 17;
  private static final int GetStatusCmd = 5;
  private static final byte GlobalFunctionAllLightsOffCap = 16;
  public static final byte Hibernate = 47;
  private static final int KeepAliveCmd = 0;
  public static final byte Macro = 13;
  public static final byte MediaPlayer = 17;
  private static final int NOTIFICATION_ID = 1;
  public static final int NoInterfaceConnected = 6;
  public static final int NotAuthorized = 1;
  public static final byte Off = 3;
  public static final byte On = 2;
  private static final byte RFCap = 19;
  public static final byte Restart = 46;
  private static final int RoomCmd = 16;
  public static final int SerialCM10A = 0;
  public static final int SerialCM11A = 1;
  public static final int SerialCM15A = 2;
  public static final int SerialCM17A = 3;
  public static final int SerialCP10 = 4;
  private static final int SetConnectedCmd = 20;
  private static final byte SetConnectedCommandSize = 1;
  private static final int SetFileQueryCmd = 15;
  private static final int SetFileSmallCmd = 14;
  private static final int SetInterfaceTypeCmd = 9;
  private static final int SetMonitoredHouseCodeCmd = 18;
  private static final int SetStatusCmd = 6;
  public static final byte Shutdown = 44;
  public static final byte Standby = 45;
  private static final byte StatusOff = 14;
  private static final byte StatusOn = 13;
  public static final byte StatusReq = 15;
  private static final String TAG = "ActiveHomeVistaService";
  public static final byte Type_0_Command_1 = 1;
  public static final byte Type_3_Command_1 = 0;
  private static final int UpdateDeviceCmd = 7;
  private static final byte WS467NEW = 43;
  private static final int clientVersion = 7;
  private static final String defaultAddress = "127.0.0.1";
  private static final byte defaultCommandSize = 5;
  private static final byte deviceCommandSize = 6;
  private static final byte getFileCommandSize = 9;
  private static final int long_timeout = 60000;
  private static final int maxDevices = 256;
  public static final byte maxDim = 100;
  private static final byte minDim = 0;
  private static final byte roomCommandSize = 5;
  private static final byte setInterfaceCommandSize = 5;
  private static final byte setMonitoredHouseCodeCommandSize = 1;
  private static final int short_timeout = 5000;
  private static final byte updateDeviceCommandSize = 5;
  private final byte[] DimStatusDevices = new byte['Ā'];
  private Socket MySocket;
  private final byte[] OnStatusDevices = new byte['Ā'];
  private final byte[] OnStatusDevicesExtended = new byte['Ā'];
  private final IActiveHomeVistaService.Stub X10_service = new IActiveHomeVistaService.Stub()
  {
    public void deviceFunction(char paramAnonymousChar, byte paramAnonymousByte1, byte paramAnonymousByte2, byte paramAnonymousByte3, byte paramAnonymousByte4, byte paramAnonymousByte5)
      throws RemoteException
    {
      new ActiveHomeVistaService.localDeviceFunctionTask(ActiveHomeVistaService.this, paramAnonymousChar, paramAnonymousByte1, paramAnonymousByte2, paramAnonymousByte3, paramAnonymousByte4, paramAnonymousByte5).execute(new Void[0]);
    }
    
    public boolean getEmulationDimMemoryCap()
      throws RemoteException
    {
      return (0x40000 & ActiveHomeVistaService.this.cap) > 0;
    }
    
    public boolean getGlobalFunctionAllLightsOffCap()
      throws RemoteException
    {
      return (0x10000 & ActiveHomeVistaService.this.cap) > 0;
    }
    
    public boolean getLocalConnected()
      throws RemoteException
    {
      return ActiveHomeVistaService.this.connected;
    }
    
    public int getLocalConnectionStatus()
      throws RemoteException
    {
      return ActiveHomeVistaService.this.connectionStatus;
    }
    
    public List<Room> getLocalHouse(boolean[] paramAnonymousArrayOfBoolean, int[] paramAnonymousArrayOfInt)
      throws RemoteException
    {
      boolean[] arrayOfBoolean = new boolean[1];
      arrayOfBoolean[0] = ActiveHomeVistaService.this.outOfMemory;
      System.arraycopy(arrayOfBoolean, 0, paramAnonymousArrayOfBoolean, 0, 1);
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = ActiveHomeVistaService.this.fileUniqueId;
      System.arraycopy(arrayOfInt, 0, paramAnonymousArrayOfInt, 0, 1);
      LinkedList localLinkedList = new LinkedList();
      try
      {
        ActiveHomeVistaService.this.houseSemaphore.acquire();
        localLinkedList.addAll(ActiveHomeVistaService.this.house);
        return localLinkedList;
      }
      catch (InterruptedException localInterruptedException)
      {
        localLinkedList.clear();
        return localLinkedList;
      }
      finally
      {
        ActiveHomeVistaService.this.houseSemaphore.release();
      }
    }
    
    public int getLocalInterfaceType()
      throws RemoteException
    {
      return ActiveHomeVistaService.this.interfaceType;
    }
    
    public void getLocalStatus(byte[] paramAnonymousArrayOfByte1, byte[] paramAnonymousArrayOfByte2, byte[] paramAnonymousArrayOfByte3)
      throws RemoteException
    {
      System.arraycopy(ActiveHomeVistaService.this.OnStatusDevices, 0, paramAnonymousArrayOfByte1, 0, 256);
      System.arraycopy(ActiveHomeVistaService.this.DimStatusDevices, 0, paramAnonymousArrayOfByte2, 0, 256);
      System.arraycopy(ActiveHomeVistaService.this.OnStatusDevicesExtended, 0, paramAnonymousArrayOfByte3, 0, 256);
    }
    
    public boolean getRFCap()
      throws RemoteException
    {
      return (0x80000 & ActiveHomeVistaService.this.cap) > 0;
    }
    
    public void getServerStatus()
      throws RemoteException
    {
      new GetServerStatusTask().execute(new Void[0]);
    }
    
    public void globalFunction(byte paramAnonymousByte)
      throws RemoteException
    {
      deviceFunction(ActiveHomeVistaService.this.preferences.getString(ActiveHomeVistaService.this.getString(2131099720), ActiveHomeVistaService.this.getString(2131099703)).charAt(0), (byte)0, paramAnonymousByte, (byte)0, (byte)0, (byte)0);
    }
    
    public void notifyForeground()
    {
      if (!ActiveHomeVistaService.this.connected) {
        ActiveHomeVistaService.this.shutdownNow();
      }
    }
    
    public void registerCallback(IActiveHomeVistaServiceCallback paramAnonymousIActiveHomeVistaServiceCallback)
      throws RemoteException
    {
      if (paramAnonymousIActiveHomeVistaServiceCallback != null) {
        ActiveHomeVistaService.this.mCallbacks.register(paramAnonymousIActiveHomeVistaServiceCallback);
      }
    }
    
    public void roomFunction(int paramAnonymousInt, byte paramAnonymousByte)
      throws RemoteException
    {
      new roomFunctionTask(paramAnonymousInt, paramAnonymousByte).execute(new Void[0]);
    }
    
    public void unregisterCallback(IActiveHomeVistaServiceCallback paramAnonymousIActiveHomeVistaServiceCallback)
      throws RemoteException
    {
      if (paramAnonymousIActiveHomeVistaServiceCallback != null) {
        ActiveHomeVistaService.this.mCallbacks.unregister(paramAnonymousIActiveHomeVistaServiceCallback);
      }
    }
    
    class GetServerStatusTask
      extends AsyncTask<Void, Void, Void>
    {
      GetServerStatusTask() {}
      
      protected Void doInBackground(Void... paramVarArgs)
      {
        ActiveHomeVistaService.this.serverGetStatus();
        return null;
      }
    }
    
    class roomFunctionTask
      extends AsyncTask<Void, Void, Void>
    {
      byte functionCode;
      int roomId;
      
      public roomFunctionTask(int paramInt, byte paramByte)
      {
        this.roomId = paramInt;
        this.functionCode = paramByte;
      }
      
      protected Void doInBackground(Void... paramVarArgs)
      {
        byte[] arrayOfByte;
        if (ActiveHomeVistaService.this.out != null) {
          arrayOfByte = new byte[5];
        }
        try
        {
          arrayOfByte[0] = 16;
          ActiveHomeVistaService.setIntInBuf(arrayOfByte, ActiveHomeVistaService.this.MySocket.getLocalAddress().getHostAddress().length(), 1);
          ActiveHomeVistaService.this.out.write(arrayOfByte, 0, 5);
          arrayOfByte[0] = this.functionCode;
          ActiveHomeVistaService.setIntInBuf(arrayOfByte, this.roomId, 1);
          ActiveHomeVistaService.this.out.write(arrayOfByte, 0, 5);
          ActiveHomeVistaService.this.out.writeBytes(ActiveHomeVistaService.this.MySocket.getLocalAddress().getHostAddress());
          ActiveHomeVistaService.this.out.flush();
          return null;
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            ActiveHomeVistaService.this.shutdownSocket();
          }
        }
      }
    }
  };
  private int cap;
  private boolean connected = false;
  private byte connectionStatus = 0;
  private int fileUniqueId = -1;
  private final List<Room> house = new LinkedList();
  private byte[] houseBuf;
  private final Device[] houseRefs = new Device['Ā'];
  private final Semaphore houseSemaphore = new Semaphore(1, true);
  private DataInputStream in;
  private int interfaceType = 6;
  private boolean keepAlivePending = false;
  private Timer keepAliveTimer = new Timer();
  private final RemoteCallbackList<IActiveHomeVistaServiceCallback> mCallbacks = new RemoteCallbackList();
  private ConnectivityBroadcastReceiver mReceiver;
  private char monitoredHouseCode = 'A';
  private SharedPreferences.OnSharedPreferenceChangeListener myPreferenceListener;
  private NotificationManager notificationManager;
  private DataOutputStream out;
  private boolean outOfMemory = false;
  private SharedPreferences preferences;
  private Thread serviceThread;
  private boolean terminated = false;
  private final Timer widgetClickTimer = new Timer();
  
  public ActiveHomeVistaService() {}
  
  private void clearHouseRefs()
  {
    for (int i = 0; i < this.houseRefs.length; i++) {
      this.houseRefs[i] = null;
    }
  }
  
  public static InetSocketAddress getAddr(SharedPreferences paramSharedPreferences, Context paramContext)
  {
    if (isLocalWifi(paramSharedPreferences, paramContext)) {}
    int i;
    InetAddress localInetAddress1;
    for (String str = paramSharedPreferences.getString(paramContext.getString(2131099716), "127.0.0.1");; str = paramSharedPreferences.getString(paramContext.getString(2131099718), "127.0.0.1"))
    {
      try
      {
        int j = Integer.parseInt(paramSharedPreferences.getString(paramContext.getString(2131099727), paramContext.getString(2131099704)));
        i = j;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;)
        {
          InetAddress localInetAddress2;
          label63:
          i = Integer.parseInt(paramContext.getString(2131099704));
        }
      }
      try
      {
        localInetAddress2 = InetAddress.getByName(str);
        localInetAddress1 = localInetAddress2;
      }
      catch (UnknownHostException localUnknownHostException)
      {
        localInetAddress1 = null;
        break label63;
      }
      if (localInetAddress1 != null) {
        break label122;
      }
      return new InetSocketAddress(str, i);
    }
    label122:
    return new InetSocketAddress(localInetAddress1, i);
  }
  
  private int getIndexFromAppWidgetId(int paramInt)
  {
    int i = ConfigureWidget.loadHouseCode(this, paramInt).charAt(0);
    int j = (byte)Integer.parseInt(ConfigureWidget.loadDeviceCode(this, paramInt));
    return 16 * (byte)(i - getString(2131099703).charAt(0)) + (j - 1);
  }
  
  private static int getIntFromBuf(byte[] paramArrayOfByte, int paramInt)
  {
    return (((0x0 | 0xFF & paramArrayOfByte[(paramInt + 3)]) << 8 | 0xFF & paramArrayOfByte[(paramInt + 2)]) << 8 | 0xFF & paramArrayOfByte[(paramInt + 1)]) << 8 | 0xFF & paramArrayOfByte[paramInt];
  }
  
  public static InetAddress getWifiDhcpBroadcastAddress(SharedPreferences paramSharedPreferences, Context paramContext)
  {
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if (localWifiManager == null) {}
    DhcpInfo localDhcpInfo;
    do
    {
      do
      {
        return null;
      } while (!isLocalWifi(paramSharedPreferences, paramContext));
      localDhcpInfo = localWifiManager.getDhcpInfo();
    } while (localDhcpInfo == null);
    int i = localDhcpInfo.ipAddress & localDhcpInfo.netmask | 0xFFFFFFFF ^ localDhcpInfo.netmask;
    byte[] arrayOfByte = new byte[4];
    for (int j = 0; j < 4; j++) {
      arrayOfByte[j] = ((byte)(0xFF & i >> j * 8));
    }
    try
    {
      InetAddress localInetAddress = InetAddress.getByAddress(arrayOfByte);
      return localInetAddress;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      Log.e("ActiveHomeVistaService", "Could not obtain broadcast address", localUnknownHostException);
    }
    return null;
  }
  
  private void handleStartCommand(Intent paramIntent)
  {
    if (paramIntent != null)
    {
      if (!paramIntent.getAction().equals("com.activehomevista.APPWIDGET_REFRESH")) {
        break label37;
      }
      updateWidget(getIndexFromAppWidgetId(paramIntent.getExtras().getInt("appWidgetId", 0)));
    }
    label37:
    while (!paramIntent.getAction().equals("com.activehomevista.APPWIDGET_CLICK")) {
      return;
    }
    this.widgetClickTimer.scheduleAtFixedRate(new WidgetClickTimerTask(paramIntent), 0L, 500L);
  }
  
  private void initPreferences()
  {
    this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.myPreferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener()
    {
      public void onSharedPreferenceChanged(SharedPreferences paramAnonymousSharedPreferences, String paramAnonymousString)
      {
        if (paramAnonymousString.equals(ActiveHomeVistaService.this.getString(2131099718))) {
          ActiveHomeVistaService.this.shutdownNow();
        }
        do
        {
          return;
          if (paramAnonymousString.equals(ActiveHomeVistaService.this.getString(2131099716)))
          {
            ActiveHomeVistaService.this.shutdownNow();
            return;
          }
          if (paramAnonymousString.equals(ActiveHomeVistaService.this.getString(2131099727)))
          {
            ActiveHomeVistaService.this.shutdownNow();
            return;
          }
          if (paramAnonymousString.equals(ActiveHomeVistaService.this.getString(2131099731)))
          {
            ActiveHomeVistaService.this.shutdownNow();
            return;
          }
        } while (!paramAnonymousString.equals(ActiveHomeVistaService.this.getString(2131099725)));
        ActiveHomeVistaService.this.shutdownNow();
      }
    };
    this.preferences.registerOnSharedPreferenceChangeListener(this.myPreferenceListener);
  }
  
  public static boolean isLocalWifi(SharedPreferences paramSharedPreferences, Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting()))
    {
      if (localNetworkInfo.getType() == 1)
      {
        WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
        return ListPreferenceMultiSelect.isValueInList(ListPreferenceMultiSelect.parseStoredValue(paramSharedPreferences.getString(paramContext.getString(2131099731), "")), localWifiManager.getConnectionInfo().getSSID());
      }
      return false;
    }
    return false;
  }
  
  private void localDeviceFunction(char paramChar, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5)
  {
    byte[] arrayOfByte1;
    if (this.out != null) {
      arrayOfByte1 = new byte[5];
    }
    try
    {
      arrayOfByte1[0] = 1;
      setIntInBuf(arrayOfByte1, this.MySocket.getLocalAddress().getHostAddress().length(), 1);
      this.out.write(arrayOfByte1, 0, 5);
      byte[] arrayOfByte2 = new byte[6];
      arrayOfByte2[0] = ((byte)paramChar);
      arrayOfByte2[1] = paramByte1;
      arrayOfByte2[2] = paramByte2;
      arrayOfByte2[3] = paramByte3;
      arrayOfByte2[4] = paramByte4;
      arrayOfByte2[5] = paramByte5;
      this.out.write(arrayOfByte2, 0, 6);
      this.out.writeBytes(this.MySocket.getLocalAddress().getHostAddress());
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      shutdownSocket();
    }
  }
  
  /* Error */
  private boolean readHouse(byte[] paramArrayOfByte)
    throws IOException
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: new 585	java/io/DataInputStream
    //   5: dup
    //   6: new 587	java/io/ByteArrayInputStream
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 590	java/io/ByteArrayInputStream:<init>	([B)V
    //   14: invokespecial 593	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   17: astore_3
    //   18: aload_0
    //   19: invokespecial 339	com/activehomevista/service/ActiveHomeVistaService:clearHouseRefs	()V
    //   22: aload_3
    //   23: invokevirtual 596	java/io/DataInputStream:readInt	()I
    //   26: invokestatic 599	java/lang/Integer:reverseBytes	(I)I
    //   29: istore 4
    //   31: aload_0
    //   32: getfield 183	com/activehomevista/service/ActiveHomeVistaService:houseSemaphore	Ljava/util/concurrent/Semaphore;
    //   35: invokevirtual 602	java/util/concurrent/Semaphore:acquire	()V
    //   38: aload_0
    //   39: getfield 161	com/activehomevista/service/ActiveHomeVistaService:house	Ljava/util/List;
    //   42: invokeinterface 607 1 0
    //   47: aload_0
    //   48: invokespecial 339	com/activehomevista/service/ActiveHomeVistaService:clearHouseRefs	()V
    //   51: iconst_0
    //   52: istore 7
    //   54: iload 7
    //   56: iload 4
    //   58: if_icmpge +35 -> 93
    //   61: aload_0
    //   62: aload_3
    //   63: invokespecial 611	com/activehomevista/service/ActiveHomeVistaService:readRoom	(Ljava/io/DataInputStream;)Z
    //   66: ifne +36 -> 102
    //   69: iconst_0
    //   70: istore_2
    //   71: ldc 80
    //   73: ldc_w 613
    //   76: invokestatic 616	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   79: pop
    //   80: aload_0
    //   81: getfield 161	com/activehomevista/service/ActiveHomeVistaService:house	Ljava/util/List;
    //   84: invokeinterface 607 1 0
    //   89: aload_0
    //   90: invokespecial 339	com/activehomevista/service/ActiveHomeVistaService:clearHouseRefs	()V
    //   93: aload_0
    //   94: getfield 183	com/activehomevista/service/ActiveHomeVistaService:houseSemaphore	Ljava/util/concurrent/Semaphore;
    //   97: invokevirtual 619	java/util/concurrent/Semaphore:release	()V
    //   100: iload_2
    //   101: ireturn
    //   102: iinc 7 1
    //   105: goto -51 -> 54
    //   108: astore 6
    //   110: aload_0
    //   111: getfield 183	com/activehomevista/service/ActiveHomeVistaService:houseSemaphore	Ljava/util/concurrent/Semaphore;
    //   114: invokevirtual 619	java/util/concurrent/Semaphore:release	()V
    //   117: iconst_0
    //   118: ireturn
    //   119: astore 5
    //   121: aload_0
    //   122: getfield 183	com/activehomevista/service/ActiveHomeVistaService:houseSemaphore	Ljava/util/concurrent/Semaphore;
    //   125: invokevirtual 619	java/util/concurrent/Semaphore:release	()V
    //   128: aload 5
    //   130: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	this	ActiveHomeVistaService
    //   0	131	1	paramArrayOfByte	byte[]
    //   1	100	2	bool	boolean
    //   17	46	3	localDataInputStream	DataInputStream
    //   29	30	4	i	int
    //   119	10	5	localObject	Object
    //   108	1	6	localInterruptedException	InterruptedException
    //   52	51	7	j	int
    // Exception table:
    //   from	to	target	type
    //   31	51	108	java/lang/InterruptedException
    //   61	69	108	java/lang/InterruptedException
    //   71	93	108	java/lang/InterruptedException
    //   31	51	119	finally
    //   61	69	119	finally
    //   71	93	119	finally
  }
  
  private int readRetry(byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    int i = 0;
    for (;;)
    {
      int j;
      if (i < paramInt)
      {
        j = this.in.read(paramArrayOfByte, i, paramInt - i);
        if (j != -1) {}
      }
      else
      {
        return i;
      }
      i += j;
    }
  }
  
  private boolean readRoom(DataInputStream paramDataInputStream)
  {
    boolean bool = true;
    Room localRoom = Room.createFromStream(paramDataInputStream);
    if (localRoom != null)
    {
      this.house.add(localRoom);
      int i = 0;
      while (i < localRoom.getNrOfDevices())
      {
        Device localDevice = Device.createFromStream(paramDataInputStream);
        if (localDevice != null)
        {
          localRoom.devices.add(localDevice);
          this.houseRefs[(16 * (localDevice.getHouseCode() - getString(2131099703).charAt(0)) + (-1 + localDevice.getDeviceCode()))] = localDevice;
          i++;
        }
        else
        {
          bool = false;
        }
      }
      return bool;
    }
    return false;
  }
  
  private void serverGetConnected()
  {
    byte[] arrayOfByte1;
    if (this.out != null) {
      arrayOfByte1 = new byte[5];
    }
    try
    {
      arrayOfByte1[0] = 19;
      String str = Base64.encodeBytes(this.preferences.getString(getString(2131099725), "").getBytes());
      if (str != null) {
        setIntInBuf(arrayOfByte1, str.length(), 1);
      }
      this.out.write(arrayOfByte1, 0, 5);
      byte[] arrayOfByte2 = new byte[4];
      setIntInBuf(arrayOfByte2, 7, 0);
      this.out.write(arrayOfByte2, 0, 4);
      if (str != null) {
        this.out.writeBytes(str);
      }
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      Log.e("ActiveHomeVistaService", "IOException in serverGetConnected");
      shutdownSocket();
    }
  }
  
  private void serverGetFile()
  {
    byte[] arrayOfByte;
    if (this.out != null) {
      arrayOfByte = new byte[9];
    }
    try
    {
      arrayOfByte[0] = 13;
      setIntInBuf(arrayOfByte, this.fileUniqueId, 1);
      Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localDisplay.getMetrics(localDisplayMetrics);
      setIntInBuf(arrayOfByte, Math.max((int)Math.ceil(64.0F * localDisplayMetrics.density), getResources().getDimensionPixelSize(2131296275)), 5);
      this.out.write(arrayOfByte, 0, 9);
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      Log.e("ActiveHomeVistaService", "IOException in serverGetFile");
      shutdownSocket();
    }
  }
  
  private void serverGetInterfaceType()
  {
    if (this.out != null)
    {
      byte[] arrayOfByte = new byte[5];
      try
      {
        arrayOfByte[0] = 8;
        Object localObject;
        if (!this.preferences.contains(getString(2131099721)))
        {
          localObject = UUID.randomUUID().toString();
          this.preferences.edit().putString(getString(2131099721), (String)localObject).commit();
        }
        for (;;)
        {
          String str2 = (String)localObject + '#' + Settings.Secure.getString(getContentResolver(), "android_id");
          setIntInBuf(arrayOfByte, str2.length(), 1);
          this.out.write(arrayOfByte, 0, 5);
          this.out.writeBytes(str2);
          this.out.flush();
          return;
          String str1 = this.preferences.getString(getString(2131099721), "");
          localObject = str1;
        }
        return;
      }
      catch (IOException localIOException)
      {
        Log.e("ActiveHomeVistaService", "IOException in serverGetInterfaceType");
        shutdownSocket();
      }
    }
  }
  
  private void serverGetMonitoredHouseCode()
  {
    byte[] arrayOfByte;
    if (this.out != null) {
      arrayOfByte = new byte[5];
    }
    try
    {
      arrayOfByte[0] = 17;
      setIntInBuf(arrayOfByte, 0, 1);
      this.out.write(arrayOfByte, 0, 5);
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      Log.e("ActiveHomeVistaService", "IOException in serverGetMonitoredHouseCode");
      shutdownSocket();
    }
  }
  
  private void serverGetStatus()
  {
    byte[] arrayOfByte;
    if (this.out != null) {
      arrayOfByte = new byte[5];
    }
    try
    {
      arrayOfByte[0] = 5;
      setIntInBuf(arrayOfByte, 0, 1);
      this.out.write(arrayOfByte, 0, 5);
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      Log.e("ActiveHomeVistaService", "IOException in serverGetStatus");
      shutdownSocket();
    }
  }
  
  private void serverKeepAlive()
  {
    byte[] arrayOfByte;
    if (this.out != null) {
      arrayOfByte = new byte[5];
    }
    try
    {
      arrayOfByte[0] = 0;
      setIntInBuf(arrayOfByte, 0, 1);
      this.out.write(arrayOfByte, 0, 5);
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      Log.e("ActiveHomeVistaService", "IOException in serverKeepAlive");
      shutdownSocket();
    }
  }
  
  private Thread serviceThread()
  {
    new Thread()
    {
      /* Error */
      private void closeSocket()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   4: invokestatic 27	com/activehomevista/service/ActiveHomeVistaService:access$2900	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/util/Timer;
        //   7: invokevirtual 32	java/util/Timer:cancel	()V
        //   10: aload_0
        //   11: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   14: new 29	java/util/Timer
        //   17: dup
        //   18: invokespecial 33	java/util/Timer:<init>	()V
        //   21: invokestatic 37	com/activehomevista/service/ActiveHomeVistaService:access$2902	(Lcom/activehomevista/service/ActiveHomeVistaService;Ljava/util/Timer;)Ljava/util/Timer;
        //   24: pop
        //   25: aload_0
        //   26: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   29: invokestatic 41	com/activehomevista/service/ActiveHomeVistaService:access$1500	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/net/Socket;
        //   32: ifnull +13 -> 45
        //   35: aload_0
        //   36: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   39: invokestatic 41	com/activehomevista/service/ActiveHomeVistaService:access$1500	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/net/Socket;
        //   42: invokevirtual 46	java/net/Socket:close	()V
        //   45: aload_0
        //   46: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   49: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   52: ifnull +13 -> 65
        //   55: aload_0
        //   56: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   59: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   62: invokevirtual 53	java/io/DataInputStream:close	()V
        //   65: aload_0
        //   66: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   69: invokestatic 57	com/activehomevista/service/ActiveHomeVistaService:access$1400	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataOutputStream;
        //   72: ifnull +13 -> 85
        //   75: aload_0
        //   76: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   79: invokestatic 57	com/activehomevista/service/ActiveHomeVistaService:access$1400	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataOutputStream;
        //   82: invokevirtual 60	java/io/DataOutputStream:close	()V
        //   85: aload_0
        //   86: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   89: invokestatic 64	com/activehomevista/service/ActiveHomeVistaService:access$200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Z
        //   92: ifeq +92 -> 184
        //   95: ldc 66
        //   97: ldc 68
        //   99: invokestatic 74	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   102: pop
        //   103: aload_0
        //   104: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   107: iconst_0
        //   108: invokestatic 78	com/activehomevista/service/ActiveHomeVistaService:access$202	(Lcom/activehomevista/service/ActiveHomeVistaService;Z)Z
        //   111: pop
        //   112: aload_0
        //   113: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   116: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   119: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   122: istore 8
        //   124: iconst_0
        //   125: istore 9
        //   127: iload 9
        //   129: iload 8
        //   131: if_icmpge +43 -> 174
        //   134: aload_0
        //   135: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   138: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   141: iload 9
        //   143: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   146: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   149: aload_0
        //   150: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   153: invokestatic 64	com/activehomevista/service/ActiveHomeVistaService:access$200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Z
        //   156: aload_0
        //   157: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   160: invokestatic 98	com/activehomevista/service/ActiveHomeVistaService:access$100	(Lcom/activehomevista/service/ActiveHomeVistaService;)I
        //   163: invokeinterface 102 3 0
        //   168: iinc 9 1
        //   171: goto -44 -> 127
        //   174: aload_0
        //   175: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   178: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   181: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   184: aload_0
        //   185: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   188: bipush 6
        //   190: invokestatic 109	com/activehomevista/service/ActiveHomeVistaService:access$102	(Lcom/activehomevista/service/ActiveHomeVistaService;I)I
        //   193: pop
        //   194: aload_0
        //   195: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   198: iconst_0
        //   199: invokestatic 113	com/activehomevista/service/ActiveHomeVistaService:access$1202	(Lcom/activehomevista/service/ActiveHomeVistaService;B)B
        //   202: pop
        //   203: return
        //   204: astore_1
        //   205: ldc 66
        //   207: ldc 115
        //   209: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   212: pop
        //   213: return
        //   214: astore 10
        //   216: goto -48 -> 168
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	219	0	this	3
        //   204	1	1	localIOException	IOException
        //   122	10	8	i	int
        //   125	44	9	j	int
        //   214	1	10	localRemoteException	RemoteException
        // Exception table:
        //   from	to	target	type
        //   0	45	204	java/io/IOException
        //   45	65	204	java/io/IOException
        //   65	85	204	java/io/IOException
        //   85	124	204	java/io/IOException
        //   134	168	204	java/io/IOException
        //   174	184	204	java/io/IOException
        //   184	203	204	java/io/IOException
        //   134	168	214	android/os/RemoteException
      }
      
      private void createNotification(String paramAnonymousString1, String paramAnonymousString2)
      {
        if ((!paramAnonymousString2.equals(ActiveHomeVistaService.this.MySocket.getLocalAddress().getHostAddress())) && (ActiveHomeVistaService.this.preferences.getBoolean(ActiveHomeVistaService.this.getString(2131099724), false)))
        {
          Context localContext = ActiveHomeVistaService.this.getApplicationContext();
          Intent localIntent = new Intent(localContext, ActiveHomeVista.class);
          localIntent.addCategory("android.intent.category.LAUNCHER");
          localIntent.setAction("android.intent.action.MAIN");
          localIntent.setFlags(268435456);
          PendingIntent localPendingIntent = PendingIntent.getActivity(localContext, 0, localIntent, 0);
          NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(localContext);
          localBuilder.setContentIntent(localPendingIntent);
          localBuilder.setAutoCancel(true);
          localBuilder.setSmallIcon(2130837588);
          localBuilder.setContentTitle(ActiveHomeVistaService.this.getString(2131099681));
          localBuilder.setContentText(paramAnonymousString1);
          localBuilder.setPriority(0);
          boolean bool = ActiveHomeVistaService.this.preferences.getBoolean(ActiveHomeVistaService.this.getString(2131099723), false);
          int i = 0;
          if (bool) {
            i = 0x0 | 0x4;
          }
          if (ActiveHomeVistaService.this.preferences.getBoolean(ActiveHomeVistaService.this.getString(2131099728), false)) {
            i |= 0x1;
          }
          if (ActiveHomeVistaService.this.preferences.getBoolean(ActiveHomeVistaService.this.getString(2131099730), false)) {
            i |= 0x2;
          }
          localBuilder.setDefaults(i);
          ActiveHomeVistaService.this.notificationManager.notify(1, localBuilder.build());
        }
      }
      
      /* Error */
      private void handleSetConnected()
      {
        // Byte code:
        //   0: iconst_1
        //   1: newarray byte
        //   3: astore_1
        //   4: aload_0
        //   5: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   8: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   11: aload_1
        //   12: iconst_0
        //   13: iconst_1
        //   14: invokevirtual 240	java/io/DataInputStream:read	([BII)I
        //   17: iconst_1
        //   18: if_icmpne +113 -> 131
        //   21: aload_0
        //   22: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   25: aload_1
        //   26: iconst_0
        //   27: baload
        //   28: invokestatic 113	com/activehomevista/service/ActiveHomeVistaService:access$1202	(Lcom/activehomevista/service/ActiveHomeVistaService;B)B
        //   31: pop
        //   32: aload_1
        //   33: iconst_0
        //   34: baload
        //   35: i2c
        //   36: tableswitch	default:+32->68, 0:+44->80, 1:+44->80, 2:+44->80, 3:+44->80
        //   68: ldc 66
        //   70: ldc -14
        //   72: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   75: pop
        //   76: aload_1
        //   77: iconst_0
        //   78: iconst_1
        //   79: bastore
        //   80: aload_0
        //   81: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   84: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   87: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   90: istore 5
        //   92: iconst_0
        //   93: istore 6
        //   95: iload 6
        //   97: iload 5
        //   99: if_icmpge +53 -> 152
        //   102: aload_0
        //   103: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   106: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   109: iload 6
        //   111: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   114: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   117: aload_1
        //   118: iconst_0
        //   119: baload
        //   120: invokeinterface 246 2 0
        //   125: iinc 6 1
        //   128: goto -33 -> 95
        //   131: aload_0
        //   132: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   135: goto -55 -> 80
        //   138: astore_3
        //   139: ldc 66
        //   141: ldc -6
        //   143: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   146: pop
        //   147: aload_0
        //   148: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   151: return
        //   152: aload_0
        //   153: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   156: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   159: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   162: aload_0
        //   163: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   166: invokestatic 254	com/activehomevista/service/ActiveHomeVistaService:access$1200	(Lcom/activehomevista/service/ActiveHomeVistaService;)B
        //   169: ifeq -18 -> 151
        //   172: ldc2_w 255
        //   175: invokestatic 260	com/activehomevista/service/ActiveHomeVistaService$3:sleep	(J)V
        //   178: aload_0
        //   179: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   182: return
        //   183: astore_2
        //   184: aload_0
        //   185: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   188: return
        //   189: astore 7
        //   191: goto -66 -> 125
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	194	0	this	3
        //   3	115	1	arrayOfByte	byte[]
        //   183	1	2	localInterruptedException	InterruptedException
        //   138	1	3	localIOException	IOException
        //   90	10	5	i	int
        //   93	33	6	j	int
        //   189	1	7	localRemoteException	RemoteException
        // Exception table:
        //   from	to	target	type
        //   4	68	138	java/io/IOException
        //   68	80	138	java/io/IOException
        //   80	92	138	java/io/IOException
        //   102	125	138	java/io/IOException
        //   131	135	138	java/io/IOException
        //   152	182	138	java/io/IOException
        //   4	68	183	java/lang/InterruptedException
        //   68	80	183	java/lang/InterruptedException
        //   80	92	183	java/lang/InterruptedException
        //   102	125	183	java/lang/InterruptedException
        //   131	135	183	java/lang/InterruptedException
        //   152	182	183	java/lang/InterruptedException
        //   102	125	189	android/os/RemoteException
      }
      
      /* Error */
      private void handleSetFile(int paramAnonymousInt)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   4: iconst_0
        //   5: invokestatic 266	com/activehomevista/service/ActiveHomeVistaService:access$602	(Lcom/activehomevista/service/ActiveHomeVistaService;Z)Z
        //   8: pop
        //   9: iconst_0
        //   10: istore_3
        //   11: aload_0
        //   12: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   15: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   18: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   21: istore 4
        //   23: iconst_0
        //   24: istore 5
        //   26: iload 5
        //   28: iload 4
        //   30: if_icmpge +29 -> 59
        //   33: aload_0
        //   34: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   37: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   40: iload 5
        //   42: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   45: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   48: invokeinterface 269 1 0
        //   53: iinc 5 1
        //   56: goto -30 -> 26
        //   59: aload_0
        //   60: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   63: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   66: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   69: aload_0
        //   70: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   73: iconst_m1
        //   74: invokestatic 272	com/activehomevista/service/ActiveHomeVistaService:access$702	(Lcom/activehomevista/service/ActiveHomeVistaService;I)I
        //   77: pop
        //   78: iconst_4
        //   79: newarray byte
        //   81: astore 25
        //   83: aload_0
        //   84: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   87: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   90: aload 25
        //   92: iconst_0
        //   93: iconst_4
        //   94: invokevirtual 240	java/io/DataInputStream:read	([BII)I
        //   97: iconst_4
        //   98: if_icmpne +416 -> 514
        //   101: iconst_0
        //   102: istore_3
        //   103: iload_1
        //   104: ifle +410 -> 514
        //   107: aload_0
        //   108: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   111: iload_1
        //   112: newarray byte
        //   114: invokestatic 276	com/activehomevista/service/ActiveHomeVistaService:access$3402	(Lcom/activehomevista/service/ActiveHomeVistaService;[B)[B
        //   117: pop
        //   118: aload_0
        //   119: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   122: aload_0
        //   123: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   126: invokestatic 280	com/activehomevista/service/ActiveHomeVistaService:access$3400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   129: iload_1
        //   130: invokestatic 284	com/activehomevista/service/ActiveHomeVistaService:access$3000	(Lcom/activehomevista/service/ActiveHomeVistaService;[BI)I
        //   133: istore_3
        //   134: iload_3
        //   135: iload_1
        //   136: if_icmpne +223 -> 359
        //   139: aload_0
        //   140: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   143: aload_0
        //   144: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   147: invokestatic 280	com/activehomevista/service/ActiveHomeVistaService:access$3400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   150: invokestatic 288	com/activehomevista/service/ActiveHomeVistaService:access$3500	(Lcom/activehomevista/service/ActiveHomeVistaService;[B)Z
        //   153: ifeq +89 -> 242
        //   156: aload_0
        //   157: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   160: aload 25
        //   162: iconst_0
        //   163: invokestatic 292	com/activehomevista/service/ActiveHomeVistaService:access$2700	([BI)I
        //   166: invokestatic 272	com/activehomevista/service/ActiveHomeVistaService:access$702	(Lcom/activehomevista/service/ActiveHomeVistaService;I)I
        //   169: pop
        //   170: aload_0
        //   171: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   174: invokevirtual 155	com/activehomevista/service/ActiveHomeVistaService:getApplicationContext	()Landroid/content/Context;
        //   177: aload_0
        //   178: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   181: invokestatic 295	com/activehomevista/service/ActiveHomeVistaService:access$700	(Lcom/activehomevista/service/ActiveHomeVistaService;)I
        //   184: aload_0
        //   185: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   188: invokestatic 280	com/activehomevista/service/ActiveHomeVistaService:access$3400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   191: invokestatic 301	com/activehomevista/service/HouseCache:writeHouseFile	(Landroid/content/Context;I[B)V
        //   194: aload_0
        //   195: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   198: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   201: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   204: istore 28
        //   206: iconst_0
        //   207: istore 29
        //   209: iload 29
        //   211: iload 28
        //   213: if_icmpge +382 -> 595
        //   216: aload_0
        //   217: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   220: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   223: iload 29
        //   225: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   228: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   231: invokeinterface 304 1 0
        //   236: iinc 29 1
        //   239: goto -30 -> 209
        //   242: aload_0
        //   243: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   246: invokestatic 308	com/activehomevista/service/ActiveHomeVistaService:access$900	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/util/List;
        //   249: invokeinterface 313 1 0
        //   254: aload_0
        //   255: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   258: invokestatic 316	com/activehomevista/service/ActiveHomeVistaService:access$3600	(Lcom/activehomevista/service/ActiveHomeVistaService;)V
        //   261: ldc 66
        //   263: ldc_w 318
        //   266: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   269: pop
        //   270: aload_0
        //   271: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   274: goto -80 -> 194
        //   277: astore 19
        //   279: aload_0
        //   280: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   283: invokestatic 308	com/activehomevista/service/ActiveHomeVistaService:access$900	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/util/List;
        //   286: invokeinterface 313 1 0
        //   291: aload_0
        //   292: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   295: invokestatic 316	com/activehomevista/service/ActiveHomeVistaService:access$3600	(Lcom/activehomevista/service/ActiveHomeVistaService;)V
        //   298: ldc 66
        //   300: ldc_w 320
        //   303: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   306: pop
        //   307: aload_0
        //   308: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   311: aload_0
        //   312: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   315: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   318: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   321: istore 21
        //   323: iconst_0
        //   324: istore 22
        //   326: iload 22
        //   328: iload 21
        //   330: if_icmpge +284 -> 614
        //   333: aload_0
        //   334: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   337: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   340: iload 22
        //   342: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   345: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   348: invokeinterface 304 1 0
        //   353: iinc 22 1
        //   356: goto -30 -> 326
        //   359: aload_0
        //   360: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   363: invokestatic 308	com/activehomevista/service/ActiveHomeVistaService:access$900	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/util/List;
        //   366: invokeinterface 313 1 0
        //   371: aload_0
        //   372: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   375: invokestatic 316	com/activehomevista/service/ActiveHomeVistaService:access$3600	(Lcom/activehomevista/service/ActiveHomeVistaService;)V
        //   378: ldc 66
        //   380: ldc_w 322
        //   383: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   386: pop
        //   387: aload_0
        //   388: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   391: goto -197 -> 194
        //   394: astore 10
        //   396: aload_0
        //   397: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   400: invokestatic 308	com/activehomevista/service/ActiveHomeVistaService:access$900	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/util/List;
        //   403: invokeinterface 313 1 0
        //   408: aload_0
        //   409: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   412: invokestatic 316	com/activehomevista/service/ActiveHomeVistaService:access$3600	(Lcom/activehomevista/service/ActiveHomeVistaService;)V
        //   415: invokestatic 327	java/lang/System:gc	()V
        //   418: iload_3
        //   419: iload_1
        //   420: if_icmpge +28 -> 448
        //   423: aload_0
        //   424: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   427: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   430: iload_1
        //   431: iload_3
        //   432: isub
        //   433: i2l
        //   434: invokevirtual 331	java/io/DataInputStream:skip	(J)J
        //   437: lstore 17
        //   439: lload 17
        //   441: ldc2_w 332
        //   444: lcmp
        //   445: ifne +182 -> 627
        //   448: aload_0
        //   449: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   452: iconst_1
        //   453: invokestatic 266	com/activehomevista/service/ActiveHomeVistaService:access$602	(Lcom/activehomevista/service/ActiveHomeVistaService;Z)Z
        //   456: pop
        //   457: aload_0
        //   458: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   461: iconst_1
        //   462: invokestatic 336	com/activehomevista/service/ActiveHomeVistaService:access$2102	(Lcom/activehomevista/service/ActiveHomeVistaService;Z)Z
        //   465: pop
        //   466: aload_0
        //   467: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   470: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   473: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   476: istore 13
        //   478: iconst_0
        //   479: istore 14
        //   481: iload 14
        //   483: iload 13
        //   485: if_icmpge +160 -> 645
        //   488: aload_0
        //   489: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   492: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   495: iload 14
        //   497: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   500: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   503: invokeinterface 304 1 0
        //   508: iinc 14 1
        //   511: goto -30 -> 481
        //   514: aload_0
        //   515: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   518: invokestatic 308	com/activehomevista/service/ActiveHomeVistaService:access$900	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/util/List;
        //   521: invokeinterface 313 1 0
        //   526: aload_0
        //   527: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   530: invokestatic 316	com/activehomevista/service/ActiveHomeVistaService:access$3600	(Lcom/activehomevista/service/ActiveHomeVistaService;)V
        //   533: ldc 66
        //   535: ldc_w 338
        //   538: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   541: pop
        //   542: goto -348 -> 194
        //   545: astore 6
        //   547: aload_0
        //   548: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   551: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   554: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   557: istore 7
        //   559: iconst_0
        //   560: istore 8
        //   562: iload 8
        //   564: iload 7
        //   566: if_icmpge +92 -> 658
        //   569: aload_0
        //   570: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   573: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   576: iload 8
        //   578: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   581: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   584: invokeinterface 304 1 0
        //   589: iinc 8 1
        //   592: goto -30 -> 562
        //   595: aload_0
        //   596: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   599: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   602: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   605: aload_0
        //   606: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   609: iconst_m1
        //   610: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   613: return
        //   614: aload_0
        //   615: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   618: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   621: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   624: goto -19 -> 605
        //   627: iload_3
        //   628: lload 17
        //   630: l2i
        //   631: iadd
        //   632: istore_3
        //   633: goto -215 -> 418
        //   636: astore 16
        //   638: aload_0
        //   639: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   642: goto -194 -> 448
        //   645: aload_0
        //   646: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   649: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   652: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   655: goto -50 -> 605
        //   658: aload_0
        //   659: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   662: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   665: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   668: aload 6
        //   670: athrow
        //   671: astore 9
        //   673: goto -84 -> 589
        //   676: astore 15
        //   678: goto -170 -> 508
        //   681: astore 23
        //   683: goto -330 -> 353
        //   686: astore 30
        //   688: goto -452 -> 236
        //   691: astore 34
        //   693: goto -640 -> 53
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	696	0	this	3
        //   0	696	1	paramAnonymousInt	int
        //   10	623	3	i	int
        //   21	10	4	j	int
        //   24	30	5	k	int
        //   545	124	6	localObject	Object
        //   557	10	7	m	int
        //   560	30	8	n	int
        //   671	1	9	localRemoteException1	RemoteException
        //   394	1	10	localOutOfMemoryError	OutOfMemoryError
        //   476	10	13	i1	int
        //   479	30	14	i2	int
        //   676	1	15	localRemoteException2	RemoteException
        //   636	1	16	localIOException1	IOException
        //   437	192	17	l	long
        //   277	1	19	localIOException2	IOException
        //   321	10	21	i3	int
        //   324	30	22	i4	int
        //   681	1	23	localRemoteException3	RemoteException
        //   81	80	25	arrayOfByte	byte[]
        //   204	10	28	i5	int
        //   207	30	29	i6	int
        //   686	1	30	localRemoteException4	RemoteException
        //   691	1	34	localRemoteException5	RemoteException
        // Exception table:
        //   from	to	target	type
        //   69	101	277	java/io/IOException
        //   107	134	277	java/io/IOException
        //   139	194	277	java/io/IOException
        //   242	274	277	java/io/IOException
        //   359	391	277	java/io/IOException
        //   514	542	277	java/io/IOException
        //   69	101	394	java/lang/OutOfMemoryError
        //   107	134	394	java/lang/OutOfMemoryError
        //   139	194	394	java/lang/OutOfMemoryError
        //   242	274	394	java/lang/OutOfMemoryError
        //   359	391	394	java/lang/OutOfMemoryError
        //   514	542	394	java/lang/OutOfMemoryError
        //   69	101	545	finally
        //   107	134	545	finally
        //   139	194	545	finally
        //   242	274	545	finally
        //   279	311	545	finally
        //   359	391	545	finally
        //   396	418	545	finally
        //   423	439	545	finally
        //   448	466	545	finally
        //   514	542	545	finally
        //   638	642	545	finally
        //   423	439	636	java/io/IOException
        //   569	589	671	android/os/RemoteException
        //   488	508	676	android/os/RemoteException
        //   333	353	681	android/os/RemoteException
        //   216	236	686	android/os/RemoteException
        //   33	53	691	android/os/RemoteException
      }
      
      private void handleSetFileQuery(int paramAnonymousInt)
      {
        if (paramAnonymousInt != ActiveHomeVistaService.this.fileUniqueId) {
          ActiveHomeVistaService.this.serverGetFile();
        }
      }
      
      /* Error */
      private void handleSetInterfaceType()
      {
        // Byte code:
        //   0: iconst_5
        //   1: newarray byte
        //   3: astore_1
        //   4: aload_0
        //   5: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   8: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   11: aload_1
        //   12: iconst_0
        //   13: iconst_5
        //   14: invokevirtual 240	java/io/DataInputStream:read	([BII)I
        //   17: iconst_5
        //   18: if_icmpne +128 -> 146
        //   21: aload_0
        //   22: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   25: invokestatic 64	com/activehomevista/service/ActiveHomeVistaService:access$200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Z
        //   28: ifne +21 -> 49
        //   31: ldc 66
        //   33: ldc_w 349
        //   36: invokestatic 74	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   39: pop
        //   40: aload_0
        //   41: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   44: iconst_1
        //   45: invokestatic 78	com/activehomevista/service/ActiveHomeVistaService:access$202	(Lcom/activehomevista/service/ActiveHomeVistaService;Z)Z
        //   48: pop
        //   49: aload_0
        //   50: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   53: aload_1
        //   54: iconst_0
        //   55: baload
        //   56: invokestatic 109	com/activehomevista/service/ActiveHomeVistaService:access$102	(Lcom/activehomevista/service/ActiveHomeVistaService;I)I
        //   59: pop
        //   60: aload_0
        //   61: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   64: aload_1
        //   65: iconst_1
        //   66: invokestatic 292	com/activehomevista/service/ActiveHomeVistaService:access$2700	([BI)I
        //   69: invokestatic 352	com/activehomevista/service/ActiveHomeVistaService:access$1302	(Lcom/activehomevista/service/ActiveHomeVistaService;I)I
        //   72: pop
        //   73: aload_0
        //   74: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   77: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   80: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   83: istore 6
        //   85: iconst_0
        //   86: istore 7
        //   88: iload 7
        //   90: iload 6
        //   92: if_icmpge +43 -> 135
        //   95: aload_0
        //   96: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   99: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   102: iload 7
        //   104: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   107: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   110: aload_0
        //   111: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   114: invokestatic 64	com/activehomevista/service/ActiveHomeVistaService:access$200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Z
        //   117: aload_0
        //   118: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   121: invokestatic 98	com/activehomevista/service/ActiveHomeVistaService:access$100	(Lcom/activehomevista/service/ActiveHomeVistaService;)I
        //   124: invokeinterface 102 3 0
        //   129: iinc 7 1
        //   132: goto -44 -> 88
        //   135: aload_0
        //   136: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   139: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   142: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   145: return
        //   146: aload_0
        //   147: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   150: return
        //   151: astore_2
        //   152: ldc 66
        //   154: ldc_w 354
        //   157: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   160: pop
        //   161: aload_0
        //   162: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   165: return
        //   166: astore 8
        //   168: goto -39 -> 129
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	171	0	this	3
        //   3	62	1	arrayOfByte	byte[]
        //   151	1	2	localIOException	IOException
        //   83	10	6	i	int
        //   86	44	7	j	int
        //   166	1	8	localRemoteException	RemoteException
        // Exception table:
        //   from	to	target	type
        //   4	49	151	java/io/IOException
        //   49	85	151	java/io/IOException
        //   95	129	151	java/io/IOException
        //   135	145	151	java/io/IOException
        //   146	150	151	java/io/IOException
        //   95	129	166	android/os/RemoteException
      }
      
      private void handleSetMonitoredHouseCode()
      {
        byte[] arrayOfByte = new byte[1];
        try
        {
          if (ActiveHomeVistaService.this.in.read(arrayOfByte, 0, 1) == 1)
          {
            ActiveHomeVistaService.access$3202(ActiveHomeVistaService.this, (char)arrayOfByte[0]);
            return;
          }
          closeSocket();
          return;
        }
        catch (IOException localIOException)
        {
          Log.e("ActiveHomeVistaService", "IOException in handleSetMonitoredHouseCode");
          closeSocket();
        }
      }
      
      /* Error */
      private void handleSetStatus()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   4: aload_0
        //   5: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   8: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   11: sipush 256
        //   14: invokestatic 284	com/activehomevista/service/ActiveHomeVistaService:access$3000	(Lcom/activehomevista/service/ActiveHomeVistaService;[BI)I
        //   17: sipush 256
        //   20: if_icmpne +116 -> 136
        //   23: aload_0
        //   24: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   27: aload_0
        //   28: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   31: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   34: sipush 256
        //   37: invokestatic 284	com/activehomevista/service/ActiveHomeVistaService:access$3000	(Lcom/activehomevista/service/ActiveHomeVistaService;[BI)I
        //   40: sipush 256
        //   43: if_icmpne +93 -> 136
        //   46: aload_0
        //   47: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   50: aload_0
        //   51: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   54: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   57: sipush 256
        //   60: invokestatic 284	com/activehomevista/service/ActiveHomeVistaService:access$3000	(Lcom/activehomevista/service/ActiveHomeVistaService;[BI)I
        //   63: sipush 256
        //   66: if_icmpne +70 -> 136
        //   69: aload_0
        //   70: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   73: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   76: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   79: istore 4
        //   81: iconst_0
        //   82: istore 5
        //   84: iload 5
        //   86: iload 4
        //   88: if_icmpge +29 -> 117
        //   91: aload_0
        //   92: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   95: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   98: iload 5
        //   100: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   103: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   106: invokeinterface 374 1 0
        //   111: iinc 5 1
        //   114: goto -30 -> 84
        //   117: aload_0
        //   118: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   121: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   124: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   127: aload_0
        //   128: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   131: iconst_m1
        //   132: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   135: return
        //   136: ldc 66
        //   138: ldc_w 376
        //   141: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   144: pop
        //   145: aload_0
        //   146: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   149: return
        //   150: astore_1
        //   151: ldc 66
        //   153: ldc_w 378
        //   156: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   159: pop
        //   160: aload_0
        //   161: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   164: return
        //   165: astore 6
        //   167: goto -56 -> 111
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	170	0	this	3
        //   150	1	1	localIOException	IOException
        //   79	10	4	i	int
        //   82	30	5	j	int
        //   165	1	6	localRemoteException	RemoteException
        // Exception table:
        //   from	to	target	type
        //   0	81	150	java/io/IOException
        //   91	111	150	java/io/IOException
        //   117	135	150	java/io/IOException
        //   136	149	150	java/io/IOException
        //   91	111	165	android/os/RemoteException
      }
      
      /* Error */
      private void handleUpdateDevice()
      {
        // Byte code:
        //   0: iconst_5
        //   1: newarray byte
        //   3: astore_1
        //   4: ldc_w 381
        //   7: astore_2
        //   8: aload_0
        //   9: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   12: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   15: aload_1
        //   16: iconst_0
        //   17: iconst_5
        //   18: invokevirtual 240	java/io/DataInputStream:read	([BII)I
        //   21: iconst_5
        //   22: if_icmpne +633 -> 655
        //   25: aload_0
        //   26: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   29: invokestatic 50	com/activehomevista/service/ActiveHomeVistaService:access$2200	(Lcom/activehomevista/service/ActiveHomeVistaService;)Ljava/io/DataInputStream;
        //   32: invokevirtual 384	java/io/DataInputStream:readLine	()Ljava/lang/String;
        //   35: astore 5
        //   37: bipush 16
        //   39: aload_1
        //   40: iconst_0
        //   41: baload
        //   42: aload_0
        //   43: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   46: ldc_w 385
        //   49: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   52: iconst_0
        //   53: invokevirtual 389	java/lang/String:charAt	(I)C
        //   56: isub
        //   57: imul
        //   58: istore 6
        //   60: iload 6
        //   62: iconst_m1
        //   63: aload_1
        //   64: iconst_1
        //   65: baload
        //   66: iadd
        //   67: iadd
        //   68: istore 7
        //   70: aload_1
        //   71: iconst_2
        //   72: baload
        //   73: tableswitch	default:+79->152, 0:+2130->2203, 1:+1865->1938, 2:+138->211, 3:+597->670, 4:+776->849, 5:+1124->1197, 6:+2005->2078, 7:+1464->1537, 8:+79->152, 9:+79->152, 10:+79->152, 11:+79->152, 12:+79->152, 13:+1633->1706, 14:+1749->1822, 15:+2255->2328
        //   152: aload_0
        //   153: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   156: ldc_w 390
        //   159: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   162: astore_2
        //   163: aload_0
        //   164: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   167: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   170: invokevirtual 88	android/os/RemoteCallbackList:beginBroadcast	()I
        //   173: istore 8
        //   175: iconst_0
        //   176: istore 9
        //   178: iload 9
        //   180: iload 8
        //   182: if_icmpge +2227 -> 2409
        //   185: aload_0
        //   186: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   189: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   192: iload 9
        //   194: invokevirtual 92	android/os/RemoteCallbackList:getBroadcastItem	(I)Landroid/os/IInterface;
        //   197: checkcast 94	com/activehomevista/service/IActiveHomeVistaServiceCallback
        //   200: invokeinterface 374 1 0
        //   205: iinc 9 1
        //   208: goto -30 -> 178
        //   211: aload_0
        //   212: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   215: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   218: iload 7
        //   220: aaload
        //   221: ifnull -58 -> 163
        //   224: new 396	java/lang/StringBuilder
        //   227: dup
        //   228: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   231: aload_0
        //   232: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   235: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   238: iload 7
        //   240: aaload
        //   241: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   244: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   247: ldc_w 408
        //   250: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   253: aload_1
        //   254: iconst_0
        //   255: baload
        //   256: i2c
        //   257: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   260: aload_1
        //   261: iconst_1
        //   262: baload
        //   263: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   266: ldc_w 416
        //   269: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   272: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   275: astore 18
        //   277: aload_0
        //   278: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   281: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   284: iload 7
        //   286: aaload
        //   287: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   290: bipush 17
        //   292: if_icmpne +35 -> 327
        //   295: new 396	java/lang/StringBuilder
        //   298: dup
        //   299: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   302: aload 18
        //   304: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   307: aload_0
        //   308: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   311: ldc_w 424
        //   314: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   317: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   320: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   323: astore_2
        //   324: goto -161 -> 163
        //   327: aload_0
        //   328: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   331: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   334: iload 7
        //   336: aaload
        //   337: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   340: bipush 44
        //   342: if_icmpne +35 -> 377
        //   345: new 396	java/lang/StringBuilder
        //   348: dup
        //   349: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   352: aload 18
        //   354: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   357: aload_0
        //   358: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   361: ldc_w 425
        //   364: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   367: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   370: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   373: astore_2
        //   374: goto -211 -> 163
        //   377: aload_0
        //   378: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   381: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   384: iload 7
        //   386: aaload
        //   387: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   390: bipush 46
        //   392: if_icmpne +35 -> 427
        //   395: new 396	java/lang/StringBuilder
        //   398: dup
        //   399: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   402: aload 18
        //   404: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   407: aload_0
        //   408: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   411: ldc_w 425
        //   414: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   417: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   420: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   423: astore_2
        //   424: goto -261 -> 163
        //   427: aload_0
        //   428: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   431: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   434: iload 7
        //   436: aaload
        //   437: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   440: bipush 45
        //   442: if_icmpne +35 -> 477
        //   445: new 396	java/lang/StringBuilder
        //   448: dup
        //   449: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   452: aload 18
        //   454: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   457: aload_0
        //   458: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   461: ldc_w 425
        //   464: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   467: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   470: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   473: astore_2
        //   474: goto -311 -> 163
        //   477: aload_0
        //   478: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   481: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   484: iload 7
        //   486: aaload
        //   487: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   490: bipush 47
        //   492: if_icmpne +35 -> 527
        //   495: new 396	java/lang/StringBuilder
        //   498: dup
        //   499: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   502: aload 18
        //   504: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   507: aload_0
        //   508: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   511: ldc_w 425
        //   514: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   517: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   520: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   523: astore_2
        //   524: goto -361 -> 163
        //   527: new 396	java/lang/StringBuilder
        //   530: dup
        //   531: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   534: aload 18
        //   536: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   539: aload_0
        //   540: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   543: ldc_w 426
        //   546: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   549: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   552: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   555: astore_2
        //   556: aload_0
        //   557: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   560: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   563: iload 7
        //   565: iconst_1
        //   566: bastore
        //   567: aload_0
        //   568: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   571: invokestatic 430	com/activehomevista/service/ActiveHomeVistaService:access$3200	(Lcom/activehomevista/service/ActiveHomeVistaService;)C
        //   574: aload_0
        //   575: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   578: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   581: iload 7
        //   583: aaload
        //   584: invokevirtual 434	com/activehomevista/Device:getHouseCode	()C
        //   587: if_icmpeq +69 -> 656
        //   590: aload_0
        //   591: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   594: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   597: iload 7
        //   599: iconst_1
        //   600: bastore
        //   601: aload_0
        //   602: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   605: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   608: iload 7
        //   610: aaload
        //   611: invokevirtual 438	com/activehomevista/Device:getDeviceDimMemoryCap	()Z
        //   614: ifne +15 -> 629
        //   617: aload_0
        //   618: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   621: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   624: iload 7
        //   626: bipush 100
        //   628: bastore
        //   629: aload_0
        //   630: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   633: iload 7
        //   635: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   638: goto -475 -> 163
        //   641: astore_3
        //   642: ldc 66
        //   644: ldc_w 440
        //   647: invokestatic 118	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   650: pop
        //   651: aload_0
        //   652: invokespecial 248	com/activehomevista/service/ActiveHomeVistaService$3:closeSocket	()V
        //   655: return
        //   656: aload_0
        //   657: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   660: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   663: iload 7
        //   665: iconst_0
        //   666: bastore
        //   667: goto -66 -> 601
        //   670: aload_0
        //   671: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   674: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   677: iload 7
        //   679: aaload
        //   680: ifnull -517 -> 163
        //   683: new 396	java/lang/StringBuilder
        //   686: dup
        //   687: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   690: aload_0
        //   691: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   694: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   697: iload 7
        //   699: aaload
        //   700: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   703: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   706: ldc_w 408
        //   709: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   712: aload_1
        //   713: iconst_0
        //   714: baload
        //   715: i2c
        //   716: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   719: aload_1
        //   720: iconst_1
        //   721: baload
        //   722: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   725: ldc_w 416
        //   728: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   731: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   734: astore 17
        //   736: aload_0
        //   737: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   740: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   743: iload 7
        //   745: aaload
        //   746: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   749: bipush 17
        //   751: if_icmpne +35 -> 786
        //   754: new 396	java/lang/StringBuilder
        //   757: dup
        //   758: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   761: aload 17
        //   763: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   766: aload_0
        //   767: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   770: ldc_w 441
        //   773: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   776: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   779: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   782: astore_2
        //   783: goto -620 -> 163
        //   786: new 396	java/lang/StringBuilder
        //   789: dup
        //   790: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   793: aload 17
        //   795: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   798: aload_0
        //   799: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   802: ldc_w 442
        //   805: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   808: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   811: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   814: astore_2
        //   815: aload_0
        //   816: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   819: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   822: iload 7
        //   824: iconst_0
        //   825: bastore
        //   826: aload_0
        //   827: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   830: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   833: iload 7
        //   835: iconst_0
        //   836: bastore
        //   837: aload_0
        //   838: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   841: iload 7
        //   843: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   846: goto -683 -> 163
        //   849: aload_0
        //   850: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   853: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   856: iload 7
        //   858: aaload
        //   859: ifnull -696 -> 163
        //   862: new 396	java/lang/StringBuilder
        //   865: dup
        //   866: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   869: aload_0
        //   870: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   873: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   876: iload 7
        //   878: aaload
        //   879: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   882: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   885: ldc_w 408
        //   888: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   891: aload_1
        //   892: iconst_0
        //   893: baload
        //   894: i2c
        //   895: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   898: aload_1
        //   899: iconst_1
        //   900: baload
        //   901: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   904: ldc_w 416
        //   907: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   910: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   913: astore 16
        //   915: aload_0
        //   916: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   919: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   922: iload 7
        //   924: aaload
        //   925: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   928: bipush 17
        //   930: if_icmpne +35 -> 965
        //   933: new 396	java/lang/StringBuilder
        //   936: dup
        //   937: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   940: aload 16
        //   942: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   945: aload_0
        //   946: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   949: ldc_w 443
        //   952: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   955: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   958: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   961: astore_2
        //   962: goto -799 -> 163
        //   965: new 396	java/lang/StringBuilder
        //   968: dup
        //   969: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   972: aload 16
        //   974: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   977: aload_0
        //   978: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   981: ldc_w 444
        //   984: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   987: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   990: ldc_w 446
        //   993: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   996: aload_1
        //   997: iconst_4
        //   998: baload
        //   999: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   1002: ldc_w 448
        //   1005: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1008: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1011: astore_2
        //   1012: aload_0
        //   1013: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1016: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1019: iload 7
        //   1021: aaload
        //   1022: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   1025: bipush 43
        //   1027: if_icmpne +17 -> 1044
        //   1030: aload_0
        //   1031: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1034: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1037: iload 7
        //   1039: baload
        //   1040: iconst_1
        //   1041: if_icmpne -878 -> 163
        //   1044: aload_0
        //   1045: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1048: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1051: iload 7
        //   1053: aaload
        //   1054: invokevirtual 438	com/activehomevista/Device:getDeviceDimMemoryCap	()Z
        //   1057: ifne +94 -> 1151
        //   1060: aload_0
        //   1061: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1064: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1067: iload 7
        //   1069: baload
        //   1070: ifne +81 -> 1151
        //   1073: aload_0
        //   1074: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1077: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1080: iload 7
        //   1082: iconst_0
        //   1083: bipush 100
        //   1085: aload_1
        //   1086: iconst_4
        //   1087: baload
        //   1088: isub
        //   1089: invokestatic 454	java/lang/Math:max	(II)I
        //   1092: i2b
        //   1093: bastore
        //   1094: aload_0
        //   1095: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1098: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1101: iload 7
        //   1103: iconst_1
        //   1104: bastore
        //   1105: aload_0
        //   1106: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1109: invokestatic 430	com/activehomevista/service/ActiveHomeVistaService:access$3200	(Lcom/activehomevista/service/ActiveHomeVistaService;)C
        //   1112: aload_0
        //   1113: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1116: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1119: iload 7
        //   1121: aaload
        //   1122: invokevirtual 434	com/activehomevista/Device:getHouseCode	()C
        //   1125: if_icmpeq +58 -> 1183
        //   1128: aload_0
        //   1129: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1132: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1135: iload 7
        //   1137: iconst_1
        //   1138: bastore
        //   1139: aload_0
        //   1140: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1143: iload 7
        //   1145: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   1148: goto -985 -> 163
        //   1151: aload_0
        //   1152: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1155: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1158: iload 7
        //   1160: iconst_0
        //   1161: aload_0
        //   1162: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1165: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1168: iload 7
        //   1170: baload
        //   1171: aload_1
        //   1172: iconst_4
        //   1173: baload
        //   1174: isub
        //   1175: invokestatic 454	java/lang/Math:max	(II)I
        //   1178: i2b
        //   1179: bastore
        //   1180: goto -86 -> 1094
        //   1183: aload_0
        //   1184: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1187: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1190: iload 7
        //   1192: iconst_0
        //   1193: bastore
        //   1194: goto -55 -> 1139
        //   1197: aload_0
        //   1198: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1201: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1204: iload 7
        //   1206: aaload
        //   1207: ifnull -1044 -> 163
        //   1210: new 396	java/lang/StringBuilder
        //   1213: dup
        //   1214: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1217: aload_0
        //   1218: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1221: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1224: iload 7
        //   1226: aaload
        //   1227: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   1230: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1233: ldc_w 408
        //   1236: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1239: aload_1
        //   1240: iconst_0
        //   1241: baload
        //   1242: i2c
        //   1243: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   1246: aload_1
        //   1247: iconst_1
        //   1248: baload
        //   1249: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   1252: ldc_w 416
        //   1255: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1258: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1261: astore 15
        //   1263: aload_0
        //   1264: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1267: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1270: iload 7
        //   1272: aaload
        //   1273: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   1276: bipush 17
        //   1278: if_icmpne +35 -> 1313
        //   1281: new 396	java/lang/StringBuilder
        //   1284: dup
        //   1285: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1288: aload 15
        //   1290: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1293: aload_0
        //   1294: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1297: ldc_w 455
        //   1300: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   1303: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1306: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1309: astore_2
        //   1310: goto -1147 -> 163
        //   1313: new 396	java/lang/StringBuilder
        //   1316: dup
        //   1317: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1320: aload 15
        //   1322: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1325: aload_0
        //   1326: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1329: ldc_w 444
        //   1332: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   1335: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1338: ldc_w 457
        //   1341: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1344: aload_1
        //   1345: iconst_4
        //   1346: baload
        //   1347: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   1350: ldc_w 448
        //   1353: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1356: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1359: astore_2
        //   1360: aload_0
        //   1361: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1364: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1367: iload 7
        //   1369: aaload
        //   1370: invokevirtual 423	com/activehomevista/Device:getDeviceType	()B
        //   1373: bipush 43
        //   1375: if_icmpne +17 -> 1392
        //   1378: aload_0
        //   1379: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1382: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1385: iload 7
        //   1387: baload
        //   1388: iconst_1
        //   1389: if_icmpne -1226 -> 163
        //   1392: aload_0
        //   1393: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1396: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1399: iload 7
        //   1401: aaload
        //   1402: invokevirtual 438	com/activehomevista/Device:getDeviceDimMemoryCap	()Z
        //   1405: ifne +85 -> 1490
        //   1408: aload_0
        //   1409: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1412: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1415: iload 7
        //   1417: baload
        //   1418: ifne +72 -> 1490
        //   1421: aload_0
        //   1422: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1425: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1428: iload 7
        //   1430: bipush 100
        //   1432: bastore
        //   1433: aload_0
        //   1434: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1437: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1440: iload 7
        //   1442: iconst_1
        //   1443: bastore
        //   1444: aload_0
        //   1445: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1448: invokestatic 430	com/activehomevista/service/ActiveHomeVistaService:access$3200	(Lcom/activehomevista/service/ActiveHomeVistaService;)C
        //   1451: aload_0
        //   1452: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1455: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1458: iload 7
        //   1460: aaload
        //   1461: invokevirtual 434	com/activehomevista/Device:getHouseCode	()C
        //   1464: if_icmpeq +59 -> 1523
        //   1467: aload_0
        //   1468: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1471: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1474: iload 7
        //   1476: iconst_1
        //   1477: bastore
        //   1478: aload_0
        //   1479: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1482: iload 7
        //   1484: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   1487: goto -1324 -> 163
        //   1490: aload_0
        //   1491: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1494: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1497: iload 7
        //   1499: bipush 100
        //   1501: aload_0
        //   1502: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1505: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1508: iload 7
        //   1510: baload
        //   1511: aload_1
        //   1512: iconst_4
        //   1513: baload
        //   1514: iadd
        //   1515: invokestatic 460	java/lang/Math:min	(II)I
        //   1518: i2b
        //   1519: bastore
        //   1520: goto -87 -> 1433
        //   1523: aload_0
        //   1524: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1527: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1530: iload 7
        //   1532: iconst_0
        //   1533: bastore
        //   1534: goto -56 -> 1478
        //   1537: aload_0
        //   1538: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1541: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1544: iload 7
        //   1546: aaload
        //   1547: ifnull -1384 -> 163
        //   1550: new 396	java/lang/StringBuilder
        //   1553: dup
        //   1554: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1557: aload_0
        //   1558: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1561: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1564: iload 7
        //   1566: aaload
        //   1567: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   1570: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1573: ldc_w 408
        //   1576: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1579: aload_1
        //   1580: iconst_0
        //   1581: baload
        //   1582: i2c
        //   1583: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   1586: aload_1
        //   1587: iconst_1
        //   1588: baload
        //   1589: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   1592: ldc_w 416
        //   1595: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1598: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1601: astore 14
        //   1603: new 396	java/lang/StringBuilder
        //   1606: dup
        //   1607: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1610: aload 14
        //   1612: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1615: aload_0
        //   1616: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1619: ldc_w 444
        //   1622: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   1625: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1628: ldc_w 462
        //   1631: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1634: aload_1
        //   1635: iconst_4
        //   1636: baload
        //   1637: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   1640: ldc_w 448
        //   1643: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1646: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1649: astore_2
        //   1650: aload_1
        //   1651: iconst_4
        //   1652: baload
        //   1653: ifle +39 -> 1692
        //   1656: aload_0
        //   1657: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1660: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1663: iload 7
        //   1665: iconst_1
        //   1666: bastore
        //   1667: aload_0
        //   1668: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1671: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1674: iload 7
        //   1676: aload_1
        //   1677: iconst_4
        //   1678: baload
        //   1679: bastore
        //   1680: aload_0
        //   1681: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1684: iload 7
        //   1686: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   1689: goto -1526 -> 163
        //   1692: aload_0
        //   1693: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1696: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1699: iload 7
        //   1701: iconst_0
        //   1702: bastore
        //   1703: goto -36 -> 1667
        //   1706: aload_0
        //   1707: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1710: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1713: iload 7
        //   1715: aaload
        //   1716: ifnull -1553 -> 163
        //   1719: new 396	java/lang/StringBuilder
        //   1722: dup
        //   1723: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1726: aload_0
        //   1727: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1730: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1733: iload 7
        //   1735: aaload
        //   1736: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   1739: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1742: ldc_w 408
        //   1745: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1748: aload_1
        //   1749: iconst_0
        //   1750: baload
        //   1751: i2c
        //   1752: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   1755: aload_1
        //   1756: iconst_1
        //   1757: baload
        //   1758: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   1761: ldc_w 416
        //   1764: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1767: aload_0
        //   1768: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1771: ldc_w 463
        //   1774: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   1777: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1780: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1783: astore_2
        //   1784: aload_0
        //   1785: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1788: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1791: iload 7
        //   1793: aaload
        //   1794: ifnull -1631 -> 163
        //   1797: aload_0
        //   1798: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1801: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1804: iload 7
        //   1806: iconst_1
        //   1807: bastore
        //   1808: aload_0
        //   1809: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1812: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1815: iload 7
        //   1817: iconst_1
        //   1818: bastore
        //   1819: goto -1656 -> 163
        //   1822: aload_0
        //   1823: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1826: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1829: iload 7
        //   1831: aaload
        //   1832: ifnull -1669 -> 163
        //   1835: new 396	java/lang/StringBuilder
        //   1838: dup
        //   1839: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1842: aload_0
        //   1843: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1846: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1849: iload 7
        //   1851: aaload
        //   1852: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   1855: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1858: ldc_w 408
        //   1861: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1864: aload_1
        //   1865: iconst_0
        //   1866: baload
        //   1867: i2c
        //   1868: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   1871: aload_1
        //   1872: iconst_1
        //   1873: baload
        //   1874: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   1877: ldc_w 416
        //   1880: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1883: aload_0
        //   1884: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1887: ldc_w 464
        //   1890: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   1893: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1896: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1899: astore_2
        //   1900: aload_0
        //   1901: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1904: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1907: iload 7
        //   1909: aaload
        //   1910: ifnull -1747 -> 163
        //   1913: aload_0
        //   1914: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1917: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1920: iload 7
        //   1922: iconst_0
        //   1923: bastore
        //   1924: aload_0
        //   1925: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1928: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   1931: iload 7
        //   1933: iconst_0
        //   1934: bastore
        //   1935: goto -1772 -> 163
        //   1938: new 396	java/lang/StringBuilder
        //   1941: dup
        //   1942: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   1945: aload_1
        //   1946: iconst_0
        //   1947: baload
        //   1948: i2c
        //   1949: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   1952: ldc_w 462
        //   1955: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1958: aload_0
        //   1959: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1962: ldc_w 465
        //   1965: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   1968: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1971: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1974: astore_2
        //   1975: iconst_0
        //   1976: istore 13
        //   1978: iload 13
        //   1980: bipush 16
        //   1982: if_icmpge -1819 -> 163
        //   1985: aload_0
        //   1986: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   1989: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   1992: iload 6
        //   1994: iload 13
        //   1996: iadd
        //   1997: aaload
        //   1998: ifnull +434 -> 2432
        //   2001: aload_0
        //   2002: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2005: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   2008: iload 6
        //   2010: iload 13
        //   2012: iadd
        //   2013: aaload
        //   2014: invokevirtual 468	com/activehomevista/Device:getDeviceAllLightsOnCap	()Z
        //   2017: ifeq +415 -> 2432
        //   2020: aload_0
        //   2021: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2024: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   2027: iload 6
        //   2029: iload 13
        //   2031: iadd
        //   2032: iconst_1
        //   2033: bastore
        //   2034: aload_0
        //   2035: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2038: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   2041: iload 6
        //   2043: iload 13
        //   2045: iadd
        //   2046: iconst_1
        //   2047: bastore
        //   2048: aload_0
        //   2049: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2052: invokestatic 371	com/activehomevista/service/ActiveHomeVistaService:access$400	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   2055: iload 6
        //   2057: iload 13
        //   2059: iadd
        //   2060: bipush 100
        //   2062: bastore
        //   2063: aload_0
        //   2064: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2067: iload 6
        //   2069: iload 13
        //   2071: iadd
        //   2072: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   2075: goto +357 -> 2432
        //   2078: new 396	java/lang/StringBuilder
        //   2081: dup
        //   2082: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   2085: aload_1
        //   2086: iconst_0
        //   2087: baload
        //   2088: i2c
        //   2089: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   2092: ldc_w 462
        //   2095: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2098: aload_0
        //   2099: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2102: ldc_w 469
        //   2105: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   2108: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2111: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   2114: astore_2
        //   2115: iconst_0
        //   2116: istore 12
        //   2118: iload 12
        //   2120: bipush 16
        //   2122: if_icmpge -1959 -> 163
        //   2125: aload_0
        //   2126: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2129: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   2132: iload 6
        //   2134: iload 12
        //   2136: iadd
        //   2137: aaload
        //   2138: ifnull +300 -> 2438
        //   2141: aload_0
        //   2142: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2145: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   2148: iload 6
        //   2150: iload 12
        //   2152: iadd
        //   2153: aaload
        //   2154: invokevirtual 472	com/activehomevista/Device:getDeviceAllLightsOffCap	()Z
        //   2157: ifeq +281 -> 2438
        //   2160: aload_0
        //   2161: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2164: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   2167: iload 6
        //   2169: iload 12
        //   2171: iadd
        //   2172: iconst_0
        //   2173: bastore
        //   2174: aload_0
        //   2175: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2178: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   2181: iload 6
        //   2183: iload 12
        //   2185: iadd
        //   2186: iconst_0
        //   2187: bastore
        //   2188: aload_0
        //   2189: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2192: iload 6
        //   2194: iload 12
        //   2196: iadd
        //   2197: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   2200: goto +238 -> 2438
        //   2203: new 396	java/lang/StringBuilder
        //   2206: dup
        //   2207: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   2210: aload_1
        //   2211: iconst_0
        //   2212: baload
        //   2213: i2c
        //   2214: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   2217: ldc_w 462
        //   2220: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2223: aload_0
        //   2224: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2227: ldc_w 473
        //   2230: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   2233: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2236: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   2239: astore_2
        //   2240: iconst_0
        //   2241: istore 11
        //   2243: iload 11
        //   2245: bipush 16
        //   2247: if_icmpge -2084 -> 163
        //   2250: aload_0
        //   2251: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2254: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   2257: iload 6
        //   2259: iload 11
        //   2261: iadd
        //   2262: aaload
        //   2263: ifnull +181 -> 2444
        //   2266: aload_0
        //   2267: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2270: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   2273: iload 6
        //   2275: iload 11
        //   2277: iadd
        //   2278: aaload
        //   2279: invokevirtual 476	com/activehomevista/Device:getDeviceAllUnitsOffCap	()Z
        //   2282: ifeq +162 -> 2444
        //   2285: aload_0
        //   2286: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2289: invokestatic 365	com/activehomevista/service/ActiveHomeVistaService:access$300	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   2292: iload 6
        //   2294: iload 11
        //   2296: iadd
        //   2297: iconst_0
        //   2298: bastore
        //   2299: aload_0
        //   2300: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2303: invokestatic 368	com/activehomevista/service/ActiveHomeVistaService:access$500	(Lcom/activehomevista/service/ActiveHomeVistaService;)[B
        //   2306: iload 6
        //   2308: iload 11
        //   2310: iadd
        //   2311: iconst_0
        //   2312: bastore
        //   2313: aload_0
        //   2314: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2317: iload 6
        //   2319: iload 11
        //   2321: iadd
        //   2322: invokestatic 342	com/activehomevista/service/ActiveHomeVistaService:access$2000	(Lcom/activehomevista/service/ActiveHomeVistaService;I)V
        //   2325: goto +119 -> 2444
        //   2328: aload_0
        //   2329: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2332: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   2335: iload 7
        //   2337: aaload
        //   2338: ifnull -2175 -> 163
        //   2341: new 396	java/lang/StringBuilder
        //   2344: dup
        //   2345: invokespecial 397	java/lang/StringBuilder:<init>	()V
        //   2348: aload_0
        //   2349: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2352: invokestatic 394	com/activehomevista/service/ActiveHomeVistaService:access$3100	(Lcom/activehomevista/service/ActiveHomeVistaService;)[Lcom/activehomevista/Device;
        //   2355: iload 7
        //   2357: aaload
        //   2358: invokevirtual 402	com/activehomevista/Device:getName	()Ljava/lang/String;
        //   2361: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2364: ldc_w 408
        //   2367: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2370: aload_1
        //   2371: iconst_0
        //   2372: baload
        //   2373: i2c
        //   2374: invokevirtual 411	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
        //   2377: aload_1
        //   2378: iconst_1
        //   2379: baload
        //   2380: invokevirtual 414	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   2383: ldc_w 416
        //   2386: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2389: aload_0
        //   2390: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2393: ldc_w 477
        //   2396: invokevirtual 145	com/activehomevista/service/ActiveHomeVistaService:getString	(I)Ljava/lang/String;
        //   2399: invokevirtual 406	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2402: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   2405: astore_2
        //   2406: goto -2243 -> 163
        //   2409: aload_0
        //   2410: getfield 15	com/activehomevista/service/ActiveHomeVistaService$3:this$0	Lcom/activehomevista/service/ActiveHomeVistaService;
        //   2413: invokestatic 82	com/activehomevista/service/ActiveHomeVistaService:access$1100	(Lcom/activehomevista/service/ActiveHomeVistaService;)Landroid/os/RemoteCallbackList;
        //   2416: invokevirtual 105	android/os/RemoteCallbackList:finishBroadcast	()V
        //   2419: aload_0
        //   2420: aload_2
        //   2421: aload 5
        //   2423: invokespecial 479	com/activehomevista/service/ActiveHomeVistaService$3:createNotification	(Ljava/lang/String;Ljava/lang/String;)V
        //   2426: return
        //   2427: astore 10
        //   2429: goto -2224 -> 205
        //   2432: iinc 13 1
        //   2435: goto -457 -> 1978
        //   2438: iinc 12 1
        //   2441: goto -323 -> 2118
        //   2444: iinc 11 1
        //   2447: goto -204 -> 2243
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	2450	0	this	3
        //   3	2375	1	arrayOfByte	byte[]
        //   7	2414	2	str1	String
        //   641	1	3	localIOException	IOException
        //   35	2387	5	str2	String
        //   58	2264	6	i	int
        //   68	2288	7	j	int
        //   173	10	8	k	int
        //   176	30	9	m	int
        //   2427	1	10	localRemoteException	RemoteException
        //   2241	204	11	n	int
        //   2116	323	12	i1	int
        //   1976	457	13	i2	int
        //   1601	10	14	str3	String
        //   1261	60	15	str4	String
        //   913	60	16	str5	String
        //   734	60	17	str6	String
        //   275	260	18	str7	String
        // Exception table:
        //   from	to	target	type
        //   8	152	641	java/io/IOException
        //   152	163	641	java/io/IOException
        //   163	175	641	java/io/IOException
        //   185	205	641	java/io/IOException
        //   211	324	641	java/io/IOException
        //   327	374	641	java/io/IOException
        //   377	424	641	java/io/IOException
        //   427	474	641	java/io/IOException
        //   477	524	641	java/io/IOException
        //   527	601	641	java/io/IOException
        //   601	629	641	java/io/IOException
        //   629	638	641	java/io/IOException
        //   656	667	641	java/io/IOException
        //   670	783	641	java/io/IOException
        //   786	846	641	java/io/IOException
        //   849	962	641	java/io/IOException
        //   965	1044	641	java/io/IOException
        //   1044	1094	641	java/io/IOException
        //   1094	1139	641	java/io/IOException
        //   1139	1148	641	java/io/IOException
        //   1151	1180	641	java/io/IOException
        //   1183	1194	641	java/io/IOException
        //   1197	1310	641	java/io/IOException
        //   1313	1392	641	java/io/IOException
        //   1392	1433	641	java/io/IOException
        //   1433	1478	641	java/io/IOException
        //   1478	1487	641	java/io/IOException
        //   1490	1520	641	java/io/IOException
        //   1523	1534	641	java/io/IOException
        //   1537	1667	641	java/io/IOException
        //   1667	1689	641	java/io/IOException
        //   1692	1703	641	java/io/IOException
        //   1706	1819	641	java/io/IOException
        //   1822	1935	641	java/io/IOException
        //   1938	1975	641	java/io/IOException
        //   1985	2075	641	java/io/IOException
        //   2078	2115	641	java/io/IOException
        //   2125	2200	641	java/io/IOException
        //   2203	2240	641	java/io/IOException
        //   2250	2325	641	java/io/IOException
        //   2328	2406	641	java/io/IOException
        //   2409	2426	641	java/io/IOException
        //   185	205	2427	android/os/RemoteException
      }
      
      private void resetKeepAliveTimer()
      {
        ActiveHomeVistaService.access$2802(ActiveHomeVistaService.this, false);
        ActiveHomeVistaService.this.keepAliveTimer.cancel();
        ActiveHomeVistaService.access$2902(ActiveHomeVistaService.this, new Timer());
        ActiveHomeVistaService.this.keepAliveTimer.schedule(new KeepAliveTimerTask(), 60000L, 60000L);
      }
      
      public void run()
      {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)ActiveHomeVistaService.this.getSystemService("connectivity");
        ActiveHomeVistaService.this.updateWidget(-1);
        while (!ActiveHomeVistaService.this.terminated)
        {
          for (;;)
          {
            byte[] arrayOfByte;
            try
            {
              NetworkInfo localNetworkInfo1 = localConnectivityManager.getActiveNetworkInfo();
              if ((localNetworkInfo1 == null) || (!localNetworkInfo1.isConnectedOrConnecting())) {
                break label567;
              }
              ActiveHomeVistaService.access$1502(ActiveHomeVistaService.this, new Socket());
              ActiveHomeVistaService.access$2202(ActiveHomeVistaService.this, null);
              ActiveHomeVistaService.access$1402(ActiveHomeVistaService.this, null);
              ActiveHomeVistaService.this.MySocket.setSoTimeout(60000);
              ActiveHomeVistaService.this.MySocket.connect(ActiveHomeVistaService.getAddr(ActiveHomeVistaService.this.preferences, ActiveHomeVistaService.this.getApplicationContext()), 5000);
              if ((ActiveHomeVistaService.this.MySocket.isConnected()) && (!ActiveHomeVistaService.this.MySocket.isClosed()))
              {
                ActiveHomeVistaService.access$2202(ActiveHomeVistaService.this, new DataInputStream(ActiveHomeVistaService.this.MySocket.getInputStream()));
                ActiveHomeVistaService.access$1402(ActiveHomeVistaService.this, new DataOutputStream(ActiveHomeVistaService.this.MySocket.getOutputStream()));
                resetKeepAliveTimer();
                ActiveHomeVistaService.this.serverGetConnected();
                ActiveHomeVistaService.this.serverGetInterfaceType();
                ActiveHomeVistaService.this.serverGetMonitoredHouseCode();
                ActiveHomeVistaService.this.serverGetStatus();
                ActiveHomeVistaService.this.serverGetFile();
                arrayOfByte = new byte[5];
                if ((!ActiveHomeVistaService.this.MySocket.isConnected()) || (ActiveHomeVistaService.this.MySocket.isClosed()) || (ActiveHomeVistaService.this.terminated)) {
                  break;
                }
                int i = ActiveHomeVistaService.this.connectionStatus;
                if (i != 0) {
                  break;
                }
                try
                {
                  NetworkInfo localNetworkInfo2 = localConnectivityManager.getActiveNetworkInfo();
                  if ((localNetworkInfo2 == null) || (!localNetworkInfo2.isConnectedOrConnecting())) {
                    break label554;
                  }
                  if (ActiveHomeVistaService.this.in.read(arrayOfByte, 0, 5) != 5) {
                    break label541;
                  }
                  resetKeepAliveTimer();
                  switch (arrayOfByte[0])
                  {
                  case 0: 
                  default: 
                    Log.e("ActiveHomeVistaService", "Unknown command received");
                    closeSocket();
                    sleep(5000L);
                  }
                }
                catch (SocketTimeoutException localSocketTimeoutException) {}
                continue;
              }
              sleep(60000L);
              continue;
            }
            catch (Exception localException)
            {
              closeSocket();
              try
              {
                if (localException.getClass() == InterruptedException.class) {
                  break;
                }
                sleep(5000L);
              }
              catch (InterruptedException localInterruptedException) {}
            }
            break;
            handleSetConnected();
            continue;
            handleSetStatus();
            continue;
            handleUpdateDevice();
            continue;
            handleSetInterfaceType();
            continue;
            handleSetMonitoredHouseCode();
            continue;
            handleSetFile(ActiveHomeVistaService.getIntFromBuf(arrayOfByte, 1));
            continue;
            handleSetFileQuery(ActiveHomeVistaService.getIntFromBuf(arrayOfByte, 1));
            continue;
            label541:
            closeSocket();
            sleep(5000L);
            continue;
            label554:
            closeSocket();
            sleep(60000L);
          }
          label567:
          closeSocket();
          sleep(60000L);
        }
        closeSocket();
        ActiveHomeVistaService.this.stopSelf();
        Log.i("ActiveHomeVistaService", "Service stopped");
      }
      
      class KeepAliveTimerTask
        extends TimerTask
      {
        KeepAliveTimerTask() {}
        
        public void run()
        {
          if (!ActiveHomeVistaService.this.keepAlivePending)
          {
            ActiveHomeVistaService.access$2802(ActiveHomeVistaService.this, true);
            ActiveHomeVistaService.this.serverKeepAlive();
            return;
          }
          ActiveHomeVistaService.access$2802(ActiveHomeVistaService.this, false);
          Log.i("ActiveHomeVistaService", "No keep alive acknowledge received within time limit, closing socket");
          ActiveHomeVistaService.this.shutdownNow();
        }
      }
    };
  }
  
  private static void setIntInBuf(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >> 24));
    paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 << 8 >> 24));
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 << 16 >> 24));
    paramArrayOfByte[paramInt2] = ((byte)(paramInt1 << 24 >> 24));
  }
  
  private void shutdownNow()
  {
    shutdownSocket();
    this.serviceThread.interrupt();
  }
  
  private void shutdownSocket()
  {
    try
    {
      if (this.MySocket != null)
      {
        this.MySocket.shutdownInput();
        this.MySocket.shutdownOutput();
      }
      return;
    }
    catch (IOException localIOException) {}
  }
  
  private void updateWidget(int paramInt)
  {
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(this);
    int[] arrayOfInt = localAppWidgetManager.getAppWidgetIds(new ComponentName(this, ActiveHomeVistaWidgetProvider.class));
    int i = arrayOfInt.length;
    int j = 0;
    if (j < i)
    {
      int k = arrayOfInt[j];
      int m = getIndexFromAppWidgetId(k);
      RemoteViews localRemoteViews;
      if ((paramInt == m) || (paramInt == -1))
      {
        localRemoteViews = ActiveHomeVistaWidgetProvider.createWidgetView(this, k);
        if (this.houseRefs[m] == null) {
          break label339;
        }
        if (this.OnStatusDevices[m] + this.OnStatusDevicesExtended[m] == 0) {
          break label249;
        }
        if (this.houseRefs[m].getOnBitmap() == null) {
          break label179;
        }
        localRemoteViews.setImageViewBitmap(2131558492, this.houseRefs[m].getOnBitmap());
        label127:
        if (!ConfigureWidget.loadStatusIndicationColor(this, k)) {
          break label227;
        }
        localRemoteViews.setImageViewResource(2131558491, 2130837577);
        label147:
        localRemoteViews.setTextViewText(2131558493, this.houseRefs[m].getName());
      }
      for (;;)
      {
        localAppWidgetManager.updateAppWidget(k, localRemoteViews);
        j++;
        break;
        label179:
        if (this.houseRefs[m].getOffBitmap() != null)
        {
          localRemoteViews.setImageViewBitmap(2131558492, this.houseRefs[m].getOffBitmap());
          break label127;
        }
        localRemoteViews.setImageViewResource(2131558492, 2130837579);
        break label127;
        label227:
        if (Build.VERSION.SDK_INT >= 14) {
          break label147;
        }
        localRemoteViews.setImageViewResource(2131558491, 2130837579);
        break label147;
        label249:
        if (this.houseRefs[m].getOffBitmap() != null) {
          localRemoteViews.setImageViewBitmap(2131558492, this.houseRefs[m].getOffBitmap());
        }
        for (;;)
        {
          if (!ConfigureWidget.loadStatusIndicationColor(this, k)) {
            break label317;
          }
          localRemoteViews.setImageViewResource(2131558491, 2130837578);
          break;
          localRemoteViews.setImageViewResource(2131558492, 2130837579);
        }
        label317:
        if (Build.VERSION.SDK_INT >= 14) {
          break label147;
        }
        localRemoteViews.setImageViewResource(2131558491, 2130837579);
        break label147;
        label339:
        localRemoteViews.setImageViewResource(2131558491, 2130837579);
        localRemoteViews.setImageViewResource(2131558492, 2130837579);
        localRemoteViews.setTextViewText(2131558493, getString(2131099739));
      }
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.X10_service;
  }
  
  public void onCreate()
  {
    Log.i("ActiveHomeVistaService", "Service started");
    super.onCreate();
    this.fileUniqueId = HouseCache.readIDFile(this);
    if (this.fileUniqueId != -1) {}
    try
    {
      this.houseBuf = HouseCache.readHouseFile(this);
      if (!readHouse(this.houseBuf)) {
        this.fileUniqueId = -1;
      }
      initPreferences();
      this.notificationManager = ((NotificationManager)getSystemService("notification"));
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      this.mReceiver = new ConnectivityBroadcastReceiver(null);
      registerReceiver(this.mReceiver, localIntentFilter);
      this.serviceThread = serviceThread();
      this.serviceThread.start();
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      for (;;)
      {
        this.house.clear();
        clearHouseRefs();
        System.gc();
        this.fileUniqueId = -1;
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        this.fileUniqueId = -1;
      }
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    unregisterReceiver(this.mReceiver);
    this.terminated = true;
    shutdownSocket();
    this.mCallbacks.kill();
    this.notificationManager.cancelAll();
    Log.i("ActiveHomeVistaService", "Service destroyed");
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    handleStartCommand(paramIntent);
    return 1;
  }
  
  private class ConnectivityBroadcastReceiver
    extends BroadcastReceiver
  {
    private ConnectivityBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
      {
        NetworkInfo localNetworkInfo = ((ConnectivityManager)ActiveHomeVistaService.this.getSystemService("connectivity")).getActiveNetworkInfo();
        if ((localNetworkInfo == null) || (!localNetworkInfo.isConnectedOrConnecting())) {
          ActiveHomeVistaService.this.shutdownNow();
        }
      }
    }
  }
  
  private class WidgetClickTimerTask
    extends TimerTask
  {
    private int counter = 0;
    private final Intent intent;
    
    public WidgetClickTimerTask(Intent paramIntent)
    {
      this.intent = paramIntent;
    }
    
    public void run()
    {
      this.counter = (1 + this.counter);
      int j;
      if (ActiveHomeVistaService.this.connected)
      {
        cancel();
        if (ActiveHomeVistaService.this.houseRefs != null)
        {
          int i = this.intent.getExtras().getInt("appWidgetId", 0);
          j = ActiveHomeVistaService.this.getIndexFromAppWidgetId(i);
          if (ActiveHomeVistaService.this.houseRefs[j] != null) {
            switch (ActiveHomeVistaService.this.houseRefs[j].getDeviceType())
            {
            default: 
              if (ActiveHomeVistaService.this.houseRefs[j].getDeviceOnlyOnCmdCap()) {
                ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
              }
              break;
            }
          }
        }
      }
      label620:
      do
      {
        do
        {
          return;
          ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
          return;
          if ((ActiveHomeVistaService.this.houseRefs[j].getDeviceExtendedType0Cap()) || (ActiveHomeVistaService.this.houseRefs[j].getDeviceExtendedType3Cap()))
          {
            if (ActiveHomeVistaService.this.OnStatusDevices[j] + ActiveHomeVistaService.this.OnStatusDevicesExtended[j] == 0)
            {
              ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)2, (byte)0, (byte)0, ActiveHomeVistaService.this.houseRefs[j].getDimDuration());
              return;
            }
            ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)3, (byte)0, (byte)0, ActiveHomeVistaService.this.houseRefs[j].getDimDuration());
            return;
          }
          if (ActiveHomeVistaService.this.OnStatusDevices[j] != 0) {
            break label620;
          }
          try
          {
            IActiveHomeVistaService.Stub localStub = ActiveHomeVistaService.this.X10_service;
            k = 0;
            if (localStub != null)
            {
              boolean bool = ActiveHomeVistaService.this.X10_service.getEmulationDimMemoryCap();
              k = bool;
            }
          }
          catch (RemoteException localRemoteException)
          {
            for (;;)
            {
              int m;
              int k = 0;
            }
            ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
            return;
          }
          if ((ActiveHomeVistaService.this.houseRefs[j].getDeviceDimMemoryCap()) || (!ActiveHomeVistaService.this.houseRefs[j].getDeviceDimsCap()) || (k == 0)) {
            break;
          }
          m = ActiveHomeVistaService.this.DimStatusDevices[j];
          ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
        } while (m >= 100);
        ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)4, (byte)0, (byte)(100 - m), (byte)0);
        return;
        ActiveHomeVistaService.this.localDeviceFunction(ActiveHomeVistaService.this.houseRefs[j].getHouseCode(), ActiveHomeVistaService.this.houseRefs[j].getDeviceCode(), (byte)3, (byte)0, (byte)0, (byte)0);
        return;
        if (this.counter == 1)
        {
          ActiveHomeVistaService.this.shutdownNow();
          return;
        }
      } while (this.counter != 25);
      cancel();
    }
  }
  
  private class localDeviceFunctionTask
    extends AsyncTask<Void, Void, Void>
  {
    final byte deviceCode;
    final byte dimDuration;
    final byte dims;
    final byte extendedCode;
    final byte functionCode;
    final char houseCode;
    
    public localDeviceFunctionTask(char paramChar, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5)
    {
      this.houseCode = paramChar;
      this.deviceCode = paramByte1;
      this.functionCode = paramByte2;
      this.extendedCode = paramByte3;
      this.dims = paramByte4;
      this.dimDuration = paramByte5;
    }
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      ActiveHomeVistaService.this.localDeviceFunction(this.houseCode, this.deviceCode, this.functionCode, this.extendedCode, this.dims, this.dimDuration);
      return null;
    }
  }
}
