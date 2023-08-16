package comtugas.bintangrestu.myrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoriteList extends RecyclerView.Adapter<FavoriteList.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public FavoriteList(Context context, ArrayList<HashMap<String, String>> list_data) {
        this.context = context;
        this.list_data = list_data;
    }

    @NonNull
    @Override
    public FavoriteList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorites_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteList.ViewHolder holder, int position) {
        Picasso.with(context)
                .load("https://myrecpiesbind1608.000webhostapp.com/img/" + list_data.get(position).get("imgFile"))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgFoods);
        holder.tvName.setText(list_data.get(position).get("namaMakanan"));
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView imgFoods, imgFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imgFoods = (ImageView) itemView.findViewById(R.id.imgFoods);
            imgFavorite = (ImageView) itemView.findViewById(R.id.imgFavorite);

        }
    }
}
