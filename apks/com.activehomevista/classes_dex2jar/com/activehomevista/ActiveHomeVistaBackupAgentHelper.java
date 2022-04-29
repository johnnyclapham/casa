package com.activehomevista;

import android.annotation.TargetApi;
import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import java.io.IOException;

@TargetApi(8)
public class ActiveHomeVistaBackupAgentHelper
  extends BackupAgentHelper
{
  private static final String PREFS_BACKUP_KEY = "prefs";
  
  public ActiveHomeVistaBackupAgentHelper() {}
  
  public void onCreate()
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = (getPackageName() + "_preferences");
    addHelper("prefs", new SharedPreferencesBackupHelper(this, arrayOfString));
  }
  
  public void onRestore(BackupDataInput paramBackupDataInput, int paramInt, ParcelFileDescriptor paramParcelFileDescriptor)
    throws IOException
  {
    super.onRestore(paramBackupDataInput, paramInt, paramParcelFileDescriptor);
    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove(getString(2131099722)).remove(getString(2131099721)).commit();
  }
}
