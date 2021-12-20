package main.model;

import javax.persistence.*;

@Entity(name = "global_settings")
@Table
public class GlobalSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String code;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String value;

    public GlobalSetting() {
    }
}
