package comtugas.bintangrestu.myrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import comtugas.bintangrestu.myrecipes.server.Api;
import comtugas.bintangrestu.myrecipes.server.RequestHandler;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    EditText edtUsername,edtFullname,edtEmail,edtPhone,edtPassword;
    Button btnRegister;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtFullname = (EditText) findViewById(R.id.edtFullname);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        if(v == R.id.btnRegister){
            createUser();
        }
    }

    //inner class to perform network request extending an AsyncTask
    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            try {

                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent intLogin = new Intent(getBaseContext(), Login.class);
                    startActivity(intLogin);
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    private void createUser() {
        String Fullname = edtFullname .getText().toString().trim();
        String Email = edtEmail.getText().toString().trim();
        String Phone = edtPhone.getText().toString().trim();
        String Username = edtUsername.getText().toString().trim();
        String Password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Fullname)) {
            edtFullname.setError("Please enter Fullname");
            edtFullname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            edtEmail.setError("Please enter Email");
            edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Username)) {
            edtUsername.setError("Please enter Username");
            edtUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Phone)) {
            edtPhone.setError("Please enter Phone");
            edtPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            edtPassword.setError("Please enter Password");
            edtPassword.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("namaLengkap", Fullname);
        params.put("email", Email);
        params.put("nohp", Phone);
        params.put("namaPengguna", Username);
        params.put("kataSandi", Password);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_USER, params, CODE_POST_REQUEST);
        request.execute();
    }

}