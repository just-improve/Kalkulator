package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

//Button [][] boardButtons = new Button[3][3];

public class Syf1 {
	MathOperations1 obj = new MathOperations1();
	@FXML
	private Label result;
	private boolean start = true;
	String labelTextStr = "";

	String strNawias = "";

	String kopiaLabela = "";

	int output = 0;

	String operator = "";

	int pierwszyNawias = 0;
	int drugiNawias = 0;
	int countOpInBtn = 0;

	int operFirstMno¿enie = 0;
	String liczbaLeftStr = "";
	String liczbaRightStr = "";
	String wynikOstStr = "";

	int lewePlaceOpInLabelObokMnozenia = -1;
	int prawePlaceOpInLabelObokMnozenia = -1;
	int frontLengthh = 0;
	int jj = 0;
	int countNawias_G = 0;

	@FXML
	public void clear(ActionEvent event) {
		result.setText("");
	}

	@FXML
	public void backOneChar(ActionEvent event) {
		labelTextStr = String.valueOf(result.getText());
		if (labelTextStr.length() >= 1) {
			labelTextStr = labelTextStr.substring(0, labelTextStr.length() - 1);
			result.setText(labelTextStr);
		}
	}

	@FXML
	public void proccesNumbers(ActionEvent event) {
		while (start == true) {
			result.setText("");
			start = false;
		}

		String value = ((Button) event.getSource()).getText();
		result.setText(result.getText() + value);
		labelTextStr = String.valueOf(result.getText());
		if (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")) {
			countOpInBtn++;
			if (countOpInBtn == 2) {
				labelTextStr = labelTextStr.substring(0, labelTextStr.length() - 2) + value;
				countOpInBtn = 1;
			}
		} else {
			countOpInBtn = 0;
		}
		result.setText(labelTextStr);
	}

	@FXML
	public void procces(ActionEvent event) {

		kopiaLabela = labelTextStr;
		checkCzyLabelMaNawiasy();

		// jesli brak nawiasów to
		// przechodzi od razu do
		// obliczaGotowyLabel_UstaloneOperatory

	}

	public void checkCzyLabelMaNawiasy() { // 1/////////////////////////////////////////////////////////////////
		countNawias_G = 0;
		for (int i = 0; i < labelTextStr.length(); i++) {
			if (countNawias_G == 0 && labelTextStr.charAt(i) == '(') {
				countNawias_G++;
				petlaUstalaWartosciNawiasowJakoInty();
				CreatingStrNawias();
					for (jj = 1; jj < strNawias.length(); jj++) {
					System.out.println(strNawias.length());
					if (strNawias.charAt(jj) == '*' || strNawias.charAt(jj) == '/' || strNawias.charAt(jj) == '+'
							|| strNawias.charAt(jj) == '-') {
						setOperatorPlaceInString(strNawias);
						setLewyZnakWzgledemOperFirst(operFirstMno¿enie, strNawias);
						setPrawyZnakWzgledemOperFirst(operFirstMno¿enie, strNawias);
						obliczaStrGdyUstaloneBoki(strNawias, lewePlaceOpInLabelObokMnozenia,
								prawePlaceOpInLabelObokMnozenia, operFirstMno¿enie);
						nawiasPutInLabelResultNewLabel(strNawias, wynikOstStr, lewePlaceOpInLabelObokMnozenia,
								prawePlaceOpInLabelObokMnozenia);
						
					}
				}
				nawiasPutInLabelResultNewLabel(labelTextStr, strNawias, pierwszyNawias, drugiNawias);
			}
		}
		if (countNawias_G != 0) {
			checkCzyLabelMaNawiasy();
		}
		if (countNawias_G == 0) {
			labelReady();
		}
	}

	public void labelReady() {
		int countOperators = 0;
		
			for (int i = 1; i < labelTextStr.length(); i++) {

				if (labelTextStr.charAt(i) == '*' || labelTextStr.charAt(i) == '/' || labelTextStr.charAt(i) == '-'
						|| labelTextStr.charAt(i) == '+') {
					countOperators++;
					System.out.println("");
				}
			}
			if (countOperators == 0) {
				output = Integer.valueOf(labelTextStr);
				System.out.println("otput " + output);
				outPut();
			} else {
				setOperatorPlaceInString(labelTextStr);
				setLewyZnakWzgledemOperFirst(operFirstMno¿enie, labelTextStr);
				setPrawyZnakWzgledemOperFirst(operFirstMno¿enie, labelTextStr);
				obliczaStrGdyUstaloneBoki(labelTextStr, lewePlaceOpInLabelObokMnozenia, prawePlaceOpInLabelObokMnozenia,
						operFirstMno¿enie);
				putInLabelResultNewLabel(lewePlaceOpInLabelObokMnozenia, prawePlaceOpInLabelObokMnozenia, wynikOstStr);
				labelReady();
				

			}

		
	}

	public void petlaUstalaWartosciNawiasowJakoInty() {

		pierwszyNawias = 0;
		drugiNawias = 0;
		boolean prawda = true;
		boolean prawda2 = true;
		for (int i = labelTextStr.length() - 1; i >= 0; i--) {
			if (prawda == true && labelTextStr.charAt(i) == '(') {
				pierwszyNawias = i;
				prawda = false;
				for (int j = i; j < labelTextStr.length(); j++) {
					if (prawda2 == true && labelTextStr.charAt(j) == ')') {
						drugiNawias = j;
						prawda2 = false;
					}
				}

			}
		}
	}

