package mue.com.webapitutorial;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public final static String strikeIronUserName = "muenzuve@gmail.com";
    public final static String strikeIronPassword = "DDvEEo";
    public final static String apiURL = "http://ws.strikeiron.com/StrikeIron/EMV6Hygiene/VerifyEmail?";

    private class emailVerificationResult {
        public String statusNbr;
        public String hygieneResult;
    }

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
            emailVerificationResult result = null;

            // HTTP Get
            try {

                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                in = new BufferedInputStream(urlConnection.getInputStream());

            } catch (Exception e) {

                System.out.println(e.getMessage());

                return e.getMessage();

            }

            // Parse XML
            XmlPullParserFactory pullParserFactory;

            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                result = parseXML(parser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Simple logic to determine if the email is dangerous, invalid, or valid
            if (result != null ) {
                if( result.hygieneResult.equals("Spam Trap")) {
                    resultToDisplay = "Dangerous email, please correct";
                }
                else if( Integer.parseInt(result.statusNbr) >= 300) {
                    resultToDisplay = "Invalid email, please re-enter";
                }
                else {
                    resultToDisplay = "Thank you for your submission";
                }
            }
            else {
                resultToDisplay = "Exception Occured";
            }

            return resultToDisplay;

        }
        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

            intent.putExtra(EXTRA_MESSAGE, result);

            startActivity(intent);

        }

    } // end CallAP



        // This is the method that is called when the submit button is clicked




    }
}
