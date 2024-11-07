package by.autobaraholka.userMicroservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor  // Конструктор со всеми параметрами
@NoArgsConstructor
@Table(name = "resettoken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int id;

    @Column(name = "token_token")
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "token_user_id") // Ссылается на пользователя
    private User user;

    @Column(name = "token_expiryDate")
    private LocalDateTime expiryDate;  // Срок действия токена

}