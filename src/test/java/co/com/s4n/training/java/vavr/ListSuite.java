package co.com.s4n.training.java.vavr;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static io.vavr.collection.Iterator.empty;
import static org.junit.Assert.*;


/**
 *  Getting started de la documentacion de vavr http://www.vavr.io/vavr-docs/#_collections
 *  Javadoc de vavr collections https://static.javadoc.io/io.vavr/vavr/0.9.0/io/vavr/collection/package-frame.html
 */

public class ListSuite {

    /**
     * Lo que sucede cuando se intenta crear un lista de null
     */
    @Test(expected = NullPointerException.class)
    public void testListOfNull() {
        List<String> list1 = List.of(null);
        list1.get();
    }

    /**
     * Lo que sucede cuando se crea una lista vacía y se llama un método
     */
    @Test
    public void testZipOnEmptyList() {
        List<String> list = List.of();
        assertTrue("Failure - List should be empty",list.isEmpty());
        List<Tuple2<String, Object>> zip = list.zip(empty());
        System.out.println("zip with empty size: "+zip.size());
        assertTrue(zip.size()==0);
    }

    @Test
    public void testingZip(){
        List<Integer> l1 = List.of(1,2,3);
        List<Integer> l2= List.of(1,2,3);
        List<Tuple2<Integer, Integer>> zip = l1.zip(l2);
        System.out.println("zip: "+zip);
        assertEquals(zip.headOption().getOrElse(new Tuple2(0,0)),new Tuple2(1,1));

    }

    @Test
    public void testingZip2(){
        List<Integer> l1 = List.of(1,2,3);
        List<Integer> l2= List.of(1,2,3,4);
        List<Tuple2<Integer, Integer>> zip = l1.zip(l2);
        System.out.println("zip: "+zip);
        assertEquals(zip.headOption().getOrElse(new Tuple2(0,0)),new Tuple2(1,1));

    }

