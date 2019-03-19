package com.heejoo.questions;

import com.heejoo.accounts.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

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
