package matcalc;

public class Variable implements Evaluable, Modifiable, Replaceable {

    private float value;
    private String name;

    public Variable(String name) {
        this.name = name;
        this.value = 0;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public float evaluate() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void setValue(float val) {
        this.value = val;
    }

    @Override
    public void replace(String name, float val) {
        if(this.name.equals(name))
            this.setValue(val);
    }
}