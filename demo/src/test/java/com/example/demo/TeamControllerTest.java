package com.example.demo;

import com.example.demo.Entities.Player;
import com.example.demo.Entities.Team;
import com.example.demo.Repositories.TeamRepository;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.Controllers.TeamController;
import com.example.demo.Controllers.PlayerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
@ActiveProfiles("test")
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    public void getAllTeams() throws Exception {
        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Team A");

        Mockito.when(teamRepository.findAll()).thenReturn(Arrays.asList(team1));

        mockMvc.perform(get("/api/teams"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Team A"));
    }

    @Test
    public void updateTeam() throws Exception {
        Team existingTeam = new Team();
        existingTeam.setId(1L);
        existingTeam.setName("Team A");

        Team updatedTeam = new Team();
        updatedTeam.setId(1L);
        updatedTeam.setName("Team B");

        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(existingTeam));
        Mockito.when(teamRepository.save(any(Team.class))).thenReturn(updatedTeam);

        mockMvc.perform(put("/api/teams/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Team B\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Team B"));
    }

    @Test
    public void deleteTeam() throws Exception {
    Team existingTeam = new Team();
    existingTeam.setId(1L);
    existingTeam.setName("Team A");

    Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(existingTeam));

    mockMvc.perform(delete("/api/teams/1"))
        .andExpect(status().isNoContent());

    Mockito.verify(teamRepository, Mockito.times(1)).delete(existingTeam);
}

    @Test
    public void deleteNonExistentTeam() throws Exception {
    Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(delete("/api/teams/1"))
        .andExpect(status().isNotFound());

    Mockito.verify(teamRepository, Mockito.never()).delete(any(Team.class));
}

}
