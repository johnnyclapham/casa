package com.activehomevista.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

class HouseCache
{
  private static final String HOUSE = "HOUSE";
  private static final String TAG = "ActiveHomeVistaHouse";
  
  HouseCache() {}
  
  public static byte[] readHouseFile(Context paramContext)
    throws IOException
  {
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(new File(paramContext.getFilesDir(), "HOUSE"), "r");
    byte[] arrayOfByte = new byte[(int)localRandomAccessFile.length()];
    localRandomAccessFile.readFully(arrayOfByte);
    localRandomAccessFile.close();
    return arrayOfByte;
  }
  
  public static int readIDFile(Context paramContext)
  {
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getInt(paramContext.getString(2131099722), -1);
  }
  
  public static void writeHouseFile(Context paramContext, int paramInt, byte[] paramArrayOfByte)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(new File(paramContext.getFilesDir(), "HOUSE"));
      localFileOutputStream.write(paramArrayOfByte);
      localFileOutputStream.close();
      PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putInt(paramContext.getString(2131099722), paramInt).commit();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        if (!new File(paramContext.getFilesDir(), "HOUSE").delete()) {
          Log.e("ActiveHomeVistaHouse", "Failed deleting file");
        }
      }
    }
    finally {}
  }
}
