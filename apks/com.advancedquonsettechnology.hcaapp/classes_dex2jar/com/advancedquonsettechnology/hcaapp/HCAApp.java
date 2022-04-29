package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class HCAApp
  extends Activity
  implements HCAConnectListener, Spinner.SpinnerTimeoutCallback
{
  HCAApplication _app;
  Spinner _spinner;
  TextView cnxinfo;
  GridView displayGrid;
  boolean enaHomeDisp = false;
  String homeDisp = null;
  RelativeLayout layoutview;
  boolean origEnaHomeDisp = this.enaHomeDisp;
  String origHomeDisp = this.homeDisp;
  TextView titlebar;
  
  public HCAApp() {}
  
  private void setupOptionMenu(Menu paramMenu)
  {
    if (HCAApplication.client.getConnected())
    {
      paramMenu.findItem(2131427490).setTitle("Disconnect");
      paramMenu.findItem(2131427490).setIcon(17301560);
      paramMenu.findItem(2131427491).setEnabled(true);
      paramMenu.findItem(2131427491).setVisible(true);
      return;
    }
    if (HCAApplication.mIPAddress.equals("")) {
      paramMenu.findItem(2131427490).setTitle("Use Settings To Get Started");
    }
    for (;;)
    {
      paramMenu.findItem(2131427490).setIcon(17301575);
      paramMenu.findItem(2131427491).setEnabled(true);
      paramMenu.findItem(2131427491).setVisible(true);
      return;
      paramMenu.findItem(2131427490).setTitle("Connect to " + HCAApplication.mIPAddress);
    }
  }
  
  private void startResponseTimer(int paramInt)
  {
    int i = Integer.parseInt(HCAApplication.mIPTimeout);
    this._spinner.start("Connecting...", i, paramInt);
  }
  
  public void HCAServerStatus(HCAConnectListener.ConnectStatus paramConnectStatus)
  {
    switch (1.$SwitchMap$com$advancedquonsettechnology$hcaapp$HCAConnectListener$ConnectStatus[paramConnectStatus.ordinal()])
    {
    default: 
    case 1: 
    case 2: 
    case 3: 
      do
      {
        return;
        if (this._spinner != null) {
          this._spinner.stop();
        }
        constructHomeDisplay();
        showHomeDisplay();
        return;
        if (this._spinner != null) {
          this._spinner.stop();
        }
        Toast.makeText(getBaseContext(), "Error connecting to server. Please check settings.", 1).show();
        return;
      } while (this._spinner == null);
      this._spinner.stop();
      return;
    case 4: 
      if (this._spinner != null) {
        this._spinner.stop();
      }
      Toast.makeText(getBaseContext(), "Please specify server info.", 0).show();
      startActivityForResult(new Intent().setClass(this, SettingsPreferences.class), 3);
      return;
    }
    if (this._spinner != null) {
      this._spinner.stop();
    }
    Toast.makeText(getBaseContext(), "No Data Connection. Please try again.", 0).show();
  }
  
  public void SpinnerTimeout(int paramInt)
  {
    Toast.makeText(getBaseContext(), "Timeout waiting for Connection", 0).show();
  }
  
  public void constructHomeDisplay()
  {
    boolean bool1 = HCAApplication.prefs.getBoolean("enable_home_display", false);
    String str = HCAApplication.prefs.getString("home_display", "");
    HCADisplay localHCADisplay = HCAApplication.findHCADisplay(str);
    if (localHCADisplay == null)
    {
      if (bool1) {
        Toast.makeText(getBaseContext(), "Could not find home display " + str + ". Using default", 0).show();
      }
      str = "All Displays";
    }
    HCAApplication.AppState localAppState = HCAApplication.appState;
    boolean bool2 = false;
    if (localHCADisplay != null)
    {
      boolean bool3 = localHCADisplay.isHTMLDisplay();
      bool2 = false;
      if (bool3) {
        bool2 = true;
      }
    }
    localAppState.homeDisplayIsHTML = bool2;
    HCAApplication.appState.homeDisplayName = str;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 3)
    {
      String str = this.homeDisp;
      boolean bool = this.enaHomeDisp;
      HCAApplication.setPreferences();
      this.homeDisp = HCAApplication.prefs.getString("home_display", "");
      this.enaHomeDisp = HCAApplication.prefs.getBoolean("enable_home_display", false);
      if ((HCAApplication.client.getConnected() == true) && ((this.enaHomeDisp != bool) || ((this.enaHomeDisp) && (!this.homeDisp.equalsIgnoreCase(str)))))
      {
        Toast.makeText(getBaseContext(), "Reconnecting with updated preferences.", 0).show();
        this._app.reconnectToHCAServer();
      }
      this._app.notifyUpdateListeners(-1);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this._app = ((HCAApplication)getApplication());
    this._spinner = new Spinner(this, this);
    if (HCAApplication.devMode) {
      Log.d("HCA", "HCAApp.onCreate");
    }
    setContentView(2130903044);
    this.layoutview = ((RelativeLayout)findViewById(2131427345));
    this.layoutview.setBackgroundColor(-1);
    this.titlebar = ((TextView)findViewById(2131427348));
    this.cnxinfo = ((TextView)findViewById(2131427349));
    showHomeScreen(true);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131361792, paramMenu);
    paramMenu.findItem(2131427491).setIcon(17301577);
    paramMenu.findItem(2131427492).setIcon(17301568);
    setupOptionMenu(paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (HCAApplication.vibration > 0) {
      ((Vibrator)getSystemService("vibrator")).vibrate(HCAApplication.vibration);
    }
    switch (paramMenuItem.getItemId())
    {
    case 2131427493: 
    default: 
      return true;
    case 2131427490: 
      startResponseTimer(0);
      if (HCAApplication.client.getConnected())
      {
        this._app.disconnectFromHCAServer(true);
        finish();
        return true;
      }
      this._app.connectToHCAServer();
      return true;
    case 2131427491: 
      startActivityForResult(new Intent().setClass(this, SettingsPreferences.class), 3);
      return true;
    case 2131427492: 
      startActivity(new Intent().setClass(this, HelpMain.class));
      return true;
    }
    startActivityForResult(new Intent().setClass(this, ModesSet.class), 2);
    return true;
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    setupOptionMenu(paramMenu);
    return true;
  }
  
  public void onStart()
  {
    super.onStart();
    if (HCAApplication.connectListener != null) {
      HCAApplication.connectListener.add(this);
    }
  }
  
  public void onStop()
  {
    super.onStop();
    if (HCAApplication.connectListener != null) {
      HCAApplication.connectListener.remove(this);
    }
  }
  
  public void showHomeDisplay()
  {
    if (HCAApplication.appState.homeDisplayIsHTML) {}
    for (Object localObject = HTMLDisplay.class;; localObject = ObjectListDisplay.class)
    {
      Intent localIntent = new Intent(getBaseContext(), (Class)localObject);
      localIntent.setFlags(268468224);
      localIntent.putExtra("com.advancedquonsettechnology.hcaapp.foldername", HCAApplication.appState.homeDisplayName);
      localIntent.putExtra("com.advancedquonsettechnology.hcaapp.BaseActivity", true);
      startActivity(localIntent);
      finish();
      return;
    }
  }
  
  void showHomeScreen(boolean paramBoolean)
  {
    ImageView localImageView = (ImageView)findViewById(2131427346);
    GridView localGridView = (GridView)findViewById(2131427347);
    if (paramBoolean)
    {
      localImageView.setVisibility(0);
      localGridView.setVisibility(4);
      this.titlebar.setText("HCA Client " + Integer.parseInt(HCAApplication.versionmajor) + "." + Integer.parseInt(HCAApplication.versionminor) + "." + Integer.parseInt(HCAApplication.versionbuild));
      this.layoutview.setBackgroundColor(-1);
      this.titlebar.setTextColor(-16777216);
      return;
    }
    localImageView.setVisibility(4);
    localGridView.setVisibility(0);
    this.layoutview.setBackgroundColor(HCAApplication.bg_color);
    this.titlebar.setTextColor(HCAApplication.fg_color);
  }
}
