package com.example.invite;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class LamdaTest {

    @Test
    public void Testing(){
        ArrayList<Integer> list = new ArrayList<>();
        Consumer<Collection<Integer>> addElements;
        list.forEach(System.out::println);
        addElements = list::addAll;
        System.out.println(addElements);
    }
}
