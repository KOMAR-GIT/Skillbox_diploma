package main.dto.interfaces;

import java.util.Date;

public interface CommentInterface {

    int getId();
    Date getTimestamp();
    String getText();
    int getUserId();
    String getUserName();
    String getPhoto();

}
