package matcalc;

public enum Operator {

    MUL('*', 3), DIV('/', 3), SUB('-', 1), ADD('+', 1), POW('^', 4);

    private char symbol;
    private int weight;

    Operator(char symbol, int weight) {
        this.symbol = symbol;
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public static Operator getOperatorBySymbol(char symbol) {
        for(Operator operator : values()) {
            if(operator.symbol == symbol) {
                return operator;
            }
        }
        return null;
    }

    public static float apply(Operator operator, float left, float right) {
        if(operator == MUL) {
            return left * right;
        }else if(operator == DIV) {
            return left / right;
        }else if(operator == SUB) {
            return left - right;
        }else if(operator == ADD) {
            return left + right;
        }else if(operator == POW) {
            return (float) Math.pow(left, right);
        }
        return 0.0f;
    }

    public char getSymbol() {
        return this.symbol;
    }

}