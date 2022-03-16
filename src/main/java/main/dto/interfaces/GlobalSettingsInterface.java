package main.dto.interfaces;

import main.model.enums.GlobalSettingsCodes;

public interface GlobalSettingsInterface {

    Integer getId();
    GlobalSettingsCodes getCode();
    String getName();
    boolean getValue();

}
