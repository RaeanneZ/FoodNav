package sg.edu.np.mad.mad24p03team2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    private Context context;
    private List<Item> items;
    private AdapterView.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public void setFilteredList(List<Item> filteredList) {
        this.items = filteredList;
        notifyDataSetChanged();
    }

    // I WILL FIX IT
    public FoodAdapter(Context context, List<Item> items, OnItemClickListener onItemClickListener){
        this.context = context;
        this.items = items;
        this.onItemClickListener = (AdapterView.OnItemClickListener) onItemClickListener;
    }

    // I WILL FIX IT
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_macro_view, parent, false);
        return new RecyclerView.ViewHolder(view);
    }

    // I WILL FIX IT
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView calNumView;
        TextView servingSizeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            calNumView = itemView.findViewById(R.id.calNum);
            servingSizeView = itemView.findViewById(R.id.servingSize);
        }

        public void bind(final Item item, final OnItemClickListener listener) {
            nameView.setText(item.getName());
            calNumView.setText(String.valueOf(item.getCalories())); //WHY IS THIS RED??
            servingSizeView.setText(String.valueOf(item.getServingSize()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
