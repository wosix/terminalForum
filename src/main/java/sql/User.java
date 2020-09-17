package sql;

import base.BaseEntity;
import lombok.*;
import sql.Comment;
import sql.Post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter @Getter
@ToString
public class User extends BaseEntity {

    @Column(name = "city", length = 20)
    private String city;

    @Column(name = "nickname", length = 10, unique = true)
    private String nickname;

    @Column(name = "password", length = 4)
    private String password;

    @Column(name = "phone_number", length = 9)
    private String phone;

    @Column(name = "account_date")
    private LocalDate localDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User(String city, String nickname, String password, String phone) {
        this.city = city;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.localDate = LocalDate.now();
    }

}
