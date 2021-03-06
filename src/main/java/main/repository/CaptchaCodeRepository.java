package main.repository;

import main.model.CaptchaCode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {

    CaptchaCode findBySecretCode(String secretCode);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM captcha_codes where (captcha_codes.time < timestampadd(SECOND, -3600 , UTC_TIMESTAMP()))", nativeQuery = true)
    void deleteOldCaptcha(@Param("time") int time);

}
