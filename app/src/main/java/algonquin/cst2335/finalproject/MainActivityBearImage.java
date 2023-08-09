package algonquin.cst2335.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class MainActivityBearImage extends AppCompatActivity {
    private EditText editTextSize;
    private ImageView imageViewBear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bear_image_generator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextSize = findViewById(R.id.editTextSize);
        imageViewBear = findViewById(R.id.imageViewBear);
        Button buttonGenerate = findViewById(R.id.buttonGenerate);

        // Set the EditText value to the saved size
        editTextSize.setText(String.valueOf(retrieveSizeFromPreferences()));

        buttonGenerate.setOnClickListener(v -> {
            fetchBearImage();
            // Save the size to SharedPreferences after fetching the image
            saveSizeToPreferences(Integer.parseInt(editTextSize.getText().toString()));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bear_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help_title)
                .setMessage(R.string.help_message)
                .setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void fetchBearImage() {
        int size = Integer.parseInt(editTextSize.getText().toString());
        String url = "https://placebear.com/" + size + "/" + size;

        ImageRequest request = new ImageRequest(url,
                response -> {
                    imageViewBear.setImageBitmap(response);
                    Toast.makeText(this, "Image loaded!", Toast.LENGTH_SHORT).show();
                }, 0, 0, null, null,
                error -> {
                    Snackbar.make(imageViewBear, "Error fetching image.", Snackbar.LENGTH_LONG).show();
                });

        Volley.newRequestQueue(this).add(request);
    }

    private void saveSizeToPreferences(int size) {
        SharedPreferences sharedPreferences = getSharedPreferences("BearPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastSize", size);
        editor.apply();
    }

    private int retrieveSizeFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("BearPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("lastSize", 100); // default size 100
    }
}