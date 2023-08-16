package comtugas.bintangrestu.myrecipes.ui.home;

import static comtugas.bintangrestu.myrecipes.server.Api.URL_READ_FOODS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import comtugas.bintangrestu.myrecipes.DetailResep;
import comtugas.bintangrestu.myrecipes.ResepList;
import comtugas.bintangrestu.myrecipes.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;



    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        RecyclerView recycleView_favorite =binding.recycleViewhome;
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView_favorite.setLayoutManager(llm);


        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, URL_READ_FOODS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("foods");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("idResep", json.getString("idResep"));
                        map.put("namaMakanan", json.getString("namaMakanan"));
                        map.put("asalMakanan", json.getString("asalMakanan"));
                        map.put("deskripsi", json.getString("deskripsi"));
                        map.put("imgFile", json.getString("imgFile"));

                        list_data.add(map);
                        ResepList adapter = new ResepList(getActivity().getApplicationContext(), list_data);
                        recycleView_favorite.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        requestQueue.add(stringRequest);

        TextView txtNamaLengkap = binding.txtNamaLengkap;
        if (getArguments() != null) {
            String getArgument = getArguments().getString("namaLengkap");
            txtNamaLengkap.setText("WELCOME, " + getArgument);
        }else{

        }


        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}