package com.comfortclick.cmclient;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.Locale;

public class WebActivity
  extends Activity
  implements TextToSpeech.OnInitListener
{
  public static int RESULT_CLOSE = 0;
  public static int RESULT_CONNECTIONFAILED = 3;
  public static int RESULT_CONNECTIONLOST;
  public static int RESULT_LOGIN_ERROR = 1;
  String address;
  String addressDomain;
  public String deviceName;
  private int dimmingTimeout;
  public boolean keepRunning = false;
  LoginClass lc;
  private Handler mDimmingTimer;
  private Runnable mDimmingTimerRunnable = new Runnable()
  {
    public void run()
    {
      if (WebActivity.this.dimmingTimeout > 1)
      {
        WebActivity localWebActivity = WebActivity.this;
        localWebActivity.dimmingTimeout = (-1 + localWebActivity.dimmingTimeout);
        WebActivity.this.mDimmingTimer.postDelayed(WebActivity.this.mDimmingTimerRunnable, 1000L);
        return;
      }
      WindowManager.LayoutParams localLayoutParams = WebActivity.this.getWindow().getAttributes();
      localLayoutParams.screenBrightness = 0.01F;
      WebActivity.this.getWindow().setAttributes(localLayoutParams);
    }
  };
  protected Dialog mSplashDialog;
  public boolean panelMode;
  public String password;
  public boolean preventExternal;
  public String profileName;
  public int refreshBack;
  public int refreshFore;
  TextToSpeech tts;
  public String username;
  WebView webView;
  
  static
  {
    RESULT_CONNECTIONLOST = 2;
  }
  
  public WebActivity() {}
  
  private void ResetDimmingTimer()
  {
    if (this.mDimmingTimer != null)
    {
      WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
      localLayoutParams.screenBrightness = 1.0F;
      getWindow().setAttributes(localLayoutParams);
      this.mDimmingTimer.removeCallbacks(this.mDimmingTimerRunnable);
      this.dimmingTimeout = 10;
      this.mDimmingTimer.postDelayed(this.mDimmingTimerRunnable, 1000L);
    }
  }
  
  private void onClose(int paramInt)
  {
    setResult(paramInt, new Intent());
    finish();
  }
  
  public void ShowOutdatedVersion()
  {
    Toast.makeText(this, getString(2131034136), 1).show();
  }
  
  public void onAndroidAction(String paramString)
  {
    try
    {
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setData(Uri.parse(paramString));
      startActivityForResult(localIntent, 1234);
      return;
    }
    catch (Exception localException) {}
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((this.mSplashDialog != null) && (this.mSplashDialog.isShowing()))
    {
      removeSplashScreen();
      showSplashScreen(this.profileName);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    setContentView(2130903045);
    Intent localIntent = getIntent();
    this.address = localIntent.getStringExtra("address");
    this.addressDomain = this.address;
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(this.address));
    if (this.address.endsWith("/")) {}
    for (String str = "";; str = "/")
    {
      this.address = str;
      this.panelMode = localIntent.getBooleanExtra("panelMode", false);
      this.profileName = localIntent.getStringExtra("profileName");
      this.username = localIntent.getStringExtra("username");
      this.password = localIntent.getStringExtra("password");
      this.deviceName = localIntent.getStringExtra("device_name");
      this.preventExternal = localIntent.getBooleanExtra("prevent_external", false);
      this.keepRunning = localIntent.getBooleanExtra("keepRunning", false);
      this.refreshFore = localIntent.getIntExtra("refresh_fore", 3);
      this.refreshBack = localIntent.getIntExtra("refresh_back", 15);
      this.dimmingTimeout = localIntent.getIntExtra("dimmingTimeout", 0);
      if (this.refreshFore < 1) {
        this.refreshFore = 1;
      }
      if (this.refreshBack < 1) {
        this.refreshBack = 1;
      }
      this.webView = ((WebView)findViewById(2131165206));
      this.webView.setOnLongClickListener(new View.OnLongClickListener()
      {
        public boolean onLongClick(View paramAnonymousView)
        {
          return true;
        }
      });
      WebSettings localWebSettings = this.webView.getSettings();
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setDatabaseEnabled(true);
      localWebSettings.setDatabasePath("/data/data/" + getPackageName() + "/databases/");
      localWebSettings.setDomStorageEnabled(true);
      this.webView.setWebViewClient(new MyWebViewClient());
      this.webView.setWebChromeClient(new WebChromeClient());
      if ((this.address == null) || (this.address.length() <= 7) || ((!this.address.startsWith("http://")) && (!this.address.startsWith("https://")))) {
        break;
      }
      this.webView.loadUrl(this.address);
      this.lc = new LoginClass();
      this.webView.addJavascriptInterface(this.lc, "LoginInfo");
      this.tts = new TextToSpeech(this, this);
      showSplashScreen(this.profileName);
      if (this.dimmingTimeout > 0)
      {
        this.dimmingTimeout = 30;
        this.mDimmingTimer = new Handler();
        this.mDimmingTimer.postDelayed(this.mDimmingTimerRunnable, 1000L);
      }
      return;
    }
    onClose(RESULT_CLOSE);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131099649, paramMenu);
    return true;
  }
  
  public void onDestroy()
  {
    removeSplashScreen();
    this.webView.loadUrl("");
    ((RelativeLayout)findViewById(2131165205)).removeView(this.webView);
    this.webView.destroy();
    this.webView = null;
    if (this.mDimmingTimer != null) {
      this.mDimmingTimer.removeCallbacks(this.mDimmingTimerRunnable);
    }
    if (this.tts != null)
    {
      this.tts.stop();
      this.tts.shutdown();
    }
    super.onDestroy();
  }
  
  public void onInit(int paramInt)
  {
    if (paramInt == 0)
    {
      Locale localLocale = Locale.getDefault();
      if (this.tts.isLanguageAvailable(localLocale) >= 0) {
        this.tts.setLanguage(localLocale);
      }
      return;
    }
    Log.e("TTS", "Initilization Failed!");
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (this.panelMode) {
        return true;
      }
      onClose(-1);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    onClose(RESULT_CLOSE);
    return true;
  }
  
  protected void onPause()
  {
    if (this.keepRunning) {
      this.webView.loadUrl("javascript:RefreshInterval = " + 1000 * this.refreshBack + ";");
    }
    for (;;)
    {
      super.onPause();
      return;
      this.webView.loadUrl("javascript:OnPause();");
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    ResetDimmingTimer();
    if (this.keepRunning)
    {
      this.webView.loadUrl("javascript:RefreshInterval = " + 1000 * this.refreshFore + ";");
      return;
    }
    this.webView.loadUrl("javascript:OnResume();");
  }
  
  public void onSpeakText(String paramString)
  {
    try
    {
      this.tts.speak(paramString, 0, null);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void onStartProgram(String paramString)
  {
    try
    {
      startActivity(getPackageManager().getLaunchIntentForPackage(paramString));
      return;
    }
    catch (Exception localException)
    {
      Toast.makeText(this, "Package " + paramString + " does not exist.", 0).show();
    }
  }
  
  public void onUserInteraction()
  {
    if (this.dimmingTimeout > 0) {
      ResetDimmingTimer();
    }
  }
  
  protected void removeSplashScreen()
  {
    if (this.mSplashDialog != null)
    {
      this.mSplashDialog.dismiss();
      this.mSplashDialog = null;
    }
  }
  
  protected void showSplashScreen(String paramString)
  {
    this.mSplashDialog = new Dialog(this, 16973829);
    this.mSplashDialog.requestWindowFeature(1);
    this.mSplashDialog.setContentView(2130903044);
    this.mSplashDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if (paramAnonymousInt == 4)
        {
          WebActivity.this.mSplashDialog.cancel();
          WebActivity.this.finish();
        }
        return false;
      }
    });
    ((TextView)this.mSplashDialog.findViewById(2131165204)).setText(getString(2131034126) + " " + paramString);
    this.mSplashDialog.show();
  }
  
  public void soundPlay(String paramString)
  {
    MediaPlayer localMediaPlayer = new MediaPlayer();
    try
    {
      if ((Build.VERSION.RELEASE.startsWith("4.1.1")) && (paramString.startsWith("https://"))) {
        paramString = paramString.replace("https://", "http://");
      }
      localMediaPlayer.setDataSource(paramString);
      localMediaPlayer.prepare();
      localMediaPlayer.start();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      localMediaPlayer.release();
    }
  }
  
  public class LoginClass
  {
    public LoginClass() {}
    
    public void clearCache()
    {
      WebActivity.this.webView.clearCache(true);
    }
    
    public void onConnectionLost()
    {
      WebActivity.this.onClose(WebActivity.RESULT_CONNECTIONLOST);
    }
    
    public void onLoginError()
    {
      WebActivity.this.ShowOutdatedVersion();
    }
    
    public void onLogout()
    {
      WebActivity.this.onClose(WebActivity.RESULT_CLOSE);
    }
    
    public void onWrongCredentials()
    {
      WebActivity.this.onClose(WebActivity.RESULT_LOGIN_ERROR);
    }
    
    public void playSound(String paramString)
    {
      WebActivity.this.soundPlay(paramString);
    }
    
    public void speakText(String paramString)
    {
      WebActivity.this.onSpeakText(paramString);
    }
    
    public void startAction(String paramString)
    {
      WebActivity.this.onAndroidAction(paramString);
    }
    
    public void startProgram(String paramString)
    {
      WebActivity.this.onStartProgram(paramString);
    }
  }
  
  public class MyWebViewClient
    extends WebViewClient
  {
    public MyWebViewClient() {}
    
    public void onPageFinished(WebView paramWebView, String paramString)
    {
      WebActivity.this.removeSplashScreen();
      paramWebView.loadUrl("javascript: {UserName='" + WebActivity.this.username + "'; Password='" + WebActivity.this.password + "'; CordovaOS='Android'; DeviceName='" + WebActivity.this.deviceName + "'; LoadTheme();}");
    }
    
    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      WebActivity.this.onClose(WebActivity.RESULT_CONNECTIONFAILED);
    }
    
    public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
    {
      paramSslErrorHandler.proceed();
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if (paramString != null) {
        try
        {
          if (WebActivity.this.addressDomain != null)
          {
            boolean bool = Uri.parse(paramString).getHost().equals(WebActivity.this.addressDomain);
            if (bool) {
              return false;
            }
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      if (!WebActivity.this.preventExternal)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
        WebActivity.this.startActivity(localIntent);
      }
      return true;
    }
  }
}
