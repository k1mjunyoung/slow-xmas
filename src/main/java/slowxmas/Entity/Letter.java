package slowxmas.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Letter {
    @Id
    private Integer letterId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime created;

    @ManyToOne
    private Tree tree;


}
