package co.com.s4n.training.java;

import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.None;

public class ExerciseTryClass {

    public static Try<String> nameOfNumber(int i){
        switch(i){
            case 1: return Try.of(()->"uno");
            case 2: return Try.of(()->"dos");
            case 3: return Try.of(()->"tres");
            case 4: return Try.of(()->"cuatro");
            case 5: return Try.of(()->"cinco");
            case 6: return Try.of(()->"seis");
            case 7: return Try.of(()->"siete");
            case 8: return Try.of(()->"ocho");
            case 9: return Try.of(()->"nueve");
            default: return Try.failure(new Exception("Custom Exception"));
        }

    }

    public static Try<String> addSize(String s){
        String res = s+ "-" + nameOfNumber(s.length()).getOrElse("None");
        System.out.println(res);
        return res.length()>10 ? Try.of(()->res) : Try.failure(new Exception("Custom Exception"));
    }

    public static Try<String> concatString(String a, String b){
        String res = a + " " + b;
        return res.length()<30 ? Try.of(()->res) : Try.failure(new Exception("Custom Exception"));
    }


    public static Try<String> removeFromSize(String s){
        int length = s.length()/2;
        Try<String> newS = Try.of(()->s.substring(0,length));
        return newS.isEmpty() ? Try.failure(new Exception("Custom Exception")) : newS;

    }

    public static Try<List> stringToList(String s){
        String[] array = s.split("");
        List<String> list = Arrays.asList(array);
        //io.vavr.collection.List<String> lis2 = io.vavr.collection.List.empty();
        //list.forEach(x -> lis2.push(x));
        //System.out.println(lis2);
        return Try.of(()->list);
    }

    public static Option<List> listFiltered(List<String> list){
        list = list.stream().filter(x -> x.contains("a")).collect(Collectors.toList());
        return Option.of(list);
    }

}
