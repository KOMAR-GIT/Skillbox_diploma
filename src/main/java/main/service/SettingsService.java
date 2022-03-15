package main.service;

import main.api.request.SettingsRequest;
import main.api.response.SettingsResponse;
import main.dto.GlobalSettingsCodes;
import main.dto.interfaces.GlobalSettingsInterface;
import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import main.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                    break;
                case POST_PREMODERATION:
                    settingsResponse.setPostPremoderation(setting.getValue());
                    break;
                case STATISTICS_IS_PUBLIC:
                    settingsResponse.setStatisticIsPublic(setting.getValue());
                    break;
            }
        }
        return settingsResponse;
    }

    public boolean saveSettings(SettingsRequest settingsRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals("anonymousUser")) {
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            if (securityUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("user:moderate"))) {
                Iterable<GlobalSetting> settings = globalSettingsRepository.findAll();
                List<GlobalSetting> newSettings = new ArrayList<>();
                for (GlobalSetting setting : settings) {
                    switch (setting.getCode()) {
                        case "MULTIUSER_MODE":
                            newSettings.add(new GlobalSetting(
                                    "MULTIUSER_MODE",
                                    setting.getName(),
                                    settingsRequest.isMultiUserMode() ? "1" : "0"));
                            break;
                        case "POST_PREMODERATION":
                            newSettings.add(new GlobalSetting(
                                    "POST_PREMODERATION",
                                    setting.getName(),
                                    settingsRequest.isPostPreModeration() ? "1" : "0"));
                            break;
                        case "STATISTICS_IS_PUBLIC":
                            newSettings.add(new GlobalSetting(
                                    "STATISTICS_IS_PUBLIC",
                                    setting.getName(),
                                    settingsRequest.isStatisticsIsPublic() ? "1" : "0"));
                            break;
                    }
                }
                globalSettingsRepository.saveAll(newSettings);
                return true;
            }
        }
        return false;
    }

    public Boolean getOneSetting(GlobalSettingsCodes code) {
        String value = globalSettingsRepository.findByCode(code.toString()).getValue();
        return value.equals("1");
    }

}
