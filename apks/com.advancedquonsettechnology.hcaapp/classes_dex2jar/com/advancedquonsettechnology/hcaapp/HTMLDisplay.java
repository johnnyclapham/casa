package com.advancedquonsettechnology.hcaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HTMLDisplay
  extends Activity
  implements Spinner.SpinnerTimeoutCallback
{
  HCAApplication _app = (HCAApplication)getApplication();
  RelativeLayout _rl;
  Spinner _spinner;
  WebView _webview;
  HCADisplay hcaDisplay;
  
  public HTMLDisplay() {}
  
  public void SpinnerTimeout(int paramInt) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (HCAApplication.devMode) {
      Log.d("HCA", "ObjectsAdapter.onCreate");
    }
    this.hcaDisplay = HCAApplication.findHCADisplay(getIntent().getStringExtra("com.advancedquonsettechnology.hcaapp.foldername"));
    requestWindowFeature(1);
    getWindow().requestFeature(2);
    this._webview = new WebView(this);
    this._webview.getSettings().setJavaScriptEnabled(true);
    this._webview.getSettings().setBuiltInZoomControls(true);
    this._webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
    this._webview.setWebChromeClient(new WebChromeClient()
    {
      public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
      {
        jdField_this.setProgress(paramAnonymousInt * 1000);
      }
    });
    this._webview.setWebViewClient(new WebViewClient()
    {
      public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
      {
        Toast.makeText(jdField_this, "Oh no! " + paramAnonymousString1, 0).show();
      }
    });
    this._rl = new RelativeLayout(this);
    this._rl.addView(this._webview, -1);
    setContentView(this._rl);
    this._spinner = new Spinner(this, this);
  }
  
  protected void onDestroy()
  {
    if (HCAApplication.devMode) {
      Log.d("HCA", "HTMLDisplay.onDestroy");
    }
    super.onDestroy();
  }
  
  protected void onStart()
  {
    super.onStart();
    this._spinner.start("Please Wait...", 30000, 0);
    new HTMLLoader().execute(new Void[0]);
  }
  
  protected void onStop()
  {
    super.onStop();
  }
  
  class HTMLLoader
    extends AsyncTask<Void, Boolean, Void>
  {
    HTMLLoader() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      boolean bool = HCAApplication.loadDisplayHTML(HTMLDisplay.this.hcaDisplay);
      Boolean[] arrayOfBoolean = new Boolean[1];
      arrayOfBoolean[0] = Boolean.valueOf(bool);
      publishProgress(arrayOfBoolean);
      return null;
    }
    
    protected void onProgressUpdate(Boolean... paramVarArgs)
    {
      HTMLDisplay.this._rl.setBackgroundColor(-1);
      if ((!paramVarArgs[0].booleanValue()) || (HTMLDisplay.this.hcaDisplay.getHtml() == null))
      {
        HTMLDisplay.this._spinner.stop();
        HTMLDisplay.this._webview.loadData("<html><h1>An error occurred while retrieving the Display HTML</h1><br />Please press Back and try again</html>", "text/html", null);
        return;
      }
      if (HTMLDisplay.this.hcaDisplay.getDisplayType() == 1) {
        HTMLDisplay.this._webview.loadUrl(HTMLDisplay.this.hcaDisplay.getHtml());
      }
      for (;;)
      {
        HTMLDisplay.this._spinner.stop();
        return;
        HTMLDisplay.this._webview.loadData(HTMLDisplay.this.hcaDisplay.getHtml(), "text/html", null);
      }
    }
  }
}
