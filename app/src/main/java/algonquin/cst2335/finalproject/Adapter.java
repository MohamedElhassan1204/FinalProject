package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<String> data;

    public Adapter(Context context, List<String> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.category_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = data.get(position);
        switch (title) {
            case "Math":
                holder.categoryImage.setImageResource(R.drawable.science_mathematics);
                break;
            case "Geography":
                holder.categoryImage.setImageResource(R.drawable.geography);
                break;
            case "Science & Nature":
                holder.categoryImage.setImageResource(R.drawable.science_and_nature);
                break;
            case "Entertainment:Music":
                holder.categoryImage.setImageResource(R.drawable.entertainment_music);
                break;
            case "Entertainment:Television":
                holder.categoryImage.setImageResource(R.drawable.entertainment_television);
                break;
            case "Entertainment:Games":
                holder.categoryImage.setImageResource(R.drawable.entertainment_video_games);
                break; // Change to your default image resource
        }
        holder.categoryLabel.setText(title); // Set the score in the score TextView
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryLabel;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryLabel = itemView.findViewById(R.id.categoryLabel);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
