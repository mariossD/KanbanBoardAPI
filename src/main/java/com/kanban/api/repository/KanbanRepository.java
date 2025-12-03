package com.kanban.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.kanban.api.model.KanbanBoard;
import com.kanban.api.model.KanbanTask;
import com.kanban.api.model.Status;

@Repository
public class KanbanRepository {

    private final List<KanbanBoard> kanbanContents = new ArrayList<>();

    public KanbanRepository() {
        kanbanContents.add(new KanbanBoard(1, "Project Alpha",
                new ArrayList<>(List.of(
                        new KanbanTask(1, "Design UI", "Create initial UI designs", Status.TODO)))));

        kanbanContents.add(new KanbanBoard(2, "Project Beta",
                new ArrayList<>(List.of(
                        new KanbanTask(1, "Set up database", "Install and configure the database", Status.IN_PROGRESS),
                        new KanbanTask(2, "Implement authentication", "Add user login functionality", Status.TODO),
                        new KanbanTask(3, "Deploy to server", "Deploy the application to the production server",
                                Status.DONE)))));
    }

    public List<KanbanBoard> getKanbanBoards() {
        return kanbanContents;
    }

    public Optional<KanbanBoard> getKanbanBoardById(Integer id) {
        return kanbanContents.stream()
                .filter(kanban -> kanban.kanbanId() == id)
                .findFirst();
    }

    public Optional<KanbanTask> getTaskById(Integer boardId, Integer taskId) {
        Optional<KanbanBoard> boardOpt = getKanbanBoardById(boardId);
        if (boardOpt.isPresent()) {
            KanbanBoard board = boardOpt.get();
            return board.kanbanTasks().stream()
                    .filter(task -> task.taskId() == taskId)
                    .findFirst();
        }
        return Optional.empty();
    }

    public Optional<KanbanTask> changeTaskStatus(Integer boardId, Integer taskId, Status newStatus) {
        Optional<KanbanTask> taskOpt = getTaskById(boardId, taskId);
        if (taskOpt.isPresent()) {
            KanbanTask oldTask = taskOpt.get();
            KanbanTask updatedTask = new KanbanTask(
                    oldTask.taskId(),
                    oldTask.taskTitle(),
                    oldTask.taskDescription(),
                    newStatus);

            // Update the task in the board
            getKanbanBoardById(boardId).ifPresent(board -> {
                List<KanbanTask> tasks = board.kanbanTasks();
                tasks.removeIf(task -> task.taskId() == taskId);
                tasks.add(updatedTask);
            });
            return Optional.of(updatedTask);
        }
        return Optional.empty();
    }

    public void save(KanbanBoard kanbanBoard) {
        kanbanContents.add(kanbanBoard);
    }

}
