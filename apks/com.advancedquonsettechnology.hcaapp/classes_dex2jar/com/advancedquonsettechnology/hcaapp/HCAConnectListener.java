package com.advancedquonsettechnology.hcaapp;

public abstract interface HCAConnectListener
{
  public abstract void HCAServerStatus(ConnectStatus paramConnectStatus);
  
  public static enum ConnectStatus
  {
    static
    {
      CONNECT_FAILED = new ConnectStatus("CONNECT_FAILED", 1);
      DISCONNECTED = new ConnectStatus("DISCONNECTED", 2);
      NO_SERVER = new ConnectStatus("NO_SERVER", 3);
      NO_NETWORK = new ConnectStatus("NO_NETWORK", 4);
      ConnectStatus[] arrayOfConnectStatus = new ConnectStatus[5];
      arrayOfConnectStatus[0] = CONNECT_OK;
      arrayOfConnectStatus[1] = CONNECT_FAILED;
      arrayOfConnectStatus[2] = DISCONNECTED;
      arrayOfConnectStatus[3] = NO_SERVER;
      arrayOfConnectStatus[4] = NO_NETWORK;
      $VALUES = arrayOfConnectStatus;
    }
    
    private ConnectStatus() {}
  }
}
