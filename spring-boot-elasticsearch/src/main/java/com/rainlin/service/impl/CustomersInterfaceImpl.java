package com.rainlin.service.impl;

import com.rainlin.model.Customer;
import com.rainlin.repository.CustomerRepository;
import com.rainlin.service.CustomersInterface;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rainlin
 */
@Service
public class CustomersInterfaceImpl implements CustomersInterface {

    private CustomerRepository customerRepository;
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public CustomersInterfaceImpl(CustomerRepository customerRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.customerRepository = customerRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        // 分页参数
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.matchQuery("address", searchContent))
                .build();
        return elasticsearchRestTemplate
                .search(query, Customer.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
