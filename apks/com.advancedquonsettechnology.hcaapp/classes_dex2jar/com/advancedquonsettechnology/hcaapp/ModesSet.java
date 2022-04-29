package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.List;

public class ModesSet
  extends Activity
  implements HCAUpdateListener, Spinner.SpinnerTimeoutCallback
{
  Spinner _spinner;
  String pickedHomeMode = "";
  
  public ModesSet() {}
  
  private void showHomeDisplay()
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
  
  public void SpinnerTimeout(int paramInt)
  {
    HCABase localHCABase = HCAApplication.findHCAObject(paramInt);
    if (localHCABase != null) {
      Toast.makeText(getBaseContext(), "Timeout waiting for " + localHCABase.getName(), 0).show();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!HCAApplication.appState.designLoaded)
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "ModesSet.onCreate and designLoaded == false");
      }
      showHomeDisplay();
    }
    Object localObject;
    do
    {
      return;
      requestWindowFeature(1);
      RelativeLayout localRelativeLayout = new RelativeLayout(getBaseContext());
      ((LayoutInflater)getBaseContext().getSystemService("layout_inflater")).inflate(2130903047, localRelativeLayout, true);
      setContentView(localRelativeLayout);
      this._spinner = new Spinner(this, this);
      int i = -1;
      int j = HCAApplication.serverCommand(new String[] { "HCAApp", "GetHomeMode" }, "GetHomeMode");
      if (j >= 0)
      {
        this._spinner.start("...", Integer.parseInt(HCAApplication.mIPTimeout), 0);
        String str = HCAApplication.getCommandResult(j);
        if (str != null)
        {
          Response localResponse = new Response(str);
          if ((localResponse.get(0).equals("0")) && (localResponse.get(1).equals("HCAApp")) && (localResponse.get(2).equals("GetHomeMode"))) {
            i = Integer.parseInt(localResponse.get(3));
          }
        }
      }
      for (;;)
      {
        RadioGroup localRadioGroup = (RadioGroup)findViewById(2131427372);
        localObject = null;
        for (int k = 0; k < HCAApplication.HomeModes().size(); k++)
        {
          RadioButton localRadioButton = new RadioButton(getBaseContext());
          localRadioButton.setText((CharSequence)HCAApplication.HomeModes().get(k));
          localRadioButton.setTag("" + k);
          localRadioButton.setTextColor(HCAApplication.fg_color);
          localRadioButton.setChecked(false);
          if (i == k) {
            localObject = localRadioButton;
          }
          localRadioButton.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              ModesSet.this.pickedHomeMode = ((String)paramAnonymousView.getTag());
              String[] arrayOfString = new String[3];
              arrayOfString[0] = "HCAApp";
              arrayOfString[1] = "SetHomeMode";
              arrayOfString[2] = ("" + ModesSet.this.pickedHomeMode);
              HCAApplication.serverCommand(arrayOfString, null);
            }
          });
          localRadioGroup.addView(localRadioButton);
        }
        Toast.makeText(getBaseContext(), "Could not retrieve current Home Mode.", 1).show();
        continue;
        Toast.makeText(getBaseContext(), "No network connection.", 1).show();
      }
    } while (localObject == null);
    localObject.setChecked(true);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131361793, paramMenu);
    paramMenu.getItem(0).setTitle("Disconnect");
    paramMenu.getItem(0).setIcon(17301560);
    paramMenu.getItem(1).setIcon(17301568);
    return true;
  }
  
  public void onHCAUpdate(int paramInt)
  {
    if (paramInt == -2) {
      this._spinner.stop();
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (HCAApplication.vibration > 0) {
      ((Vibrator)getSystemService("vibrator")).vibrate(HCAApplication.vibration);
    }
    if (paramMenuItem.getItemId() == 2131427493) {
      startActivity(new Intent().setClass(this, HelpMain.class));
    }
    if (paramMenuItem.getItemId() == 2131427490)
    {
      Intent localIntent = getIntent();
      localIntent.putExtra("com.advancedquonsettechnology.hcaapp.mainDisconnect", "true");
      setResult(-1, localIntent);
      finish();
    }
    return true;
  }
  
  protected void onStart()
  {
    super.onStart();
    RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(2131427368);
    if (localRelativeLayout != null) {
      localRelativeLayout.setBackgroundColor(HCAApplication.bg_color);
    }
    HCAApplication.visibleUpdateListener.add(this);
  }
  
  protected void onStop()
  {
    super.onStop();
    this._spinner.stop();
    HCAApplication.visibleUpdateListener.remove(this);
  }
}
