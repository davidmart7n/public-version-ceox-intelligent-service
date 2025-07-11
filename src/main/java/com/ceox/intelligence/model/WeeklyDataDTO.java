package com.ceox.intelligence.model;

import java.util.List;

public class WeeklyDataDTO {
    private List<?> tasks;
    private List<?> projects;
    private List<?> clients;
    private List<?> notifications;

    public WeeklyDataDTO() {}

    public List<?> getTasks() {
        return tasks;
    }

    public void setTasks(List<?> tasks) {
        this.tasks = tasks;
    }

    public List<?> getProjects() {
        return projects;
    }

    public void setProjects(List<?> projects) {
        this.projects = projects;
    }

    public List<?> getClients() {
        return clients;
    }

    public void setClients(List<?> clients) {
        this.clients = clients;
    }

    public List<?> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<?> notifications) {
        this.notifications = notifications;
    }
}
