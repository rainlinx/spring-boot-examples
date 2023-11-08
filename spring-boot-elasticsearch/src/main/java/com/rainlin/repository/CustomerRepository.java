package com.rainlin.repository;

import com.rainlin.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author rainlin
 */
@Repository
public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
    List<Customer> findByAddress(String address);

    Customer findByUserName(String userName);

    int deleteByUserName(String userName);

    Page<Customer> findByAddress(String address, Pageable pageable);
}
