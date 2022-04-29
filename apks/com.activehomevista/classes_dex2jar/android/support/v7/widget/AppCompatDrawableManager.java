package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.LruCache;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.drawable;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.WeakHashMap;

public final class AppCompatDrawableManager
{
  private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
  private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
  private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
  private static final ColorFilterLruCache COLOR_FILTER_CACHE;
  private static final boolean DEBUG = false;
  private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
  private static AppCompatDrawableManager INSTANCE;
  private static final String TAG = "TintManager";
  private static final int[] TINT_CHECKABLE_BUTTON_LIST;
  private static final int[] TINT_COLOR_CONTROL_NORMAL;
  private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
  private ArrayList<InflateDelegate> mDelegates;
  private WeakHashMap<Context, SparseArray<ColorStateList>> mTintLists;
  
  static
  {
    COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    int[] arrayOfInt1 = new int[3];
    arrayOfInt1[0] = R.drawable.abc_textfield_search_default_mtrl_alpha;
    arrayOfInt1[1] = R.drawable.abc_textfield_default_mtrl_alpha;
    arrayOfInt1[2] = R.drawable.abc_ab_share_pack_mtrl_alpha;
    COLORFILTER_TINT_COLOR_CONTROL_NORMAL = arrayOfInt1;
    int[] arrayOfInt2 = new int[12];
    arrayOfInt2[0] = R.drawable.abc_ic_ab_back_mtrl_am_alpha;
    arrayOfInt2[1] = R.drawable.abc_ic_go_search_api_mtrl_alpha;
    arrayOfInt2[2] = R.drawable.abc_ic_search_api_mtrl_alpha;
    arrayOfInt2[3] = R.drawable.abc_ic_commit_search_api_mtrl_alpha;
    arrayOfInt2[4] = R.drawable.abc_ic_clear_mtrl_alpha;
    arrayOfInt2[5] = R.drawable.abc_ic_menu_share_mtrl_alpha;
    arrayOfInt2[6] = R.drawable.abc_ic_menu_copy_mtrl_am_alpha;
    arrayOfInt2[7] = R.drawable.abc_ic_menu_cut_mtrl_alpha;
    arrayOfInt2[8] = R.drawable.abc_ic_menu_selectall_mtrl_alpha;
    arrayOfInt2[9] = R.drawable.abc_ic_menu_paste_mtrl_am_alpha;
    arrayOfInt2[10] = R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha;
    arrayOfInt2[11] = R.drawable.abc_ic_voice_search_api_mtrl_alpha;
    TINT_COLOR_CONTROL_NORMAL = arrayOfInt2;
    int[] arrayOfInt3 = new int[4];
    arrayOfInt3[0] = R.drawable.abc_textfield_activated_mtrl_alpha;
    arrayOfInt3[1] = R.drawable.abc_textfield_search_activated_mtrl_alpha;
    arrayOfInt3[2] = R.drawable.abc_cab_background_top_mtrl_alpha;
    arrayOfInt3[3] = R.drawable.abc_text_cursor_material;
    COLORFILTER_COLOR_CONTROL_ACTIVATED = arrayOfInt3;
    int[] arrayOfInt4 = new int[3];
    arrayOfInt4[0] = R.drawable.abc_popup_background_mtrl_mult;
    arrayOfInt4[1] = R.drawable.abc_cab_background_internal_bg;
    arrayOfInt4[2] = R.drawable.abc_menu_hardkey_panel_mtrl_mult;
    COLORFILTER_COLOR_BACKGROUND_MULTIPLY = arrayOfInt4;
    int[] arrayOfInt5 = new int[10];
    arrayOfInt5[0] = R.drawable.abc_edit_text_material;
    arrayOfInt5[1] = R.drawable.abc_tab_indicator_material;
    arrayOfInt5[2] = R.drawable.abc_textfield_search_material;
    arrayOfInt5[3] = R.drawable.abc_spinner_mtrl_am_alpha;
    arrayOfInt5[4] = R.drawable.abc_spinner_textfield_background_material;
    arrayOfInt5[5] = R.drawable.abc_ratingbar_full_material;
    arrayOfInt5[6] = R.drawable.abc_switch_track_mtrl_alpha;
    arrayOfInt5[7] = R.drawable.abc_switch_thumb_material;
    arrayOfInt5[8] = R.drawable.abc_btn_default_mtrl_shape;
    arrayOfInt5[9] = R.drawable.abc_btn_borderless_material;
    TINT_COLOR_CONTROL_STATE_LIST = arrayOfInt5;
    int[] arrayOfInt6 = new int[2];
    arrayOfInt6[0] = R.drawable.abc_btn_check_material;
    arrayOfInt6[1] = R.drawable.abc_btn_radio_material;
    TINT_CHECKABLE_BUTTON_LIST = arrayOfInt6;
  }
  
