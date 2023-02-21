package com.business.app.repository;

import com.business.app.model.Redirect;
import com.business.app.model.RedirectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedirectRepository extends JpaRepository<Redirect, RedirectId> {

    boolean existsById(RedirectId redirectId);
}
