package com.example.springbootgraphql.model;

import lombok.Data;

/**
 * @author rainlin
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private Integer sex;
    private Company company;
}
