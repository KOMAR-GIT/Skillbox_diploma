package main.dto.interfaces;

import java.math.BigInteger;
import java.sql.Timestamp;

public interface PostInterface {

    Integer getId();
    Timestamp getTimestamp();
    Byte getActive();
    Integer getUserId();
    String getUserName();
    String getTitle();
    String getText();
    BigInteger getLikeCount();
    BigInteger getDislikeCount();
    Integer getViewCount();
}
