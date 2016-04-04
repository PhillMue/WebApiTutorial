package mue.com.webapitutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // This is the method that is called when the submit button is clicked

    public void verifyEmail(View view) {

        EditText emailEditText = (EditText) findViewById(R.id.email_address);
        String email = emailEditText.getText().toString();

        // TODO, create the task to call the REST API

    }
}
