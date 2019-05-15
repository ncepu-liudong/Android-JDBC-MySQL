package com.example.graduationproject.tool;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentTools {
    private static IntentTools ourInstance;

    public static IntentTools getInstance() {
        if (ourInstance == null) {
            synchronized (IntentTools.class) {
                if (ourInstance == null) {
                    ourInstance = new IntentTools();
                }
            }
        }
        return ourInstance;
    }

    public void intent(Context context, Class<?> cs, Bundle bundle) {
        Intent i = new Intent(context, cs);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        context.startActivity(i);
    }

    public void receiver(Context context, String string, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(string);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.sendBroadcast(intent);
    }
}
