package cl.jgutierrez.android.menuexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {
    Switch compraAuto;
    Button btnSave;
    EditText numFruta;
    TextView textNumFruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        compraAuto = (Switch) findViewById(R.id.switchCompraAuto);
        numFruta = (EditText) findViewById(R.id.numFruta);
        btnSave =  (Button) findViewById(R.id.save);
        textNumFruta = (TextView) findViewById(R.id.textNumFruta);
        cargarPref();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPref();
            }
        });
        compraAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             abrirCantidadFruta();
            }
        });

    }

    void abrirCantidadFruta(){
        if(compraAuto.isChecked()) {
            numFruta.setVisibility(View.VISIBLE);
            textNumFruta.setVisibility(View.VISIBLE);
        }else{
            numFruta.setVisibility(View.INVISIBLE);
            textNumFruta.setVisibility(View.INVISIBLE);
        }
    }

    void cargarPref(){
        SharedPreferences userPref = getSharedPreferences("userPreferences",Context.MODE_PRIVATE);
        compraAuto.setChecked(userPref.getBoolean("frutaAuto", false));
        numFruta.setText(userPref.getString("numFruta","0"));
        abrirCantidadFruta();

    }
    void guardarPref(){
        SharedPreferences userPref = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        boolean valorSwitch = compraAuto.isChecked();
        String valorEditText = numFruta.getText().toString();
        editor.putBoolean("frutaAuto",valorSwitch);
        editor.putString("numFruta", valorEditText);
        editor.commit();
        this.finish();
    }


}
