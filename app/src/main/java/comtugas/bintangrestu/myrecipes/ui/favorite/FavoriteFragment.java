package comtugas.bintangrestu.myrecipes.ui.favorite;

import static comtugas.bintangrestu.myrecipes.server.Api.URL_SELECT_FAVORITE;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

import comtugas.bintangrestu.myrecipes.FavoriteList;
import comtugas.bintangrestu.myrecipes.ResepList;
import comtugas.bintangrestu.myrecipes.databinding.FragmentFavoriteBinding;



public class FavoriteFragment extends Fragment {
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    String idPengguna;

    ArrayList<HashMap<String, String>> list_data;

    private FragmentFavoriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteViewModel slideshowViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recycleView_favorite =binding.recycleViewfavorite;
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView_favorite.setLayoutManager(llm);

        if (getArguments() != null) {
            idPengguna = getArguments().getString("idPengguna");


        }else{

        }

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.POST, URL_SELECT_FAVORITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("favorites");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("idFavorite", json.getString("idFavorite"));
                        map.put("idPengguna", json.getString("idPengguna"));
                        map.put("idResep", json.getString("idResep"));
                        map.put("namaMakanan", json.getString("namaMakanan"));
                        map.put("imgFile", json.getString("imgFile"));

                        list_data.add(map);
                        FavoriteList adapter = new FavoriteList(getActivity().getApplicationContext(), list_data);
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
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("idPengguna",idPengguna);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);

        //final TextView textView = binding.textSlideshow;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}