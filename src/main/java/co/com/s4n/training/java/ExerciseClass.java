package co.com.s4n.training.java;

import io.vavr.control.Option;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

import static io.vavr.API.None;

public class ExerciseClass {

    public static Option<String> create(String name, int number){


        Option<String> res = Option.of(String.valueOf(number) + " - " + name);
        System.out.println(res.getOrElse("NONE"));
        return res;
    }

    public static Option<String> nameOfNumber(int i){
        switch(i){
            case 1: return Option.of("uno");
            case 2: return Option.of("dos");
            case 3: return Option.of("tres");
            case 4: return Option.of("cuatro");
            case 5: return Option.of("cinco");
            case 6: return Option.of("seis");
            case 7: return Option.of("siete");
            case 8: return Option.of("ocho");
            case 9: return Option.of("nueve");
            default: return None();
        }
    }

    public static Option<String> addSize(String s){
        String res = s+ "-" + nameOfNumber(s.length()).getOrElse("None");
        System.out.println(res);
        return res.length()>10 ? Option.of(res) : None();
    }

    public static Option<String> concatString(String a, String b){
        String res = a + " " + b;
        return res.length()<30 ? Option.of(res) : None();
    }


    public static Option<String> removeFromSize(String s){
        int length = s.length()/2;
        Option<String> newS = Option.of(s.substring(0,length));
        return newS.isEmpty() ? None() : newS;

    }

    public static Option<List> stringToList(String s){
        String[] array = s.split("");
        List<String> list = Arrays.asList(array);
        //io.vavr.collection.List<String> lis2 = io.vavr.collection.List.empty();
        //list.forEach(x -> lis2.push(x));
        //System.out.println(lis2);
        return Option.of(list);
    }

    public static Option<List> listFiltered(List<String> list){
        list = list.stream().filter(x -> x.contains("a")).collect(Collectors.toList());
        return Option.of(list);
    }






}
