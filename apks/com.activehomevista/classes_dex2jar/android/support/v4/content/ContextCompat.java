/*
 * Decompiled with CFR 0_96.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Process
 *  android.util.Log
 */
package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompatApi21;
import android.support.v4.content.ContextCompatApi23;
import android.support.v4.content.ContextCompatFroyo;
import android.support.v4.content.ContextCompatHoneycomb;
import android.support.v4.content.ContextCompatJellybean;
import android.support.v4.content.ContextCompatKitKat;
import android.util.Log;
import java.io.File;

public class ContextCompat {
    private static final String DIR_ANDROID = "Android";
    private static final String DIR_CACHE = "cache";
    private static final String DIR_DATA = "data";
    private static final String DIR_FILES = "files";
    private static final String DIR_OBB = "obb";
    private static final String TAG = "ContextCompat";

    /*
     * Enabled aggressive block sorting
     */
    private static /* varargs */ File buildPath(File file, String ... arrstring) {
        int n = arrstring.length;
        int n2 = 0;
        File file2 = file;
        while (n2 < n) {
            String string = arrstring[n2];
            File file3 = file2 == null ? new File(string) : (string != null ? new File(file2, string) : file2);
            ++n2;
            file2 = file3;
        }
        return file2;
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String string) {
        if (string == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(string, Process.myPid(), Process.myUid());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static File createFilesDir(File file) {
        synchronized (ContextCompat.class) {
            block4 : {
                if (file.exists()) return file;
                if (file.mkdirs()) return file;
                boolean bl = file.exists();
                if (!bl) break block4;
                return file;
            }
            Log.w((String)"ContextCompat", (String)("Unable to create files subdir " + file.getPath()));
            return null;
        }
    }

    public static final int getColor(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ContextCompatApi23.getColor(context, n);
        }
        return context.getResources().getColor(n);
    }

    public static final ColorStateList getColorStateList(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ContextCompatApi23.getColorStateList(context, n);
        }
        return context.getResources().getColorStateList(n);
    }

    public static final Drawable getDrawable(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.getDrawable(context, n);
        }
        return context.getResources().getDrawable(n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File[] getExternalCacheDirs(Context context) {
        File file;
        int n = Build.VERSION.SDK_INT;
        if (n >= 19) {
            return ContextCompatKitKat.getExternalCacheDirs(context);
        }
        if (n >= 8) {
            file = ContextCompatFroyo.getExternalCacheDir(context);
            do {
                return new File[]{file};
                break;
            } while (true);
        }
        File file2 = Environment.getExternalStorageDirectory();
        String[] arrstring = new String[]{"Android", "data", context.getPackageName(), "cache"};
        file = ContextCompat.buildPath(file2, arrstring);
        return new File[]{file};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File[] getExternalFilesDirs(Context context, String string) {
        File file;
        int n = Build.VERSION.SDK_INT;
        if (n >= 19) {
            return ContextCompatKitKat.getExternalFilesDirs(context, string);
        }
        if (n >= 8) {
            file = ContextCompatFroyo.getExternalFilesDir(context, string);
            do {
                return new File[]{file};
                break;
            } while (true);
        }
        File file2 = Environment.getExternalStorageDirectory();
        String[] arrstring = new String[]{"Android", "data", context.getPackageName(), "files", string};
        file = ContextCompat.buildPath(file2, arrstring);
        return new File[]{file};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File[] getObbDirs(Context context) {
        File file;
        int n = Build.VERSION.SDK_INT;
        if (n >= 19) {
            return ContextCompatKitKat.getObbDirs(context);
        }
        if (n >= 11) {
            file = ContextCompatHoneycomb.getObbDir(context);
            do {
                return new File[]{file};
                break;
            } while (true);
        }
        File file2 = Environment.getExternalStorageDirectory();
        String[] arrstring = new String[]{"Android", "obb", context.getPackageName()};
        file = ContextCompat.buildPath(file2, arrstring);
        return new File[]{file};
    }

    public static boolean startActivities(Context context, Intent[] arrintent) {
        return ContextCompat.startActivities(context, arrintent, null);
    }

    public static boolean startActivities(Context context, Intent[] arrintent, Bundle bundle) {
        int n = Build.VERSION.SDK_INT;
        if (n >= 16) {
            ContextCompatJellybean.startActivities(context, arrintent, bundle);
            return true;
        }
        if (n >= 11) {
            ContextCompatHoneycomb.startActivities(context, arrintent);
            return true;
        }
        return false;
    }

    public final File getCodeCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.getCodeCacheDir(context);
        }
        return ContextCompat.createFilesDir(new File(context.getApplicationInfo().dataDir, "code_cache"));
    }

    public final File getNoBackupFilesDir(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.getNoBackupFilesDir(context);
        }
        return ContextCompat.createFilesDir(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }
}

