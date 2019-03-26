package com.heejoo.answer;

import com.heejoo.AbstractEntity;
import com.heejoo.accounts.Account;
import com.heejoo.questions.Question;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer extends AbstractEntity {

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private Account writer;

    public boolean isSameWriter(Account loginAccount){
        return loginAccount.equals(this.writer);
    }
}
