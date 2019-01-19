package gkjcompany.cityproblems_v11;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class LogIn extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener {

    Button bt_login;
    private EditText et_email, et_pass;
    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView register_text;
    TextView fb_Login;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private ProgressDialog pd;
    String myResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        bt_login = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.etEmail);
        et_pass = (EditText) findViewById(R.id.etPassword);


        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rem_userpass = (CheckBox) findViewById(R.id.checkBox_logIN);

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);

        et_email.setText(sharedPreferences.getString(KEY_EMAIL, ""));
        et_pass.setText(sharedPreferences.getString(KEY_PASS, ""));
        register_text = (TextView) findViewById(R.id.t_Register);
        fb_Login = (TextView)findViewById(R.id.facebook_login);


        et_email.addTextChangedListener((TextWatcher) this);
        et_pass.addTextChangedListener((TextWatcher) this);
        rem_userpass.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);


        bt_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (et_email.getText().toString().length() <= 0) {
                    et_email.setError("Email can not be empty !");
                } else if (et_pass.getText().toString().length() <= 0) {
                    et_pass.setError("Password can not be empty !");
                } else {
                    et_pass.setError(null);
                    et_email.setError(null);
                    pd = new ProgressDialog(LogIn.this);
                    pd.setMessage("Loading...");
                    pd.show();
                    new SendRequest().execute();
                }
            }
        });


        register_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
            }
        });

        fb_Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(LogIn.this, "Facebook Log-In Coming Soon!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


    //Save preference start
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }

    private void managePrefs() {
        if (rem_userpass.isChecked()) {
            editor.putString(KEY_EMAIL, et_email.getText().toString().trim());
            editor.putString(KEY_PASS, et_pass.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_EMAIL);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }
    //Save preference end

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

        String email = et_email.getText().toString();
        String pass = et_pass.getText().toString();

        String SHA1 = SHA1(pass);


        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL("https://icsdmobileprojectapi.000webhostapp.com/login.php");

                JSONObject postDataParams = new JSONObject();



                postDataParams.put("type", "user");
                postDataParams.put("email", email);
                postDataParams.put("password", SHA1);
                Log.e("pass",SHA1);

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
            JSONObject jsonResult = null;


            try {
                jsonResult = new JSONObject(result);
                Log.e("result", "" + jsonResult.getBoolean("success"));
                if (jsonResult.getBoolean("success")) {

                    Intent intent = new Intent(LogIn.this, ReportsList.class);
                    intent.putExtra("user_email",email);

                    startActivity(intent);
                    pd.dismiss();

                } else {
                    Toast.makeText(getApplicationContext(), jsonResult.getString("message"),
                            Toast.LENGTH_LONG).show();

                    pd.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


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
