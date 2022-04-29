package com.advancedquonsettechnology.hcaapp;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class ColorPickerDialog
  extends Dialog
{
  private int mDefaultColor;
  private int mInitialColor;
  private String mKey;
  private OnColorChangedListener mListener;
  
  public ColorPickerDialog(Context paramContext, OnColorChangedListener paramOnColorChangedListener, String paramString, int paramInt1, int paramInt2)
  {
    super(paramContext);
    this.mListener = paramOnColorChangedListener;
    this.mKey = paramString;
    this.mInitialColor = paramInt1;
    this.mDefaultColor = paramInt2;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    OnColorChangedListener local1 = new OnColorChangedListener()
    {
      public void colorChanged(String paramAnonymousString, int paramAnonymousInt)
      {
        ColorPickerDialog.this.mListener.colorChanged(ColorPickerDialog.this.mKey, paramAnonymousInt);
        ColorPickerDialog.this.dismiss();
      }
    };
    setContentView(new ColorPickerView(getContext(), local1, this.mInitialColor, this.mDefaultColor));
    setTitle(2131230732);
  }
  
  private static class ColorPickerView
    extends View
  {
    private int height = 366;
    private int mCurrentColor;
    private float mCurrentHue = 0.0F;
    private int mCurrentX = 0;
    private int mCurrentY = 0;
    private int mDefaultColor;
    private final int[] mHueBarColors = new int['Ă'];
    private ColorPickerDialog.OnColorChangedListener mListener;
    private int[] mMainColors = new int[65536];
    private Paint mPaint;
    private int width = 276;
    
    ColorPickerView(Context paramContext, ColorPickerDialog.OnColorChangedListener paramOnColorChangedListener, int paramInt1, int paramInt2)
    {
      super();
      this.mListener = paramOnColorChangedListener;
      this.mDefaultColor = paramInt2;
      float[] arrayOfFloat = new float[3];
      Color.colorToHSV(paramInt1, arrayOfFloat);
      this.mCurrentHue = arrayOfFloat[0];
      updateMainColors();
      this.mCurrentColor = paramInt1;
      Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
      new Point();
      this.width = ((int)(0.5D * localDisplay.getWidth()));
      this.height = ((int)(0.3D * localDisplay.getHeight()));
      this.width = (256 * (this.width / 256));
      int i = 0;
      for (float f1 = 0.0F; f1 < 256.0F; f1 += 6.0F)
      {
        this.mHueBarColors[i] = Color.rgb(255, 0, (int)f1);
        i++;
      }
      for (float f2 = 0.0F; f2 < 256.0F; f2 += 6.0F)
      {
        this.mHueBarColors[i] = Color.rgb(255 - (int)f2, 0, 255);
        i++;
      }
      for (float f3 = 0.0F; f3 < 256.0F; f3 += 6.0F)
      {
        this.mHueBarColors[i] = Color.rgb(0, (int)f3, 255);
        i++;
      }
      for (float f4 = 0.0F; f4 < 256.0F; f4 += 6.0F)
      {
        this.mHueBarColors[i] = Color.rgb(0, 255, 255 - (int)f4);
        i++;
      }
      for (float f5 = 0.0F; f5 < 256.0F; f5 += 6.0F)
      {
        this.mHueBarColors[i] = Color.rgb((int)f5, 255, 0);
        i++;
      }
      for (float f6 = 0.0F; f6 < 256.0F; f6 += 6.0F)
      {
        this.mHueBarColors[i] = Color.rgb(255, 255 - (int)f6, 0);
        i++;
      }
      this.mPaint = new Paint(1);
      this.mPaint.setTextAlign(Paint.Align.CENTER);
      this.mPaint.setTextSize(12.0F);
    }
    
    private int getCurrentMainColor()
    {
      int i = 255 - (int)(255.0F * this.mCurrentHue / 360.0F);
      int j = 0;
      for (float f1 = 0.0F; f1 < 256.0F; f1 += 6.0F)
      {
        if (j == i) {
          return Color.rgb(255, 0, (int)f1);
        }
        j++;
      }
      for (float f2 = 0.0F; f2 < 256.0F; f2 += 6.0F)
      {
        if (j == i) {
          return Color.rgb(255 - (int)f2, 0, 255);
        }
        j++;
      }
      for (float f3 = 0.0F; f3 < 256.0F; f3 += 6.0F)
      {
        if (j == i) {
          return Color.rgb(0, (int)f3, 255);
        }
        j++;
      }
      for (float f4 = 0.0F; f4 < 256.0F; f4 += 6.0F)
      {
        if (j == i) {
          return Color.rgb(0, 255, 255 - (int)f4);
        }
        j++;
      }
      for (float f5 = 0.0F; f5 < 256.0F; f5 += 6.0F)
      {
        if (j == i) {
          return Color.rgb((int)f5, 255, 0);
        }
        j++;
      }
      for (float f6 = 0.0F; f6 < 256.0F; f6 += 6.0F)
      {
        if (j == i) {
          return Color.rgb(255, 255 - (int)f6, 0);
        }
        j++;
      }
      return -65536;
    }
    
    private void updateMainColors()
    {
      int i = getCurrentMainColor();
      int j = 0;
      int[] arrayOfInt = new int['Ā'];
      for (int k = 0; k < 256; k++)
      {
        int m = 0;
        if (m < 256)
        {
          if (k == 0)
          {
            this.mMainColors[j] = Color.rgb(255 - m * (255 - Color.red(i)) / 255, 255 - m * (255 - Color.green(i)) / 255, 255 - m * (255 - Color.blue(i)) / 255);
            arrayOfInt[m] = this.mMainColors[j];
          }
          for (;;)
          {
            j++;
            m++;
            break;
            this.mMainColors[j] = Color.rgb((255 - k) * Color.red(arrayOfInt[m]) / 255, (255 - k) * Color.green(arrayOfInt[m]) / 255, (255 - k) * Color.blue(arrayOfInt[m]) / 255);
          }
        }
      }
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      int i = 255 - (int)(255.0F * this.mCurrentHue / 360.0F);
      int j = 0;
      if (j < 256)
      {
        if (i != j)
        {
          this.mPaint.setColor(this.mHueBarColors[j]);
          this.mPaint.setStrokeWidth(this.width / 256);
        }
        for (;;)
        {
          paramCanvas.drawLine(10 + j * this.width / 256, 0.0F, 10 + j * this.width / 256, 40.0F, this.mPaint);
          j++;
          break;
          this.mPaint.setColor(-16777216);
          this.mPaint.setStrokeWidth(this.width / 256);
        }
      }
      for (int k = 0; k < 256; k++)
      {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = this.mMainColors[k];
        arrayOfInt[1] = -16777216;
        LinearGradient localLinearGradient = new LinearGradient(0.0F, 50.0F, 0.0F, 306.0F, arrayOfInt, null, Shader.TileMode.REPEAT);
        this.mPaint.setShader(localLinearGradient);
        paramCanvas.drawLine(k + 10, 50.0F, k + 10, 306.0F, this.mPaint);
      }
      this.mPaint.setShader(null);
      if ((this.mCurrentX != 0) && (this.mCurrentY != 0))
      {
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(-16777216);
        paramCanvas.drawCircle(this.mCurrentX, this.mCurrentY, 10.0F, this.mPaint);
      }
      this.mPaint.setStyle(Paint.Style.FILL);
      this.mPaint.setColor(this.mCurrentColor);
      paramCanvas.drawRect(10.0F, 316.0F, 138.0F, 356.0F, this.mPaint);
      if (Color.red(this.mCurrentColor) + Color.green(this.mCurrentColor) + Color.blue(this.mCurrentColor) < 384)
      {
        this.mPaint.setColor(-1);
        paramCanvas.drawText(getResources().getString(2131230731), 74.0F, 340.0F, this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mDefaultColor);
        paramCanvas.drawRect(138.0F, 316.0F, 266.0F, 356.0F, this.mPaint);
        if (Color.red(this.mDefaultColor) + Color.green(this.mDefaultColor) + Color.blue(this.mDefaultColor) >= 384) {
          break label491;
        }
        this.mPaint.setColor(-1);
      }
      for (;;)
      {
        paramCanvas.drawText(getResources().getString(2131230733), 202.0F, 340.0F, this.mPaint);
        return;
        this.mPaint.setColor(-16777216);
        break;
        label491:
        this.mPaint.setColor(-16777216);
      }
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      setMeasuredDimension(this.width, this.height);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      if (paramMotionEvent.getAction() != 0) {
        return true;
      }
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      if ((f1 > 10.0F) && (f1 < 266.0F) && (f2 > 0.0F) && (f2 < 40.0F))
      {
        this.mCurrentHue = (360.0F * (255.0F - f1) / 255.0F);
        updateMainColors();
        int j = -10 + this.mCurrentX;
        int k = -60 + this.mCurrentY;
        int m = j + 256 * (k - 1);
        if ((m > 0) && (m < this.mMainColors.length)) {
          this.mCurrentColor = this.mMainColors[(j + 256 * (k - 1))];
        }
        invalidate();
      }
      if ((f1 > 10.0F) && (f1 < 266.0F) && (f2 > 50.0F) && (f2 < 306.0F))
      {
        this.mCurrentX = ((int)f1);
        this.mCurrentY = ((int)f2);
        int i = -10 + this.mCurrentX + 256 * (-1 + (-60 + this.mCurrentY));
        if ((i > 0) && (i < this.mMainColors.length))
        {
          this.mCurrentColor = this.mMainColors[i];
          invalidate();
        }
      }
      if ((f1 > 10.0F) && (f1 < 138.0F) && (f2 > 316.0F) && (f2 < 356.0F)) {
        this.mListener.colorChanged("", this.mCurrentColor);
      }
      if ((f1 > 138.0F) && (f1 < 266.0F) && (f2 > 316.0F) && (f2 < 356.0F)) {
        this.mListener.colorChanged("", this.mDefaultColor);
      }
      return true;
    }
  }
  
  public static abstract interface OnColorChangedListener
  {
    public abstract void colorChanged(String paramString, int paramInt);
  }
}
