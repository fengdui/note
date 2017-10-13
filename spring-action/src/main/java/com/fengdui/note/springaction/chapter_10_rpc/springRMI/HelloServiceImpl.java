package com.fengdui.note.springaction.chapter_10_rpc.springRMI;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author FD
 * @date 2017/1/20
 */
@Transactional
@Service("fd")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayYourAge(int age) {
        return null;
    }

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
