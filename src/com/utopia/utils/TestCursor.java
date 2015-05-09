package com.utopia.utils;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

public class TestCursor extends CursorWrapper {
    private static final String TAG = "TestCursor";
    private boolean mIsClosed = false;
    private Throwable mTrace;
    
    public TestCursor(Cursor c) {
        super(c);
        mTrace = new Throwable("Explicit termination method 'close()' not called");
    }
    
    @Override
    public void close() {
        mIsClosed = true;
    }
    
    @Override
    public void finalize() throws Throwable {
        try {
            if (mIsClosed != true) {
                Log.e(TAG, "Cursor leaks", mTrace);
            }
        } finally {
            super.finalize();
        }
    }
}
