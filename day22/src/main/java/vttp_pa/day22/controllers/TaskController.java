package vttp_pa.day22.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp_pa.day22.models.Task;
import vttp_pa.day22.models.User;
import vttp_pa.day22.services.TaskService;
import vttp_pa.day22.services.UserService;

@Controller
@RequestMapping
public class TaskController {

    @Autowired
    private UserService userSvc;

    @Autowired
    private TaskService taskSvc;
    
    @RequestMapping("/task")
    public String taskPage() {
        return "task";
    }
    
    @PostMapping("/task")
    public String postTask(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {

        User user = new User();
        user.setUsername(form.getFirst("username"));
        user.setPassword(form.getFirst("password"));

        // if user not founc, return error page
        if (!userSvc.findUser(user)) 
            return "fail_auth";

        Task task = new Task();
        task.setTaskName(form.getFirst("taskName"));
        task.setPriority(form.getFirst("priority"));
        task.setAssignTo(user);
        task.setCompletionDate(form.getFirst("completion"));

        taskSvc.createTask(task);
        // model.addAttribute("name", username);
        // model.addAttribute("haveUser", haveuser);
        // model.addAttribute("taskName", taskName);

        return "created-task";
    }
}
