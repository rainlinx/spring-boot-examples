package com.rainlin;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResumeService {

    public void storeResume() {
        System.out.println("Storing resume ...");
    }

    public void printTaskA(String msg) {
        System.out.println(Optional.ofNullable(msg).orElse("taskA"));
    }

    public void printTaskB(String msg) {
        System.out.println(Optional.ofNullable(msg).orElse("taskA"));
    }
}
