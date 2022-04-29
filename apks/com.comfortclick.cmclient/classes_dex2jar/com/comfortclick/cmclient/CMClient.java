package com.comfortclick.cmclient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CMClient
  extends Activity
{
  protected static final int CONTEXTMENU_CONNECT = 2;
  protected static final int CONTEXTMENU_DELETEITEM = 0;
  protected static final int CONTEXTMENU_EDITITEM = 1;
  private Profile currentProfile;
  private Runnable mDelayedConnect = new Runnable()
  {
    public void run()
    {
      if (CMClient.this.mReconnectCountDown > 1)
      {
        CMClient localCMClient = CMClient.this;
        localCMClient.mReconnectCountDown = (-1 + localCMClient.mReconnectCountDown);
        CMClient.this.SetStatusText(CMClient.this.getString(2131034132) + " " + CMClient.this.mReconnectCountDown + " s.");
        CMClient.this.mReconnectTimer.postDelayed(CMClient.this.mDelayedConnect, 1000L);
        return;
      }
      CMClient.this.StopCountDown();
      ConnectivityManager localConnectivityManager = (ConnectivityManager)CMClient.this.getSystemService("connectivity");
      NetworkInfo.State localState1 = localConnectivityManager.getNetworkInfo(0).getState();
      NetworkInfo.State localState2 = localConnectivityManager.getNetworkInfo(1).getState();
      boolean bool1 = CMClient.this.settings.getBoolean(CMClient.this.getString(2131034114), true);
      boolean bool2 = CMClient.this.settings.getBoolean(CMClient.this.getString(2131034115), false);
      if (((bool1) && ((localState2 == NetworkInfo.State.CONNECTED) || (localState2 == NetworkInfo.State.CONNECTING))) || ((bool2) && ((localState1 == NetworkInfo.State.CONNECTED) || (localState1 == NetworkInfo.State.CONNECTING))))
      {
        CMClient.this.Connect(CMClient.this.currentProfile);
        return;
      }
      CMClient.this.StartCountDown();
    }
  };
  private ListView mFavList;
  private Boolean mIsUpdating = Boolean.valueOf(false);
  private int mReconnectCountDown;
  private Handler mReconnectTimer;
  protected ArrayList<Profile> profileList = new ArrayList();
  private SharedPreferences settings;
  
  public CMClient() {}
  
  private void StopCountDown()
  {
    this.mReconnectTimer.removeCallbacks(this.mDelayedConnect);
    SetStatusText("ComfortClick Manager");
  }
  
  private void initListView()
  {
    this.mFavList = ((ListView)findViewById(2131165188));
    refreshFavListItems();
    this.mFavList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
    {
      public void onCreateContextMenu(ContextMenu paramAnonymousContextMenu, View paramAnonymousView, ContextMenu.ContextMenuInfo paramAnonymousContextMenuInfo)
      {
        paramAnonymousContextMenu.add(0, 0, 0, CMClient.this.getString(2131034123));
        paramAnonymousContextMenu.add(1, 1, 1, CMClient.this.getString(2131034124));
        paramAnonymousContextMenu.add(2, 2, 3, CMClient.this.getString(2131034125));
      }
    });
    this.mFavList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Profile localProfile = (Profile)CMClient.this.mFavList.getAdapter().getItem(paramAnonymousInt);
        CMClient.this.Connect(localProfile);
      }
    });
  }
  
  private void refreshFavListItems()
  {
    Collections.sort(this.profileList, new Comparator()
    {
      public int compare(Profile paramAnonymousProfile1, Profile paramAnonymousProfile2)
      {
        return paramAnonymousProfile1.profileName.compareToIgnoreCase(paramAnonymousProfile2.profileName);
      }
    });
    ProfileAdapter localProfileAdapter = new ProfileAdapter(this, 2130903040, this.profileList);
    this.mFavList.setAdapter(localProfileAdapter);
  }
  
  public boolean Connect(Profile paramProfile)
  {
    try
    {
      StopCountDown();
      this.currentProfile = paramProfile;
      Intent localIntent = new Intent(getApplicationContext(), WebActivity.class);
      localIntent.putExtra("address", paramProfile.address);
      localIntent.putExtra("panelMode", false);
      localIntent.putExtra("profileName", paramProfile.profileName);
      localIntent.putExtra("username", paramProfile.username);
      localIntent.putExtra("password", paramProfile.password);
      localIntent.putExtra("device_name", this.settings.getString(getString(2131034113), ""));
      localIntent.putExtra("keepRunning", this.settings.getBoolean(getString(2131034112), false));
      localIntent.putExtra("refresh_fore", Integer.parseInt(this.settings.getString(getString(2131034118), "3")));
      localIntent.putExtra("refresh_back", Integer.parseInt(this.settings.getString(getString(2131034117), "15")));
      localIntent.putExtra("dimmingTimeout", 0);
      startActivityForResult(localIntent, 0);
      return true;
    }
    catch (Exception localException)
    {
      Toast.makeText(getApplicationContext(), "Error: " + localException.getMessage(), 0).show();
    }
    return true;
  }
  
  public void SetStatusText(final String paramString)
  {
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        ((TextView)CMClient.this.findViewById(2131165189)).setText(paramString);
      }
    });
  }
  
  public void StartCountDown()
  {
    if (this.mIsUpdating.booleanValue()) {
      return;
    }
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    boolean bool1 = localSharedPreferences.getBoolean(getString(2131034114), true);
    boolean bool2 = localSharedPreferences.getBoolean(getString(2131034115), false);
    int i = Integer.parseInt(localSharedPreferences.getString(getString(2131034116), "30"));
    if ((bool1) || (bool2))
    {
      this.mReconnectTimer.removeCallbacks(this.mDelayedConnect);
      this.mReconnectCountDown = i;
      this.mReconnectTimer.postDelayed(this.mDelayedConnect, 1000L);
      return;
    }
    SetStatusText(getString(2131034133));
  }
  
  public void loadSettings()
  {
    try
    {
      String str = getSharedPreferences("CMClientProfileList", 0).getString("json", "");
      ArrayList localArrayList = (ArrayList)new Gson().fromJson(str, new TypeToken() {}.getType());
      if (localArrayList != null)
      {
        this.profileList = localArrayList;
        return;
      }
      this.profileList = new ArrayList();
      Profile localProfile = new Profile("Demo", "https://89.212.118.250", "User", "Pass");
      this.profileList.add(localProfile);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != WebActivity.RESULT_CLOSE)
    {
      if (paramInt2 != WebActivity.RESULT_LOGIN_ERROR) {
        break label25;
      }
      showProfileSettings(this.currentProfile, false, true);
    }
    label25:
    do
    {
      return;
      if (paramInt2 == WebActivity.RESULT_CONNECTIONLOST)
      {
        StartCountDown();
        return;
      }
    } while (paramInt2 != WebActivity.RESULT_CONNECTIONFAILED);
    StartCountDown();
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramMenuItem.getMenuInfo();
    Profile localProfile = (Profile)this.mFavList.getAdapter().getItem(localAdapterContextMenuInfo.position);
    switch (paramMenuItem.getItemId())
    {
    default: 
      return false;
    case 0: 
      this.profileList.remove(localProfile);
      saveSettings();
      refreshFavListItems();
      return true;
    case 1: 
      showProfileSettings(localProfile, false, false);
      return true;
    }
    Connect(localProfile);
    return true;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903041);
    this.settings = PreferenceManager.getDefaultSharedPreferences(this);
    this.mReconnectTimer = new Handler();
    this.mReconnectCountDown = 30;
    loadSettings();
    initListView();
    if (this.currentProfile != null) {
      Connect(this.currentProfile);
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131099648, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    StopCountDown();
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131165207: 
      showProfileSettings(new Profile("New", "https://", "", ""), true, false);
      return true;
    case 2131165209: 
      finish();
      return true;
    }
    startprefActivity();
    return true;
  }
  
  public void saveSettings()
  {
    try
    {
      SharedPreferences.Editor localEditor = getSharedPreferences("CMClientProfileList", 0).edit();
      localEditor.putString("json", new Gson().toJson(this.profileList));
      localEditor.commit();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void showProfileSettings(final Profile paramProfile, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    final Dialog localDialog = new Dialog(this);
    localDialog.setContentView(2130903042);
    localDialog.setTitle("Profile settings");
    localDialog.setCancelable(true);
    final TextView localTextView1 = (TextView)localDialog.findViewById(2131165191);
    localTextView1.setText(paramProfile.profileName);
    final TextView localTextView2 = (TextView)localDialog.findViewById(2131165193);
    localTextView2.setText(paramProfile.address);
    final TextView localTextView3 = (TextView)localDialog.findViewById(2131165195);
    localTextView3.setText(paramProfile.username);
    final TextView localTextView4 = (TextView)localDialog.findViewById(2131165197);
    localTextView4.setText(paramProfile.password);
    ((Button)localDialog.findViewById(2131165201)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramProfile.profileName = localTextView1.getText().toString();
        paramProfile.address = localTextView2.getText().toString();
        paramProfile.username = localTextView3.getText().toString();
        paramProfile.password = localTextView4.getText().toString();
        if (paramBoolean1) {
          CMClient.this.profileList.add(paramProfile);
        }
        CMClient.this.saveSettings();
        CMClient.this.refreshFavListItems();
        localDialog.hide();
        if (paramBoolean2) {
          CMClient.this.Connect(paramProfile);
        }
      }
    });
    ((Button)localDialog.findViewById(2131165200)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localDialog.cancel();
      }
    });
    localDialog.show();
  }
  
  void startprefActivity()
  {
    Intent localIntent = new Intent("android.intent.action.MAIN");
    localIntent.setClass(this, CMClientPreferencesActivity.class);
    startActivity(localIntent);
  }
}
