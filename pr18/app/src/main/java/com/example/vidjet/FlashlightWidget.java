package com.example.vidjet;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.hardware.camera2.*;
import android.widget.RemoteViews;

public class FlashlightWidget extends AppWidgetProvider {
    private static boolean isFlashOn = false;

    @Override
    public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
        for (int id : ids) updateWidget(c, m, id);
    }

    @Override
    public void onReceive(Context c, Intent i) {
        if ("TOGGLE_FLASHLIGHT".equals(i.getAction())) {
            try {
                CameraManager cm = (CameraManager) c.getSystemService(Context.CAMERA_SERVICE);
                cm.setTorchMode(cm.getCameraIdList()[0], isFlashOn = !isFlashOn);
            } catch (Exception ignored) {}
            AppWidgetManager m = AppWidgetManager.getInstance(c);
            for (int id : m.getAppWidgetIds(new ComponentName(c, FlashlightWidget.class))) updateWidget(c, m, id);
        }
        super.onReceive(c, i);
    }

    private void updateWidget(Context c, AppWidgetManager m, int id) {
        RemoteViews v = new RemoteViews(c.getPackageName(), R.layout.widget_layout);
        Intent i = new Intent(c, FlashlightWidget.class).setAction("TOGGLE_FLASHLIGHT");
        PendingIntent p = PendingIntent.getBroadcast(c, 0, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        v.setOnClickPendingIntent(R.id.btnFlashlight, p);
        v.setImageViewResource(R.id.btnFlashlight, isFlashOn ? R.drawable.torch1 : R.drawable.torch);
        m.updateAppWidget(id, v);
    }
}