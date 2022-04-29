package com.advancedquonsettechnology.hcaapp;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HCAClient
{
  Socket HCASocket = null;
  int clientNumber;
  private boolean connected = false;
  InputStreamReader in;
  String msg;
  OutputStream out;
  BufferedReader reader;
  
  public HCAClient() {}
  
  void closeClient()
  {
    try
    {
      if (this.HCASocket != null)
      {
        if (this.HCASocket.isConnected())
        {
          this.reader.close();
          this.out.close();
        }
        if (!this.HCASocket.isClosed()) {
          this.HCASocket.close();
        }
      }
      this.connected = false;
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  String getConnectResponse()
  {
    char[] arrayOfChar = new char[16];
    try
    {
      int i = this.reader.read(arrayOfChar, 0, 16);
      Object localObject = null;
      if (i > 0)
      {
        String str = String.valueOf(arrayOfChar);
        localObject = str;
      }
      return localObject;
    }
    catch (Exception localException)
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "getConnectResponse: exception " + localException.getMessage());
      }
      localException.printStackTrace();
    }
    return null;
  }
  
  boolean getConnected()
  {
    return this.connected;
  }
  
  /* Error */
  void openClient()
  {
    // Byte code:
    //   0: ldc 105
    //   2: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   5: aload_0
    //   6: iconst_0
    //   7: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   10: iconst_0
    //   11: putstatic 111	com/advancedquonsettechnology/hcaapp/HCAApplication:IsSecondary	Z
    //   14: new 113	java/net/InetSocketAddress
    //   17: dup
    //   18: getstatic 116	com/advancedquonsettechnology/hcaapp/HCAApplication:mIPAddress	Ljava/lang/String;
    //   21: invokestatic 122	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   24: getstatic 125	com/advancedquonsettechnology/hcaapp/HCAApplication:mIPPort	Ljava/lang/String;
    //   27: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   30: invokespecial 134	java/net/InetSocketAddress:<init>	(Ljava/net/InetAddress;I)V
    //   33: astore 9
    //   35: aload_0
    //   36: new 31	java/net/Socket
    //   39: dup
    //   40: invokespecial 135	java/net/Socket:<init>	()V
    //   43: putfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   46: aload_0
    //   47: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   50: aload 9
    //   52: getstatic 138	com/advancedquonsettechnology/hcaapp/HCAApplication:mIPTimeout	Ljava/lang/String;
    //   55: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   58: invokevirtual 142	java/net/Socket:connect	(Ljava/net/SocketAddress;I)V
    //   61: aload_0
    //   62: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   65: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   68: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   71: aload_0
    //   72: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   75: iconst_1
    //   76: invokevirtual 153	java/net/Socket:setKeepAlive	(Z)V
    //   79: aload_0
    //   80: iconst_1
    //   81: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   84: aload_0
    //   85: getfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   88: istore 11
    //   90: iload 11
    //   92: ifne +77 -> 169
    //   95: new 113	java/net/InetSocketAddress
    //   98: dup
    //   99: getstatic 156	com/advancedquonsettechnology/hcaapp/HCAApplication:mIPAddressSecondary	Ljava/lang/String;
    //   102: invokestatic 122	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   105: getstatic 125	com/advancedquonsettechnology/hcaapp/HCAApplication:mIPPort	Ljava/lang/String;
    //   108: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   111: invokespecial 134	java/net/InetSocketAddress:<init>	(Ljava/net/InetAddress;I)V
    //   114: astore 34
    //   116: aload_0
    //   117: new 31	java/net/Socket
    //   120: dup
    //   121: invokespecial 135	java/net/Socket:<init>	()V
    //   124: putfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   127: aload_0
    //   128: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   131: aload 34
    //   133: getstatic 138	com/advancedquonsettechnology/hcaapp/HCAApplication:mIPTimeout	Ljava/lang/String;
    //   136: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   139: invokevirtual 142	java/net/Socket:connect	(Ljava/net/SocketAddress;I)V
    //   142: aload_0
    //   143: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   146: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   149: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   152: aload_0
    //   153: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   156: iconst_1
    //   157: invokevirtual 153	java/net/Socket:setKeepAlive	(Z)V
    //   160: iconst_1
    //   161: putstatic 111	com/advancedquonsettechnology/hcaapp/HCAApplication:IsSecondary	Z
    //   164: aload_0
    //   165: iconst_1
    //   166: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   169: aload_0
    //   170: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   173: invokevirtual 35	java/net/Socket:isConnected	()Z
    //   176: ifeq +1004 -> 1180
    //   179: aload_0
    //   180: aload_0
    //   181: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   184: invokevirtual 160	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
    //   187: putfield 44	com/advancedquonsettechnology/hcaapp/HCAClient:out	Ljava/io/OutputStream;
    //   190: aload_0
    //   191: getfield 44	com/advancedquonsettechnology/hcaapp/HCAClient:out	Ljava/io/OutputStream;
    //   194: invokevirtual 163	java/io/OutputStream:flush	()V
    //   197: aload_0
    //   198: new 165	java/io/InputStreamReader
    //   201: dup
    //   202: aload_0
    //   203: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   206: invokevirtual 169	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   209: invokespecial 172	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   212: putfield 174	com/advancedquonsettechnology/hcaapp/HCAClient:in	Ljava/io/InputStreamReader;
    //   215: aload_0
    //   216: new 39	java/io/BufferedReader
    //   219: dup
    //   220: aload_0
    //   221: getfield 174	com/advancedquonsettechnology/hcaapp/HCAClient:in	Ljava/io/InputStreamReader;
    //   224: invokespecial 177	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   227: putfield 37	com/advancedquonsettechnology/hcaapp/HCAClient:reader	Ljava/io/BufferedReader;
    //   230: aload_0
    //   231: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   234: getstatic 138	com/advancedquonsettechnology/hcaapp/HCAApplication:mIPTimeout	Ljava/lang/String;
    //   237: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   240: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   243: bipush 14
    //   245: invokestatic 180	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   248: putstatic 184	com/advancedquonsettechnology/hcaapp/HCAApplication:serverProtocol	Ljava/lang/Integer;
    //   251: new 77	java/lang/StringBuilder
    //   254: dup
    //   255: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   258: ldc -70
    //   260: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: getstatic 189	com/advancedquonsettechnology/hcaapp/HCAApplication:versionmajor	Ljava/lang/String;
    //   266: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: getstatic 192	com/advancedquonsettechnology/hcaapp/HCAApplication:versionminor	Ljava/lang/String;
    //   272: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: getstatic 195	com/advancedquonsettechnology/hcaapp/HCAApplication:versionbuild	Ljava/lang/String;
    //   278: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   284: astore 20
    //   286: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   289: ifeq +34 -> 323
    //   292: ldc 75
    //   294: new 77	java/lang/StringBuilder
    //   297: dup
    //   298: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   301: ldc -59
    //   303: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: aload 20
    //   308: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: ldc -57
    //   313: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   316: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   319: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   322: pop
    //   323: aload_0
    //   324: aload 20
    //   326: invokevirtual 203	com/advancedquonsettechnology/hcaapp/HCAClient:sendMessage	(Ljava/lang/String;)V
    //   329: aload_0
    //   330: invokevirtual 205	com/advancedquonsettechnology/hcaapp/HCAClient:getConnectResponse	()Ljava/lang/String;
    //   333: astore 21
    //   335: aload 21
    //   337: ifnull +13 -> 350
    //   340: aload 21
    //   342: invokevirtual 209	java/lang/String:length	()I
    //   345: bipush 16
    //   347: if_icmpeq +96 -> 443
    //   350: aload_0
    //   351: iconst_0
    //   352: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   355: ldc -45
    //   357: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   360: aload_0
    //   361: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   364: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   367: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   370: return
    //   371: astore 10
    //   373: aload_0
    //   374: iconst_0
    //   375: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   378: goto -294 -> 84
    //   381: astore 7
    //   383: ldc -43
    //   385: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   388: aload_0
    //   389: iconst_0
    //   390: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   393: aload_0
    //   394: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   397: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   400: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   403: return
    //   404: astore 8
    //   406: return
    //   407: astore 35
    //   409: aload_0
    //   410: iconst_0
    //   411: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   414: goto -245 -> 169
    //   417: astore 5
    //   419: ldc -41
    //   421: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   424: aload_0
    //   425: iconst_0
    //   426: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   429: aload_0
    //   430: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   433: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   436: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   439: return
    //   440: astore 6
    //   442: return
    //   443: aload 21
    //   445: iconst_3
    //   446: invokevirtual 219	java/lang/String:charAt	(I)C
    //   449: istore 23
    //   451: iload 23
    //   453: bipush 48
    //   455: if_icmpeq +973 -> 1428
    //   458: aload 21
    //   460: bipush 7
    //   462: bipush 10
    //   464: invokevirtual 223	java/lang/String:substring	(II)Ljava/lang/String;
    //   467: astore 24
    //   469: aload 24
    //   471: ldc -31
    //   473: invokevirtual 228	java/lang/String:compareTo	(Ljava/lang/String;)I
    //   476: ifne +134 -> 610
    //   479: new 77	java/lang/StringBuilder
    //   482: dup
    //   483: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   486: ldc -26
    //   488: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   491: getstatic 189	com/advancedquonsettechnology/hcaapp/HCAApplication:versionmajor	Ljava/lang/String;
    //   494: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   497: getstatic 192	com/advancedquonsettechnology/hcaapp/HCAApplication:versionminor	Ljava/lang/String;
    //   500: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   503: getstatic 195	com/advancedquonsettechnology/hcaapp/HCAApplication:versionbuild	Ljava/lang/String;
    //   506: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   509: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   512: astore 25
    //   514: bipush 10
    //   516: invokestatic 180	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   519: putstatic 184	com/advancedquonsettechnology/hcaapp/HCAApplication:serverProtocol	Ljava/lang/Integer;
    //   522: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   525: ifeq +34 -> 559
    //   528: ldc 75
    //   530: new 77	java/lang/StringBuilder
    //   533: dup
    //   534: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   537: ldc -59
    //   539: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   542: aload 25
    //   544: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: ldc -57
    //   549: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   552: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   555: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   558: pop
    //   559: aload_0
    //   560: aload 25
    //   562: invokevirtual 203	com/advancedquonsettechnology/hcaapp/HCAClient:sendMessage	(Ljava/lang/String;)V
    //   565: aload_0
    //   566: invokevirtual 205	com/advancedquonsettechnology/hcaapp/HCAClient:getConnectResponse	()Ljava/lang/String;
    //   569: astore 21
    //   571: aload 21
    //   573: ifnull +13 -> 586
    //   576: aload 21
    //   578: invokevirtual 209	java/lang/String:length	()I
    //   581: bipush 16
    //   583: if_icmpeq +223 -> 806
    //   586: aload_0
    //   587: iconst_0
    //   588: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   591: ldc -45
    //   593: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   596: aload_0
    //   597: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   600: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   603: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   606: return
    //   607: astore 26
    //   609: return
    //   610: aload 24
    //   612: ldc -24
    //   614: invokevirtual 228	java/lang/String:compareTo	(Ljava/lang/String;)I
    //   617: ifne +74 -> 691
    //   620: new 77	java/lang/StringBuilder
    //   623: dup
    //   624: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   627: ldc -22
    //   629: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   632: getstatic 189	com/advancedquonsettechnology/hcaapp/HCAApplication:versionmajor	Ljava/lang/String;
    //   635: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   638: getstatic 192	com/advancedquonsettechnology/hcaapp/HCAApplication:versionminor	Ljava/lang/String;
    //   641: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   644: getstatic 195	com/advancedquonsettechnology/hcaapp/HCAApplication:versionbuild	Ljava/lang/String;
    //   647: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   650: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   653: astore 25
    //   655: bipush 11
    //   657: invokestatic 180	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   660: putstatic 184	com/advancedquonsettechnology/hcaapp/HCAApplication:serverProtocol	Ljava/lang/Integer;
    //   663: goto -141 -> 522
    //   666: astore_3
    //   667: ldc -20
    //   669: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   672: aload_0
    //   673: iconst_0
    //   674: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   677: aload_0
    //   678: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   681: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   684: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   687: return
    //   688: astore 4
    //   690: return
    //   691: aload 24
    //   693: ldc -18
    //   695: invokevirtual 228	java/lang/String:compareTo	(Ljava/lang/String;)I
    //   698: ifne +62 -> 760
    //   701: new 77	java/lang/StringBuilder
    //   704: dup
    //   705: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   708: ldc -16
    //   710: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   713: getstatic 189	com/advancedquonsettechnology/hcaapp/HCAApplication:versionmajor	Ljava/lang/String;
    //   716: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   719: getstatic 192	com/advancedquonsettechnology/hcaapp/HCAApplication:versionminor	Ljava/lang/String;
    //   722: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   725: getstatic 195	com/advancedquonsettechnology/hcaapp/HCAApplication:versionbuild	Ljava/lang/String;
    //   728: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   731: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   734: astore 25
    //   736: bipush 12
    //   738: invokestatic 180	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   741: putstatic 184	com/advancedquonsettechnology/hcaapp/HCAApplication:serverProtocol	Ljava/lang/Integer;
    //   744: goto -222 -> 522
    //   747: astore_1
    //   748: aload_0
    //   749: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   752: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   755: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   758: aload_1
    //   759: athrow
    //   760: new 77	java/lang/StringBuilder
    //   763: dup
    //   764: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   767: ldc -14
    //   769: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   772: getstatic 189	com/advancedquonsettechnology/hcaapp/HCAApplication:versionmajor	Ljava/lang/String;
    //   775: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   778: getstatic 192	com/advancedquonsettechnology/hcaapp/HCAApplication:versionminor	Ljava/lang/String;
    //   781: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   784: getstatic 195	com/advancedquonsettechnology/hcaapp/HCAApplication:versionbuild	Ljava/lang/String;
    //   787: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   790: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   793: astore 25
    //   795: bipush 13
    //   797: invokestatic 180	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   800: putstatic 184	com/advancedquonsettechnology/hcaapp/HCAApplication:serverProtocol	Ljava/lang/Integer;
    //   803: goto -281 -> 522
    //   806: aload 21
    //   808: iconst_3
    //   809: invokevirtual 219	java/lang/String:charAt	(I)C
    //   812: istore 23
    //   814: goto +614 -> 1428
    //   817: aload_0
    //   818: getfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   821: istore 12
    //   823: iload 12
    //   825: ifne +369 -> 1194
    //   828: aload_0
    //   829: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   832: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   835: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   838: return
    //   839: astore 19
    //   841: return
    //   842: iconst_1
    //   843: istore 27
    //   845: new 77	java/lang/StringBuilder
    //   848: dup
    //   849: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   852: ldc 105
    //   854: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   857: aload 21
    //   859: bipush 8
    //   861: bipush 10
    //   863: invokevirtual 223	java/lang/String:substring	(II)Ljava/lang/String;
    //   866: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   869: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   872: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   875: putstatic 248	com/advancedquonsettechnology/hcaapp/HCAApplication:svrversionmajor	Ljava/lang/String;
    //   878: new 77	java/lang/StringBuilder
    //   881: dup
    //   882: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   885: ldc 105
    //   887: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   890: aload 21
    //   892: bipush 11
    //   894: bipush 13
    //   896: invokevirtual 223	java/lang/String:substring	(II)Ljava/lang/String;
    //   899: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   902: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   905: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   908: putstatic 251	com/advancedquonsettechnology/hcaapp/HCAApplication:svrversionminor	Ljava/lang/String;
    //   911: new 77	java/lang/StringBuilder
    //   914: dup
    //   915: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   918: ldc 105
    //   920: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   923: aload 21
    //   925: bipush 14
    //   927: bipush 16
    //   929: invokevirtual 223	java/lang/String:substring	(II)Ljava/lang/String;
    //   932: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   935: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   938: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   941: putstatic 254	com/advancedquonsettechnology/hcaapp/HCAApplication:svrversionbuild	Ljava/lang/String;
    //   944: getstatic 248	com/advancedquonsettechnology/hcaapp/HCAApplication:svrversionmajor	Ljava/lang/String;
    //   947: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   950: putstatic 257	com/advancedquonsettechnology/hcaapp/HCAApplication:svrversion	I
    //   953: getstatic 257	com/advancedquonsettechnology/hcaapp/HCAApplication:svrversion	I
    //   956: getstatic 260	com/advancedquonsettechnology/hcaapp/HCAApplication:svrversionmajormin	Ljava/lang/Integer;
    //   959: invokevirtual 263	java/lang/Integer:intValue	()I
    //   962: if_icmpge +6 -> 968
    //   965: iconst_0
    //   966: istore 27
    //   968: iload 27
    //   970: ifne +21 -> 991
    //   973: aload_0
    //   974: invokevirtual 265	com/advancedquonsettechnology/hcaapp/HCAClient:closeClient	()V
    //   977: ldc_w 267
    //   980: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   983: aload_0
    //   984: iconst_0
    //   985: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   988: goto -171 -> 817
    //   991: aload 21
    //   993: iconst_5
    //   994: invokevirtual 219	java/lang/String:charAt	(I)C
    //   997: bipush 49
    //   999: if_icmpne -182 -> 817
    //   1002: getstatic 270	com/advancedquonsettechnology/hcaapp/HCAApplication:mRemotePassword	Ljava/lang/String;
    //   1005: ldc 105
    //   1007: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1010: ifeq +84 -> 1094
    //   1013: iconst_3
    //   1014: anewarray 64	java/lang/String
    //   1017: dup
    //   1018: iconst_0
    //   1019: ldc_w 276
    //   1022: aastore
    //   1023: dup
    //   1024: iconst_1
    //   1025: ldc_w 278
    //   1028: aastore
    //   1029: dup
    //   1030: iconst_2
    //   1031: ldc_w 280
    //   1034: aastore
    //   1035: astore 28
    //   1037: aload_0
    //   1038: aload 28
    //   1040: invokevirtual 284	com/advancedquonsettechnology/hcaapp/HCAClient:sendCommand	([Ljava/lang/String;)V
    //   1043: getstatic 288	com/advancedquonsettechnology/hcaapp/HCAApplication:client	Lcom/advancedquonsettechnology/hcaapp/HCAClient;
    //   1046: iconst_1
    //   1047: invokevirtual 292	com/advancedquonsettechnology/hcaapp/HCAClient:waitForPacket	(Z)Lcom/advancedquonsettechnology/hcaapp/Response;
    //   1050: astore 31
    //   1052: aload 31
    //   1054: astore 30
    //   1056: aload 30
    //   1058: ifnull +18 -> 1076
    //   1061: aload 30
    //   1063: iconst_0
    //   1064: invokevirtual 298	com/advancedquonsettechnology/hcaapp/Response:get	(I)Ljava/lang/String;
    //   1067: ldc_w 300
    //   1070: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1073: ifne -256 -> 817
    //   1076: aload_0
    //   1077: invokevirtual 265	com/advancedquonsettechnology/hcaapp/HCAClient:closeClient	()V
    //   1080: ldc_w 302
    //   1083: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   1086: aload_0
    //   1087: iconst_0
    //   1088: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   1091: goto -274 -> 817
    //   1094: iconst_4
    //   1095: anewarray 64	java/lang/String
    //   1098: astore 28
    //   1100: aload 28
    //   1102: iconst_0
    //   1103: ldc_w 276
    //   1106: aastore
    //   1107: aload 28
    //   1109: iconst_1
    //   1110: ldc_w 278
    //   1113: aastore
    //   1114: aload 28
    //   1116: iconst_2
    //   1117: ldc_w 280
    //   1120: aastore
    //   1121: aload 28
    //   1123: iconst_3
    //   1124: getstatic 270	com/advancedquonsettechnology/hcaapp/HCAApplication:mRemotePassword	Ljava/lang/String;
    //   1127: aastore
    //   1128: goto -91 -> 1037
    //   1131: astore 29
    //   1133: aload 29
    //   1135: invokevirtual 97	java/lang/Exception:printStackTrace	()V
    //   1138: aconst_null
    //   1139: astore 30
    //   1141: goto -85 -> 1056
    //   1144: aload_0
    //   1145: invokevirtual 265	com/advancedquonsettechnology/hcaapp/HCAClient:closeClient	()V
    //   1148: ldc_w 304
    //   1151: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   1154: aload_0
    //   1155: iconst_0
    //   1156: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   1159: goto -342 -> 817
    //   1162: aload_0
    //   1163: invokevirtual 265	com/advancedquonsettechnology/hcaapp/HCAClient:closeClient	()V
    //   1166: ldc_w 306
    //   1169: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   1172: aload_0
    //   1173: iconst_0
    //   1174: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   1177: goto -360 -> 817
    //   1180: ldc_w 308
    //   1183: putstatic 108	com/advancedquonsettechnology/hcaapp/HCAApplication:mErrorText	Ljava/lang/String;
    //   1186: aload_0
    //   1187: iconst_0
    //   1188: putfield 24	com/advancedquonsettechnology/hcaapp/HCAClient:connected	Z
    //   1191: goto -374 -> 817
    //   1194: getstatic 184	com/advancedquonsettechnology/hcaapp/HCAApplication:serverProtocol	Ljava/lang/Integer;
    //   1197: invokevirtual 263	java/lang/Integer:intValue	()I
    //   1200: bipush 11
    //   1202: if_icmplt +195 -> 1397
    //   1205: aload_0
    //   1206: iconst_2
    //   1207: anewarray 64	java/lang/String
    //   1210: dup
    //   1211: iconst_0
    //   1212: ldc_w 310
    //   1215: aastore
    //   1216: dup
    //   1217: iconst_1
    //   1218: ldc_w 312
    //   1221: aastore
    //   1222: invokevirtual 284	com/advancedquonsettechnology/hcaapp/HCAClient:sendCommand	([Ljava/lang/String;)V
    //   1225: getstatic 288	com/advancedquonsettechnology/hcaapp/HCAApplication:client	Lcom/advancedquonsettechnology/hcaapp/HCAClient;
    //   1228: iconst_1
    //   1229: invokevirtual 292	com/advancedquonsettechnology/hcaapp/HCAClient:waitForPacket	(Z)Lcom/advancedquonsettechnology/hcaapp/Response;
    //   1232: astore 15
    //   1234: aload 15
    //   1236: ifnull +161 -> 1397
    //   1239: aload 15
    //   1241: iconst_0
    //   1242: invokevirtual 298	com/advancedquonsettechnology/hcaapp/Response:get	(I)Ljava/lang/String;
    //   1245: ldc_w 300
    //   1248: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1251: ifeq +146 -> 1397
    //   1254: aload 15
    //   1256: iconst_1
    //   1257: invokevirtual 298	com/advancedquonsettechnology/hcaapp/Response:get	(I)Ljava/lang/String;
    //   1260: ldc_w 310
    //   1263: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1266: ifeq +131 -> 1397
    //   1269: aload 15
    //   1271: iconst_2
    //   1272: invokevirtual 298	com/advancedquonsettechnology/hcaapp/Response:get	(I)Ljava/lang/String;
    //   1275: ldc_w 312
    //   1278: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1281: ifeq +116 -> 1397
    //   1284: aload 15
    //   1286: iconst_3
    //   1287: invokevirtual 298	com/advancedquonsettechnology/hcaapp/Response:get	(I)Ljava/lang/String;
    //   1290: astore 16
    //   1292: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   1295: ifeq +34 -> 1329
    //   1298: ldc 75
    //   1300: new 77	java/lang/StringBuilder
    //   1303: dup
    //   1304: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   1307: ldc_w 314
    //   1310: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1313: getstatic 318	com/advancedquonsettechnology/hcaapp/HCAApplication:appState	Lcom/advancedquonsettechnology/hcaapp/HCAApplication$AppState;
    //   1316: getfield 323	com/advancedquonsettechnology/hcaapp/HCAApplication$AppState:designTS	Ljava/lang/String;
    //   1319: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1322: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1325: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1328: pop
    //   1329: getstatic 318	com/advancedquonsettechnology/hcaapp/HCAApplication:appState	Lcom/advancedquonsettechnology/hcaapp/HCAApplication$AppState;
    //   1332: getfield 323	com/advancedquonsettechnology/hcaapp/HCAApplication$AppState:designTS	Ljava/lang/String;
    //   1335: aload 16
    //   1337: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1340: ifne +57 -> 1397
    //   1343: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   1346: ifeq +36 -> 1382
    //   1349: ldc 75
    //   1351: new 77	java/lang/StringBuilder
    //   1354: dup
    //   1355: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   1358: ldc_w 325
    //   1361: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1364: aload 16
    //   1366: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1369: ldc_w 327
    //   1372: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1375: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1378: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1381: pop
    //   1382: getstatic 318	com/advancedquonsettechnology/hcaapp/HCAApplication:appState	Lcom/advancedquonsettechnology/hcaapp/HCAApplication$AppState;
    //   1385: iconst_0
    //   1386: putfield 330	com/advancedquonsettechnology/hcaapp/HCAApplication$AppState:designLoaded	Z
    //   1389: getstatic 318	com/advancedquonsettechnology/hcaapp/HCAApplication:appState	Lcom/advancedquonsettechnology/hcaapp/HCAApplication$AppState;
    //   1392: aload 16
    //   1394: putfield 323	com/advancedquonsettechnology/hcaapp/HCAApplication$AppState:designTS	Ljava/lang/String;
    //   1397: aload_0
    //   1398: getfield 26	com/advancedquonsettechnology/hcaapp/HCAClient:HCASocket	Ljava/net/Socket;
    //   1401: getstatic 145	com/advancedquonsettechnology/hcaapp/HCAApplication:socketReadTimeout	I
    //   1404: invokevirtual 149	java/net/Socket:setSoTimeout	(I)V
    //   1407: return
    //   1408: astore 13
    //   1410: return
    //   1411: astore 14
    //   1413: aload 14
    //   1415: invokevirtual 97	java/lang/Exception:printStackTrace	()V
    //   1418: goto -21 -> 1397
    //   1421: astore_2
    //   1422: goto -664 -> 758
    //   1425: astore 22
    //   1427: return
    //   1428: iload 23
    //   1430: tableswitch	default:+-613->817, 48:+-588->842, 49:+-613->817, 50:+-613->817, 51:+-286->1144, 52:+-268->1162
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1464	0	this	HCAClient
    //   747	12	1	localObject	Object
    //   1421	1	2	localException1	Exception
    //   666	1	3	localIOException	IOException
    //   688	1	4	localException2	Exception
    //   417	1	5	localUnknownHostException	java.net.UnknownHostException
    //   440	1	6	localException3	Exception
    //   381	1	7	localIllegalArgumentException	IllegalArgumentException
    //   404	1	8	localException4	Exception
    //   33	18	9	localInetSocketAddress1	java.net.InetSocketAddress
    //   371	1	10	localException5	Exception
    //   88	3	11	bool1	boolean
    //   821	3	12	bool2	boolean
    //   1408	1	13	localException6	Exception
    //   1411	3	14	localException7	Exception
    //   1232	53	15	localResponse1	Response
    //   1290	103	16	str1	String
    //   839	1	19	localException8	Exception
    //   284	41	20	str2	String
    //   333	659	21	str3	String
    //   1425	1	22	localException9	Exception
    //   449	980	23	i	int
    //   467	225	24	str4	String
    //   512	282	25	str5	String
    //   607	1	26	localException10	Exception
    //   843	126	27	j	int
    //   1035	87	28	arrayOfString	String[]
    //   1131	3	29	localException11	Exception
    //   1054	86	30	localResponse2	Response
    //   1050	3	31	localResponse3	Response
    //   114	18	34	localInetSocketAddress2	java.net.InetSocketAddress
    //   407	1	35	localException12	Exception
    // Exception table:
    //   from	to	target	type
    //   14	84	371	java/lang/Exception
    //   0	14	381	java/lang/IllegalArgumentException
    //   14	84	381	java/lang/IllegalArgumentException
    //   84	90	381	java/lang/IllegalArgumentException
    //   95	169	381	java/lang/IllegalArgumentException
    //   169	323	381	java/lang/IllegalArgumentException
    //   323	335	381	java/lang/IllegalArgumentException
    //   340	350	381	java/lang/IllegalArgumentException
    //   350	360	381	java/lang/IllegalArgumentException
    //   373	378	381	java/lang/IllegalArgumentException
    //   409	414	381	java/lang/IllegalArgumentException
    //   443	451	381	java/lang/IllegalArgumentException
    //   458	522	381	java/lang/IllegalArgumentException
    //   522	559	381	java/lang/IllegalArgumentException
    //   559	571	381	java/lang/IllegalArgumentException
    //   576	586	381	java/lang/IllegalArgumentException
    //   586	596	381	java/lang/IllegalArgumentException
    //   610	663	381	java/lang/IllegalArgumentException
    //   691	744	381	java/lang/IllegalArgumentException
    //   760	803	381	java/lang/IllegalArgumentException
    //   806	814	381	java/lang/IllegalArgumentException
    //   817	823	381	java/lang/IllegalArgumentException
    //   845	965	381	java/lang/IllegalArgumentException
    //   973	988	381	java/lang/IllegalArgumentException
    //   991	1037	381	java/lang/IllegalArgumentException
    //   1037	1043	381	java/lang/IllegalArgumentException
    //   1043	1052	381	java/lang/IllegalArgumentException
    //   1061	1076	381	java/lang/IllegalArgumentException
    //   1076	1091	381	java/lang/IllegalArgumentException
    //   1094	1128	381	java/lang/IllegalArgumentException
    //   1133	1138	381	java/lang/IllegalArgumentException
    //   1144	1159	381	java/lang/IllegalArgumentException
    //   1162	1177	381	java/lang/IllegalArgumentException
    //   1180	1191	381	java/lang/IllegalArgumentException
    //   1194	1225	381	java/lang/IllegalArgumentException
    //   1225	1234	381	java/lang/IllegalArgumentException
    //   1239	1329	381	java/lang/IllegalArgumentException
    //   1329	1382	381	java/lang/IllegalArgumentException
    //   1382	1397	381	java/lang/IllegalArgumentException
    //   1413	1418	381	java/lang/IllegalArgumentException
    //   393	403	404	java/lang/Exception
    //   95	169	407	java/lang/Exception
    //   0	14	417	java/net/UnknownHostException
    //   14	84	417	java/net/UnknownHostException
    //   84	90	417	java/net/UnknownHostException
    //   95	169	417	java/net/UnknownHostException
    //   169	323	417	java/net/UnknownHostException
    //   323	335	417	java/net/UnknownHostException
    //   340	350	417	java/net/UnknownHostException
    //   350	360	417	java/net/UnknownHostException
    //   373	378	417	java/net/UnknownHostException
    //   409	414	417	java/net/UnknownHostException
    //   443	451	417	java/net/UnknownHostException
    //   458	522	417	java/net/UnknownHostException
    //   522	559	417	java/net/UnknownHostException
    //   559	571	417	java/net/UnknownHostException
    //   576	586	417	java/net/UnknownHostException
    //   586	596	417	java/net/UnknownHostException
    //   610	663	417	java/net/UnknownHostException
    //   691	744	417	java/net/UnknownHostException
    //   760	803	417	java/net/UnknownHostException
    //   806	814	417	java/net/UnknownHostException
    //   817	823	417	java/net/UnknownHostException
    //   845	965	417	java/net/UnknownHostException
    //   973	988	417	java/net/UnknownHostException
    //   991	1037	417	java/net/UnknownHostException
    //   1037	1043	417	java/net/UnknownHostException
    //   1043	1052	417	java/net/UnknownHostException
    //   1061	1076	417	java/net/UnknownHostException
    //   1076	1091	417	java/net/UnknownHostException
    //   1094	1128	417	java/net/UnknownHostException
    //   1133	1138	417	java/net/UnknownHostException
    //   1144	1159	417	java/net/UnknownHostException
    //   1162	1177	417	java/net/UnknownHostException
    //   1180	1191	417	java/net/UnknownHostException
    //   1194	1225	417	java/net/UnknownHostException
    //   1225	1234	417	java/net/UnknownHostException
    //   1239	1329	417	java/net/UnknownHostException
    //   1329	1382	417	java/net/UnknownHostException
    //   1382	1397	417	java/net/UnknownHostException
    //   1413	1418	417	java/net/UnknownHostException
    //   429	439	440	java/lang/Exception
    //   596	606	607	java/lang/Exception
    //   0	14	666	java/io/IOException
    //   14	84	666	java/io/IOException
    //   84	90	666	java/io/IOException
    //   95	169	666	java/io/IOException
    //   169	323	666	java/io/IOException
    //   323	335	666	java/io/IOException
    //   340	350	666	java/io/IOException
    //   350	360	666	java/io/IOException
    //   373	378	666	java/io/IOException
    //   409	414	666	java/io/IOException
    //   443	451	666	java/io/IOException
    //   458	522	666	java/io/IOException
    //   522	559	666	java/io/IOException
    //   559	571	666	java/io/IOException
    //   576	586	666	java/io/IOException
    //   586	596	666	java/io/IOException
    //   610	663	666	java/io/IOException
    //   691	744	666	java/io/IOException
    //   760	803	666	java/io/IOException
    //   806	814	666	java/io/IOException
    //   817	823	666	java/io/IOException
    //   845	965	666	java/io/IOException
    //   973	988	666	java/io/IOException
    //   991	1037	666	java/io/IOException
    //   1037	1043	666	java/io/IOException
    //   1043	1052	666	java/io/IOException
    //   1061	1076	666	java/io/IOException
    //   1076	1091	666	java/io/IOException
    //   1094	1128	666	java/io/IOException
    //   1133	1138	666	java/io/IOException
    //   1144	1159	666	java/io/IOException
    //   1162	1177	666	java/io/IOException
    //   1180	1191	666	java/io/IOException
    //   1194	1225	666	java/io/IOException
    //   1225	1234	666	java/io/IOException
    //   1239	1329	666	java/io/IOException
    //   1329	1382	666	java/io/IOException
    //   1382	1397	666	java/io/IOException
    //   1413	1418	666	java/io/IOException
    //   677	687	688	java/lang/Exception
    //   0	14	747	finally
    //   14	84	747	finally
    //   84	90	747	finally
    //   95	169	747	finally
    //   169	323	747	finally
    //   323	335	747	finally
    //   340	350	747	finally
    //   350	360	747	finally
    //   373	378	747	finally
    //   383	393	747	finally
    //   409	414	747	finally
    //   419	429	747	finally
    //   443	451	747	finally
    //   458	522	747	finally
    //   522	559	747	finally
    //   559	571	747	finally
    //   576	586	747	finally
    //   586	596	747	finally
    //   610	663	747	finally
    //   667	677	747	finally
    //   691	744	747	finally
    //   760	803	747	finally
    //   806	814	747	finally
    //   817	823	747	finally
    //   845	965	747	finally
    //   973	988	747	finally
    //   991	1037	747	finally
    //   1037	1043	747	finally
    //   1043	1052	747	finally
    //   1061	1076	747	finally
    //   1076	1091	747	finally
    //   1094	1128	747	finally
    //   1133	1138	747	finally
    //   1144	1159	747	finally
    //   1162	1177	747	finally
    //   1180	1191	747	finally
    //   1194	1225	747	finally
    //   1225	1234	747	finally
    //   1239	1329	747	finally
    //   1329	1382	747	finally
    //   1382	1397	747	finally
    //   1413	1418	747	finally
    //   828	838	839	java/lang/Exception
    //   1043	1052	1131	java/lang/Exception
    //   1397	1407	1408	java/lang/Exception
    //   1225	1234	1411	java/lang/Exception
    //   1239	1329	1411	java/lang/Exception
    //   1329	1382	1411	java/lang/Exception
    //   1382	1397	1411	java/lang/Exception
    //   748	758	1421	java/lang/Exception
    //   360	370	1425	java/lang/Exception
  }
  
  void sendCommand(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    for (;;)
    {
      int i;
      try
      {
        i = 12 + paramString1.length();
        if (paramString3 == "") {
          break label610;
        }
        i += 4;
      }
      catch (IOException localIOException)
      {
        Object[] arrayOfObject1;
        String str1;
        int j;
        StringBuilder localStringBuilder1;
        Object[] arrayOfObject2;
        String str2;
        StringBuilder localStringBuilder2;
        Object[] arrayOfObject3;
        StringBuilder localStringBuilder3;
        Object[] arrayOfObject4;
        StringBuilder localStringBuilder4;
        Object[] arrayOfObject5;
        int k;
        StringBuilder localStringBuilder5;
        Object[] arrayOfObject6;
        String str3;
        String str4;
        String str5;
        localIOException.printStackTrace();
        return;
      }
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i);
      str1 = String.format("%04d", arrayOfObject1);
      j = i + paramString2.length();
      localStringBuilder1 = new StringBuilder().append(str1);
      arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(j);
      str2 = String.format("%04d", arrayOfObject2);
      if (paramString3 != "")
      {
        j += paramString3.length();
        localStringBuilder2 = new StringBuilder().append(str2);
        arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(j);
        str2 = String.format("%04d", arrayOfObject3);
      }
      if (paramString4 != "")
      {
        j += paramString4.length();
        localStringBuilder3 = new StringBuilder().append(str2);
        arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(j);
        str2 = String.format("%04d", arrayOfObject4);
      }
      if (paramString5 != "")
      {
        j += paramString5.length();
        localStringBuilder4 = new StringBuilder().append(str2);
        arrayOfObject5 = new Object[1];
        arrayOfObject5[0] = Integer.valueOf(j);
        str2 = String.format("%04d", arrayOfObject5);
      }
      if (paramString6 != "")
      {
        k = j + paramString6.length();
        localStringBuilder5 = new StringBuilder().append(str2);
        arrayOfObject6 = new Object[1];
        arrayOfObject6[0] = Integer.valueOf(k);
        str2 = String.format("%04d", arrayOfObject6);
      }
      str3 = str2 + "    ";
      str4 = paramString1 + paramString2;
      if (paramString3 != "") {
        str4 = str4 + paramString3;
      }
      if (paramString4 != "") {
        str4 = str4 + paramString4;
      }
      if (paramString5 != "") {
        str4 = str4 + paramString5;
      }
      if (paramString6 != "") {
        str4 = str4 + paramString6;
      }
      str5 = str3 + str4;
      if (HCAApplication.devMode) {
        Log.d("HCA", "sendCommand 2 [" + str5 + "] len " + str5.length());
      }
      this.out.write(str5.getBytes());
      this.out.flush();
      return;
      label610:
      if (paramString4 != "") {
        i += 4;
      }
      if (paramString5 != "") {
        i += 4;
      }
      if (paramString6 != "") {
        i += 4;
      }
    }
  }
  
  void sendCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      int j;
      int m;
      int n;
      try
      {
        HCAApplication.mErrorText = "";
        int i = 12 + paramArrayOfString[0].length();
        j = 2;
        if (j < paramArrayOfString.length)
        {
          if (paramArrayOfString[j] != "") {
            i += 4;
          }
        }
        else
        {
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(i);
          String str1 = String.format("%04d", arrayOfObject1);
          String str2 = paramArrayOfString[0];
          int k = i + paramArrayOfString[1].length();
          StringBuilder localStringBuilder1 = new StringBuilder().append(str1);
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(k);
          String str3 = String.format("%04d", arrayOfObject2);
          m = 2;
          if (m < paramArrayOfString.length)
          {
            if (paramArrayOfString[m] == "") {
              break label422;
            }
            k += paramArrayOfString[m].length();
            StringBuilder localStringBuilder2 = new StringBuilder().append(str3);
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = Integer.valueOf(k);
            str3 = String.format("%04d", arrayOfObject3);
            break label422;
          }
          String str4 = str3 + "    ";
          String str5 = str2 + paramArrayOfString[1];
          n = 2;
          if (n < paramArrayOfString.length)
          {
            if (paramArrayOfString[n] == "") {
              break label428;
            }
            str5 = str5 + paramArrayOfString[n];
            break label428;
          }
          String str6 = str4 + str5;
          if (HCAApplication.devMode) {
            Log.d("HCA", "sendCommand [" + str6 + "] len " + str6.length());
          }
          this.out.write(str6.getBytes());
          this.out.flush();
          return;
        }
      }
      catch (IOException localIOException)
      {
        HCAApplication.mErrorText = "sendCommand IO Exception - " + localIOException.getMessage();
        return;
      }
      j++;
      continue;
      label422:
      m++;
      continue;
      label428:
      n++;
    }
  }
  
  void sendMessage(String paramString)
  {
    try
    {
      if (HCAApplication.devMode) {
        Log.d("HCA", "sendMessage [" + paramString + "] len " + paramString.length());
      }
      this.out.write(paramString.getBytes());
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  void sendPacket(String paramString)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(8 + paramString.length());
      String str = String.format("%04u", arrayOfObject) + "    " + paramString;
      if (HCAApplication.devMode) {
        Log.d("HCA", "sendPacket [" + str + "] len " + str.length());
      }
      this.out.write(str.getBytes());
      this.out.flush();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  /* Error */
  Response waitForPacket(boolean paramBoolean)
    throws Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_0
    //   3: istore_2
    //   4: sipush 10240
    //   7: newarray char
    //   9: astore 4
    //   11: iconst_m1
    //   12: istore 5
    //   14: aconst_null
    //   15: astore 6
    //   17: bipush 20
    //   19: istore 7
    //   21: iload 7
    //   23: ifle +355 -> 378
    //   26: aload_0
    //   27: getfield 37	com/advancedquonsettechnology/hcaapp/HCAClient:reader	Ljava/io/BufferedReader;
    //   30: aload 4
    //   32: iload_2
    //   33: iconst_4
    //   34: invokevirtual 62	java/io/BufferedReader:read	([CII)I
    //   37: istore 27
    //   39: iload 27
    //   41: ifle +105 -> 146
    //   44: iload_2
    //   45: iload 27
    //   47: iadd
    //   48: istore_2
    //   49: aload 4
    //   51: iconst_0
    //   52: iload_2
    //   53: invokestatic 369	java/lang/String:copyValueOf	([CII)Ljava/lang/String;
    //   56: astore 6
    //   58: aload 6
    //   60: ldc_w 339
    //   63: invokevirtual 372	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   66: istore 5
    //   68: iload 5
    //   70: ifle -49 -> 21
    //   73: iload 5
    //   75: iconst_4
    //   76: if_icmplt +10 -> 86
    //   79: iload 5
    //   81: iconst_4
    //   82: irem
    //   83: ifeq +295 -> 378
    //   86: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   89: ifeq +49 -> 138
    //   92: ldc 75
    //   94: new 77	java/lang/StringBuilder
    //   97: dup
    //   98: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   101: ldc_w 374
    //   104: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: iload 5
    //   109: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   112: ldc_w 376
    //   115: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: aload 6
    //   120: invokevirtual 377	java/lang/String:toString	()Ljava/lang/String;
    //   123: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: ldc -57
    //   128: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   137: pop
    //   138: aconst_null
    //   139: astore 8
    //   141: aload_0
    //   142: monitorexit
    //   143: aload 8
    //   145: areturn
    //   146: new 29	java/io/IOException
    //   149: dup
    //   150: ldc_w 379
    //   153: invokespecial 381	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   156: athrow
    //   157: astore 25
    //   159: iload_1
    //   160: ifne +9 -> 169
    //   163: aconst_null
    //   164: astore 8
    //   166: goto -25 -> 141
    //   169: iinc 7 -1
    //   172: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   175: ifeq -154 -> 21
    //   178: iload_2
    //   179: ifle -158 -> 21
    //   182: ldc 75
    //   184: new 77	java/lang/StringBuilder
    //   187: dup
    //   188: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   191: ldc_w 383
    //   194: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: iload_2
    //   198: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   201: ldc_w 385
    //   204: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: iconst_4
    //   208: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   211: ldc_w 387
    //   214: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: aload 4
    //   219: iconst_0
    //   220: iload_2
    //   221: invokestatic 369	java/lang/String:copyValueOf	([CII)Ljava/lang/String;
    //   224: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: ldc -57
    //   229: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   235: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   238: pop
    //   239: goto -218 -> 21
    //   242: astore_3
    //   243: aload_0
    //   244: monitorexit
    //   245: aload_3
    //   246: athrow
    //   247: astore 23
    //   249: iload_1
    //   250: ifne +9 -> 259
    //   253: aconst_null
    //   254: astore 8
    //   256: goto -115 -> 141
    //   259: iinc 7 -1
    //   262: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   265: ifeq -244 -> 21
    //   268: ldc 75
    //   270: new 77	java/lang/StringBuilder
    //   273: dup
    //   274: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   277: ldc_w 389
    //   280: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: iload_2
    //   284: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   287: ldc_w 385
    //   290: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   293: iconst_4
    //   294: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   297: ldc_w 387
    //   300: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: aload 4
    //   305: iconst_0
    //   306: iload_2
    //   307: invokestatic 369	java/lang/String:copyValueOf	([CII)Ljava/lang/String;
    //   310: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   313: ldc -57
    //   315: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   321: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   324: pop
    //   325: goto -304 -> 21
    //   328: astore 21
    //   330: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   333: ifeq +37 -> 370
    //   336: iload_2
    //   337: ifle +33 -> 370
    //   340: ldc 75
    //   342: new 77	java/lang/StringBuilder
    //   345: dup
    //   346: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   349: ldc_w 391
    //   352: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   355: aload 21
    //   357: invokevirtual 87	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   360: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   363: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   366: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   369: pop
    //   370: aload 21
    //   372: invokevirtual 97	java/lang/Exception:printStackTrace	()V
    //   375: aload 21
    //   377: athrow
    //   378: iload 7
    //   380: ifgt +9 -> 389
    //   383: aconst_null
    //   384: astore 8
    //   386: goto -245 -> 141
    //   389: iload 5
    //   391: iconst_4
    //   392: isub
    //   393: istore 9
    //   395: aload 6
    //   397: iload 9
    //   399: iload 5
    //   401: invokevirtual 223	java/lang/String:substring	(II)Ljava/lang/String;
    //   404: invokestatic 131	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   407: istore 12
    //   409: iload 12
    //   411: iload_2
    //   412: isub
    //   413: istore 13
    //   415: iload 13
    //   417: ifle +256 -> 673
    //   420: bipush 20
    //   422: istore 14
    //   424: iload 13
    //   426: ifle +164 -> 590
    //   429: iload 14
    //   431: ifle +159 -> 590
    //   434: aload_0
    //   435: getfield 37	com/advancedquonsettechnology/hcaapp/HCAClient:reader	Ljava/io/BufferedReader;
    //   438: aload 4
    //   440: iload_2
    //   441: iload 13
    //   443: invokevirtual 62	java/io/BufferedReader:read	([CII)I
    //   446: istore 20
    //   448: iload 20
    //   450: ifge +229 -> 679
    //   453: new 29	java/io/IOException
    //   456: dup
    //   457: ldc_w 379
    //   460: invokespecial 381	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   463: athrow
    //   464: astore 10
    //   466: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   469: ifeq +37 -> 506
    //   472: iload_2
    //   473: ifle +33 -> 506
    //   476: ldc 75
    //   478: new 77	java/lang/StringBuilder
    //   481: dup
    //   482: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   485: ldc_w 391
    //   488: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   491: aload 10
    //   493: invokevirtual 87	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   496: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   499: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   502: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   505: pop
    //   506: aload 10
    //   508: invokevirtual 97	java/lang/Exception:printStackTrace	()V
    //   511: aload 10
    //   513: athrow
    //   514: astore 18
    //   516: iinc 14 -1
    //   519: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   522: ifeq -98 -> 424
    //   525: iload_2
    //   526: ifle -102 -> 424
    //   529: ldc 75
    //   531: new 77	java/lang/StringBuilder
    //   534: dup
    //   535: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   538: ldc_w 393
    //   541: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   544: iload_2
    //   545: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   548: ldc_w 385
    //   551: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   554: iload 13
    //   556: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   559: ldc_w 387
    //   562: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   565: aload 4
    //   567: iconst_0
    //   568: iload_2
    //   569: invokestatic 369	java/lang/String:copyValueOf	([CII)Ljava/lang/String;
    //   572: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   575: ldc -57
    //   577: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   580: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   583: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   586: pop
    //   587: goto -163 -> 424
    //   590: iload 14
    //   592: ifle +75 -> 667
    //   595: aload 4
    //   597: iconst_0
    //   598: iload 12
    //   600: invokestatic 369	java/lang/String:copyValueOf	([CII)Ljava/lang/String;
    //   603: astore 15
    //   605: getstatic 73	com/advancedquonsettechnology/hcaapp/HCAApplication:devMode	Z
    //   608: ifeq +41 -> 649
    //   611: ldc 75
    //   613: new 77	java/lang/StringBuilder
    //   616: dup
    //   617: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   620: ldc_w 395
    //   623: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   626: aload 15
    //   628: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   631: ldc_w 397
    //   634: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   637: iload 14
    //   639: invokevirtual 245	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   642: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   645: invokestatic 96	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   648: pop
    //   649: new 294	com/advancedquonsettechnology/hcaapp/Response
    //   652: dup
    //   653: aload 15
    //   655: invokespecial 398	com/advancedquonsettechnology/hcaapp/Response:<init>	(Ljava/lang/String;)V
    //   658: astore 16
    //   660: aload 16
    //   662: astore 8
    //   664: goto -523 -> 141
    //   667: aconst_null
    //   668: astore 8
    //   670: goto -529 -> 141
    //   673: aconst_null
    //   674: astore 8
    //   676: goto -535 -> 141
    //   679: iload_2
    //   680: iload 20
    //   682: iadd
    //   683: istore_2
    //   684: iload 13
    //   686: iload 20
    //   688: isub
    //   689: istore 13
    //   691: goto -267 -> 424
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	694	0	this	HCAClient
    //   0	694	1	paramBoolean	boolean
    //   3	681	2	i	int
    //   242	4	3	localObject1	Object
    //   9	587	4	arrayOfChar	char[]
    //   12	388	5	j	int
    //   15	381	6	str1	String
    //   19	360	7	k	int
    //   139	536	8	localObject2	Object
    //   393	5	9	m	int
    //   464	48	10	localException1	Exception
    //   407	192	12	n	int
    //   413	277	13	i1	int
    //   422	216	14	i2	int
    //   603	51	15	str2	String
    //   658	3	16	localResponse	Response
    //   514	1	18	localSocketTimeoutException1	java.net.SocketTimeoutException
    //   446	243	20	i3	int
    //   328	48	21	localException2	Exception
    //   247	1	23	localIOException	IOException
    //   157	1	25	localSocketTimeoutException2	java.net.SocketTimeoutException
    //   37	11	27	i4	int
    // Exception table:
    //   from	to	target	type
    //   26	39	157	java/net/SocketTimeoutException
    //   49	68	157	java/net/SocketTimeoutException
    //   86	138	157	java/net/SocketTimeoutException
    //   146	157	157	java/net/SocketTimeoutException
    //   4	11	242	finally
    //   26	39	242	finally
    //   49	68	242	finally
    //   86	138	242	finally
    //   146	157	242	finally
    //   172	178	242	finally
    //   182	239	242	finally
    //   262	325	242	finally
    //   330	336	242	finally
    //   340	370	242	finally
    //   370	378	242	finally
    //   395	409	242	finally
    //   434	448	242	finally
    //   453	464	242	finally
    //   466	472	242	finally
    //   476	506	242	finally
    //   506	514	242	finally
    //   519	525	242	finally
    //   529	587	242	finally
    //   595	649	242	finally
    //   649	660	242	finally
    //   26	39	247	java/io/IOException
    //   49	68	247	java/io/IOException
    //   86	138	247	java/io/IOException
    //   146	157	247	java/io/IOException
    //   26	39	328	java/lang/Exception
    //   49	68	328	java/lang/Exception
    //   86	138	328	java/lang/Exception
    //   146	157	328	java/lang/Exception
    //   395	409	464	java/lang/Exception
    //   434	448	464	java/lang/Exception
    //   453	464	464	java/lang/Exception
    //   519	525	464	java/lang/Exception
    //   529	587	464	java/lang/Exception
    //   595	649	464	java/lang/Exception
    //   649	660	464	java/lang/Exception
    //   434	448	514	java/net/SocketTimeoutException
  }
}
