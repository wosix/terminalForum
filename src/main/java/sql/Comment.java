package sql;

import base.BaseEntity;
import javafx.geometry.Pos;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Setter @Getter
@ToString
public class Comment extends BaseEntity {

    @Column(name = "comment")
    private String comment;

    @Column(name = "comment_date")
    private LocalDate localDate;

    @Column(name = "comment_time")
    private LocalTime localTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    public Comment(String comment, User user, Post post) {
        this.comment = comment;
        this.localDate = LocalDate.now();
        this.localTime = LocalTime.now();
        this.user = user;
        this.post = post;
    }

    @Override
    public String toString() {
        return  user.getNickname() + ":                              " + localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond() + " " + localDate + "\n" +
                comment;

//                "Comment{" +
//                "comment='" + comment + '\'' +
//                ", localDate=" + localDate +
//                ", localTime=" + localTime +
//                ", user=" + user +
//                ", post=" + post +
//                '}';
    }
}
