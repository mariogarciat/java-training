package co.com.s4n.training.java.jdk;

import static org.junit.Assert.*;

import co.com.s4n.training.java.CollectablePerson;
import co.com.s4n.training.java.MyClass;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class CompletableFutureSuite {

    private void sleep(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e){
            System.out.println("Problemas durmiendo hilo");
        }
    }

    @Test
    public void t1() {

        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();


        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            Thread.sleep(300);

            completableFuture.complete("Hello");
            return null;
        });
            System.out.println(Thread.currentThread().getName());

        try {
            String s = completableFuture.get(500, TimeUnit.MILLISECONDS);
            assertEquals(s, "Hello");
        }catch(Exception e){
            assertTrue(false);
        }finally{
            executorService.shutdown();

        }

    }

    @Test
    public void t2(){
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            Thread.sleep(300);

            completableFuture.complete("Hello");
            return null;
        });

        try {
            String s = completableFuture.get(500, TimeUnit.MILLISECONDS);
            assertEquals(s, "Hello");
        }catch(Exception e){
            assertTrue(false);
        }finally{
            executorService.shutdown();
        }
    }

    @Test
    public void t3(){
        // Se puede construir un CompletableFuture a partir de una lambda Supplier (que no recibe parámetros pero sí tiene retorno)
        // con supplyAsync
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return "Hello";
        });

        try {
            String s = future.get(500, TimeUnit.MILLISECONDS);
            assertEquals(s, "Hello");
        }catch(Exception e){

            assertTrue(false);
        }
    }

    @Test
    public void t4(){

        int i = 0;
        // Se puede construir un CompletableFuture a partir de una lambda (Supplier)
        // con runAsync
        Runnable r = () -> {
            sleep(300);
            System.out.println("Soy impuro y no merezco existir");
        };

        // Note el tipo de retorno de runAsync. Siempre es un CompletableFuture<Void> asi que
        // no tenemos manera de determinar el retorno al completar el computo
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(r);

        try {
            voidCompletableFuture.get(500, TimeUnit.MILLISECONDS);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void t5(){

        String testName = "t5";

        System.out.println(testName + " - El test (hilo ppal) esta corriendo en: "+Thread.currentThread().getName());

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            imprimirMensaje(testName + " - completbleFuture corriendo en el thread: "+Thread.currentThread().getName());
            return "Hello";
        });

        //thenApply acepta lambdas de aridad 1 con retorno
        CompletableFuture<String> future = completableFuture
                .thenApply(s -> {
                    imprimirMensaje(testName + " - future corriendo en el thread: "+Thread.currentThread().getName());

                    return s + " World";
                })
                .thenApply(s -> {
                    imprimirMensaje(testName + " - future corriendo en el thread: "+Thread.currentThread().getName());

                    return s + "!";
                });

        try {
            assertEquals("Hello World!", future.get());
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testApplyAsync(){

        String testName = "t5Async";

        System.out.println(testName + " - El test (hilo ppal) esta corriendo en: "+Thread.currentThread().getName());

        ExecutorService es = Executors.newFixedThreadPool(2);

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            imprimirMensaje(testName + " - completbleFuture corriendo en el thread: "+Thread.currentThread().getName());
            return "Hello";
        });

        //thenApply acepta lambdas de aridad 1 con retorno
        CompletableFuture<String> futuree = completableFuture
                .thenApplyAsync(s -> {
                    imprimirMensaje(testName + " - future1 corriendo en el thread: "+Thread.currentThread().getName());
                    return s + " World1";
                },es)
                .thenApplyAsync(s -> {
                    imprimirMensaje(testName + " - future2 corriendo en el thread: "+Thread.currentThread().getName());
                    return s+" World2";
                },es);

        try {
            assertEquals("Hello World1 World2", futuree.get());
        }catch(Exception e){
            assertTrue(false);
        }
    }


    @Test
    public void t6(){

        String testName = "t6";

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
                    sleep(200);
            imprimirMensaje(testName + " - completbleFuture corriendo en el thread: "+Thread.currentThread().getName());
            return "Hello";
        });

        // thenAccept solo acepta Consumer (lambdas de aridad 1 que no tienen retorno)
        // analice el segundo thenAccept ¿Tiene sentido?
        CompletableFuture<Void> future = completableFuture
                .thenAccept(s -> {
                    imprimirMensaje(testName + " - future corriendo en el thread: " + Thread.currentThread().getName() + " lo que viene del futuro es: "+s);
                })
                .thenAccept(s -> {
                    imprimirMensaje(testName + " - future corriendo en el thread: " + Thread.currentThread().getName() + " lo que viene del futuro es: "+s);
                });

    }

    public void imprimirMensaje(String message){
        Date date = new Date();
        String format = "HH:mm:ss:SS";
        SimpleDateFormat sDate = new SimpleDateFormat(format);

        String out = sDate.format(date);
        System.out.println(out + " " + message+"-----------------------------");


    }

    @Test
    public void testPrint(){
        String message = "Hola mundo";
        imprimirMensaje(message);
    }

    @Test
    public void t7(){

        String testName = "t7";

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            imprimirMensaje(testName + " - completbleFuture corriendo en el thread: "+Thread.currentThread().getName());
            return "Hello";
        });

        //thenAccept solo acepta Consumer (lambdas de aridad 1 que no tienen retorno)
        CompletableFuture<Void> future = completableFuture
                .thenRun(() -> {
                    imprimirMensaje(testName + " - future corriendo en el thread: " + Thread.currentThread().getName());
                })
                .thenRun(() -> {
                    imprimirMensaje(testName + " - future corriendo en el thread: " + Thread.currentThread().getName());
                });

    }

    @Test
    public void t8(){

        String testName = "t8";

        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(testName + " - future corriendo en el thread: " + Thread.currentThread().getName());
                    return "Hello";
                })
                .thenCompose(s -> {
                    System.out.println(testName + " - compose corriendo en el thread: " + Thread.currentThread().getName());
                    return CompletableFuture.supplyAsync(() ->{
                        System.out.println(testName + " - CompletableFuture interno corriendo en el thread: " + Thread.currentThread().getName());
                        return s + " World"  ;
                    } );
                });

        try {
            assertEquals("Hello World", completableFuture.get());
        }catch(Exception e){
            assertTrue(false);
        }
    }



    @Test
    public void testPersona(){

        String testName = "tP";
        CompletableFuture<CollectablePerson> futurePerson = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(testName + " - future corriendo en el thread: " + Thread.currentThread().getName());
                    return "Mario.21";
                }).thenCompose(s -> {
                    System.out.println(testName + " - compose corriendo en el thread: " + Thread.currentThread().getName());
                    String[] array = s.split("\\.");
                    String name = array[0];
                    int age = Integer.parseInt(array[1]);
                    return CompletableFuture.supplyAsync(()->{
                        System.out.println(testName + " - CompletableFuture interno corriendo en el thread: " + Thread.currentThread().getName());
                        CollectablePerson persona = new CollectablePerson();
                        persona.addName(name);
                        persona.addAge(age);
                        return persona;
                    });

                });


        try {
            CollectablePerson person = futurePerson.get();
            assertEquals(" Mario", person.name);
            assertEquals(21, person.age);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void t9(){

        String testName = "t9";


        // El segundo parametro de thenCombina es un BiFunction la cual sí tiene que tener retorno.
        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> " World"),
                        (s1, s2) -> {
                            System.out.println(testName + " - future corriendo en el thread: " + Thread.currentThread().getName());
                            s1 = s1+s2;
                            s2 = s2+s1;
                            return s1+s2;
                        }
                );

        try {
            assertEquals("Hello World WorldHello World", completableFuture.get());
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void t10(){

        String testName = "t10";
        //thenAcceptBoth Usa Consumer
        // El segundo parametro de thenAcceptBoth debe ser un BiConsumer. No puede tener retorno.
        CompletableFuture future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(
                        CompletableFuture.supplyAsync(() -> " World"),
                        (s1, s2) -> System.out.println(testName + " corriendo en thread: "+Thread.currentThread().getName()+ " : " +s1 + s2));

        try{
            Object o = future.get();
        }catch(Exception e){
            assertTrue(false);

        }
    }

    @Test
    public void testEnlaceConSupplyAsync(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        CompletableFuture f = CompletableFuture.supplyAsync(()->"Hello",executorService);

        CompletableFuture f2 = f.supplyAsync(()->{
            imprimirMensaje("t11 ejecutando a");
            sleep(500);
            return "a";
        }).supplyAsync(()->{
            imprimirMensaje("t11 ejecutando b");
            return "b";
        });
        try {
            assertEquals(f2.get(),"b");
        }catch (Exception e){
            assertFalse(true);
        }
    }
    @Test
    public void testEnlaceConSupplyAsync2(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        CompletableFuture f = CompletableFuture.supplyAsync(()->"Hello",executorService);

        CompletableFuture f2 = f.supplyAsync(()->{
            imprimirMensaje("t11 2 ejecutando a");
            sleep(500);
            return "a";
        },executorService).supplyAsync(()->{
            imprimirMensaje("t11 2 ejecutando b");
            return "b";
        },executorService);
        try {
            assertEquals(f2.get(),"b");
        }catch (Exception e){
            assertFalse(true);
        }
    }

    @Test
    public void t11(){

        String testName = "t11";

        //Siempre crear executor cuando se trabaje con multihilado
        ExecutorService es = Executors.newFixedThreadPool(1);
        CompletableFuture f = CompletableFuture.supplyAsync(()->"Hello",es);

        f.supplyAsync(() -> "Hello")
                .thenCombineAsync(
                    CompletableFuture.supplyAsync(() -> {
                        System.out.println(testName + " thenCombineAsync en Thread (1): " + Thread.currentThread().getName());
                        return " World";
                    }),
                    (s1, s2) -> {
                        System.out.println(testName + " thenCombineAsync en Thread (2): " + Thread.currentThread().getName());
                        return s1 + s2;
                    },
                    es
                );

    }

}
