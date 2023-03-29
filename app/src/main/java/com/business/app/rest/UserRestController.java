package com.business.app.rest;

import com.example.data.dto.RedirectDto;
import com.example.data.dto.WithdrawDto;
import com.business.app.exception.IllegalAccessException;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotFoundUserException;
import com.business.app.security.JwtTokenProvider;
import com.business.app.service.*;
import com.example.data.model.Withdraw;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.data.model.User;

import java.util.Objects;


/**
 * Контроллер для запросов со стороны пользователя
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/users/")
public class UserRestController {

    private final RedirectService redirectService;

    private final PurchaseService purchaseService;

    private final UserService userService;

    private final WithdrawService withdrawService;

    private final JwtTokenProvider jwtTokenProvider;

    public UserRestController(RedirectService redirectService, PurchaseService purchaseService, UserService userService, WithdrawService withdrawService, JwtTokenProvider jwtTokenProvider) {
        this.redirectService = redirectService;
        this.purchaseService = purchaseService;
        this.userService = userService;
        this.withdrawService = withdrawService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("redirect")
    public ResponseEntity<?> addRedirect(@RequestBody RedirectDto redirectDto,
                                         HttpServletRequest request) throws NotFoundRedirectException, IllegalAccessException{
        if (!Objects.equals(jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.resolveToken(request)), redirectDto.getUserId()))
            throw new IllegalAccessException("У вас нет права на эту операцию");
        return new ResponseEntity<>(redirectService.addRedirect(redirectDto), HttpStatus.OK);
    }

    @GetMapping(value = "{username}/available-balance")
    public ResponseEntity<?> getAvailableBalance(@PathVariable(name = "username") String username,
                                                 HttpServletRequest request) throws NotFoundUserException, IllegalAccessException {

        if (!Objects.equals(jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.resolveToken(request)), username))
            throw new IllegalAccessException("У вас нет права на эту операцию");


        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getAvailableBalance(), HttpStatus.OK);
    }


    @GetMapping(value = "{username}/pending-balance")
    public ResponseEntity<?> getPendingBalance(@PathVariable(name = "username") String username,
                                               HttpServletRequest request) throws NotFoundUserException, IllegalAccessException {

        if (!Objects.equals(jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.resolveToken(request)), username))
            throw new IllegalAccessException("У вас нет права на эту операцию");
        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getPendingBalance(), HttpStatus.OK);
    }

    @GetMapping(value = "{username}/purchases")
    public ResponseEntity<?> getAllPurchases(@PathVariable(name = "username") String username,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             HttpServletRequest request) throws NotFoundUserException, IllegalAccessException {

        if (!Objects.equals(jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.resolveToken(request)), username))
            throw new IllegalAccessException("У вас нет права на эту операцию");
        User user = userService.getUser(username);
        return new ResponseEntity<>(purchaseService.getPurchasePage(user, page, size), HttpStatus.OK);
    }

    @PostMapping("withdraw")
    public String makeWithdraw(@RequestBody WithdrawDto withdrawDto,
                                          HttpServletRequest request) throws IllegalAccessException {
        if (!Objects.equals(jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.resolveToken(request)), withdrawDto.getUsername()))
            throw new IllegalAccessException("У вас нет права на эту операцию");
        log.info("Withdraw method called");
        return "Заявка на вывод отправлена. Её номер: " + withdrawService.sendWithdraw(withdrawDto);
    }


    @GetMapping("withdraw/{id}")
    public Withdraw getWithdraw(@PathVariable String id){
        return withdrawService.getWithdraw(id);
    }

}
