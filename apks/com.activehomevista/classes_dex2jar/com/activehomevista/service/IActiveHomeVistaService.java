/*
 * Decompiled with CFR 0_96.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.activehomevista.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.activehomevista.Room;
import com.activehomevista.service.IActiveHomeVistaServiceCallback;
import java.util.ArrayList;
import java.util.List;

public interface IActiveHomeVistaService
extends IInterface {
    public void deviceFunction(char var1, byte var2, byte var3, byte var4, byte var5, byte var6) throws RemoteException;

    public boolean getEmulationDimMemoryCap() throws RemoteException;

    public boolean getGlobalFunctionAllLightsOffCap() throws RemoteException;

    public boolean getLocalConnected() throws RemoteException;

    public int getLocalConnectionStatus() throws RemoteException;

    public List<Room> getLocalHouse(boolean[] var1, int[] var2) throws RemoteException;

    public int getLocalInterfaceType() throws RemoteException;

    public void getLocalStatus(byte[] var1, byte[] var2, byte[] var3) throws RemoteException;

    public boolean getRFCap() throws RemoteException;

    public void getServerStatus() throws RemoteException;

    public void globalFunction(byte var1) throws RemoteException;

    public void notifyForeground() throws RemoteException;

    public void registerCallback(IActiveHomeVistaServiceCallback var1) throws RemoteException;

    public void roomFunction(int var1, byte var2) throws RemoteException;

    public void unregisterCallback(IActiveHomeVistaServiceCallback var1) throws RemoteException;

    /*
     * Failed to analyse overrides
     */
    public static abstract class Stub
    extends Binder
    implements IActiveHomeVistaService {
        private static final String DESCRIPTOR = "com.activehomevista.service.IActiveHomeVistaService";
        static final int TRANSACTION_deviceFunction = 2;
        static final int TRANSACTION_getEmulationDimMemoryCap = 6;
        static final int TRANSACTION_getGlobalFunctionAllLightsOffCap = 5;
        static final int TRANSACTION_getLocalConnected = 10;
        static final int TRANSACTION_getLocalConnectionStatus = 9;
        static final int TRANSACTION_getLocalHouse = 12;
        static final int TRANSACTION_getLocalInterfaceType = 8;
        static final int TRANSACTION_getLocalStatus = 11;
        static final int TRANSACTION_getRFCap = 7;
        static final int TRANSACTION_getServerStatus = 4;
        static final int TRANSACTION_globalFunction = 1;
        static final int TRANSACTION_notifyForeground = 13;
        static final int TRANSACTION_registerCallback = 14;
        static final int TRANSACTION_roomFunction = 3;
        static final int TRANSACTION_unregisterCallback = 15;

        public Stub() {
            this.attachInterface((IInterface)this, "com.activehomevista.service.IActiveHomeVistaService");
        }

        public static IActiveHomeVistaService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.activehomevista.service.IActiveHomeVistaService");
            if (iInterface != null && iInterface instanceof IActiveHomeVistaService) {
                return (IActiveHomeVistaService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                case 1598968902: {
                    parcel2.writeString("com.activehomevista.service.IActiveHomeVistaService");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    this.globalFunction(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    this.deviceFunction((char)parcel.readInt(), parcel.readByte(), parcel.readByte(), parcel.readByte(), parcel.readByte(), parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    this.roomFunction(parcel.readInt(), parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    this.getServerStatus();
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    boolean bl = this.getGlobalFunctionAllLightsOffCap();
                    parcel2.writeNoException();
                    int n3 = bl ? 1 : 0;
                    parcel2.writeInt(n3);
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    boolean bl = this.getEmulationDimMemoryCap();
                    parcel2.writeNoException();
                    int n4 = bl ? 1 : 0;
                    parcel2.writeInt(n4);
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    boolean bl = this.getRFCap();
                    parcel2.writeNoException();
                    int n5 = bl ? 1 : 0;
                    parcel2.writeInt(n5);
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    int n6 = this.getLocalInterfaceType();
                    parcel2.writeNoException();
                    parcel2.writeInt(n6);
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    int n7 = this.getLocalConnectionStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(n7);
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    boolean bl = this.getLocalConnected();
                    parcel2.writeNoException();
                    int n8 = bl ? 1 : 0;
                    parcel2.writeInt(n8);
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    int n9 = parcel.readInt();
                    Object object = n9 < 0 ? null : (Object)new byte[n9];
                    int n10 = parcel.readInt();
                    Object object2 = n10 < 0 ? null : (Object)new byte[n10];
                    int n11 = parcel.readInt();
                    Object object3 = n11 < 0 ? null : (Object)new byte[n11];
                    this.getLocalStatus((byte[])object, (byte[])object2, (byte[])object3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray((byte[])object);
                    parcel2.writeByteArray((byte[])object2);
                    parcel2.writeByteArray((byte[])object3);
                    return true;
                }
                case 12: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    int n12 = parcel.readInt();
                    Object object = n12 < 0 ? null : (Object)new boolean[n12];
                    int n13 = parcel.readInt();
                    Object object4 = n13 < 0 ? null : (Object)new int[n13];
                    List list = this.getLocalHouse((boolean[])object, (int[])object4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(list);
                    parcel2.writeBooleanArray((boolean[])object);
                    parcel2.writeIntArray((int[])object4);
                    return true;
                }
                case 13: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    this.notifyForeground();
                    parcel2.writeNoException();
                    return true;
                }
                case 14: {
                    parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
                    this.registerCallback(IActiveHomeVistaServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 15: 
            }
            parcel.enforceInterface("com.activehomevista.service.IActiveHomeVistaService");
            this.unregisterCallback(IActiveHomeVistaServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        /*
         * Failed to analyse overrides
         */
        private static class Proxy
        implements IActiveHomeVistaService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void deviceFunction(char c, byte by, byte by2, byte by3, byte by4, byte by5) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    parcel.writeInt((int)c);
                    parcel.writeByte(by);
                    parcel.writeByte(by2);
                    parcel.writeByte(by3);
                    parcel.writeByte(by4);
                    parcel.writeByte(by5);
                    this.mRemote.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public boolean getEmulationDimMemoryCap() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block2 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                        this.mRemote.transact(6, parcel2, parcel, 0);
                        parcel.readException();
                        int n = parcel.readInt();
                        bl = false;
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable var3_5) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw var3_5;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            public boolean getGlobalFunctionAllLightsOffCap() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block2 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                        this.mRemote.transact(5, parcel2, parcel, 0);
                        parcel.readException();
                        int n = parcel.readInt();
                        bl = false;
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable var3_5) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw var3_5;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            public String getInterfaceDescriptor() {
                return "com.activehomevista.service.IActiveHomeVistaService";
            }

            public boolean getLocalConnected() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block2 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                        this.mRemote.transact(10, parcel2, parcel, 0);
                        parcel.readException();
                        int n = parcel.readInt();
                        bl = false;
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable var3_5) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw var3_5;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            public int getLocalConnectionStatus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    this.mRemote.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             */
            public List<Room> getLocalHouse(boolean[] arrbl, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    if (arrbl == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrbl.length);
                    }
                    if (arrn == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrn.length);
                    }
                    this.mRemote.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    ArrayList arrayList = parcel2.createTypedArrayList(Room.CREATOR);
                    parcel2.readBooleanArray(arrbl);
                    parcel2.readIntArray(arrn);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public int getLocalInterfaceType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    this.mRemote.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             */
            public void getLocalStatus(byte[] arrby, byte[] arrby2, byte[] arrby3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    if (arrby == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrby.length);
                    }
                    if (arrby2 == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrby2.length);
                    }
                    if (arrby3 == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrby3.length);
                    }
                    this.mRemote.transact(11, parcel, parcel2, 0);
                    parcel2.readException();
                    parcel2.readByteArray(arrby);
                    parcel2.readByteArray(arrby2);
                    parcel2.readByteArray(arrby3);
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public boolean getRFCap() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block2 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                        this.mRemote.transact(7, parcel2, parcel, 0);
                        parcel.readException();
                        int n = parcel.readInt();
                        bl = false;
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable var3_5) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw var3_5;
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            public void getServerStatus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    this.mRemote.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public void globalFunction(byte by) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    parcel.writeByte(by);
                    this.mRemote.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public void notifyForeground() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    this.mRemote.transact(13, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             */
            public void registerCallback(IActiveHomeVistaServiceCallback iActiveHomeVistaServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    IBinder iBinder = iActiveHomeVistaServiceCallback != null ? iActiveHomeVistaServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    this.mRemote.transact(14, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public void roomFunction(int n, byte by) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    parcel.writeInt(n);
                    parcel.writeByte(by);
                    this.mRemote.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             */
            public void unregisterCallback(IActiveHomeVistaServiceCallback iActiveHomeVistaServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.activehomevista.service.IActiveHomeVistaService");
                    IBinder iBinder = iActiveHomeVistaServiceCallback != null ? iActiveHomeVistaServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    this.mRemote.transact(15, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

