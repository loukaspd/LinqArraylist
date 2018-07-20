package loukaspd.gr.linqarraylistproject;

import java.util.ArrayList;
import java.util.Collection;

public class LinqArrayList<E> {
    // ---------- Class variables - Constructor ---------- //
    private final ArrayList<E> _arrayList;
    public ArrayList<E> getArrayList() {
        return _arrayList;
    }

    public LinqArrayList(ArrayList<E> arrayList){
        _arrayList = arrayList;
    }
    public LinqArrayList(Collection<E> collection) {
        _arrayList = new ArrayList<E>(collection);
    }


    // ---------- Interfaces ---------- //
    public interface VoidFunc<E> {
        void lambda(E item);
    };
    public interface BooleanFunc<E> {
        boolean lambda(E item);
    };
    public interface SelectFunc<E,T> {
        T lambda(E item);
    };
    public interface SumFunc<E> {
        double lambda(E item);
    };



    // ---------- Methods ---------- //
    public void forEach(VoidFunc<E> func) {
        for(E item : _arrayList) {
            func.lambda(item);
        }
    }

    public E first(BooleanFunc<E> func){
        for(E item : _arrayList) {
            if (func.lambda(item)) return item;
        }
        throw new IllegalStateException();
    }

    public E firstOrDefault(BooleanFunc<E> func){
        for(E item : _arrayList) {
            if (func.lambda(item)) return item;
        }
        return null;
    }

    public LinqArrayList<E> where(BooleanFunc<E> func) {
        ArrayList<E> result = new ArrayList<E>();
        for(E item : _arrayList) {
            if (func.lambda(item)) result.add(item);
        }
        return new LinqArrayList<E>(result);
    }

    public <T> LinqArrayList<T> select(SelectFunc<E,T> func) {
        ArrayList<T> result = new ArrayList<T>();
        for(E item : _arrayList) {
            result.add(func.lambda(item));
        }
        return new LinqArrayList<T>(result);
    }

    public boolean any(BooleanFunc<E> func) {
        for(E item : _arrayList) {
            if (func.lambda(item)) return true;
        }
        return false;
    }

    public boolean remove(BooleanFunc<E> func) {
        for(E item : _arrayList) {
            if (func.lambda(item)) {
                _arrayList.remove(item);
                return true;
            }
        }
        return false;
    }

    public LinqArrayList<E> distinct() {
        ArrayList<E> result = new ArrayList<>();
        for(E item : _arrayList) {
            if(!result.contains(item)) {
                result.add(item);
            }
        }
        return new LinqArrayList<>(result);
    }

    public double sum(SumFunc<E> func) {
        double result = 0;
        for(E item : _arrayList) {
            result += func.lambda(item);
        }
        return result;
    }
}
