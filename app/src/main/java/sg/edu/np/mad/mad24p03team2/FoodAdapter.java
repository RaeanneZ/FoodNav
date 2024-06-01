package sg.edu.np.mad.mad24p03team2;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.FoodItemClass;



public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<FoodItemClass> items;
    private final RecyclerViewInterface recyclerViewInterface;

    public void setFilteredList(List<FoodItemClass> filteredList) {
        this.items = filteredList;
        notifyDataSetChanged(); // Alert UI that dataset has been changed
    }

    public FoodAdapter(List<FoodItemClass> itemList, RecyclerViewInterface recyclerViewInterface) {
        this.items = itemList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    ;

    // I WILL FIX IT <-- YOU NEVER DID
    public FoodAdapter(Context context, List<FoodItemClass> items, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    // I WILL FIX IT
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.custom_macro_view, parent, false);
        return new FoodAdapter.FoodViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        // Assigning values to the views created based on position of the recycler view
        FoodItemClass item = items.get(position);
        holder.nameView.setText(item.getName());
        holder.calNumView.setText(String.valueOf(item.getCalories()));
        holder.servingSizeView.setText(String.valueOf(item.getServing_size_g()));
    }

    @Override
    public int getItemCount() {
        // Total number of items to be displayed
        return items.size();
    }


    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        // Grabbing the views from layout file
        TextView nameView;
        TextView calNumView;
        TextView servingSizeView;

        public FoodViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            calNumView = itemView.findViewById(R.id.calNum);
            servingSizeView = itemView.findViewById(R.id.servingSize);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

