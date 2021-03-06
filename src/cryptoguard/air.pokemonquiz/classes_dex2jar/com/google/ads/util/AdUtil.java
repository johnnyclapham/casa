package com.google.ads.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.ads.AdActivity;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AdUtil
{
  public static final int a = a(Build.VERSION.SDK);
  private static Boolean b = null;
  private static String c = null;
  private static String d;
  private static String e = null;
  private static AudioManager f;
  private static boolean g = true;
  private static boolean h = false;
  private static String i = null;
  
  public static int a()
  {
    if (a >= 9) {
      return 6;
    }
    return 0;
  }
  
  public static int a(Context paramContext, int paramInt)
  {
    return (int)TypedValue.applyDimension(1, paramInt, paramContext.getResources().getDisplayMetrics());
  }
  
  public static int a(Context paramContext, DisplayMetrics paramDisplayMetrics)
  {
    if (a >= 4) {
      return e.a(paramContext, paramDisplayMetrics);
    }
    return paramDisplayMetrics.heightPixels;
  }
  
  public static int a(String paramString)
  {
    try
    {
      int j = Integer.parseInt(paramString);
      return j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      b.e("The Android SDK version couldn't be parsed to an int: " + Build.VERSION.SDK);
      b.e("Defaulting to Android SDK version 3.");
    }
    return 3;
  }
  
  public static DisplayMetrics a(Activity paramActivity)
  {
    if (paramActivity.getWindowManager() == null) {
      return null;
    }
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics;
  }
  
  public static String a(Context paramContext)
  {
    if (c == null)
    {
      String str1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
      if ((str1 == null) || (c())) {}
      for (String str2 = b("emulator"); str2 == null; str2 = b(str1)) {
        return null;
      }
      c = str2.toUpperCase(Locale.US);
    }
    return c;
  }
  
  public static String a(Readable paramReadable)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    CharBuffer localCharBuffer = CharBuffer.allocate(2048);
    for (;;)
    {
      int j = paramReadable.read(localCharBuffer);
      if (j == -1) {
        break;
      }
      localCharBuffer.flip();
      localStringBuilder.append(localCharBuffer, 0, j);
    }
    return localStringBuilder.toString();
  }
  
  public static String a(Map<String, Object> paramMap)
  {
    try
    {
      String str = b(paramMap).toString();
      return str;
    }
    catch (JSONException localJSONException)
    {
      b.d("JsonException in serialization: ", localJSONException);
    }
    return null;
  }
  
  public static HashMap<String, Object> a(Location paramLocation)
  {
    if (paramLocation == null) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("time", Long.valueOf(1000L * paramLocation.getTime()));
    localHashMap.put("lat", Long.valueOf((1.0E7D * paramLocation.getLatitude())));
    localHashMap.put("long", Long.valueOf((1.0E7D * paramLocation.getLongitude())));
    localHashMap.put("radius", Long.valueOf((1000.0F * paramLocation.getAccuracy())));
    return localHashMap;
  }
  
  public static JSONArray a(Set<Object> paramSet)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    if ((paramSet == null) || (paramSet.isEmpty())) {}
    for (;;)
    {
      return localJSONArray;
      Iterator localIterator = paramSet.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        if (((localObject instanceof String)) || ((localObject instanceof Integer)) || ((localObject instanceof Double)) || ((localObject instanceof Long)) || ((localObject instanceof Float))) {
          localJSONArray.put(localObject);
        } else if ((localObject instanceof Map)) {
          try
          {
            localJSONArray.put(b((Map)localObject));
          }
          catch (ClassCastException localClassCastException2)
          {
            b.d("Unknown map type in json serialization: ", localClassCastException2);
          }
        } else if ((localObject instanceof Set)) {
          try
          {
            localJSONArray.put(a((Set)localObject));
          }
          catch (ClassCastException localClassCastException1)
          {
            b.d("Unknown map type in json serialization: ", localClassCastException1);
          }
        } else {
          b.e("Unknown value in json serialization: " + localObject);
        }
      }
    }
  }
  
  public static void a(WebView paramWebView)
  {
    String str = i(paramWebView.getContext().getApplicationContext());
    paramWebView.getSettings().setUserAgentString(str);
  }
  
  public static void a(HttpURLConnection paramHttpURLConnection, Context paramContext)
  {
    paramHttpURLConnection.setRequestProperty("User-Agent", i(paramContext));
  }
  
  public static void a(boolean paramBoolean)
  {
    g = paramBoolean;
  }
  
  public static boolean a(int paramInt1, int paramInt2, String paramString)
  {
    if ((paramInt1 & paramInt2) == 0)
    {
      b.b("The android:configChanges value of the com.google.ads.AdActivity must include " + paramString + ".");
      return false;
    }
    return true;
  }
  
  public static boolean a(Intent paramIntent, Context paramContext)
  {
    return paramContext.getPackageManager().resolveActivity(paramIntent, 65536) != null;
  }
  
  public static boolean a(Uri paramUri)
  {
    if (paramUri == null) {
      return false;
    }
    String str = paramUri.getScheme();
    return ("http".equalsIgnoreCase(str)) || ("https".equalsIgnoreCase(str));
  }
  
  static boolean a(d paramD)
  {
    if (paramD == null) {}
    for (d localD = d.d;; localD = paramD) {
      return (localD.equals(d.e)) || (localD.equals(d.f));
    }
  }
  
  public static int b()
  {
    if (a >= 9) {
      return 7;
    }
    return 1;
  }
  
  public static int b(Context paramContext, DisplayMetrics paramDisplayMetrics)
  {
    if (a >= 4) {
      return e.b(paramContext, paramDisplayMetrics);
    }
    return paramDisplayMetrics.widthPixels;
  }
  
  public static String b(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      int j = paramString.length();
      localObject = null;
      if (j <= 0) {}
    }
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes(), 0, paramString.length());
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new BigInteger(1, localMessageDigest.digest());
      String str = String.format(localLocale, "%032X", arrayOfObject);
      localObject = str;
      return localObject;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
    return paramString.substring(0, 32);
  }
  
  public static HashMap<String, String> b(Uri paramUri)
  {
    Object localObject;
    if (paramUri == null) {
      localObject = null;
    }
    String str1;
    do
    {
      return localObject;
      localObject = new HashMap();
      str1 = paramUri.getEncodedQuery();
    } while (str1 == null);
    String[] arrayOfString = str1.split("&");
    int j = arrayOfString.length;
    int k = 0;
    label40:
    String str2;
    int m;
    if (k < j)
    {
      str2 = arrayOfString[k];
      m = str2.indexOf("=");
      if (m >= 0) {
        break label85;
      }
      ((HashMap)localObject).put(Uri.decode(str2), null);
    }
    for (;;)
    {
      k++;
      break label40;
      break;
      label85:
      ((HashMap)localObject).put(Uri.decode(str2.substring(0, m)), Uri.decode(str2.substring(m + 1, str2.length())));
    }
  }
  
  public static JSONObject b(Map<String, Object> paramMap)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    if ((paramMap == null) || (paramMap.isEmpty())) {
      return localJSONObject;
    }
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramMap.get(str);
      if (((localObject instanceof String)) || ((localObject instanceof Integer)) || ((localObject instanceof Double)) || ((localObject instanceof Long)) || ((localObject instanceof Float))) {
        localJSONObject.put(str, localObject);
      } else if ((localObject instanceof Map)) {
        try
        {
          localJSONObject.put(str, b((Map)localObject));
        }
        catch (ClassCastException localClassCastException2)
        {
          b.d("Unknown map type in json serialization: ", localClassCastException2);
        }
      } else if ((localObject instanceof Set)) {
        try
        {
          localJSONObject.put(str, a((Set)localObject));
        }
        catch (ClassCastException localClassCastException1)
        {
          b.d("Unknown map type in json serialization: ", localClassCastException1);
        }
      } else {
        b.e("Unknown value in json serialization: " + localObject);
      }
    }
    return localJSONObject;
  }
  
  public static boolean b(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    String str = paramContext.getPackageName();
    if (localPackageManager.checkPermission("android.permission.INTERNET", str) == -1)
    {
      b.b("INTERNET permissions must be enabled in AndroidManifest.xml.");
      return false;
    }
    if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", str) == -1)
    {
      b.b("ACCESS_NETWORK_STATE permissions must be enabled in AndroidManifest.xml.");
      return false;
    }
    return true;
  }
  
  public static boolean c()
  {
    return a(null);
  }
  
  public static boolean c(Context paramContext)
  {
    if (b != null) {
      return b.booleanValue();
    }
    ResolveInfo localResolveInfo = paramContext.getPackageManager().resolveActivity(new Intent(paramContext, AdActivity.class), 65536);
    b = Boolean.valueOf(true);
    if ((localResolveInfo == null) || (localResolveInfo.activityInfo == null))
    {
      b.b("Could not find com.google.ads.AdActivity, please make sure it is registered in AndroidManifest.xml.");
      b = Boolean.valueOf(false);
    }
    for (;;)
    {
      return b.booleanValue();
      if (!a(localResolveInfo.activityInfo.configChanges, 16, "keyboard")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 32, "keyboardHidden")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 128, "orientation")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 256, "screenLayout")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 512, "uiMode")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 1024, "screenSize")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 2048, "smallestScreenSize")) {
        b = Boolean.valueOf(false);
      }
    }
  }
  
  public static String d(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo == null) {
      return null;
    }
    switch (localNetworkInfo.getType())
    {
    default: 
      return "unknown";
    case 0: 
      return "ed";
    }
    return "wi";
  }
  
  public static boolean d()
  {
    return g;
  }
  
  public static String e(Context paramContext)
  {
    if (d == null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      PackageManager localPackageManager = paramContext.getPackageManager();
      List localList1 = localPackageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=donuts")), 65536);
      if ((localList1 == null) || (localList1.isEmpty())) {
        localStringBuilder.append("m");
      }
      List localList2 = localPackageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pname:com.google")), 65536);
      if ((localList2 == null) || (localList2.isEmpty()))
      {
        if (localStringBuilder.length() > 0) {
          localStringBuilder.append(",");
        }
        localStringBuilder.append("a");
      }
      d = localStringBuilder.toString();
    }
    return d;
  }
  
  public static String f(Context paramContext)
  {
    if (e != null) {
      return e;
    }
    try
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      ResolveInfo localResolveInfo = localPackageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.ads")), 65536);
      if (localResolveInfo == null) {
        return null;
      }
      ActivityInfo localActivityInfo = localResolveInfo.activityInfo;
      if (localActivityInfo == null) {
        return null;
      }
      PackageInfo localPackageInfo = localPackageManager.getPackageInfo(localActivityInfo.packageName, 0);
      if (localPackageInfo == null) {
        return null;
      }
      e = localPackageInfo.versionCode + "." + localActivityInfo.packageName;
      String str = e;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public static a g(Context paramContext)
  {
    if (f == null) {
      f = (AudioManager)paramContext.getSystemService("audio");
    }
    int j = f.getMode();
    if (c()) {
      return a.e;
    }
    if ((f.isMusicActive()) || (f.isSpeakerphoneOn()) || (j == 2) || (j == 1)) {
      return a.d;
    }
    int k = f.getRingerMode();
    if ((k == 0) || (k == 1)) {
      return a.d;
    }
    return a.b;
  }
  
  public static void h(Context paramContext)
  {
    if (h) {
      return;
    }
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.USER_PRESENT");
    localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
    paramContext.registerReceiver(new UserActivityReceiver(), localIntentFilter);
    h = true;
  }
  
  public static String i(Context paramContext)
  {
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    if (i == null)
    {
      str1 = new WebView(paramContext).getSettings().getUserAgentString();
      if ((str1 == null) || (str1.length() == 0) || (str1.equals("Java0")))
      {
        str2 = System.getProperty("os.name", "Linux");
        str3 = "Android " + Build.VERSION.RELEASE;
        Locale localLocale = Locale.getDefault();
        str4 = localLocale.getLanguage().toLowerCase(Locale.US);
        if (str4.length() == 0) {
          str4 = "en";
        }
        str5 = localLocale.getCountry().toLowerCase(Locale.US);
        if (str5.length() <= 0) {
          break label295;
        }
      }
    }
    label295:
    for (String str6 = str4 + "-" + str5;; str6 = str4)
    {
      String str7 = Build.MODEL + " Build/" + Build.ID;
      str1 = "Mozilla/5.0 (" + str2 + "; U; " + str3 + "; " + str6 + "; " + str7 + ") AppleWebKit/0.0 (KHTML, like " + "Gecko) Version/0.0 Mobile Safari/0.0";
      i = str1 + " (Mobile; " + "afma-sdk-a-v" + "6.3.1" + ")";
      return i;
    }
  }
  
  public static class UserActivityReceiver
    extends BroadcastReceiver
  {
    public UserActivityReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent.getAction().equals("android.intent.action.USER_PRESENT")) {
        AdUtil.a(true);
      }
      while (!paramIntent.getAction().equals("android.intent.action.SCREEN_OFF")) {
        return;
      }
      AdUtil.a(false);
    }
  }
  
  public static enum a
  {
    static
    {
      a[] arrayOfA = new a[6];
      arrayOfA[0] = a;
      arrayOfA[1] = b;
      arrayOfA[2] = c;
      arrayOfA[3] = d;
      arrayOfA[4] = e;
      arrayOfA[5] = f;
      g = arrayOfA;
    }
    
    private a() {}
  }
}
