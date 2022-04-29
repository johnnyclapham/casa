package com.comfortclick.cmclient;

public class Profile
{
  protected String address;
  protected String password;
  protected String profileName;
  protected String username;
  
  protected Profile(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.profileName = paramString1;
    this.address = paramString2;
    this.username = paramString3;
    this.password = paramString4;
  }
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof Profile)) && (((Profile)paramObject).profileName.compareTo(this.profileName) == 0);
  }
  
  public String toString()
  {
    return this.profileName + " (" + this.address + ")";
  }
}
