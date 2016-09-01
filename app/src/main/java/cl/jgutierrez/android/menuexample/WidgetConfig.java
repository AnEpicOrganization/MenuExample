package cl.jgutierrez.android.menuexample;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;

/**
 * Created by matias on 31-08-16.
 */

public class WidgetConfig extends Activity {

    private int widgetId = 0;

    private Button btnAccept;
    private Button btnCancel;
    private EditText txtMessage;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_widget_config);

        //Obtenemos el Intent que ha lanzado esta ventana
        //y recuperamos sus parámetros
        Intent intentOrigen = getIntent();
        Bundle params = intentOrigen.getExtras();

        //Obtenemos el ID del widget que se está configurando
        widgetId = params.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        //Establecemos el resultado por defecto (si se pulsa el botón 'Atrás'
        //del teléfono será éste el resultado devuelto).
        setResult(RESULT_CANCELED);

        //Obtenemos la referencia a los controles de la pantalla
        btnAccept = (Button)findViewById(R.id.BtnAccept);
        btnCancel = (Button)findViewById(R.id.BtnCancel);
        txtMessage = (EditText)findViewById(R.id.TxtMessage);

        //Implementación del botón "Cancelar"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Devolvemos como resultado: CANCELAR (RESULT_CANCELED)
                finish();
            }
        });

        //Implementación del botón "Aceptar"
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Guardamos el mensaje personalizado en las preferencias
                SharedPreferences prefs =
                        getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("msg_" + widgetId, txtMessage.getText().toString());
                editor.commit();

                //Actualizamos el widget tras la configuración
                AppWidgetManager appWidgetManager =
                        AppWidgetManager.getInstance(WidgetConfig.this);
                try {
                    DynamicWidget.updateWidget(WidgetConfig.this, appWidgetManager, widgetId);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Devolvemos como resultado: ACEPTAR (RESULT_OK)
                Intent resultado = new Intent();
                resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
