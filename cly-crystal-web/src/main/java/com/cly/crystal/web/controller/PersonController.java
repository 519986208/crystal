package com.cly.crystal.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cly.crystal.share.service.PersonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PersonController {

    @Resource
    private PersonService personService;

    @RequestMapping("test")
    public Object list() {
        log.error("111111111111111");
        return personService.list();
    }

}
