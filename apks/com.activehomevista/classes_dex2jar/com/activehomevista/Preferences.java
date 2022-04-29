package com.activehomevista;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import java.util.List;
import java.util.ListIterator;

public class Preferences
  extends PreferenceActivity
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  public Preferences() {}
  
  private void updateWiFiEntries()
  {
    List localList = ((WifiManager)getSystemService("wifi")).getConfiguredNetworks();
    if (localList != null) {}
    ListPreferenceMultiSelect localListPreferenceMultiSelect;
    String[] arrayOfString;
    int j;
    for (int i = localList.size();; i = 0)
    {
      localListPreferenceMultiSelect = (ListPreferenceMultiSelect)findPreference(getString(2131099731));
      arrayOfString = ListPreferenceMultiSelect.parseStoredValue(PreferenceManager.getDefaultSharedPreferences(this).getString(getString(2131099731), ""));
      j = 0;
      if (arrayOfString == null) {
        break;
      }
      arrayOfCharSequence1 = new CharSequence[i + arrayOfString.length / 2];
      arrayOfCharSequence2 = new CharSequence[i + arrayOfString.length / 2];
      for (j = 0; j < arrayOfString.length / 2; j++)
      {
        arrayOfCharSequence1[j] = arrayOfString[(j * 2)];
        arrayOfCharSequence2[j] = arrayOfString[(1 + j * 2)];
      }
    }
    CharSequence[] arrayOfCharSequence1 = new CharSequence[i];
    CharSequence[] arrayOfCharSequence2 = new CharSequence[i];
    if (localList != null)
    {
      ListIterator localListIterator = localList.listIterator();
      while (localListIterator.hasNext())
      {
        localListIterator.next();
        if (!ListPreferenceMultiSelect.isValueInList(arrayOfString, ((WifiConfiguration)localList.get(localListIterator.previousIndex())).SSID))
        {
          arrayOfCharSequence1[j] = ((WifiConfiguration)localList.get(localListIterator.previousIndex())).SSID;
          if (arrayOfCharSequence1[j] == null) {
            arrayOfCharSequence1[j] = "";
          }
          arrayOfCharSequence2[j] = ((WifiConfiguration)localList.get(localListIterator.previousIndex())).SSID;
          if (arrayOfCharSequence2[j] == null) {
            arrayOfCharSequence2[j] = "";
          }
          j++;
        }
      }
    }
    CharSequence[] arrayOfCharSequence3 = new CharSequence[j];
    CharSequence[] arrayOfCharSequence4 = new CharSequence[j];
    for (int k = 0; k < arrayOfCharSequence3.length; k++)
    {
      arrayOfCharSequence3[k] = arrayOfCharSequence1[k];
      if ((arrayOfCharSequence3[k].length() >= 2) && (arrayOfCharSequence3[k].charAt(0) == '"') && (arrayOfCharSequence3[k].charAt(-1 + arrayOfCharSequence3[k].length()) == '"')) {
        arrayOfCharSequence3[k] = arrayOfCharSequence3[k].subSequence(1, -1 + arrayOfCharSequence3[k].length());
      }
      arrayOfCharSequence4[k] = arrayOfCharSequence2[k];
    }
    localListPreferenceMultiSelect.setEntries(arrayOfCharSequence3);
    localListPreferenceMultiSelect.setEntryValues(arrayOfCharSequence4);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    ActiveHomeVista.updateTheme(PreferenceManager.getDefaultSharedPreferences(this), this);
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131034114);
    updateWiFiEntries();
    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
  }
  
  protected void onPause()
  {
    super.onPause();
    PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
  }
  
  protected void onResume()
  {
    super.onResume();
    updateWiFiEntries();
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if (paramString.equals(getString(2131099731))) {
      updateWiFiEntries();
    }
    if ((paramString.equals(getString(2131099729))) && (Build.VERSION.SDK_INT >= 11)) {
      ActiveHomeVista.VersionHelper.recreate(this);
    }
  }
}
