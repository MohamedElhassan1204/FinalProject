package algonquin.cst2335.finalproject;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.QuizSelectionPageBinding;

public class QuizSelectionPage extends AppCompatActivity {
    QuizSelectionPageBinding binding;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> items;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_selection_page);
        binding = QuizSelectionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        items = new ArrayList<>();
        items.add("Math");
        items.add("Geography");
        items.add("Science & Nature");
        items.add("Entertainment:Music");
        items.add("Entertainment:Television");
        items.add("Entertainment:Games");
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, items); // Pass 0 as the initial score
        recyclerView.setAdapter(adapter);

        binding.startTest.setOnClickListener(click -> {
            if (!binding.questionNumber.getText().toString().isEmpty()) {
                int questionNumber = Integer.parseInt(binding.questionNumber.getText().toString());
                String url = null;
                if (questionNumber > 50 || questionNumber == 0) {
                    Toast.makeText(this, "Cannot select over 50 questions.", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("questionNumber", questionNumber);
                    editor.apply();

                    Intent nextPage = new Intent(QuizSelectionPage.this, QuestionPage.class);
                    nextPage.putExtra("questionNumber", questionNumber);

                    startActivity(nextPage);
                    Log.w("QuizSelectionPage", "Starting QuestionPage");
                }
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
            Intent HomeIntent = new Intent(QuizSelectionPage.this, MainActivity.class);
            startActivity(HomeIntent );
        } else if (item.getItemId() == R.id.Delete) {
            Snackbar.make(binding.getRoot(),"Item Deleted",Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }
}

