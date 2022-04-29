package com.activehomevista;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Spinner;

public class ConfigureWidget
  extends Activity
{
  private static final int APPWIDGET_HOST_ID = 10101;
  private static final String PREFIX_DEVICE_CODE = "devicecode";
  private static final String PREFIX_HOUSE_CODE = "housecode";
  private static final String PREFIX_STATUS_INDICATION_COLOR = "coloredborder";
  private static final String PREFS_NAME = "com.activehomevista.ActiveHomeVistaWidgetProvider";
  private CheckBox checkbox_status_indication_color;
  private int mAppWidgetId = 0;
  private final View.OnClickListener mOnClickCancelListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      ConfigureWidget.this.setResult(0);
      if (ConfigureWidget.this.mAppWidgetId != 0) {
        new AppWidgetHost(ConfigureWidget.this, 10101).deleteAppWidgetId(ConfigureWidget.this.mAppWidgetId);
      }
      ConfigureWidget.this.finish();
    }
  };
  private final View.OnClickListener mOnClickOkListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      ConfigureWidget localConfigureWidget = ConfigureWidget.this;
      ConfigureWidget.saveHouseCode(localConfigureWidget, ConfigureWidget.this.mAppWidgetId, ConfigureWidget.this.spinner_house_code.getSelectedItem().toString());
      ConfigureWidget.saveDeviceCode(localConfigureWidget, ConfigureWidget.this.mAppWidgetId, ConfigureWidget.this.spinner_device_code.getSelectedItem().toString());
      ConfigureWidget.saveStatusIndicationColor(localConfigureWidget, ConfigureWidget.this.mAppWidgetId, ConfigureWidget.this.checkbox_status_indication_color.isChecked());
      ActiveHomeVistaWidgetProvider.refreshWidget(localConfigureWidget, ConfigureWidget.this.mAppWidgetId);
      Intent localIntent = new Intent();
      localIntent.putExtra("appWidgetId", ConfigureWidget.this.mAppWidgetId);
      ConfigureWidget.this.setResult(-1, localIntent);
      ConfigureWidget.this.finish();
    }
  };
  private Spinner spinner_device_code;
  private Spinner spinner_house_code;
  
  public ConfigureWidget() {}
  
  public static String loadDeviceCode(Context paramContext, int paramInt)
  {
    if (paramInt != 0)
    {
      String str = paramContext.getSharedPreferences("com.activehomevista.ActiveHomeVistaWidgetProvider", 0).getString("devicecode" + paramInt, null);
      if (str != null) {
        return str;
      }
      return paramContext.getString(2131099702);
    }
    return paramContext.getString(2131099702);
  }
  
  public static String loadHouseCode(Context paramContext, int paramInt)
  {
    if (paramInt != 0)
    {
      String str = paramContext.getSharedPreferences("com.activehomevista.ActiveHomeVistaWidgetProvider", 0).getString("housecode" + paramInt, null);
      if (str != null) {
        return str;
      }
      return paramContext.getString(2131099703);
    }
    return paramContext.getString(2131099703);
  }
  
  public static boolean loadStatusIndicationColor(Context paramContext, int paramInt)
  {
    boolean bool = true;
    if (paramInt != 0) {
      bool = paramContext.getSharedPreferences("com.activehomevista.ActiveHomeVistaWidgetProvider", 0).getBoolean("coloredborder" + paramInt, bool);
    }
    return bool;
  }
  
  public static void removeAll(Context paramContext, int paramInt)
  {
    if (paramInt != 0)
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.activehomevista.ActiveHomeVistaWidgetProvider", 0).edit();
      localEditor.remove("housecode" + paramInt);
      localEditor.remove("devicecode" + paramInt);
      localEditor.remove("coloredborder" + paramInt);
      localEditor.commit();
    }
  }
  
  private static void saveDeviceCode(Context paramContext, int paramInt, String paramString)
  {
    if (paramInt != 0)
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.activehomevista.ActiveHomeVistaWidgetProvider", 0).edit();
      localEditor.putString("devicecode" + paramInt, paramString);
      localEditor.commit();
    }
  }
  
  private static void saveHouseCode(Context paramContext, int paramInt, String paramString)
  {
    if (paramInt != 0)
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.activehomevista.ActiveHomeVistaWidgetProvider", 0).edit();
      localEditor.putString("housecode" + paramInt, paramString);
      localEditor.commit();
    }
  }
  
  private static void saveStatusIndicationColor(Context paramContext, int paramInt, boolean paramBoolean)
  {
    if (paramInt != 0)
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.activehomevista.ActiveHomeVistaWidgetProvider", 0).edit();
      localEditor.putBoolean("coloredborder" + paramInt, paramBoolean);
      localEditor.commit();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    ActiveHomeVista.updateTheme(PreferenceManager.getDefaultSharedPreferences(this), this);
    super.onCreate(paramBundle);
    setResult(0);
    setContentView(2130903066);
    this.spinner_house_code = ((Spinner)findViewById(2131558485));
    this.spinner_device_code = ((Spinner)findViewById(2131558486));
    this.checkbox_status_indication_color = ((CheckBox)findViewById(2131558487));
    findViewById(2131558489).setOnClickListener(this.mOnClickOkListener);
    findViewById(2131558488).setOnClickListener(this.mOnClickCancelListener);
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null) {
      this.mAppWidgetId = localBundle.getInt("appWidgetId", 0);
    }
    if (this.mAppWidgetId == 0) {
      finish();
    }
    this.spinner_house_code.setSelection(loadHouseCode(this, this.mAppWidgetId).charAt(0) - getString(2131099703).charAt(0));
    this.spinner_device_code.setSelection(-1 + Integer.parseInt(loadDeviceCode(this, this.mAppWidgetId)));
    this.checkbox_status_indication_color.setChecked(loadStatusIndicationColor(this, this.mAppWidgetId));
  }
}
