package algonquin.cst2335.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<ImageEntry> imageList;
    private Context context;


    private SavedImagesActivity activity;

    private ImageDeleteListener deleteListener;
    public interface ImageDeleteListener {
        void onDeleteImage(long imageId);
    }

    public ImageListAdapter(Context context, List<ImageEntry> imageList, ImageDeleteListener deleteListener) {
        this.activity = (SavedImagesActivity) context;
        this.context = context;
        this.imageList = imageList;
        this.deleteListener = deleteListener;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_image, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageEntry imageEntry = imageList.get(position);
        Glide.with(context).load(imageEntry.getImageData()).into(holder.savedImageView);
        holder.imageDimensions.setText(imageEntry.getWidth() + " x " + imageEntry.getHeight());

        holder.itemView.setOnClickListener(v -> {
            ImageDetailFragment detailFragment = ImageDetailFragment.newInstance(imageEntry);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();

            // Hide the RecyclerView
            activity.findViewById(R.id.savedImagesRecyclerView).setVisibility(View.GONE);
        });


        holder.deleteImageButton.setOnClickListener(v -> {
            long imageId = imageList.get(position).getId();
            deleteListener.onDeleteImage(imageId);
        });


    }
    public void updateData(List<ImageEntry> newImageList) {
        this.imageList = newImageList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView savedImageView;
        TextView imageDimensions;
        Button deleteImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            savedImageView = itemView.findViewById(R.id.savedImageView);
            imageDimensions = itemView.findViewById(R.id.imageDimensions);
            deleteImageButton = itemView.findViewById(R.id.deleteImageButton);
        }
    }
}
