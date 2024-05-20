package sg.edu.np.mad.mad24p03team2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    Context context;
    List<Item> items;

    public void setFilteredList(List<Item> filteredList) {
        this.items = filteredList;
        notifyDataSetChanged();
    }

    public FoodAdapter(Context context, List<Item> items){
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.calNumView.setText(items.get(position).getCalNum());
        holder.servingSizeView.setText(items.get(position).getServingSize());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
