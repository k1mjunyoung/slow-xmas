package slowxmas.Service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slowxmas.Entity.Tree;
import slowxmas.Repository.TreeRepository;

@Service
@RequiredArgsConstructor
public class TreeService {
    private final TreeRepository treeRepository;

    public void create(Long kakaoId) {
        Tree tree = new Tree();

        tree.setTreeId(kakaoId);
        tree.setCreated(LocalDateTime.now());

        this.treeRepository.save(tree);
    }


}
