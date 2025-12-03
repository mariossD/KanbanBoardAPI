package com.kanban.api.model;

public record KanbanTask(
    int taskId,
    String taskTitle,
    String taskDescription,
    Status taskStatus) {
}
