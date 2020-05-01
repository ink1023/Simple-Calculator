import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionParser {
//    public static void main(String[] args) {
//        String exp = "1+2×3-4";
//        System.out.println(ExpressionParser3.parse(exp));
//    }

    public static float parse(String expressionStr){
        StringBuilder sb = new StringBuilder(expressionStr);
        ArrayList<Character> operatorList = new ArrayList<>();
        ArrayList<Float> operandList = new ArrayList<>();
        int clipStart=0, clipEnd=0;
        for(int i=0; i<sb.length(); i++){
            char c = sb.charAt(i);
            Character[] opArr = new Character[] {'+','-','×','÷'};
            if (Arrays.asList(opArr).contains(c)) { //遇到了操作符
                clipEnd = i-1;
                operatorList.add(c);
                float prevNum = Float.parseFloat(sb.substring(clipStart, clipEnd+1));
                operandList.add(prevNum);//把上一个操作数拿出来
                //System.out.println(sb.substring(clipStart, clipEnd+1));
                clipStart = clipEnd+2;
            }
        }
        float lastNum = Float.parseFloat(sb.substring(clipEnd+2, sb.length()));
        operandList.add(lastNum);//把最后一个操作数拿出来


        while( -1!=operatorList.indexOf('×') || -1!=operatorList.indexOf('÷')){
            int opeartorIndex= Math.max(operatorList.indexOf('×'), operatorList.indexOf('÷'));
            Character operator = operatorList.get(opeartorIndex);
            float operand1 = operandList.get(opeartorIndex);
            float operand2 = operandList.get(opeartorIndex+1);
            float result = ExpressionParser.simpleCalc(operand1, operator, operand2);
            operatorList.remove(opeartorIndex);
            operandList.set(opeartorIndex, result);
            operandList.remove(opeartorIndex+1);
        }

        while( -1!=operatorList.indexOf('+') || -1!=operatorList.indexOf('-')){
            int opeartorIndex= Math.max(operatorList.indexOf('+'), operatorList.indexOf('-'));
            Character operator = operatorList.get(opeartorIndex);
            float operand1 = operandList.get(opeartorIndex);
            float operand2 = operandList.get(opeartorIndex+1);
            float result = ExpressionParser.simpleCalc(operand1, operator, operand2);
            operatorList.remove(opeartorIndex);
            operandList.set(opeartorIndex, result);
            operandList.remove(opeartorIndex+1);
        }
        return  operandList.get(0);
    }

    public static float simpleCalc(float operand1, Character operator, float operand2){
        float result;
        switch (operator){
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '×':
                result = operand1 * operand2;
                break;
            case '÷':
                result = operand1 / operand2;
                break;
            default:
                throw new NumberFormatException();
        }
        return result;
    }
}
