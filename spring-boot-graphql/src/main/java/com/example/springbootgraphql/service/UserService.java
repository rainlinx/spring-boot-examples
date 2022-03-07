package com.example.springbootgraphql.service;

import com.example.springbootgraphql.model.Company;
import com.example.springbootgraphql.model.User;
import org.springframework.stereotype.Service;

/**
 * @author rainlin
 */
@Service
public class UserService {

    public User getUserById(Long id) {
        final User user = new User();
        user.setId(id);
        user.setAge(26);
        user.setName("rainlin");
        user.setSex(0);
        final Company company = new Company();
        company.setId(id);
        company.setName("cd");
        user.setCompany(company);
        return user;
    }
}
