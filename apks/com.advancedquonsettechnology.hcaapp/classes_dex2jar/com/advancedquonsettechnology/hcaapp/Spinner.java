package com.advancedquonsettechnology.hcaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;

public class Spinner
{
  SpinnerTimeoutCallback _cb;
  Context _context;
  private Handler _handler = new Handler();
  int _id;
  ProgressDialog _progDlg;
  boolean _spin;
  int _spincount;
  private Runnable updateTimeTask = new Runnable()
  {
    public void run()
    {
      if (!Spinner.this._spin) {}
      do
      {
        return;
        Spinner localSpinner = Spinner.this;
        localSpinner._spincount = (-1 + localSpinner._spincount);
        if (Spinner.this._spincount > 0)
        {
          Spinner.this._handler.postAtTime(this, 1000L + SystemClock.uptimeMillis());
          return;
        }
        Spinner.this.stop();
      } while (Spinner.this._cb == null);
      Spinner.this._cb.SpinnerTimeout(Spinner.this._id);
    }
  };
  
  public Spinner(Context paramContext, SpinnerTimeoutCallback paramSpinnerTimeoutCallback)
  {
    this._context = paramContext;
    this._cb = paramSpinnerTimeoutCallback;
  }
  
  private void hide()
  {
    if ((this._progDlg != null) && (this._progDlg.getWindow() != null) && (this._progDlg.isShowing())) {
      this._progDlg.dismiss();
    }
  }
  
  public void start(String paramString, int paramInt1, int paramInt2)
  {
    this._id = paramInt2;
    this._spin = true;
    this._spincount = (paramInt1 / 1000);
    this._handler.removeCallbacks(this.updateTimeTask);
    this._handler.postDelayed(this.updateTimeTask, 1000L);
    this._progDlg = ProgressDialog.show(this._context, "", paramString, true);
  }
  
  public void stop()
  {
    hide();
    this._spin = false;
  }
  
  public void stopIfId(int paramInt)
  {
    if (paramInt == this._id) {
      stop();
    }
  }
  
  public static abstract interface SpinnerTimeoutCallback
  {
    public abstract void SpinnerTimeout(int paramInt);
  }
}
