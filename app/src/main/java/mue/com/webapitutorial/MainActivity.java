package mue.com.webapitutorial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

        if( email != null && !email.isEmpty()) {

            String urlString = apiURL + "LicenseInfo.RegisteredUser.UserID=" + strikeIronUserName + "&LicenseInfo.RegisteredUser.Password=" + strikeIronPassword + "&VerifyEmail.Email=" + email + "&VerifyEmail.Timeout=30";

            new CallAPI().execute(urlString);

        // TODO, create the task to call the REST API

    }
    private class CallAPI extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... params) {

            String urlString = params[0]; // URL to call

            String resultToDisplay = "";

            InputStream in = null;

            // HTTP Get
            try {

                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                in = new BufferedInputStream(urlConnection.getInputStream());

            } catch (Exception e) {

                System.out.println(e.getMessage());

                return e.getMessage();

            }

            return resultToDisplay;
        }

        public final static String strikeIronUserName = "muenzuve@gmail.com";
        public final static String strikeIronPassword = "DDvEEo";
        public final static String apiURL = "http://ws.strikeiron.com/StrikeIron/EMV6Hygiene/VerifyEmail?";
    }
}
