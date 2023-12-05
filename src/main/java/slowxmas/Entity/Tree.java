package slowxmas.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tree {
    @Id
    private Long treeId;

    private LocalDateTime created;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.REMOVE)
    private List<Letter> letterList;
}
