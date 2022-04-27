package com.google.ads;

public class aq
{
  private final byte[] a = new byte['Ā'];
  private int b;
  private int c;
  
  public aq(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < 256; i++) {
      this.a[i] = ((byte)i);
    }
    int j = 0;
    for (int k = 0; k < 256; k++)
    {
      j = 0xFF & j + this.a[k] + paramArrayOfByte[(k % paramArrayOfByte.length)];
      int m = this.a[k];
      this.a[k] = this.a[j];
      this.a[j] = m;
    }
    this.b = 0;
    this.c = 0;
  }
  
  public void a(byte[] paramArrayOfByte)
  {
    int i = this.b;
    int j = this.c;
    int k = i;
    for (int m = 0; m < paramArrayOfByte.length; m++)
    {
      k = 0xFF & k + 1;
      j = 0xFF & j + this.a[k];
      int n = this.a[k];
      this.a[k] = this.a[j];
      this.a[j] = n;
      paramArrayOfByte[m] = ((byte)(paramArrayOfByte[m] ^ this.a[(0xFF & this.a[k] + this.a[j])]));
    }
    this.b = k;
    this.c = j;
  }
}
