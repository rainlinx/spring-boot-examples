package com.rainlin.repository;

import com.rainlin.model.Customer;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void saveCustomers() {
        repository.save(new Customer("Alice", "北京", 13));
        repository.save(new Customer("Bob", "北京", 23));
        repository.save(new Customer("neo", "西安", 30));
        repository.save(new Customer("summer", "烟台", 22));
    }

    @Test
    public void fetchAllCustomers() {
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        Iterable<Customer> iterable = repository.findAll();
        for (Customer customer : iterable) {
            System.out.println(customer);
        }
    }

    @Test
    public void deleteCustomers() {
        repository.deleteAll();
        //        repository.deleteByUserName("neo");
    }

    @Test
    public void updateCustomers() {
        Customer customer = repository.findByUserName("summer");
        System.out.println(customer);
        customer.setAddress("北京市海淀区西直门");
        repository.save(customer);
        customer = repository.findByUserName("summer");
        System.out.println(customer);
    }

    @Test
    public void fetchIndividualCustomers() {
        System.out.println("Customer found with findByUserName('summer'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByUserName("summer"));
        System.out.println("--------------------------------");
        System.out.println("Customers found with findByAddress(\"北京\"):");
        String q = "北京";
        for (Customer customer : repository.findByAddress(q)) {
            System.out.println(customer);
        }
    }

    @Test
    public void fetchPageCustomers() {
        System.out.println("Customers found with fetchPageCustomers:");
        System.out.println("-------------------------------");
        Sort sort = Sort.by(Sort.Direction.DESC, "address.keyword");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Customer> customers = repository.findByAddress("北京", pageable);
        System.out.println("Page customers " + customers.getContent());
    }

    @Test
    public void fetchPage2Customers() {
        System.out.println("Customers found with fetchPageCustomers:");
        System.out.println("-------------------------------");
        QueryBuilder customerQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("address", "北京"));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 10))
                .withQuery(customerQuery)
                .build();

        List<Customer> customers = elasticsearchRestTemplate.search(query, Customer.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println("Page customers " + customers);

        query = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(1, 10))
                .withQuery(customerQuery)
                .build();
        customers = elasticsearchRestTemplate.search(query, Customer.class)
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println("Page customers " + customers);
    }

    @Test
    public void fetchAggregation() {
        System.out.println("Customers found with fetchAggregation:");
        System.out.println("-------------------------------");

        QueryBuilder customerQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("address", "北京"));

        SumAggregationBuilder sumBuilder = AggregationBuilders.sum("sumAge").field("age");

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(customerQuery)
                .addAggregation(sumBuilder)
                .build();

        Aggregations aggregations = elasticsearchRestTemplate.search(query, Customer.class).getAggregations();

        //获得对应的聚合函数的聚合子类，该聚合子类也是个map集合,里面的value就是桶Bucket，我们要获得Bucket
        final Aggregation sumAge = Optional.ofNullable(aggregations)
                .orElseThrow(() -> new RuntimeException("没有聚合数据"))
                .get("sumAge");
        System.out.println("sum age is " + ((Sum) sumAge).getValue());
    }

}
