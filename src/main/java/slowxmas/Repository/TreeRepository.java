package slowxmas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slowxmas.Entity.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
}
