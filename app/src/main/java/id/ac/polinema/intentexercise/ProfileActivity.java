package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getName();
    public static final String EXTRA_DATA = "EXTRA_DATA";

    private TextView tvAbout;
    private TextView tvFullname;
    private TextView tvEmail;
    private TextView tvHomepage;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Bind View
        tvAbout = findViewById(R.id.label_about);
        tvFullname = findViewById(R.id.label_fullname);
        tvEmail = findViewById(R.id.label_email);
        tvHomepage = findViewById(R.id.label_homepage);

        Intent extras = getIntent();
        user = extras.getParcelableExtra(EXTRA_DATA);

        if (user.getImageUri() != null) {
            try {
                Uri imgProfile = user.getImageUri();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgProfile);
                ImageView image = findViewById(R.id.image_profile);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.d(TAG, "onCreated: "+ e.getMessage());
            }
        }

        tvAbout.setText(user.getAbout());
        tvFullname.setText(user.getFullname());
        tvEmail.setText(user.getEmail());
        tvHomepage.setText(user.getHomePage());
    }

    public void handleViewHomePage(View view) {
        String homepage = user.getHomePage();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));

        startActivity(intent);
    }
}
