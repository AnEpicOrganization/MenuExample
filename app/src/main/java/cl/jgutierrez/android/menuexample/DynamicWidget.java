package cl.jgutierrez.android.menuexample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by matias on 31-08-16.
 */

public class DynamicWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        //Iteramos la lista de widgets en ejecución
        for (int i = 0; i < appWidgetIds.length; i++)
        {
            //ID del widget actual
            int widgetId = appWidgetIds[i];

            //Actualizamos el widget actual
            try {
                updateWidget(context, appWidgetManager, widgetId);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateWidget(Context context,
                                        AppWidgetManager appWidgetManager, int widgetId) throws ParseException {
        //Recuperamos el mensaje personalizado para el widget actual
        SharedPreferences prefs =
                context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        String message = prefs.getString("msg_" + widgetId, "Hora actual:");

        //Obtenemos la lista de controles del widget actual
        RemoteViews controles =
                new RemoteViews(context.getPackageName(), R.layout.dynamic_widget);

        //Actualizamos el mensaje en el control del widget
        controles.setTextViewText(R.id.LblMessage, message);

        //Obtenemos la hora actual
        Calendar calendario = new GregorianCalendar();
        String time = calendario.getTime().toLocaleString();

        //Actualizamos la hora en el control del widget
        controles.setTextViewText(R.id.LblTime, time);

        //Notificamos al manager de la actualización del widget actual
        appWidgetManager.updateAppWidget(widgetId, controles);

        Intent intent = new Intent("cl.jgutierrez.android.menuexample.UPDATE_WIDGET");
        intent.putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, widgetId,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

        controles.setOnClickPendingIntent(R.id.BtnUpdate, pendingIntent);

        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent2 =
                PendingIntent.getActivity(context, widgetId,
                        intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        controles.setOnClickPendingIntent(R.id.FrmWidget, pendingIntent2);


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("cl.jgutierrez.android.menuexample.UPDATE_WIDGET")) {
            //Obtenemos el ID del widget a actualizar
            int widgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            //Obtenemos el widget manager de nuestro contexto
            AppWidgetManager widgetManager =
                    AppWidgetManager.getInstance(context);

            //Actualizamos el widget
            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                try {
                    updateWidget(context, widgetManager, widgetId);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
