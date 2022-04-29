package com.comfortclick.cmclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class CMClientPreferencesActivity
  extends PreferenceActivity
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  public CMClientPreferencesActivity() {}
  
  private void initSummary(Preference paramPreference)
  {
    if ((paramPreference instanceof PreferenceCategory))
    {
      PreferenceCategory localPreferenceCategory = (PreferenceCategory)paramPreference;
      for (int i = 0;; i++)
      {
        if (i >= localPreferenceCategory.getPreferenceCount()) {
          return;
        }
        initSummary(localPreferenceCategory.getPreference(i));
      }
    }
    updatePrefSummary(paramPreference);
  }
  
  private SharedPreferences prefs()
  {
    return getPreferenceManager().getSharedPreferences();
  }
  
  private void updatePrefSummary(Preference paramPreference)
  {
    if ((paramPreference instanceof EditTextPreference)) {
      paramPreference.setSummary(((EditTextPreference)paramPreference).getText());
    }
  }
  
  public void onBackPressed()
  {
    setResult(-1, new Intent());
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2130968578);
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    PreferenceManager.setDefaultValues(this, 2130968578, false);
    if (prefs().getString(getString(2131034113), "").equals(""))
    {
      SharedPreferences.Editor localEditor = prefs().edit();
      localEditor.putString(getString(2131034113), Build.MODEL);
      localEditor.commit();
    }
    EditTextPreference localEditTextPreference = (EditTextPreference)getPreferenceManager().findPreference(getString(2131034113));
    String str = prefs().getString(getString(2131034113), "");
    localEditTextPreference.setText(str);
    localEditTextPreference.setSummary(str);
    for (int i = 0;; i++)
    {
      if (i >= getPreferenceScreen().getPreferenceCount()) {
        return;
      }
      initSummary(getPreferenceScreen().getPreference(i));
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }
  
  protected void onResume()
  {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    updatePrefSummary(findPreference(paramString));
  }
}
