package co.com.s4n.training.java.vavr;

import io.vavr.Lazy;
import io.vavr.concurrent.Future;
import org.junit.Test;

import java.util.function.Supplier;

import static java.lang.Thread.sleep;

public class LazySuite {

    @Test
    public void futureExerciseLazy(){
        Lazy<Future<Integer>> f1 = Lazy.of(()->Future.of(()->{
            sleep(500);
            return 4;
        }));

        Lazy<Future<Integer>> f2 = Lazy.of(()->Future.of(()->{
            sleep(800);
            return 5;
        }));
        Lazy<Future<Integer>> f3 = Lazy.of(()->Future.of(()->{
            sleep(300);
            return 6;
        }));
        long inicio = System.nanoTime();


        Future<Integer> res = f1.get()
                .flatMap(a -> f2.get()
                        .flatMap(b -> f3.get()
                                .flatMap(c -> Future.of(()->a+b+c))));

        System.out.println("Resultado de ejercicio: "+res.get());
        long fin = System.nanoTime();
        long elapsed = fin - inicio;
        System.out.println("Tiempo en mili de lazy: "+elapsed*Math.pow(10,-6));
    }

    @Test
    public void futureExerciseLazyMemoized(){
        Lazy<Future<Integer>> f1 = Lazy.of(()->Future.of(()->{
            sleep(500);
            return 4;
        }));

        Lazy<Future<Integer>> f2 = Lazy.of(()->Future.of(()->{
            sleep(800);
            return 5;
        }));
        Lazy<Future<Integer>> f3 = Lazy.of(()->Future.of(()->{
            sleep(300);
            return 6;
        }));
        long inicio = System.nanoTime();


        Future<Integer> res = f1.get()
                .flatMap(a -> f2.get()
                        .flatMap(b -> f3.get()
                                .flatMap(c -> Future.of(()->a+b+c))));

        System.out.println("Resultado de ejercicio : "+res.get());
        long fin = System.nanoTime();
        long elapsed = fin - inicio;
        System.out.println("Tiempo en mili de lazy : "+elapsed*Math.pow(10,-6));
        long inicio2 = System.nanoTime();


        Future<Integer> res2 = f1.get()
                .flatMap(a -> f2.get()
                        .flatMap(b -> f3.get()
                                .flatMap(c -> Future.of(()->a+b+c))));

        System.out.println("Resultado de ejercicio memoized: "+res2.get());
        long fin2 = System.nanoTime();
        long elapsed2 = fin2 - inicio2;
        System.out.println("Tiempo en mili de lazy memoized: "+elapsed2*Math.pow(10,-6));
    }

    @Test
    public void testCompareSuplierFuture(){


        Lazy<Future<Integer>> lazy = Lazy.of(()->Future.of(()->{
            sleep(700);
            return 4;
        }));

        long inicioLazy = System.nanoTime();
        Future<Integer> res1 = lazy.get();
        res1.await();
        long finLazy = System.nanoTime();
        long elapsedLazy = finLazy - inicioLazy;
        System.out.println("Primer Lazy: "+elapsedLazy*Math.pow(10,-6));

        long inicioLazy2 = System.nanoTime();
        Future<Integer> res2 = lazy.get();
        res2.await();
        long finLazy2 = System.nanoTime();
        long elapsedLazy2 = finLazy2 - inicioLazy2;
        System.out.println("Segundo Lazy: "+elapsedLazy2*Math.pow(10,-6));


        Supplier<Integer> supplier = () -> {
            try {
                sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 7;
        };

        long inicioSupplier = System.nanoTime();
        supplier.get();
        long finSupplier = System.nanoTime();
        long elapsedSupplier = finSupplier - inicioSupplier;
        System.out.println("Primer Supplier: "+elapsedSupplier*Math.pow(10,-6));

        long inicioSupplier2 = System.nanoTime();
        supplier.get();
        long finSupplier2 = System.nanoTime();
        long elapsedSupplier2 = finSupplier2 - inicioSupplier2;
        System.out.println("Segundo Suppler: "+elapsedSupplier2*Math.pow(10,-6));

    }
}
