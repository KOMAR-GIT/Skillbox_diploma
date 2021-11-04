package main.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "captcha_codes")
@Table
public class CaptchaCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private Date time;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String code;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String secret_code;

    public CaptchaCode() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecret_code() {
        return secret_code;
    }

    public void setSecret_code(String secret_code) {
        this.secret_code = secret_code;
    }
}
