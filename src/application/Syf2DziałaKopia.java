package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class Syf2Dzia³aKopia {

	MathOperations1 obj = new MathOperations1();
	@FXML
	private Label result;
	private boolean start = true;
	boolean stop = true;
	String labelTextStr = "";

	String rownaSie = "=";
	String strNawias = "";
	String strWynikZnawiasu = "";
	String front = "";
	String end = "";
	String kopiaLabela = "";
	String liczba1str = "";
	String liczba2str = "";
	String liczba3str = "";

	int liczba1 = 0;
	int liczba2 = 0;
	int liczba3 = 0;
	int tymInt = 0;
	int ipos = 0;
	int output = 0;
	int count = 0;

	int opPos1 = 0;
	int opPos2 = 0;
	String operator = "";
	String operator2 = "";

	int pierwszyNawias = 0;
	int drugiNawias = 0;

	int pierwszyIntNawias = 0;

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
	public void proccesNumbers(ActionEvent event) { // liczby wszytskie
		while (start == true) {
			result.setText("");
			stop = true;
			start = false;
		}
		String value = ((Button) event.getSource()).getText();
		result.setText(result.getText() + value);
	}

	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// METODA GLOWNA URUCHAMIANA GDY WCISNIEMY ZNAK "=" RÓWNA SIÊ
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// METODA GLOWNA URUCHAMIANA GDY WCISNIEMY ZNAK "=" RÓWNA SIÊ
	
	
	@FXML
	public void procces(ActionEvent event) { // znak = równa siê
		labelTextStr = String.valueOf(result.getText());
		kopiaLabela = labelTextStr;

		petlaUstalaWartosciNawiasowJakoInty();    //petlaUstalaWartosciNawiasowJakoInty  > checkCzyNawiasyPoprawneIzamieniaNaStringaInsideNawias NIC

		if (stop == true) {
			checkPosNawiasOper_iCloseGdyManyOperators(); // checkPosNawiasOper_iCloseGdyManyOperators >    setNumbersAndOperFromNawiasy  else > ustalaLabelOstatecznyBezNawiasu
		}

		petlaUstalaMiejsceOper();                      //petlaUstalaMiejsceOper   NIC
		obliczaGdy2liczby();						//obliczaGdy2liczby > obliczaGdy3liczby(); >output

	}

	public void checkCzyLabelMaNawiasy() {               //zagadka jak zrobiæ ¿eby dzia³a³o kilka nawiasów
		int counttt = 0;
		System.out.println(labelTextStr);
		for (int i = 0; i < labelTextStr.length(); i++) {
			System.out.println(labelTextStr.charAt(i));
			if (labelTextStr.charAt(i) == '(') {
				System.out.println("checkCzyLabelMaNawiasy if w petli " + labelTextStr);
				counttt++;
				petlaUstalaWartosciNawiasowJakoInty();
				break;
			}

		}
		if (counttt == 0) {
			petlaUstalaMiejsceOper();
			System.out.println("checkCzyLabelMaNawiasy gdy count ==0 " + labelTextStr);
		}
		counttt = 0;
	}
	
	public void petlaUstalaWartosciNawiasowJakoInty() {
		int valueI = 0;
		for (int i = 0; i < labelTextStr.length(); i++) {
			if (labelTextStr.charAt(i) == '(') {
				valueI = i;
				for (int j = valueI; j < labelTextStr.length(); j++) {
					if (labelTextStr.charAt(j) == ')') {
						pierwszyNawias = i;
						drugiNawias = j;
						break;
					}
				}
			}
		}
		valueI = 0;
		checkCzyNawiasyPoprawneIzamieniaNaStringaInsideNawias();
	}
	
	

	public void checkCzyNawiasyPoprawneIzamieniaNaStringaInsideNawias() {
		if (drugiNawias == 0 || pierwszyNawias > drugiNawias || drugiNawias + 1 == pierwszyNawias) {
			stop = false;

		} else {
			strNawias = labelTextStr.substring(pierwszyNawias + 1, drugiNawias); //

		}

	}

	public void checkPosNawiasOper_iCloseGdyManyOperators() {
		for (int i = 0; i < strNawias.length(); i++) {
			if (strNawias.charAt(i) == '/' || strNawias.charAt(i) == '+' || strNawias.charAt(i) == '-'
					|| strNawias.charAt(i) == '*') {
				count++;
				ipos = i;
			}
		}
		if ((count >= 2)) {
			result.setText("");
			stop = false; // tu zamienia w false w podwójnym dzia³aniu
			return;
		}
		setNumbersAndOperFromNawiasy();

	}

	public void setNumbersAndOperFromNawiasy() { // procces wywo³uje tê metodê
		if (count == 0) {
			int singleLiczbaNawias = 0;
			singleLiczbaNawias = Integer.valueOf(strNawias);
			String tymFront = labelTextStr.substring(0, pierwszyNawias);
			String tymEnd = labelTextStr.substring(drugiNawias + 1, labelTextStr.length());
			labelTextStr = tymFront + singleLiczbaNawias + tymEnd; // ustali³o
																	// label
																	// ostateczny
																	// bez
																	// nawiasu
			System.out.println("tym " + tymEnd + " label " + labelTextStr);
		} else {
			operator = strNawias.substring(ipos, ipos + 1);
			liczba1str = strNawias.substring(0, ipos);
			liczba2str = strNawias.substring(ipos + 1, strNawias.length());
			liczba1 = Integer.valueOf(liczba1str); //
			liczba2 = Integer.valueOf(liczba2str);
			ipos = 0;
			count = 0;
			ustalaLabelOstatecznyBezNawiasu();
		}
		pierwszyNawias = 0;
		drugiNawias = 0;
	}

	public void ustalaLabelOstatecznyBezNawiasu() { // zmienia labeltekst na
													// ostateczny 3+3 lub 3+4*4
		tymInt = obj.calculate(liczba1, liczba2, operator);
		strWynikZnawiasu = String.valueOf(tymInt);
		front = labelTextStr.substring(0, pierwszyNawias);
		end = labelTextStr.substring(drugiNawias + 1, labelTextStr.length());
		labelTextStr = front + strWynikZnawiasu + end;
		pierwszyNawias = 0;
		drugiNawias = 0;

	}


	public void petlaUstalaMiejsceOper() {

		for (int i = 0; i < labelTextStr.length(); i++) {
			if (opPos1 == 0 && (labelTextStr.charAt(i) == '/' || labelTextStr.charAt(i) == '+'
					|| labelTextStr.charAt(i) == '-' || labelTextStr.charAt(i) == '*')) {
				opPos1 = i;
				for (int j = i + 1; j < labelTextStr.length(); j++) {
					if (labelTextStr.charAt(j) == '/' || labelTextStr.charAt(j) == '+' || labelTextStr.charAt(j) == '-'
							|| labelTextStr.charAt(j) == '*') {
						opPos2 = j;
						// break;
					}
				}
			}
		}
		System.out.println("petlaUstalaMiejsceOper onPos1= "+opPos1+" onPos2= "+opPos2);
	}

	public void obliczaGdy2liczby() {

		if (opPos1 == 1 && opPos2 == 0) {
			operator = labelTextStr.substring(opPos1, opPos1 + 1);

			liczba1str = labelTextStr.substring(0, opPos1);
			liczba2str = labelTextStr.substring(opPos1 + 1, labelTextStr.length());
			liczba1 = Integer.valueOf(liczba1str);
			liczba2 = Integer.valueOf(liczba2str);
			output = obj.calculate(liczba1, liczba2, operator);
			opPos1 = 0;
			opPos2 = 0;
			outPut();
		} else
			obliczaGdy3liczby();
	}

	public void obliczaGdy3liczby() {
		if (opPos1 >= 1 && opPos2 >= 3) { // pierwszy operator mo¿e pojawiæ siê
											// najwczeœniej na pozycji pierwszej
											// --drugi najwczeœniej na pozycji
											// trzeciej

			operator = labelTextStr.substring(opPos1, opPos1 + 1);
			operator2 = labelTextStr.substring(opPos2, opPos2 + 1);
			liczba1str = labelTextStr.substring(0, opPos1);
			liczba2str = labelTextStr.substring(opPos1 + 1, opPos2);
			liczba3str = labelTextStr.substring(opPos2 + 1, labelTextStr.length());
			liczba1 = Integer.valueOf(liczba1str);
			liczba2 = Integer.valueOf(liczba2str); // b³¹d
			liczba3 = Integer.valueOf(liczba3str);
			if ((labelTextStr.substring(opPos2, opPos2 + 1).equals("/") // if w
																		// ifie
					|| (labelTextStr.substring(opPos2, opPos2 + 1).equals("*")))) {
				output = obj.calculate(liczba2, liczba3, operator2);
				output = obj.calculate(liczba1, output, operator);
			} else {

				output = obj.calculate(liczba1, liczba2, operator);
				output = obj.calculate(output, liczba3, operator2);

			}
			opPos1 = 0;
			opPos2 = 0;
		}
		outPut();
	}

	// if jeden operator to wywo³aj standarowe metody - gdy dwa operatory to
	// podziel

	public void outPut() {

		String ostResult = kopiaLabela + rownaSie + String.valueOf(output);
		result.setText(ostResult);
		start = true;
		stop = true;
	}
}




