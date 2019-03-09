package com.heejoo.accounts;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @GeneratedValue
    @Id
    private Long id;
    private String accountId;
    private String password;
    private String name;
    private String email;
    private LocalDate joinDate;


}
