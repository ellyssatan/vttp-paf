package vttp_pa.day22.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_pa.day22.models.Task;
import vttp_pa.day22.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository tRepo;

    public boolean createTask(Task task) throws Exception {

        int count = tRepo.createTask(task);
        System.out.printf("Insert task count: %d", count);

        return count > 0;
    }
}
