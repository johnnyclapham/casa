package com.activehomevista;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class SizeChangingListView
  extends ListView
{
  public SizeChangingListView(Context paramContext)
  {
    super(paramContext);
  }
  
  public SizeChangingListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SizeChangingListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (getCheckedItemPosition() != -1) {
      setSelection(getCheckedItemPosition());
    }
  }
}
