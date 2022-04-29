package com.advancedquonsettechnology.hcaapp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class SettingsPreferences
  extends PreferenceActivity
  implements ColorPickerDialog.OnColorChangedListener
{
  public SettingsPreferences() {}
  
  public void colorChanged(String paramString, int paramInt)
  {
    findPreference(paramString).getEditor().putInt(paramString, paramInt).commit();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131034113);
    findPreference("app_fg_color").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        int i = SettingsPreferences.this.getSharedPreferences("com.advancedquonsettechnology.hcaapp_preferences", 0).getInt("app_fg_color", -1);
        new ColorPickerDialog(SettingsPreferences.this, SettingsPreferences.this, "app_fg_color", i, -1).show();
        return true;
      }
    });
    findPreference("app_bg_color").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        int i = SettingsPreferences.this.getSharedPreferences("com.advancedquonsettechnology.hcaapp_preferences", 0).getInt("app_bg_color", 9408);
        new ColorPickerDialog(SettingsPreferences.this, SettingsPreferences.this, "app_bg_color", i, 9408).show();
        return true;
      }
    });
    findPreference("folder_on_color").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        int i = SettingsPreferences.this.getSharedPreferences("com.advancedquonsettechnology.hcaapp_preferences", 0).getInt("folder_on_color", 65280);
        new ColorPickerDialog(SettingsPreferences.this, SettingsPreferences.this, "folder_on_color", i, 65280).show();
        return true;
      }
    });
    findPreference("suspend_user_bg_color").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        int i = SettingsPreferences.this.getSharedPreferences("com.advancedquonsettechnology.hcaapp_preferences", 0).getInt("suspend_user_bg_color", -65536);
        new ColorPickerDialog(SettingsPreferences.this, SettingsPreferences.this, "suspend_user_bg_color", i, -65536).show();
        return true;
      }
    });
    findPreference("suspend_mode_bg_color").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        int i = SettingsPreferences.this.getSharedPreferences("com.advancedquonsettechnology.hcaapp_preferences", 0).getInt("suspend_mode_bg_color", -16711936);
        new ColorPickerDialog(SettingsPreferences.this, SettingsPreferences.this, "suspend_mode_bg_color", i, -16711936).show();
        return true;
      }
    });
  }
}
