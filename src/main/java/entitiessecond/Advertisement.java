package entitiessecond;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "advertisement", schema = "public", catalog = "car")
@Getter
@Setter
@NoArgsConstructor
public class Advertisement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "description")
    private String description;

    @Column(name = "sold")
    private Boolean sold;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "users_id")
    private Users user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "mark_id")
    private Mark mark;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "body_id")
    private Body body;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Photo> photoList = new ArrayList<>();

    public Advertisement(String description, Boolean sold, Mark mark, Body body, Users users) {
        this.description = description;
        this.sold = sold;
        this.created = new Date(System.currentTimeMillis());
        this.mark = mark;
        this.body = body;
        this.user = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Advertisement that = (Advertisement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Advertisement{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", sold=" + sold
                + '}';
    }
}
