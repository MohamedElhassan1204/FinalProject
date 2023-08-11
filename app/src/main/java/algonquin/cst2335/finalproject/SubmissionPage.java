package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

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

    private boolean isContestantAlreadyAdded(String name) {
        for (String contestantName : contestant) {
            if (contestantName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
