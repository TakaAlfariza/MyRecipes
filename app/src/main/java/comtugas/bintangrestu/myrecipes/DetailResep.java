package comtugas.bintangrestu.myrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class DetailResep extends AppCompatActivity implements View.OnClickListener {
String namaMakanan;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    List<dataResep> dataResepList;
    List<dataFavorite> dataOneFavoriteList;
    LinearLayout linLayImg;
    TextView txtTitle, txtContent;
    String picFood, idResep, idPengguna, namaLengkap, idFavorite;

    ImageView imgFavorite;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_resep);

        Intent intent = getIntent();
        namaMakanan = intent.getStringExtra("namaMakanan");
        linLayImg = (LinearLayout) findViewById(R.id.linLayImg);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        imgFavorite = (ImageView) findViewById(R.id.imgFavorite);

        imgFavorite.setOnClickListener(this);


        //Belum bisa hapus favorite, makanya dikasih View.INVISIBLE.

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        idPengguna = sh.getString("idPengguna", "");
        namaLengkap = sh.getString("namaPengguna", "");

        dataResepList =  new ArrayList<>();
        dataOneFavoriteList =  new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("namaMakanan", namaMakanan);

        //DetailResep.PerformNetworkRequest request = new DetailResep.PerformNetworkRequest(Api.URL_SELECT_FOODS, params, CODE_POST_REQUEST);
        //request.execute();
        DetailResep.PerformNetworkRequest request = new DetailResep.PerformNetworkRequest(Api.URL_SELECT_FOODS, params, CODE_POST_REQUEST);
        request.execute();

    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        if(v == R.id.imgFavorite){
            HashMap<String, String> params = new HashMap<>();
            params.put("idPengguna", idPengguna);
            params.put("idResep", idResep);


            DetailResep.PerformNetworkRequest2 request = new DetailResep.PerformNetworkRequest2(Api.URL_CREATE_FAVORITE, params, CODE_POST_REQUEST);
            request.execute();
        }
    }

    //Menampilkan data resep makanan.
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


                    refreshdataResepList(object.getJSONArray("food"));


                    //Intent intLogin = new Intent(getBaseContext(), Login.class);
                    //startActivity(intLogin);
                    // refreshHeroList(object.getJSONArray("heroes"));
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

    //Mengecek Resep Favorit

    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest3 extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest3(String url, HashMap<String, String> params, int requestCode) {
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
                    refreshdataOneFavoList(object.getJSONArray("favorites"));




                    //Intent intLogin = new Intent(getBaseContext(), Login.class);
                    //startActivity(intLogin);
                    // refreshHeroList(object.getJSONArray("heroes"));
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

    //Menambah resep makanan favorit
    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest4 extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest4(String url, HashMap<String, String> params, int requestCode) {
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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("idPengguna", idPengguna);
                    intent.putExtra("namaLengkap", namaLengkap);
                    startActivity(intent);
                    finish();
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

    //Hapus Favorite
    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest2 extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest2(String url, HashMap<String, String> params, int requestCode) {
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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("idPengguna", idPengguna);
                    intent.putExtra("namaLengkap", namaLengkap);
                    startActivity(intent);
                    finish();
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

    private void refreshdataResepList(JSONArray users) throws JSONException {
        dataResepList.clear();

        for (int i = 0; i < users.length(); i++) {
            JSONObject obj = users.getJSONObject(i);
            //idPengguna = obj.getInt("idPengguna");
            /*
            namaLengkap = obj.getString("namaLengkap");
            namaPengguna = obj.getString("namaPengguna");
            email = obj.getString("email");
            kataSandi = obj.getString("kataSandi");
            nohp = obj.getString("nohp");
            */

            idResep = String.valueOf(obj.getInt("idResep"));
            txtTitle.setText(obj.getString("namaMakanan"));
            txtContent.setText(obj.getString("deskripsi"));
            picFood = obj.getString("imgFile").replace(".png", "");
            int resID = getResources().getIdentifier(picFood , "drawable", getPackageName());
            ImageView ii= new ImageView(this);
            ii.setBackgroundResource(resID);
            linLayImg.addView(ii);
            HashMap<String, String> params = new HashMap<>();

            params.put("idPengguna", idPengguna);
            params.put("idResep", idResep);
            DetailResep.PerformNetworkRequest3 request = new DetailResep.PerformNetworkRequest3(Api.URL_SELECT_ONE_FAVORITE, params, CODE_POST_REQUEST);
            request.execute();

        }

    }

    //Menampilkan satu favorite dari pengguna.
    private void refreshdataOneFavoList(JSONArray users) throws JSONException {
        dataOneFavoriteList.clear();

        for (int i = 0; i < users.length(); i++) {
            JSONObject obj = users.getJSONObject(i);
            //idPengguna = obj.getInt("idPengguna");
            /*
            namaLengkap = obj.getString("namaLengkap");
            namaPengguna = obj.getString("namaPengguna");
            email = obj.getString("email");
            kataSandi = obj.getString("kataSandi");
            nohp = obj.getString("nohp");
            */

            idFavorite = String.valueOf(obj.getInt("idResep"));


        }

    }
}