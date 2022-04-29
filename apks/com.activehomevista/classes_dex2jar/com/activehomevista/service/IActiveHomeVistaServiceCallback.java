package com.activehomevista.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IActiveHomeVistaServiceCallback
  extends IInterface
{
  public abstract void beginUpdateHouse()
    throws RemoteException;
  
  public abstract void endUpdateHouse()
    throws RemoteException;
  
  public abstract void updateConnectionStatus(int paramInt)
    throws RemoteException;
  
  public abstract void updateDeviceStatus()
    throws RemoteException;
  
  public abstract void updateInterfaceStatus(boolean paramBoolean, int paramInt)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IActiveHomeVistaServiceCallback
  {
    private static final String DESCRIPTOR = "com.activehomevista.service.IActiveHomeVistaServiceCallback";
    static final int TRANSACTION_beginUpdateHouse = 1;
    static final int TRANSACTION_endUpdateHouse = 2;
    static final int TRANSACTION_updateConnectionStatus = 5;
    static final int TRANSACTION_updateDeviceStatus = 3;
    static final int TRANSACTION_updateInterfaceStatus = 4;
    
    public Stub()
    {
      attachInterface(this, "com.activehomevista.service.IActiveHomeVistaServiceCallback");
    }
    
    public static IActiveHomeVistaServiceCallback asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.activehomevista.service.IActiveHomeVistaServiceCallback");
      if ((localIInterface != null) && ((localIInterface instanceof IActiveHomeVistaServiceCallback))) {
        return (IActiveHomeVistaServiceCallback)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.activehomevista.service.IActiveHomeVistaServiceCallback");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.activehomevista.service.IActiveHomeVistaServiceCallback");
        beginUpdateHouse();
        paramParcel2.writeNoException();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.activehomevista.service.IActiveHomeVistaServiceCallback");
        endUpdateHouse();
        paramParcel2.writeNoException();
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.activehomevista.service.IActiveHomeVistaServiceCallback");
        updateDeviceStatus();
        paramParcel2.writeNoException();
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.activehomevista.service.IActiveHomeVistaServiceCallback");
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool = true;; bool = false)
        {
          updateInterfaceStatus(bool, paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        }
      }
      paramParcel1.enforceInterface("com.activehomevista.service.IActiveHomeVistaServiceCallback");
      updateConnectionStatus(paramParcel1.readInt());
      paramParcel2.writeNoException();
      return true;
    }
    
    private static class Proxy
      implements IActiveHomeVistaServiceCallback
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public void beginUpdateHouse()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaServiceCallback");
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void endUpdateHouse()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaServiceCallback");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "com.activehomevista.service.IActiveHomeVistaServiceCallback";
      }
      
      public void updateConnectionStatus(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaServiceCallback");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void updateDeviceStatus()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaServiceCallback");
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void updateInterfaceStatus(boolean paramBoolean, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaServiceCallback");
          int i = 0;
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}
