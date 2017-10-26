package application;

public class MathOperations1 {
	
	public int calculate(int number1, int number2, String operator) {
		switch (operator) {
		case "+":
			return number1 + number2;
		case "-":
			return number1 - number2;
		case "*":
			return number1 * number2;
		
		case "/":
			try {
				return number1 / number2;	
				
			} catch (ArithmeticException e) {
				System.out.println("nie dziel przez zero durniu!!!");
			}
			default:
				return 0;
		}
	}
}