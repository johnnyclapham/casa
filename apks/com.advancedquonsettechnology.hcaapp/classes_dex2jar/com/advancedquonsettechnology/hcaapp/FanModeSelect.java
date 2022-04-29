package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class FanModeSelect
  extends Activity
{
  public FanModeSelect() {}
  
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
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!HCAApplication.appState.designLoaded)
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "FanModeSelect.onCreate and designLoaded == false");
      }
      showHomeDisplay();
    }
    Button localButton;
    do
    {
      return;
      Intent localIntent = getIntent();
      boolean bool1 = localIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.fanon", false);
      boolean bool2 = localIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.fanoff", false);
      requestWindowFeature(1);
      setContentView(2130903041);
      RadioButton localRadioButton1 = (RadioButton)findViewById(2131427337);
      if (localRadioButton1 != null)
      {
        if (bool1) {
          localRadioButton1.setChecked(true);
        }
        localRadioButton1.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ((Vibrator)FanModeSelect.this.getSystemService("vibrator")).vibrate(20L);
            ((RadioButton)FanModeSelect.this.findViewById(2131427336)).setChecked(false);
            Intent localIntent = new Intent();
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.fanmode", true);
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.fanon", true);
            FanModeSelect.this.setResult(-1, localIntent);
            FanModeSelect.this.finish();
          }
        });
      }
      RadioButton localRadioButton2 = (RadioButton)findViewById(2131427336);
      if (localRadioButton2 != null)
      {
        if (bool2) {
          localRadioButton2.setChecked(true);
        }
        localRadioButton2.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ((Vibrator)FanModeSelect.this.getSystemService("vibrator")).vibrate(20L);
            ((RadioButton)FanModeSelect.this.findViewById(2131427337)).setChecked(false);
            Intent localIntent = new Intent();
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.fanmode", true);
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.fanoff", true);
            FanModeSelect.this.setResult(-1, localIntent);
            FanModeSelect.this.finish();
          }
        });
      }
      localButton = (Button)findViewById(2131427334);
    } while (localButton == null);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ((Vibrator)FanModeSelect.this.getSystemService("vibrator")).vibrate(20L);
        Intent localIntent = new Intent();
        localIntent.putExtra("com.advancedquonsettechnology.hcaapp.fanmode", true);
        FanModeSelect.this.setResult(0, localIntent);
        FanModeSelect.this.finish();
      }
    });
  }
  
  protected void onStart()
  {
    super.onStart();
    RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(2131427331);
    if (localRelativeLayout != null) {
      localRelativeLayout.setBackgroundColor(HCAApplication.bg_color);
    }
  }
}
