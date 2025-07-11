package com.example.salesdashboard.repository;

import com.example.salesdashboard.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByUserEmail(String userEmail);

    @Override
    <S extends Sale> List<S> saveAll(Iterable<S> entities);
}
