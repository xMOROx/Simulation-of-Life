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
        Object tmp = this.values[i];
        this.values[i] = this.values[j];
        this.values[j] = tmp;
        int tmp2 = this.indexes[i];
        this.indexes[i] = this.indexes[j];
        this.indexes[j] = tmp2;
    }

    public void add(T object) {
        Integer value = this.valueFunction.apply(object);
        this.indexes[this.insertIndex] = value;
        this.values[this.insertIndex] = object;
        if (this.insertIndex < this.size) {
            this.insertIndex++;
        }
        for (int i = this.insertIndex - 1; i > 0; i--) {
            if (value > this.indexes[i - 1]) {
                swap(i, i - 1);
            } else break;
        }
    }

    public T get(int index) {
        return (T) this.values[index];
    }

    public int count() {
        return this.insertIndex;
    }
}
