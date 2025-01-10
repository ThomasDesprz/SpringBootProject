package com.example.demo;

import com.example.demo.Entities.Player;
import com.example.demo.Entities.Team;
import com.example.demo.Repositories.TeamRepository;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.Controllers.TeamController;
import com.example.demo.Controllers.PlayerController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
@ActiveProfiles("test")
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerRepository playerRepository;

    @Test
    public void updatePlayer() throws Exception {
        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Team A");

        Player existingPlayer = new Player();
        existingPlayer.setId(1L);
        existingPlayer.setName("Player 1");
        existingPlayer.setTeam(team1);

        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(existingPlayer));

        mockMvc.perform(put("/api/players/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Player\", \"team\": { \"id\": 1 }}"))
            .andExpect(status().isOk());
    }

    @Test
    public void deletePlayer() throws Exception {
    Player existingPlayer = new Player();
    existingPlayer.setId(1L);
    existingPlayer.setName("Player A");

    Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(existingPlayer));

    mockMvc.perform(delete("/api/players/1"))
        .andExpect(status().isNoContent());

    Mockito.verify(playerRepository, Mockito.times(1)).delete(existingPlayer);
    }

    @Test
    public void deleteNonExistentPlayer() throws Exception {
    Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(delete("/api/players/1"))
        .andExpect(status().isNotFound());

    Mockito.verify(playerRepository, Mockito.never()).delete(any(Player.class));
    }

}
