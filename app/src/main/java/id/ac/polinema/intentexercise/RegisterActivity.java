package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements
        Validator.ValidationListener,
        View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getName();
    private static final int GALLERY_REQUEST_CODE = 1;

    private Validator validator;
    private Uri imgUri;

    @NotEmpty
    private EditText edtFullname;

    @NotEmpty
    @Email
    private EditText edtEmail;

    @NotEmpty
    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText edtPassword;

    @NotEmpty
    @ConfirmPassword
    private EditText edtConfirmPassword;

    @NotEmpty
    private EditText edthomePage;

    @NotEmpty
    private EditText edtAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind View
        edtFullname = findViewById(R.id.text_fullname);
        edtEmail = findViewById(R.id.text_email);
        edtPassword = findViewById(R.id.text_password);
        edtConfirmPassword = findViewById(R.id.text_confirm_password);
        edthomePage = findViewById(R.id.text_homepage);
        edtAbout = findViewById(R.id.text_about);

        // Set on click listener button
        findViewById(R.id.imageView).setOnClickListener(this);
        findViewById(R.id.button_ok).setOnClickListener(this);

        // Instance UI Validator
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        String fullname = edtFullname.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String homepage = edthomePage.getText().toString();
        String about = edtAbout.getText().toString();

        User user = new User(fullname, email, password, homepage, about, imgUri);
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_DATA, user);

        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();

            String message = "";

            if (view instanceof EditText) {
                EditText edt = (EditText) view;
                message = edt.getHint().toString() + " ";
                edt.setError(error.getCollatedErrorMessage(this));
            }

            message += error.getCollatedErrorMessage(this);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView :
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
                break;

            case R.id.button_ok :
                validator.validate();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                imgUri = data.getData();
            }
        }
    }
}
