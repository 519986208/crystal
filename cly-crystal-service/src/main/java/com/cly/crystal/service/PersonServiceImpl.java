package com.cly.crystal.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.cly.crystal.datasource.dao.PersonDao;
import com.cly.crystal.share.bean.Person;
import com.cly.crystal.share.service.PersonService;
import com.github.pagehelper.PageHelper;

@Service
@RestController
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonDao personDao;

    @Override
    public List<Person> list() {
        PageHelper.startPage(1, 20);//分页插件
        List<Person> findAll = personDao.findAll();
        return findAll;
    }

}
