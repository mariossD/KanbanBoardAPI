package com.kanban.api.model;

import java.util.List;

public record KanbanBoard(
        int kanbanId,
        String kanbanTitle,
        List<KanbanTask> kanbanTasks) {
}
