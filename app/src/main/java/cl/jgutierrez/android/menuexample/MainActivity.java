package cl.jgutierrez.android.menuexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public void sayToast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    public void sayToast(int id) {
        Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
    }

    static final String[] FRUITS = new String[] { "Arándano", "Coco", "Frambuesa", "Frutilla",
            "Guava", "Kiwi", "Mango", "Manzana", "Naranja", "Palta", "Pera", "Plátano", "Tomate",
            "Uva" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> array = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, FRUITS);
        list.setAdapter(array);
        registerForContextMenu(list);
        loadPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        SubMenu sub = menu.findItem(R.id.action_menu_opt1).getSubMenu();
        CheckBox check = (CheckBox) findViewById(R.id.checkBox);
        sub.setGroupEnabled(R.id.menu_group1, check.isChecked());
        return super.onPrepareOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, appPreferences.class);
                startActivity(intent);
                return true;
            case R.id.action_menu_opt1:
                sayToast(R.string.opt1_sel);
                return true;
            case R.id.action_menu_opt1_sub1:
                sayToast(R.string.sub1_sel);
                return true;
            case R.id.action_menu_opt1_sub2:
                sayToast(R.string.sub2_sel);
                return true;
            case R.id.action_menu_opt1_sub3:
                sayToast(R.string.sub3_sel);
                return true;
            case R.id.menu_group1_opt1:
            case R.id.menu_group1_opt2:
                item.setChecked(true);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if(v.getId() == R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            ListView lst = (ListView)findViewById(R.id.listView);
            menu.setHeaderTitle(lst.getAdapter().getItem(info.position).toString());
            inflater.inflate(R.menu.list_ctx_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ctx_1:
                sayToast(R.string.buy_excl);
                return true;
            case R.id.ctx_2:
                sayToast(R.string.lets_buy);
                return true;
            case R.id.ctx_3:
                sayToast(R.string.sharing);

                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        sayToast(R.string.shared_excl);
                    }
                }, 2000);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void loadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        boolean nightModeActive = sharedPreferences.getBoolean("background_color", false);
        if(nightModeActive){
            LinearLayout mainLayout= (LinearLayout) findViewById(R.id.mainActivityLayout);
            mainLayout.setBackgroundColor(Color.parseColor("3c3f41"));
        }
        boolean frutaAuto = sharedPreferences.getBoolean("FrutaAuto",false);
        String usuario = sharedPreferences.getString("user","");

    }
}
