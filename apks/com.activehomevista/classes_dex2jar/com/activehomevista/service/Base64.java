package com.activehomevista.service;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class Base64
{
  public static final int DECODE = 0;
  public static final int DONT_GUNZIP = 4;
  public static final int DO_BREAK_LINES = 8;
  public static final int ENCODE = 1;
  private static final byte EQUALS_SIGN = 61;
  private static final byte EQUALS_SIGN_ENC = -1;
  public static final int GZIP = 2;
  private static final int MAX_LINE_LENGTH = 76;
  private static final byte NEW_LINE = 10;
  public static final int NO_OPTIONS = 0;
  public static final int ORDERED = 32;
  private static final String PREFERRED_ENCODING = "US-ASCII";
  public static final int URL_SAFE = 16;
  private static final byte WHITE_SPACE_ENC = -5;
  private static final byte[] _ORDERED_ALPHABET;
  private static final byte[] _ORDERED_DECODABET;
  private static final byte[] _STANDARD_ALPHABET;
  private static final byte[] _STANDARD_DECODABET;
  private static final byte[] _URL_SAFE_ALPHABET;
  private static final byte[] _URL_SAFE_DECODABET;
  
  static
  {
    if (!Base64.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      _STANDARD_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
      _STANDARD_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
      _URL_SAFE_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
      _URL_SAFE_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
      _ORDERED_ALPHABET = new byte[] { 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
      _ORDERED_DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
      return;
    }
  }
  
  private Base64() {}
  
  public static byte[] decode(String paramString)
    throws IOException
  {
    return decode(paramString, 0);
  }
  
  /* Error */
  public static byte[] decode(String paramString, int paramInt)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +13 -> 14
    //   4: new 209	java/lang/NullPointerException
    //   7: dup
    //   8: ldc -45
    //   10: invokespecial 214	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   13: athrow
    //   14: aload_0
    //   15: ldc 33
    //   17: invokevirtual 219	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   20: astore 26
    //   22: aload 26
    //   24: astore_3
    //   25: aload_3
    //   26: iconst_0
    //   27: aload_3
    //   28: arraylength
    //   29: iload_1
    //   30: invokestatic 222	com/activehomevista/service/Base64:decode	([BIII)[B
    //   33: astore 4
    //   35: iload_1
    //   36: iconst_4
    //   37: iand
    //   38: ifeq +167 -> 205
    //   41: iconst_1
    //   42: istore 5
    //   44: aload 4
    //   46: ifnull +147 -> 193
    //   49: aload 4
    //   51: arraylength
    //   52: iconst_4
    //   53: if_icmplt +140 -> 193
    //   56: iload 5
    //   58: ifne +135 -> 193
    //   61: ldc -33
    //   63: sipush 255
    //   66: aload 4
    //   68: iconst_0
    //   69: baload
    //   70: iand
    //   71: ldc -32
    //   73: aload 4
    //   75: iconst_1
    //   76: baload
    //   77: bipush 8
    //   79: ishl
    //   80: iand
    //   81: ior
    //   82: if_icmpne +111 -> 193
    //   85: aconst_null
    //   86: astore 6
    //   88: aconst_null
    //   89: astore 7
    //   91: aconst_null
    //   92: astore 8
    //   94: sipush 2048
    //   97: newarray byte
    //   99: astore 9
    //   101: new 226	java/io/ByteArrayOutputStream
    //   104: dup
    //   105: invokespecial 227	java/io/ByteArrayOutputStream:<init>	()V
    //   108: astore 10
    //   110: new 229	java/io/ByteArrayInputStream
    //   113: dup
    //   114: aload 4
    //   116: invokespecial 232	java/io/ByteArrayInputStream:<init>	([B)V
    //   119: astore 11
    //   121: new 234	java/util/zip/GZIPInputStream
    //   124: dup
    //   125: aload 11
    //   127: invokespecial 237	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   130: astore 12
    //   132: aload 12
    //   134: aload 9
    //   136: invokevirtual 241	java/util/zip/GZIPInputStream:read	([B)I
    //   139: istore 21
    //   141: iload 21
    //   143: iflt +68 -> 211
    //   146: aload 10
    //   148: aload 9
    //   150: iconst_0
    //   151: iload 21
    //   153: invokevirtual 245	java/io/ByteArrayOutputStream:write	([BII)V
    //   156: goto -24 -> 132
    //   159: astore 17
    //   161: aload 10
    //   163: astore 8
    //   165: aload 12
    //   167: astore 7
    //   169: aload 11
    //   171: astore 6
    //   173: aload 17
    //   175: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   178: aload 8
    //   180: invokevirtual 251	java/io/ByteArrayOutputStream:close	()V
    //   183: aload 7
    //   185: invokevirtual 252	java/util/zip/GZIPInputStream:close	()V
    //   188: aload 6
    //   190: invokevirtual 253	java/io/ByteArrayInputStream:close	()V
    //   193: aload 4
    //   195: areturn
    //   196: astore_2
    //   197: aload_0
    //   198: invokevirtual 256	java/lang/String:getBytes	()[B
    //   201: astore_3
    //   202: goto -177 -> 25
    //   205: iconst_0
    //   206: istore 5
    //   208: goto -164 -> 44
    //   211: aload 10
    //   213: invokevirtual 259	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   216: astore 22
    //   218: aload 10
    //   220: invokevirtual 251	java/io/ByteArrayOutputStream:close	()V
    //   223: aload 12
    //   225: invokevirtual 252	java/util/zip/GZIPInputStream:close	()V
    //   228: aload 11
    //   230: invokevirtual 253	java/io/ByteArrayInputStream:close	()V
    //   233: aload 22
    //   235: areturn
    //   236: astore 25
    //   238: aload 22
    //   240: areturn
    //   241: astore 13
    //   243: aload 8
    //   245: invokevirtual 251	java/io/ByteArrayOutputStream:close	()V
    //   248: aload 7
    //   250: invokevirtual 252	java/util/zip/GZIPInputStream:close	()V
    //   253: aload 6
    //   255: invokevirtual 253	java/io/ByteArrayInputStream:close	()V
    //   258: aload 13
    //   260: athrow
    //   261: astore 23
    //   263: goto -40 -> 223
    //   266: astore 24
    //   268: goto -40 -> 228
    //   271: astore 18
    //   273: goto -90 -> 183
    //   276: astore 19
    //   278: goto -90 -> 188
    //   281: astore 20
    //   283: aload 4
    //   285: areturn
    //   286: astore 14
    //   288: goto -40 -> 248
    //   291: astore 15
    //   293: goto -40 -> 253
    //   296: astore 16
    //   298: goto -40 -> 258
    //   301: astore 13
    //   303: aload 10
    //   305: astore 8
    //   307: aconst_null
    //   308: astore 6
    //   310: aconst_null
    //   311: astore 7
    //   313: goto -70 -> 243
    //   316: astore 13
    //   318: aload 10
    //   320: astore 8
    //   322: aload 11
    //   324: astore 6
    //   326: aconst_null
    //   327: astore 7
    //   329: goto -86 -> 243
    //   332: astore 13
    //   334: aload 10
    //   336: astore 8
    //   338: aload 12
    //   340: astore 7
    //   342: aload 11
    //   344: astore 6
    //   346: goto -103 -> 243
    //   349: astore 17
    //   351: aconst_null
    //   352: astore 6
    //   354: aconst_null
    //   355: astore 8
    //   357: aconst_null
    //   358: astore 7
    //   360: goto -187 -> 173
    //   363: astore 17
    //   365: aload 10
    //   367: astore 8
    //   369: aconst_null
    //   370: astore 6
    //   372: aconst_null
    //   373: astore 7
    //   375: goto -202 -> 173
    //   378: astore 17
    //   380: aload 10
    //   382: astore 8
    //   384: aload 11
    //   386: astore 6
    //   388: aconst_null
    //   389: astore 7
    //   391: goto -218 -> 173
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	394	0	paramString	String
    //   0	394	1	paramInt	int
    //   196	1	2	localUnsupportedEncodingException	UnsupportedEncodingException
    //   24	178	3	arrayOfByte1	byte[]
    //   33	251	4	arrayOfByte2	byte[]
    //   42	165	5	i	int
    //   86	301	6	localObject1	Object
    //   89	301	7	localObject2	Object
    //   92	291	8	localObject3	Object
    //   99	50	9	arrayOfByte3	byte[]
    //   108	273	10	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   119	266	11	localByteArrayInputStream	java.io.ByteArrayInputStream
    //   130	209	12	localGZIPInputStream	java.util.zip.GZIPInputStream
    //   241	18	13	localObject4	Object
    //   301	1	13	localObject5	Object
    //   316	1	13	localObject6	Object
    //   332	1	13	localObject7	Object
    //   286	1	14	localException1	Exception
    //   291	1	15	localException2	Exception
    //   296	1	16	localException3	Exception
    //   159	15	17	localIOException1	IOException
    //   349	1	17	localIOException2	IOException
    //   363	1	17	localIOException3	IOException
    //   378	1	17	localIOException4	IOException
    //   271	1	18	localException4	Exception
    //   276	1	19	localException5	Exception
    //   281	1	20	localException6	Exception
    //   139	13	21	j	int
    //   216	23	22	arrayOfByte4	byte[]
    //   261	1	23	localException7	Exception
    //   266	1	24	localException8	Exception
    //   236	1	25	localException9	Exception
    //   20	3	26	arrayOfByte5	byte[]
    // Exception table:
    //   from	to	target	type
    //   132	141	159	java/io/IOException
    //   146	156	159	java/io/IOException
    //   211	218	159	java/io/IOException
    //   14	22	196	java/io/UnsupportedEncodingException
    //   228	233	236	java/lang/Exception
    //   101	110	241	finally
    //   173	178	241	finally
    //   218	223	261	java/lang/Exception
    //   223	228	266	java/lang/Exception
    //   178	183	271	java/lang/Exception
    //   183	188	276	java/lang/Exception
    //   188	193	281	java/lang/Exception
    //   243	248	286	java/lang/Exception
    //   248	253	291	java/lang/Exception
    //   253	258	296	java/lang/Exception
    //   110	121	301	finally
    //   121	132	316	finally
    //   132	141	332	finally
    //   146	156	332	finally
    //   211	218	332	finally
    //   101	110	349	java/io/IOException
    //   110	121	363	java/io/IOException
    //   121	132	378	java/io/IOException
  }
  
  public static byte[] decode(byte[] paramArrayOfByte)
    throws IOException
  {
    return decode(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
  }
  
  public static byte[] decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    if (paramArrayOfByte == null) {
      throw new NullPointerException("Cannot decode null source array.");
    }
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length))
    {
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = Integer.valueOf(paramArrayOfByte.length);
      arrayOfObject1[1] = Integer.valueOf(paramInt1);
      arrayOfObject1[2] = Integer.valueOf(paramInt2);
      throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", arrayOfObject1));
    }
    if (paramInt2 == 0) {
      return new byte[0];
    }
    if (paramInt2 < 4) {
      throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + paramInt2);
    }
    byte[] arrayOfByte1 = getDecodabet(paramInt3);
    byte[] arrayOfByte2 = new byte[paramInt2 * 3 / 4];
    int i = 0;
    byte[] arrayOfByte3 = new byte[4];
    int j = paramInt1;
    int k = 0;
    int n;
    if (j < paramInt1 + paramInt2)
    {
      int m = arrayOfByte1[(0xFF & paramArrayOfByte[j])];
      if (m >= -5)
      {
        if (m < -1) {
          break label297;
        }
        n = k + 1;
        arrayOfByte3[k] = paramArrayOfByte[j];
        if (n <= 3) {
          break label301;
        }
        i += decode4to3(arrayOfByte3, 0, arrayOfByte2, i, paramInt3);
        int i1 = paramArrayOfByte[j];
        n = 0;
        if (i1 != 61) {
          break label301;
        }
      }
    }
    for (;;)
    {
      byte[] arrayOfByte4 = new byte[i];
      System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, i);
      return arrayOfByte4;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(0xFF & paramArrayOfByte[j]);
      arrayOfObject2[1] = Integer.valueOf(j);
      throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", arrayOfObject2));
      label297:
      n = k;
      label301:
      j++;
      k = n;
      break;
    }
  }
  
  private static int decode4to3(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    if (paramArrayOfByte1 == null) {
      throw new NullPointerException("Source array was null.");
    }
    if (paramArrayOfByte2 == null) {
      throw new NullPointerException("Destination array was null.");
    }
    if ((paramInt1 < 0) || (paramInt1 + 3 >= paramArrayOfByte1.length))
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Integer.valueOf(paramArrayOfByte1.length);
      arrayOfObject1[1] = Integer.valueOf(paramInt1);
      throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", arrayOfObject1));
    }
    if ((paramInt2 < 0) || (paramInt2 + 2 >= paramArrayOfByte2.length))
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(paramArrayOfByte2.length);
      arrayOfObject2[1] = Integer.valueOf(paramInt2);
      throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", arrayOfObject2));
    }
    byte[] arrayOfByte = getDecodabet(paramInt3);
    if (paramArrayOfByte1[(paramInt1 + 2)] == 61)
    {
      paramArrayOfByte2[paramInt2] = ((byte)(((0xFF & arrayOfByte[paramArrayOfByte1[paramInt1]]) << 18 | (0xFF & arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]]) << 12) >>> 16));
      return 1;
    }
    if (paramArrayOfByte1[(paramInt1 + 3)] == 61)
    {
      int j = (0xFF & arrayOfByte[paramArrayOfByte1[paramInt1]]) << 18 | (0xFF & arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]]) << 12 | (0xFF & arrayOfByte[paramArrayOfByte1[(paramInt1 + 2)]]) << 6;
      paramArrayOfByte2[paramInt2] = ((byte)(j >>> 16));
      paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(j >>> 8));
      return 2;
    }
    int i = (0xFF & arrayOfByte[paramArrayOfByte1[paramInt1]]) << 18 | (0xFF & arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]]) << 12 | (0xFF & arrayOfByte[paramArrayOfByte1[(paramInt1 + 2)]]) << 6 | 0xFF & arrayOfByte[paramArrayOfByte1[(paramInt1 + 3)]];
    paramArrayOfByte2[paramInt2] = ((byte)(i >> 16));
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(i >> 8));
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)i);
    return 3;
  }
  
  /* Error */
  public static void decodeFileToFile(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 315	com/activehomevista/service/Base64:decodeFromFile	(Ljava/lang/String;)[B
    //   4: astore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: new 317	java/io/BufferedOutputStream
    //   10: dup
    //   11: new 319	java/io/FileOutputStream
    //   14: dup
    //   15: aload_1
    //   16: invokespecial 320	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   19: invokespecial 323	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   22: astore 4
    //   24: aload 4
    //   26: aload_2
    //   27: invokevirtual 327	java/io/OutputStream:write	([B)V
    //   30: aload 4
    //   32: invokevirtual 328	java/io/OutputStream:close	()V
    //   35: return
    //   36: astore 5
    //   38: aload 5
    //   40: athrow
    //   41: astore 6
    //   43: aload_3
    //   44: invokevirtual 328	java/io/OutputStream:close	()V
    //   47: aload 6
    //   49: athrow
    //   50: astore 8
    //   52: return
    //   53: astore 7
    //   55: goto -8 -> 47
    //   58: astore 6
    //   60: aload 4
    //   62: astore_3
    //   63: goto -20 -> 43
    //   66: astore 5
    //   68: aload 4
    //   70: astore_3
    //   71: goto -33 -> 38
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	74	0	paramString1	String
    //   0	74	1	paramString2	String
    //   4	23	2	arrayOfByte	byte[]
    //   6	65	3	localObject1	Object
    //   22	47	4	localBufferedOutputStream	java.io.BufferedOutputStream
    //   36	3	5	localIOException1	IOException
    //   66	1	5	localIOException2	IOException
    //   41	7	6	localObject2	Object
    //   58	1	6	localObject3	Object
    //   53	1	7	localException1	Exception
    //   50	1	8	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   7	24	36	java/io/IOException
    //   7	24	41	finally
    //   38	41	41	finally
    //   30	35	50	java/lang/Exception
    //   43	47	53	java/lang/Exception
    //   24	30	58	finally
    //   24	30	66	java/io/IOException
  }
  
  /* Error */
  public static byte[] decodeFromFile(String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 330	java/io/File
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 331	java/io/File:<init>	(Ljava/lang/String;)V
    //   10: astore_2
    //   11: iconst_0
    //   12: istore_3
    //   13: aload_2
    //   14: invokevirtual 335	java/io/File:length	()J
    //   17: ldc2_w 336
    //   20: lcmp
    //   21: istore 7
    //   23: aconst_null
    //   24: astore_1
    //   25: iload 7
    //   27: ifle +54 -> 81
    //   30: new 200	java/io/IOException
    //   33: dup
    //   34: new 279	java/lang/StringBuilder
    //   37: dup
    //   38: invokespecial 280	java/lang/StringBuilder:<init>	()V
    //   41: ldc_w 339
    //   44: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: aload_2
    //   48: invokevirtual 335	java/io/File:length	()J
    //   51: invokevirtual 342	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   54: ldc_w 344
    //   57: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: invokevirtual 293	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: invokespecial 302	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   66: athrow
    //   67: astore 6
    //   69: aload 6
    //   71: athrow
    //   72: astore 4
    //   74: aload_1
    //   75: invokevirtual 347	com/activehomevista/service/Base64$InputStream:close	()V
    //   78: aload 4
    //   80: athrow
    //   81: aload_2
    //   82: invokevirtual 335	java/io/File:length	()J
    //   85: l2i
    //   86: newarray byte
    //   88: astore 8
    //   90: new 346	com/activehomevista/service/Base64$InputStream
    //   93: dup
    //   94: new 349	java/io/BufferedInputStream
    //   97: dup
    //   98: new 351	java/io/FileInputStream
    //   101: dup
    //   102: aload_2
    //   103: invokespecial 354	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   106: invokespecial 355	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   109: iconst_0
    //   110: invokespecial 358	com/activehomevista/service/Base64$InputStream:<init>	(Ljava/io/InputStream;I)V
    //   113: astore 9
    //   115: aload 9
    //   117: aload 8
    //   119: iload_3
    //   120: sipush 4096
    //   123: invokevirtual 361	com/activehomevista/service/Base64$InputStream:read	([BII)I
    //   126: istore 10
    //   128: iload 10
    //   130: iflt +11 -> 141
    //   133: iload_3
    //   134: iload 10
    //   136: iadd
    //   137: istore_3
    //   138: goto -23 -> 115
    //   141: iload_3
    //   142: newarray byte
    //   144: astore 11
    //   146: aload 8
    //   148: iconst_0
    //   149: aload 11
    //   151: iconst_0
    //   152: iload_3
    //   153: invokestatic 299	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   156: aload 9
    //   158: invokevirtual 347	com/activehomevista/service/Base64$InputStream:close	()V
    //   161: aload 11
    //   163: areturn
    //   164: astore 12
    //   166: aload 11
    //   168: areturn
    //   169: astore 5
    //   171: goto -93 -> 78
    //   174: astore 4
    //   176: aload 9
    //   178: astore_1
    //   179: goto -105 -> 74
    //   182: astore 6
    //   184: aload 9
    //   186: astore_1
    //   187: goto -118 -> 69
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	190	0	paramString	String
    //   1	186	1	localObject1	Object
    //   10	93	2	localFile	java.io.File
    //   12	141	3	i	int
    //   72	7	4	localObject2	Object
    //   174	1	4	localObject3	Object
    //   169	1	5	localException1	Exception
    //   67	3	6	localIOException1	IOException
    //   182	1	6	localIOException2	IOException
    //   21	5	7	bool	boolean
    //   88	59	8	arrayOfByte1	byte[]
    //   113	72	9	localInputStream	InputStream
    //   126	11	10	j	int
    //   144	23	11	arrayOfByte2	byte[]
    //   164	1	12	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   2	11	67	java/io/IOException
    //   13	23	67	java/io/IOException
    //   30	67	67	java/io/IOException
    //   81	115	67	java/io/IOException
    //   2	11	72	finally
    //   13	23	72	finally
    //   30	67	72	finally
    //   69	72	72	finally
    //   81	115	72	finally
    //   156	161	164	java/lang/Exception
    //   74	78	169	java/lang/Exception
    //   115	128	174	finally
    //   141	156	174	finally
    //   115	128	182	java/io/IOException
    //   141	156	182	java/io/IOException
  }
  
  /* Error */
  public static void decodeToFile(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 364	com/activehomevista/service/Base64$OutputStream
    //   5: dup
    //   6: new 319	java/io/FileOutputStream
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 320	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   14: iconst_0
    //   15: invokespecial 367	com/activehomevista/service/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   18: astore_3
    //   19: aload_3
    //   20: aload_0
    //   21: ldc 33
    //   23: invokevirtual 219	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   26: invokevirtual 368	com/activehomevista/service/Base64$OutputStream:write	([B)V
    //   29: aload_3
    //   30: invokevirtual 369	com/activehomevista/service/Base64$OutputStream:close	()V
    //   33: return
    //   34: astore 4
    //   36: aload 4
    //   38: athrow
    //   39: astore 5
    //   41: aload_2
    //   42: invokevirtual 369	com/activehomevista/service/Base64$OutputStream:close	()V
    //   45: aload 5
    //   47: athrow
    //   48: astore 7
    //   50: return
    //   51: astore 6
    //   53: goto -8 -> 45
    //   56: astore 5
    //   58: aload_3
    //   59: astore_2
    //   60: goto -19 -> 41
    //   63: astore 4
    //   65: aload_3
    //   66: astore_2
    //   67: goto -31 -> 36
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	paramString1	String
    //   0	70	1	paramString2	String
    //   1	66	2	localObject1	Object
    //   18	48	3	localOutputStream	OutputStream
    //   34	3	4	localIOException1	IOException
    //   63	1	4	localIOException2	IOException
    //   39	7	5	localObject2	Object
    //   56	1	5	localObject3	Object
    //   51	1	6	localException1	Exception
    //   48	1	7	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   2	19	34	java/io/IOException
    //   2	19	39	finally
    //   36	39	39	finally
    //   29	33	48	java/lang/Exception
    //   41	45	51	java/lang/Exception
    //   19	29	56	finally
    //   19	29	63	java/io/IOException
  }
  
  public static Object decodeToObject(String paramString)
    throws IOException, ClassNotFoundException
  {
    return decodeToObject(paramString, 0, null);
  }
  
  /* Error */
  public static Object decodeToObject(String paramString, int paramInt, final ClassLoader paramClassLoader)
    throws IOException, ClassNotFoundException
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokestatic 203	com/activehomevista/service/Base64:decode	(Ljava/lang/String;I)[B
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: new 229	java/io/ByteArrayInputStream
    //   15: dup
    //   16: aload_3
    //   17: invokespecial 232	java/io/ByteArrayInputStream:<init>	([B)V
    //   20: astore 6
    //   22: aload_2
    //   23: ifnonnull +34 -> 57
    //   26: new 378	java/io/ObjectInputStream
    //   29: dup
    //   30: aload 6
    //   32: invokespecial 379	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   35: astore 5
    //   37: aload 5
    //   39: invokevirtual 383	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   42: astore 13
    //   44: aload 6
    //   46: invokevirtual 253	java/io/ByteArrayInputStream:close	()V
    //   49: aload 5
    //   51: invokevirtual 384	java/io/ObjectInputStream:close	()V
    //   54: aload 13
    //   56: areturn
    //   57: new 386	com/activehomevista/service/Base64$1
    //   60: dup
    //   61: aload 6
    //   63: aload_2
    //   64: invokespecial 389	com/activehomevista/service/Base64$1:<init>	(Ljava/io/InputStream;Ljava/lang/ClassLoader;)V
    //   67: astore 7
    //   69: aload 7
    //   71: astore 5
    //   73: goto -36 -> 37
    //   76: astore 8
    //   78: aload 8
    //   80: athrow
    //   81: astore 9
    //   83: aload 4
    //   85: invokevirtual 253	java/io/ByteArrayInputStream:close	()V
    //   88: aload 5
    //   90: invokevirtual 384	java/io/ObjectInputStream:close	()V
    //   93: aload 9
    //   95: athrow
    //   96: astore 12
    //   98: aload 12
    //   100: athrow
    //   101: astore 14
    //   103: goto -54 -> 49
    //   106: astore 15
    //   108: aload 13
    //   110: areturn
    //   111: astore 10
    //   113: goto -25 -> 88
    //   116: astore 11
    //   118: goto -25 -> 93
    //   121: astore 9
    //   123: aload 6
    //   125: astore 4
    //   127: goto -44 -> 83
    //   130: astore 12
    //   132: aload 6
    //   134: astore 4
    //   136: goto -38 -> 98
    //   139: astore 8
    //   141: aload 6
    //   143: astore 4
    //   145: goto -67 -> 78
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	148	0	paramString	String
    //   0	148	1	paramInt	int
    //   0	148	2	paramClassLoader	ClassLoader
    //   5	12	3	arrayOfByte	byte[]
    //   7	137	4	localObject1	Object
    //   10	79	5	localObject2	Object
    //   20	122	6	localByteArrayInputStream	java.io.ByteArrayInputStream
    //   67	3	7	local1	1
    //   76	3	8	localIOException1	IOException
    //   139	1	8	localIOException2	IOException
    //   81	13	9	localObject3	Object
    //   121	1	9	localObject4	Object
    //   111	1	10	localException1	Exception
    //   116	1	11	localException2	Exception
    //   96	3	12	localClassNotFoundException1	ClassNotFoundException
    //   130	1	12	localClassNotFoundException2	ClassNotFoundException
    //   42	67	13	localObject5	Object
    //   101	1	14	localException3	Exception
    //   106	1	15	localException4	Exception
    // Exception table:
    //   from	to	target	type
    //   12	22	76	java/io/IOException
    //   12	22	81	finally
    //   78	81	81	finally
    //   98	101	81	finally
    //   12	22	96	java/lang/ClassNotFoundException
    //   44	49	101	java/lang/Exception
    //   49	54	106	java/lang/Exception
    //   83	88	111	java/lang/Exception
    //   88	93	116	java/lang/Exception
    //   26	37	121	finally
    //   37	44	121	finally
    //   57	69	121	finally
    //   26	37	130	java/lang/ClassNotFoundException
    //   37	44	130	java/lang/ClassNotFoundException
    //   57	69	130	java/lang/ClassNotFoundException
    //   26	37	139	java/io/IOException
    //   37	44	139	java/io/IOException
    //   57	69	139	java/io/IOException
  }
  
  public static void encode(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
  {
    byte[] arrayOfByte1 = new byte[3];
    byte[] arrayOfByte2 = new byte[4];
    while (paramByteBuffer1.hasRemaining())
    {
      int i = Math.min(3, paramByteBuffer1.remaining());
      paramByteBuffer1.get(arrayOfByte1, 0, i);
      encode3to4(arrayOfByte2, arrayOfByte1, i, 0);
      paramByteBuffer2.put(arrayOfByte2);
    }
  }
  
  public static void encode(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer)
  {
    byte[] arrayOfByte1 = new byte[3];
    byte[] arrayOfByte2 = new byte[4];
    while (paramByteBuffer.hasRemaining())
    {
      int i = Math.min(3, paramByteBuffer.remaining());
      paramByteBuffer.get(arrayOfByte1, 0, i);
      encode3to4(arrayOfByte2, arrayOfByte1, i, 0);
      for (int j = 0; j < 4; j++) {
        paramCharBuffer.put((char)(0xFF & arrayOfByte2[j]));
      }
    }
  }
  
  private static byte[] encode3to4(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4)
  {
    byte[] arrayOfByte = getAlphabet(paramInt4);
    int i;
    if (paramInt2 > 0)
    {
      i = paramArrayOfByte1[paramInt1] << 24 >>> 8;
      label22:
      if (paramInt2 <= 1) {
        break label112;
      }
    }
    int n;
    label112:
    for (int j = paramArrayOfByte1[(paramInt1 + 1)] << 24 >>> 16;; j = 0)
    {
      int k = j | i;
      int m = 0;
      if (paramInt2 > 2) {
        m = paramArrayOfByte1[(paramInt1 + 2)] << 24 >>> 24;
      }
      n = k | m;
      switch (paramInt2)
      {
      default: 
        return paramArrayOfByte2;
        i = 0;
        break label22;
      }
    }
    paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
    paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(0x3F & n >>> 6)];
    paramArrayOfByte2[(paramInt3 + 3)] = arrayOfByte[(n & 0x3F)];
    return paramArrayOfByte2;
    paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
    paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(0x3F & n >>> 6)];
    paramArrayOfByte2[(paramInt3 + 3)] = 61;
    return paramArrayOfByte2;
    paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
    paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
    paramArrayOfByte2[(paramInt3 + 2)] = 61;
    paramArrayOfByte2[(paramInt3 + 3)] = 61;
    return paramArrayOfByte2;
  }
  
  private static byte[] encode3to4(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
  {
    encode3to4(paramArrayOfByte2, 0, paramInt1, paramArrayOfByte1, 0, paramInt2);
    return paramArrayOfByte1;
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte)
  {
    String str1;
    try
    {
      String str2 = encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
      str1 = str2;
    }
    catch (IOException localIOException)
    {
      boolean bool;
      do
      {
        bool = $assertionsDisabled;
        str1 = null;
      } while (bool);
      throw new AssertionError(localIOException.getMessage());
    }
    assert (str1 != null);
    return str1;
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    return encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    String str1;
    try
    {
      String str2 = encodeBytes(paramArrayOfByte, paramInt1, paramInt2, 0);
      str1 = str2;
    }
    catch (IOException localIOException)
    {
      boolean bool;
      do
      {
        bool = $assertionsDisabled;
        str1 = null;
      } while (bool);
      throw new AssertionError(localIOException.getMessage());
    }
    assert (str1 != null);
    return str1;
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    byte[] arrayOfByte = encodeBytesToBytes(paramArrayOfByte, paramInt1, paramInt2, paramInt3);
    try
    {
      String str = new String(arrayOfByte, "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return new String(arrayOfByte);
  }
  
  public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte)
  {
    try
    {
      byte[] arrayOfByte2 = encodeBytesToBytes(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
      arrayOfByte1 = arrayOfByte2;
    }
    catch (IOException localIOException)
    {
      boolean bool;
      do
      {
        bool = $assertionsDisabled;
        byte[] arrayOfByte1 = null;
      } while (bool);
      throw new AssertionError("IOExceptions only come from GZipping, which is turned off: " + localIOException.getMessage());
    }
    return arrayOfByte1;
  }
  
  /* Error */
  public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +14 -> 15
    //   4: new 209	java/lang/NullPointerException
    //   7: dup
    //   8: ldc_w 450
    //   11: invokespecial 214	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   14: athrow
    //   15: iload_1
    //   16: ifge +31 -> 47
    //   19: new 270	java/lang/IllegalArgumentException
    //   22: dup
    //   23: new 279	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 280	java/lang/StringBuilder:<init>	()V
    //   30: ldc_w 452
    //   33: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: iload_1
    //   37: invokevirtual 289	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   40: invokevirtual 293	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: invokespecial 277	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   46: athrow
    //   47: iload_2
    //   48: ifge +31 -> 79
    //   51: new 270	java/lang/IllegalArgumentException
    //   54: dup
    //   55: new 279	java/lang/StringBuilder
    //   58: dup
    //   59: invokespecial 280	java/lang/StringBuilder:<init>	()V
    //   62: ldc_w 454
    //   65: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: iload_2
    //   69: invokevirtual 289	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   72: invokevirtual 293	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   75: invokespecial 277	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   78: athrow
    //   79: iload_1
    //   80: iload_2
    //   81: iadd
    //   82: aload_0
    //   83: arraylength
    //   84: if_icmple +50 -> 134
    //   87: iconst_3
    //   88: anewarray 4	java/lang/Object
    //   91: astore 30
    //   93: aload 30
    //   95: iconst_0
    //   96: iload_1
    //   97: invokestatic 268	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   100: aastore
    //   101: aload 30
    //   103: iconst_1
    //   104: iload_2
    //   105: invokestatic 268	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   108: aastore
    //   109: aload 30
    //   111: iconst_2
    //   112: aload_0
    //   113: arraylength
    //   114: invokestatic 268	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   117: aastore
    //   118: new 270	java/lang/IllegalArgumentException
    //   121: dup
    //   122: ldc_w 456
    //   125: aload 30
    //   127: invokestatic 276	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   130: invokespecial 277	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   133: athrow
    //   134: iload_3
    //   135: iconst_2
    //   136: iand
    //   137: ifeq +105 -> 242
    //   140: aconst_null
    //   141: astore 16
    //   143: aconst_null
    //   144: astore 17
    //   146: aconst_null
    //   147: astore 18
    //   149: new 226	java/io/ByteArrayOutputStream
    //   152: dup
    //   153: invokespecial 227	java/io/ByteArrayOutputStream:<init>	()V
    //   156: astore 19
    //   158: new 364	com/activehomevista/service/Base64$OutputStream
    //   161: dup
    //   162: aload 19
    //   164: iload_3
    //   165: iconst_1
    //   166: ior
    //   167: invokespecial 367	com/activehomevista/service/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   170: astore 20
    //   172: new 458	java/util/zip/GZIPOutputStream
    //   175: dup
    //   176: aload 20
    //   178: invokespecial 459	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   181: astore 21
    //   183: aload 21
    //   185: aload_0
    //   186: iload_1
    //   187: iload_2
    //   188: invokevirtual 460	java/util/zip/GZIPOutputStream:write	([BII)V
    //   191: aload 21
    //   193: invokevirtual 461	java/util/zip/GZIPOutputStream:close	()V
    //   196: aload 21
    //   198: invokevirtual 461	java/util/zip/GZIPOutputStream:close	()V
    //   201: aload 20
    //   203: invokevirtual 369	com/activehomevista/service/Base64$OutputStream:close	()V
    //   206: aload 19
    //   208: invokevirtual 251	java/io/ByteArrayOutputStream:close	()V
    //   211: aload 19
    //   213: invokevirtual 259	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   216: areturn
    //   217: astore 22
    //   219: aload 22
    //   221: athrow
    //   222: astore 23
    //   224: aload 17
    //   226: invokevirtual 461	java/util/zip/GZIPOutputStream:close	()V
    //   229: aload 18
    //   231: invokevirtual 369	com/activehomevista/service/Base64$OutputStream:close	()V
    //   234: aload 16
    //   236: invokevirtual 251	java/io/ByteArrayOutputStream:close	()V
    //   239: aload 23
    //   241: athrow
    //   242: iload_3
    //   243: bipush 8
    //   245: iand
    //   246: ifeq +125 -> 371
    //   249: iconst_1
    //   250: istore 4
    //   252: iconst_4
    //   253: iload_2
    //   254: iconst_3
    //   255: idiv
    //   256: imul
    //   257: istore 5
    //   259: iload_2
    //   260: iconst_3
    //   261: irem
    //   262: ifle +115 -> 377
    //   265: iconst_4
    //   266: istore 6
    //   268: iload 5
    //   270: iload 6
    //   272: iadd
    //   273: istore 7
    //   275: iload 4
    //   277: ifeq +13 -> 290
    //   280: iload 7
    //   282: iload 7
    //   284: bipush 76
    //   286: idiv
    //   287: iadd
    //   288: istore 7
    //   290: iload 7
    //   292: newarray byte
    //   294: astore 8
    //   296: iconst_0
    //   297: istore 9
    //   299: iconst_0
    //   300: istore 10
    //   302: iload_2
    //   303: iconst_2
    //   304: isub
    //   305: istore 11
    //   307: iconst_0
    //   308: istore 12
    //   310: iload 9
    //   312: iload 11
    //   314: if_icmpge +69 -> 383
    //   317: aload_0
    //   318: iload 9
    //   320: iload_1
    //   321: iadd
    //   322: iconst_3
    //   323: aload 8
    //   325: iload 10
    //   327: iload_3
    //   328: invokestatic 187	com/activehomevista/service/Base64:encode3to4	([BII[BII)[B
    //   331: pop
    //   332: iinc 12 4
    //   335: iload 4
    //   337: ifeq +25 -> 362
    //   340: iload 12
    //   342: bipush 76
    //   344: if_icmplt +18 -> 362
    //   347: aload 8
    //   349: iload 10
    //   351: iconst_4
    //   352: iadd
    //   353: bipush 10
    //   355: bastore
    //   356: iinc 10 1
    //   359: iconst_0
    //   360: istore 12
    //   362: iinc 9 3
    //   365: iinc 10 4
    //   368: goto -58 -> 310
    //   371: iconst_0
    //   372: istore 4
    //   374: goto -122 -> 252
    //   377: iconst_0
    //   378: istore 6
    //   380: goto -112 -> 268
    //   383: iload 9
    //   385: iload_2
    //   386: if_icmpge +24 -> 410
    //   389: aload_0
    //   390: iload 9
    //   392: iload_1
    //   393: iadd
    //   394: iload_2
    //   395: iload 9
    //   397: isub
    //   398: aload 8
    //   400: iload 10
    //   402: iload_3
    //   403: invokestatic 187	com/activehomevista/service/Base64:encode3to4	([BII[BII)[B
    //   406: pop
    //   407: iinc 10 4
    //   410: iload 10
    //   412: iconst_m1
    //   413: aload 8
    //   415: arraylength
    //   416: iadd
    //   417: if_icmpgt +23 -> 440
    //   420: iload 10
    //   422: newarray byte
    //   424: astore 13
    //   426: aload 8
    //   428: iconst_0
    //   429: aload 13
    //   431: iconst_0
    //   432: iload 10
    //   434: invokestatic 299	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   437: aload 13
    //   439: areturn
    //   440: aload 8
    //   442: areturn
    //   443: astore 27
    //   445: goto -244 -> 201
    //   448: astore 28
    //   450: goto -244 -> 206
    //   453: astore 29
    //   455: goto -244 -> 211
    //   458: astore 24
    //   460: goto -231 -> 229
    //   463: astore 25
    //   465: goto -231 -> 234
    //   468: astore 26
    //   470: goto -231 -> 239
    //   473: astore 23
    //   475: aload 19
    //   477: astore 16
    //   479: aconst_null
    //   480: astore 18
    //   482: aconst_null
    //   483: astore 17
    //   485: goto -261 -> 224
    //   488: astore 23
    //   490: aload 20
    //   492: astore 18
    //   494: aload 19
    //   496: astore 16
    //   498: aconst_null
    //   499: astore 17
    //   501: goto -277 -> 224
    //   504: astore 23
    //   506: aload 20
    //   508: astore 18
    //   510: aload 21
    //   512: astore 17
    //   514: aload 19
    //   516: astore 16
    //   518: goto -294 -> 224
    //   521: astore 22
    //   523: aload 19
    //   525: astore 16
    //   527: aconst_null
    //   528: astore 18
    //   530: aconst_null
    //   531: astore 17
    //   533: goto -314 -> 219
    //   536: astore 22
    //   538: aload 20
    //   540: astore 18
    //   542: aload 19
    //   544: astore 16
    //   546: aconst_null
    //   547: astore 17
    //   549: goto -330 -> 219
    //   552: astore 22
    //   554: aload 20
    //   556: astore 18
    //   558: aload 21
    //   560: astore 17
    //   562: aload 19
    //   564: astore 16
    //   566: goto -347 -> 219
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	569	0	paramArrayOfByte	byte[]
    //   0	569	1	paramInt1	int
    //   0	569	2	paramInt2	int
    //   0	569	3	paramInt3	int
    //   250	123	4	i	int
    //   257	16	5	j	int
    //   266	113	6	k	int
    //   273	18	7	m	int
    //   294	147	8	arrayOfByte1	byte[]
    //   297	101	9	n	int
    //   300	133	10	i1	int
    //   305	10	11	i2	int
    //   308	53	12	i3	int
    //   424	14	13	arrayOfByte2	byte[]
    //   141	424	16	localObject1	Object
    //   144	417	17	localObject2	Object
    //   147	410	18	localObject3	Object
    //   156	407	19	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   170	385	20	localOutputStream	OutputStream
    //   181	378	21	localGZIPOutputStream	java.util.zip.GZIPOutputStream
    //   217	3	22	localIOException1	IOException
    //   521	1	22	localIOException2	IOException
    //   536	1	22	localIOException3	IOException
    //   552	1	22	localIOException4	IOException
    //   222	18	23	localObject4	Object
    //   473	1	23	localObject5	Object
    //   488	1	23	localObject6	Object
    //   504	1	23	localObject7	Object
    //   458	1	24	localException1	Exception
    //   463	1	25	localException2	Exception
    //   468	1	26	localException3	Exception
    //   443	1	27	localException4	Exception
    //   448	1	28	localException5	Exception
    //   453	1	29	localException6	Exception
    //   91	35	30	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   149	158	217	java/io/IOException
    //   149	158	222	finally
    //   219	222	222	finally
    //   196	201	443	java/lang/Exception
    //   201	206	448	java/lang/Exception
    //   206	211	453	java/lang/Exception
    //   224	229	458	java/lang/Exception
    //   229	234	463	java/lang/Exception
    //   234	239	468	java/lang/Exception
    //   158	172	473	finally
    //   172	183	488	finally
    //   183	196	504	finally
    //   158	172	521	java/io/IOException
    //   172	183	536	java/io/IOException
    //   183	196	552	java/io/IOException
  }
  
  /* Error */
  public static void encodeFileToFile(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 466	com/activehomevista/service/Base64:encodeFromFile	(Ljava/lang/String;)Ljava/lang/String;
    //   4: astore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: new 317	java/io/BufferedOutputStream
    //   10: dup
    //   11: new 319	java/io/FileOutputStream
    //   14: dup
    //   15: aload_1
    //   16: invokespecial 320	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   19: invokespecial 323	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   22: astore 4
    //   24: aload 4
    //   26: aload_2
    //   27: ldc 33
    //   29: invokevirtual 219	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   32: invokevirtual 327	java/io/OutputStream:write	([B)V
    //   35: aload 4
    //   37: invokevirtual 328	java/io/OutputStream:close	()V
    //   40: return
    //   41: astore 5
    //   43: aload 5
    //   45: athrow
    //   46: astore 6
    //   48: aload_3
    //   49: invokevirtual 328	java/io/OutputStream:close	()V
    //   52: aload 6
    //   54: athrow
    //   55: astore 8
    //   57: return
    //   58: astore 7
    //   60: goto -8 -> 52
    //   63: astore 6
    //   65: aload 4
    //   67: astore_3
    //   68: goto -20 -> 48
    //   71: astore 5
    //   73: aload 4
    //   75: astore_3
    //   76: goto -33 -> 43
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	paramString1	String
    //   0	79	1	paramString2	String
    //   4	23	2	str	String
    //   6	70	3	localObject1	Object
    //   22	52	4	localBufferedOutputStream	java.io.BufferedOutputStream
    //   41	3	5	localIOException1	IOException
    //   71	1	5	localIOException2	IOException
    //   46	7	6	localObject2	Object
    //   63	1	6	localObject3	Object
    //   58	1	7	localException1	Exception
    //   55	1	8	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   7	24	41	java/io/IOException
    //   7	24	46	finally
    //   43	46	46	finally
    //   35	40	55	java/lang/Exception
    //   48	52	58	java/lang/Exception
    //   24	35	63	finally
    //   24	35	71	java/io/IOException
  }
  
  /* Error */
  public static String encodeFromFile(String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 330	java/io/File
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 331	java/io/File:<init>	(Ljava/lang/String;)V
    //   10: astore_2
    //   11: dconst_1
    //   12: ldc2_w 467
    //   15: aload_2
    //   16: invokevirtual 335	java/io/File:length	()J
    //   19: l2d
    //   20: dmul
    //   21: dadd
    //   22: d2i
    //   23: bipush 40
    //   25: invokestatic 471	java/lang/Math:max	(II)I
    //   28: newarray byte
    //   30: astore 6
    //   32: iconst_0
    //   33: istore 7
    //   35: new 346	com/activehomevista/service/Base64$InputStream
    //   38: dup
    //   39: new 349	java/io/BufferedInputStream
    //   42: dup
    //   43: new 351	java/io/FileInputStream
    //   46: dup
    //   47: aload_2
    //   48: invokespecial 354	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   51: invokespecial 355	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   54: iconst_1
    //   55: invokespecial 358	com/activehomevista/service/Base64$InputStream:<init>	(Ljava/io/InputStream;I)V
    //   58: astore 8
    //   60: aload 8
    //   62: aload 6
    //   64: iload 7
    //   66: sipush 4096
    //   69: invokevirtual 361	com/activehomevista/service/Base64$InputStream:read	([BII)I
    //   72: istore 9
    //   74: iload 9
    //   76: iflt +13 -> 89
    //   79: iload 7
    //   81: iload 9
    //   83: iadd
    //   84: istore 7
    //   86: goto -26 -> 60
    //   89: new 216	java/lang/String
    //   92: dup
    //   93: aload 6
    //   95: iconst_0
    //   96: iload 7
    //   98: ldc 33
    //   100: invokespecial 474	java/lang/String:<init>	([BIILjava/lang/String;)V
    //   103: astore 10
    //   105: aload 8
    //   107: invokevirtual 347	com/activehomevista/service/Base64$InputStream:close	()V
    //   110: aload 10
    //   112: areturn
    //   113: astore 5
    //   115: aload 5
    //   117: athrow
    //   118: astore_3
    //   119: aload_1
    //   120: invokevirtual 347	com/activehomevista/service/Base64$InputStream:close	()V
    //   123: aload_3
    //   124: athrow
    //   125: astore 11
    //   127: aload 10
    //   129: areturn
    //   130: astore 4
    //   132: goto -9 -> 123
    //   135: astore_3
    //   136: aload 8
    //   138: astore_1
    //   139: goto -20 -> 119
    //   142: astore 5
    //   144: aload 8
    //   146: astore_1
    //   147: goto -32 -> 115
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	150	0	paramString	String
    //   1	146	1	localObject1	Object
    //   10	38	2	localFile	java.io.File
    //   118	6	3	localObject2	Object
    //   135	1	3	localObject3	Object
    //   130	1	4	localException1	Exception
    //   113	3	5	localIOException1	IOException
    //   142	1	5	localIOException2	IOException
    //   30	64	6	arrayOfByte	byte[]
    //   33	64	7	i	int
    //   58	87	8	localInputStream	InputStream
    //   72	12	9	j	int
    //   103	25	10	str	String
    //   125	1	11	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   2	32	113	java/io/IOException
    //   35	60	113	java/io/IOException
    //   2	32	118	finally
    //   35	60	118	finally
    //   115	118	118	finally
    //   105	110	125	java/lang/Exception
    //   119	123	130	java/lang/Exception
    //   60	74	135	finally
    //   89	105	135	finally
    //   60	74	142	java/io/IOException
    //   89	105	142	java/io/IOException
  }
  
  public static String encodeObject(Serializable paramSerializable)
    throws IOException
  {
    return encodeObject(paramSerializable, 0);
  }
  
  /* Error */
  public static String encodeObject(Serializable paramSerializable, int paramInt)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +14 -> 15
    //   4: new 209	java/lang/NullPointerException
    //   7: dup
    //   8: ldc_w 481
    //   11: invokespecial 214	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   14: athrow
    //   15: aconst_null
    //   16: astore_2
    //   17: aconst_null
    //   18: astore_3
    //   19: aconst_null
    //   20: astore 4
    //   22: aconst_null
    //   23: astore 5
    //   25: new 226	java/io/ByteArrayOutputStream
    //   28: dup
    //   29: invokespecial 227	java/io/ByteArrayOutputStream:<init>	()V
    //   32: astore 6
    //   34: new 364	com/activehomevista/service/Base64$OutputStream
    //   37: dup
    //   38: aload 6
    //   40: iload_1
    //   41: iconst_1
    //   42: ior
    //   43: invokespecial 367	com/activehomevista/service/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   46: astore 7
    //   48: iload_1
    //   49: iconst_2
    //   50: iand
    //   51: ifeq +78 -> 129
    //   54: new 458	java/util/zip/GZIPOutputStream
    //   57: dup
    //   58: aload 7
    //   60: invokespecial 459	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   63: astore 21
    //   65: new 483	java/io/ObjectOutputStream
    //   68: dup
    //   69: aload 21
    //   71: invokespecial 484	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   74: astore 22
    //   76: aload 22
    //   78: astore 5
    //   80: aload 21
    //   82: astore 4
    //   84: aload 5
    //   86: aload_0
    //   87: invokevirtual 487	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
    //   90: aload 5
    //   92: invokevirtual 488	java/io/ObjectOutputStream:close	()V
    //   95: aload 4
    //   97: invokevirtual 461	java/util/zip/GZIPOutputStream:close	()V
    //   100: aload 7
    //   102: invokevirtual 328	java/io/OutputStream:close	()V
    //   105: aload 6
    //   107: invokevirtual 251	java/io/ByteArrayOutputStream:close	()V
    //   110: new 216	java/lang/String
    //   113: dup
    //   114: aload 6
    //   116: invokevirtual 259	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   119: ldc 33
    //   121: invokespecial 445	java/lang/String:<init>	([BLjava/lang/String;)V
    //   124: astore 19
    //   126: aload 19
    //   128: areturn
    //   129: new 483	java/io/ObjectOutputStream
    //   132: dup
    //   133: aload 7
    //   135: invokespecial 484	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   138: astore 8
    //   140: aload 8
    //   142: astore 5
    //   144: aconst_null
    //   145: astore 4
    //   147: goto -63 -> 84
    //   150: astore 9
    //   152: aload 9
    //   154: athrow
    //   155: astore 10
    //   157: aload 5
    //   159: invokevirtual 488	java/io/ObjectOutputStream:close	()V
    //   162: aload 4
    //   164: invokevirtual 461	java/util/zip/GZIPOutputStream:close	()V
    //   167: aload_3
    //   168: invokevirtual 328	java/io/OutputStream:close	()V
    //   171: aload_2
    //   172: invokevirtual 251	java/io/ByteArrayOutputStream:close	()V
    //   175: aload 10
    //   177: athrow
    //   178: astore 20
    //   180: new 216	java/lang/String
    //   183: dup
    //   184: aload 6
    //   186: invokevirtual 259	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   189: invokespecial 446	java/lang/String:<init>	([B)V
    //   192: areturn
    //   193: astore 15
    //   195: goto -100 -> 95
    //   198: astore 16
    //   200: goto -100 -> 100
    //   203: astore 17
    //   205: goto -100 -> 105
    //   208: astore 18
    //   210: goto -100 -> 110
    //   213: astore 11
    //   215: goto -53 -> 162
    //   218: astore 12
    //   220: goto -53 -> 167
    //   223: astore 13
    //   225: goto -54 -> 171
    //   228: astore 14
    //   230: goto -55 -> 175
    //   233: astore 10
    //   235: aload 6
    //   237: astore_2
    //   238: aconst_null
    //   239: astore_3
    //   240: aconst_null
    //   241: astore 4
    //   243: aconst_null
    //   244: astore 5
    //   246: goto -89 -> 157
    //   249: astore 10
    //   251: aload 7
    //   253: astore_3
    //   254: aload 6
    //   256: astore_2
    //   257: goto -100 -> 157
    //   260: astore 10
    //   262: aload 21
    //   264: astore 4
    //   266: aload 7
    //   268: astore_3
    //   269: aload 6
    //   271: astore_2
    //   272: aconst_null
    //   273: astore 5
    //   275: goto -118 -> 157
    //   278: astore 9
    //   280: aload 6
    //   282: astore_2
    //   283: aconst_null
    //   284: astore_3
    //   285: aconst_null
    //   286: astore 4
    //   288: aconst_null
    //   289: astore 5
    //   291: goto -139 -> 152
    //   294: astore 9
    //   296: aload 7
    //   298: astore_3
    //   299: aload 6
    //   301: astore_2
    //   302: goto -150 -> 152
    //   305: astore 9
    //   307: aload 21
    //   309: astore 4
    //   311: aload 7
    //   313: astore_3
    //   314: aload 6
    //   316: astore_2
    //   317: aconst_null
    //   318: astore 5
    //   320: goto -168 -> 152
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	323	0	paramSerializable	Serializable
    //   0	323	1	paramInt	int
    //   16	301	2	localObject1	Object
    //   18	296	3	localObject2	Object
    //   20	290	4	localObject3	Object
    //   23	296	5	localObject4	Object
    //   32	283	6	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   46	266	7	localOutputStream	OutputStream
    //   138	3	8	localObjectOutputStream1	java.io.ObjectOutputStream
    //   150	3	9	localIOException1	IOException
    //   278	1	9	localIOException2	IOException
    //   294	1	9	localIOException3	IOException
    //   305	1	9	localIOException4	IOException
    //   155	21	10	localObject5	Object
    //   233	1	10	localObject6	Object
    //   249	1	10	localObject7	Object
    //   260	1	10	localObject8	Object
    //   213	1	11	localException1	Exception
    //   218	1	12	localException2	Exception
    //   223	1	13	localException3	Exception
    //   228	1	14	localException4	Exception
    //   193	1	15	localException5	Exception
    //   198	1	16	localException6	Exception
    //   203	1	17	localException7	Exception
    //   208	1	18	localException8	Exception
    //   124	3	19	str	String
    //   178	1	20	localUnsupportedEncodingException	UnsupportedEncodingException
    //   63	245	21	localGZIPOutputStream	java.util.zip.GZIPOutputStream
    //   74	3	22	localObjectOutputStream2	java.io.ObjectOutputStream
    // Exception table:
    //   from	to	target	type
    //   25	34	150	java/io/IOException
    //   25	34	155	finally
    //   152	155	155	finally
    //   110	126	178	java/io/UnsupportedEncodingException
    //   90	95	193	java/lang/Exception
    //   95	100	198	java/lang/Exception
    //   100	105	203	java/lang/Exception
    //   105	110	208	java/lang/Exception
    //   157	162	213	java/lang/Exception
    //   162	167	218	java/lang/Exception
    //   167	171	223	java/lang/Exception
    //   171	175	228	java/lang/Exception
    //   34	48	233	finally
    //   54	65	249	finally
    //   84	90	249	finally
    //   129	140	249	finally
    //   65	76	260	finally
    //   34	48	278	java/io/IOException
    //   54	65	294	java/io/IOException
    //   84	90	294	java/io/IOException
    //   129	140	294	java/io/IOException
    //   65	76	305	java/io/IOException
  }
  
  /* Error */
  public static void encodeToFile(byte[] paramArrayOfByte, String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +14 -> 15
    //   4: new 209	java/lang/NullPointerException
    //   7: dup
    //   8: ldc_w 491
    //   11: invokespecial 214	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   14: athrow
    //   15: aconst_null
    //   16: astore_2
    //   17: new 364	com/activehomevista/service/Base64$OutputStream
    //   20: dup
    //   21: new 319	java/io/FileOutputStream
    //   24: dup
    //   25: aload_1
    //   26: invokespecial 320	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   29: iconst_1
    //   30: invokespecial 367	com/activehomevista/service/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   33: astore_3
    //   34: aload_3
    //   35: aload_0
    //   36: invokevirtual 368	com/activehomevista/service/Base64$OutputStream:write	([B)V
    //   39: aload_3
    //   40: invokevirtual 369	com/activehomevista/service/Base64$OutputStream:close	()V
    //   43: return
    //   44: astore 4
    //   46: aload 4
    //   48: athrow
    //   49: astore 5
    //   51: aload_2
    //   52: invokevirtual 369	com/activehomevista/service/Base64$OutputStream:close	()V
    //   55: aload 5
    //   57: athrow
    //   58: astore 7
    //   60: return
    //   61: astore 6
    //   63: goto -8 -> 55
    //   66: astore 5
    //   68: aload_3
    //   69: astore_2
    //   70: goto -19 -> 51
    //   73: astore 4
    //   75: aload_3
    //   76: astore_2
    //   77: goto -31 -> 46
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	paramArrayOfByte	byte[]
    //   0	80	1	paramString	String
    //   16	61	2	localObject1	Object
    //   33	43	3	localOutputStream	OutputStream
    //   44	3	4	localIOException1	IOException
    //   73	1	4	localIOException2	IOException
    //   49	7	5	localObject2	Object
    //   66	1	5	localObject3	Object
    //   61	1	6	localException1	Exception
    //   58	1	7	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   17	34	44	java/io/IOException
    //   17	34	49	finally
    //   46	49	49	finally
    //   39	43	58	java/lang/Exception
    //   51	55	61	java/lang/Exception
    //   34	39	66	finally
    //   34	39	73	java/io/IOException
  }
  
  private static final byte[] getAlphabet(int paramInt)
  {
    if ((paramInt & 0x10) == 16) {
      return _URL_SAFE_ALPHABET;
    }
    if ((paramInt & 0x20) == 32) {
      return _ORDERED_ALPHABET;
    }
    return _STANDARD_ALPHABET;
  }
  
  private static final byte[] getDecodabet(int paramInt)
  {
    if ((paramInt & 0x10) == 16) {
      return _URL_SAFE_DECODABET;
    }
    if ((paramInt & 0x20) == 32) {
      return _ORDERED_DECODABET;
    }
    return _STANDARD_DECODABET;
  }
  
  public static class InputStream
    extends FilterInputStream
  {
    private boolean breakLines;
    private byte[] buffer;
    private int bufferLength;
    private byte[] decodabet;
    private boolean encode;
    private int lineLength;
    private int numSigBytes;
    private int options;
    private int position;
    
    public InputStream(InputStream paramInputStream)
    {
      this(paramInputStream, 0);
    }
    
    public InputStream(InputStream paramInputStream, int paramInt)
    {
      super();
      this.options = paramInt;
      boolean bool2;
      if ((paramInt & 0x8) > 0)
      {
        bool2 = bool1;
        this.breakLines = bool2;
        if ((paramInt & 0x1) <= 0) {
          break label90;
        }
        label34:
        this.encode = bool1;
        if (!this.encode) {
          break label95;
        }
      }
      label90:
      label95:
      for (int i = 4;; i = 3)
      {
        this.bufferLength = i;
        this.buffer = new byte[this.bufferLength];
        this.position = -1;
        this.lineLength = 0;
        this.decodabet = Base64.getDecodabet(paramInt);
        return;
        bool2 = false;
        break;
        bool1 = false;
        break label34;
      }
    }
    
    public int read()
      throws IOException
    {
      if (this.position < 0)
      {
        if (!this.encode) {
          break label117;
        }
        byte[] arrayOfByte3 = new byte[3];
        int n = 0;
        for (int i1 = 0; i1 < 3; i1++)
        {
          int i2 = this.in.read();
          if (i2 < 0) {
            break;
          }
          arrayOfByte3[i1] = ((byte)i2);
          n++;
        }
        if (n > 0)
        {
          Base64.encode3to4(arrayOfByte3, 0, n, this.buffer, 0, this.options);
          this.position = 0;
          this.numSigBytes = 4;
        }
      }
      else
      {
        if (this.position < 0) {
          break label313;
        }
        if (this.position < this.numSigBytes) {
          break label229;
        }
        return -1;
      }
      return -1;
      label117:
      byte[] arrayOfByte2 = new byte[4];
      for (int k = 0;; k++)
      {
        int m;
        if (k < 4)
        {
          do
          {
            m = this.in.read();
          } while ((m >= 0) && (this.decodabet[(m & 0x7F)] <= -5));
          if (m >= 0) {}
        }
        else
        {
          if (k != 4) {
            break label212;
          }
          this.numSigBytes = Base64.decode4to3(arrayOfByte2, 0, this.buffer, 0, this.options);
          this.position = 0;
          break;
        }
        arrayOfByte2[k] = ((byte)m);
      }
      label212:
      if (k == 0) {
        return -1;
      }
      throw new IOException("Improperly padded Base64 input.");
      label229:
      if ((this.encode) && (this.breakLines) && (this.lineLength >= 76))
      {
        this.lineLength = 0;
        return 10;
      }
      this.lineLength = (1 + this.lineLength);
      byte[] arrayOfByte1 = this.buffer;
      int i = this.position;
      this.position = (i + 1);
      int j = arrayOfByte1[i];
      if (this.position >= this.bufferLength) {
        this.position = -1;
      }
      return j & 0xFF;
      label313:
      throw new IOException("Error in Base64 code reading stream.");
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = 0;
      while (i < paramInt2)
      {
        int j = read();
        if (j >= 0)
        {
          paramArrayOfByte[(paramInt1 + i)] = ((byte)j);
          i++;
        }
        else if (i == 0)
        {
          i = -1;
        }
      }
      return i;
    }
  }
  
  public static class OutputStream
    extends FilterOutputStream
  {
    private byte[] b4;
    private boolean breakLines;
    private byte[] buffer;
    private int bufferLength;
    private byte[] decodabet;
    private boolean encode;
    private int lineLength;
    private int options;
    private int position;
    private boolean suspendEncoding;
    
    public OutputStream(OutputStream paramOutputStream)
    {
      this(paramOutputStream, 1);
    }
    
    public OutputStream(OutputStream paramOutputStream, int paramInt)
    {
      super();
      boolean bool2;
      if ((paramInt & 0x8) != 0)
      {
        bool2 = bool1;
        this.breakLines = bool2;
        if ((paramInt & 0x1) == 0) {
          break label102;
        }
        label29:
        this.encode = bool1;
        if (!this.encode) {
          break label107;
        }
      }
      label102:
      label107:
      for (int i = 3;; i = 4)
      {
        this.bufferLength = i;
        this.buffer = new byte[this.bufferLength];
        this.position = 0;
        this.lineLength = 0;
        this.suspendEncoding = false;
        this.b4 = new byte[4];
        this.options = paramInt;
        this.decodabet = Base64.getDecodabet(paramInt);
        return;
        bool2 = false;
        break;
        bool1 = false;
        break label29;
      }
    }
    
    public void close()
      throws IOException
    {
      flushBase64();
      super.close();
      this.buffer = null;
      this.out = null;
    }
    
    public void flushBase64()
      throws IOException
    {
      if (this.position > 0)
      {
        if (this.encode)
        {
          this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
          this.position = 0;
        }
      }
      else {
        return;
      }
      throw new IOException("Base64 input not properly padded.");
    }
    
    public void resumeEncoding()
    {
      this.suspendEncoding = false;
    }
    
    public void suspendEncoding()
      throws IOException
    {
      flushBase64();
      this.suspendEncoding = true;
    }
    
    public void write(int paramInt)
      throws IOException
    {
      if (this.suspendEncoding) {
        this.out.write(paramInt);
      }
      do
      {
        do
        {
          do
          {
            return;
            if (!this.encode) {
              break;
            }
            byte[] arrayOfByte2 = this.buffer;
            int k = this.position;
            this.position = (k + 1);
            arrayOfByte2[k] = ((byte)paramInt);
          } while (this.position < this.bufferLength);
          this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
          this.lineLength = (4 + this.lineLength);
          if ((this.breakLines) && (this.lineLength >= 76))
          {
            this.out.write(10);
            this.lineLength = 0;
          }
          this.position = 0;
          return;
          if (this.decodabet[(paramInt & 0x7F)] <= -5) {
            break;
          }
          byte[] arrayOfByte1 = this.buffer;
          int i = this.position;
          this.position = (i + 1);
          arrayOfByte1[i] = ((byte)paramInt);
        } while (this.position < this.bufferLength);
        int j = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
        this.out.write(this.b4, 0, j);
        this.position = 0;
        return;
      } while (this.decodabet[(paramInt & 0x7F)] == -5);
      throw new IOException("Invalid character in Base64 data.");
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      if (this.suspendEncoding) {
        this.out.write(paramArrayOfByte, paramInt1, paramInt2);
      }
      for (;;)
      {
        return;
        for (int i = 0; i < paramInt2; i++) {
          write(paramArrayOfByte[(paramInt1 + i)]);
        }
      }
    }
  }
}
