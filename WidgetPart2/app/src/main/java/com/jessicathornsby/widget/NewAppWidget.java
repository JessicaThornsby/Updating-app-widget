package com.jessicathornsby.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import java.text.DateFormat;
import java.util.Date;
import android.widget.Toast;


public class NewAppWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//Retrieve the current time//

        String timeString =
                DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.id_value, String.valueOf(appWidgetId));
        views.setTextViewText(R.id.update_value,
                context.getResources().getString(
                        R.string.time, timeString));

//Create an Intent with the AppWidgetManager.ACTION_APPWIDGET_UPDATE action//

        Intent intentUpdate = new Intent(context, NewAppWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

//Update the current widget instance only, by creating an array that contains the widget’s unique ID//


        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

//Wrap the intent as a PendingIntent, using PendingIntent.getBroadcast()//

        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);

//Send the pending intent in response to the user tapping the ‘Update’ TextView//

        views.setOnClickPendingIntent(R.id.update, pendingUpdate);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://code.tutsplus.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.launch_url, pendingIntent);

//Request that the AppWidgetManager updates the application widget//

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Toast.makeText(context, "Widget has been updated! ", Toast.LENGTH_SHORT).show();
        }
    }
}