    @Test
    public void testHead(){
        List<Integer> list1 = List.of(1,2,3);
        Integer head = list1.head();//Siempre toma el primer elemento
        assertEquals(head, new Integer(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void testHead2(){
        List<Integer> list1 = List.of();
        Integer head = list1.head();//Siempre toma el primer elemento

        assertEquals(head,null);
    }

    @Test
    public void testHead3(){
        List<Integer> list1 = List.of();
        Option<Integer> head = list1.headOption();//Siempre toma el primer elemento

        assertEquals(Option.none(),head);
    }

    @Test
    public void testHead4(){
        List<Integer> list1 = List.of(1,2,3);
        Option<Integer> head = list1.headOption();//Siempre toma el primer elemento

        assertEquals(new Integer(1),head.getOrElse(666));
    }

    @Test
    public void testTail(){
        List<Integer> list1 = List.of(1,2,3);
        List<Integer> expectedTail = List.of(2,3);
        List<Integer> tail = list1.tail();//Toma todo menos el primero elemento
        assertEquals(tail, expectedTail);
    }

    @Test
    public void testTail2(){
        List<Integer> list1 = List.of(1);
        List<Integer> expectedTail = List.of();
        List<Integer> tail = list1.tail();//Si tiene un elemento, tail devuelve lista vacía
        assertEquals(tail, expectedTail);
    }


    @Test
    public void testZip(){
        List<Integer> list1 = List.of(1,2,3);
        List<Integer> list2 = List.of(1,2,3);
        List<Tuple2<Integer, Integer>> zippedList = list1.zip(list2);
        assertEquals(zippedList.head(), Tuple.of(new Integer(1), new Integer(1)) );
        assertEquals(zippedList.tail().head(), Tuple.of(new Integer(2), new Integer(2)) );
    }

    /**
     * Una Lista es inmutable
     */
    @Test
    public void testListIsImmutable() {
        List<Integer> list1 = List.of(0, 1, 2);
        List<Integer> list2 = list1.map(i -> i);
        assertEquals(List.of(0, 1, 2),list1);
        assertNotSame(list1,list2);
    }

    public String nameOfNumber(int i){
        switch(i){
            case 1: return "uno";
            case 2: return "dos";
            case 3: return "tres";
            default: return "idk";
        }
    }

    @Test
    public void testMap(){

        List<Integer> list1 = List.of(1, 2, 3);
        List<String> list2 = list1.map(i -> nameOfNumber(i));

        assertEquals(list2, List.of("uno", "dos", "tres"));
        assertEquals(list1, List.of(1,2,3));

    }


    @Test
    public void testFilter(){
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> filteredList = list.filter(i -> i % 2 == 0);
        assertTrue(filteredList.get(0)==2);

    }


    /**
     * Se revisa el comportamiento cuando se pasa un iterador vacío
     */
    @Test
    public void testZipWhenEmpty() {
        List<String> list = List.of("I", "Mario's", "Please", "me");
        List<Tuple2<String, Integer>> zipped = list.zip(empty());
        assertTrue("Failure - The list should be empty",zipped.isEmpty());
    }

    /**
     * Se revisa el comportamiento cuando se pasa el iterador de otra lista
     */
    @Test
    public void testZipWhenNotEmpty() {
        List<String> list1 = List.of("I", "Mario's", "Please", "me", ":(");
        List<String> list2 = List.of("deleted", "test", "forgive", "!");
        List<Tuple2<String, String>> zipped2 = list1.zip(list2.iterator());
        List<Tuple2<String, String>> expected2 = List.of(Tuple.of("I", "deleted"), Tuple.of("Mario's", "test"),
                Tuple.of("Please", "forgive"), Tuple.of("me", "!"));
        assertEquals("Failure - The list wasn't match correctly.",expected2,zipped2);
    }

    /**
     * El zipWithIndex agrega numeración a cada item
     */
    @Test
    public void testZipWithIndex() {
        List<String> list = List.of("A", "B", "C");
        List<Tuple2<String, Integer>> expected = List.of(Tuple.of("A", 0), Tuple.of("B", 1), Tuple.of("C", 2));
        assertEquals("Failure - The list doesn't have the correct index.",expected,list.zipWithIndex());
    }

    /**
     *  pop y push por defecto trabajan para las pilas.
     */
    @Test
    public void testListStack() {
        List<String> list = List.of("B", "A");

        assertEquals("Failure pop does not drop the first element of the his list",
                List.of("A"), list.pop());

        assertEquals("Failure push does not add the element as the first in his list",
                List.of("D", "C", "B", "A"), list.push("C", "D"));

        assertEquals("Failure push does not add the element as the first in his list",
                List.of("C", "B", "A"), list.push("C"));

        assertEquals("Failure it's a lie first in last out",
                List.of("B", "A"), list.push("C").pop());

        assertEquals("Failure don't return the correct tuple",
                Tuple.of("B", List.of("A")), list.pop2());
    }

    @Test(expected = NoSuchElementException.class)
    public void popEmpty(){
        List<Object> l1 = List.of();
        List<Object> l2 = l1.pop();
        assertEquals(l2,empty());
    }
    @Test
    public void popEmptyOption(){
        List<Object> l1 = List.of();
        Option<List<Object>> l2 = l1.popOption();
        assertEquals(l2,Option.none());
    }

    @Test
    public void popAndTail(){
        List<Integer> l1 = List.of(1,2,3);
        List<Integer> l2 = l1.pop();
        List<Integer> l3 = l1.tail();
        assertEquals(l2,l3);
    }
    @Test
    public void testPop2(){
        List<Object> l1 = List.of(1,2,3,4,5);
        Tuple2 t = l1.pop2();
        assertEquals(Tuple.of(1,List.of(2,3,4,5)),t);
    }

    @Test(expected = NoSuchElementException.class)
    public void testPop2Empty(){
        List<Object> l1 = List.of();
        Tuple2 t = l1.pop2();
        assertEquals(empty(),t);//No llega a esta línea
    }

    /**
     * Una lista de vavr se comporta como una pila ya que guarda y
     * retorna sus elementos como LIFO.
     * Peek retorna el ultimo elemento en ingresar en la lista
     */
    @Test
    public void testLIFORetrieval() {
        List<String> list = List.empty();
        //Because vavr List is inmutable, we must capture the new list that the push method returns
        list = list.push("a");
        list = list.push("b");
        list = list.push("c");
        list = list.push("d");
        list = list.push("e");
        assertEquals("The list did not behave as a stack", List.of("d", "c", "b", "a"), list.pop());
        assertEquals("The list did not behave as a stack", "e", list.peek());
    }

    /**
     * Una lista puede ser filtrada dado un prediacado y el resultado
     * es guardado en una tupla
     */
    @Test
    public void testSpan() {
        List<String> list = List.of("a", "b", "c");
        Tuple2<List<String>, List<String>> tuple = list.span(s -> s.equals("a"));
        assertEquals("The first element of the tuple did not match", List.of("a"), tuple._1);
        assertEquals("The second element of the tuple did not match", List.of("b", "c"), tuple._2);
    }


    /**
     * Validar dos listas con la funcion Takewhile con los predicados el elemento menor a ocho y el elemento mayor a dos
     */
    @Test
    public void testListToTakeWhile() {
        List<Integer> myList = List.ofAll(4, 6, 8, 5);
        List<Integer> myListOne = List.ofAll(2, 4, 3);
        List<Integer> myListRes = myList.takeWhile(j -> j < 8);
        List<Integer> myListResOne = myListOne.takeWhile(j -> j > 2);
        assertTrue("List with values less than eight", myListRes.nonEmpty());
        assertEquals("List with length of two", 2, myListRes.length());
        assertEquals("List with last value six", new Integer(6), myListRes.last());
        assertTrue("List with values greater than two", myListResOne.isEmpty());
    }


    @Test
    public void testFold(){
        List<Integer> l1 = List.of(1,2,3,4,5);
        Integer r = l1.fold(0,(acc, el) -> acc + el);
        assertEquals(r.intValue(),15);
    }

    @Test
    public void testFold2(){
        List<String> l1 = List.of("abc","def","ghi");
        String r = l1.foldLeft("",(acc, el) -> acc + el);
        String r2 = l1.foldRight("",(el,acc) -> acc + el);
        //assertEquals(r.intValue(),15);
        System.out.println(r);
        System.out.println(r2);
        assertNotEquals(r,r2);
    }

    /**
     * Se puede separar una lista en ventanas de un tamaño especifico
     */
    @Test
    public void testSliding(){
        List<String> list = List.of(
                "First",
                "window",
                "!",
                "???",
                "???",
                "???");
        assertEquals("Failure - the window is incorrect",List.of("First","window","!"),list.sliding(3).head());
    }

    /**
     * Al dividir una lista en ventanas se puede especificar el tamaño del salto antes de crear la siguiente ventana
     */
    @Test
    public void testSlidingWithExplicitStep(){
        List<String> list = List.of(
                "First",
                "window",
                "!",
                "Second",
                "window",
                "!");
        List<List<String>> windows = list.sliding(3,3).toList(); // Iterator -> List
        assertEquals("Failure - the window is incorrect",
                List.of("Second","window","!"),
                windows.get(1));
        List<List<String>> windows2 = list.sliding(3,1).toList(); // Iterator -> List
        assertEquals("Failure - the window is incorrect",
                List.of("window","!","Second"),
                windows2.get(1));
    }
}