package arie;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import arie.myappwidget.NumberGenerator;
import arie.myappwidget.R;

public class BilanganAcakWidget extends AppWidgetProvider {
    private static String WIDGET_CLICK = "widgetsclick";
    private static String WIDGET_ID_EXTRA = "widget_id_extra";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.bilangan_acak_widget);
        String lastUpdate = "Random: "+ NumberGenerator.Generate(100);
        views.setTextViewText(R.id.appwidget_text, lastUpdate);
        views.setOnClickPendingIntent(R.id.btn_click, getPendingSelfIntent(context, appWidgetId, WIDGET_CLICK));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (WIDGET_CLICK.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bilangan_acak_widget);
            String lastUpdate = "Random: " + NumberGenerator.Generate(100);
            int appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0);

            views.setTextViewText(R.id.appwidget_text, lastUpdate);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, int appWidgetId, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        intent.putExtra(WIDGET_ID_EXTRA, appWidgetId);
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

