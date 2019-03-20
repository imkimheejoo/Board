package com.heejoo.questions;

import com.heejoo.accounts.Account;
import com.heejoo.answer.Answer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String contents;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private Account writer;
    //    private String writer;
    private LocalDateTime createTime;
    //mappedBy는 매핑되는 변수의 이름
    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;

    public String formattedLocalDateTime() {
        if (createTime == null) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
        return createTime.format(dateTimeFormatter);
    }

    public boolean sampleWriterCorrect(Account loginAccount) {
        return writer.equals(loginAccount);
    }
}