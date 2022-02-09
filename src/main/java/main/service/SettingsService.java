package main.service;

import main.api.response.SettingsResponse;
import main.dto.GlobalSettingsCodes;
import main.dto.interfaces.GlobalSettingsInterface;
import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService {

    private final GlobalSettingsRepository globalSettingsRepository;

    public SettingsService(GlobalSettingsRepository globalSettingsRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
    }


    public SettingsResponse getAllGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        List<GlobalSettingsInterface> settings = globalSettingsRepository.getSettings();
        for (GlobalSettingsInterface setting : settings) {
            switch (setting.getCode()) {
                case MULTIUSER_MODE:
                    settingsResponse.setMultiuserMode(setting.getValue());
                case POST_PREMODERATION:
                    settingsResponse.setPostPremoderation(setting.getValue());
                case STATISTICS_IS_PUBLIC:
                    settingsResponse.setStatisticIsPublic(setting.getValue());
            }
        }
        return settingsResponse;
    }

    public Boolean getOneSetting(GlobalSettingsCodes code) {
        String value = globalSettingsRepository.findByCode(code.toString()).getValue();
        return value.equals("1");
    }

}
