package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ObjectDisplay
  extends Activity
  implements HCAUpdateListener, Spinner.SpinnerTimeoutCallback
{
  static final int AR_THRM_FAN = 1;
  static final int AR_THRM_MODE = 2;
  Button Bulb1;
  Button Bulb2;
  Button Bulb3;
  Button Bulb4;
  Button Bulb5;
  Button Bulb6;
  Button Bulb7;
  Button Bulb8;
  Button FanMode;
  Button ModeMode;
  Button ProgOff;
  Button ProgOn;
  Button ProgStart;
  Button ProgStop;
  Button ProgStopOff;
  Button ProgStopOn;
  Button ProgStopStart;
  Button Rocker1B;
  Button Rocker1T;
  Button Rocker2B;
  Button Rocker2T;
  Button Rocker3B;
  Button Rocker3T;
  Button Rocker4B;
  Button Rocker4T;
  Button Rocker5B;
  Button Rocker5T;
  Button Rocker6B;
  Button Rocker6T;
  Button Rocker7B;
  Button Rocker7T;
  Button Rocker8B;
  Button Rocker8T;
  private Context _ctx;
  Spinner _spinner;
  boolean coolSetpointPending = false;
  SeekBar dimview;
  TextView fnameView;
  boolean haveStateIcon = true;
  int hcaObjId = 0;
  HCABase hcaObject;
  boolean heatSetpointPending = false;
  ImageView iconView;
  private int laststate = 0;
  String layoutname;
  private RelativeLayout layoutview;
  TextView nameView;
  Timer setpointTimer;
  RelativeLayout stateview;
  RelativeLayout suspenseView;
  boolean updateThermostatState = false;
  
  public ObjectDisplay() {}
  
  private void setEnableButtons(String paramString, int paramInt)
  {
    this.hcaObject.setState(paramInt);
    if (!this.haveStateIcon) {}
    switch (this.hcaObject.getState())
    {
    default: 
      this.stateview.setBackgroundResource(2131165191);
      this.stateview.forceLayout();
      if ((paramString.equals("ProgramOnOff")) || (paramString.equals("ProgramStart")))
      {
        if (this.ProgOn != null) {
          this.ProgOn.setEnabled(true);
        }
        if (this.ProgStopOn != null) {
          this.ProgStopOn.setEnabled(true);
        }
        if (this.ProgOff != null) {
          this.ProgOff.setEnabled(true);
        }
        if (this.ProgStopOff != null) {
          this.ProgStopOff.setEnabled(true);
        }
        if (this.ProgStart != null) {
          this.ProgStart.setEnabled(true);
        }
        if (this.ProgStopStart != null) {
          this.ProgStopStart.setEnabled(true);
        }
        if (this.ProgStop != null) {
          this.ProgStop.setEnabled(true);
        }
      }
      break;
    }
    do
    {
      do
      {
        return;
        this.stateview.setBackgroundColor(HCAApplication.bg_color);
        break;
        switch (paramInt)
        {
        default: 
          if (this.ProgOn != null) {
            this.ProgOn.setEnabled(false);
          }
          if (this.ProgStopOn != null) {
            this.ProgStopOn.setEnabled(false);
          }
          if (this.ProgOff != null) {
            this.ProgOff.setEnabled(false);
          }
          if (this.ProgStopOff != null) {
            this.ProgStopOff.setEnabled(false);
          }
          if (this.ProgStart != null) {
            this.ProgStart.setEnabled(false);
          }
          if (this.ProgStopStart != null) {
            this.ProgStopStart.setEnabled(false);
          }
          break;
        }
      } while (this.ProgStop == null);
      this.ProgStop.setEnabled(true);
      return;
      if (this.ProgOn != null) {
        this.ProgOn.setEnabled(true);
      }
      if (this.ProgStopOn != null) {
        this.ProgStopOn.setEnabled(true);
      }
      if (this.ProgOff != null) {
        this.ProgOff.setEnabled(true);
      }
      if (this.ProgStopOff != null) {
        this.ProgStopOff.setEnabled(true);
      }
      if (this.ProgStart != null) {
        this.ProgStart.setEnabled(true);
      }
      if (this.ProgStopStart != null) {
        this.ProgStopStart.setEnabled(true);
      }
    } while (this.ProgStop == null);
    this.ProgStop.setEnabled(true);
  }
  
  private void setStateButtons(HCADevice paramHCADevice)
  {
    getBaseContext().getResources();
    if (paramHCADevice.getButtonCount() > 0)
    {
      int i = 0;
      if (i < paramHCADevice.getButtonCount())
      {
        switch (i)
        {
        }
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        i++;
                        break;
                      } while (this.Bulb1 == null);
                      if (paramHCADevice.getButtonState(i) == 0)
                      {
                        this.Bulb1.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                        this.Bulb1.setTextColor(-1);
                      }
                      for (;;)
                      {
                        this.Bulb1.setText(paramHCADevice.getButtonName(i));
                        break;
                        this.Bulb1.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                        this.Bulb1.setTextColor(-16777216);
                      }
                    } while (this.Bulb2 == null);
                    if (paramHCADevice.getButtonState(i) == 0)
                    {
                      this.Bulb2.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                      this.Bulb2.setTextColor(-1);
                    }
                    for (;;)
                    {
                      this.Bulb2.setText(paramHCADevice.getButtonName(i));
                      break;
                      this.Bulb2.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                      this.Bulb2.setTextColor(-16777216);
                    }
                  } while (this.Bulb3 == null);
                  if (paramHCADevice.getButtonState(i) == 0)
                  {
                    this.Bulb3.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                    this.Bulb3.setTextColor(-1);
                  }
                  for (;;)
                  {
                    this.Bulb3.setText(paramHCADevice.getButtonName(i));
                    break;
                    this.Bulb3.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                    this.Bulb3.setTextColor(-16777216);
                  }
                } while (this.Bulb4 == null);
                if (paramHCADevice.getButtonState(i) == 0)
                {
                  this.Bulb4.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                  this.Bulb4.setTextColor(-1);
                }
                for (;;)
                {
                  this.Bulb4.setText(paramHCADevice.getButtonName(i));
                  break;
                  this.Bulb4.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                  this.Bulb4.setTextColor(-16777216);
                }
              } while (this.Bulb5 == null);
              if (paramHCADevice.getButtonState(i) == 0)
              {
                this.Bulb5.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                this.Bulb5.setTextColor(-1);
              }
              for (;;)
              {
                this.Bulb5.setText(paramHCADevice.getButtonName(i));
                break;
                this.Bulb5.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                this.Bulb5.setTextColor(-16777216);
              }
            } while (this.Bulb6 == null);
            if (paramHCADevice.getButtonState(i) == 0)
            {
              this.Bulb6.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
              this.Bulb6.setTextColor(-1);
            }
            for (;;)
            {
              this.Bulb6.setText(paramHCADevice.getButtonName(i));
              break;
              this.Bulb6.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
              this.Bulb6.setTextColor(-16777216);
            }
          } while (this.Bulb7 == null);
          if (paramHCADevice.getButtonState(i) == 0)
          {
            this.Bulb7.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
            this.Bulb7.setTextColor(-1);
          }
          for (;;)
          {
            this.Bulb7.setText(paramHCADevice.getButtonName(i));
            break;
            this.Bulb7.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
            this.Bulb7.setTextColor(-16777216);
          }
        } while (this.Bulb8 == null);
        if (paramHCADevice.getButtonState(i) == 0)
        {
          this.Bulb8.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
          this.Bulb8.setTextColor(-1);
        }
        for (;;)
        {
          this.Bulb8.setText(paramHCADevice.getButtonName(i));
          break;
          this.Bulb8.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
          this.Bulb8.setTextColor(-16777216);
        }
      }
    }
  }
  
  private void setTextButtons(HCADevice paramHCADevice)
  {
    if (paramHCADevice.getRockerCount() > 0)
    {
      int j = 0;
      if (j < paramHCADevice.getRockerCount())
      {
        switch (j)
        {
        }
        for (;;)
        {
          j++;
          break;
          if (this.Rocker1T != null)
          {
            this.Rocker1T.setText(paramHCADevice.getRockerName(j));
            continue;
            if (this.Rocker2T != null)
            {
              this.Rocker2T.setText(paramHCADevice.getRockerName(j));
              continue;
              if (this.Rocker3T != null)
              {
                this.Rocker3T.setText(paramHCADevice.getRockerName(j));
                continue;
                if (this.Rocker4T != null)
                {
                  this.Rocker4T.setText(paramHCADevice.getRockerName(j));
                  continue;
                  if (this.Rocker5T != null)
                  {
                    this.Rocker5T.setText(paramHCADevice.getRockerName(j));
                    continue;
                    if (this.Rocker6T != null)
                    {
                      this.Rocker6T.setText(paramHCADevice.getRockerName(j));
                      continue;
                      if (this.Rocker7T != null)
                      {
                        this.Rocker7T.setText(paramHCADevice.getRockerName(j));
                        continue;
                        if (this.Rocker8T != null) {
                          this.Rocker8T.setText(paramHCADevice.getRockerName(j));
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    if (paramHCADevice.getButtonCount() > 0)
    {
      int i = 0;
      if (i < paramHCADevice.getButtonCount())
      {
        switch (i)
        {
        }
        for (;;)
        {
          i++;
          break;
          if (this.Bulb1 != null)
          {
            this.Bulb1.setText(paramHCADevice.getButtonName(i));
            continue;
            if (this.Bulb2 != null)
            {
              this.Bulb2.setText(paramHCADevice.getButtonName(i));
              continue;
              if (this.Bulb3 != null)
              {
                this.Bulb3.setText(paramHCADevice.getButtonName(i));
                continue;
                if (this.Bulb4 != null)
                {
                  this.Bulb4.setText(paramHCADevice.getButtonName(i));
                  continue;
                  if (this.Bulb5 != null)
                  {
                    this.Bulb5.setText(paramHCADevice.getButtonName(i));
                    continue;
                    if (this.Bulb6 != null)
                    {
                      this.Bulb6.setText(paramHCADevice.getButtonName(i));
                      continue;
                      if (this.Bulb7 != null)
                      {
                        this.Bulb7.setText(paramHCADevice.getButtonName(i));
                        continue;
                        if (this.Bulb8 != null) {
                          this.Bulb8.setText(paramHCADevice.getButtonName(i));
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
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
  
  private void startResponseTimer(int paramInt)
  {
    int i = Integer.parseInt(HCAApplication.mIPTimeout);
    if (this.hcaObject.isThermostat()) {
      i *= 5;
    }
    this._spinner.start("...", i, paramInt);
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
    switch (paramInt1)
    {
    }
    HCAThermostat localHCAThermostat1;
    boolean bool1;
    boolean bool2;
    boolean bool3;
    boolean bool4;
    do
    {
      do
      {
        HCAThermostat localHCAThermostat2;
        boolean bool5;
        boolean bool6;
        do
        {
          do
          {
            return;
            localHCAThermostat2 = (HCAThermostat)this.hcaObject;
          } while (!paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.fanmode", false));
          bool5 = paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.fanon", false);
          bool6 = paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.fanoff", false);
          if (HCAApplication.vibration > 0) {
            ((Vibrator)getSystemService("vibrator")).vibrate(HCAApplication.vibration);
          }
        } while ((!bool5) && (!bool6));
        String[] arrayOfString2;
        if (bool5)
        {
          localHCAThermostat2.fanmode = true;
          arrayOfString2 = new String[5];
          arrayOfString2[0] = "HCAObject";
          arrayOfString2[1] = "Device.Thermostat";
          arrayOfString2[2] = this.hcaObject.getCommandName();
          arrayOfString2[3] = "2";
          arrayOfString2[4] = "1";
        }
        while (HCAApplication.serverCommand(arrayOfString2, null) >= 0)
        {
          startResponseTimer(this.hcaObject.getObjectId());
          TextView localTextView2 = (TextView)findViewById(2131427333);
          localTextView2.setTextColor(HCAApplication.fg_color);
          if (localHCAThermostat2.fanmode)
          {
            localTextView2.setText("Fan is On");
            return;
            localHCAThermostat2.fanmode = false;
            arrayOfString2 = new String[5];
            arrayOfString2[0] = "HCAObject";
            arrayOfString2[1] = "Device.Thermostat";
            arrayOfString2[2] = this.hcaObject.getCommandName();
            arrayOfString2[3] = "2";
            arrayOfString2[4] = "0";
          }
          else
          {
            localTextView2.setText("Fan is Off");
            return;
          }
        }
        Toast.makeText(getBaseContext(), "No network connection.", 1).show();
        return;
        localHCAThermostat1 = (HCAThermostat)this.hcaObject;
      } while (!paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modemode", false));
      bool1 = paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modeoff", false);
      bool2 = paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modeheat", false);
      bool3 = paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modecool", false);
      bool4 = paramIntent.getBooleanExtra("com.advancedquonsettechnology.hcaapp.modeauto", false);
      if (HCAApplication.vibration > 0) {
        ((Vibrator)getSystemService("vibrator")).vibrate(HCAApplication.vibration);
      }
    } while ((!bool1) && (!bool2) && (!bool3) && (!bool4));
    String[] arrayOfString1 = null;
    if (bool1)
    {
      localHCAThermostat1.modemode = 0;
      arrayOfString1 = new String[5];
      arrayOfString1[0] = "HCAObject";
      arrayOfString1[1] = "Device.Thermostat";
      arrayOfString1[2] = this.hcaObject.getCommandName();
      arrayOfString1[3] = "1";
      arrayOfString1[4] = "0";
    }
    if (bool2)
    {
      localHCAThermostat1.modemode = 1;
      arrayOfString1 = new String[5];
      arrayOfString1[0] = "HCAObject";
      arrayOfString1[1] = "Device.Thermostat";
      arrayOfString1[2] = this.hcaObject.getCommandName();
      arrayOfString1[3] = "1";
      arrayOfString1[4] = "1";
    }
    if (bool3)
    {
      localHCAThermostat1.modemode = 2;
      arrayOfString1 = new String[5];
      arrayOfString1[0] = "HCAObject";
      arrayOfString1[1] = "Device.Thermostat";
      arrayOfString1[2] = this.hcaObject.getCommandName();
      arrayOfString1[3] = "1";
      arrayOfString1[4] = "2";
    }
    if (bool4)
    {
      localHCAThermostat1.modemode = 3;
      arrayOfString1 = new String[5];
      arrayOfString1[0] = "HCAObject";
      arrayOfString1[1] = "Device.Thermostat";
      arrayOfString1[2] = this.hcaObject.getCommandName();
      arrayOfString1[3] = "1";
      arrayOfString1[4] = "3";
    }
    HCAApplication.serverCommand(arrayOfString1, null);
    startResponseTimer(this.hcaObject.getObjectId());
    TextView localTextView1 = (TextView)findViewById(2131427419);
    localTextView1.setTextColor(HCAApplication.fg_color);
    switch (localHCAThermostat1.modemode)
    {
    default: 
      return;
    case 0: 
      localTextView1.setText("Mode is Off");
      return;
    case 1: 
      localTextView1.setText("Heat Mode");
      return;
    case 2: 
      localTextView1.setText("Cool Mode");
      return;
    }
    localTextView1.setText("Auto Mode");
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectDisplay.onCreate");
    }
    if (!HCAApplication.appState.designLoaded)
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "ObjectDisplay.onCreate and designLoaded == false");
      }
      showHomeDisplay();
    }
    label612:
    label1247:
    label2147:
    label2164:
    label2170:
    label2185:
    for (;;)
    {
      return;
      this._ctx = getBaseContext();
      this.hcaObjId = getIntent().getIntExtra("com.advancedquonsettechnology.hcaapp.objectid", 0);
      this.hcaObject = HCAApplication.findHCAObject(this.hcaObjId);
      if ((this.hcaObject == null) || (this.hcaObject.getLayoutName() == null))
      {
        finish();
        return;
      }
      this.laststate = this.hcaObject.getState();
      this.layoutname = this.hcaObject.getLayoutName().toLowerCase();
      if ((this.layoutname.toLowerCase().startsWith("thermostat")) && (HCAApplication.svrversion >= 12)) {
        this.layoutname = "thermostatv13";
      }
      if ((this.layoutname.equals("upb7")) && (!((HCADevice)this.hcaObject).getButtonName(6).equals("UP"))) {
        this.layoutname = "upb7a";
      }
      if ((this.layoutname.equals("upb7load")) && (!((HCADevice)this.hcaObject).getButtonName(6).equals("UP"))) {
        this.layoutname = "upb7aload";
      }
      if ((this.layoutname.equals("upb7loaddim")) && (!((HCADevice)this.hcaObject).getButtonName(6).equals("UP"))) {
        this.layoutname = "upb7aloaddim";
      }
      int i = this._ctx.getResources().getIdentifier("com.advancedquonsettechnology.hcaapp:layout/" + this.layoutname, "", "");
      if (i == 0) {
        i = this._ctx.getResources().getIdentifier("com.advancedquonsettechnology.hcaapp:layout/missing", "", "");
      }
      this.layoutview = new RelativeLayout(this._ctx);
      Spinner localSpinner = new Spinner(this, this);
      this._spinner = localSpinner;
      ((LayoutInflater)getBaseContext().getSystemService("layout_inflater")).inflate(i, this.layoutview, true);
      setContentView(this.layoutview);
      this.nameView = ((TextView)this.layoutview.findViewWithTag("Name"));
      this.fnameView = ((TextView)this.layoutview.findViewWithTag("FolderName"));
      this.iconView = ((ImageView)this.layoutview.findViewWithTag("Icon"));
      this.suspenseView = ((RelativeLayout)this.layoutview.findViewWithTag("Suspense"));
      this.stateview = ((RelativeLayout)this.layoutview.findViewWithTag("State"));
      showDeviceState();
      if (this.hcaObject.isThermostat()) {
        showThermostatState();
      }
      int j = 1;
      if (j <= 8)
      {
        Button localButton13 = (Button)this.layoutview.findViewWithTag("Rocker" + j + "T");
        Button localButton14;
        switch (j)
        {
        default: 
          if (localButton13 != null)
          {
            localButton13.setBackgroundDrawable(getBaseContext().getResources().getDrawable(2130837662));
            View.OnClickListener local1 = new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                if (HCAApplication.vibration > 0) {
                  ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
                }
                String str1 = ((String)paramAnonymousView.getTag()).substring(6, 7);
                String str2 = "" + (-1 + Integer.parseInt(str1));
                String[] arrayOfString;
                switch (ObjectDisplay.this.hcaObject.getKind())
                {
                default: 
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Device.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  arrayOfString[5] = "0";
                  if (HCAApplication.serverCommand(arrayOfString, null) >= 0)
                  {
                    ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
                    label180:
                    if (!ObjectDisplay.this.haveStateIcon) {
                      switch (ObjectDisplay.this.hcaObject.getState())
                      {
                      default: 
                        int i = 16776960 + 0;
                        ObjectDisplay.this.stateview.setBackgroundColor(i);
                      }
                    }
                  }
                  break;
                }
                for (;;)
                {
                  SeekBar localSeekBar = (SeekBar)ObjectDisplay.this.layoutview.findViewWithTag("Dim");
                  if (localSeekBar != null) {
                    localSeekBar.setProgress(0);
                  }
                  ObjectDisplay.this.stateview.forceLayout();
                  if (ObjectDisplay.this.hcaObject.isDevice()) {
                    ObjectDisplay.this.setStateButtons((HCADevice)ObjectDisplay.this.hcaObject);
                  }
                  return;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Device.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  arrayOfString[5] = "0";
                  break;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Program.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  arrayOfString[5] = "0";
                  break;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Group.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  arrayOfString[5] = "0";
                  break;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Controller.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  arrayOfString[5] = "0";
                  break;
                  Toast.makeText(ObjectDisplay.this.getBaseContext(), "No network connection.", 1).show();
                  break label180;
                  ObjectDisplay.this.stateview.setBackgroundColor(HCAApplication.bg_color);
                  continue;
                  ObjectDisplay.this.stateview.setBackgroundResource(2131165191);
                }
              }
            };
            localButton13.setOnClickListener(local1);
          }
          localButton14 = (Button)this.layoutview.findViewWithTag("Rocker" + j + "B");
          switch (j)
          {
          }
          break;
        }
        for (;;)
        {
          if (localButton14 != null)
          {
            localButton14.setBackgroundDrawable(getBaseContext().getResources().getDrawable(2130837662));
            View.OnClickListener local2 = new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                if (HCAApplication.vibration > 0) {
                  ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
                }
                String str1 = ((String)paramAnonymousView.getTag()).substring(6, 7);
                String str2 = "" + (-1 + Integer.parseInt(str1));
                String[] arrayOfString;
                switch (ObjectDisplay.this.hcaObject.getKind())
                {
                default: 
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Device.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "1";
                  arrayOfString[5] = "0";
                }
                for (;;)
                {
                  HCAApplication.serverCommand(arrayOfString, null);
                  ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
                  return;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Device.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "1";
                  arrayOfString[5] = "0";
                  continue;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Program.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "1";
                  arrayOfString[5] = "0";
                  continue;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Group.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "1";
                  arrayOfString[5] = "0";
                  continue;
                  arrayOfString = new String[6];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Controller.RockerPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "1";
                  arrayOfString[5] = "0";
                }
              }
            };
            localButton14.setOnClickListener(local2);
          }
          j++;
          break;
          this.Rocker1T = localButton13;
          break label612;
          this.Rocker2T = localButton13;
          break label612;
          this.Rocker3T = localButton13;
          break label612;
          this.Rocker4T = localButton13;
          break label612;
          this.Rocker5T = localButton13;
          break label612;
          this.Rocker6T = localButton13;
          break label612;
          this.Rocker7T = localButton13;
          break label612;
          this.Rocker8T = localButton13;
          break label612;
          this.Rocker1B = localButton14;
          continue;
          this.Rocker2B = localButton14;
          continue;
          this.Rocker3B = localButton14;
          continue;
          this.Rocker4B = localButton14;
          continue;
          this.Rocker5B = localButton14;
          continue;
          this.Rocker6B = localButton14;
          continue;
          this.Rocker7B = localButton14;
          continue;
          this.Rocker8B = localButton14;
        }
      }
      int k = 1;
      if (k <= 8)
      {
        Button localButton12 = (Button)this.layoutview.findViewWithTag("Bulb" + k);
        switch (k)
        {
        }
        for (;;)
        {
          if (localButton12 != null)
          {
            View.OnClickListener local3 = new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                if (HCAApplication.vibration > 0) {
                  ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
                }
                String str1 = ((String)paramAnonymousView.getTag()).substring(4);
                String str2 = "" + (-1 + Integer.parseInt(str1));
                String[] arrayOfString;
                switch (ObjectDisplay.this.hcaObject.getKind())
                {
                default: 
                  arrayOfString = new String[5];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Device.ButtonPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                }
                for (;;)
                {
                  HCAApplication.serverCommand(arrayOfString, null);
                  ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
                  return;
                  arrayOfString = new String[5];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Device.ButtonPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  continue;
                  arrayOfString = new String[5];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Program.ButtonPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  continue;
                  arrayOfString = new String[5];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Group.ButtonPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                  continue;
                  arrayOfString = new String[5];
                  arrayOfString[0] = "HCAObject";
                  arrayOfString[1] = "Controller.ButtonPress";
                  arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
                  arrayOfString[3] = str2;
                  arrayOfString[4] = "0";
                }
              }
            };
            localButton12.setOnClickListener(local3);
          }
          k++;
          break;
          this.Bulb1 = localButton12;
          continue;
          this.Bulb2 = localButton12;
          continue;
          this.Bulb3 = localButton12;
          continue;
          this.Bulb4 = localButton12;
          continue;
          this.Bulb5 = localButton12;
          continue;
          this.Bulb6 = localButton12;
          continue;
          this.Bulb7 = localButton12;
          continue;
          this.Bulb8 = localButton12;
        }
      }
      int m;
      if ((this.hcaObject.isDevice()) && (this.hcaObject.getKind() != 1))
      {
        if (this.layoutname.equals("hkey6"))
        {
          String[] arrayOfString3 = new String[3];
          arrayOfString3[0] = "HCAApp";
          arrayOfString3[1] = "KeypadButtonState";
          arrayOfString3[2] = this.hcaObject.getCommandName();
          if (HCAApplication.serverCommand(arrayOfString3, null) < 0) {
            break label2147;
          }
          startResponseTimer(this.hcaObject.getObjectId());
        }
        if ((!this.layoutname.equals("irdevice")) && (!this.layoutname.equals("onoff")) && (!this.layoutname.equals("onoffdim"))) {
          break label2164;
        }
        m = 1;
        if (m == 0)
        {
          setTextButtons((HCADevice)this.hcaObject);
          setStateButtons((HCADevice)this.hcaObject);
        }
      }
      Button localButton1 = (Button)this.layoutview.findViewWithTag("Off");
      if (localButton1 != null)
      {
        View.OnClickListener local4 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            HCAApplication.serverCommand(ObjectDisplay.this.hcaObject.getOffCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton1.setOnClickListener(local4);
      }
      SeekBar localSeekBar1 = (SeekBar)this.layoutview.findViewWithTag("Dim");
      if (localSeekBar1 != null)
      {
        localSeekBar1.setProgress(this.hcaObject.getState());
        SeekBar.OnSeekBarChangeListener local5 = new SeekBar.OnSeekBarChangeListener()
        {
          public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
          {
            ObjectDisplay.this.hcaObject.setState(paramAnonymousInt);
            if (!ObjectDisplay.this.haveStateIcon) {
              switch (ObjectDisplay.this.hcaObject.getState())
              {
              default: 
                int i = 16776960 + 16777216 * (2 * ObjectDisplay.this.hcaObject.getState());
                ObjectDisplay.this.stateview.setBackgroundColor(i);
              }
            }
            for (;;)
            {
              ObjectDisplay.this.stateview.forceLayout();
              return;
              ObjectDisplay.this.stateview.setBackgroundColor(HCAApplication.bg_color);
              continue;
              ObjectDisplay.this.stateview.setBackgroundResource(2131165191);
            }
          }
          
          public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
          
          public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            ObjectDisplay.this.hcaObject.setState(paramAnonymousSeekBar.getProgress());
            ObjectDisplay.access$302(ObjectDisplay.this, paramAnonymousSeekBar.getProgress());
            HCAApplication.serverCommand(ObjectDisplay.this.hcaObject.getDimToCommand(ObjectDisplay.this.laststate), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localSeekBar1.setOnSeekBarChangeListener(local5);
      }
      Button localButton2 = (Button)this.layoutview.findViewWithTag("On");
      if (localButton2 != null)
      {
        View.OnClickListener local6 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            ObjectDisplay.this.hcaObject.setState(100);
            HCAApplication.serverCommand(ObjectDisplay.this.hcaObject.getOnCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton2.setOnClickListener(local6);
      }
      Button localButton3 = (Button)this.layoutview.findViewWithTag("ProgOff");
      this.ProgOff = localButton3;
      if (localButton3 != null)
      {
        View.OnClickListener local7 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            HCAApplication.serverCommand(ObjectDisplay.this.hcaObject.getOffCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton3.setOnClickListener(local7);
      }
      Button localButton4 = (Button)this.layoutview.findViewWithTag("ProgOn");
      this.ProgOn = localButton4;
      if (localButton4 != null)
      {
        View.OnClickListener local8 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            HCAApplication.serverCommand(ObjectDisplay.this.hcaObject.getOnCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton4.setOnClickListener(local8);
      }
      Button localButton5 = (Button)this.layoutview.findViewWithTag("ProgStopOff");
      this.ProgStopOff = localButton5;
      if (localButton5 != null)
      {
        View.OnClickListener local9 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getStopCommand(), null);
            HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getOffCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton5.setOnClickListener(local9);
      }
      Button localButton6 = (Button)this.layoutview.findViewWithTag("ProgStopOn");
      this.ProgStopOn = localButton6;
      if (localButton6 != null)
      {
        View.OnClickListener local10 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getStopCommand(), null);
            HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getOnCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton6.setOnClickListener(local10);
      }
      Button localButton7 = (Button)this.layoutview.findViewWithTag("ProgStopStart");
      this.ProgStopStart = localButton7;
      if (localButton7 != null)
      {
        View.OnClickListener local11 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            if ((HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getStopCommand(), null) >= 0) && (HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getStartCommand(), null) >= 0)) {
              ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
            }
          }
        };
        localButton7.setOnClickListener(local11);
      }
      Button localButton8 = (Button)this.layoutview.findViewWithTag("ProgStart");
      this.ProgStart = localButton8;
      if (localButton8 != null)
      {
        View.OnClickListener local12 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getStartCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton8.setOnClickListener(local12);
      }
      Button localButton9 = (Button)this.layoutview.findViewWithTag("ProgStop");
      this.ProgStop = localButton9;
      if (localButton9 != null)
      {
        View.OnClickListener local13 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            HCAApplication.serverCommand(((HCAProgram)ObjectDisplay.this.hcaObject).getStopCommand(), null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localButton9.setOnClickListener(local13);
      }
      if (this.hcaObject.getKind() == 1) {
        setEnableButtons(this.layoutname, this.hcaObject.getState());
      }
      Button localButton10 = (Button)this.layoutview.findViewWithTag("FanMode");
      this.FanMode = localButton10;
      if (localButton10 != null)
      {
        final HCAThermostat localHCAThermostat4 = (HCAThermostat)this.hcaObject;
        View.OnClickListener local14 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            Intent localIntent = new Intent(ObjectDisplay.this.getBaseContext(), FanModeSelect.class);
            if (localHCAThermostat4.fanmode) {
              localIntent.putExtra("com.advancedquonsettechnology.hcaapp.fanon", true);
            }
            for (;;)
            {
              ObjectDisplay.this.startActivityForResult(localIntent, 1);
              return;
              localIntent.putExtra("com.advancedquonsettechnology.hcaapp.fanoff", true);
            }
          }
        };
        localButton10.setOnClickListener(local14);
      }
      Button localButton11 = (Button)this.layoutview.findViewWithTag("ModeMode");
      this.ModeMode = localButton11;
      if (localButton11 != null)
      {
        final HCAThermostat localHCAThermostat3 = (HCAThermostat)this.hcaObject;
        View.OnClickListener local15 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            Intent localIntent = new Intent(ObjectDisplay.this.getBaseContext(), ModeSelect.class);
            switch (localHCAThermostat3.modemode)
            {
            }
            for (;;)
            {
              ObjectDisplay.this.startActivityForResult(localIntent, 2);
              return;
              localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modeoff", true);
              continue;
              localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modeheat", true);
              continue;
              localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modecool", true);
              continue;
              localIntent.putExtra("com.advancedquonsettechnology.hcaapp.modeauto", true);
            }
          }
        };
        localButton11.setOnClickListener(local15);
      }
      SeekBar localSeekBar2 = (SeekBar)this.layoutview.findViewWithTag("HeatSet");
      if (localSeekBar2 != null)
      {
        final HCAThermostat localHCAThermostat2 = (HCAThermostat)this.hcaObject;
        localSeekBar2.setMax(localHCAThermostat2.heatsethigh - localHCAThermostat2.heatsetlow);
        localSeekBar2.setProgress(localHCAThermostat2.heatset - localHCAThermostat2.heatsetlow);
        SeekBar.OnSeekBarChangeListener local16 = new SeekBar.OnSeekBarChangeListener()
        {
          public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
          {
            int i = paramAnonymousInt + localHCAThermostat2.heatsetlow;
            TextView localTextView = (TextView)ObjectDisplay.this.findViewById(2131427440);
            if (localTextView != null)
            {
              localTextView.setTextColor(HCAApplication.fg_color);
              localTextView.setText("" + i + '°' + "F");
            }
          }
          
          public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
          
          public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            int i = paramAnonymousSeekBar.getProgress() + localHCAThermostat2.heatsetlow;
            localHCAThermostat2.heatset = i;
            String[] arrayOfString = new String[5];
            arrayOfString[0] = "HCAObject";
            arrayOfString[1] = "Device.Thermostat";
            arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
            arrayOfString[3] = "0";
            arrayOfString[4] = ("" + i);
            HCAApplication.serverCommand(arrayOfString, null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localSeekBar2.setOnSeekBarChangeListener(local16);
      }
      SeekBar localSeekBar3 = (SeekBar)this.layoutview.findViewWithTag("CoolSet");
      if (localSeekBar3 != null)
      {
        final HCAThermostat localHCAThermostat1 = (HCAThermostat)this.hcaObject;
        localSeekBar3.setMax(localHCAThermostat1.coolsethigh - localHCAThermostat1.coolsetlow);
        localSeekBar3.setProgress(localHCAThermostat1.coolset - localHCAThermostat1.coolsetlow);
        SeekBar.OnSeekBarChangeListener local17 = new SeekBar.OnSeekBarChangeListener()
        {
          public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
          {
            int i = paramAnonymousInt + localHCAThermostat1.coolsetlow;
            TextView localTextView = (TextView)ObjectDisplay.this.findViewById(2131427448);
            if (localTextView != null)
            {
              localTextView.setTextColor(HCAApplication.fg_color);
              localTextView.setText("" + i + '°' + "F");
            }
          }
          
          public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
          
          public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
          {
            if (HCAApplication.vibration > 0) {
              ((Vibrator)ObjectDisplay.this.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
            }
            int i = paramAnonymousSeekBar.getProgress() + localHCAThermostat1.coolsetlow;
            localHCAThermostat1.coolset = i;
            String[] arrayOfString = new String[5];
            arrayOfString[0] = "HCAObject";
            arrayOfString[1] = "Device.Thermostat";
            arrayOfString[2] = ObjectDisplay.this.hcaObject.getCommandName();
            arrayOfString[3] = "10";
            arrayOfString[4] = ("" + i);
            HCAApplication.serverCommand(arrayOfString, null);
            ObjectDisplay.this.startResponseTimer(ObjectDisplay.this.hcaObject.getObjectId());
          }
        };
        localSeekBar3.setOnSeekBarChangeListener(local17);
      }
      if (this.hcaObject.isThermostat())
      {
        String[] arrayOfString2 = new String[3];
        arrayOfString2[0] = "HCAApp";
        arrayOfString2[1] = "ThermostatState";
        arrayOfString2[2] = this.hcaObject.getCommandName();
        if (HCAApplication.serverCommand(arrayOfString2, null) < 0) {
          break label2170;
        }
        startResponseTimer(this.hcaObject.getObjectId());
      }
      for (;;)
      {
        if (!this.hcaObject.isIRDevice()) {
          break label2185;
        }
        if (HCAApplication._IRKeys == null) {
          HCAApplication._IRKeys = new ArrayList();
        }
        HCAApplication.irdeviceview = (AbsoluteLayout)this.layoutview.findViewById(2131427376);
        String[] arrayOfString1 = new String[3];
        arrayOfString1[0] = "HCAApp";
        arrayOfString1[1] = "IRKeypad";
        arrayOfString1[2] = this.hcaObject.getCommandName();
        if (HCAApplication.serverCommand(arrayOfString1, null) < 0) {
          break label2187;
        }
        startResponseTimer(this.hcaObject.getObjectId());
        return;
        Toast.makeText(getBaseContext(), "No network connection.", 1).show();
        break;
        m = 0;
        break label1247;
        Toast.makeText(getBaseContext(), "No network connection.", 1).show();
      }
    }
    label2187:
    Toast.makeText(getBaseContext(), "No network connection.", 1).show();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131361793, paramMenu);
    paramMenu.getItem(0).setTitle("Disconnect");
    paramMenu.getItem(0).setIcon(17301560);
    paramMenu.getItem(1).setIcon(17301568);
    return true;
  }
  
  protected void onDestroy()
  {
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectDisplay.onDestroy");
    }
    finishActivity(1);
    finishActivity(200);
    super.onDestroy();
  }
  
  public void onHCAUpdate(int paramInt)
  {
    if ((paramInt != -1) && (paramInt != this.hcaObjId)) {}
    label234:
    HCADevice localHCADevice;
    int i;
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              this._spinner.stop();
              showDeviceState();
              if (!this.hcaObject.isProgram()) {
                break;
              }
              if ((this.stateview != null) && (!this.haveStateIcon)) {
                switch (this.hcaObject.getState())
                {
                default: 
                  this.stateview.setBackgroundResource(2131165191);
                }
              }
              for (;;)
              {
                if ((!this.hcaObject.getLayoutName().equals("ProgramOnOff")) && (!this.hcaObject.getLayoutName().equals("ProgramStart"))) {
                  break label234;
                }
                if (this.ProgOn != null) {
                  this.ProgOn.setEnabled(true);
                }
                if (this.ProgStopOn != null) {
                  this.ProgStopOn.setEnabled(true);
                }
                if (this.ProgOff != null) {
                  this.ProgOff.setEnabled(true);
                }
                if (this.ProgStopOff != null) {
                  this.ProgStopOff.setEnabled(true);
                }
                if (this.ProgStart != null) {
                  this.ProgStart.setEnabled(true);
                }
                if (this.ProgStopStart != null) {
                  this.ProgStopStart.setEnabled(true);
                }
                if (this.ProgStop == null) {
                  break;
                }
                this.ProgStop.setEnabled(true);
                return;
                this.stateview.setBackgroundColor(HCAApplication.bg_color);
              }
              switch (this.hcaObject.getState())
              {
              default: 
                if (this.ProgOn != null) {
                  this.ProgOn.setEnabled(false);
                }
                if (this.ProgStopOn != null) {
                  this.ProgStopOn.setEnabled(false);
                }
                if (this.ProgOff != null) {
                  this.ProgOff.setEnabled(false);
                }
                if (this.ProgStopOff != null) {
                  this.ProgStopOff.setEnabled(false);
                }
                if (this.ProgStart != null) {
                  this.ProgStart.setEnabled(false);
                }
                if (this.ProgStopStart != null) {
                  this.ProgStopStart.setEnabled(false);
                }
                break;
              }
            } while (this.ProgStop == null);
            this.ProgStop.setEnabled(true);
            return;
            if (this.ProgOn != null) {
              this.ProgOn.setEnabled(true);
            }
            if (this.ProgStopOn != null) {
              this.ProgStopOn.setEnabled(true);
            }
            if (this.ProgOff != null) {
              this.ProgOff.setEnabled(true);
            }
            if (this.ProgStopOff != null) {
              this.ProgStopOff.setEnabled(true);
            }
            if (this.ProgStart != null) {
              this.ProgStart.setEnabled(true);
            }
            if (this.ProgStopStart != null) {
              this.ProgStopStart.setEnabled(true);
            }
          } while (this.ProgStop == null);
          this.ProgStop.setEnabled(true);
          return;
          if (!this.hcaObject.isThermostat()) {
            break;
          }
          showThermostatState();
        } while (!this.updateThermostatState);
        updateThermostatMode();
        return;
        this.dimview = ((SeekBar)this.layoutview.findViewWithTag("Dim"));
        if (this.dimview != null) {
          this.dimview.setProgress(this.hcaObject.getState());
        }
        boolean bool = this.hcaObject.isDevice();
        localHCADevice = null;
        if (bool) {
          localHCADevice = (HCADevice)this.hcaObject;
        }
      } while ((localHCADevice == null) || (localHCADevice.getButtonCount() <= 0));
      i = 0;
    } while (i >= localHCADevice.getButtonCount());
    switch (i)
    {
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    i++;
                    break;
                  } while (this.Bulb1 == null);
                  if (localHCADevice.getButtonState(i) == 0)
                  {
                    this.Bulb1.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                    this.Bulb1.setTextColor(-1);
                  }
                  for (;;)
                  {
                    this.Bulb1.setText(localHCADevice.getButtonName(i));
                    break;
                    this.Bulb1.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                    this.Bulb1.setTextColor(-16777216);
                  }
                } while (this.Bulb2 == null);
                if (localHCADevice.getButtonState(i) == 0)
                {
                  this.Bulb2.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                  this.Bulb2.setTextColor(-1);
                }
                for (;;)
                {
                  this.Bulb2.setText(localHCADevice.getButtonName(i));
                  break;
                  this.Bulb2.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                  this.Bulb2.setTextColor(-16777216);
                }
              } while (this.Bulb3 == null);
              if (localHCADevice.getButtonState(i) == 0)
              {
                this.Bulb3.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
                this.Bulb3.setTextColor(-1);
              }
              for (;;)
              {
                this.Bulb3.setText(localHCADevice.getButtonName(i));
                break;
                this.Bulb3.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
                this.Bulb3.setTextColor(-16777216);
              }
            } while (this.Bulb4 == null);
            if (localHCADevice.getButtonState(i) == 0)
            {
              this.Bulb4.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
              this.Bulb4.setTextColor(-1);
            }
            for (;;)
            {
              this.Bulb4.setText(localHCADevice.getButtonName(i));
              break;
              this.Bulb4.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
              this.Bulb4.setTextColor(-16777216);
            }
          } while (this.Bulb5 == null);
          if (localHCADevice.getButtonState(i) == 0)
          {
            this.Bulb5.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
            this.Bulb5.setTextColor(-1);
          }
          for (;;)
          {
            this.Bulb5.setText(localHCADevice.getButtonName(i));
            break;
            this.Bulb5.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
            this.Bulb5.setTextColor(-16777216);
          }
        } while (this.Bulb6 == null);
        if (localHCADevice.getButtonState(i) == 0)
        {
          this.Bulb6.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
          this.Bulb6.setTextColor(-1);
        }
        for (;;)
        {
          this.Bulb6.setText(localHCADevice.getButtonName(i));
          break;
          this.Bulb6.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
          this.Bulb6.setTextColor(-16777216);
        }
      } while (this.Bulb7 == null);
      if (localHCADevice.getButtonState(i) == 0)
      {
        this.Bulb7.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
        this.Bulb7.setTextColor(-1);
      }
      for (;;)
      {
        this.Bulb7.setText(localHCADevice.getButtonName(i));
        break;
        this.Bulb7.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
        this.Bulb7.setTextColor(-16777216);
      }
    } while (this.Bulb8 == null);
    if (localHCADevice.getButtonState(i) == 0)
    {
      this.Bulb8.getBackground().setColorFilter(-12303292, PorterDuff.Mode.SRC_ATOP);
      this.Bulb8.setTextColor(-1);
    }
    for (;;)
    {
      this.Bulb8.setText(localHCADevice.getButtonName(i));
      break;
      this.Bulb8.getBackground().setColorFilter(65280, PorterDuff.Mode.SRC_ATOP);
      this.Bulb8.setTextColor(-16777216);
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (HCAApplication.vibration > 0) {
      ((Vibrator)getSystemService("vibrator")).vibrate(HCAApplication.vibration);
    }
    if (paramMenuItem.getItemId() == 2131427493) {
      startActivity(new Intent().setClass(this, HelpDevice.class));
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
    HCAApplication.visibleUpdateListener.add(this);
    if (this.layoutview != null) {
      this.layoutview.setBackgroundColor(HCAApplication.bg_color);
    }
  }
  
  protected void onStop()
  {
    super.onStop();
    this._spinner.stop();
    HCAApplication.visibleUpdateListener.remove(this);
  }
  
  void setThermostatMode(int paramInt)
  {
    String[] arrayOfString;
    if (HCAApplication.svrversion >= 13)
    {
      arrayOfString = new String[5];
      arrayOfString[0] = "HCAApp";
      arrayOfString[1] = "ThermostatChange";
      arrayOfString[2] = this.hcaObject.getCommandName();
      arrayOfString[3] = "2";
      arrayOfString[4] = String.valueOf(paramInt);
    }
    for (;;)
    {
      this.updateThermostatState = true;
      HCAApplication.serverCommand(arrayOfString, null);
      startResponseTimer(this.hcaObject.getObjectId());
      return;
      arrayOfString = new String[5];
      arrayOfString[0] = "HCAObject";
      arrayOfString[1] = "Device.Thermostat";
      arrayOfString[2] = this.hcaObject.getCommandName();
      arrayOfString[3] = "1";
      arrayOfString[4] = String.valueOf(paramInt);
    }
  }
  
  void showDeviceState()
  {
    String str1;
    String str3;
    if (this.hcaObject.getState() == 0)
    {
      str1 = "_off";
      String str2 = this.hcaObject.getIconName();
      int i = getBaseContext().getResources().getIdentifier(str2 + str1, "drawable", "com.advancedquonsettechnology.hcaapp");
      if (i == 0)
      {
        this.haveStateIcon = false;
        i = getBaseContext().getResources().getIdentifier(str2, "drawable", "com.advancedquonsettechnology.hcaapp");
      }
      if (i == 0)
      {
        if (this.hcaObject.getKind() != 1) {
          break label342;
        }
        str3 = "program2";
        label104:
        i = getBaseContext().getResources().getIdentifier(str3 + str1, "drawable", "com.advancedquonsettechnology.hcaapp");
      }
      if (this.iconView != null) {
        this.iconView.setImageResource(i);
      }
      if ((this.stateview != null) && (!this.haveStateIcon)) {}
      switch (this.hcaObject.getState())
      {
      default: 
        int j = 16776960 + 16777216 * (2 * this.hcaObject.getState());
        this.stateview.setBackgroundColor(j);
        label232:
        if (this.suspenseView != null) {
          switch (this.hcaObject.getSuspended())
          {
          }
        }
        break;
      }
    }
    for (;;)
    {
      if (this.nameView != null)
      {
        this.nameView.setText(this.hcaObject.getName());
        this.nameView.setTextColor(HCAApplication.fg_color);
      }
      if (this.fnameView != null)
      {
        this.fnameView.setText(this.hcaObject.getFolderName());
        this.fnameView.setTextColor(HCAApplication.fg_color);
      }
      return;
      str1 = "_on";
      break;
      label342:
      if (this.hcaObject.getKind() == 4)
      {
        str3 = "room_unoccupied";
        break label104;
      }
      if (this.hcaObject.getKind() == 2)
      {
        str3 = "group2";
        break label104;
      }
      if (this.hcaObject.getKind() == 3)
      {
        str3 = "keypad4";
        break label104;
      }
      str3 = "light_bulb";
      break label104;
      this.stateview.setBackgroundColor(HCAApplication.bg_color);
      break label232;
      this.stateview.setBackgroundResource(2131165191);
      break label232;
      this.suspenseView.setBackgroundColor(HCAApplication.bg_color);
      continue;
      this.suspenseView.setBackgroundResource(2131165185);
      continue;
      this.suspenseView.setBackgroundResource(2131165187);
    }
  }
  
  void showThermostatState()
  {
    if (!this.hcaObject.isThermostat()) {
      return;
    }
    HCAThermostat localHCAThermostat = (HCAThermostat)this.hcaObject;
    if (HCAApplication.svrversion >= 12)
    {
      if ((localHCAThermostat.errorText != null) && (localHCAThermostat.errorText.length() > 0)) {
        Toast.makeText(getBaseContext(), "Thermostat: " + localHCAThermostat.errorText, 1).show();
      }
      TextView localTextView10 = (TextView)this.layoutview.findViewById(2131427463);
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = this.hcaObject.getFolderName();
      arrayOfObject1[1] = this.hcaObject.getName();
      localTextView10.setText(String.format("%s %s", arrayOfObject1));
      TextView localTextView11 = (TextView)this.layoutview.findViewById(2131427451);
      localTextView11.setTextColor(HCAApplication.fg_color);
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = Integer.valueOf(localHCAThermostat.currenttemp);
      arrayOfObject2[1] = Character.valueOf('°');
      String str;
      label197:
      TextView localTextView12;
      label286:
      TextView localTextView13;
      NumberPicker localNumberPicker1;
      label370:
      TextView localTextView14;
      NumberPicker localNumberPicker2;
      if (localHCAThermostat.units != null)
      {
        str = localHCAThermostat.units;
        arrayOfObject2[2] = str;
        localTextView11.setText(String.format("Temp: %d%c%s", arrayOfObject2));
        localTextView12 = (TextView)this.layoutview.findViewById(2131427452);
        if (localHCAThermostat.currenthumid <= 0) {
          break label717;
        }
        localTextView12.setTextColor(HCAApplication.fg_color);
        localTextView12.setText("Humidity: " + localHCAThermostat.currenthumid + "%");
        localTextView12.setVisibility(0);
        localTextView13 = (TextView)this.layoutview.findViewById(2131427460);
        localNumberPicker1 = (NumberPicker)this.layoutview.findViewById(2131427458);
        localNumberPicker1.setDescendantFocusability(393216);
        if (localHCAThermostat.heatset <= 0) {
          break label726;
        }
        localNumberPicker1.setMinValue(localHCAThermostat.heatsetlow);
        localNumberPicker1.setMaxValue(localHCAThermostat.heatsethigh);
        localNumberPicker1.setValue(localHCAThermostat.heatset);
        localNumberPicker1.setVisibility(0);
        localTextView13.setVisibility(0);
        localNumberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
          public void onValueChange(NumberPicker paramAnonymousNumberPicker, int paramAnonymousInt1, int paramAnonymousInt2)
          {
            ObjectDisplay.this.heatSetpointPending = true;
            if (ObjectDisplay.this.setpointTimer != null) {
              ObjectDisplay.this.setpointTimer.cancel();
            }
            ObjectDisplay.this.setpointTimer = new Timer();
            ObjectDisplay.this.setpointTimer.schedule(new TimerTask()
            {
              public void run()
              {
                ObjectDisplay.this.runOnUiThread(new Runnable()
                {
                  public void run()
                  {
                    ObjectDisplay.this.updateThermostatSetpoint();
                  }
                });
              }
            }, 1200L);
          }
        });
        localTextView14 = (TextView)this.layoutview.findViewById(2131427461);
        localNumberPicker2 = (NumberPicker)this.layoutview.findViewById(2131427459);
        localNumberPicker2.setDescendantFocusability(393216);
        if (localHCAThermostat.coolset <= 0) {
          break label741;
        }
        localNumberPicker2.setMinValue(localHCAThermostat.coolsetlow);
        localNumberPicker2.setMaxValue(localHCAThermostat.coolsethigh);
        localNumberPicker2.setValue(localHCAThermostat.coolset);
        localNumberPicker2.setVisibility(0);
        localTextView14.setVisibility(0);
      }
      Button localButton1;
      Button localButton2;
      Button localButton3;
      Button localButton4;
      for (;;)
      {
        localNumberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
          public void onValueChange(NumberPicker paramAnonymousNumberPicker, int paramAnonymousInt1, int paramAnonymousInt2)
          {
            ObjectDisplay.this.coolSetpointPending = true;
            if (ObjectDisplay.this.setpointTimer != null) {
              ObjectDisplay.this.setpointTimer.cancel();
            }
            ObjectDisplay.this.setpointTimer = new Timer();
            ObjectDisplay.this.setpointTimer.schedule(new TimerTask()
            {
              public void run()
              {
                ObjectDisplay.this.runOnUiThread(new Runnable()
                {
                  public void run()
                  {
                    ObjectDisplay.this.updateThermostatSetpoint();
                  }
                });
              }
            }, 1200L);
          }
        });
        localButton1 = (Button)this.layoutview.findViewById(2131427454);
        localButton2 = (Button)this.layoutview.findViewById(2131427456);
        localButton3 = (Button)this.layoutview.findViewById(2131427457);
        localButton4 = (Button)this.layoutview.findViewById(2131427455);
        localButton1.getBackground().clearColorFilter();
        localButton2.getBackground().clearColorFilter();
        localButton3.getBackground().clearColorFilter();
        localButton4.getBackground().clearColorFilter();
        localButton1.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ObjectDisplay.this.setThermostatMode(0);
          }
        });
        localButton2.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ObjectDisplay.this.setThermostatMode(1);
          }
        });
        localButton3.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ObjectDisplay.this.setThermostatMode(2);
          }
        });
        localButton4.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ObjectDisplay.this.setThermostatMode(3);
          }
        });
        if (localHCAThermostat.modemode < 0) {
          break label816;
        }
        localButton1.setVisibility(0);
        localButton2.setVisibility(0);
        localButton3.setVisibility(0);
        localButton4.setVisibility(0);
        switch (localHCAThermostat.modemode)
        {
        default: 
          return;
        case 0: 
          localButton1.getBackground().setColorFilter(new LightingColorFilter(-1, -5636096));
          return;
          str = "F";
          break label197;
          label717:
          localTextView12.setVisibility(4);
          break label286;
          label726:
          localNumberPicker1.setVisibility(4);
          localTextView13.setVisibility(4);
          break label370;
          label741:
          localNumberPicker2.setVisibility(4);
          localTextView14.setVisibility(4);
        }
      }
      localButton2.getBackground().setColorFilter(new LightingColorFilter(-1, -5636096));
      return;
      localButton3.getBackground().setColorFilter(new LightingColorFilter(-1, -5636096));
      return;
      localButton4.getBackground().setColorFilter(new LightingColorFilter(-1, -5636096));
      return;
      label816:
      localButton1.setVisibility(4);
      localButton2.setVisibility(4);
      localButton3.setVisibility(4);
      localButton4.setVisibility(4);
      return;
    }
    TextView localTextView1 = (TextView)this.layoutview.findViewById(2131427436);
    localTextView1.setTextColor(HCAApplication.fg_color);
    localTextView1.setText("Temp is " + localHCAThermostat.currenttemp + '°' + "F");
    TextView localTextView2 = (TextView)this.layoutview.findViewById(2131427333);
    label940:
    TextView localTextView3;
    if (localTextView2 != null)
    {
      localTextView2.setTextColor(HCAApplication.fg_color);
      if (localHCAThermostat.fanmode) {
        localTextView2.setText("Fan is On");
      }
    }
    else
    {
      localTextView3 = (TextView)this.layoutview.findViewById(2131427419);
      if (localTextView3 != null)
      {
        localTextView3.setTextColor(HCAApplication.fg_color);
        switch (localHCAThermostat.modemode)
        {
        }
      }
    }
    for (;;)
    {
      TextView localTextView4 = (TextView)this.layoutview.findViewById(2131427440);
      if (localTextView4 != null)
      {
        localTextView4.setTextColor(HCAApplication.fg_color);
        localTextView4.setText("" + localHCAThermostat.heatset + '°' + "F");
      }
      TextView localTextView5 = (TextView)this.layoutview.findViewById(2131427448);
      if (localTextView5 != null)
      {
        localTextView5.setTextColor(HCAApplication.fg_color);
        localTextView5.setText("" + localHCAThermostat.coolset + '°' + "F");
      }
      TextView localTextView6 = (TextView)this.layoutview.findViewById(2131427449);
      if (localTextView6 != null)
      {
        localTextView6.setTextColor(HCAApplication.fg_color);
        localTextView6.setText("" + localHCAThermostat.coolsetlow + '°' + "F");
      }
      TextView localTextView7 = (TextView)this.layoutview.findViewById(2131427445);
      if (localTextView7 != null)
      {
        localTextView7.setTextColor(HCAApplication.fg_color);
        localTextView7.setText("" + localHCAThermostat.coolsethigh + '°' + "F");
      }
      TextView localTextView8 = (TextView)this.layoutview.findViewById(2131427441);
      if (localTextView8 != null)
      {
        localTextView8.setTextColor(HCAApplication.fg_color);
        localTextView8.setText("" + localHCAThermostat.heatsetlow + '°' + "F");
      }
      TextView localTextView9 = (TextView)this.layoutview.findViewById(2131427437);
      if (localTextView9 != null)
      {
        localTextView9.setTextColor(HCAApplication.fg_color);
        localTextView9.setText("" + localHCAThermostat.heatsethigh + '°' + "F");
      }
      this.dimview = ((SeekBar)this.layoutview.findViewWithTag("HeatSet"));
      if (this.dimview != null)
      {
        int k = localHCAThermostat.heatsethigh - localHCAThermostat.heatsetlow;
        this.dimview.setMax(k);
        int m = localHCAThermostat.heatset;
        this.dimview.setProgress(m - localHCAThermostat.heatsetlow);
      }
      this.dimview = ((SeekBar)this.layoutview.findViewWithTag("CoolSet"));
      if (this.dimview == null) {
        break;
      }
      int i = localHCAThermostat.coolsethigh - localHCAThermostat.coolsetlow;
      this.dimview.setMax(i);
      int j = localHCAThermostat.coolset;
      this.dimview.setProgress(j - localHCAThermostat.coolsetlow);
      return;
      localTextView2.setText("Fan is Off");
      break label940;
      localTextView3.setText("Mode is Off");
      continue;
      localTextView3.setText("Mode is Off");
      continue;
      localTextView3.setText("Heat Mode");
      continue;
      localTextView3.setText("Cool Mode");
      continue;
      localTextView3.setText("Auto Mode");
    }
  }
  
  void updateThermostatMode()
  {
    this.updateThermostatState = false;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = "HCAApp";
    arrayOfString[1] = "ThermostatState";
    arrayOfString[2] = this.hcaObject.getCommandName();
    HCAApplication.serverCommand(arrayOfString, null);
    startResponseTimer(this.hcaObject.getObjectId());
  }
  
  void updateThermostatSetpoint()
  {
    HCAThermostat localHCAThermostat = (HCAThermostat)this.hcaObject;
    String[] arrayOfString;
    if (this.heatSetpointPending)
    {
      localNumberPicker2 = (NumberPicker)this.layoutview.findViewById(2131427458);
      localHCAThermostat.heatset = localNumberPicker2.getValue();
      if (HCAApplication.svrversion >= 13)
      {
        arrayOfString = new String[5];
        arrayOfString[0] = "HCAApp";
        arrayOfString[1] = "ThermostatChange";
        arrayOfString[2] = this.hcaObject.getCommandName();
        arrayOfString[3] = "1";
        arrayOfString[4] = String.valueOf(localNumberPicker2.getValue());
        this.heatSetpointPending = false;
        HCAApplication.serverCommand(arrayOfString, null);
        startResponseTimer(this.hcaObject.getObjectId());
      }
    }
    while (!this.coolSetpointPending) {
      for (;;)
      {
        NumberPicker localNumberPicker2;
        return;
        arrayOfString = new String[5];
        arrayOfString[0] = "HCAObject";
        arrayOfString[1] = "Device.Thermostat";
        arrayOfString[2] = this.hcaObject.getCommandName();
        arrayOfString[3] = "0";
        arrayOfString[4] = String.valueOf(localNumberPicker2.getValue());
      }
    }
    NumberPicker localNumberPicker1 = (NumberPicker)this.layoutview.findViewById(2131427459);
    localHCAThermostat.coolset = localNumberPicker1.getValue();
    if (HCAApplication.svrversion >= 13)
    {
      arrayOfString = new String[5];
      arrayOfString[0] = "HCAApp";
      arrayOfString[1] = "ThermostatChange";
      arrayOfString[2] = this.hcaObject.getCommandName();
      arrayOfString[3] = "7";
      arrayOfString[4] = String.valueOf(localNumberPicker1.getValue());
    }
    for (;;)
    {
      this.coolSetpointPending = false;
      break;
      arrayOfString = new String[5];
      arrayOfString[0] = "HCAObject";
      arrayOfString[1] = "Device.Thermostat";
      arrayOfString[2] = this.hcaObject.getCommandName();
      arrayOfString[3] = "10";
      arrayOfString[4] = String.valueOf(localNumberPicker1.getValue());
    }
  }
}
