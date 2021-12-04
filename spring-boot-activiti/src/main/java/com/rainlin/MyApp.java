package com.rainlin;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MyApp {

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

    //@Bean
    //public CommandLineRunner init(final RepositoryService repositoryService,
    //                              final RuntimeService runtimeService,
    //                              final TaskService taskService) {
    //
    //    return new CommandLineRunner() {
    //        @Override
    //        public void run(String... strings) throws Exception {
    //            Map<String, Object> variables = new HashMap<String, Object>();
    //            variables.put("applicantName", "John Doe");
    //            variables.put("email", "john.doe@activiti.com");
    //            variables.put("phoneNumber", "123456789");
    //            //runtimeService.startProcessInstanceByKey("hireProcess", variables);
    //            //System.out.printf("正在运行中的流程有%d条\n", runtimeService.createProcessInstanceQuery().count());
    //            //Execution execution = runtimeService.createExecutionQuery().list().get(0);
    //            //runtimeService.messageEventReceived("message", execution.getId());
    //            //runtimeService.messageEventReceived();
    //        }
    //    };
    //}
}
