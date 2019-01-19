package gkjcompany.cityproblems_v11;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Register extends AppCompatActivity {

    Button register_submit;
    private EditText et_name, et_surname, et_email, et_pass;
    TextView t_Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText) findViewById(R.id.etName);
        et_surname = (EditText) findViewById(R.id.etSurname);
        et_email = (EditText) findViewById(R.id.etEmail);
        et_pass = (EditText) findViewById(R.id.etPassword);

        register_submit = (Button) findViewById(R.id.btn_Register);
        t_Login = (TextView) findViewById(R.id.t_Login);
        t_Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LogIn.class);
                startActivity(intent);
            }
        });

        register_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (et_email.getText().toString().length() <= 0) {
                    et_email.setError("Email can not be empty !");
                } else if (et_pass.getText().toString().length() <= 0) {
                    et_pass.setError("Password can not be empty !");
                } else if (et_name.getText().toString().length() <= 0) {
                    et_name.setError("Name can not be empty !");
                } else if (et_surname.getText().toString().length() <= 0) {
                    et_surname.setError("Surname can not be empty !");
                } else {
                    et_pass.setError(null);
                    et_email.setError(null);
                    et_name.setError(null);
                    et_surname.setError(null);
                    new Register.SendRequest().execute();
                }
            }
        });
    }


    //HASH Start

    public static String SHA1(final String text) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("UTF-8"),
                    0, text.length());
            byte[] sha1hash = md.digest();

            return toHex(sha1hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHex(final byte[] buf) {
        if (buf == null) return "";

        int l = buf.length;
        StringBuffer result = new StringBuffer(2 * l);

        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(final StringBuffer sb, final byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f))
                .append(HEX.charAt(b & 0x0f));
    }

    //HASH End


    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        String name = et_name.getText().toString();
        String surname = et_surname.getText().toString();
        String email = et_email.getText().toString();
        String pass = et_pass.getText().toString();

        String SHA1 = SHA1(pass);


        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://icsdmobileprojectapi.000webhostapp.com/register.php");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("type", "user");
                postDataParams.put("name", name);
                postDataParams.put("surname", surname);
                postDataParams.put("email", email);
                postDataParams.put("password", SHA1);

                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                Log.e("respone code ", String.valueOf(responseCode));

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("result", result);
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Register.this, LogIn.class);
            startActivity(intent);
        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
