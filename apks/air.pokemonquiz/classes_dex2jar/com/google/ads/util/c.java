package com.google.ads.util;

public class c
{
  static
  {
    if (!c.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      a = bool;
      return;
    }
  }
  
  private c() {}
  
  public static byte[] a(String paramString)
  {
    return a(paramString.getBytes(), 0);
  }
  
  public static byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    return a(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }
  
  public static byte[] a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    b localB = new b(paramInt3, new byte[paramInt2 * 3 / 4]);
    if (!localB.a(paramArrayOfByte, paramInt1, paramInt2, true)) {
      throw new IllegalArgumentException("bad base-64");
    }
    if (localB.b == localB.a.length) {
      return localB.a;
    }
    byte[] arrayOfByte = new byte[localB.b];
    System.arraycopy(localB.a, 0, arrayOfByte, 0, localB.b);
    return arrayOfByte;
  }
  
  public static abstract class a
  {
    public byte[] a;
    public int b;
    
    public a() {}
  }
  
  public static class b
    extends c.a
  {
    private static final int[] c = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static final int[] d = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private int e;
    private int f;
    private final int[] g;
    
    public b(int paramInt, byte[] paramArrayOfByte)
    {
      this.a = paramArrayOfByte;
      if ((paramInt & 0x8) == 0) {}
      for (int[] arrayOfInt = c;; arrayOfInt = d)
      {
        this.g = arrayOfInt;
        this.e = 0;
        this.f = 0;
        return;
      }
    }
    
    public boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      if (this.e == 6) {
        return false;
      }
      int i = paramInt2 + paramInt1;
      int j = this.e;
      int k = this.f;
      byte[] arrayOfByte = this.a;
      int[] arrayOfInt = this.g;
      int m = paramInt1;
      int n = 0;
      int i1 = j;
      int i2;
      if (m < i) {
        if (i1 == 0)
        {
          while (m + 4 <= i)
          {
            k = arrayOfInt[(0xFF & paramArrayOfByte[m])] << 18 | arrayOfInt[(0xFF & paramArrayOfByte[(m + 1)])] << 12 | arrayOfInt[(0xFF & paramArrayOfByte[(m + 2)])] << 6 | arrayOfInt[(0xFF & paramArrayOfByte[(m + 3)])];
            if (k < 0) {
              break;
            }
            arrayOfByte[(n + 2)] = ((byte)k);
            arrayOfByte[(n + 1)] = ((byte)(k >> 8));
            arrayOfByte[n] = ((byte)(k >> 16));
            n += 3;
            m += 4;
          }
          if (m >= i) {
            i2 = n;
          }
        }
      }
      for (int i3 = k;; i3 = k)
      {
        if (!paramBoolean)
        {
          this.e = i1;
          this.f = i3;
          this.b = i2;
          return true;
          int i6 = m + 1;
          int i7 = arrayOfInt[(0xFF & paramArrayOfByte[m])];
          switch (i1)
          {
          }
          label547:
          do
          {
            do
            {
              for (;;)
              {
                m = i6;
                break;
                if (i7 >= 0)
                {
                  i1++;
                  k = i7;
                }
                else if (i7 != -1)
                {
                  this.e = 6;
                  return false;
                  if (i7 >= 0)
                  {
                    k = i7 | k << 6;
                    i1++;
                  }
                  else if (i7 != -1)
                  {
                    this.e = 6;
                    return false;
                    if (i7 >= 0)
                    {
                      k = i7 | k << 6;
                      i1++;
                    }
                    else if (i7 == -2)
                    {
                      int i8 = n + 1;
                      arrayOfByte[n] = ((byte)(k >> 4));
                      i1 = 4;
                      n = i8;
                    }
                    else if (i7 != -1)
                    {
                      this.e = 6;
                      return false;
                      if (i7 >= 0)
                      {
                        k = i7 | k << 6;
                        arrayOfByte[(n + 2)] = ((byte)k);
                        arrayOfByte[(n + 1)] = ((byte)(k >> 8));
                        arrayOfByte[n] = ((byte)(k >> 16));
                        n += 3;
                        i1 = 0;
                      }
                      else if (i7 == -2)
                      {
                        arrayOfByte[(n + 1)] = ((byte)(k >> 2));
                        arrayOfByte[n] = ((byte)(k >> 10));
                        n += 2;
                        i1 = 5;
                      }
                      else if (i7 != -1)
                      {
                        this.e = 6;
                        return false;
                        if (i7 != -2) {
                          break label547;
                        }
                        i1++;
                      }
                    }
                  }
                }
              }
            } while (i7 == -1);
            this.e = 6;
            return false;
          } while (i7 == -1);
          this.e = 6;
          return false;
        }
        switch (i1)
        {
        case 0: 
        default: 
        case 1: 
        case 2: 
        case 3: 
          for (;;)
          {
            this.e = i1;
            this.b = i2;
            return true;
            this.e = 6;
            return false;
            int i5 = i2 + 1;
            arrayOfByte[i2] = ((byte)(i3 >> 4));
            i2 = i5;
            continue;
            int i4 = i2 + 1;
            arrayOfByte[i2] = ((byte)(i3 >> 10));
            i2 = i4 + 1;
            arrayOfByte[i4] = ((byte)(i3 >> 2));
          }
        }
        this.e = 6;
        return false;
        i2 = n;
      }
    }
  }
}
