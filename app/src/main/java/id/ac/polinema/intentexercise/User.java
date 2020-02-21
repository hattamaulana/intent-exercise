package id.ac.polinema.intentexercise;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String fullname;
    private String email;
    private String password;
    private String homePage;
    private String about;
    private Uri imageUri;

    public User() {
    }

    public User(
            String fullname, String email, String password, String homePage, String about,
            Uri imageUri
    ) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.about = about;
        this.imageUri = imageUri;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        if (homePage.contains("ht")) {
            this.homePage = homePage;
        } else {
            this.homePage = homePage;
        }
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullname);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.homePage);
        dest.writeString(this.about);
        dest.writeParcelable(this.imageUri, flags);
    }

    protected User(Parcel in) {
        this.fullname = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.homePage = in.readString();
        this.about = in.readString();
        this.imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
