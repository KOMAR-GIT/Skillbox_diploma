package main.dto;

import java.math.BigInteger;

public class UserDto {

    private Integer id;
    private String name;
    private String photo;
    private String email;
    private Byte moderation;
    private BigInteger moderationCount;
    private Boolean settings;

    public UserDto() {
    }

    public UserDto(Integer id,
                   String name,
                   String photo,
                   String email,
                   Byte moderation,
                   BigInteger moderationCount) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.moderation = moderation;
        this.moderationCount = moderationCount;
        settings = moderation == 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getModeration() {
        return moderation;
    }

    public void setModeration(Byte moderation) {
        this.moderation = moderation;
    }

    public BigInteger getModerationCount() {
        return moderationCount;
    }

    public void setModerationCount(BigInteger moderationCount) {
        this.moderationCount = moderationCount;
    }

    public Boolean getSettings() {
        return settings;
    }

    public Boolean isSettings() {
        return settings;
    }

    public void setSettings(Boolean settings) {
        this.settings = settings;
    }
}
