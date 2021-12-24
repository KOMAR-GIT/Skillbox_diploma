package main.service;

import main.api.response.SettingsResponse;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    public SettingsResponse getGlobalSettings(){
        return new SettingsResponse();
    }

}
