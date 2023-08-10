package algonquin.cst2335.finalproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageDetailFragment extends Fragment {

    private ImageEntry imageEntry;

    // Required empty public constructor
    public ImageDetailFragment() {
    }

    // Static factory method to create a new instance of the fragment
    public static ImageDetailFragment newInstance(ImageEntry imageEntry) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("imageEntry", imageEntry); // Assuming ImageEntry is Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageEntry = (ImageEntry) getArguments().getSerializable("imageEntry");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_details, container, false);

        ImageView detailImageView = view.findViewById(R.id.detailImageView);
        TextView detailImageDimensions = view.findViewById(R.id.detailImageDimensions);
        Button detailDeleteImageButton = view.findViewById(R.id.detailDeleteImageButton);



        if (imageEntry != null) {
            Log.d("ImageDetailFragment", "Displaying image details");
            detailImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageEntry.getImageData(), 0, imageEntry.getImageData().length));
            detailImageDimensions.setText(imageEntry.getWidth() + " x " + imageEntry.getHeight());
        } else {
            Log.d("ImageDetailFragment", "ImageEntry is null");
        }

        detailDeleteImageButton.setOnClickListener(v -> {
            if (getActivity() instanceof SavedImagesActivity) {
                SavedImagesActivity activity = (SavedImagesActivity) getActivity();
                activity.deleteImage((int) imageEntry.getId());
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

                // Show the RecyclerView again after deleting the image
                activity.findViewById(R.id.savedImagesRecyclerView).setVisibility(View.VISIBLE);
            }
        });


        return view;
    }


}
