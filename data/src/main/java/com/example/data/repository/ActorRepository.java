package com.example.data.repository;

import com.example.data.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, String> {


}
