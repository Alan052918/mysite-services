package com.example.user_account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @SequenceGenerator(name = "user_account_id_sequence", sequenceName = "user_account_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_id_sequence")
    private Long id;

    private String name;
    private String email;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;

}
