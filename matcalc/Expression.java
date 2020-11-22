package matcalc;

import java.util.List;
import java.util.ArrayList;

public class Expression implements Evaluable, Replaceable {

    private String expressionString;
    private List<Evaluable> values = new ArrayList<Evaluable>();
    private List<Operator> operators = new ArrayList<Operator>();

    Expression(ExpressionString expression) throws ArithmeticException {
        this.expressionString = expression.stringValue;

        int startOfSubExpression = 0;

        if(expression.operatorIndices.size() <= 0)
            throw new ArithmeticException("could not parse expression " + this.expressionString);


        for(int i = 0; i < expression.operatorIndices.size(); i++) {
            int operatorIndex = expression.operatorIndices.get(i);
            if(expression.operatorweights.get(i) == expression.lowestWeight) {
                // add a new expression
                String sub = expression.stringValue.substring(startOfSubExpression, operatorIndex);
                Operator op = Operator.getOperatorBySymbol(expression.stringValue.charAt(operatorIndex));

                Evaluable value;
                // check if it is a leading negative sign
                if(sub.length() == 0) {
                    if(op == Operator.SUB) {
                        value = new Constant(0);
                    }else{
                        throw new ArithmeticException("An expression can only start with an operator if it is a negative sign.");
                    }
                }else{
                    value = Expression.create(sub);
                }

                

                this.values.add(value);
                

                startOfSubExpression = operatorIndex + 1;
                this.operators.add(op);
            }
        }
        String sub = expression.stringValue.substring(startOfSubExpression);
        this.values.add(Expression.create(sub));

        if(this.values.size() - 1 != this.operators.size())
            throw new ArithmeticException("Amount of operators does not match the amount of subexpressions\n");

    }

    
    public static Evaluable create(String value) throws ArithmeticException {
        value = removeBracketsAtStartAndEnd(value.toLowerCase().replace(" ", ""));
        ExpressionString expressionString = new ExpressionString(value);
        if(!expressionString.valid) throw new ArithmeticException("Could not parse " + value);
        
        if(!expressionString.operatorIndices.isEmpty()) {
            // its an expression
            return new Expression(expressionString);
        }else if(expressionString.functionIndices.size() == 1 && expressionString.functionIndices.get(0)[0] == 0 && 
            expressionString.functionIndices.get(0)[1] == value.length()) {
            // function or expression
            final int firstBracketOpen = value.indexOf("(");
            final String functionname = value.substring(0, firstBracketOpen);
            final String functionExpression = value.substring(firstBracketOpen + 1, value.length() - 1);
            return new FunctionCall(functionname, functionExpression);
        }else{
            // variable or constant
            try{
                final float constantValue = Float.parseFloat(value);
                return new Constant(constantValue);
            }catch(Exception e) {
                return new Variable(value);
            }
        }
    }

    @Override
    public void replace(String var, float val) {

        for(Evaluable value : this.values) {
            if(value instanceof Variable) {
                Variable l = (Variable) value;
                if(l.getName().equals(var)) {
                    l.setValue(val);
                }
            }else if(value instanceof Replaceable) {
                ((Replaceable) value).replace(var, val);
            }
        }
    }


    @Override
    public float evaluate() {
        float value = this.values.get(0).evaluate();
        for(int i = 1; i < this.values.size(); i++) {
            Operator operator = this.operators.get(i - 1);
            float right = this.values.get(i).evaluate();
            value = Operator.apply(operator, value, right);
        }

        return value;
    }

    @Override
    public String toString() {
        String string = "(";
        for(int i = 0; i < this.values.size() - 1; i++) {
            string += this.values.get(i);
            string += this.operators.get(i).getSymbol();
        }
        string += this.values.get(this.values.size() - 1).toString();
        return string + ")";
    }

    public static String removeBracketsAtStartAndEnd(String string) {
        if(string.length() == 0) return string;
        int i = 0;
        while(string.charAt(i) == '(' && string.charAt(string.length() - 1 - i) == ')' && i < string.length() - 1 - i) {
            i++;
        }
        return string.substring(i, string.length() - i);
    }
}

class ExpressionString {

    public String stringValue;
    public boolean valid = true;
    public List<Integer> operatorIndices = new ArrayList<>();
    public List<Integer> operatorweights = new ArrayList<>();
    public List<int[]> functionIndices = new ArrayList<>();
    public int lowestWeight = Integer.MAX_VALUE;

    public ExpressionString(String expression) {
        this.stringValue = expression;

        int weight = 0;
        for(int i = 0; i < this.stringValue.length(); i++) {
            final char c = this.stringValue.charAt(i);
            Operator op = Operator.getOperatorBySymbol(c);
            if(op != null) {
                int w = weight + op.getWeight();
                this.lowestWeight = Math.min(this.lowestWeight, w);
                this.operatorweights.add(w);
                this.operatorIndices.add(i);
            }
            // check if a function starts
            // if it is skip the function
            if(c == '(' && i > 0 && Character.isAlphabetic(stringValue.charAt(i - 1))) {
                int functionStart = -1;
                int functionEnd = -1;

                for(int j = i; j >= 0; j--) {
                    if(Operator.getOperatorBySymbol(this.stringValue.charAt(j)) != null) {
                        functionStart = j + 1;
                    }
                }
                if(functionStart == -1) functionStart = 0;
                
                int bracketCount = 0;
                for(int j = i; j < this.stringValue.length(); j++) {
                    if(this.stringValue.charAt(j) == '(')
                        bracketCount++;
                    else if(this.stringValue.charAt(j) == ')')
                        bracketCount--;
                    
                    if(bracketCount == 0) {
                        functionEnd = j;
                        break;
                    }
                }

                if(functionEnd == -1)
                    this.valid = false;
                else
                    i = functionEnd;
                int[] substringIndices = {functionStart, functionEnd + 1};
                this.functionIndices.add(substringIndices);
            }else if(c == '(') {
                weight += 5;
            }else if(c == ')'){
                weight -= 5;
            }

        }

    }
}