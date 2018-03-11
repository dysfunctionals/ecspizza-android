package pizza.ecs.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    
    public void login(View view) {
        view = (View) view.getParent();
        String email = ((TextView) findViewById(R.id.email)).getText().toString();
        String password = ((TextView) view.findViewById(R.id.password)).getText().toString();
        
        // TODO: login
        System.out.println("Username: " + email + ", Password: " + password);
        
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("UUID", );
        //intent.putExtra("token", );
        startActivity(intent);
    }
}
