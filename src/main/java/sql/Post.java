package sql;

import base.BaseEntity;
import lombok.*;
import sql.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter @Getter
public class Post extends BaseEntity {

    @Column(name = "text")
    private String text;

    @Column(name = "post_date")
    private LocalDate localDate;

    @Column(name = "post_time")
    private LocalTime localTime;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post(String text, User user) {
        this.text = text;
        this.localDate = LocalDate.now();
        this.localTime = LocalTime.now();
        this.user = user;
    }

    @Override
    public String toString() {
        return  user.getNickname() + ":                              " + localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond() + " " + localDate + "\n" +
                text;

//                "Post{" +
//                "text='" + text + '\'' +
//                ", localDate=" + localDate +
//                ", localTime=" + localTime +
//                ", user=" + user.getNickname() +
//                '}';
    }
}
