package com.easyexcel.demo.java8;

import lombok.*;
import org.junit.Test;

import java.util.Optional;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-23 17:20
 **/
public class Mail {

    @Test
    public void test1(){

        Student s1 = null;//new Student();

        Optional<Student> o1 = Optional.ofNullable(s1);
        System.out.println(o1);

        Student rs1 = Optional.ofNullable(s1).orElse(new Student("qita",32));
        System.out.println(rs1);

        Optional.ofNullable(s1).ifPresent(student -> System.out.println("s1 存在"));


        Student s2 =new Student("s2",null);

        Student rs2 = Optional.ofNullable(s2).orElse(new Student("qita",22));
        System.out.println(rs2);

        Integer integer = Optional.ofNullable(rs2.getAge()).orElse(0);
        System.out.println(integer);

    }
}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Student{
    private String name;
    private Integer age;
}