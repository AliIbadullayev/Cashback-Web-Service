package com.example.data.repository;

import com.example.data.model.Redirect;
import com.example.data.model.RedirectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedirectRepository extends JpaRepository<Redirect, RedirectId> {


    boolean existsById(RedirectId redirectId);
}
