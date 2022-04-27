package com.google.ads;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class aj
  implements ai
{
  public MotionEvent a = null;
  public DisplayMetrics b = null;
  private at c = null;
  private ByteArrayOutputStream d = null;
  
  protected aj(Context paramContext)
  {
    try
    {
      this.b = paramContext.getResources().getDisplayMetrics();
      return;
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      this.b = new DisplayMetrics();
      this.b.density = 1.0F;
    }
  }
  
  private String a(Context paramContext, String paramString, boolean paramBoolean)
  {
    try
    {
      a();
      if (paramBoolean) {
        c(paramContext);
      }
      byte[] arrayOfByte;
      for (;;)
      {
        arrayOfByte = b();
        if (arrayOfByte.length != 0) {
          break;
        }
        return Integer.toString(5);
        b(paramContext);
      }
      String str;
      return Integer.toString(3);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      return Integer.toString(7);
      str = a(arrayOfByte, paramString);
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      return Integer.toString(7);
    }
    catch (IOException localIOException) {}
  }
  
  private void a()
  {
    this.d = new ByteArrayOutputStream();
    this.c = at.a(this.d);
  }
  
  private byte[] b()
    throws IOException
  {
    this.c.a();
    return this.d.toByteArray();
  }
  
  public String a(Context paramContext)
  {
    return a(paramContext, null, false);
  }
  
  public String a(Context paramContext, String paramString)
  {
    return a(paramContext, paramString, true);
  }
  
  public String a(byte[] paramArrayOfByte, String paramString)
    throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException
  {
    if (paramArrayOfByte.length > 239)
    {
      a();
      a(20, 1L);
    }
    for (byte[] arrayOfByte1 = b();; arrayOfByte1 = paramArrayOfByte)
    {
      byte[] arrayOfByte6;
      if (arrayOfByte1.length < 239)
      {
        arrayOfByte6 = new byte[239 - arrayOfByte1.length];
        new SecureRandom().nextBytes(arrayOfByte6);
      }
      for (byte[] arrayOfByte2 = ByteBuffer.allocate(240).put((byte)arrayOfByte1.length).put(arrayOfByte1).put(arrayOfByte6).array();; arrayOfByte2 = ByteBuffer.allocate(240).put((byte)arrayOfByte1.length).put(arrayOfByte1).array())
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(arrayOfByte2);
        byte[] arrayOfByte3 = localMessageDigest.digest();
        byte[] arrayOfByte4 = ByteBuffer.allocate(256).put(arrayOfByte3).put(arrayOfByte2).array();
        byte[] arrayOfByte5 = new byte['Ā'];
        new ag().a(arrayOfByte4, arrayOfByte5);
        if ((paramString != null) && (paramString.length() > 0)) {
          a(paramString, arrayOfByte5);
        }
        return ap.a(arrayOfByte5, false);
      }
    }
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.a != null) {
      this.a.recycle();
    }
    this.a = MotionEvent.obtain(0L, paramInt3, 1, paramInt1 * this.b.density, paramInt2 * this.b.density, 0.0F, 0.0F, 0, 0.0F, 0.0F, 0, 0);
  }
  
  protected void a(int paramInt, long paramLong)
    throws IOException
  {
    this.c.a(paramInt, paramLong);
  }
  
  protected void a(int paramInt, String paramString)
    throws IOException
  {
    this.c.a(paramInt, paramString);
  }
  
  public void a(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 1)
    {
      if (this.a != null) {
        this.a.recycle();
      }
      this.a = MotionEvent.obtain(paramMotionEvent);
    }
  }
  
  public void a(String paramString, byte[] paramArrayOfByte)
    throws UnsupportedEncodingException
  {
    if (paramString.length() > 32) {}
    for (String str = paramString.substring(0, 32);; str = paramString)
    {
      new aq(str.getBytes("UTF-8")).a(paramArrayOfByte);
      return;
    }
  }
  
  protected abstract void b(Context paramContext);
  
  protected abstract void c(Context paramContext);
}