	public void setOperatorPlaceInString(String str) { // jesli operatororów
		operFirstMno¿enie = 0;
		int liczyOpeMnozenia = 0; // na razie nie u¿ywane poniewa¿

		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) == '*' || (str.charAt(i) == '/')) {
				operFirstMno¿enie = i;
				liczyOpeMnozenia++;
				return;
			}

		}

		for (int j = 1; j < str.length(); j++) {
			if ((liczyOpeMnozenia == 0) && (str.charAt(j) == '+' || (str.charAt(j) == '-'))) {
				operFirstMno¿enie = j;
				
			}

		}
	}

	public void setLewyZnakWzgledemOperFirst(int a, String strOblicz) {
		lewePlaceOpInLabelObokMnozenia = -1;
		boolean prawda = true;

		for (int i = operFirstMno¿enie - 1; i >= 0; i--) {
			if (((prawda == true) && (strOblicz.charAt(i) == '*' || strOblicz.charAt(i) == '/'
					|| strOblicz.charAt(i) == '-' || strOblicz.charAt(i) == '+'))) {
				lewePlaceOpInLabelObokMnozenia = i;
				prawda = false;
			}
		}

		operator = strOblicz.substring(operFirstMno¿enie, operFirstMno¿enie + 1);
	}

	public void setPrawyZnakWzgledemOperFirst(int a, String strOblicz) {
		prawePlaceOpInLabelObokMnozenia = -1;
		boolean prawda = true;

		for (int i = operFirstMno¿enie + 1; i < strOblicz.length(); i++) {
			if (((prawda == true) && (strOblicz.charAt(i) == '*' || strOblicz.charAt(i) == '/'
					|| strOblicz.charAt(i) == '-' || strOblicz.charAt(i) == '+'))) {
				prawePlaceOpInLabelObokMnozenia = i;
				prawda = false;
			}
		}
	}

	public void putInLabelResultNewLabel(int firstSignNotTaken, int secondSignNotTaken, String wynikOstLubStrNawias) {///////// zmieniana

		String przodLabelaL = "";
		if (firstSignNotTaken == -1) {
			firstSignNotTaken = 0;
			przodLabelaL = labelTextStr.substring(0, firstSignNotTaken); //
		} else
			przodLabelaL = labelTextStr.substring(0, firstSignNotTaken + 1);

		String tylLabelaL = "";
		if (secondSignNotTaken == -1) {
			tylLabelaL = "";
		} else {
			tylLabelaL = labelTextStr.substring(secondSignNotTaken, labelTextStr.length());
		}

		System.out.println(labelTextStr.length());
		labelTextStr = przodLabelaL + wynikOstLubStrNawias + tylLabelaL;
		przodLabelaL = ""; // kasuje label texta nie wiem czy potrzebnie
	}

	public void nawiasPutInLabelResultNewLabel(String LableOrStrNawias, String wynikOstOrStrNawias,
			int firstSignNotTaken, int secondSignNotTaken) {
		String przodLabelaL = "";
		String tylLabelaL = "";

		if (LableOrStrNawias.equals(labelTextStr)) {
			przodLabelaL = LableOrStrNawias.substring(0, firstSignNotTaken);
			tylLabelaL = LableOrStrNawias.substring(secondSignNotTaken + 1, LableOrStrNawias.length());
			LableOrStrNawias = przodLabelaL + strNawias + tylLabelaL;
			labelTextStr = LableOrStrNawias;
			System.out.println("");
			return;
		}

		if (LableOrStrNawias.equals(strNawias)) {
			if (firstSignNotTaken == -1 || firstSignNotTaken == 0) {
				przodLabelaL = "";
			} else {
				przodLabelaL = LableOrStrNawias.substring(0, firstSignNotTaken + 1);
			}

			if (secondSignNotTaken == -1 || secondSignNotTaken == -0) {
				tylLabelaL = "";
			} else {
				tylLabelaL = LableOrStrNawias.substring(secondSignNotTaken, LableOrStrNawias.length());
			}

			LableOrStrNawias = przodLabelaL + wynikOstStr + tylLabelaL;
			strNawias = LableOrStrNawias;
			jj = 0;
			System.out.println("ss");

		}

	}

	public void obliczaStrGdyUstaloneBoki(String str, int pierwszyZnak, int drugiZnak, int operator) {
		int wynikOst = 0;
		if (pierwszyZnak == -1) {
			liczbaLeftStr = str.substring(0, operator);

		} else {
			liczbaLeftStr = str.substring(pierwszyZnak + 1, operator);
		}
		if (drugiZnak == -1) {
			liczbaRightStr = str.substring(operator + 1, str.length());
		} else {
			liczbaRightStr = str.substring(operator + 1, drugiZnak);
		}
		this.operator = str.substring(operator, operator + 1);
		int num1 = Integer.valueOf(liczbaLeftStr);
		int num2 = Integer.valueOf(liczbaRightStr);
		wynikOst = obj.calculate(num1, num2, this.operator);
		wynikOstStr = String.valueOf(wynikOst);
		this.operator = "";
		operator = 0; // kasowanie nie wiem czy konieczne
	}

	public void outPut() {
		String rownaSie = "=";
		String ostResult = kopiaLabela + rownaSie + String.valueOf(output);
		result.setText(ostResult);
		start = true;
	}

	public void CreatingStrNawias() {
		/*
		 * String fr=""; String en=""; fr=labelTextStr.substring(0,
		 * pierwszyNawias); en=labelTextStr.substring(drugiNawias+1,
		 * labelTextStr.length());
		 */
		strNawias = labelTextStr.substring(pierwszyNawias + 1, drugiNawias);
		/*
		 * labelTextStr=fr+strNawias+en; System.out.println("ggg"+labelTextStr);
		 */
	}

}
