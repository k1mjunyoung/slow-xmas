package slowxmas.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import slowxmas.Service.TreeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tree")
public class TreeController {
    private final TreeService treeService;

    /*@GetMapping("/create")
    public String createTree(Model model) {

    }*/
}
