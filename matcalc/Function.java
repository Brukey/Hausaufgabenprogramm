package matcalc;
import java.util.HashMap;

public class Function {

    private String name;
    private String parameterName;
    private Evaluable expression;
    private static HashMap<String, Function> registeredFunction = new HashMap<String, Function>();

    public Function(String name, String parameterName) {
        this.name = name;
        this.parameterName = parameterName;
    }

    public void setExpression(Evaluable expression) {
        this.expression = expression;
    }

    public void register() {
        registeredFunction.put(this.name, this);
    }

    public float evaluate(float x) {
        if(this.expression == null) return 0;

        if(this.expression instanceof Replaceable) {
            ((Replaceable) this.expression).replace(parameterName, x);
        }
        
        return this.expression.evaluate();
    }

    public static Function getFunctionByName(String name) {
        return registeredFunction.get(name);
    }

    public String getFunctionName() {
        return this.name;
    }


    public String toString(float x) {
        return String.format("%s(%f) = %f", this.name, x, this.evaluate(x));
    }

    @Override
    public String toString() {
        return String.format("%s(%s) = %s", this.name, this.parameterName, this.expression.toString());
    }

    static {
        Function cos = new Function("cos", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.cos(x);
            }
        };

        Function sin = new Function("sin", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.sin(x);
            }
        };

        Function abs = new Function("abs", "x") {
            @Override
            public float evaluate(float x) {
                return x < 0 ? (-x) : x;
            }
        };

        Function tan = new Function("tan", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.tan(x);
            }
        };

        Function random = new Function("rand", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.random() * x;
            }
        };

        Function round = new Function("round", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.round(x);
            }
        };

        Function ln = new Function("ln", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.log(x);
            }
        };

        
        Function atan = new Function("atan", "x") {
            @Override
            public float evaluate(float x) {
                System.out.println(x);
                return (float) Math.atan(x);
            }
        };

        Function acos = new Function("acos", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.acos(x);
            }
        };

        Function asin = new Function("asin", "x") {
            @Override
            public float evaluate(float x) {
                return (float) Math.asin(x);
            }
        };

        abs.register();
        tan.register();
        random.register();
        round.register();
        ln.register();
        sin.register();
        cos.register();
        asin.register();
        acos.register();
        atan.register();
    }
}