/*
 * public void checkczyLabelmaRównaSie() {
 * 
 * for (int i = 0; i < labelTextStr.length(); i++) { if (labelTextStr.charAt(i)
 * == '=') { result.setText(""); stop = false; return; } }
 * 
 * }
 */

/*
 * public void sprawdzaCzyJestOperator() { int count = 0; for (int i = 0; i <
 * labelTextStr.length(); i++) { if ( count==0 && (labelTextStr.charAt(i) == '/'
 * || labelTextStr.charAt(i) == '+' || labelTextStr.charAt(i) == '-' ||
 * labelTextStr.charAt(i) == '*')) { count++; ipos = i; } } if ((count == 0) ||
 * ipos == 0) { //count>=2 (count>=2 || count == 0) labelTextStr=" zmieni³o ";
 * result.setText(""); stop=false; return; } stop=true; setNumbers(); }
 */

/*
 * public void setNumbers() { // procces wywo³uje tê metodê operator =
 * labelTextStr.substring(ipos, ipos + 1); liczba1str =
 * labelTextStr.substring(0, ipos); liczba2str = labelTextStr.substring(ipos +
 * 1, labelTextStr.length()); liczba1 = Integer.valueOf(liczba1str); // liczba2
 * = Integer.valueOf(liczba2str);
 * 
 * }
 */

