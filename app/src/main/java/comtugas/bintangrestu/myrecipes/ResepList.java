package comtugas.bintangrestu.myrecipes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ResepList extends RecyclerView.Adapter<ResepList.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public ResepList(Context context, ArrayList<HashMap<String, String>> list_data) {
        this.context = context;
        this.list_data = list_data;
    }

    @NonNull
    @Override
    public ResepList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipes_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResepList.ViewHolder holder, int position) {
        Picasso.with(context)
                .load("https://myrecpiesbind1608.000webhostapp.com/img/" + list_data.get(position).get("imgFile"))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgFoods);
        holder.tvName.setText(list_data.get(position).get("namaMakanan"));
        holder.namaMakanan = list_data.get(position).get("namaMakanan");
        holder.idResep = list_data.get(position).get("idResep");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), holder.namaMakanan, Toast.LENGTH_SHORT).show();
                Intent intDetail = new Intent(context, DetailResep.class);
                intDetail.putExtra("namaMakanan", String.valueOf(holder.namaMakanan));
                context.startActivity(intDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView imgFoods;

        String namaMakanan, idResep;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imgFoods = (ImageView) itemView.findViewById(R.id.imgFoods);

        }
    }
}
