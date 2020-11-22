package matcalc;

public class FunctionCall implements Replaceable, Evaluable {

    private Function function;
    private Evaluable param;

    public FunctionCall(String functionname, String expression) {
        this.param = Expression.create(expression);
        this.function = Function.getFunctionByName(functionname);
        if(function == null || expression.length() == 0) {
            throw new ArithmeticException("Could not find function " + functionname);
        }
    }

    @Override
    public void replace(String var, float val) {
        if(this.param instanceof Replaceable) {
            ((Replaceable)this.param).replace(var, val);
        }
    }

    @Override
    public float evaluate() {
        float param = this.param.evaluate();
        float val = this.function.evaluate(param);
        return val;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.function.getFunctionName(), this.param.toString());
    }

}