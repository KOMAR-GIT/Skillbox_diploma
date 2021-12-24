package main.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "captcha_codes")
@Table
public class CaptchaCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private Date time;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    private String code;

    @Column(name = "secret_code", columnDefinition = "TINYTEXT", nullable = false)
    private String secretCode;

    public CaptchaCode() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
}
