package com.business.app.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для запросов со стороны платежной системы (для вывода средств)
 * */
@RestController
@RequestMapping(value = "/api/acquire/")
public class ThirdPartyAcquireRestController {
}
