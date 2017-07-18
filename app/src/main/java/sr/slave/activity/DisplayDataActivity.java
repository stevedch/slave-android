package sr.slave.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import sr.slave.R;
import sr.slave.model.User;

public class DisplayDataActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data_main);
        Realm.init(this);

        textView = (TextView) findViewById(R.id.textView_22);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> users = realm.where(User.class).findAll();


        if (!users.isEmpty()) {
            for (User user : users) {
                textView.append(String.format("%s %s %s %s \n", user.getId(), user.getCreatedAt().toString(), user.getUser(), user.getSalt()));
            }
        }
    }
}
