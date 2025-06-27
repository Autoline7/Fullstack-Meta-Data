package controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    @GetMapping("/unfollowers")
    public List<String> getUnfollowers() {
        //Example Data
        List<String> unfollowers = new ArrayList<>();
        unfollowers.add("diego");
        unfollowers.add("vi");
        unfollowers.add("mariana");
        return unfollowers;
    }
}
