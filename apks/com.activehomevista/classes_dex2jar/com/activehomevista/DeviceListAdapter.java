package com.activehomevista;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

class DeviceListAdapter
  extends BaseAdapter
{
  private final Boolean darkTheme;
  private final List<Device> devices;
  private final LayoutInflater inflater;
  
  public DeviceListAdapter(Activity paramActivity, List<Device> paramList)
  {
    this.inflater = paramActivity.getLayoutInflater();
    this.devices = paramList;
    this.darkTheme = Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(paramActivity).getString(paramActivity.getString(2131099729), paramActivity.getString(2131099705)).equals(paramActivity.getString(2131099705)));
  }
  
  public int getCount()
  {
    return this.devices.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.devices.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  @SuppressLint({"SetTextI18n"})
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    ActiveHomeVista localActiveHomeVista = (ActiveHomeVista)paramViewGroup.getContext();
    Device localDevice = (Device)this.devices.get(paramInt);
    DeviceViewCache localDeviceViewCache;
    label115:
    label141:
    TextView localTextView;
    if (localView == null)
    {
      localView = this.inflater.inflate(2130903068, null);
      localDeviceViewCache = new DeviceViewCache(localView);
      localView.setTag(localDeviceViewCache);
      localDeviceViewCache.getMainTextView().setText(localDevice.getName());
      if (((ListView)paramViewGroup).getCheckedItemPosition() != paramInt) {
        break label362;
      }
      if (!this.darkTheme.booleanValue()) {
        break label341;
      }
      localView.setBackgroundColor(localView.getContext().getResources().getColor(2131492865));
      localDeviceViewCache.getMainTextView().setTextColor(Color.parseColor("white"));
      localDeviceViewCache.getSubTextView().setTextColor(Color.parseColor("white"));
      localTextView = localDeviceViewCache.getSubTextView();
      if ((!localActiveHomeVista.getDeviceOnStatus(localDevice)) && (!localActiveHomeVista.getDeviceOnStatusExtended(localDevice))) {
        break label485;
      }
      if ((localDevice.getDeviceType() == 13) || (localDevice.getDeviceType() == 17) || (localDevice.getDeviceType() == 44) || (localDevice.getDeviceType() == 46) || (localDevice.getDeviceType() == 45) || (localDevice.getDeviceType() == 47) || (localDevice.getDeviceOnlyOnCmdCap())) {
        break label451;
      }
      if (!localDevice.getDeviceDimsCap()) {
        break label436;
      }
      localTextView.setText(localActiveHomeVista.getString(2131099744) + ", " + localActiveHomeVista.getString(2131099708) + ": " + localActiveHomeVista.getDeviceDimStatus(localDevice) + "%");
      label304:
      if (localDevice.getOnBitmap() == null) {
        break label461;
      }
      localDeviceViewCache.getImageView().setImageBitmap(localDevice.getOnBitmap());
    }
    for (;;)
    {
      return localView;
      localDeviceViewCache = (DeviceViewCache)localView.getTag();
      break;
      label341:
      localView.setBackgroundColor(localView.getContext().getResources().getColor(2131492864));
      break label115;
      label362:
      localView.setBackgroundColor(0);
      if (this.darkTheme.booleanValue())
      {
        localDeviceViewCache.getMainTextView().setTextColor(Color.parseColor("white"));
        localDeviceViewCache.getSubTextView().setTextColor(Color.parseColor("white"));
        break label141;
      }
      localDeviceViewCache.getMainTextView().setTextColor(Color.parseColor("black"));
      localDeviceViewCache.getSubTextView().setTextColor(Color.parseColor("black"));
      break label141;
      label436:
      localTextView.setText(localActiveHomeVista.getString(2131099744));
      break label304;
      label451:
      localTextView.setText("");
      break label304;
      label461:
      if (localDevice.getOffBitmap() != null)
      {
        localDeviceViewCache.getImageView().setImageBitmap(localDevice.getOffBitmap());
        return localView;
        label485:
        if ((localDevice.getDeviceType() != 13) && (localDevice.getDeviceType() != 17) && (localDevice.getDeviceType() != 44) && (localDevice.getDeviceType() != 46) && (localDevice.getDeviceType() != 45) && (localDevice.getDeviceType() != 47) && (!localDevice.getDeviceOnlyOnCmdCap())) {
          if (localDevice.getDeviceDimsCap()) {
            localTextView.setText(localActiveHomeVista.getString(2131099742) + ", " + localActiveHomeVista.getString(2131099708) + ": " + localActiveHomeVista.getDeviceDimStatus(localDevice) + "%");
          }
        }
        while (localDevice.getOffBitmap() != null)
        {
          localDeviceViewCache.getImageView().setImageBitmap(localDevice.getOffBitmap());
          return localView;
          localTextView.setText(localActiveHomeVista.getString(2131099742));
          continue;
          localTextView.setText("");
        }
      }
    }
  }
  
  public class DeviceViewCache
  {
    private final View baseView;
    private ImageView imageView;
    private TextView mainTextView;
    private TextView subTextView;
    
    public DeviceViewCache(View paramView)
    {
      this.baseView = paramView;
    }
    
    public ImageView getImageView()
    {
      if (this.imageView == null) {
        this.imageView = ((ImageView)this.baseView.findViewById(2131558494));
      }
      return this.imageView;
    }
    
    public TextView getMainTextView()
    {
      if (this.mainTextView == null) {
        this.mainTextView = ((TextView)this.baseView.findViewById(2131558495));
      }
      return this.mainTextView;
    }
    
    public TextView getSubTextView()
    {
      if (this.subTextView == null) {
        this.subTextView = ((TextView)this.baseView.findViewById(2131558496));
      }
      return this.subTextView;
    }
  }
}
