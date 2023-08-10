package algonquin.cst2335.finalproject;

import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_HEIGHT;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_ID;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_IMAGE;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_WIDTH;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.TABLE_NAME;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivityBearImage extends AppCompatActivity {
    private EditText editTextSize;
    private ImageView imageViewBear;
    private MainActivityBearImage activity;

    private RecyclerView savedImagesRecyclerView;
    private ImageListAdapter adapter;


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
            // Fetch the bear image
            fetchBearImage();

            // Save the size to SharedPreferences after fetching the image
            saveSizeToPreferences(Integer.parseInt(editTextSize.getText().toString()));
        });





        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            // Get the current displayed image and its dimensions
            Drawable drawable = imageViewBear.getDrawable();

            if (drawable instanceof BitmapDrawable) {
                Bitmap currentImage = ((BitmapDrawable) drawable).getBitmap();
                int size = Integer.parseInt(editTextSize.getText().toString());

                // Save the image to the database
                saveImageToDatabase(currentImage, size, size);

                // Optionally, show a toast or a message to inform the user that the image has been saved
                Toast.makeText(this, "Image saved!", Toast.LENGTH_SHORT).show();

                // Update the RecyclerView with the new list of images
                List<ImageEntry> updatedImageList = fetchImagesFromDatabase();
            } else {
                Toast.makeText(this, "Image not loaded yet!", Toast.LENGTH_SHORT).show();
            }
        });

        Button viewSavedImagesButton = findViewById(R.id.viewSavedImagesButton);
        viewSavedImagesButton.setOnClickListener(v -> {
            Log.d("SavedImagesActivity", "Starting SavedImagesActivity");
            Intent intent = new Intent(MainActivityBearImage.this, SavedImagesActivity.class);
            startActivity(intent);
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
                    Log.d("MainActivityBearImage", "Image loaded successfully");
                    imageViewBear.setImageBitmap(response);
                    Toast.makeText(this, "Image loaded!", Toast.LENGTH_SHORT).show();
                }, 0, 0, null, null,
                error -> {
                    Log.d("MainActivityBearImage", "Error fetching image: " + error.getMessage());
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

    private void saveImageToDatabase(Bitmap image, int width, int height) {
        ImageDatabaseHelper dbHelper = new ImageDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, imageBytes);
        values.put(COLUMN_WIDTH, width);
        values.put(COLUMN_HEIGHT, height);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteImage(int id) {
        ImageDatabaseHelper dbHelper = new ImageDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    private List<ImageEntry> fetchImagesFromDatabase() {
        List<ImageEntry> images = new ArrayList<>();
        ImageDatabaseHelper dbHelper = new ImageDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ImageEntry entry = new ImageEntry();
            entry.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            entry.setWidth(cursor.getInt(cursor.getColumnIndex(COLUMN_WIDTH)));
            entry.setHeight(cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT)));
            entry.setImageData(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));
            images.add(entry);
        }
        cursor.close();
        db.close();
        return images;
    }






}