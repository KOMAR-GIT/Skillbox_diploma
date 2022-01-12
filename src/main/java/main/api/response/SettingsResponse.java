package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class SettingsResponse {

    @JsonProperty("MULTIUSER_MODE")
    private Boolean multiuserMode;
    @JsonProperty("POST_PREMODERATION")
    private Boolean postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private Boolean statisticIsPublic;

    public SettingsResponse(Boolean multiuserMode, Boolean postPremoderation, Boolean statisticIsPublic) {
        this.multiuserMode = multiuserMode;
        this.postPremoderation = postPremoderation;
        this.statisticIsPublic = statisticIsPublic;
    }

    public SettingsResponse() {
    }

    public Boolean isMultiuserMode() {
        return multiuserMode;
    }

    public void setMultiuserMode(Boolean multiuserMode) {
        this.multiuserMode = multiuserMode;
    }

    public Boolean isPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(Boolean postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public Boolean isStatisticIsPublic() {
        return statisticIsPublic;
    }

    public void setStatisticIsPublic(Boolean statisticIsPublic) {
        this.statisticIsPublic = statisticIsPublic;
    }
}
