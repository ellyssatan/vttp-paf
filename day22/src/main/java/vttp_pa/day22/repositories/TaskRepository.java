package vttp_pa.day22.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp_pa.day22.models.Task;

import static vttp_pa.day22.repositories.Queries.*;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate template;

    public Integer createTask(Task task) throws Exception {
        return template.update(SQL_INSERT_TASK,
            task.getTaskName(), task.getPriority().toString(), task.getAssignTo().getUsername(), task.getCompletion());
    }
}
