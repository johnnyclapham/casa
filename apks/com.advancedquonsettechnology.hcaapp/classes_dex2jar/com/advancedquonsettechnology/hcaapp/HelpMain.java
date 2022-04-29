package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpMain
  extends Activity
{
  public HelpMain() {}
  
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
    requestWindowFeature(1);
    RelativeLayout localRelativeLayout = new RelativeLayout(getBaseContext());
    ((LayoutInflater)getBaseContext().getSystemService("layout_inflater")).inflate(2130903045, localRelativeLayout, true);
    setContentView(localRelativeLayout);
    TextView localTextView = (TextView)findViewById(2131427350);
    if (localTextView != null)
    {
      Object[] arrayOfObject = new Object[8];
      arrayOfObject[0] = Integer.valueOf(Integer.parseInt(HCAApplication.versionmajor));
      arrayOfObject[1] = Integer.valueOf(Integer.parseInt(HCAApplication.versionminor));
      arrayOfObject[2] = Integer.valueOf(Integer.parseInt(HCAApplication.versionbuild));
      arrayOfObject[3] = Integer.valueOf(Integer.parseInt(HCAApplication.svrversionmajor));
      arrayOfObject[4] = Integer.valueOf(Integer.parseInt(HCAApplication.svrversionminor));
      arrayOfObject[5] = Integer.valueOf(Integer.parseInt(HCAApplication.svrversionbuild));
      arrayOfObject[6] = HCAApplication.serverProtocol;
      arrayOfObject[7] = HCAApplication.serverProtocolMinor;
      localTextView.setText(String.format("Client: %d.%d.%d, Server: %d.%d.%d (%d.%d)", arrayOfObject));
    }
  }
}
