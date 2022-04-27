/*
 * Decompiled with CFR 0_96.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  dalvik.system.DexClassLoader
 */
package com.google.ads;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.ads.aj;
import com.google.ads.am;
import com.google.ads.an;
import com.google.ads.ao;
import com.google.ads.ap;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ak
extends aj {
    public static boolean c;
    private static Method d;
    private static Method e;
    private static Method f;
    private static Method g;
    private static Method h;
    private static String i;
    private static long j;

    static {
        d = null;
        e = null;
        f = null;
        g = null;
        h = null;
        i = null;
        j = 0;
        c = false;
    }

    protected ak(Context context) {
        super(context);
    }

    public static ak a(String string, Context context) {
        ak.b(string, context);
        return new ak(context);
    }

    public static String a() throws a {
        if (i == null) {
            throw new a();
        }
        return i;
    }

    public static ArrayList<Long> a(MotionEvent motionEvent, DisplayMetrics displayMetrics) throws a {
        if (g == null || motionEvent == null) {
            throw new a();
        }
        try {
            ArrayList arrayList = (ArrayList)g.invoke(null, new Object[]{motionEvent, displayMetrics});
            return arrayList;
        }
        catch (IllegalAccessException var3_3) {
            throw new a(var3_3);
        }
        catch (InvocationTargetException var2_4) {
            throw new a(var2_4);
        }
    }

    public static Long b() throws a {
        if (d == null) {
            throw new a();
        }
        try {
            Long l = (Long)d.invoke(null, new Object[0]);
            return l;
        }
        catch (IllegalAccessException var1_1) {
            throw new a(var1_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    private static String b(byte[] arrby, String string) throws a {
        try {
            String string2 = new String(am.a(arrby, string), "UTF-8");
            return string2;
        }
        catch (am.a var5_3) {
            throw new a(var5_3);
        }
        catch (ao var4_4) {
            throw new a(var4_4);
        }
        catch (UnsupportedEncodingException var3_5) {
            throw new a(var3_5);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public static void b(String string, Context context) {
        synchronized (ak.class) {
            boolean bl = c;
            if (!bl) {
                try {
                    i = string;
                    ak.f(context);
                    j = ak.b();
                    c = true;
                }
                catch (UnsupportedOperationException var5_3) {
                }
                catch (a var4_4) {}
            }
            return;
        }
    }

    public static String c() throws a {
        if (e == null) {
            throw new a();
        }
        try {
            String string = (String)e.invoke(null, new Object[0]);
            return string;
        }
        catch (IllegalAccessException var1_1) {
            throw new a(var1_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String d(Context context) throws a {
        if (h == null) {
            throw new a();
        }
        String string = (String)h.invoke(null, new Object[]{context});
        if (string != null) return string;
        try {
            throw new a();
        }
        catch (IllegalAccessException var2_2) {
            throw new a(var2_2);
        }
        catch (InvocationTargetException var1_3) {
            throw new a(var1_3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public static String e(Context context) throws a {
        if (f == null) {
            throw new a();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer)f.invoke(null, new Object[]{context});
            if (byteBuffer != null) return ap.a(byteBuffer.array(), false);
            throw new a();
        }
        catch (IllegalAccessException var2_2) {
            throw new a(var2_2);
        }
        catch (InvocationTargetException var1_4) {
            throw new a(var1_4);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    private static void f(Context context) throws a {
        try {
            byte[] arrby = am.a(an.a());
            byte[] arrby2 = am.a(arrby, an.b());
            File file = context.getCacheDir();
            if (file == null && (file = context.getDir("dex", 0)) == null) {
                throw new a();
            }
            File file2 = File.createTempFile("ads", ".jar", file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(arrby2, 0, arrby2.length);
            fileOutputStream.close();
            DexClassLoader dexClassLoader = new DexClassLoader(file2.getAbsolutePath(), file.getAbsolutePath(), null, context.getClassLoader());
            Class class_ = dexClassLoader.loadClass(ak.b(arrby, an.c()));
            Class class_2 = dexClassLoader.loadClass(ak.b(arrby, an.i()));
            Class class_3 = dexClassLoader.loadClass(ak.b(arrby, an.g()));
            Class class_4 = dexClassLoader.loadClass(ak.b(arrby, an.k()));
            Class class_5 = dexClassLoader.loadClass(ak.b(arrby, an.e()));
            d = class_.getMethod(ak.b(arrby, an.d()), new Class[0]);
            e = class_2.getMethod(ak.b(arrby, an.j()), new Class[0]);
            f = class_3.getMethod(ak.b(arrby, an.h()), Context.class);
            g = class_4.getMethod(ak.b(arrby, an.l()), MotionEvent.class, DisplayMetrics.class);
            h = class_5.getMethod(ak.b(arrby, an.f()), Context.class);
            String string = file2.getName();
            file2.delete();
            new File(file, string.replace((CharSequence)".jar", (CharSequence)".dex")).delete();
            return;
        }
        catch (ao var7_4) {
            throw new a(var7_4);
        }
        catch (FileNotFoundException var6_14) {
            throw new a(var6_14);
        }
        catch (IOException var5_15) {
            throw new a(var5_15);
        }
        catch (ClassNotFoundException var4_16) {
            throw new a(var4_16);
        }
        catch (am.a var3_17) {
            throw new a(var3_17);
        }
        catch (NoSuchMethodException var2_18) {
            throw new a(var2_18);
        }
        catch (NullPointerException var1_19) {
            throw new a(var1_19);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    @Override
    protected void b(Context context) {
        block10 : {
            try {
                this.a(1, ak.c());
                break block10;
            }
            catch (IOException var6_2) {
                return;
            }
            catch (a a) {}
        }
        try {
            this.a(2, ak.a());
        }
        catch (a var3_5) {}
        try {
            this.a(25, ak.b());
        }
        catch (a var4_4) {}
        try {
            this.a(24, ak.d(context));
            return;
        }
        catch (a var5_3) {
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    @Override
    protected void c(Context context) {
        block16 : {
            try {
                this.a(2, ak.a());
                break block16;
            }
            catch (IOException var7_4) {
                return;
            }
            catch (a a) {}
        }
        try {
            this.a(1, ak.c());
        }
        catch (a var3_8) {}
        try {
            long l = ak.b();
            this.a(25, l);
            if (j != 0) {
                this.a(17, l - j);
                this.a(23, j);
            }
        }
        catch (a var4_7) {}
        try {
            ArrayList<Long> arrayList = ak.a(this.a, this.b);
            this.a(14, arrayList.get(0));
            this.a(15, arrayList.get(1));
            if (arrayList.size() >= 3) {
                this.a(16, arrayList.get(2));
            }
        }
        catch (a var5_6) {}
        try {
            this.a(27, ak.e(context));
            return;
        }
        catch (a var6_5) {
            return;
        }
    }

    public static class a
    extends Exception {
        public a() {
        }

        public a(Throwable throwable) {
            super(throwable);
        }
    }

}

