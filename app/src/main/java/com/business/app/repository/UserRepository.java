package com.business.app.repository;

import com.business.app.model.Marketplace;
import com.business.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}