package com.activehomevista;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.app.backup.BackupManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.activehomevista.service.ActiveHomeVistaService;
import com.activehomevista.service.IActiveHomeVistaService;
import com.activehomevista.service.IActiveHomeVistaServiceCallback;
import com.activehomevista.service.IActiveHomeVistaServiceCallback.Stub;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

public class ActiveHomeVista
  extends AppCompatActivity
{
  private static final int BEGIN_UPDATE_HOUSE = 0;
  private static final int END_UPDATE_HOUSE = 1;
  private static final String TAG = "ActiveHomeVista";
  private static final int UPDATE_CONNECTED = 3;
  private static final int UPDATE_CONNECTION_STATUS = 5;
  private static final int UPDATE_DEVICE_STATUS = 2;
  private static final int UPDATE_INTERFACE_TYPE = 4;
  private static List<Room> house = new LinkedList();
  private static myHandler mHandler;
  private static final String serviceName = "com.activehomevista.service.X10_SERVICE";
  private final byte[] DimStatusDevices = new byte['Ā'];
  private final byte[] OnStatusDevices = new byte['Ā'];
  private final byte[] OnStatusDevicesExtended = new byte['Ā'];
  private boolean RFCap = false;
  private TextView activeHouseCodeTextView;
  private AlertDialog alertDialog = null;
  private Button allLightsOffButton;
  private Button allLightsOnButton;
  private Button allUnitsOffButton;
  private Button allUnitsOnButton;
  private ServiceConnection conn;
  private boolean connected = false;
  private LinearLayout deviceActionsLinearLayout;
  private ListView deviceListView;
  private SeekBar dimsSeekBar;
  private Toast dimsToast = null;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private boolean emulationDimMemoryCap = false;
  private int fileUniqueIdPersistent = -1;
  private int fileUniqueIdVolatile = -1;
  private boolean globalFunctionAllLightsOffCap = false;
  private boolean interfaceConnected = false;
  private boolean isServiceStarted = false;
  private boolean isVisible = false;
  private int lastDeviceListPosition = -1;
  private int lastRoomListPosition = -1;
  private final IActiveHomeVistaServiceCallback mCallback = new IActiveHomeVistaServiceCallback.Stub()
  {
    public void beginUpdateHouse()
      throws RemoteException
    {
      ActiveHomeVista.mHandler.sendMessage(ActiveHomeVista.mHandler.obtainMessage(0));
    }
    
    public void endUpdateHouse()
      throws RemoteException
    {
      ActiveHomeVista.mHandler.sendMessage(ActiveHomeVista.mHandler.obtainMessage(1));
    }
    
    public void updateConnectionStatus(int paramAnonymousInt)
      throws RemoteException
    {
      ActiveHomeVista.mHandler.sendMessage(ActiveHomeVista.mHandler.obtainMessage(5, Integer.valueOf(paramAnonymousInt)));
    }
    
    public void updateDeviceStatus()
      throws RemoteException
    {
      ActiveHomeVista.mHandler.sendMessage(ActiveHomeVista.mHandler.obtainMessage(2));
    }
    
    public void updateInterfaceStatus(boolean paramAnonymousBoolean, int paramAnonymousInt)
      throws RemoteException
    {
      ActiveHomeVista.mHandler.sendMessage(ActiveHomeVista.mHandler.obtainMessage(3, Boolean.valueOf(paramAnonymousBoolean)));
      ActiveHomeVista.mHandler.sendMessage(ActiveHomeVista.mHandler.obtainMessage(4, Integer.valueOf(paramAnonymousInt)));
    }
  };
  private SharedPreferences.OnSharedPreferenceChangeListener myPreferenceListener;
  private IActiveHomeVistaService myService;
  private ImageButton nextButton;
  private Button offButton;
  private Button onButton;
  private ToggleButton onOffToggleButton;
  private ImageButton playButton;
  private Button powerStateButton;
  private SharedPreferences preferences;
  private ImageButton previousButton;
  private ProgressDialog progressDialog = null;
  private ListView roomListView;
  private boolean statusAlertActive = false;
  private Button statusButton;
  private TextView statusTextView;
  private ImageButton stopButton;
  
  public ActiveHomeVista() {}
  
  private void CreateDimsAlert(int paramInt)
  {
    this.dimsToast = Toast.makeText(this, getString(2131099708) + ": " + paramInt + "%", 0);
    this.dimsToast.show();
  }
  
  private void UpdateCapacities()
  {
    if (this.myService != null) {}
    try
    {
      this.globalFunctionAllLightsOffCap = this.myService.getGlobalFunctionAllLightsOffCap();
      this.emulationDimMemoryCap = this.myService.getEmulationDimMemoryCap();
      this.RFCap = this.myService.getRFCap();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e("ActiveHomeVista", "Getting capacities failed");
    }
  }
  
  private void UpdateConnectionStatus(Integer paramInteger)
  {
    if ((!isFinishing()) && (this.isVisible)) {
      switch (paramInteger.intValue())
      {
      default: 
        Log.e("ActiveHomeVista", "Unknown status received");
      }
    }
    do
    {
      do
      {
        do
        {
          return;
        } while (this.statusAlertActive);
        this.statusAlertActive = true;
        AlertDialog.Builder localBuilder3 = new AlertDialog.Builder(this);
        localBuilder3.setTitle(2131099689);
        localBuilder3.setIcon(2130837581);
        localBuilder3.setMessage(2131099688);
        localBuilder3.setPositiveButton(17039370, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            ActiveHomeVista.access$3702(ActiveHomeVista.this, false);
            ActiveHomeVista.this.startPreferences();
          }
        });
        localBuilder3.setNegativeButton(17039360, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            ActiveHomeVista.access$3702(ActiveHomeVista.this, false);
          }
        });
        this.alertDialog = localBuilder3.create();
        this.alertDialog.show();
        return;
      } while (this.statusAlertActive);
      this.statusAlertActive = true;
      AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(this);
      localBuilder2.setTitle(2131099691);
      localBuilder2.setIcon(2130837581);
      localBuilder2.setMessage(2131099690);
      localBuilder2.setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ActiveHomeVista.access$3702(ActiveHomeVista.this, false);
        }
      });
      this.alertDialog = localBuilder2.create();
      this.alertDialog.show();
      return;
    } while (this.statusAlertActive);
    this.statusAlertActive = true;
    AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
    localBuilder1.setTitle(2131099693);
    localBuilder1.setIcon(2130837581);
    localBuilder1.setMessage(2131099692);
    localBuilder1.setPositiveButton(17039370, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setData(Uri.parse("market://search?q=pname:com.activehomevista"));
        try
        {
          ActiveHomeVista.this.startActivity(localIntent);
          ActiveHomeVista.access$3702(ActiveHomeVista.this, false);
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          for (;;) {}
        }
      }
    });
    localBuilder1.setNegativeButton(17039360, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ActiveHomeVista.access$3702(ActiveHomeVista.this, false);
      }
    });
    this.alertDialog = localBuilder1.create();
    this.alertDialog.show();
  }
  
  private void UpdateDimsAlert(int paramInt)
  {
    if (this.dimsToast == null)
    {
      CreateDimsAlert(paramInt);
      return;
    }
    this.dimsToast.setText(getString(2131099708) + ": " + paramInt + "%");
    this.dimsToast.show();
  }
  
  private void UpdateInterfaceType(Integer paramInteger)
  {
    String str;
    if (!isFinishing())
    {
      if (!this.connected) {
        break label198;
      }
      this.interfaceConnected = true;
      switch (paramInteger.intValue())
      {
      case 5: 
      default: 
        str = getString(2131099694);
      }
    }
    for (;;)
    {
      supportInvalidateOptionsMenu();
      this.statusTextView.setText(str);
      roomButtonsSetEnabled((Room)this.roomListView.getItemAtPosition(this.roomListView.getCheckedItemPosition()));
      deviceButtonsSetEnabled((Device)this.deviceListView.getItemAtPosition(this.deviceListView.getCheckedItemPosition()));
      return;
      str = getString(2131099695);
      continue;
      str = getString(2131099696);
      continue;
      str = getString(2131099697);
      continue;
      str = getString(2131099698);
      continue;
      str = getString(2131099699);
      continue;
      str = getString(2131099738);
      this.interfaceConnected = false;
      continue;
      label198:
      str = getString(2131099700);
      this.interfaceConnected = false;
    }
  }
  
  private void cancelAlertDialog()
  {
    if (this.alertDialog != null)
    {
      this.alertDialog.dismiss();
      this.alertDialog = null;
    }
  }
  
  private void cancelProgressDialog()
  {
    if (this.progressDialog != null)
    {
      this.progressDialog.dismiss();
      this.progressDialog = null;
    }
  }
  
  private void checkNoServerAddressSupplied()
  {
    if ((this.preferences.getString(getString(2131099718), "").equals("")) && (this.preferences.getString(getString(2131099716), "").equals("")))
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle(2131099780);
      localBuilder.setIcon(2130837581);
      localBuilder.setMessage(2131099779);
      localBuilder.setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ActiveHomeVista.this.startPreferences();
        }
      });
      localBuilder.setNegativeButton(17039360, null);
      this.alertDialog = localBuilder.create();
      this.alertDialog.show();
    }
  }
  
  private boolean connectedPlus()
  {
    return (this.connected) && (this.interfaceConnected);
  }
  
  private void deviceButtonsSetEnabled(Device paramDevice)
  {
    if (paramDevice != null)
    {
      this.powerStateButton.setEnabled(this.connected);
      this.powerStateButton.setFocusable(this.connected);
      this.stopButton.setEnabled(this.connected);
      this.stopButton.setFocusable(this.connected);
      this.previousButton.setEnabled(this.connected);
      this.previousButton.setFocusable(this.connected);
      this.playButton.setEnabled(this.connected);
      this.playButton.setFocusable(this.connected);
      this.nextButton.setEnabled(this.connected);
      this.nextButton.setFocusable(this.connected);
      this.onOffToggleButton.setEnabled(connectedPlus());
      this.onOffToggleButton.setFocusable(connectedPlus());
      this.onButton.setEnabled(connectedPlus());
      this.onButton.setFocusable(connectedPlus());
      this.offButton.setEnabled(connectedPlus());
      this.offButton.setFocusable(connectedPlus());
      this.statusButton.setEnabled(connectedPlus());
      this.statusButton.setFocusable(connectedPlus());
      switch (paramDevice.getDeviceType())
      {
      default: 
        if (paramDevice.getDeviceDimsCap())
        {
          this.dimsSeekBar.setEnabled(connectedPlus());
          this.dimsSeekBar.setFocusable(connectedPlus());
          return;
        }
        break;
      case 13: 
      case 17: 
      case 44: 
      case 45: 
      case 46: 
      case 47: 
        this.dimsSeekBar.setEnabled(false);
        this.dimsSeekBar.setFocusable(false);
        return;
      }
      this.dimsSeekBar.setEnabled(false);
      this.dimsSeekBar.setProgress(0);
      this.dimsSeekBar.setFocusable(false);
      return;
    }
    this.powerStateButton.setEnabled(false);
    this.powerStateButton.setFocusable(false);
    this.stopButton.setEnabled(false);
    this.stopButton.setFocusable(false);
    this.previousButton.setEnabled(false);
    this.previousButton.setFocusable(false);
    this.playButton.setEnabled(false);
    this.playButton.setFocusable(false);
    this.nextButton.setEnabled(false);
    this.nextButton.setFocusable(false);
    this.onOffToggleButton.setEnabled(false);
    this.onOffToggleButton.setFocusable(false);
    this.onButton.setEnabled(false);
    this.onButton.setFocusable(false);
    this.offButton.setEnabled(false);
    this.offButton.setFocusable(false);
    this.statusButton.setEnabled(false);
    this.statusButton.setFocusable(false);
    this.dimsSeekBar.setEnabled(false);
    this.dimsSeekBar.setFocusable(false);
    this.dimsSeekBar.setProgress(0);
  }
  
  private void deviceButtonsSetVisibility(Device paramDevice)
  {
    if (paramDevice != null)
    {
      if (this.drawerToggle == null) {
        this.deviceActionsLinearLayout.setVisibility(0);
      }
      this.dimsSeekBar.setVisibility(0);
      switch (paramDevice.getDeviceType())
      {
      default: 
        if (paramDevice.getDeviceOnlyOnCmdCap())
        {
          this.onButton.setTag(paramDevice);
          this.onButton.setVisibility(0);
          if ((!paramDevice.getDeviceRFDeviceCap()) || (this.RFCap))
          {
            this.onButton.setClickable(true);
            this.onButton.setFocusable(true);
            this.offButton.setVisibility(8);
            this.onOffToggleButton.setVisibility(8);
            this.stopButton.setVisibility(8);
            this.previousButton.setVisibility(8);
            this.playButton.setVisibility(8);
            this.nextButton.setVisibility(8);
            this.powerStateButton.setVisibility(8);
            this.statusButton.setVisibility(8);
            if (this.drawerToggle == null) {
              this.dimsSeekBar.setVisibility(8);
            }
          }
        }
        break;
      }
    }
    label688:
    label711:
    label810:
    label829:
    label841:
    do
    {
      do
      {
        do
        {
          do
          {
            return;
            this.powerStateButton.setText(2131099712);
            this.powerStateButton.setTag(paramDevice);
            this.powerStateButton.setVisibility(0);
            this.onOffToggleButton.setVisibility(8);
            this.onButton.setVisibility(8);
            this.offButton.setVisibility(8);
            this.stopButton.setVisibility(8);
            this.previousButton.setVisibility(8);
            this.playButton.setVisibility(8);
            this.nextButton.setVisibility(8);
            this.statusButton.setVisibility(8);
          } while (this.drawerToggle != null);
          this.dimsSeekBar.setVisibility(8);
          return;
          this.stopButton.setTag(paramDevice);
          this.previousButton.setTag(paramDevice);
          this.playButton.setTag(paramDevice);
          this.nextButton.setTag(paramDevice);
          this.stopButton.setVisibility(0);
          this.previousButton.setVisibility(0);
          this.playButton.setVisibility(0);
          this.nextButton.setVisibility(0);
          this.onOffToggleButton.setVisibility(8);
          this.onButton.setVisibility(8);
          this.offButton.setVisibility(8);
          this.powerStateButton.setVisibility(8);
          this.statusButton.setVisibility(8);
        } while (this.drawerToggle != null);
        this.dimsSeekBar.setVisibility(8);
        return;
        this.onButton.setTag(paramDevice);
        this.offButton.setTag(paramDevice);
        this.onButton.setVisibility(0);
        this.offButton.setVisibility(0);
        this.onOffToggleButton.setVisibility(8);
        this.stopButton.setVisibility(8);
        this.previousButton.setVisibility(8);
        this.playButton.setVisibility(8);
        this.nextButton.setVisibility(8);
        this.powerStateButton.setVisibility(8);
        this.statusButton.setVisibility(8);
      } while (this.drawerToggle != null);
      this.dimsSeekBar.setVisibility(8);
      return;
      this.onButton.setClickable(false);
      this.onButton.setFocusable(false);
      break;
      this.onOffToggleButton.setTag(paramDevice);
      this.onOffToggleButton.setVisibility(0);
      ToggleButton localToggleButton = this.onOffToggleButton;
      boolean bool;
      if ((getDeviceOnStatus(paramDevice)) || (getDeviceOnStatusExtended(paramDevice)))
      {
        bool = true;
        localToggleButton.setChecked(bool);
        if (!paramDevice.getDeviceDimsCap()) {
          break label810;
        }
        this.dimsSeekBar.setTag(paramDevice);
        if (this.drawerToggle == null) {
          this.dimsSeekBar.setVisibility(0);
        }
        this.dimsSeekBar.setProgress(getDeviceDimStatus(paramDevice));
        if (!paramDevice.getDeviceStatusCap()) {
          break label829;
        }
        this.statusButton.setTag(paramDevice);
        this.statusButton.setVisibility(0);
        if ((paramDevice.getDeviceRFDeviceCap()) && (!this.RFCap)) {
          break label841;
        }
        this.onOffToggleButton.setClickable(true);
        this.onOffToggleButton.setFocusable(true);
      }
      for (;;)
      {
        this.stopButton.setVisibility(8);
        this.previousButton.setVisibility(8);
        this.playButton.setVisibility(8);
        this.nextButton.setVisibility(8);
        this.onButton.setVisibility(8);
        this.offButton.setVisibility(8);
        this.powerStateButton.setVisibility(8);
        return;
        bool = false;
        break;
        if (this.drawerToggle != null) {
          break label688;
        }
        this.dimsSeekBar.setVisibility(8);
        break label688;
        this.statusButton.setVisibility(8);
        break label711;
        this.onOffToggleButton.setClickable(false);
        this.onOffToggleButton.setFocusable(false);
      }
      if (this.drawerToggle == null) {
        this.deviceActionsLinearLayout.setVisibility(8);
      }
      this.powerStateButton.setVisibility(8);
      this.onOffToggleButton.setVisibility(8);
      this.onButton.setVisibility(8);
      this.offButton.setVisibility(8);
      this.stopButton.setVisibility(8);
      this.previousButton.setVisibility(8);
      this.playButton.setVisibility(8);
      this.nextButton.setVisibility(8);
      this.statusButton.setVisibility(8);
    } while (this.drawerToggle != null);
    this.dimsSeekBar.setVisibility(8);
  }
  
  private void executeDeviceCommand(Device paramDevice, byte paramByte)
  {
    executeDeviceCommand(paramDevice, paramByte, (byte)0);
  }
  
  private void executeDeviceCommand(Device paramDevice, byte paramByte1, byte paramByte2)
  {
    try
    {
      if ((this.myService != null) && (paramDevice != null)) {
        this.myService.deviceFunction(paramDevice.getHouseCode(), paramDevice.getDeviceCode(), paramByte1, (byte)0, paramByte2, (byte)0);
      }
      return;
    }
    catch (RemoteException localRemoteException) {}
  }
  
  private void executeDimsSeekBarUpdate(SeekBar paramSeekBar)
  {
    Device localDevice = (Device)paramSeekBar.getTag();
    try
    {
      if ((this.myService != null) && (localDevice != null) && (paramSeekBar.getProgress() != getDeviceDimStatus(localDevice)))
      {
        if (localDevice.getDeviceExtendedType0Cap())
        {
          this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)7, (byte)1, (byte)paramSeekBar.getProgress(), (byte)0);
          return;
        }
        if (localDevice.getDeviceExtendedType3Cap())
        {
          this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)7, (byte)0, (byte)paramSeekBar.getProgress(), localDevice.getDimDuration());
          return;
        }
        if ((localDevice.getDeviceDimMemoryCap()) || (getDeviceOnStatus(localDevice)) || (!this.emulationDimMemoryCap))
        {
          if (getDeviceDimStatus(localDevice) < paramSeekBar.getProgress())
          {
            this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)5, (byte)0, (byte)(paramSeekBar.getProgress() - getDeviceDimStatus(localDevice)), (byte)0);
            return;
          }
          this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)4, (byte)0, (byte)(getDeviceDimStatus(localDevice) - paramSeekBar.getProgress()), (byte)0);
          return;
        }
        this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
        if (paramSeekBar.getProgress() < 100) {
          this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)4, (byte)0, (byte)(100 - paramSeekBar.getProgress()), (byte)0);
        }
      }
      return;
    }
    catch (RemoteException localRemoteException) {}
  }
  
  private void executeRoomCommand(Room paramRoom, byte paramByte)
  {
    try
    {
      if ((this.myService != null) && (paramRoom != null)) {
        this.myService.roomFunction(paramRoom.getId(), paramByte);
      }
      return;
    }
    catch (RemoteException localRemoteException) {}
  }
  
  private boolean getFile()
  {
    boolean[] arrayOfBoolean = new boolean[1];
    for (;;)
    {
      try
      {
        int[] arrayOfInt = new int[1];
        if (this.myService == null) {
          continue;
        }
        house = this.myService.getLocalHouse(arrayOfBoolean, arrayOfInt);
        if ((arrayOfInt[0] != this.fileUniqueIdVolatile) || (arrayOfInt[0] == -1) || (arrayOfBoolean[0] != 0))
        {
          RoomListAdapter localRoomListAdapter = new RoomListAdapter(this, house);
          this.roomListView.setAdapter(localRoomListAdapter);
          this.fileUniqueIdVolatile = arrayOfInt[0];
        }
        if ((arrayOfInt[0] != this.fileUniqueIdPersistent) || (arrayOfInt[0] == -1) || (arrayOfBoolean[0] != 0))
        {
          this.lastRoomListPosition = -1;
          this.lastDeviceListPosition = -1;
          this.fileUniqueIdPersistent = arrayOfInt[0];
        }
        bool1 = this.roomListView.isHapticFeedbackEnabled();
        this.roomListView.setHapticFeedbackEnabled(false);
        bool2 = this.roomListView.isSoundEffectsEnabled();
        this.roomListView.setSoundEffectsEnabled(false);
        if ((this.lastRoomListPosition < 0) || (this.lastRoomListPosition >= house.size())) {
          continue;
        }
        int i = this.lastDeviceListPosition;
        this.roomListView.performItemClick(this.roomListView, this.lastRoomListPosition, this.lastRoomListPosition);
        this.roomListView.setSelection(this.lastRoomListPosition);
        if ((i >= 0) && (i < ((Room)house.get(this.lastRoomListPosition)).devices.size()))
        {
          boolean bool3 = this.deviceListView.isHapticFeedbackEnabled();
          this.deviceListView.setHapticFeedbackEnabled(false);
          boolean bool4 = this.deviceListView.isSoundEffectsEnabled();
          this.deviceListView.setSoundEffectsEnabled(false);
          this.deviceListView.performItemClick(this.deviceListView, i, i);
          this.deviceListView.setSelection(i);
          this.deviceListView.setHapticFeedbackEnabled(bool3);
          this.deviceListView.setSoundEffectsEnabled(bool4);
        }
      }
      catch (RemoteException localRemoteException)
      {
        boolean bool1;
        boolean bool2;
        Log.e("ActiveHomeVista", "Getting file failed");
        continue;
        this.lastRoomListPosition = -1;
        if (house.size() <= 0) {
          continue;
        }
        this.roomListView.performItemClick(this.roomListView, 0, 0L);
        continue;
      }
      this.roomListView.setHapticFeedbackEnabled(bool1);
      this.roomListView.setSoundEffectsEnabled(bool2);
      return arrayOfBoolean[0];
      house.clear();
    }
  }
  
  private String getSoftwareVersion()
  {
    try
    {
      String str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return "";
  }
  
  private void handleServiceDisconnect()
  {
    try
    {
      if (this.myService != null) {
        this.myService.unregisterCallback(this.mCallback);
      }
      this.myService = null;
      this.connected = false;
      UpdateInterfaceType(Integer.valueOf(6));
      Log.i("ActiveHomeVista", "Disconnected from ActiveHomeVistaService");
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        Log.e("ActiveHomeVista", "Unregistering call back failed");
      }
    }
  }
  
  private void initDeviceListView()
  {
    this.deviceListView = ((ListView)findViewById(2131558498));
    registerForContextMenu(this.deviceListView);
    this.deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (paramAnonymousInt == ActiveHomeVista.this.lastDeviceListPosition)
        {
          ActiveHomeVista.this.deviceListView.setItemChecked(paramAnonymousInt, false);
          ActiveHomeVista.access$2402(ActiveHomeVista.this, -1);
          Room localRoom = (Room)ActiveHomeVista.this.roomListView.getItemAtPosition(ActiveHomeVista.this.roomListView.getCheckedItemPosition());
          ActiveHomeVista.this.roomButtonsSetVisibility(localRoom);
          ActiveHomeVista.this.deviceButtonsSetVisibility(null);
          ActiveHomeVista.this.deviceButtonsSetEnabled(null);
          return;
        }
        ActiveHomeVista.this.deviceListView.setItemChecked(paramAnonymousInt, true);
        ActiveHomeVista.access$2402(ActiveHomeVista.this, paramAnonymousInt);
        Device localDevice = (Device)ActiveHomeVista.this.deviceListView.getItemAtPosition(paramAnonymousInt);
        if (ActiveHomeVista.this.drawerToggle != null) {
          ActiveHomeVista.this.roomButtonsSetVisibility(null);
        }
        ActiveHomeVista.this.deviceButtonsSetVisibility(localDevice);
        ActiveHomeVista.this.deviceButtonsSetEnabled(localDevice);
      }
    });
  }
  
  private void initDimsSeekBar()
  {
    SeekBar.OnSeekBarChangeListener local19 = new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean) {
          ActiveHomeVista.this.UpdateDimsAlert(paramAnonymousSeekBar.getProgress());
        }
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
        ActiveHomeVista.this.UpdateDimsAlert(paramAnonymousSeekBar.getProgress());
      }
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
        ActiveHomeVista.this.executeDimsSeekBarUpdate(paramAnonymousSeekBar);
      }
    };
    this.dimsSeekBar = ((SeekBar)findViewById(2131558502));
    this.dimsSeekBar.setOnSeekBarChangeListener(local19);
    this.dimsSeekBar.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ActiveHomeVista.this.executeDimsSeekBarUpdate((SeekBar)paramAnonymousView);
      }
    });
  }
  
  private void initDrawer()
  {
    this.drawerLayout = ((DrawerLayout)findViewById(2131558497));
    if (this.drawerLayout != null)
    {
      this.drawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, 2131099710, 2131099709)
      {
        public void onDrawerClosed(View paramAnonymousView)
        {
          ActiveHomeVista.this.supportInvalidateOptionsMenu();
        }
        
        public void onDrawerOpened(View paramAnonymousView)
        {
          ActiveHomeVista.this.supportInvalidateOptionsMenu();
        }
      };
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null)
      {
        localActionBar.setDisplayHomeAsUpEnabled(true);
        localActionBar.setHomeButtonEnabled(true);
      }
      this.drawerLayout.setDrawerListener(this.drawerToggle);
    }
  }
  
  private void initMediaPlayerButtons()
  {
    this.stopButton = ((ImageButton)findViewById(2131558512));
    this.stopButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeDeviceCommand(localDevice, (byte)3);
      }
    });
    this.previousButton = ((ImageButton)findViewById(2131558513));
    this.previousButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeDeviceCommand(localDevice, (byte)4);
      }
    });
    this.playButton = ((ImageButton)findViewById(2131558514));
    this.playButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeDeviceCommand(localDevice, (byte)2);
      }
    });
    this.nextButton = ((ImageButton)findViewById(2131558515));
    this.nextButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeDeviceCommand(localDevice, (byte)5);
      }
    });
  }
  
  private void initOffButton()
  {
    this.offButton = ((Button)findViewById(2131558505));
    this.offButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        try
        {
          if ((ActiveHomeVista.this.myService != null) && (localDevice != null)) {
            ActiveHomeVista.this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)3, (byte)0, (byte)0, (byte)0);
          }
          return;
        }
        catch (RemoteException localRemoteException) {}
      }
    });
  }
  
  private void initOnButton()
  {
    this.onButton = ((Button)findViewById(2131558504));
    this.onButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        try
        {
          if ((ActiveHomeVista.this.myService != null) && (localDevice != null)) {
            ActiveHomeVista.this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
          }
          return;
        }
        catch (RemoteException localRemoteException) {}
      }
    });
  }
  
  private void initOnOffToggleButton()
  {
    this.onOffToggleButton = ((ToggleButton)findViewById(2131558503));
    this.onOffToggleButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        if (((ToggleButton)paramAnonymousView).isChecked())
        {
          ((ToggleButton)paramAnonymousView).setChecked(false);
          try
          {
            if ((ActiveHomeVista.this.myService == null) || (localDevice == null)) {
              return;
            }
            if ((!localDevice.getDeviceDimMemoryCap()) && (localDevice.getDeviceDimsCap()) && (ActiveHomeVista.this.emulationDimMemoryCap))
            {
              int i = ActiveHomeVista.this.getDeviceDimStatus(localDevice);
              ActiveHomeVista.this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
              if (i >= 100) {
                return;
              }
              ActiveHomeVista.this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)4, (byte)0, (byte)(100 - i), (byte)0);
              return;
            }
            ActiveHomeVista.this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)2, (byte)0, (byte)0, (byte)0);
            return;
          }
          catch (RemoteException localRemoteException2)
          {
            return;
          }
        }
        ((ToggleButton)paramAnonymousView).setChecked(true);
        try
        {
          if ((ActiveHomeVista.this.myService != null) && (localDevice != null))
          {
            ActiveHomeVista.this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)3, (byte)0, (byte)0, (byte)0);
            return;
          }
        }
        catch (RemoteException localRemoteException1) {}
      }
    });
  }
  
  private void initPowerStateButton()
  {
    this.powerStateButton = ((Button)findViewById(2131558511));
    this.powerStateButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        switch (localDevice.getDeviceType())
        {
        default: 
          return;
        case 45: 
        case 47: 
          ActiveHomeVista.this.executeDeviceCommand(localDevice, (byte)2);
          return;
        case 44: 
          ActiveHomeVista.this.shutDownDialog(localDevice);
          return;
        }
        ActiveHomeVista.this.restartDialog(localDevice);
      }
    });
  }
  
  private void initPreferences()
  {
    this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.myPreferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener()
    {
      public void onSharedPreferenceChanged(SharedPreferences paramAnonymousSharedPreferences, String paramAnonymousString)
      {
        if (paramAnonymousString.equals(ActiveHomeVista.this.getString(2131099720))) {
          ActiveHomeVista.this.setActiveHouseCodeTextView("(" + ActiveHomeVista.this.preferences.getString(ActiveHomeVista.this.getString(2131099720), ActiveHomeVista.this.getString(2131099703)) + ")");
        }
        if ((paramAnonymousString.equals(ActiveHomeVista.this.getString(2131099729))) && (Build.VERSION.SDK_INT >= 11)) {
          ActiveHomeVista.VersionHelper.recreate(ActiveHomeVista.this);
        }
        if (Build.VERSION.SDK_INT >= 8) {
          ActiveHomeVista.VersionHelper.dataChanged(ActiveHomeVista.this.getPackageName());
        }
      }
    };
    this.preferences.registerOnSharedPreferenceChangeListener(this.myPreferenceListener);
  }
  
  private void initRoomButtons()
  {
    this.allLightsOnButton = ((Button)findViewById(2131558507));
    this.allLightsOnButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Room localRoom = (Room)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeRoomCommand(localRoom, (byte)1);
      }
    });
    this.allLightsOffButton = ((Button)findViewById(2131558508));
    this.allLightsOffButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Room localRoom = (Room)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeRoomCommand(localRoom, (byte)6);
      }
    });
    this.allUnitsOnButton = ((Button)findViewById(2131558509));
    this.allUnitsOnButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Room localRoom = (Room)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeRoomCommand(localRoom, (byte)16);
      }
    });
    this.allUnitsOffButton = ((Button)findViewById(2131558510));
    this.allUnitsOffButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Room localRoom = (Room)paramAnonymousView.getTag();
        ActiveHomeVista.this.executeRoomCommand(localRoom, (byte)0);
      }
    });
  }
  
  private void initRoomListView()
  {
    this.roomListView = ((ListView)findViewById(2131558516));
    registerForContextMenu(this.roomListView);
    this.roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        ActiveHomeVista.access$2302(ActiveHomeVista.this, paramAnonymousInt);
        ActiveHomeVista.access$2402(ActiveHomeVista.this, -1);
        ActiveHomeVista.this.roomListView.setItemChecked(paramAnonymousInt, true);
        ActionBar localActionBar = ActiveHomeVista.this.getSupportActionBar();
        Room localRoom = (Room)ActiveHomeVista.this.roomListView.getItemAtPosition(paramAnonymousInt);
        if (localRoom != null)
        {
          if (localActionBar != null) {
            localActionBar.setTitle(localRoom.name);
          }
          ActiveHomeVista.this.roomButtonsSetVisibility(localRoom);
          ActiveHomeVista.this.roomButtonsSetEnabled(localRoom);
          ActiveHomeVista.this.deviceListView.setAdapter(new DeviceListAdapter(ActiveHomeVista.this, ((Room)ActiveHomeVista.house.get(paramAnonymousInt)).devices));
          ActiveHomeVista.this.deviceButtonsSetVisibility(null);
          ActiveHomeVista.this.deviceButtonsSetEnabled(null);
          if (ActiveHomeVista.this.drawerLayout != null) {
            ActiveHomeVista.this.drawerLayout.closeDrawers();
          }
        }
        while (localActionBar == null) {
          return;
        }
        localActionBar.setTitle(2131099681);
      }
    });
  }
  
  private void initStatusRequestButton()
  {
    this.statusButton = ((Button)findViewById(2131558506));
    this.statusButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Device localDevice = (Device)paramAnonymousView.getTag();
        try
        {
          if ((ActiveHomeVista.this.myService != null) && (localDevice != null)) {
            ActiveHomeVista.this.myService.deviceFunction(localDevice.getHouseCode(), localDevice.getDeviceCode(), (byte)15, (byte)0, (byte)0, (byte)0);
          }
          return;
        }
        catch (RemoteException localRemoteException) {}
      }
    });
  }
  
  private void initTextViews()
  {
    this.activeHouseCodeTextView = ((TextView)findViewById(2131558501));
    this.activeHouseCodeTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ActiveHomeVista.this.startPreferences();
      }
    });
    this.statusTextView = ((TextView)findViewById(2131558500));
    this.statusTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ActiveHomeVista.this.startPreferences();
      }
    });
  }
  
  private void outOfMemoryDialog()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(2131099747);
    localBuilder.setIcon(2130837581);
    localBuilder.setMessage(2131099746);
    localBuilder.setPositiveButton(17039370, null);
    this.alertDialog = localBuilder.create();
    this.alertDialog.show();
  }
  
  private static boolean parseHexString(String paramString, byte[] paramArrayOfByte)
  {
    if (paramString.length() == 12)
    {
      int m = 0;
      int n = 0;
      for (;;)
      {
        int i1;
        if (m < 12) {
          i1 = m + 2;
        }
        try
        {
          paramArrayOfByte[n] = ((byte)Integer.parseInt(paramString.substring(m, i1), 16));
          m += 2;
          n++;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
      }
      return true;
    }
    if (paramString.length() == 17)
    {
      int i = 0;
      int j = 0;
      for (;;)
      {
        int k;
        if (i < 17) {
          k = i + 2;
        }
        try
        {
          paramArrayOfByte[j] = ((byte)Integer.parseInt(paramString.substring(i, k), 16));
          i += 3;
          j++;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  private void restartDialog(final Device paramDevice)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(2131099753);
    localBuilder.setIcon(2130837581);
    localBuilder.setMessage(2131099752);
    localBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ActiveHomeVista.this.executeDeviceCommand(paramDevice, (byte)2);
      }
    });
    localBuilder.setNegativeButton(17039369, null);
    this.alertDialog = localBuilder.create();
    this.alertDialog.show();
  }
  
  private void roomButtonsSetEnabled(Room paramRoom)
  {
    boolean bool1 = true;
    if (paramRoom != null)
    {
      Button localButton1 = this.allLightsOnButton;
      boolean bool2;
      boolean bool3;
      label57:
      boolean bool4;
      label87:
      boolean bool5;
      label117:
      boolean bool6;
      label147:
      boolean bool7;
      label177:
      boolean bool8;
      label207:
      Button localButton8;
      if ((paramRoom.getDeviceAllLightsOnCap()) && (connectedPlus()))
      {
        bool2 = bool1;
        localButton1.setEnabled(bool2);
        Button localButton2 = this.allLightsOnButton;
        if ((!paramRoom.getDeviceAllLightsOnCap()) || (!connectedPlus())) {
          break label247;
        }
        bool3 = bool1;
        localButton2.setFocusable(bool3);
        Button localButton3 = this.allLightsOffButton;
        if ((!paramRoom.getDeviceAllLightsOffCap()) || (!connectedPlus())) {
          break label253;
        }
        bool4 = bool1;
        localButton3.setEnabled(bool4);
        Button localButton4 = this.allLightsOffButton;
        if ((!paramRoom.getDeviceAllLightsOffCap()) || (!connectedPlus())) {
          break label259;
        }
        bool5 = bool1;
        localButton4.setFocusable(bool5);
        Button localButton5 = this.allUnitsOnButton;
        if ((!paramRoom.getDeviceAllUnitsOnCap()) || (!connectedPlus())) {
          break label265;
        }
        bool6 = bool1;
        localButton5.setEnabled(bool6);
        Button localButton6 = this.allUnitsOnButton;
        if ((!paramRoom.getDeviceAllUnitsOnCap()) || (!connectedPlus())) {
          break label271;
        }
        bool7 = bool1;
        localButton6.setFocusable(bool7);
        Button localButton7 = this.allUnitsOffButton;
        if ((!paramRoom.getDeviceAllUnitsOffCap()) || (!connectedPlus())) {
          break label277;
        }
        bool8 = bool1;
        localButton7.setEnabled(bool8);
        localButton8 = this.allUnitsOffButton;
        if ((!paramRoom.getDeviceAllUnitsOffCap()) || (!connectedPlus())) {
          break label283;
        }
      }
      for (;;)
      {
        localButton8.setFocusable(bool1);
        return;
        bool2 = false;
        break;
        label247:
        bool3 = false;
        break label57;
        label253:
        bool4 = false;
        break label87;
        label259:
        bool5 = false;
        break label117;
        label265:
        bool6 = false;
        break label147;
        label271:
        bool7 = false;
        break label177;
        label277:
        bool8 = false;
        break label207;
        label283:
        bool1 = false;
      }
    }
    this.allLightsOnButton.setEnabled(false);
    this.allLightsOnButton.setFocusable(false);
    this.allLightsOffButton.setEnabled(false);
    this.allLightsOffButton.setFocusable(false);
    this.allUnitsOnButton.setEnabled(false);
    this.allUnitsOnButton.setFocusable(false);
    this.allUnitsOffButton.setEnabled(false);
    this.allUnitsOffButton.setFocusable(false);
  }
  
  private void roomButtonsSetVisibility(Room paramRoom)
  {
    if (paramRoom != null)
    {
      this.allLightsOnButton.setTag(paramRoom);
      this.allLightsOffButton.setTag(paramRoom);
      this.allUnitsOnButton.setTag(paramRoom);
      this.allUnitsOffButton.setTag(paramRoom);
      this.allLightsOnButton.setVisibility(0);
      this.allLightsOffButton.setVisibility(0);
      this.allUnitsOnButton.setVisibility(0);
      this.allUnitsOffButton.setVisibility(0);
      if (this.drawerToggle != null) {
        this.dimsSeekBar.setVisibility(0);
      }
    }
    do
    {
      return;
      this.allLightsOnButton.setVisibility(8);
      this.allLightsOffButton.setVisibility(8);
      this.allUnitsOnButton.setVisibility(8);
      this.allUnitsOffButton.setVisibility(8);
    } while (this.drawerToggle == null);
    this.dimsSeekBar.setVisibility(8);
  }
  
  private void setActiveHouseCodeTextView(String paramString)
  {
    this.activeHouseCodeTextView.setText(paramString);
  }
  
  private void shutDownDialog(final Device paramDevice)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(2131099758);
    localBuilder.setIcon(2130837581);
    localBuilder.setMessage(2131099757);
    localBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ActiveHomeVista.this.executeDeviceCommand(paramDevice, (byte)2);
      }
    });
    localBuilder.setNegativeButton(17039369, null);
    this.alertDialog = localBuilder.create();
    this.alertDialog.show();
  }
  
  private void startPreferences()
  {
    startActivity(new Intent(this, Preferences.class));
  }
  
  private void startService()
  {
    if (!this.isServiceStarted)
    {
      Intent localIntent = new Intent(this, ActiveHomeVistaService.class);
      localIntent.setAction("com.activehomevista.service.X10_SERVICE");
      startService(localIntent);
      bindService(localIntent, this.conn, 0);
      this.isServiceStarted = true;
      Log.i("ActiveHomeVista", "ActiveHomeVistaService is started");
    }
  }
  
  public static void updateTheme(SharedPreferences paramSharedPreferences, Activity paramActivity)
  {
    if (!paramSharedPreferences.getString(paramActivity.getString(2131099729), paramActivity.getString(2131099705)).equals(paramActivity.getString(2131099705))) {
      paramActivity.setTheme(2131362018);
    }
  }
  
  public byte getDeviceDimStatus(Device paramDevice)
  {
    int i = 16 * (byte)(paramDevice.getHouseCode() - getString(2131099703).charAt(0)) + (-1 + paramDevice.getDeviceCode());
    if ((i >= 0) && (i <= -1 + this.DimStatusDevices.length)) {
      return this.DimStatusDevices[i];
    }
    return 0;
  }
  
  public boolean getDeviceOnStatus(Device paramDevice)
  {
    int i = 16 * (byte)(paramDevice.getHouseCode() - getString(2131099703).charAt(0)) + (-1 + paramDevice.getDeviceCode());
    boolean bool = false;
    if (i >= 0)
    {
      int j = -1 + this.OnStatusDevices.length;
      bool = false;
      if (i <= j)
      {
        int k = this.OnStatusDevices[i];
        bool = false;
        if (k != 0) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  public boolean getDeviceOnStatusExtended(Device paramDevice)
  {
    int i = 16 * (byte)(paramDevice.getHouseCode() - getString(2131099703).charAt(0)) + (-1 + paramDevice.getDeviceCode());
    boolean bool = false;
    if (i >= 0)
    {
      int j = -1 + this.OnStatusDevicesExtended.length;
      bool = false;
      if (i <= j)
      {
        int k = this.OnStatusDevicesExtended[i];
        bool = false;
        if (k != 0) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (this.drawerToggle != null) {
      this.drawerToggle.onConfigurationChanged(paramConfiguration);
    }
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramMenuItem.getMenuInfo();
    Room localRoom;
    if (localAdapterContextMenuInfo.targetView.getParent() == this.roomListView)
    {
      localRoom = (Room)this.roomListView.getItemAtPosition(localAdapterContextMenuInfo.position);
      if (localRoom != null) {
        switch (paramMenuItem.getItemId())
        {
        }
      }
    }
    Device localDevice;
    int i;
    do
    {
      do
      {
        do
        {
          return false;
          executeRoomCommand(localRoom, (byte)1);
          return false;
          executeRoomCommand(localRoom, (byte)6);
          return false;
          executeRoomCommand(localRoom, (byte)16);
          return false;
          executeRoomCommand(localRoom, (byte)0);
          return false;
        } while (localAdapterContextMenuInfo.targetView.getParent() != this.deviceListView);
        localDevice = (Device)this.deviceListView.getItemAtPosition(localAdapterContextMenuInfo.position);
      } while (localDevice == null);
      switch (paramMenuItem.getItemId())
      {
      default: 
        return false;
      case 2: 
        switch (localDevice.getDeviceType())
        {
        case 45: 
        default: 
          if ((localDevice.getDeviceDimMemoryCap()) || (!localDevice.getDeviceDimsCap()) || (!this.emulationDimMemoryCap)) {
            break label321;
          }
          i = getDeviceDimStatus(localDevice);
          executeDeviceCommand(localDevice, (byte)2);
        }
        break;
      }
    } while (i >= 100);
    executeDeviceCommand(localDevice, (byte)4, (byte)(100 - i));
    return false;
    restartDialog(localDevice);
    return false;
    shutDownDialog(localDevice);
    return false;
    label321:
    executeDeviceCommand(localDevice, (byte)2);
    return false;
    executeDeviceCommand(localDevice, (byte)3);
    return false;
    executeDeviceCommand(localDevice, (byte)4);
    return false;
    executeDeviceCommand(localDevice, (byte)5);
    return false;
    executeDeviceCommand(localDevice, (byte)15);
    return false;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    initPreferences();
    updateTheme(this.preferences, this);
    super.onCreate(paramBundle);
    mHandler = new myHandler();
    mHandler.activityRef = new WeakReference(this);
    if (paramBundle != null)
    {
      if (!paramBundle.containsKey("lastCheckedItemGroupPosition")) {
        break label266;
      }
      this.lastRoomListPosition = paramBundle.getInt("lastCheckedItemGroupPosition");
      if (!paramBundle.containsKey("lastCheckedItemChildPosition")) {
        break label274;
      }
      this.lastDeviceListPosition = paramBundle.getInt("lastCheckedItemChildPosition");
      label87:
      if (!paramBundle.containsKey("fileUniqueId")) {
        break label282;
      }
    }
    label266:
    label274:
    label282:
    for (this.fileUniqueIdPersistent = paramBundle.getInt("fileUniqueId");; this.fileUniqueIdPersistent = -1)
    {
      setContentView(2130903069);
      initRoomListView();
      initDeviceListView();
      initDrawer();
      initDimsSeekBar();
      initOnOffToggleButton();
      initMediaPlayerButtons();
      initPowerStateButton();
      initRoomButtons();
      initOnButton();
      initOffButton();
      initStatusRequestButton();
      initTextViews();
      this.deviceActionsLinearLayout = ((LinearLayout)findViewById(2131558499));
      roomButtonsSetVisibility(null);
      roomButtonsSetEnabled(null);
      deviceButtonsSetVisibility(null);
      deviceButtonsSetEnabled(null);
      this.conn = new ServiceConnection()
      {
        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
        {
          Log.i("ActiveHomeVista", "Connection to ActiveHomeVistaService established");
          try
          {
            ActiveHomeVista.access$102(ActiveHomeVista.this, (IActiveHomeVistaService)paramAnonymousIBinder);
            if (ActiveHomeVista.this.myService == null) {}
          }
          catch (Exception localException)
          {
            try
            {
              ActiveHomeVista.this.myService.registerCallback(ActiveHomeVista.this.mCallback);
              try
              {
                ActiveHomeVista.access$302(ActiveHomeVista.this, ActiveHomeVista.this.myService.getLocalConnected());
                if (!ActiveHomeVista.this.connected) {
                  ActiveHomeVista.this.myService.notifyForeground();
                }
                ActiveHomeVista.this.UpdateInterfaceType(Integer.valueOf(ActiveHomeVista.this.myService.getLocalInterfaceType()));
                ActiveHomeVista.this.UpdateCapacities();
                ActiveHomeVista.this.UpdateConnectionStatus(Integer.valueOf(ActiveHomeVista.this.myService.getLocalConnectionStatus()));
                ActiveHomeVista.this.myService.getLocalStatus(ActiveHomeVista.this.OnStatusDevices, ActiveHomeVista.this.DimStatusDevices, ActiveHomeVista.this.OnStatusDevicesExtended);
                if ((ActiveHomeVista.this.getFile()) && (!ActiveHomeVista.this.isFinishing()) && (ActiveHomeVista.this.isVisible)) {
                  ActiveHomeVista.this.outOfMemoryDialog();
                }
                return;
              }
              catch (RemoteException localRemoteException2)
              {
                Log.e("ActiveHomeVista", "Getting information from ActiveHomeVistaService failed");
              }
              localException = localException;
              Log.e("ActiveHomeVista", "Cast of service to IActiveHomeVistaService failed");
              ActiveHomeVista.access$102(ActiveHomeVista.this, null);
            }
            catch (RemoteException localRemoteException1)
            {
              for (;;)
              {
                Log.e("ActiveHomeVista", "Registering call back failed");
              }
            }
          }
        }
        
        public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
        {
          Log.i("ActiveHomeVista", "Connection to ActiveHomeVistaService lost");
          ActiveHomeVista.access$1302(ActiveHomeVista.this, false);
          ActiveHomeVista.this.handleServiceDisconnect();
        }
      };
      checkNoServerAddressSupplied();
      setActiveHouseCodeTextView("(" + this.preferences.getString(getString(2131099720), getString(2131099703)) + ")");
      return;
      this.lastRoomListPosition = -1;
      break;
      this.lastDeviceListPosition = -1;
      break label87;
    }
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    int i = 1;
    if (paramView == this.roomListView)
    {
      Room localRoom = (Room)this.roomListView.getItemAtPosition(((AdapterView.AdapterContextMenuInfo)paramContextMenuInfo).position);
      if (localRoom != null)
      {
        paramContextMenu.setHeaderTitle(localRoom.getName());
        MenuItem localMenuItem3 = paramContextMenu.add(0, i, 0, 2131099678);
        if ((!localRoom.getDeviceAllLightsOnCap()) || (!connectedPlus())) {
          break label219;
        }
        int j = i;
        localMenuItem3.setEnabled(j);
        MenuItem localMenuItem4 = paramContextMenu.add(0, 6, 0, 2131099677);
        if ((!localRoom.getDeviceAllLightsOffCap()) || (!connectedPlus())) {
          break label225;
        }
        int m = i;
        label125:
        localMenuItem4.setEnabled(m);
        MenuItem localMenuItem5 = paramContextMenu.add(0, 16, 0, 2131099680);
        if ((!localRoom.getDeviceAllUnitsOnCap()) || (!connectedPlus())) {
          break label231;
        }
        int i1 = i;
        label169:
        localMenuItem5.setEnabled(i1);
        MenuItem localMenuItem6 = paramContextMenu.add(0, 0, 0, 2131099679);
        if ((!localRoom.getDeviceAllUnitsOffCap()) || (!connectedPlus())) {
          break label237;
        }
        label208:
        localMenuItem6.setEnabled(i);
      }
    }
    label219:
    label225:
    label231:
    label237:
    boolean bool1;
    Device localDevice;
    do
    {
      do
      {
        return;
        int k = 0;
        break;
        int n = 0;
        break label125;
        int i2 = 0;
        break label169;
        bool1 = false;
        break label208;
      } while (paramView != this.deviceListView);
      localDevice = (Device)this.deviceListView.getItemAtPosition(((AdapterView.AdapterContextMenuInfo)paramContextMenuInfo).position);
    } while (localDevice == null);
    paramContextMenu.setHeaderTitle(localDevice.getName());
    if (localDevice.getDeviceType() == 17)
    {
      paramContextMenu.add(0, 3, 0, 2131099764).setEnabled(this.connected);
      paramContextMenu.add(0, 4, 0, 2131099751).setEnabled(this.connected);
      paramContextMenu.add(0, 2, 0, 2131099749).setEnabled(this.connected);
      paramContextMenu.add(0, 5, 0, 2131099737).setEnabled(this.connected);
      return;
    }
    if (localDevice.getDeviceType() == 13)
    {
      paramContextMenu.add(0, 2, 0, 2131099745).setEnabled(connectedPlus());
      paramContextMenu.add(0, 3, 0, 2131099743).setEnabled(connectedPlus());
      return;
    }
    if (localDevice.getDeviceType() == 44)
    {
      paramContextMenu.add(0, 2, 0, 2131099712).setEnabled(this.connected);
      return;
    }
    if (localDevice.getDeviceType() == 46)
    {
      paramContextMenu.add(0, 2, 0, 2131099712).setEnabled(this.connected);
      return;
    }
    if (localDevice.getDeviceType() == 45)
    {
      paramContextMenu.add(0, 2, 0, 2131099712).setEnabled(this.connected);
      return;
    }
    if (localDevice.getDeviceType() == 47)
    {
      paramContextMenu.add(0, 2, 0, 2131099712).setEnabled(this.connected);
      return;
    }
    if (localDevice.getDeviceOnlyOnCmdCap())
    {
      if ((!localDevice.getDeviceRFDeviceCap()) || (this.RFCap))
      {
        paramContextMenu.add(0, 2, 0, 2131099745).setEnabled(connectedPlus());
        return;
      }
      paramContextMenu.add(0, 2, 0, 2131099745).setEnabled(false);
      return;
    }
    if ((!localDevice.getDeviceRFDeviceCap()) || (this.RFCap))
    {
      MenuItem localMenuItem1 = paramContextMenu.add(0, 2, 0, 2131099745);
      boolean bool2;
      label697:
      MenuItem localMenuItem2;
      if ((!getDeviceOnStatus(localDevice)) && (!getDeviceOnStatusExtended(localDevice)) && (connectedPlus()))
      {
        bool2 = bool1;
        localMenuItem1.setEnabled(bool2);
        localMenuItem2 = paramContextMenu.add(0, 3, 0, 2131099743);
        if (((!getDeviceOnStatus(localDevice)) && (!getDeviceOnStatusExtended(localDevice))) || (!connectedPlus())) {
          break label794;
        }
      }
      for (;;)
      {
        localMenuItem2.setEnabled(bool1);
        if (!localDevice.getDeviceStatusCap()) {
          break;
        }
        paramContextMenu.add(0, 15, 0, 2131099763).setEnabled(connectedPlus());
        return;
        bool2 = false;
        break label697;
        label794:
        bool1 = false;
      }
    }
    paramContextMenu.add(0, 2, 0, 2131099745).setEnabled(false);
    paramContextMenu.add(0, 3, 0, 2131099743).setEnabled(false);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623936, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    this.isVisible = false;
    super.onDestroy();
    this.preferences.unregisterOnSharedPreferenceChangeListener(this.myPreferenceListener);
    unregisterForContextMenu(this.roomListView);
    unregisterForContextMenu(this.deviceListView);
    unbindService(this.conn);
    handleServiceDisconnect();
    Log.i("ActiveHomeVista", "ActiveHomeVista destroyed");
  }
  
  @SuppressLint({"SetTextI18n"})
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if ((this.drawerToggle != null) && (this.drawerToggle.onOptionsItemSelected(paramMenuItem))) {
      return true;
    }
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return super.onOptionsItemSelected(paramMenuItem);
      startPreferences();
      continue;
      View localView = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903065, null);
      ((TextView)localView.findViewById(2131558481)).setText(getString(2131099681) + " " + getString(2131099682) + " " + getSoftwareVersion());
      ((TextView)localView.findViewById(2131558482)).setMovementMethod(LinkMovementMethod.getInstance());
      ((TextView)localView.findViewById(2131558484)).setMovementMethod(LinkMovementMethod.getInstance());
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle(2131099673);
      localBuilder.setIcon(2130837581);
      localBuilder.setView(localView);
      localBuilder.setPositiveButton(17039370, null);
      this.alertDialog = localBuilder.create();
      this.alertDialog.show();
      continue;
      try
      {
        if (this.myService == null) {
          continue;
        }
        this.myService.globalFunction((byte)6);
      }
      catch (RemoteException localRemoteException3) {}
      continue;
      try
      {
        if (this.myService == null) {
          continue;
        }
        this.myService.globalFunction((byte)1);
      }
      catch (RemoteException localRemoteException2) {}
      continue;
      try
      {
        if (this.myService == null) {
          continue;
        }
        this.myService.globalFunction((byte)0);
      }
      catch (RemoteException localRemoteException1) {}
      continue;
      Toast.makeText(this, 2131099785, 0).show();
      new wakeUpServerTask(null).execute(new Context[] { this });
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    cancelProgressDialog();
    cancelAlertDialog();
  }
  
  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    if (this.drawerToggle != null) {
      this.drawerToggle.syncState();
    }
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    paramMenu.findItem(2131558530).setEnabled(connectedPlus());
    MenuItem localMenuItem1 = paramMenu.findItem(2131558531);
    if ((connectedPlus()) && (this.myService != null) && (this.globalFunctionAllLightsOffCap)) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      localMenuItem1.setEnabled(bool1);
      paramMenu.findItem(2131558532).setEnabled(connectedPlus());
      MenuItem localMenuItem2 = paramMenu.findItem(2131558533);
      boolean bool2 = this.connected;
      boolean bool3 = false;
      if (!bool2)
      {
        IActiveHomeVistaService localIActiveHomeVistaService = this.myService;
        bool3 = false;
        if (localIActiveHomeVistaService != null) {
          bool3 = true;
        }
      }
      localMenuItem2.setEnabled(bool3);
      return true;
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.myService != null) {}
    try
    {
      this.myService.notifyForeground();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e("ActiveHomeVista", "NotifyForeground failed");
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putInt("lastCheckedItemGroupPosition", this.lastRoomListPosition);
    paramBundle.putInt("lastCheckedItemChildPosition", this.lastDeviceListPosition);
    paramBundle.putInt("fileUniqueId", this.fileUniqueIdPersistent);
    super.onSaveInstanceState(paramBundle);
  }
  
  protected void onStart()
  {
    super.onStart();
    startService();
    this.isVisible = true;
  }
  
  protected void onStop()
  {
    this.isVisible = false;
    super.onStop();
  }
  
  static class VersionHelper
  {
    VersionHelper() {}
    
    @TargetApi(8)
    static void dataChanged(String paramString)
    {
      BackupManager.dataChanged(paramString);
    }
    
    @TargetApi(11)
    static void recreate(Activity paramActivity)
    {
      paramActivity.recreate();
    }
  }
  
  static class myHandler
    extends Handler
  {
    public WeakReference<ActiveHomeVista> activityRef;
    
    myHandler() {}
    
    public void handleMessage(Message paramMessage)
    {
      ActiveHomeVista localActiveHomeVista = (ActiveHomeVista)this.activityRef.get();
      if (localActiveHomeVista != null) {
        switch (paramMessage.what)
        {
        default: 
          super.handleMessage(paramMessage);
        }
      }
      for (;;)
      {
        return;
        if ((localActiveHomeVista.isFinishing()) || (!localActiveHomeVista.isVisible)) {
          continue;
        }
        ActiveHomeVista.access$3802(localActiveHomeVista, ProgressDialog.show(localActiveHomeVista, localActiveHomeVista.getString(2131099734), localActiveHomeVista.getString(2131099733)));
        return;
        if (localActiveHomeVista.getFile())
        {
          localActiveHomeVista.cancelProgressDialog();
          if ((localActiveHomeVista.isFinishing()) || (!localActiveHomeVista.isVisible)) {
            continue;
          }
          localActiveHomeVista.outOfMemoryDialog();
          return;
        }
        localActiveHomeVista.cancelProgressDialog();
        return;
        if (localActiveHomeVista.myService != null) {}
        try
        {
          localActiveHomeVista.myService.getLocalStatus(localActiveHomeVista.OnStatusDevices, localActiveHomeVista.DimStatusDevices, localActiveHomeVista.OnStatusDevicesExtended);
          localActiveHomeVista.deviceListView.invalidateViews();
          ToggleButton localToggleButton;
          if ((localActiveHomeVista.onOffToggleButton.getTag() != null) && (localActiveHomeVista.onOffToggleButton.isEnabled()))
          {
            Device localDevice2 = (Device)localActiveHomeVista.onOffToggleButton.getTag();
            localToggleButton = localActiveHomeVista.onOffToggleButton;
            if ((!localActiveHomeVista.getDeviceOnStatus(localDevice2)) && (!localActiveHomeVista.getDeviceOnStatusExtended(localDevice2))) {
              break label280;
            }
          }
          label280:
          for (boolean bool = true;; bool = false)
          {
            localToggleButton.setChecked(bool);
            if ((localActiveHomeVista.dimsSeekBar.getTag() == null) || (!localActiveHomeVista.dimsSeekBar.isEnabled())) {
              break;
            }
            Device localDevice1 = (Device)localActiveHomeVista.dimsSeekBar.getTag();
            localActiveHomeVista.dimsSeekBar.setProgress(localActiveHomeVista.getDeviceDimStatus(localDevice1));
            return;
          }
          ActiveHomeVista.access$302(localActiveHomeVista, ((Boolean)paramMessage.obj).booleanValue());
          return;
          localActiveHomeVista.UpdateInterfaceType((Integer)paramMessage.obj);
          localActiveHomeVista.UpdateCapacities();
          return;
          localActiveHomeVista.UpdateConnectionStatus((Integer)paramMessage.obj);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
  }
  
  private class wakeUpServerTask
    extends AsyncTask<Context, Void, Void>
  {
    private wakeUpServerTask() {}
    
    protected Void doInBackground(Context... paramVarArgs)
    {
      for (;;)
      {
        try
        {
          localDatagramSocket = new DatagramSocket();
          arrayOfByte1 = new byte[6];
          if (!ActiveHomeVista.parseHexString(ActiveHomeVista.this.preferences.getString(ActiveHomeVista.this.getString(2131099717), ""), arrayOfByte1)) {
            continue;
          }
          arrayOfByte2 = new byte[102];
          int i = 0;
          if (i >= 6) {
            continue;
          }
          arrayOfByte2[i] = -1;
          i++;
          continue;
        }
        catch (Exception localException)
        {
          DatagramSocket localDatagramSocket;
          byte[] arrayOfByte1;
          byte[] arrayOfByte2;
          int m;
          int k;
          InetAddress localInetAddress;
          Log.e("ActiveHomeVista", "Sending WOL packet failed: " + localException.toString());
          break label332;
          int j = 1;
          continue;
        }
        if (j > 16) {
          continue;
        }
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j * 6, 6);
        j++;
      }
      try
      {
        m = Integer.parseInt(ActiveHomeVista.this.preferences.getString(ActiveHomeVista.this.getString(2131099719), ActiveHomeVista.this.getString(2131099701)));
        k = m;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;)
        {
          k = Integer.parseInt(ActiveHomeVista.this.getString(2131099701));
          continue;
          localDatagramSocket.setBroadcast(false);
          localDatagramSocket.send(new DatagramPacket(arrayOfByte2, arrayOfByte2.length, ActiveHomeVistaService.getAddr(ActiveHomeVista.this.preferences, paramVarArgs[0]).getAddress(), k));
        }
      }
      if ((ActiveHomeVistaService.isLocalWifi(ActiveHomeVista.this.preferences, paramVarArgs[0])) && (ActiveHomeVista.this.preferences.getBoolean(ActiveHomeVista.this.getString(2131099726), true)))
      {
        localDatagramSocket.setBroadcast(true);
        localInetAddress = ActiveHomeVistaService.getWifiDhcpBroadcastAddress(ActiveHomeVista.this.preferences, paramVarArgs[0]);
        if (localInetAddress != null) {
          localDatagramSocket.send(new DatagramPacket(arrayOfByte2, arrayOfByte2.length, localInetAddress, k));
        }
        localDatagramSocket.close();
      }
      label332:
      return null;
    }
  }
}
