package com.comfortclick.cmclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class ProfileAdapter
  extends ArrayAdapter<Profile>
{
  Context context;
  int resource;
  String response;
  
  public ProfileAdapter(Context paramContext, int paramInt, List<Profile> paramList)
  {
    super(paramContext, paramInt, paramList);
    this.resource = paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Profile localProfile = (Profile)getItem(paramInt);
    LinearLayout localLinearLayout;
    if (paramView == null)
    {
      localLinearLayout = new LinearLayout(getContext());
      ((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(this.resource, localLinearLayout, true);
    }
    for (;;)
    {
      TextView localTextView1 = (TextView)localLinearLayout.findViewById(2131165184);
      TextView localTextView2 = (TextView)localLinearLayout.findViewById(2131165185);
      localTextView1.setText(localProfile.profileName);
      localTextView2.setText(localProfile.address);
      return localLinearLayout;
      localLinearLayout = (LinearLayout)paramView;
    }
  }
}
