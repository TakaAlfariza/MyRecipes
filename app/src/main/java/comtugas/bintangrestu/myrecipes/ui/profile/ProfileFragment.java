package comtugas.bintangrestu.myrecipes.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comtugas.bintangrestu.myrecipes.Login;
import comtugas.bintangrestu.myrecipes.MainActivity;
import comtugas.bintangrestu.myrecipes.Register;
import comtugas.bintangrestu.myrecipes.dataUser;
import comtugas.bintangrestu.myrecipes.databinding.FragmentProfileBinding;
import comtugas.bintangrestu.myrecipes.server.Api;
import comtugas.bintangrestu.myrecipes.server.RequestHandler;

public class ProfileFragment extends Fragment {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    List<dataUser> dataUserList2;
    private FragmentProfileBinding binding;
    String idPengguna, idProfile, namaLengkap;

    String Fullname, Email, Phone, Username, Password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfilesViewModel galleryViewModel =
                new ViewModelProvider(this).get(ProfilesViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        EditText edtUsername = binding.edtUsername;
        EditText edtEmail = binding.edtEmail;
        EditText edtFullname = binding.edtFullname;
        EditText edtPassword = binding.edtPassword;
        EditText edtPhone = binding.edtPhone;
        Button btnUpdate = binding.btnUpdate;

        dataUserList2 =  new ArrayList<>();

        if (getArguments() != null) {
            idPengguna = getArguments().getString("idPengguna");
            namaLengkap = getArguments().getString("namaLengkap");

        }else{

        }

        HashMap<String, String> params = new HashMap<>();

        params.put("idPengguna", idPengguna);


        ProfileFragment.PerformNetworkRequest request = new ProfileFragment.PerformNetworkRequest(Api.URL_PROFILE_USERS, params, CODE_POST_REQUEST);
        request.execute();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();

            }
        });

        //final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Menampilkan data profil.
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

                        Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        refreshdataUserList(object.getJSONArray("user"));


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

    //Update data
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
                        Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getBaseContext(),
                                MainActivity.class);
                        intent.putExtra("idPengguna", String.valueOf(idPengguna));
                        intent.putExtra("namaLengkap", Fullname);
                        getActivity().startActivity(intent);


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

    private void refreshdataUserList(JSONArray users) throws JSONException {
        dataUserList2.clear();

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
            binding.edtFullname.setText(obj.getString("namaLengkap"));
            binding.edtUsername.setText(obj.getString("namaPengguna"));
            binding.edtEmail.setText(obj.getString("email"));
            binding.edtPassword.setText(obj.getString("kataSandi"));
            binding.edtPhone.setText(obj.getString("nohp"));

            //ctxtRegister.setText(String.valueOf(obj.getInt("idPengguna")));
        }

    }



    private void updateUser() {
        Fullname = binding.edtFullname.getText().toString().trim();
        Email = binding.edtEmail.getText().toString().trim();
        Phone = binding.edtPhone.getText().toString().trim();
        Username = binding.edtUsername.getText().toString().trim();
        Password = binding.edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Fullname)) {
            binding.edtFullname.setError("Please enter Fullname");
            binding.edtFullname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            binding.edtEmail.setError("Please enter Email");
            binding.edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Username)) {
            binding.edtUsername.setError("Please enter Username");
            binding.edtUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Phone)) {
            binding.edtPhone.setError("Please enter Phone");
            binding.edtPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            binding.edtPassword.setError("Please enter Password");
            binding.edtPassword.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("namaLengkap", Fullname);
        params.put("email", Email);
        params.put("nohp", Phone);
        params.put("namaPengguna", Username);
        params.put("kataSandi", Password);
        params.put("idPengguna", idPengguna);

        ProfileFragment.PerformNetworkRequest2 request = new ProfileFragment.PerformNetworkRequest2(Api.URL_UPDATE_USER, params, CODE_POST_REQUEST);
        request.execute();
    }
}