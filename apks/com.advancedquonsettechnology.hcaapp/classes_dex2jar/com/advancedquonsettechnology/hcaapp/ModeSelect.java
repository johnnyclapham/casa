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

public class ModeSelect
  extends Activity
{
  public ModeSelect() {}
  
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
        Log.d("HCA", "ModeSelect.onCreate and designLoaded == false");
      }
      showHomeDisplay();
    }
    Button localButton;
    do
    {
      return;
      Intent localIntent = getIntent();
      boolean bool1 = localIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modeoff", false);
      boolean bool2 = localIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modeheat", false);
      boolean bool3 = localIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modecool", false);
      boolean bool4 = localIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modeauto", false);
      requestWindowFeature(1);
      setContentView(2130903062);
      RadioButton localRadioButton1 = (RadioButton)findViewById(2131427425);
      if (localRadioButton1 != null)
      {
        if (bool1) {
          localRadioButton1.setChecked(true);
        }
        localRadioButton1.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ((Vibrator)ModeSelect.this.getSystemService("vibrator")).vibrate(20L);
            ((RadioButton)ModeSelect.this.findViewById(2131427423)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427424)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427422)).setChecked(false);
            Intent localIntent = new Intent();
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modemode", true);
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modeoff", true);
            ModeSelect.this.setResult(-1, localIntent);
            ModeSelect.this.finish();
          }
        });
      }
      RadioButton localRadioButton2 = (RadioButton)findViewById(2131427424);
      if (localRadioButton2 != null)
      {
        if (bool2) {
          localRadioButton2.setChecked(true);
        }
        localRadioButton2.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ((Vibrator)ModeSelect.this.getSystemService("vibrator")).vibrate(20L);
            ((RadioButton)ModeSelect.this.findViewById(2131427425)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427423)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427422)).setChecked(false);
            Intent localIntent = new Intent();
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modemode", true);
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modeheat", true);
            ModeSelect.this.setResult(-1, localIntent);
            ModeSelect.this.finish();
          }
        });
      }
      RadioButton localRadioButton3 = (RadioButton)findViewById(2131427423);
      if (localRadioButton3 != null)
      {
        if (bool3) {
          localRadioButton3.setChecked(true);
        }
        localRadioButton3.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ((Vibrator)ModeSelect.this.getSystemService("vibrator")).vibrate(20L);
            ((RadioButton)ModeSelect.this.findViewById(2131427425)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427424)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427422)).setChecked(false);
            Intent localIntent = new Intent();
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modemode", true);
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modecool", true);
            ModeSelect.this.setResult(-1, localIntent);
            ModeSelect.this.finish();
          }
        });
      }
      RadioButton localRadioButton4 = (RadioButton)findViewById(2131427422);
      if (localRadioButton4 != null)
      {
        if (bool4) {
          localRadioButton4.setChecked(true);
        }
        localRadioButton4.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ((Vibrator)ModeSelect.this.getSystemService("vibrator")).vibrate(20L);
            ((RadioButton)ModeSelect.this.findViewById(2131427425)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427424)).setChecked(false);
            ((RadioButton)ModeSelect.this.findViewById(2131427423)).setChecked(false);
            Intent localIntent = new Intent();
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modemode", true);
            localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modeauto", true);
            ModeSelect.this.setResult(-1, localIntent);
            ModeSelect.this.finish();
          }
        });
      }
      localButton = (Button)findViewById(2131427420);
    } while (localButton == null);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ((Vibrator)ModeSelect.this.getSystemService("vibrator")).vibrate(20L);
        Intent localIntent = new Intent();
        localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modemode", true);
        ModeSelect.this.setResult(0, localIntent);
        ModeSelect.this.finish();
      }
    });
  }
  
  protected void onStart()
  {
    super.onStart();
    RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(2131427417);
    if (localRelativeLayout != null) {
      localRelativeLayout.setBackgroundColor(HCAApplication.bg_color);
    }
  }
}
