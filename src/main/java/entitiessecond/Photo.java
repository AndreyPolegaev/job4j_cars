package entitiessecond;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "photo", schema = "public", catalog = "car")
@Getter
@Setter
@NoArgsConstructor
public class Photo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "photoname")
    private String photoName;

    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Advertisement advertisement;

    public Photo(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Photo{"
                + "id=" + id
                + ", photoName='" + photoName + '\''
                + '}';
    }
}
