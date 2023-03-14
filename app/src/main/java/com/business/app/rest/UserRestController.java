package com.business.app.rest;

import com.business.app.dto.RedirectDto;
import com.business.app.dto.WithdrawDto;
import com.business.app.exception.NotFoundUserException;
import com.business.app.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.data.model.User;

/**
 * Контроллер для запросов со стороны пользователя
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/users/")
public class UserRestController {

    @Autowired
    RedirectService redirectService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    UserService userService;

    @Autowired
    WithdrawService withdrawService;


    @PostMapping("redirect")
    public ResponseEntity<?> addRedirect(@RequestBody RedirectDto redirectDto,
                                         HttpServletRequest request) {
        log.info("Redirect method called with url: {}", request.getRequestURL());
        return new ResponseEntity<>(redirectService.addRedirect(redirectDto, request.getRequestURL().toString()), HttpStatus.OK);
    }

    @GetMapping(value = "{username}/available-balance")
    public ResponseEntity<?> getAvailableBalance(@PathVariable(name = "username") String username) throws NotFoundUserException {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getAvailableBalance(), HttpStatus.OK);
    }


    @GetMapping(value = "{username}/pending-balance")
    public ResponseEntity<?> getPendingBalance(@PathVariable(name = "username") String username) throws NotFoundUserException {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getPendingBalance(), HttpStatus.OK);
    }

    @GetMapping(value = "{username}/purchases")
    public ResponseEntity<?> getAllPurchases(@PathVariable(name = "username") String username,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size) throws NotFoundUserException {
        User user = userService.getUser(username);

        return new ResponseEntity<>(purchaseService.getPurchasePage(user, page, size), HttpStatus.OK);
    }

    @PostMapping("withdraw")
    public ResponseEntity<?> makeWithdraw(@RequestBody WithdrawDto withdrawDto,
                                          HttpServletRequest request) {
        log.info("Withdraw method called with url: {}", request.getRequestURL());
        return new ResponseEntity<>(withdrawService.sendWithdraw(withdrawDto, request.getRequestURL().toString()), HttpStatus.OK);
    }

}
