package cl.jgutierrez.android.menuexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Switch compraAuto;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        compraAuto = (Switch) findViewById(R.id.switchCompraAuto);
        btnSave =  (Button) findViewById(R.id.save);
        cargarPref();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPref();
            }
        });

    }

    void cargarPref(){
        SharedPreferences userPref = getSharedPreferences("userPreferences",Context.MODE_PRIVATE);
        compraAuto.setChecked(userPref.getBoolean("Checked", false));
    }
    void guardarPref(){
        SharedPreferences userPref = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        boolean valor = compraAuto.isChecked();
        editor.putBoolean("Checked",valor);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
