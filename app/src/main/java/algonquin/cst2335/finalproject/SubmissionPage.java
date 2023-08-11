package algonquin.cst2335.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.ActivityMainBearImageGeneratorBinding;
import algonquin.cst2335.finalproject.databinding.SubmissionPageBinding;

public class SubmissionPage extends AppCompatActivity {

    int questionNumber;
    int score;
    int totalScore;
    RecyclerView recyclerView;
    ScoreAdapter scoreAdapter;
    boolean hasSubmitted = false;
    ArrayList<String> contestant;
    int scoreToPass ;
    QuizContestantDAO qDAO;
    QuizContestantViewModel contestantModel;
    ArrayList<QuizContestant> quizContestant;
    QuizContestant contestantObj;
    QuizContestantDAO contestantDAO;
    SubmissionPageBinding binding;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubmissionPageBinding binding = SubmissionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contestantModel = new ViewModelProvider(this).get(QuizContestantViewModel.class);
        quizContestant = contestantModel.quizContestant.getValue();

        contestant = new ArrayList<>();
        recyclerView = binding.recyclerScoreView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreAdapter = new ScoreAdapter(this, contestant, scoreToPass);
        recyclerView.setAdapter(scoreAdapter);

        QuestionDatabase db = Room.databaseBuilder(getApplicationContext(), QuestionDatabase.class, "database-name").build();
        qDAO = db.qcDAO();

        binding.scoreButton.setOnClickListener(view -> {

            String name = binding.nameText.getText().toString();
            String contestantScore = String.valueOf(scoreToPass);

            if (!name.isEmpty()) {
                if (!isContestantAlreadyAdded(name)) {
                    contestant.add(name);
                    hasSubmitted = true;

                    // Update the RecyclerView's data
                } else {
                    Toast.makeText(this, "Contestant is already added.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a name before submitting.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.Help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instructions");
            builder.setMessage(R.string.helpmessage);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (item.getItemId() == R.id.Home) {
            Intent HomeIntent = new Intent(SubmissionPage.this, MainActivity.class);
            startActivity(HomeIntent );
        } else if (item.getItemId() == R.id.Delete) {
            Snackbar.make(binding.recyclerScoreView,"Item Deleted",Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean isContestantAlreadyAdded(String name) {
        for (String contestantName : contestant) {
            if (contestantName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
