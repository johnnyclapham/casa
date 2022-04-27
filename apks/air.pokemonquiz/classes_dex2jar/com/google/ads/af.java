package com.google.ads;

import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.lang.ref.WeakReference;

public class af
  implements Runnable
{
  private WeakReference<d> a;
  
  public af(d paramD)
  {
    this.a = new WeakReference(paramD);
  }
  
  public void run()
  {
    d localD = (d)this.a.get();
    if (localD == null)
    {
      b.a("The ad must be gone, so cancelling the refresh timer.");
      return;
    }
    localD.z();
  }
}
