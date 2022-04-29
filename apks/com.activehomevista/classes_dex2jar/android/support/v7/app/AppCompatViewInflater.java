/*
 * Decompiled with CFR 0_96.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.util.Map;

class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final Map<String, Constructor<? extends View>> sConstructorMap;
    private static final Class<?>[] sConstructorSignature;
    private static final int[] sOnClickAttrs;
    private final Object[] mConstructorArgs = new Object[2];

    static {
        sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sOnClickAttrs = new int[]{16843375};
        sConstructorMap = new ArrayMap<String, Constructor<? extends View>>();
    }

    AppCompatViewInflater() {
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        Context context = view.getContext();
        if (!(ViewCompat.hasOnClickListeners(view) && context instanceof ContextWrapper)) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, sOnClickAttrs);
        String string = typedArray.getString(0);
        if (string != null) {
            view.setOnClickListener((View.OnClickListener)new DeclaredOnClickListener(view, string));
        }
        typedArray.recycle();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    private View createView(Context var1_1, String var2_2, String var3_3) throws ClassNotFoundException, InflateException {
        var4_4 /* !! */  = AppCompatViewInflater.sConstructorMap.get(var2_2);
        if (var4_4 /* !! */  != null) ** GOTO lbl8
        try {
            var7_5 = var1_1.getClassLoader();
            var8_6 = var3_3 != null ? var3_3 + var2_2 : var2_2;
            var4_4 /* !! */  = var7_5.loadClass(var8_6).asSubclass(View.class).getConstructor(AppCompatViewInflater.sConstructorSignature);
            AppCompatViewInflater.sConstructorMap.put(var2_2, var4_4 /* !! */ );
lbl8: // 2 sources:
            var4_4 /* !! */ .setAccessible(true);
            return var4_4 /* !! */ .newInstance(this.mConstructorArgs);
        }
        catch (Exception var5_8) {
            return null;
        }
    }

    private View createViewFromTag(Context context, String string, AttributeSet attributeSet) {
        if (string.equals("view")) {
            string = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attributeSet;
            if (-1 == string.indexOf(46)) {
                View view = this.createView(context, string, "android.widget.");
                return view;
            }
            View view = this.createView(context, string, null);
            return view;
        }
        catch (Exception var5_6) {
            return null;
        }
        finally {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
        }
    }

    private static Context themifyContext(Context context, AttributeSet attributeSet, boolean bl, boolean bl2) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.View, 0, 0);
        int n = 0;
        if (bl) {
            n = typedArray.getResourceId(R.styleable.View_android_theme, 0);
        }
        if (bl2 && n == 0 && (n = typedArray.getResourceId(R.styleable.View_theme, 0)) != 0) {
            Log.i((String)"AppCompatViewInflater", (String)"app:theme is now deprecated. Please move to using android:theme instead.");
        }
        typedArray.recycle();
        if (!(n == 0 || context instanceof ContextThemeWrapper && ((ContextThemeWrapper)context).getThemeResId() == n)) {
            context = new ContextThemeWrapper(context, n);
        }
        return context;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public final View createView(View var1_1, String var2_2, @NonNull Context var3_3, @NonNull AttributeSet var4_4, boolean var5_5, boolean var6_6, boolean var7_7) {
        var8_8 = var3_3;
        if (var5_5 && var1_1 != null) {
            var3_3 = var1_1.getContext();
        }
        if (var6_6 || var7_7) {
            var3_3 = AppCompatViewInflater.themifyContext(var3_3, var4_4, var6_6, var7_7);
        }
        var9_9 = -1;
        switch (var2_2.hashCode()) {
            case -938935918: {
                if (var2_2.equals("TextView")) {
                    var9_9 = 0;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case 1125864064: {
                if (var2_2.equals("ImageView")) {
                    var9_9 = 1;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case 2001146706: {
                if (var2_2.equals("Button")) {
                    var9_9 = 2;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case 1666676343: {
                if (var2_2.equals("EditText")) {
                    var9_9 = 3;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case -339785223: {
                if (var2_2.equals("Spinner")) {
                    var9_9 = 4;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case -937446323: {
                if (var2_2.equals("ImageButton")) {
                    var9_9 = 5;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case 1601505219: {
                if (var2_2.equals("CheckBox")) {
                    var9_9 = 6;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case 776382189: {
                if (var2_2.equals("RadioButton")) {
                    var9_9 = 7;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case -1455429095: {
                if (var2_2.equals("CheckedTextView")) {
                    var9_9 = 8;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case 1413872058: {
                if (var2_2.equals("AutoCompleteTextView")) {
                    var9_9 = 9;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case -1346021293: {
                if (var2_2.equals("MultiAutoCompleteTextView")) {
                    var9_9 = 10;
                    ** break;
                } else {
                    ** GOTO lbl55
                }
            }
            case -1946472170: {
                if (var2_2.equals("RatingBar")) {
                    var9_9 = 11;
                }
            }
lbl55: // 37 sources:
            default: {
                ** GOTO lbl60
            }
            case -658531749: 
        }
        if (var2_2.equals("SeekBar")) {
            var9_9 = 12;
        }
lbl60: // 4 sources:
        var10_10 = null;
        switch (var9_9) {
            case 0: {
                var10_10 = new AppCompatTextView(var3_3, var4_4);
                ** break;
            }
            case 1: {
                var10_10 = new AppCompatImageView(var3_3, var4_4);
                ** break;
            }
            case 2: {
                var10_10 = new AppCompatButton(var3_3, var4_4);
                ** break;
            }
            case 3: {
                var10_10 = new AppCompatEditText(var3_3, var4_4);
                ** break;
            }
            case 4: {
                var10_10 = new AppCompatSpinner(var3_3, var4_4);
                ** break;
            }
            case 5: {
                var10_10 = new AppCompatImageButton(var3_3, var4_4);
                ** break;
            }
            case 6: {
                var10_10 = new AppCompatCheckBox(var3_3, var4_4);
                ** break;
            }
            case 7: {
                var10_10 = new AppCompatRadioButton(var3_3, var4_4);
                ** break;
            }
            case 8: {
                var10_10 = new AppCompatCheckedTextView(var3_3, var4_4);
                ** break;
            }
            case 9: {
                var10_10 = new AppCompatAutoCompleteTextView(var3_3, var4_4);
                ** break;
            }
            case 10: {
                var10_10 = new AppCompatMultiAutoCompleteTextView(var3_3, var4_4);
                ** break;
            }
            case 11: {
                var10_10 = new AppCompatRatingBar(var3_3, var4_4);
            }
lbl97: // 13 sources:
            default: {
                ** GOTO lbl101
            }
            case 12: 
        }
        var10_10 = new AppCompatSeekBar(var3_3, var4_4);
lbl101: // 2 sources:
        if (var10_10 == null && var8_8 != var3_3) {
            var10_10 = this.createViewFromTag(var3_3, var2_2, var4_4);
        }
        if (var10_10 == null) return var10_10;
        this.checkOnClickListener((View)var10_10, var4_4);
        return var10_10;
    }

    /*
     * Failed to analyse overrides
     */
    private static class DeclaredOnClickListener
    implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View view, @NonNull String string) {
            this.mHostView = view;
            this.mMethodName = string;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         */
        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String string) {
            String string2;
            while (context != null) {
                try {
                    Method method;
                    if (!(context.isRestricted() || (method = context.getClass().getMethod(this.mMethodName, View.class)) == null)) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                }
                catch (NoSuchMethodException var5_3) {
                    // empty catch block
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper)context).getBaseContext();
                    continue;
                }
                context = null;
            }
            int n = this.mHostView.getId();
            if (n == -1) {
                string2 = "";
                throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + string2);
            }
            string2 = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(n) + "'";
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + string2);
        }

        public void onClick(@NonNull View view) {
            if (this.mResolvedMethod == null) {
                this.resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke((Object)this.mResolvedContext, new Object[]{view});
                return;
            }
            catch (IllegalAccessException var3_2) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", var3_2);
            }
            catch (InvocationTargetException var2_3) {
                throw new IllegalStateException("Could not execute method for android:onClick", var2_3);
            }
        }
    }

}

