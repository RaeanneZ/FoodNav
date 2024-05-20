package sg.edu.np.mad.mad24p03team2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolder extends RecyclerView.ViewHolder {

    TextView nameView, calNumView, servingSizeView;
    public FoodViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.name);
        calNumView = itemView.findViewById(R.id.calNum);
        servingSizeView = itemView.findViewById(R.id.servingSize);

    }
}
