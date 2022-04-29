package com.advancedquonsettechnology.hcaapp;

public class Response
{
  private int[] Ends;
  private String[] Fields;
  private String Header;
  private int allcount;
  private String allresponse;
  private int hdrlen;
  private int lastget;
  
  public Response(String paramString)
  {
    this.allresponse = paramString;
    if (this.allresponse == null) {
      this.allcount = 0;
    }
    for (;;)
    {
      return;
      this.hdrlen = this.allresponse.indexOf("    ");
      if (this.hdrlen == -1)
      {
        this.allcount = 0;
        return;
      }
      int i = 4 + this.hdrlen;
      this.Header = this.allresponse.substring(0, this.hdrlen);
      this.allcount = (this.Header.length() / 4);
      this.Fields = new String[this.allcount];
      this.Ends = new int[this.allcount];
      for (int j = 0; j < this.allcount; j++)
      {
        int n = j * 4;
        int i1 = Integer.parseInt(this.Header.substring(n, n + 4));
        this.Ends[j] = i1;
      }
      for (int k = 0; k < this.allcount; k++)
      {
        int m = this.Ends[k];
        String str = this.allresponse.substring(i, m);
        this.Fields[k] = str;
        i = m;
      }
    }
  }
  
  public String get(int paramInt)
  {
    if ((this.Fields != null) && (this.Fields.length > paramInt))
    {
      this.lastget = paramInt;
      return this.Fields[paramInt];
    }
    return "";
  }
  
  public int getCount()
  {
    return this.allcount;
  }
  
  public String getFirst()
  {
    if ((this.Fields != null) && (this.Fields.length > 0))
    {
      this.lastget = 0;
      return this.Fields[0];
    }
    return "";
  }
  
  public String getNext()
  {
    String str = "";
    if (this.lastget < -1 + this.allcount)
    {
      this.lastget = (1 + this.lastget);
      str = this.Fields[this.lastget];
    }
    return str;
  }
  
  public String getRawMessage()
  {
    return this.allresponse;
  }
}
