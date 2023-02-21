package com.business.app.service;

import com.business.app.dto.RedirectDto;
import com.business.app.exception.RedirectAddException;
import com.business.app.model.Marketplace;
import com.business.app.model.Redirect;
import com.business.app.model.RedirectId;
import com.business.app.model.User;
import com.business.app.repository.MarketplaceRepository;
import com.business.app.repository.RedirectRepository;
import com.business.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RedirectService {
    @Autowired
    RedirectRepository redirectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MarketplaceRepository marketplaceRepository;

    public Redirect addRedirect(RedirectDto redirectDto) throws RedirectAddException {
        Redirect redirect = new Redirect();
        Optional<User> user = userRepository.findById(redirectDto.getUserId());
        Optional<Marketplace> marketplace = marketplaceRepository.findById(redirectDto.getMarketplaceId());


        if (user.isPresent() && marketplace.isPresent()){
            RedirectId redirectId = new RedirectId(user.get(), marketplace.get());
            redirect.setPk(redirectId);
            redirect.setTime(Timestamp.valueOf(LocalDateTime.now()));
            redirectRepository.save(redirect);
            return redirect;
        }
        else {
            throw new RedirectAddException("Not correct user or marketplace");
        }
    }




}