/*
 * public void dostajeLabel_ustala_liczby_oblicza(){
 * 
 * 
 * if (opPos1==1 && opPos2==0){ operator=labelTextStr.substring(opPos1,
 * opPos1+1);
 * 
 * liczba1str=labelTextStr.substring(0, opPos1);
 * liczba2str=labelTextStr.substring(opPos1+1, labelTextStr.length());
 * liczba1=Integer.valueOf(liczba1str); liczba2=Integer.valueOf(liczba2str);
 * output=obj.calculate(liczba1, liczba2, operator);
 * 
 * opPos1=0; opPos2=0;
 * 
 * } else if (opPos1>=1 && opPos2>=3){ //pierwszy operator mo¿e pojawiæ siê
 * najwczeœniej na pozycji pierwszej --drugi najwczeœniej na pozycji trzeciej
 * 
 * operator=labelTextStr.substring(opPos1, opPos1+1);
 * operator2=labelTextStr.substring(opPos2, opPos2+1);
 * liczba1str=labelTextStr.substring(0, opPos1);
 * liczba2str=labelTextStr.substring(opPos1+1, opPos2);
 * liczba3str=labelTextStr.substring(opPos2+1, labelTextStr.length());
 * liczba1=Integer.valueOf(liczba1str); liczba2=Integer.valueOf(liczba2str);
 * //b³¹d liczba3=Integer.valueOf(liczba3str); if (
 * (labelTextStr.substring(opPos2, opPos2+1).equals("/") ||
 * (labelTextStr.substring(opPos2, opPos2+1).equals("*"))) ){
 * System.out.println("if op1" + labelTextStr.substring(opPos1, opPos1+1));
 * System.out.println("if op2"+ labelTextStr.substring(opPos2, opPos2+1));
 * output=obj.calculate(liczba2, liczba3, operator2);
 * output=obj.calculate(liczba1, output, operator); } else {
 * System.out.println("else op1" + labelTextStr.substring(opPos1, opPos1+1));
 * System.out.println("else op2"+ labelTextStr.substring(opPos2, opPos2+1));
 * output=obj.calculate(liczba1, liczba2, operator);
 * output=obj.calculate(output, liczba3, operator2);
 * 
 * } System.out.println("poza elsem kasowanie opPosów"); opPos1=0; opPos2=0; } }
 */
