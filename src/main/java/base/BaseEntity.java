package base;

import javax.persistence.*;

@MappedSuperclass
@NamedQuery(name = "findByLogin", query = "from User where nickname = ?2")
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
}
