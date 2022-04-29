package com.activehomevista;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

class RoomListAdapter
  extends BaseAdapter
{
  private final Boolean darkTheme;
  private final LayoutInflater inflater;
  private final List<Room> rooms;
  
  public RoomListAdapter(Activity paramActivity, List<Room> paramList)
  {
    this.inflater = paramActivity.getLayoutInflater();
    this.rooms = paramList;
    this.darkTheme = Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(paramActivity).getString(paramActivity.getString(2131099729), paramActivity.getString(2131099705)).equals(paramActivity.getString(2131099705)));
  }
  
  public int getCount()
  {
    return this.rooms.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.rooms.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    Room localRoom = (Room)this.rooms.get(paramInt);
    RoomViewCache localRoomViewCache;
    if (localView == null)
    {
      localView = this.inflater.inflate(2130903078, null);
      localRoomViewCache = new RoomViewCache(localView);
      localView.setTag(localRoomViewCache);
      localRoomViewCache.getMainTextView().setText(localRoom.getName());
      if (((ListView)paramViewGroup).getCheckedItemPosition() != paramInt) {
        break label156;
      }
      if (!this.darkTheme.booleanValue()) {
        break label135;
      }
      localView.setBackgroundColor(localView.getContext().getResources().getColor(2131492865));
    }
    for (;;)
    {
      localRoomViewCache.getMainTextView().setTextColor(Color.parseColor("white"));
      return localView;
      localRoomViewCache = (RoomViewCache)localView.getTag();
      break;
      label135:
      localView.setBackgroundColor(localView.getContext().getResources().getColor(2131492864));
    }
    label156:
    localView.setBackgroundColor(0);
    if (this.darkTheme.booleanValue())
    {
      localRoomViewCache.getMainTextView().setTextColor(Color.parseColor("white"));
      return localView;
    }
    localRoomViewCache.getMainTextView().setTextColor(Color.parseColor("black"));
    return localView;
  }
  
  public class RoomViewCache
  {
    private final View baseView;
    private TextView mainTextView;
    
    public RoomViewCache(View paramView)
    {
      this.baseView = paramView;
    }
    
    public TextView getMainTextView()
    {
      if (this.mainTextView == null) {
        this.mainTextView = ((TextView)this.baseView.findViewById(2131558495));
      }
      return this.mainTextView;
    }
  }
}
