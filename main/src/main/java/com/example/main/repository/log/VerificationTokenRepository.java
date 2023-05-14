package com.example.main.repository.log;

import com.example.main.entity.log.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByUnverifiedUserEmail(String aString);

    Optional<VerificationToken> findByToken(String aString);

    boolean existsByUnverifiedUserEmail(String aString);

    boolean existsByToken(String aLong);

    @Transactional
    void deleteByToken(String aString);

    void deleteByUnverifiedUserEmail(String aString);
}
