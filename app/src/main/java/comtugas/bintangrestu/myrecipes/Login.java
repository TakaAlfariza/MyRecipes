package comtugas.bintangrestu.myrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comtugas.bintangrestu.myrecipes.server.Api;
import comtugas.bintangrestu.myrecipes.server.RequestHandler;
import comtugas.bintangrestu.myrecipes.ui.home.HomeFragment;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView ctxtRegister;
    EditText edtUsername,edtPassword;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    Button btnLogin;
    List<dataUser> dataUserList;

    Integer idPengguna;
    String namaLengkap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ctxtRegister = (TextView) findViewById(R.id.ctxtRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        ctxtRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        dataUserList =  new ArrayList<>();

    }

    //Menampilkan data yang di-login dari server.
    private void refreshdataUserList(JSONArray users) throws JSONException {
        dataUserList.clear();

        for (int i = 0; i < users.length(); i++) {
            JSONObject obj = users.getJSONObject(i);
                idPengguna = obj.getInt("idPengguna");
                namaLengkap = obj.getString("namaLengkap");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("idPengguna", String.valueOf(idPengguna));
                intent.putExtra("namaLengkap", namaLengkap);

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("idPengguna", String.valueOf(idPengguna));
                myEdit.putString("namaLengkap", namaLengkap);
                myEdit.commit();
                // start the Intent
                startActivity(intent);






            /*
            heroList.add(new Hero(
                    obj.getInt("id"),
                    obj.getString("name"),
                    obj.getString("realname"),
                    obj.getInt("rating"),
                    obj.getString("teamaffiliation")
            ));


             */
        }


    }

    @Override
    public void onClick(View view) {
        int click = view.getId();
        if(click == R.id.ctxtRegister){
            Intent intRegister = new Intent(this, Register.class);
            startActivity(intRegister);
        }else if(click == R.id.btnLogin){
            loginUser();
            //Intent intLogin= new Intent(this, MainActivity.class);
            //startActivity(intLogin);
        }

    }

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
                    if(object.length() == 0){
                        Toast.makeText(getApplicationContext(), "Username atau Password", Toast.LENGTH_SHORT).show();
                    }else{
                        refreshdataUserList(object.getJSONArray("user"));
                    }


                    //Intent intLogin = new Intent(getBaseContext(), Login.class);
                    //startActivity(intLogin);
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

    private void loginUser() {

        String Username = edtUsername.getText().toString().trim();
        String Password = edtPassword.getText().toString().trim();



        if (TextUtils.isEmpty(Username)) {
            edtUsername.setError("Please enter Username");
            edtUsername.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(Password)) {
            edtPassword.setError("Please enter Password");
            edtPassword.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();

        params.put("namaPengguna", Username);
        params.put("kataSandi", Password);

        Login.PerformNetworkRequest request = new Login.PerformNetworkRequest(Api.URL_LOGIN_USER, params, CODE_POST_REQUEST);
        request.execute();
    }
}