package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class HCABaseAdapter
  extends ArrayAdapter<HCABase>
{
  Activity _activity;
  String _displayName;
  List<HCABase> _objects;
  int _resource;
  Spinner _spinner;
  boolean _twoPartNames;
  
  public HCABaseAdapter(Context paramContext, int paramInt, List<HCABase> paramList, String paramString, boolean paramBoolean, Spinner paramSpinner)
  {
    super(paramContext, paramInt, paramList);
    this._resource = paramInt;
    this._activity = ((Activity)paramContext);
    this._objects = paramList;
    this._displayName = paramString;
    this._twoPartNames = paramBoolean;
    this._spinner = paramSpinner;
  }
  
  private void doClickBehavior(View paramView, boolean paramBoolean)
  {
    if (HCAApplication.vibration > 0) {
      ((Vibrator)this._activity.getSystemService("vibrator")).vibrate(HCAApplication.vibration);
    }
    int i = ((Integer)paramView.getTag()).intValue();
    HCABase localHCABase = (HCABase)this._objects.get(i);
    if (paramBoolean) {}
    for (int j = localHCABase.getLongTapAction(); localHCABase.isDisplay(); j = localHCABase.getShortTapAction())
    {
      showDisplayIcons((HCADisplay)localHCABase);
      return;
    }
    String[] arrayOfString;
    switch (j)
    {
    default: 
      return;
    case 1: 
      int k = localHCABase.getKind();
      arrayOfString = null;
      switch (k)
      {
      }
      break;
    }
    while (arrayOfString != null)
    {
      if (HCAApplication.serverCommand(arrayOfString, null) < 0) {
        break label342;
      }
      if (this._spinner == null) {
        break;
      }
      int m = Integer.parseInt(HCAApplication.mIPTimeout);
      if (localHCABase.isThermostat()) {
        m *= 10;
      }
      this._spinner.start("...", m, localHCABase.getObjectId());
      return;
      if (localHCABase.isInert()) {
        break;
      }
      showDeviceControlPage(localHCABase);
      return;
      if (localHCABase.getState() > 0)
      {
        arrayOfString = localHCABase.getOffCommand();
      }
      else
      {
        arrayOfString = localHCABase.getOnCommand();
        continue;
        if (localHCABase.getState() > 0)
        {
          arrayOfString = ((HCAProgram)localHCABase).getStopCommand();
        }
        else
        {
          arrayOfString = ((HCAProgram)localHCABase).getStartCommand();
          continue;
          showDeviceControlPage(localHCABase);
          arrayOfString = null;
        }
      }
    }
    label342:
    Toast.makeText(getContext(), "No network connection.", 1).show();
  }
  
  private void showDeviceControlPage(HCABase paramHCABase)
  {
    Intent localIntent = new Intent(getContext(), ObjectDisplay.class);
    localIntent.putExtra("com.advancedquonsettechnology.hcaapp.objectid", paramHCABase.getObjectId());
    this._activity.startActivityForResult(localIntent, 4);
  }
  
  private void showDisplayIcons(HCADisplay paramHCADisplay)
  {
    if (paramHCADisplay.isHTMLDisplay()) {}
    for (Object localObject = HTMLDisplay.class;; localObject = ObjectListDisplay.class)
    {
      Intent localIntent = new Intent(getContext(), (Class)localObject);
      localIntent.putExtra("com.advancedquonsettechnology.hcaapp.foldername", paramHCADisplay.getName());
      this._activity.startActivityForResult(localIntent, 5);
      return;
    }
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    RelativeLayout localRelativeLayout;
    HCABase localHCABase;
    TextView localTextView;
    String str1;
    label131:
    String str3;
    if (paramView == null)
    {
      localRelativeLayout = new RelativeLayout(getContext());
      ((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(this._resource, localRelativeLayout, true);
      localRelativeLayout.setTag(Integer.valueOf(paramInt));
      localHCABase = (HCABase)this._objects.get(paramInt);
      localHCABase.setCurrentDisplay(this._displayName);
      localTextView = (TextView)localRelativeLayout.findViewById(2131427338);
      ImageView localImageView = (ImageView)localRelativeLayout.findViewById(2131427341);
      ((RelativeLayout)localRelativeLayout.findViewWithTag("State"));
      ((RelativeLayout)localRelativeLayout.findViewWithTag("Suspense"));
      if (localHCABase.getIconState() != 0) {
        break label490;
      }
      str1 = "_off";
      String str2 = localHCABase.getIconName();
      if ((localHCABase.isDisplay()) && (HCAApplication.serverProtocol.intValue() <= 10)) {
        str2 = "room_" + str2;
      }
      int i = getContext().getResources().getIdentifier(str2 + str1, "drawable", "com.advancedquonsettechnology.hcaapp");
      if (i == 0)
      {
        i = getContext().getResources().getIdentifier(str2, "drawable", "com.advancedquonsettechnology.hcaapp");
        if ((i == 0) && (localHCABase.isDisplay()) && (HCAApplication.serverProtocol.intValue() <= 10))
        {
          String str4 = localHCABase.getIconName();
          i = getContext().getResources().getIdentifier(str4, "drawable", "com.advancedquonsettechnology.hcaapp");
        }
      }
      if (i == 0)
      {
        if (localHCABase.getKind() != 1) {
          break label498;
        }
        str3 = "program2";
        label313:
        i = getContext().getResources().getIdentifier(str3 + str1, "drawable", "com.advancedquonsettechnology.hcaapp");
      }
      localImageView.setImageResource(i);
      if (!localHCABase.isDevice()) {
        break label557;
      }
    }
    label490:
    label498:
    label557:
    for (boolean bool = this._twoPartNames;; bool = false)
    {
      localTextView.setText(localHCABase.getCurrentIconLabel(bool));
      int j = HCAApplication.fg_color;
      if (localHCABase.getState() != 0) {
        j = HCAApplication.folder_on_color;
      }
      if (!localHCABase.isDisplay())
      {
        if (localHCABase.getSuspended() == 2) {
          j = HCAApplication.suspend_mode_bg_color;
        }
        if (localHCABase.getInErrorState() != 0) {
          j = HCAApplication.suspend_user_bg_color;
        }
      }
      localTextView.setTextColor(j);
      View.OnLongClickListener local1 = new View.OnLongClickListener()
      {
        public boolean onLongClick(View paramAnonymousView)
        {
          HCABaseAdapter.this.doClickBehavior(paramAnonymousView, true);
          return true;
        }
      };
      localRelativeLayout.setOnLongClickListener(local1);
      View.OnClickListener local2 = new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          HCABaseAdapter.this.doClickBehavior(paramAnonymousView, false);
        }
      };
      localRelativeLayout.setOnClickListener(local2);
      return localRelativeLayout;
      localRelativeLayout = (RelativeLayout)paramView;
      break;
      str1 = "_on";
      break label131;
      if (localHCABase.getKind() == 4)
      {
        str3 = "room_unoccupied";
        break label313;
      }
      if (localHCABase.getKind() == 2)
      {
        str3 = "group2";
        break label313;
      }
      if (localHCABase.getKind() == 3)
      {
        str3 = "keypad4";
        break label313;
      }
      str3 = "light_bulb";
      break label313;
    }
  }
}
