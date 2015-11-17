package com.andres_k.utils.stockage;

/**
 * Created by andres_k on 13/03/2015.
 */
public class Tuple<T1, T2, T3> {
    T1 v1;
    T2 v2;
    T3 v3;

    public Tuple(T1 v1, T2 v2, T3 v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Tuple(Tuple<T1, T2, T3> tuple) {
        if (tuple != null) {
            this.v1 = tuple.v1;
            this.v2 = tuple.v2;
            this.v3 = tuple.v3;
        }
    }

    public T1 getV1() {
        return this.v1;
    }

    public T2 getV2() {
        return this.v2;
    }

    public T3 getV3() {
        return this.v3;
    }

    public void setV1(T1 value) {
        this.v1 = value;
    }

    public void setV2(T2 value) {
        this.v2 = value;
    }

    public void setV3(T3 value) {
        this.v3 = value;
    }

    public void copy(Tuple<T1, T2, T3> values) {
        this.v1 = values.getV1();
        this.v2 = values.getV2();
        this.v3 = values.getV3();
    }

    @Override
    public String toString() {
        return "v1:" + this.getV1() + " v2:" + this.getV2() + " v3:" + this.getV3();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Tuple) {
            if (this.getV1().equals(((Tuple) object).getV1())
                    && this.getV2().equals(((Tuple) object).getV2())
                    && this.getV3().equals(((Tuple) object).getV3())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.v1.hashCode() + this.v2.hashCode() + this.v3.hashCode();
    }

}
