package co.com.s4n.training.java.jdk;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;

public class LambdaSuite {

    @FunctionalInterface
    interface InterfaceDeEjemplo{
        int metodoDeEjemplo(int x, int y);
    }

    class ClaseDeEjemplo{
        public int metodoDeEjemplo1(int z, InterfaceDeEjemplo i){
            return z + i.metodoDeEjemplo(1,2);
        }

        public int metodoDeEjemplo2(int z, BiFunction<Integer, Integer, Integer> fn){
            return z + fn.apply(1,2);
        }
    }

    @Test
    public void smokeTest() {
        assertTrue(true);
    }

    @Test
    public void usarUnaInterfaceFuncional1(){

        InterfaceDeEjemplo i = (x,y)->x+y;

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo1(1,i);

        assertTrue(resultado==4);
    }

    @Test
    public void usarUnaInterfaceFuncional3(){

        InterfaceDeEjemplo i = (x,y)->(x+y)*y;

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo1(1,i);

        assertTrue(resultado==7);
    }

    @Test
    public void usarUnaInterfaceFuncional2(){

        BiFunction<Integer, Integer, Integer> f = (x, y) -> new Integer(x.intValue()+y.intValue());

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo2(1,f);

        assertTrue(resultado==4);
    }

    @Test
    public void usarUnaInterfaceFuncional4(){

        BiFunction<Integer, Integer, Integer> f = (x, y) -> {
            Integer w = x *3;
            w = w/y;
            return w;
        };

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo2(1,f);

        assertTrue(resultado==2);
    }

    class ClaseDeEjemplo2{

        public int metodoDeEjemplo2(int x, int y, IntBinaryOperator fn){
            return fn.applyAsInt(x,y);
        }
    }
    @Test
    public void usarUnaFuncionConTiposPrimitivos(){
        IntBinaryOperator f = (x, y) -> x + y;

        ClaseDeEjemplo2 instancia = new ClaseDeEjemplo2();

        int resultado = instancia.metodoDeEjemplo2(1,2,f);

        assertEquals(3,resultado);
    }
    /*@Test
    public void usarUnaFuncionConTiposPrimitivos2(){
        IntBinaryOperator f = (x, y) -> x + y;

        ClaseDeEjemplo2 instancia = new ClaseDeEjemplo2();
        Double y2 = Double.valueOf(3);
        int resultado = instancia.metodoDeEjemplo2(1,y2,f);

        assertEquals(3,resultado);
    }*/

    class ClaseDeEjemplo3{

        public String operarConSupplier(Supplier<Integer> s){
            return "El int que me han entregado es: " + s.get();
        }
    }

    @Test
    public void usarUnaFuncionConSupplier(){
        Supplier s1 = () -> {
            System.out.println("Cuándo se evalúa esto? (1)");
            return 4;
        };

        Supplier s2 = () -> {
            System.out.println("Cuándo se evalúa esto? (2)");
            return 4;
        };

        ClaseDeEjemplo3 instancia = new ClaseDeEjemplo3();

        String resultado = instancia.operarConSupplier(s2);

        assertEquals("El int que me han entregado es: 4",resultado);
    }

    class ClaseDeEjemplo4{

        private int i = 0;

        public void operarConConsumer(Consumer<Integer> c){
            c.accept(i);
        }
    }

    class ClaseDeEjemplo5{


        public void operarConConsumer(Consumer<Integer> c, Integer i){
            c.accept(i);
        }
    }

    @Test
    public void usarUnaFuncionConConsumer(){
        Consumer<Integer> c1 = x -> {
            System.out.println("Me han entregado este valor: "+x);
        };

        ClaseDeEjemplo4 instancia = new ClaseDeEjemplo4();

        instancia.operarConConsumer(c1);


    }
    @Test
    public void usarUnaFuncionConConsumer2(){
        Consumer<Integer> c2 = x -> {
            System.out.println("Me han entregado este valor: "+x);
        };

        ClaseDeEjemplo5 instancia = new ClaseDeEjemplo5();

        instancia.operarConConsumer(c2,7);


    }

    @FunctionalInterface
    interface InterfaceEjercicio{
        Consumer<Integer> suppliersToConsumer(Supplier<Integer> a,Supplier<Integer> b,Supplier<Integer> c);
    }


    @Test
    public void pruebaEjercicio(){

        InterfaceEjercicio i = (s1,s2,s3)-> {

            Consumer<Integer> cEj = d -> {
                Integer suma = s1.get()+s2.get()+s3.get()+d;
                System.out.println("Consumer-->"+suma);
            };
            return cEj;
        };

        Supplier s1 = ()-> 1;
        Supplier s2 = ()-> 2;
        Supplier s3 = ()-> 3;

        Consumer<Integer> consumer = i.suppliersToConsumer(s1,s2,s3);
        consumer.accept(new Integer(9));
    }

    @FunctionalInterface
    interface InterfaceEjercicio2{
        Consumer<Integer> suppliersToConsumer(Supplier<Integer> a,Supplier<Integer> b,Supplier<Integer> c);
    }

}
