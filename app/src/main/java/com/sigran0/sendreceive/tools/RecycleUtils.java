package com.sigran0.sendreceive.tools;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecycleUtils {
    private RecycleUtils(){
        throw new AssertionError();
    }
    public static void recursiveRecycle(View root) {
        if (root == null){
            return;
        }
        if (Build.VERSION.SDK_INT < 16) {
            root.setBackgroundDrawable(null);
        }else{
            root.setBackground(null);
        }
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                recursiveRecycle(group.getChildAt(i));
            }
            if (!(root instanceof AdapterView)) {
                group.removeAllViews();
            }

        }
        if (root instanceof ImageView) {
            ((ImageView) root).setImageDrawable(null);
        }
        return;
    }

    public static void recursiveRecycle(List<WeakReference<View>> recycleList) {
        for (WeakReference<View> ref : recycleList) {
            recursiveRecycle(ref.get());
        }
    }
}
