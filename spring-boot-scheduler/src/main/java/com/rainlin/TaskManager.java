package com.rainlin;

import com.rainlin.model.TaskVO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 定时任务管理器
 *
 * @author rainlin
 * @date 2023-11-13
 */
@Configuration
public class TaskManager implements SchedulingConfigurer, CommandLineRunner {
    private final ConcurrentHashMap<String, CronTask> cronTaskConcurrentHashMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, FixedDelayTask> fixedDelayTaskConcurrentHashMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, FixedRateTask> fixedRateTaskConcurrentHashMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Task> taskConcurrentHashMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ScheduledTask> scheduledTaskConcurrentHashMap = new ConcurrentHashMap<>();
    private final ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;

    public TaskManager(ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor) {
        this.scheduledAnnotationBeanPostProcessor = scheduledAnnotationBeanPostProcessor;
    }

    /**
     * 获取定时任务列表
     *
     * @return 定时任务列表
     */
    public List<TaskVO> getScheduledTasks() {
        List<TaskVO> cronTasks = cronTaskConcurrentHashMap.values().stream()
                .map(cronTask -> new TaskVO(cronTask.toString(), cronTask.getExpression()))
                .collect(Collectors.toList());
        List<TaskVO> fixedDelayTasks = fixedDelayTaskConcurrentHashMap.values().stream()
                .map(fixedDelayTask -> new TaskVO(fixedDelayTask.toString(), fixedDelayTask.getInterval() + "/" + fixedDelayTask.getInitialDelay()))
                .collect(Collectors.toList());
        List<TaskVO> fixedRageTasks = fixedRateTaskConcurrentHashMap.values().stream()
                .map(fixedRateTask -> new TaskVO(fixedRateTask.toString(), fixedRateTask.getInterval() + "/" + fixedRateTask.getInitialDelay()))
                .collect(Collectors.toList());
        List<TaskVO> tasks = new ArrayList<>();
        tasks.addAll(cronTasks);
        tasks.addAll(fixedDelayTasks);
        tasks.addAll(fixedRageTasks);
        return tasks;
    }

    /**
     * 立即运行定时任务
     *
     * @param taskName 任务名
     */
    public void run(String taskName) {
        Optional.ofNullable(taskConcurrentHashMap.get(taskName))
                .orElseThrow(() -> new RuntimeException("任务不存在"))
                .getRunnable().run();
    }

    /**
     * 停止定时任务
     *
     * @param taskName 任务名
     */
    public void stop(String taskName) {
        scheduledTaskConcurrentHashMap.get(taskName).cancel();
    }

    /**
     * 启动定时任务
     *
     * @param taskName 任务名
     */
    public void start(String taskName) {
        // TODO
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.getCronTaskList()
                .forEach(cronTask -> cronTaskConcurrentHashMap.put(cronTask.toString(), cronTask));
        taskRegistrar.getFixedDelayTaskList()
                .forEach(fixedDelayTask -> fixedDelayTaskConcurrentHashMap.put(fixedDelayTask.toString(), (FixedDelayTask) fixedDelayTask));
        taskRegistrar.getFixedRateTaskList()
                .forEach(fixedRateTask -> fixedRateTaskConcurrentHashMap.put(fixedRateTask.toString(), (FixedRateTask) fixedRateTask));
        taskConcurrentHashMap.putAll(cronTaskConcurrentHashMap);
        taskConcurrentHashMap.putAll(fixedDelayTaskConcurrentHashMap);
        taskConcurrentHashMap.putAll(fixedRateTaskConcurrentHashMap);
    }

    @Override
    public void run(String... args) {
        Map<String, ScheduledTask> scheduledTaskMap = scheduledAnnotationBeanPostProcessor.getScheduledTasks().stream()
                .collect(Collectors.toMap(ScheduledTask::toString, Function.identity()));
        this.scheduledTaskConcurrentHashMap.putAll(scheduledTaskMap);
    }
}
