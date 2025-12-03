package com.kanban.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import com.kanban.api.model.KanbanBoard;
import com.kanban.api.repository.KanbanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/boards")
public class KanbanBoardController {

    private final KanbanRepository kanbanRepository;

    public KanbanBoardController(KanbanRepository kanbanCollectionRepository) {
        this.kanbanRepository = kanbanCollectionRepository;
    }

    @GetMapping("")
    public List<KanbanBoard> getAllKanbanBoards() {
        return kanbanRepository.getKanbanBoards();
    }

    @GetMapping("/{id}")
    public KanbanBoard getKanbanBoardById(@PathVariable Integer id) {
        return kanbanRepository.getKanbanBoardById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Kanban board not found with id: " + id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void createKanbanBoard(@RequestBody KanbanBoard kanbanBoard) {
        kanbanRepository.save(kanbanBoard);
    }

    @PutMapping("/{id}")
    public void updateKanbanBoard(@RequestBody KanbanBoard kanbanBoard, @PathVariable Integer id) {
        if (!kanbanRepository.getKanbanBoardById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Kanban board not found with id: " + id);
        }
        kanbanRepository.save(kanbanBoard);
    }

}
