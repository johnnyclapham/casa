package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompatApi23
{
  DrawableCompatApi23() {}
  
  public static int getLayoutDirection(Drawable paramDrawable)
  {
    return paramDrawable.getLayoutDirection();
  }
  
  public static void setLayoutDirection(Drawable paramDrawable, int paramInt)
  {
    paramDrawable.setLayoutDirection(paramInt);
  }
}
