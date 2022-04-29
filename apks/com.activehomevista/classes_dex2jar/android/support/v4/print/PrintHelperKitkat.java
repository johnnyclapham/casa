/*
 * Decompiled with CFR 0_96.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.ColorMatrix
 *  android.graphics.ColorMatrixColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.pdf.PdfDocument
 *  android.graphics.pdf.PdfDocument$Page
 *  android.graphics.pdf.PdfDocument$PageInfo
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.CancellationSignal$OnCancelListener
 *  android.os.ParcelFileDescriptor
 *  android.print.PageRange
 *  android.print.PrintAttributes
 *  android.print.PrintAttributes$Builder
 *  android.print.PrintAttributes$MediaSize
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$LayoutResultCallback
 *  android.print.PrintDocumentAdapter$WriteResultCallback
 *  android.print.PrintDocumentInfo
 *  android.print.PrintDocumentInfo$Builder
 *  android.print.PrintJob
 *  android.print.PrintManager
 *  android.print.pdf.PrintedPdfDocument
 *  android.util.Log
 */
package android.support.v4.print;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class PrintHelperKitkat {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelperKitkat";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode = 2;
    final Context mContext;
    BitmapFactory.Options mDecodeOptions = null;
    private final Object mLock = new Object();
    int mOrientation = 1;
    int mScaleMode = 2;

    PrintHelperKitkat(Context context) {
        this.mContext = context;
    }

    private Bitmap convertBitmapForColorMode(Bitmap bitmap, int n) {
        if (n != 1) {
            return bitmap;
        }
        Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter((ColorFilter)new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.setBitmap(null);
        return bitmap2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Matrix getMatrix(int n, int n2, RectF rectF, int n3) {
        Matrix matrix = new Matrix();
        float f = rectF.width() / (float)n;
        float f2 = n3 == 2 ? Math.max(f, rectF.height() / (float)n2) : Math.min(f, rectF.height() / (float)n2);
        matrix.postScale(f2, f2);
        matrix.postTranslate((rectF.width() - f2 * (float)n) / 2.0f, (rectF.height() - f2 * (float)n2) / 2.0f);
        return matrix;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    private Bitmap loadBitmap(Uri uri, BitmapFactory.Options options) throws FileNotFoundException {
        Bitmap bitmap;
        if (uri == null) throw new IllegalArgumentException("bad argument to loadBitmap");
        if (this.mContext == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream inputStream = null;
        try {
            inputStream = this.mContext.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream((InputStream)inputStream, (Rect)null, (BitmapFactory.Options)options);
            if (inputStream == null) return bitmap;
        }
        catch (Throwable var4_6) {
            if (inputStream == null) throw var4_6;
            try {
                inputStream.close();
            }
            catch (IOException iOException) {
                Log.w((String)"PrintHelperKitkat", (String)"close fail ", (Throwable)iOException);
                throw var4_6;
            }
            throw var4_6;
        }
        try {
            inputStream.close();
            return bitmap;
        }
        catch (IOException var8_5) {
            Log.w((String)"PrintHelperKitkat", (String)"close fail ", (Throwable)var8_5);
            return bitmap;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Bitmap loadConstrainedBitmap(Uri uri, int n) throws FileNotFoundException {
        if (n <= 0) throw new IllegalArgumentException("bad argument to getScaledBitmap");
        if (uri == null) throw new IllegalArgumentException("bad argument to getScaledBitmap");
        if (this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        this.loadBitmap(uri, options);
        int n2 = options.outWidth;
        int n3 = options.outHeight;
        if (n2 <= 0) return null;
        if (n3 <= 0) {
            return null;
        }
        int n4 = 1;
        for (int i = Math.max((int)n2, (int)n3); i > n; --i, --n4) {
        }
        if (n4 <= 0) return null;
        if (Math.min(n2, n3) / n4 <= 0) return null;
        Object object = this.mLock;
        // MONITORENTER : object
        this.mDecodeOptions = new BitmapFactory.Options();
        this.mDecodeOptions.inMutable = true;
        this.mDecodeOptions.inSampleSize = n4;
        BitmapFactory.Options options2 = this.mDecodeOptions;
        // MONITOREXIT : object
        try {
            Bitmap bitmap = this.loadBitmap(uri, options2);
            return bitmap;
        }
        finally {
            Object object2 = this.mLock;
            // MONITORENTER : object2
            this.mDecodeOptions = null;
            // MONITOREXIT : object2
        }
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getScaleMode() {
        return this.mScaleMode;
    }

    public void printBitmap(final String string, final Bitmap bitmap, final OnPrintFinishCallback onPrintFinishCallback) {
        if (bitmap == null) {
            return;
        }
        final int n = this.mScaleMode;
        PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.MediaSize mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
        }
        PrintAttributes printAttributes = new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
        printManager.print(string, (PrintDocumentAdapter)new PrintDocumentAdapter(){
            private PrintAttributes mAttributes;

            public void onFinish() {
                if (onPrintFinishCallback != null) {
                    onPrintFinishCallback.onFinish();
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                boolean bl = true;
                this.mAttributes = printAttributes2;
                PrintDocumentInfo printDocumentInfo = new PrintDocumentInfo.Builder(string).setContentType((int)bl ? 1 : 0).setPageCount((int)bl ? 1 : 0).build();
                if (printAttributes2.equals((Object)printAttributes)) {
                    bl = false;
                }
                layoutResultCallback.onLayoutFinished(printDocumentInfo, bl);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void onWrite(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                Bitmap bitmap2 = PrintHelperKitkat.this.convertBitmapForColorMode(bitmap, this.mAttributes.getColorMode());
                try {
                    PdfDocument.Page page = printedPdfDocument.startPage(1);
                    RectF rectF = new RectF(page.getInfo().getContentRect());
                    Matrix matrix = PrintHelperKitkat.this.getMatrix(bitmap2.getWidth(), bitmap2.getHeight(), rectF, n);
                    page.getCanvas().drawBitmap(bitmap2, matrix, null);
                    printedPdfDocument.finishPage(page);
                    try {
                        printedPdfDocument.writeTo((OutputStream)new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                        PageRange[] arrpageRange2 = new PageRange[]{PageRange.ALL_PAGES};
                        writeResultCallback.onWriteFinished(arrpageRange2);
                        do {
                            return;
                            break;
                        } while (true);
                    }
                    catch (IOException var12_11) {
                        Log.e((String)"PrintHelperKitkat", (String)"Error writing printed content", (Throwable)var12_11);
                        writeResultCallback.onWriteFailed(null);
                        return;
                    }
                }
                finally {
                    if (printedPdfDocument != null) {
                        printedPdfDocument.close();
                    }
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                    if (bitmap2 != bitmap) {
                        bitmap2.recycle();
                    }
                }
            }
        }, printAttributes);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void printBitmap(final String string, final Uri uri, final OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
         var4_4 = new PrintDocumentAdapter(this.mScaleMode){
            private PrintAttributes mAttributes;
            Bitmap mBitmap;
            AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
            final /* synthetic */ int val$fittingMode;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             */
            private void cancelLoad() {
                Object object = PrintHelperKitkat.this.mLock;
                synchronized (object) {
                    if (PrintHelperKitkat.this.mDecodeOptions != null) {
                        PrintHelperKitkat.this.mDecodeOptions.requestCancelDecode();
                        PrintHelperKitkat.this.mDecodeOptions = null;
                    }
                    return;
                }
            }

            public void onFinish() {
                super.onFinish();
                this.cancelLoad();
                if (this.mLoadBitmap != null) {
                    this.mLoadBitmap.cancel(true);
                }
                if (onPrintFinishCallback != null) {
                    onPrintFinishCallback.onFinish();
                }
                if (this.mBitmap != null) {
                    this.mBitmap.recycle();
                    this.mBitmap = null;
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onLayout(final PrintAttributes printAttributes, final PrintAttributes printAttributes2, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                boolean bl = true;
                this.mAttributes = printAttributes2;
                if (cancellationSignal.isCanceled()) {
                    layoutResultCallback.onLayoutCancelled();
                    return;
                }
                if (this.mBitmap == null) {
                    this.mLoadBitmap = new AsyncTask<Uri, Boolean, Bitmap>(){

                        protected /* varargs */ Bitmap doInBackground(Uri ... arruri) {
                            try {
                                Bitmap bitmap = PrintHelperKitkat.this.loadConstrainedBitmap(uri, 3500);
                                return bitmap;
                            }
                            catch (FileNotFoundException var2_3) {
                                return null;
                            }
                        }

                        protected void onCancelled(Bitmap bitmap) {
                            layoutResultCallback.onLayoutCancelled();
                            2.this.mLoadBitmap = null;
                        }

                        /*
                         * Enabled aggressive block sorting
                         */
                        protected void onPostExecute(Bitmap bitmap) {
                            boolean bl = true;
                            super.onPostExecute((Object)bitmap);
                            2.this.mBitmap = bitmap;
                            if (bitmap != null) {
                                PrintDocumentInfo printDocumentInfo = new PrintDocumentInfo.Builder(string).setContentType((int)bl ? 1 : 0).setPageCount((int)bl ? 1 : 0).build();
                                if (printAttributes2.equals((Object)printAttributes)) {
                                    bl = false;
                                }
                                layoutResultCallback.onLayoutFinished(printDocumentInfo, bl);
                            } else {
                                layoutResultCallback.onLayoutFailed(null);
                            }
                            2.this.mLoadBitmap = null;
                        }

                        protected void onPreExecute() {
                            cancellationSignal.setOnCancelListener((CancellationSignal.OnCancelListener)new CancellationSignal.OnCancelListener(){

                                public void onCancel() {
                                    2.this.cancelLoad();
                                    1.this.cancel(false);
                                }
                            });
                        }

                    }.execute((Object[])new Uri[0]);
                    return;
                }
                PrintDocumentInfo printDocumentInfo = new PrintDocumentInfo.Builder(string).setContentType((int)bl ? 1 : 0).setPageCount((int)bl ? 1 : 0).build();
                if (printAttributes2.equals((Object)printAttributes)) {
                    bl = false;
                }
                layoutResultCallback.onLayoutFinished(printDocumentInfo, bl);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void onWrite(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                Bitmap bitmap = PrintHelperKitkat.this.convertBitmapForColorMode(this.mBitmap, this.mAttributes.getColorMode());
                try {
                    PdfDocument.Page page = printedPdfDocument.startPage(1);
                    RectF rectF = new RectF(page.getInfo().getContentRect());
                    Matrix matrix = PrintHelperKitkat.this.getMatrix(this.mBitmap.getWidth(), this.mBitmap.getHeight(), rectF, this.val$fittingMode);
                    page.getCanvas().drawBitmap(bitmap, matrix, null);
                    printedPdfDocument.finishPage(page);
                    try {
                        printedPdfDocument.writeTo((OutputStream)new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                        PageRange[] arrpageRange2 = new PageRange[]{PageRange.ALL_PAGES};
                        writeResultCallback.onWriteFinished(arrpageRange2);
                        do {
                            return;
                            break;
                        } while (true);
                    }
                    catch (IOException var12_11) {
                        Log.e((String)"PrintHelperKitkat", (String)"Error writing printed content", (Throwable)var12_11);
                        writeResultCallback.onWriteFailed(null);
                        return;
                    }
                }
                finally {
                    if (printedPdfDocument != null) {
                        printedPdfDocument.close();
                    }
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                    if (bitmap != this.mBitmap) {
                        bitmap.recycle();
                    }
                }
            }

        };
        PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(this.mColorMode);
        if (this.mOrientation == 1) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        } else if (this.mOrientation == 2) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
        printManager.print(string, (PrintDocumentAdapter)var4_4, builder.build());
    }

    public void setColorMode(int n) {
        this.mColorMode = n;
    }

    public void setOrientation(int n) {
        this.mOrientation = n;
    }

    public void setScaleMode(int n) {
        this.mScaleMode = n;
    }

    public static interface OnPrintFinishCallback {
        public void onFinish();
    }

}

