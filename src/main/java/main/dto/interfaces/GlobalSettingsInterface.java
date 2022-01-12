package main.dto.interfaces;

import main.dto.GlobalSettingsCodes;

public interface GlobalSettingsInterface {

    Integer getId();
    GlobalSettingsCodes getCode();
    String getName();
    boolean getValue();

}
