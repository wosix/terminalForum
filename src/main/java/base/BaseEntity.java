package base;

import javax.persistence.*;

@MappedSuperclass
@NamedQuery(name = "findByLogin", query = "from User where nickname = ?2")
@NamedQuery(name = "findPostById", query = "from Post where id = ?3")
@NamedQuery(name = "findCommentByPostId", query = "from Post where post_id = ?4")
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
}
