package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "engine", schema = "public", catalog = "car")
@Getter
@Setter
@NoArgsConstructor
public class Engine {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number", nullable = false, length = 128)
    private String number;

    /** при удалении двигетеля, автомобиль каскадно удаляем*/
    @OneToOne(mappedBy = "engine", cascade = CascadeType.ALL)
    private Car car;

    public Engine(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Engine engine = (Engine) o;
        return Objects.equals(id, engine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Engine{"
                + "id=" + id
                + ", number='" + number + '\''
                + '}';
    }
}
