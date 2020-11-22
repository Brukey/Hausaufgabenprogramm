package matcalc;

public class Constant implements Evaluable {

    private float value;


    public Constant(float value) {
        this.value = value;
    }

    @Override
    public float evaluate() {
        return this.value;
    }

    @Override
    public String toString() {
        return "" + this.value;
    }
}