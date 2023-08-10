package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import algonquin.cst2335.finalproject.R;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> contestantNames;
    private int score;

    public ScoreAdapter(Context context, List<String> contestantNames, int score) {
        this.layoutInflater = LayoutInflater.from(context);
        this.contestantNames = contestantNames;
        this.score = score;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.score_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = contestantNames.get(position);
        holder.nameText.setText(name);
        holder.scoreText.setText(String.valueOf(score));
    }

    @Override
    public int getItemCount() {
        return contestantNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, scoreText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            scoreText = itemView.findViewById(R.id.score);
        }
    }
}
