package com.rainlin.repository;

import com.rainlin.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  VisitorRepository extends JpaRepository<Visitor, Long> {
    Visitor findByIp(String ip);
}
