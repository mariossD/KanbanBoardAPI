package com.kanban.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.kanban.api.model.KanbanBoard;
import com.kanban.api.model.KanbanTask;
import com.kanban.api.model.Status;

@Service
public class KanbanClientService {

  private final WebClient client = WebClient.create("http://localhost:8080");

  public void fetchBoards() {
    String response = client.get()
        .uri("/api/boards")
        .retrieve()
        .bodyToMono(String.class)
        .block();

    System.out.println(response);
  }

  public void postBoard(KanbanBoard board) {
    String response = client.post()
        .uri("/api/boards")
        .bodyValue(board)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    System.out.println(response);
  }

}
