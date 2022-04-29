package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

class AppCompatImageHelper
{
  private static final int[] VIEW_ATTRS = { 16843033 };
  private final TintManager mTintManager;
  private final ImageView mView;
  
  AppCompatImageHelper(ImageView paramImageView, TintManager paramTintManager)
  {
    this.mView = paramImageView;
    this.mTintManager = paramTintManager;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, VIEW_ATTRS, paramInt, 0);
    try
    {
      if (localTintTypedArray.hasValue(0)) {
        this.mView.setImageDrawable(localTintTypedArray.getDrawable(0));
      }
      return;
    }
    finally
    {
      localTintTypedArray.recycle();
    }
  }
  
  void setImageResource(int paramInt)
  {
    if (paramInt != 0)
    {
      ImageView localImageView = this.mView;
      if (this.mTintManager != null) {}
      for (Drawable localDrawable = this.mTintManager.getDrawable(paramInt);; localDrawable = ContextCompat.getDrawable(this.mView.getContext(), paramInt))
      {
        localImageView.setImageDrawable(localDrawable);
        return;
      }
    }
    this.mView.setImageDrawable(null);
  }
}
