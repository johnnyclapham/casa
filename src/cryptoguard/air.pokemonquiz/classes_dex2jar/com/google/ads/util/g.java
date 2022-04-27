/*
 * Decompiled with CFR 0_96.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.graphics.Paint
 *  android.text.Editable
 *  android.view.View
 *  android.view.Window
 *  android.webkit.ConsoleMessage
 *  android.webkit.ConsoleMessage$MessageLevel
 *  android.webkit.JsPromptResult
 *  android.webkit.JsResult
 *  android.webkit.WebChromeClient
 *  android.webkit.WebChromeClient$CustomViewCallback
 *  android.webkit.WebResourceResponse
 *  android.webkit.WebSettings
 *  android.webkit.WebStorage
 *  android.webkit.WebStorage$QuotaUpdater
 *  android.webkit.WebView
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.ads.util;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.ads.AdActivity;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.c;
import com.google.ads.internal.d;
import com.google.ads.internal.i;
import com.google.ads.m;
import com.google.ads.n;
import com.google.ads.o;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.i;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@TargetApi(value=11)
public class g {
    public static void a(View view) {
        view.setLayerType(1, null);
    }

    public static void a(Window window) {
        window.setFlags(16777216, 16777216);
    }

    public static void a(WebSettings webSettings, n n) {
        Context context = n.f.a();
        m.a a = n.d.a().a.a();
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(a.i.a().longValue());
        webSettings.setAppCachePath(new File(context.getCacheDir(), "admob").getAbsolutePath());
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(context.getDatabasePath("admob").getAbsolutePath());
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
    }

    public static void b(View view) {
        view.setLayerType(0, null);
    }

    /*
     * Failed to analyse overrides
     */
    public static class a
    extends WebChromeClient {
        private final n a;

        public a(n n) {
            this.a = n;
        }

        private static void a(AlertDialog.Builder builder, Context context, String string, String string2, final JsPromptResult jsPromptResult) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            TextView textView = new TextView(context);
            textView.setText((CharSequence)string);
            final EditText editText = new EditText(context);
            editText.setText((CharSequence)string2);
            linearLayout.addView((View)textView);
            linearLayout.addView((View)editText);
            builder.setView((View)linearLayout).setPositiveButton(17039370, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    jsPromptResult.confirm(editText.getText().toString());
                }
            }).setNegativeButton(17039360, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    jsPromptResult.cancel();
                }
            }).setOnCancelListener((DialogInterface.OnCancelListener)new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    jsPromptResult.cancel();
                }
            }).create().show();
        }

        private static void a(AlertDialog.Builder builder, String string, final JsResult jsResult) {
            builder.setMessage((CharSequence)string).setPositiveButton(17039370, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    jsResult.confirm();
                }
            }).setNegativeButton(17039360, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    jsResult.cancel();
                }
            }).setOnCancelListener((DialogInterface.OnCancelListener)new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    jsResult.cancel();
                }
            }).create().show();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static boolean a(WebView webView, String string, String string2, String string3, JsResult jsResult, JsPromptResult jsPromptResult, boolean bl) {
            AdActivity adActivity;
            if (!(webView instanceof AdWebView) || (adActivity = ((AdWebView)webView).i()) == null) return false;
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)adActivity);
            builder.setTitle((CharSequence)string);
            if (bl) {
                a.a(builder, (Context)adActivity, string2, string3, jsPromptResult);
                do {
                    return true;
                    break;
                } while (true);
            }
            a.a(builder, string2, jsResult);
            return true;
        }

        public void onCloseWindow(WebView webView) {
            if (webView instanceof AdWebView) {
                ((AdWebView)webView).f();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean onConsoleMessage(ConsoleMessage var1_1) {
            var2_2 = "JS: " + var1_1.message() + " (" + var1_1.sourceId() + ":" + var1_1.lineNumber() + ")";
            switch (com.google.ads.util.g$1.a[var1_1.messageLevel().ordinal()]) {
                case 1: {
                    com.google.ads.util.b.b(var2_2);
                    ** break;
                }
                case 2: {
                    com.google.ads.util.b.e(var2_2);
                    ** break;
                }
                case 3: 
                case 4: {
                    com.google.ads.util.b.c(var2_2);
                }
lbl11: // 4 sources:
                default: {
                    return super.onConsoleMessage(var1_1);
                }
                case 5: 
            }
            com.google.ads.util.b.a(var2_2);
            return super.onConsoleMessage(var1_1);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onExceededDatabaseQuota(String string, String string2, long l, long l2, long l3, WebStorage.QuotaUpdater quotaUpdater) {
            m.a a = this.a.d.a().a.a();
            long l4 = a.l.a() - l3;
            if (l4 <= 0) {
                quotaUpdater.updateQuota(l);
                return;
            }
            long l5 = l == 0 ? (l2 <= l4 && l2 <= a.m.a() ? l2 : 0) : (l2 == 0 ? Math.min(l + Math.min(a.n.a(), l4), a.m.a()) : (l2 <= Math.min(a.m.a() - l, l4) ? l + l2 : l));
            quotaUpdater.updateQuota(l5);
        }

        public boolean onJsAlert(WebView webView, String string, String string2, JsResult jsResult) {
            return a.a(webView, string, string2, null, jsResult, null, false);
        }

        public boolean onJsBeforeUnload(WebView webView, String string, String string2, JsResult jsResult) {
            return a.a(webView, string, string2, null, jsResult, null, false);
        }

        public boolean onJsConfirm(WebView webView, String string, String string2, JsResult jsResult) {
            return a.a(webView, string, string2, null, jsResult, null, false);
        }

        public boolean onJsPrompt(WebView webView, String string, String string2, String string3, JsPromptResult jsPromptResult) {
            return a.a(webView, string, string2, string3, null, jsPromptResult, true);
        }

        public void onReachedMaxAppCacheSize(long l, long l2, WebStorage.QuotaUpdater quotaUpdater) {
            long l3;
            m.a a = this.a.d.a().a.a();
            long l4 = a.k.a() - l2;
            if (l4 < (l3 = l + a.j.a())) {
                quotaUpdater.updateQuota(0);
                return;
            }
            quotaUpdater.updateQuota(l3);
        }

        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
            customViewCallback.onCustomViewHidden();
        }

    }

    /*
     * Failed to analyse overrides
     */
    public static class b
    extends i {
        public b(d d, Map<String, o> map, boolean bl, boolean bl2) {
            super(d, map, bl, bl2);
        }

        private static WebResourceResponse a(String string, Context context) throws IOException {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string).openConnection();
            try {
                AdUtil.a(httpURLConnection, context.getApplicationContext());
                httpURLConnection.connect();
                WebResourceResponse webResourceResponse = new WebResourceResponse("application/javascript", "UTF-8", (InputStream)new ByteArrayInputStream(AdUtil.a(new InputStreamReader(httpURLConnection.getInputStream())).getBytes("UTF-8")));
                return webResourceResponse;
            }
            finally {
                httpURLConnection.disconnect();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Lifted jumps to return sites
         */
        public WebResourceResponse shouldInterceptRequest(WebView var1_1, String var2_2) {
            try {
                if ("mraid.js".equalsIgnoreCase(new File(var2_2).getName()) == false) return super.shouldInterceptRequest(var1_1, var2_2);
                var5_3 = this.a.k();
                if (var5_3 != null) {
                    var5_3.c(true);
                } else {
                    this.a.a(true);
                }
                var6_4 = this.a.i().d.a().a.a();
                if (!this.a.i().b()) {
                    if (this.b) {
                        var10_5 = var6_4.f.a();
                        com.google.ads.util.b.a("shouldInterceptRequest(" + var10_5 + ")");
                        return b.a(var10_5, var1_1.getContext());
                    }
                    var9_7 = var6_4.e.a();
                    com.google.ads.util.b.a("shouldInterceptRequest(" + var9_7 + ")");
                    return b.a(var9_7, var1_1.getContext());
                } else {
                    ** GOTO lbl20
                }
            }
            catch (IOException var4_6) {
                com.google.ads.util.b.d("IOException fetching MRAID JS.", var4_6);
            }
            return super.shouldInterceptRequest(var1_1, var2_2);
lbl20: // 2 sources:
            var7_8 = var6_4.g.a();
            com.google.ads.util.b.a("shouldInterceptRequest(" + var7_8 + ")");
            return b.a(var7_8, var1_1.getContext());
            catch (Throwable var3_10) {
                com.google.ads.util.b.d("An unknown error occurred fetching MRAID JS.", var3_10);
                return super.shouldInterceptRequest(var1_1, var2_2);
            }
        }
    }

}

