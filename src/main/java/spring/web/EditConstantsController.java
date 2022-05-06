package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.Rank;
import spring.service.RankService;

import java.util.List;

import static spring.Application.*;

@RestController
@RequestMapping("/archery/admin/edit")
public class EditConstantsController
{
   /* private RankService rankService;

    @GetMapping(value = "/ranks", consumes = "application/json")
    public ResponseEntity<List<Rank>> showRanks()
    {
        return new ResponseEntity<>(rankService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/constants", consumes = "application/json")
    public ResponseEntity<String> showConstants()
    {
        String constants = numberOfShields + " " + wishedNumberOfJuniors + " " + wishedNumberOfDemandingTrainer;
        return new ResponseEntity<>(constants, HttpStatus.OK);
    }

    @PostMapping(value = "/changerankcolor", consumes = "application/json")
    public ResponseEntity<String> changeColor(@RequestParam String name, @RequestParam String color)
    {
        rankService.changeColor("blue", "juniors");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/changeshields", consumes = "application/json")
    public ResponseEntity<String> changeShields(@RequestParam int number)
    {
        numberOfShields = number;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/changejuniors", consumes = "application/json")
    public ResponseEntity<String> changeJuniors(@RequestParam int number)
    {
        wishedNumberOfJuniors = number;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/changedemandingtrainer", consumes = "application/json")
    public ResponseEntity<String> changeDemandingTrainer(@RequestParam int number)
    {
        wishedNumberOfDemandingTrainer = number;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public void setRankService(RankService rankService)
    {
        this.rankService = rankService;
    }*/
}
