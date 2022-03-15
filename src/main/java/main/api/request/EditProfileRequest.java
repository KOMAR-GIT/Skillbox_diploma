package main.api.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class EditProfileRequest {

    private String name;
    private String email;
    private String password;
    private Boolean removePhoto;
    @Autowired
    private MultipartFile photo;

    public EditProfileRequest() {
        photo = null;
    }

    public EditProfileRequest(String name, String email, String password, Boolean removePhoto, MultipartFile photo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.removePhoto = removePhoto;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getRemovePhoto() {
        return removePhoto;
    }

    public void setRemovePhoto(Boolean removePhoto) {
        this.removePhoto = removePhoto;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
