package com.example.Consumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikimediaChangeRepository extends JpaRepository<WikimediaChange, Long> {
}
