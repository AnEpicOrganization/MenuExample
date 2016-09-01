package cl.jgutierrez.android.menuexample;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

public class appPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FragmentManager fragmentManager = getFragmentManager(); //fragmentManager: interactua con objetos fragment de una activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // fragmentTransacion: API para realizar un conjunto de operaciones fragment

        SettingsFragment settingsFragment =  new SettingsFragment();//permite guardar automaticamente SharedPreferences cuando el usuario interactua
        fragmentTransaction.add(android.R.id.content, settingsFragment, "SETTINGS_FRAGMENT");//content: obtiene el elemento root de una vista, "SETTINGS_FRAGMENT" nombre del xml donde se guardan los datos
        fragmentTransaction.commit();//guarda la transaccion

    }

    public static class SettingsFragment extends PreferenceFragment{
            @Override
            public void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.app_preferences);

            }
    }
}
