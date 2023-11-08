//package org.activiti;
//
//import org.activiti.engine.HistoryService;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@SpringBootTest
//public class SimpleProcessTest {
//    @Autowired
//    private RuntimeService runtimeService;
//    @Autowired
//    private TaskService taskService;
//    @Autowired
//    private HistoryService historyService;
//    @Autowired
//    private RepositoryService repositoryService;
//
//    @Test
//    public void testSimpleProcess() {
//        final ProcessInstance simple = runtimeService.startProcessInstanceByKey("Simple");
//        System.out.println("unfinished: " + historyService.createHistoricProcessInstanceQuery().processInstanceId(simple.getProcessInstanceId()).unfinished().count());
//        System.out.println("finished: " + historyService.createHistoricProcessInstanceQuery().processInstanceId(simple.getProcessInstanceId()).finished().count());
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("printTask", "A");
//        variables.put("msg", "Hello World");
//        variables.put("tlName", "xiangyl");
//        runtimeService.messageEventReceived("message",
//                runtimeService.createExecutionQuery().processInstanceId(simple.getProcessInstanceId())
//                        .messageEventSubscriptionName("message").singleResult().getId(), variables);
//
//        final Task userTask = taskService.createTaskQuery().processInstanceId(simple.getProcessInstanceId()).taskAssignee("xiangyl").singleResult();
//        taskService.complete(userTask.getId());
//        System.out.println("finished: " + historyService.createHistoricProcessInstanceQuery().processInstanceId(simple.getProcessInstanceId()).finished().count());
//    }
//}
