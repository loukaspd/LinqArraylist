package loukaspd.gr.linqarraylistproject;

import java.util.*;

public class LinqArrayList<E> {
    // ---------- Class variables - Constructor ---------- //
    private final Collection<E> _collection;
    public ArrayList<E> getArrayList() {
        if (_collection instanceof ArrayList) return (ArrayList<E>) _collection;
        return new ArrayList<>(_collection);
    }
    public Collection<E> getCollection() {return _collection;}

    public LinqArrayList(Collection<E> collection) {
        _collection = collection;
    }


    // ---------- Interfaces ---------- //
    public interface VoidFunc<E> {
        void lambda(E item);
    }
    public interface BooleanFunc<E> {
        boolean lambda(E item);
    }
    public interface SelectFunc<E,T> {
        T lambda(E item);
    }
    public interface SelectManyFunc<E,T> {
        Collection<T> lambda(E item);
    }
    public interface SumFunc<E> {
        double lambda(E item);
    }



    // ---------- Methods ---------- //
    public void forEach(VoidFunc<E> func) {
        Enumeration<E> enumeration = Collections.enumeration(_collection);

        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            func.lambda(item);
        }
    }

    public E first(BooleanFunc<E> func){
        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            if (func.lambda(item)) return item;
        }
        throw new IllegalStateException();
    }

    public E firstOrDefault(BooleanFunc<E> func){
        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            if (func.lambda(item)) return item;
        }
        return null;
    }

    public LinqArrayList<E> where(BooleanFunc<E> func) {
        ArrayList<E> result = new ArrayList<E>();
        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            if (func.lambda(item)) result.add(item);
        }
        return new LinqArrayList<E>(result);
    }

    public <T> LinqArrayList<T> select(SelectFunc<E,T> func) {
        ArrayList<T> result = new ArrayList<T>();

        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            result.add(func.lambda(item));
        }
        return new LinqArrayList<T>(result);
    }

    public <T> LinqArrayList<T> selectMany(SelectManyFunc<E,T> func) {
        ArrayList<T> result = new ArrayList<T>();

        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            Collection<T> collection = func.lambda(item);
            if (collection == null) continue;
            result.addAll(collection);
        }

        return new LinqArrayList<T>(result);
    }

    public boolean any(BooleanFunc<E> func) {
        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            if (func.lambda(item)) return true;
        }
        return false;
    }

    public boolean remove(BooleanFunc<E> func) {
        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            if (func.lambda(item)) {
                _collection.remove(item);
                return true;
            }
        }
        return false;
    }

    public LinqArrayList<E> distinct() {
        ArrayList<E> result = new ArrayList<>();

        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            if(!result.contains(item)) {
                result.add(item);
            }
        }
        return new LinqArrayList<>(result);
    }

    public double sum(SumFunc<E> func) {
        double result = 0;

        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            result += func.lambda(item);
        }
        return result;
    }

    public <T> Hashtable<T,ArrayList<E>> groupBy(SelectFunc<E,T> func) {
        Hashtable<T,ArrayList<E>> dictionary = new Hashtable<T,ArrayList<E>>();

        Enumeration<E> enumeration = Collections.enumeration(_collection);
        while(enumeration.hasMoreElements()) {
            E item = enumeration.nextElement();

            T key = func.lambda(item);
            if (dictionary.containsKey(key)) dictionary.get(key).add(item);
            else {
                ArrayList<E> group = new ArrayList<>();
                group.add(item);
                dictionary.put(key, group);
            }
        }

        return dictionary;
    }
}
