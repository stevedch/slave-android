package sr.slave.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import sr.slave.R;
import sr.slave.model.Roles;
import sr.slave.model.User;

public class MainActivity extends AppCompatActivity {

    EditText editText, editText2;
    Realm realm;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        findViewById(R.id.register_data).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editText = (EditText) findViewById(R.id.editText);
                editText2 = (EditText) findViewById(R.id.editText_2);

                Snackbar.make(v, String.format("%s %s", editText.getText(), editText2.getText()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                realm.beginTransaction();

                User user = realm.createObject(User.class, 2);
                user.setCreatedAt(new Date());
                user.setUser(editText.getText().toString());
                user.setPassword(editText.getText().toString());
                user.setSalt("test");

                RealmList<Roles> roles = new RealmList<>();

                Roles rol = realm.createObject(Roles.class, 3);
                rol.setName("TEST");
                roles.add(rol);

                user.setRoles(roles);
                realm.commitTransaction();
                // Intent i = new Intent(MainActivity.this, DisplayDataActivity.class);
                // startActivity(i);
            }
        });

        findViewById(R.id.view_data).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DisplayDataActivity.class);
                startActivity(i);
            }
        });

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
