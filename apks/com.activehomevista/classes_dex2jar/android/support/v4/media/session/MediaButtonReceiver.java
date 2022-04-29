package android.support.v4.media.session;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.view.KeyEvent;
import java.util.List;

public class MediaButtonReceiver
  extends BroadcastReceiver
{
  public MediaButtonReceiver() {}
  
  public static KeyEvent handleIntent(MediaSessionCompat paramMediaSessionCompat, Intent paramIntent)
  {
    if ((paramMediaSessionCompat == null) || (paramIntent == null) || (!"android.intent.action.MEDIA_BUTTON".equals(paramIntent.getAction())) || (!paramIntent.hasExtra("android.intent.extra.KEY_EVENT"))) {
      return null;
    }
    KeyEvent localKeyEvent = (KeyEvent)paramIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
    paramMediaSessionCompat.getController().dispatchMediaButtonEvent(localKeyEvent);
    return localKeyEvent;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Intent localIntent = new Intent("android.intent.action.MEDIA_BUTTON");
    localIntent.setPackage(paramContext.getPackageName());
    List localList = paramContext.getPackageManager().queryIntentServices(localIntent, 0);
    if (localList.size() != 1) {
      throw new IllegalStateException("Expected 1 Service that handles android.intent.action.MEDIA_BUTTON, found " + localList.size());
    }
    ResolveInfo localResolveInfo = (ResolveInfo)localList.get(0);
    paramIntent.setComponent(new ComponentName(localResolveInfo.serviceInfo.packageName, localResolveInfo.serviceInfo.name));
    paramContext.startService(paramIntent);
  }
}
