package com.cly.crystal.datasource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cly.crystal.share.bean.Person;

//@Mapper
public interface PersonDao {

    //    @Select("select * from person where name = #{name}")
    Person findUserByName(@Param("name") String name);

    List<Person> findAll();

    List<Person> findUserPage();

}
