package com.google.ads;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.internal.g;
import com.google.ads.util.b;
import com.google.ads.util.i.b;
import java.util.HashMap;
import java.util.Locale;

public class r
  implements o
{
  public r() {}
  
  public void a(d paramD, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str1 = (String)paramHashMap.get("u");
    if (str1 == null)
    {
      b.e("Could not get URL from click gmsg.");
      return;
    }
    g localG = paramD.n();
    String[] arrayOfString;
    if (localG != null)
    {
      Uri localUri3 = Uri.parse(str1);
      String str2 = localUri3.getHost();
      if ((str2 != null) && (str2.toLowerCase(Locale.US).endsWith(".admob.com")))
      {
        String str3 = localUri3.getPath();
        if (str3 == null) {
          break label226;
        }
        arrayOfString = str3.split("/");
        if (arrayOfString.length < 4) {
          break label226;
        }
      }
    }
    label226:
    for (String str4 = arrayOfString[2] + "/" + arrayOfString[3];; str4 = null)
    {
      localG.a(str4);
      n localN = paramD.i();
      Context localContext = (Context)localN.f.a();
      al localAl = (al)localN.m.a();
      Uri localUri1 = Uri.parse(str1);
      if (localAl.a(localUri1)) {}
      for (Uri localUri2 = localAl.a(localUri1, localContext);; localUri2 = localUri1)
      {
        new Thread(new ae(localUri2.toString(), localContext)).start();
        return;
      }
    }
  }
}
