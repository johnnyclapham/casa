package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class HelpDevice
  extends Activity
{
  public HelpDevice() {}
  
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
        Log.d("HCA", "HelpDevice.onCreate and designLoaded == false");
      }
      showHomeDisplay();
      return;
    }
    requestWindowFeature(1);
    RelativeLayout localRelativeLayout = new RelativeLayout(getBaseContext());
    ((LayoutInflater)getBaseContext().getSystemService("layout_inflater")).inflate(2130903063, localRelativeLayout, true);
    setContentView(localRelativeLayout);
  }
}
