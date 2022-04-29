package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class ObjectListDisplay
  extends Activity
  implements HCAConnectListener, HCAUpdateListener, Spinner.SpinnerTimeoutCallback
{
  HCAApplication _app;
  boolean _baseActivity = false;
  String _displayName;
  Spinner _spinner;
  HCABaseAdapter objAdapter;
  List<HCABase> objectList;
  GridView objectsGrid;
  RelativeLayout objectsView;
  
  public ObjectListDisplay() {}
  
  private void showHomeDisplay()
  {
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onCreate showHomeDisplay (" + this._baseActivity + ")");
    }
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
  
  private void showObjects()
  {
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.showObjects (" + this._baseActivity + ")");
    }
    this.objectList = HCAApplication.getDisplayObjectList(this._displayName);
    if (this.objectList == null)
    {
      Toast.makeText(getBaseContext(), "Unable to load display " + this._displayName, 1).show();
      return;
    }
    HCADisplay localHCADisplay = HCAApplication.findHCADisplay(this._displayName);
    if (localHCADisplay != null) {}
    for (boolean bool = localHCADisplay.getShowTwoPartIcons();; bool = false)
    {
      this.objectsGrid = ((GridView)findViewById(2131427344));
      if (this.objectsGrid == null) {
        break;
      }
      this.objAdapter = new HCABaseAdapter(this, 2130903042, this.objectList, this._displayName, bool, this._spinner);
      this.objectsGrid.setAdapter(this.objAdapter);
      return;
    }
  }
  
  public void HCAServerStatus(HCAConnectListener.ConnectStatus paramConnectStatus)
  {
    switch (1.$SwitchMap$com$advancedquonsettechnology$hcaapp$HCAConnectListener$ConnectStatus[paramConnectStatus.ordinal()])
    {
    default: 
    case 1: 
    case 2: 
    case 3: 
    case 4: 
      do
      {
        return;
        if (HCAApplication.devMode) {
          Log.d("HCA", "ObjectListDisplay.HCAServerStatus(CONNECT_OK)");
        }
        showObjects();
        return;
      } while (!this._baseActivity);
      if (paramConnectStatus != HCAConnectListener.ConnectStatus.DISCONNECTED)
      {
        Toast.makeText(getBaseContext(), "Connection to HCA Server was lost. Please reconnect.", 0).show();
        if (HCAApplication.devMode) {
          Log.d("HCA", "ObjectListDisplay.HCAServerStatus(CONNECT_FAILED) startActivity HCAApp... ");
        }
      }
      startActivity(new Intent(getBaseContext(), HCAApp.class).addFlags(67108864));
      finish();
      return;
    }
    Toast.makeText(getBaseContext(), "No Data Connection. Please try again.", 0).show();
  }
  
  public void SpinnerTimeout(int paramInt)
  {
    HCABase localHCABase = HCAApplication.findHCAObject(paramInt);
    if (localHCABase != null) {
      Toast.makeText(getBaseContext(), "Timeout waiting for " + localHCABase.getName(), 0).show();
    }
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 4) || (paramInt1 == 5) || (paramInt1 == 2))
    {
      if (HCAApplication.mErrorText.equals("")) {
        break label38;
      }
      finish();
    }
    label38:
    while ((paramIntent == null) || (paramIntent.getStringExtra("com.advancedquonsettechnology.hcaapp.mainDisconnect").equals("true") != true)) {
      return;
    }
    if (this._baseActivity) {
      this._app.disconnectFromHCAServer(true);
    }
    for (;;)
    {
      finish();
      return;
      Intent localIntent = getIntent();
      localIntent.putExtra("com.advancedquonsettechnology.hcaapp.mainDisconnect", "true");
      setResult(-1, localIntent);
    }
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onBackPressed(" + this._baseActivity + ")");
    }
    if (this._baseActivity) {
      this._app.disconnectFromHCAServer(true);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this._app = ((HCAApplication)getApplication());
    this._baseActivity = getIntent().getBooleanExtra("com.advancedquonsettechnology.hcaapp.BaseActivity", false);
    this._displayName = getIntent().getStringExtra("com.advancedquonsettechnology.hcaapp.foldername");
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onCreate (" + this._baseActivity + ") = " + toString());
    }
    HCAApplication.mErrorText = "";
    if ((this._baseActivity) && (paramBundle != null))
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "ObjectListDisplay.onCreate with savedInstanceState (" + this._baseActivity + ")");
      }
      HCAApplication.appState.homeDisplayIsHTML = paramBundle.getBoolean("homeDisplayIsHTML");
      HCAApplication.appState.homeDisplayName = paramBundle.getString("homeDisplayName");
      HCAApplication.appState.areConnected = paramBundle.getBoolean("areConnected");
      HCAApplication.appState.designTS = paramBundle.getString("designTS");
      HCAApplication.appState.updateTS = paramBundle.getString("updateTS");
    }
    setContentView(2130903043);
    this.objectsView = ((RelativeLayout)findViewById(2131427342));
    this.objectsView.setBackgroundColor(HCAApplication.bg_color);
    TextView localTextView = (TextView)this.objectsView.findViewById(2131427343);
    localTextView.setTextColor(HCAApplication.fg_color);
    localTextView.setText(this._displayName);
    this._spinner = new Spinner(this, this);
    if (!HCAApplication.appState.designLoaded)
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "ObjectListDisplay.onCreate and designLoaded == false (" + this._baseActivity + ")");
      }
      if (this._baseActivity)
      {
        if (!HCAApplication.client.getConnected())
        {
          if (HCAApplication.devMode) {
            Log.d("HCA", "ObjectListDisplay.onCreate connectToHCAServer (" + this._baseActivity + ")");
          }
          this._app.connectToHCAServer();
        }
        return;
      }
      showHomeDisplay();
      return;
    }
    showObjects();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131361794, paramMenu);
    paramMenu.findItem(2131427490).setTitle("Disconnect");
    paramMenu.findItem(2131427490).setIcon(17301560);
    paramMenu.findItem(2131427494).setIcon(17301576);
    paramMenu.findItem(2131427492).setIcon(17301568);
    return true;
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onDestroy(" + this._baseActivity + ")");
    }
    if (HCAApplication.visibleUpdateListener != null) {
      HCAApplication.visibleUpdateListener.remove(this);
    }
    if ((this._baseActivity) && (HCAApplication.connectListener != null)) {
      HCAApplication.connectListener.remove(this);
    }
    finishActivity(2000);
  }
  
  public void onHCAUpdate(int paramInt)
  {
    if (this.objAdapter != null)
    {
      this.objAdapter.notifyDataSetChanged();
      this._spinner.stopIfId(paramInt);
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (HCAApplication.vibration > 0) {
      ((Vibrator)getSystemService("vibrator")).vibrate(HCAApplication.vibration);
    }
    switch (paramMenuItem.getItemId())
    {
    case 2131427491: 
    case 2131427493: 
    default: 
      return true;
    case 2131427490: 
      if (this._baseActivity)
      {
        this._app.disconnectFromHCAServer(true);
        return true;
      }
      Intent localIntent = getIntent();
      localIntent.putExtra("com.advancedquonsettechnology.hcaapp.mainDisconnect", "true");
      setResult(-1, localIntent);
      finish();
      return true;
    case 2131427492: 
      startActivity(new Intent().setClass(this, HelpMain.class));
      return true;
    }
    startActivityForResult(new Intent().setClass(this, ModesSet.class), 2);
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onPause(" + this._baseActivity + ")");
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onResume(" + this._baseActivity + ")");
    }
    showObjects();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onSaveInstanceState(" + this._baseActivity + ")");
    }
    if (this._baseActivity)
    {
      paramBundle.putBoolean("homeDisplayIsHTML", HCAApplication.appState.homeDisplayIsHTML);
      paramBundle.putString("homeDisplayName", HCAApplication.appState.homeDisplayName);
      paramBundle.putBoolean("areConnected", HCAApplication.appState.areConnected);
      paramBundle.putBoolean("designLoaded", HCAApplication.appState.designLoaded);
      paramBundle.putString("designTS", HCAApplication.appState.designTS);
      paramBundle.putString("updateTS", HCAApplication.appState.updateTS);
    }
  }
  
  protected void onStart()
  {
    super.onStart();
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onStart(" + this._baseActivity + ")");
    }
    RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(2131427342);
    if (localRelativeLayout != null) {
      localRelativeLayout.setBackgroundColor(HCAApplication.bg_color);
    }
    if (this.objAdapter != null) {
      this.objAdapter.notifyDataSetChanged();
    }
    if (HCAApplication.visibleUpdateListener != null) {
      HCAApplication.visibleUpdateListener.add(this);
    }
    if (HCAApplication.connectListener != null) {
      HCAApplication.connectListener.add(this);
    }
  }
  
  protected void onStop()
  {
    super.onStop();
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectListDisplay.onStop(" + this._baseActivity + ")");
    }
    this._spinner.stop();
    if (HCAApplication.visibleUpdateListener != null) {
      HCAApplication.visibleUpdateListener.remove(this);
    }
    if (HCAApplication.connectListener != null) {
      HCAApplication.connectListener.remove(this);
    }
  }
}
