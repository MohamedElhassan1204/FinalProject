package algonquin.cst2335.finalproject;

import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_HEIGHT;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_ID;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_IMAGE;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.COLUMN_WIDTH;
import static algonquin.cst2335.finalproject.ImageDatabaseHelper.TABLE_NAME;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedImagesActivity extends AppCompatActivity implements ImageListAdapter.ImageDeleteListener{

    private RecyclerView savedImagesRecyclerView;
    private ImageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        Toolbar toolbar = findViewById(R.id.savedImagesToolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        savedImagesRecyclerView = findViewById(R.id.savedImagesRecyclerView);
        savedImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch the saved images from the database
        List<ImageEntry> savedImages = fetchImagesFromDatabase();

        // Initialize the adapter with the fetched images
        adapter = new ImageListAdapter(this, savedImages, imageId -> deleteImage((int) imageId));


        // Set the adapter to the RecyclerView
        savedImagesRecyclerView.setAdapter(adapter);

        if (savedImages.isEmpty()) {
            Toast.makeText(this, "No saved images found!", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();  // Close this activity and return to the previous one (if it's still in the stack)
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void deleteImage(int id) {
        ImageDatabaseHelper dbHelper = new ImageDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();

        // Fetch the updated list of images and update the RecyclerView
        List<ImageEntry> updatedImageList = fetchImagesFromDatabase();
        adapter.updateData(updatedImageList);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            findViewById(R.id.savedImagesRecyclerView).setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    public void onDeleteImage(long imageId) {
        deleteImage((int) imageId);
    }


}
