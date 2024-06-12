package com.rainlin.controller;

import com.rainlin.TaskManager;
import com.rainlin.model.TaskVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 定时任务管理控制器
 *
 * @author rainlin
 * @date 2023-11-13
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskManager taskManager;

    public TaskController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * 获取定时任务列表
     *
     * @return 定时任务列表
     */
    @RequestMapping
    public List<TaskVO> getScheduledTasks() {
        return taskManager.getScheduledTasks();
    }

    /**
     * 立即运行定时任务
     *
     * @param taskName 任务名
     */
    @PostMapping("/run")
    public void runTask(@RequestParam String taskName) {
        taskManager.run(taskName);
    }

    /**
     * 取消定时任务
     *
     * @param taskName 任务名
     */
    @PostMapping("/stop")
    public void stopTask(@RequestParam String taskName) {
        taskManager.stop(taskName);
    }
}