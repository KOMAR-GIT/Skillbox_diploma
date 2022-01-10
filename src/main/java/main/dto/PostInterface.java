package main.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

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
