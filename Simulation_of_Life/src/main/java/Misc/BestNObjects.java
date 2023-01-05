package Misc;

import java.util.function.Function;

public class BestNObjects<T> {
    private final Function<T, Integer> valueFunction;
    private final Object[] values;
    private final int[] indexes;
    private final int size;
    private int insertIndex = 0;

    public BestNObjects(int size, Function<T, Integer> valueFunction) {
        this.size = size;
        this.valueFunction = valueFunction;
        this.values = new Object[size + 1];
        this.indexes = new int[size + 1];
    }

    private void swap(int i, int j) {
        Object tmp = values[i];
        values[i] = values[j];
        values[j] = tmp;
        int tmp2 = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = tmp2;
    }

    public void add(T object) {
        Integer value = valueFunction.apply(object);
        this.indexes[insertIndex] = value;
        this.values[insertIndex] = object;
        if(insertIndex < size) {
            insertIndex++;
        }
        for(int i = insertIndex - 1; i > 0; i--) {
            if(value > indexes[i - 1]) {
                swap(i, i - 1);
            } else break;

        }
    }
    public T get(int index) {
        return (T) values[index];
    }
    public int count() {
        return insertIndex;
    }
}
