package com.heejoo.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.heejoo.AbstractEntity;
import com.heejoo.accounts.Account;
import com.heejoo.answer.Answer;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends AbstractEntity {

    private String title;
    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private Account writer;
    //    private String writer;

    //mappedBy는 매핑되는 변수의 이름
    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;

    private Integer countOfAnswer = 0;

    public void addAnswer(){
        countOfAnswer++;
    }

    public void deleteAnswer(){
        countOfAnswer--;
    }

    public boolean sampleWriterCorrect(Account loginAccount) {
        return writer.equals(loginAccount);
    }
}
