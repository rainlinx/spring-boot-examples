package com.example.springbootgraphql.resolver;

import com.example.springbootgraphql.model.User;
import com.example.springbootgraphql.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

/**
 * @author rainlin
 */
@Component
public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserService userService;

    public UserQueryResolver(UserService userService) {
        this.userService = userService;
    }

    public User getUserById(Long id) {
        return userService.getUserById(id);
    }
}
