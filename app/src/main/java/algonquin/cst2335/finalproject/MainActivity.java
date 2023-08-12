package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toolbar;

import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button quizButton = binding.quizButton;
        Button bearImageButton = binding.bearImageButton;

        quizButton.setOnClickListener(clk -> {
            Intent nextPage = new Intent( MainActivity.this, QuizSelectionPage.class);
            startActivity(nextPage);
        });
        // Set the onClickListener for the Bear Image button
        bearImageButton.setOnClickListener(clk -> {
            Intent bearImageIntent = new Intent(MainActivity.this, MainActivityBearImage.class);
            startActivity(bearImageIntent);
        });
    }

}