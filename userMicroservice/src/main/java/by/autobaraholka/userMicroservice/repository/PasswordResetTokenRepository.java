package by.autobaraholka.userMicroservice.repository;

import by.autobaraholka.userMicroservice.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser_Email(String email);
}