  public AppCompatDrawableManager() {}
  
  private void addTintListToCache(@NonNull Context paramContext, @DrawableRes int paramInt, @NonNull ColorStateList paramColorStateList)
  {
    if (this.mTintLists == null) {
      this.mTintLists = new WeakHashMap();
    }
    SparseArray localSparseArray = (SparseArray)this.mTintLists.get(paramContext);
    if (localSparseArray == null)
    {
      localSparseArray = new SparseArray();
      this.mTintLists.put(paramContext, localSparseArray);
    }
    localSparseArray.append(paramInt, paramColorStateList);
  }
  
  private static boolean arrayContains(int[] paramArrayOfInt, int paramInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++) {
      if (paramArrayOfInt[j] == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  private ColorStateList createButtonColorStateList(Context paramContext, int paramInt)
  {
    int[][] arrayOfInt = new int[4][];
    int[] arrayOfInt1 = new int[4];
    int i = ThemeUtils.getThemeAttrColor(paramContext, paramInt);
    int j = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlHighlight);
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorButtonNormal);
    int k = 0 + 1;
    arrayOfInt[k] = ThemeUtils.PRESSED_STATE_SET;
    arrayOfInt1[k] = ColorUtils.compositeColors(j, i);
    int m = k + 1;
    arrayOfInt[m] = ThemeUtils.FOCUSED_STATE_SET;
    arrayOfInt1[m] = ColorUtils.compositeColors(j, i);
    int n = m + 1;
    arrayOfInt[n] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[n] = i;
    (n + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private ColorStateList createCheckableButtonColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.CHECKED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private ColorStateList createColoredButtonColorStateList(Context paramContext)
  {
    return createButtonColorStateList(paramContext, R.attr.colorAccent);
  }
  
  private ColorStateList createDefaultButtonColorStateList(Context paramContext)
  {
    return createButtonColorStateList(paramContext, R.attr.colorButtonNormal);
  }
  
  private ColorStateList createDefaultColorStateList(Context paramContext)
  {
    int i = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int j = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    int[][] arrayOfInt = new int[7][];
    int[] arrayOfInt1 = new int[7];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int k = 0 + 1;
    arrayOfInt[k] = ThemeUtils.FOCUSED_STATE_SET;
    arrayOfInt1[k] = j;
    int m = k + 1;
    arrayOfInt[m] = ThemeUtils.ACTIVATED_STATE_SET;
    arrayOfInt1[m] = j;
    int n = m + 1;
    arrayOfInt[n] = ThemeUtils.PRESSED_STATE_SET;
    arrayOfInt1[n] = j;
    int i1 = n + 1;
    arrayOfInt[i1] = ThemeUtils.CHECKED_STATE_SET;
    arrayOfInt1[i1] = j;
    int i2 = i1 + 1;
    arrayOfInt[i2] = ThemeUtils.SELECTED_STATE_SET;
    arrayOfInt1[i2] = j;
    int i3 = i2 + 1;
    arrayOfInt[i3] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[i3] = i;
    (i3 + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private ColorStateList createEditTextColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private ColorStateList createSeekbarThumbColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[2][];
    int[] arrayOfInt1 = new int[2];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlActivated);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    (i + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private ColorStateList createSpinnerColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private ColorStateList createSwitchThumbColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    ColorStateList localColorStateList = ThemeUtils.getThemeAttrColorStateList(paramContext, R.attr.colorSwitchThumbNormal);
    if ((localColorStateList != null) && (localColorStateList.isStateful()))
    {
      arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
      arrayOfInt1[0] = localColorStateList.getColorForState(arrayOfInt[0], 0);
      int k = 0 + 1;
      arrayOfInt[k] = ThemeUtils.CHECKED_STATE_SET;
      arrayOfInt1[k] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
      int m = k + 1;
      arrayOfInt[m] = ThemeUtils.EMPTY_STATE_SET;
      arrayOfInt1[m] = localColorStateList.getDefaultColor();
      (m + 1);
    }
    for (;;)
    {
      return new ColorStateList(arrayOfInt, arrayOfInt1);
      arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
      arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorSwitchThumbNormal);
      int i = 0 + 1;
      arrayOfInt[i] = ThemeUtils.CHECKED_STATE_SET;
      arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
      int j = i + 1;
      arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
      arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorSwitchThumbNormal);
      (j + 1);
    }
  }
  
  private ColorStateList createSwitchTrackColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getThemeAttrColor(paramContext, 16842800, 0.1F);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.CHECKED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated, 0.3F);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, 16842800, 0.3F);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private static PorterDuffColorFilter createTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, int[] paramArrayOfInt)
  {
    if ((paramColorStateList == null) || (paramMode == null)) {
      return null;
    }
    return getPorterDuffColorFilter(paramColorStateList.getColorForState(paramArrayOfInt, 0), paramMode);
  }
  
  public static AppCompatDrawableManager get()
  {
    if (INSTANCE == null) {
      INSTANCE = new AppCompatDrawableManager();
    }
    return INSTANCE;
  }
  
  private static PorterDuffColorFilter getPorterDuffColorFilter(int paramInt, PorterDuff.Mode paramMode)
  {
    PorterDuffColorFilter localPorterDuffColorFilter = COLOR_FILTER_CACHE.get(paramInt, paramMode);
    if (localPorterDuffColorFilter == null)
    {
      localPorterDuffColorFilter = new PorterDuffColorFilter(paramInt, paramMode);
      COLOR_FILTER_CACHE.put(paramInt, paramMode, localPorterDuffColorFilter);
    }
    return localPorterDuffColorFilter;
  }
  
  private ColorStateList getTintListFromCache(@NonNull Context paramContext, @DrawableRes int paramInt)
  {
    WeakHashMap localWeakHashMap = this.mTintLists;
    ColorStateList localColorStateList = null;
    if (localWeakHashMap != null)
    {
      SparseArray localSparseArray = (SparseArray)this.mTintLists.get(paramContext);
      localColorStateList = null;
      if (localSparseArray != null) {
        localColorStateList = (ColorStateList)localSparseArray.get(paramInt);
      }
    }
    return localColorStateList;
  }
  
  private static void setPorterDuffColorFilter(Drawable paramDrawable, int paramInt, PorterDuff.Mode paramMode)
  {
    if (paramMode == null) {
      paramMode = DEFAULT_MODE;
    }
    paramDrawable.setColorFilter(getPorterDuffColorFilter(paramInt, paramMode));
  }
  
  private static boolean shouldMutateBackground(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (;;)
    {
      return true;
      if ((paramDrawable instanceof LayerDrawable))
      {
        if (Build.VERSION.SDK_INT < 16) {
          return false;
        }
      }
      else if ((paramDrawable instanceof InsetDrawable))
      {
        if (Build.VERSION.SDK_INT < 14) {
          return false;
        }
      }
      else if ((paramDrawable instanceof DrawableContainer))
      {
        Drawable.ConstantState localConstantState = paramDrawable.getConstantState();
        if ((localConstantState instanceof DrawableContainer.DrawableContainerState))
        {
          Drawable[] arrayOfDrawable = ((DrawableContainer.DrawableContainerState)localConstantState).getChildren();
          int i = arrayOfDrawable.length;
          for (int j = 0; j < i; j++) {
            if (!shouldMutateBackground(arrayOfDrawable[j])) {
              return false;
            }
          }
        }
      }
    }
  }
  
  public static void tintDrawable(Drawable paramDrawable, TintInfo paramTintInfo, int[] paramArrayOfInt)
  {
    if ((shouldMutateBackground(paramDrawable)) && (paramDrawable.mutate() != paramDrawable)) {
      Log.d("TintManager", "Mutated drawable is not the same instance as the input.");
    }
    label64:
    label93:
    label106:
    for (;;)
    {
      return;
      ColorStateList localColorStateList;
      PorterDuff.Mode localMode;
      if ((paramTintInfo.mHasTintList) || (paramTintInfo.mHasTintMode)) {
        if (paramTintInfo.mHasTintList)
        {
          localColorStateList = paramTintInfo.mTintList;
          if (!paramTintInfo.mHasTintMode) {
            break label93;
          }
          localMode = paramTintInfo.mTintMode;
          paramDrawable.setColorFilter(createTintFilter(localColorStateList, localMode, paramArrayOfInt));
        }
      }
      for (;;)
      {
        if (Build.VERSION.SDK_INT > 10) {
          break label106;
        }
        paramDrawable.invalidateSelf();
        return;
        localColorStateList = null;
        break;
        localMode = DEFAULT_MODE;
        break label64;
        paramDrawable.clearColorFilter();
      }
    }
  }
  
  public void addDelegate(@NonNull InflateDelegate paramInflateDelegate)
  {
    if (this.mDelegates == null) {
      this.mDelegates = new ArrayList();
    }
    if (!this.mDelegates.contains(paramInflateDelegate)) {
      this.mDelegates.add(paramInflateDelegate);
    }
  }
  
  public Drawable getDrawable(@NonNull Context paramContext, @DrawableRes int paramInt)
  {
    return getDrawable(paramContext, paramInt, false);
  }
  
  public Drawable getDrawable(@NonNull Context paramContext, @DrawableRes int paramInt, boolean paramBoolean)
  {
    if (this.mDelegates != null)
    {
      int i = 0;
      int j = this.mDelegates.size();
      while (i < j)
      {
        Drawable localDrawable2 = ((InflateDelegate)this.mDelegates.get(i)).onInflateDrawable(paramContext, paramInt);
        if (localDrawable2 != null) {
          return localDrawable2;
        }
        i++;
      }
    }
    Drawable localDrawable1 = ContextCompat.getDrawable(paramContext, paramInt);
    if (localDrawable1 != null)
    {
      if (Build.VERSION.SDK_INT >= 8) {
        localDrawable1 = localDrawable1.mutate();
      }
      ColorStateList localColorStateList = getTintList(paramContext, paramInt);
      if (localColorStateList == null) {
        break label137;
      }
      localDrawable1 = DrawableCompat.wrap(localDrawable1);
      DrawableCompat.setTintList(localDrawable1, localColorStateList);
      PorterDuff.Mode localMode = getTintMode(paramInt);
      if (localMode != null) {
        DrawableCompat.setTintMode(localDrawable1, localMode);
      }
    }
    for (;;)
    {
      return localDrawable1;
      label137:
      if (paramInt == R.drawable.abc_cab_background_top_material)
      {
        Drawable[] arrayOfDrawable = new Drawable[2];
        arrayOfDrawable[0] = getDrawable(paramContext, R.drawable.abc_cab_background_internal_bg);
        arrayOfDrawable[1] = getDrawable(paramContext, R.drawable.abc_cab_background_top_mtrl_alpha);
        return new LayerDrawable(arrayOfDrawable);
      }
      if (paramInt == R.drawable.abc_seekbar_track_material)
      {
        LayerDrawable localLayerDrawable = (LayerDrawable)localDrawable1;
        setPorterDuffColorFilter(localLayerDrawable.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal), DEFAULT_MODE);
        setPorterDuffColorFilter(localLayerDrawable.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal), DEFAULT_MODE);
        setPorterDuffColorFilter(localLayerDrawable.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated), DEFAULT_MODE);
      }
      else if ((!tintDrawableUsingColorFilter(paramContext, paramInt, localDrawable1)) && (paramBoolean))
      {
        localDrawable1 = null;
      }
    }
  }
  
  public final ColorStateList getTintList(@NonNull Context paramContext, @DrawableRes int paramInt)
  {
    ColorStateList localColorStateList = getTintListFromCache(paramContext, paramInt);
    if (localColorStateList == null)
    {
      if (paramInt != R.drawable.abc_edit_text_material) {
        break label37;
      }
      localColorStateList = createEditTextColorStateList(paramContext);
    }
    for (;;)
    {
      if (localColorStateList != null) {
        addTintListToCache(paramContext, paramInt, localColorStateList);
      }
      return localColorStateList;
      label37:
      if (paramInt == R.drawable.abc_switch_track_mtrl_alpha) {
        localColorStateList = createSwitchTrackColorStateList(paramContext);
      } else if (paramInt == R.drawable.abc_switch_thumb_material) {
        localColorStateList = createSwitchThumbColorStateList(paramContext);
      } else if ((paramInt == R.drawable.abc_btn_default_mtrl_shape) || (paramInt == R.drawable.abc_btn_borderless_material)) {
        localColorStateList = createDefaultButtonColorStateList(paramContext);
      } else if (paramInt == R.drawable.abc_btn_colored_material) {
        localColorStateList = createColoredButtonColorStateList(paramContext);
      } else if ((paramInt == R.drawable.abc_spinner_mtrl_am_alpha) || (paramInt == R.drawable.abc_spinner_textfield_background_material)) {
        localColorStateList = createSpinnerColorStateList(paramContext);
      } else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, paramInt)) {
        localColorStateList = ThemeUtils.getThemeAttrColorStateList(paramContext, R.attr.colorControlNormal);
      } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, paramInt)) {
        localColorStateList = createDefaultColorStateList(paramContext);
      } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, paramInt)) {
        localColorStateList = createCheckableButtonColorStateList(paramContext);
      } else if (paramInt == R.drawable.abc_seekbar_thumb_material) {
        localColorStateList = createSeekbarThumbColorStateList(paramContext);
      }
    }
  }
  
  final PorterDuff.Mode getTintMode(int paramInt)
  {
    int i = R.drawable.abc_switch_thumb_material;
    PorterDuff.Mode localMode = null;
    if (paramInt == i) {
      localMode = PorterDuff.Mode.MULTIPLY;
    }
    return localMode;
  }
  
  public void removeDelegate(@NonNull InflateDelegate paramInflateDelegate)
  {
    if (this.mDelegates != null) {
      this.mDelegates.remove(paramInflateDelegate);
    }
  }
  
  public final boolean tintDrawableUsingColorFilter(@NonNull Context paramContext, @DrawableRes int paramInt, @NonNull Drawable paramDrawable)
  {
    PorterDuff.Mode localMode = DEFAULT_MODE;
    int i = -1;
    int k;
    int m;
    if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, paramInt))
    {
      k = R.attr.colorControlNormal;
      m = 1;
    }
    while (m != 0)
    {
      paramDrawable.setColorFilter(getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(paramContext, k), localMode));
      if (i != -1) {
        paramDrawable.setAlpha(i);
      }
      return true;
      if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, paramInt))
      {
        k = R.attr.colorControlActivated;
        m = 1;
      }
      else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, paramInt))
      {
        k = 16842801;
        m = 1;
        localMode = PorterDuff.Mode.MULTIPLY;
      }
      else
      {
        int j = R.drawable.abc_list_divider_mtrl_alpha;
        k = 0;
        m = 0;
        if (paramInt == j)
        {
          k = 16842800;
          m = 1;
          i = Math.round(40.8F);
        }
      }
    }
    return false;
  }
  
  private static class ColorFilterLruCache
    extends LruCache<Integer, PorterDuffColorFilter>
  {
    public ColorFilterLruCache(int paramInt)
    {
      super();
    }
    
    private static int generateCacheKey(int paramInt, PorterDuff.Mode paramMode)
    {
      return 31 * (paramInt + 31) + paramMode.hashCode();
    }
    
    PorterDuffColorFilter get(int paramInt, PorterDuff.Mode paramMode)
    {
      return (PorterDuffColorFilter)get(Integer.valueOf(generateCacheKey(paramInt, paramMode)));
    }
    
    PorterDuffColorFilter put(int paramInt, PorterDuff.Mode paramMode, PorterDuffColorFilter paramPorterDuffColorFilter)
    {
      return (PorterDuffColorFilter)put(Integer.valueOf(generateCacheKey(paramInt, paramMode)), paramPorterDuffColorFilter);
    }
  }
  
  public static abstract interface InflateDelegate
  {
    @Nullable
    public abstract Drawable onInflateDrawable(@NonNull Context paramContext, @DrawableRes int paramInt);
  }
}