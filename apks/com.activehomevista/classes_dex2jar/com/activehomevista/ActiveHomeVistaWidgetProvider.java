package com.activehomevista;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.activehomevista.service.ActiveHomeVistaService;

public class ActiveHomeVistaWidgetProvider
  extends AppWidgetProvider
{
  public ActiveHomeVistaWidgetProvider() {}
  
  public static RemoteViews createWidgetView(Context paramContext, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, ActiveHomeVistaService.class);
    localIntent.setAction("com.activehomevista.APPWIDGET_CLICK");
    localIntent.putExtra("appWidgetId", paramInt);
    PendingIntent localPendingIntent = PendingIntent.getService(paramContext, paramInt, localIntent, 0);
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130903067);
    localRemoteViews.setOnClickPendingIntent(2131558490, localPendingIntent);
    return localRemoteViews;
  }
  
  public static void refreshWidget(Context paramContext, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, ActiveHomeVistaService.class);
    localIntent.setAction("com.activehomevista.APPWIDGET_REFRESH");
    localIntent.putExtra("appWidgetId", paramInt);
    PendingIntent localPendingIntent = PendingIntent.getService(paramContext, paramInt, localIntent, 0);
    try
    {
      localPendingIntent.send();
      return;
    }
    catch (PendingIntent.CanceledException localCanceledException)
    {
      localCanceledException.printStackTrace();
    }
  }
  
  public void onDeleted(Context paramContext, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++) {
      ConfigureWidget.removeAll(paramContext, paramArrayOfInt[j]);
    }
    super.onDeleted(paramContext, paramArrayOfInt);
  }
  
  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++) {
      refreshWidget(paramContext, paramArrayOfInt[j]);
    }
    super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfInt);
  }
}
