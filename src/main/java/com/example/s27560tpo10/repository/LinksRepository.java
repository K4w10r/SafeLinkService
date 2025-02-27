package com.example.s27560tpo10.repository;

import com.example.s27560tpo10.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinksRepository extends CrudRepository<Link, String> {
}
