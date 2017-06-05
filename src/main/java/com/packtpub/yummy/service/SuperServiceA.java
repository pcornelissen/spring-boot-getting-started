package com.packtpub.yummy.service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class SuperServiceA implements SuperService {

    @Value("Can you read me")
    public String read;
    @Value("${foo:default value}")
    public String read1;
    @Value("${foo:${test}}")
    public String read2;
    @Value("${foo:${bar:oh no!}}")
    public String read3;

    @Value("#{myConfiguration.fraction}")
    public String bean1;
    @Value("#{myConfiguration.key.charAt(3)}")
    public String bean2;
    @Value("#{dataSource.createPool().closed}")
    public String bean3;
    @Value("#{environment['user.home']}")
    public String bean4;
    @Value("#{myConfiguration.links[1]}")
    public URL bean5;

    @Value("#{(10^2 > 10)?'true!':'falsy'}")
    public String calc;
    @Value("#{'hakuna matata' matches '[a-zA-Z\\s]+'}")
    public Boolean match;
    @Value("#{{1,2,3,4,5}}")
    public List<Integer> list;
    @Value("#{{'key':'value','key1':33}}")
    public Map map;

    @Value("#{{1,2,3,4,5}.?[#this >2]}")
    public List<Integer> listFiltered;
    @Value("#{{1,2,3,4,5}.^[#this >2]}")
    public List<Integer> listFilteredFirst;
    @Value("#{{1,2,3,4,5}.$[#this >2]}")
    public List<Integer> listFilteredLast;
    @Value("#{{1,2,3,4,5}.![#this+'.']}")
    public List<String> listProjected;

    @Override
    public void foo() {
        System.out.println("###############################");
        System.out.println(this.toString());
        System.out.println("###############################");
        System.exit(0);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
