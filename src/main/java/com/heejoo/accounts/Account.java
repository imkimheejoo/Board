package com.heejoo.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Account {

    @GeneratedValue
    @Id
    private Long id;
    public boolean matchId(Long newId){
        if(newId==null){
            return false;
        }
        return newId.equals(id);
    }
    private String accountId;
    @JsonIgnore
    private String password;
    private String name;
    private String email;
    private LocalDate joinDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean matchPassword(String newPassword){
        if(newPassword==null){
            return false;
        }
        return newPassword.equals(password);
    }

}
