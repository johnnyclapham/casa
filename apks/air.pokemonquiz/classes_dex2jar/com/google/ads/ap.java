package com.google.ads;

public final class ap
{
  private static final char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
  private static final char[] b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
  private static final byte[] c = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9 };
  private static final byte[] d = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9 };
  
  private static int a(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfChar[(paramInt1 + 2)] == '=')
    {
      paramArrayOfByte1[paramInt2] = ((byte)((paramArrayOfByte2[paramArrayOfChar[paramInt1]] << 24 >>> 6 | paramArrayOfByte2[paramArrayOfChar[(paramInt1 + 1)]] << 24 >>> 12) >>> 16));
      return 1;
    }
    if (paramArrayOfChar[(paramInt1 + 3)] == '=')
    {
      int j = paramArrayOfByte2[paramArrayOfChar[paramInt1]] << 24 >>> 6 | paramArrayOfByte2[paramArrayOfChar[(paramInt1 + 1)]] << 24 >>> 12 | paramArrayOfByte2[paramArrayOfChar[(paramInt1 + 2)]] << 24 >>> 18;
      paramArrayOfByte1[paramInt2] = ((byte)(j >>> 16));
      paramArrayOfByte1[(paramInt2 + 1)] = ((byte)(j >>> 8));
      return 2;
    }
    int i = paramArrayOfByte2[paramArrayOfChar[paramInt1]] << 24 >>> 6 | paramArrayOfByte2[paramArrayOfChar[(paramInt1 + 1)]] << 24 >>> 12 | paramArrayOfByte2[paramArrayOfChar[(paramInt1 + 2)]] << 24 >>> 18 | paramArrayOfByte2[paramArrayOfChar[(paramInt1 + 3)]] << 24 >>> 24;
    paramArrayOfByte1[paramInt2] = ((byte)(i >> 16));
    paramArrayOfByte1[(paramInt2 + 1)] = ((byte)(i >> 8));
    paramArrayOfByte1[(paramInt2 + 2)] = ((byte)i);
    return 3;
  }
  
  public static String a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, char[] paramArrayOfChar, boolean paramBoolean)
  {
    char[] arrayOfChar = a(paramArrayOfByte, paramInt1, paramInt2, paramArrayOfChar, Integer.MAX_VALUE);
    for (int i = arrayOfChar.length;; i--) {
      if ((paramBoolean) || (i <= 0) || (arrayOfChar[(i - 1)] != '=')) {
        return new String(arrayOfChar, 0, i);
      }
    }
  }
  
  public static String a(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    return a(paramArrayOfByte, 0, paramArrayOfByte.length, b, paramBoolean);
  }
  
  public static byte[] a(String paramString)
    throws ao
  {
    char[] arrayOfChar = paramString.toCharArray();
    return a(arrayOfChar, 0, arrayOfChar.length);
  }
  
  public static byte[] a(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws ao
  {
    return a(paramArrayOfChar, paramInt1, paramInt2, c);
  }
  
  public static byte[] a(char[] paramArrayOfChar, int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    throws ao
  {
    byte[] arrayOfByte1 = new byte[2 + paramInt2 * 3 / 4];
    char[] arrayOfChar = new char[4];
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int i2;
    if (j < paramInt2)
    {
      int i1 = paramArrayOfChar[(j + paramInt1)];
      i2 = (char)(i1 & 0x7F);
      int i3 = paramArrayOfByte[i2];
      if ((i2 == i1) && (i3 < -5)) {
        throw new ao("Bad Base64 input character at " + j + ": " + paramArrayOfChar[(paramInt1 + j)] + "(decimal)");
      }
      if (i3 >= -1)
      {
        if (i2 != 61) {
          break label215;
        }
        if (m == 0) {
          break label141;
        }
      }
    }
    for (;;)
    {
      j++;
      break;
      label141:
      if (j < 2) {
        throw new ao("Invalid padding char found in position " + j);
      }
      m = 1;
      int i5 = (char)(0x7F & paramArrayOfChar[(paramInt1 + (paramInt2 - 1))]);
      if ((i5 != 61) && (i5 != 10))
      {
        throw new ao("encoded value has invalid trailing char");
        label215:
        if (m != 0) {
          throw new ao("Data found after trailing padding char at index " + j);
        }
        int i4 = i + 1;
        arrayOfChar[i] = i2;
        if (i4 == 4)
        {
          k += a(arrayOfChar, 0, arrayOfByte1, k, paramArrayOfByte);
          i = 0;
          continue;
          if (i != 0)
          {
            if (i == 1) {
              throw new ao("single trailing character at offset " + (paramInt2 - 1));
            }
            arrayOfChar[i] = '=';
          }
          for (int n = k + a(arrayOfChar, 0, arrayOfByte1, k, paramArrayOfByte);; n = k)
          {
            byte[] arrayOfByte2 = new byte[n];
            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, n);
            return arrayOfByte2;
          }
        }
        else
        {
          i = i4;
        }
      }
    }
  }
  
  public static char[] a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
  {
    int i = 4 * ((paramInt2 + 2) / 3);
    char[] arrayOfChar = new char[i + i / paramInt3];
    int j = paramInt2 - 2;
    int k = 0;
    int m = 0;
    int n = 0;
    while (n < j)
    {
      int i2 = paramArrayOfByte[(n + paramInt1)] << 24 >>> 8 | paramArrayOfByte[(paramInt1 + (n + 1))] << 24 >>> 16 | paramArrayOfByte[(paramInt1 + (n + 2))] << 24 >>> 24;
      arrayOfChar[m] = paramArrayOfChar[(i2 >>> 18)];
      arrayOfChar[(m + 1)] = paramArrayOfChar[(0x3F & i2 >>> 12)];
      arrayOfChar[(m + 2)] = paramArrayOfChar[(0x3F & i2 >>> 6)];
      arrayOfChar[(m + 3)] = paramArrayOfChar[(i2 & 0x3F)];
      int i3 = k + 4;
      if (i3 == paramInt3)
      {
        arrayOfChar[(m + 4)] = '\n';
        m++;
        i3 = 0;
      }
      n += 3;
      m += 4;
      k = i3;
    }
    if (n < paramInt2)
    {
      a(paramArrayOfByte, n + paramInt1, paramInt2 - n, arrayOfChar, m, paramArrayOfChar);
      if (k + 4 != paramInt3) {
        break label243;
      }
      arrayOfChar[(m + 4)] = '\n';
    }
    label243:
    for (int i1 = m + 1;; i1 = m)
    {
      (i1 + 4);
      return arrayOfChar;
    }
  }
  
  private static char[] a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, char[] paramArrayOfChar1, int paramInt3, char[] paramArrayOfChar2)
  {
    int i;
    label15:
    int j;
    label33:
    int k;
    if (paramInt2 > 0)
    {
      i = paramArrayOfByte[paramInt1] << 24 >>> 8;
      if (paramInt2 <= 1) {
        break label100;
      }
      j = paramArrayOfByte[(paramInt1 + 1)] << 24 >>> 16;
      k = i | j;
      if (paramInt2 <= 2) {
        break label106;
      }
    }
    int n;
    label100:
    label106:
    for (int m = paramArrayOfByte[(paramInt1 + 2)] << 24 >>> 24;; m = 0)
    {
      n = k | m;
      switch (paramInt2)
      {
      default: 
        return paramArrayOfChar1;
        i = 0;
        break label15;
        j = 0;
        break label33;
      }
    }
    paramArrayOfChar1[paramInt3] = paramArrayOfChar2[(n >>> 18)];
    paramArrayOfChar1[(paramInt3 + 1)] = paramArrayOfChar2[(0x3F & n >>> 12)];
    paramArrayOfChar1[(paramInt3 + 2)] = paramArrayOfChar2[(0x3F & n >>> 6)];
    paramArrayOfChar1[(paramInt3 + 3)] = paramArrayOfChar2[(n & 0x3F)];
    return paramArrayOfChar1;
    paramArrayOfChar1[paramInt3] = paramArrayOfChar2[(n >>> 18)];
    paramArrayOfChar1[(paramInt3 + 1)] = paramArrayOfChar2[(0x3F & n >>> 12)];
    paramArrayOfChar1[(paramInt3 + 2)] = paramArrayOfChar2[(0x3F & n >>> 6)];
    paramArrayOfChar1[(paramInt3 + 3)] = '=';
    return paramArrayOfChar1;
    paramArrayOfChar1[paramInt3] = paramArrayOfChar2[(n >>> 18)];
    paramArrayOfChar1[(paramInt3 + 1)] = paramArrayOfChar2[(0x3F & n >>> 12)];
    paramArrayOfChar1[(paramInt3 + 2)] = '=';
    paramArrayOfChar1[(paramInt3 + 3)] = '=';
    return paramArrayOfChar1;
  }
}
