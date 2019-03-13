package com.heejoo.boards;

import com.heejoo.accounts.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String contents;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private Account writer;
//    private String writer;
    private LocalDateTime loadTime;
}
