package com.business.app.repository;

import com.business.app.model.Marketplace;
import com.business.app.model.Redirect;
import com.business.app.model.RedirectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

public interface RedirectRepository extends JpaRepository<Redirect, RedirectId> {

    boolean existsById(RedirectId redirectId);
}
