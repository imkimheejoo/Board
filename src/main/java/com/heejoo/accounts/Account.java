package com.heejoo.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heejoo.AbstractEntity;
import com.heejoo.answer.Answer;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractEntity {

    private String accountId;
    @JsonIgnore
    private String password;
    private String name;
    private String email;
    private LocalDate joinDate;


    public boolean matchPassword(String newPassword){
        if(newPassword==null){
            return false;
        }
        return newPassword.equals(password);
    }

}
