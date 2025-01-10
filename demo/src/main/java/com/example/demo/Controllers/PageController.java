package com.example.demo.Controllers;

import com.example.demo.Entities.Team;
import com.example.demo.Entities.Player;
import com.example.demo.Repositories.TeamRepository;
import com.example.demo.Repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("players", playerRepository.findAll());
        return "index";
    }

    @PostMapping("/teams")
    public String createTeam(@RequestParam String name) {
        Team team = new Team();
        team.setName(name);
        teamRepository.save(team);
        return "redirect:/";
    }

    @PostMapping("/players")
    public String createPlayer(@RequestParam String name, @RequestParam Long teamId) {
        Player player = new Player();
        player.setName(name);
        player.setTeam(teamRepository.findById(teamId).orElse(null));
        playerRepository.save(player);
        return "redirect:/";
    }

    @GetMapping("/delete/team/{id}")
    public String deleteTeam(@PathVariable Long id) {
        teamRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/delete/player/{id}")
    public String deletePlayer(@PathVariable Long id) {
        playerRepository.deleteById(id);
        return "redirect:/";
    }
}
