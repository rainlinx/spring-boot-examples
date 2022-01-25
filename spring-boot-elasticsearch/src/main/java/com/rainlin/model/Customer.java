
package com.rainlin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author rainlin
 */
@Data
@Document(indexName = "customer")
public class Customer {

    @Id
    private String id;
    private String userName;
    private String address;
    private int age;

    public Customer(String userName, String address, int age) {
        this.userName = userName;
        this.address = address;
        this.age = age;
    }
}
