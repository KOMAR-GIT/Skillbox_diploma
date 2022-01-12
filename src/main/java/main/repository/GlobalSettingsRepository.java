package main.repository;

import main.dto.interfaces.GlobalSettingsInterface;
import main.model.GlobalSetting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSetting, Integer> {


    @Query(value = "SELECT * FROM global_settings", nativeQuery = true)
    List<GlobalSettingsInterface> getSettings();


}
