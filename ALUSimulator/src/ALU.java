/**
 * ģ��ALU���������͸���������������
 * @author [151250077_���]
 *
 */

public class ALU {
	
	//����
	public char andGate (char number1, char number2) {
		if(number1 == '1' && number2 == '1') {
			return '1';
		} else {
			return '0';
		}
	}
	
	//����
	public char orGate (char number1, char number2) {
		if(number1 == '1' || number2 == '1') {
			return '1';
		} else {
			return '0';
		}
	}

	//�����
	public char XorGate (char number1, char number2) {
		if(number1 == '1' && number2 == '0') {
			return '1';
		} else if(number1 == '0' && number2 == '1') {
			return '1';
		} else {
			return '0';
		}
	}
	
	//����
	public char notGate(char number) {
		if(number == '1') {
			return '0';
		} else {
			return '1';
		}
	}

	//�����������ĳ˷�
	public String signedMultiplication (String operand1, String operand2, int length) {
		
		//���в�������ȫ
		if(operand1.length()-1 < length){
			int subLength = length - operand1.length() + 1;
	    	for(int i = 0; i < subLength; i++){
	    		operand1 = operand1.substring(0, 1) + "0" +operand1.substring(1);
			}
		}
					
		if(operand2.length()-1 < length){
    		int subLength = length - operand2.length() + 1;
    		for(int i = 0; i < subLength; i++){
				operand2 = operand2.substring(0, 1) + "0" +operand2.substring(1);
			}
		}
			
		//��ֵ����
		char sign1 = operand1.substring(0,1).toCharArray()[0];
		char sign2 = operand2.substring(0,1).toCharArray()[0];			
			
		String value1 = operand1.substring(1);
		String value2 = operand2.substring(1);

		char sign = XorGate(sign1, sign2);
		String value = integerMultiplication(value1, value2, length).substring(1);
		
		String result = String.valueOf(sign) + value;
		
		return result;
	}

	//����������Ч������
	public String significantDivision (String operand1, String operand2, int length) {
		
		String result = "";
		
		//���в�������ȫ
		String dividend = operand1;
		if(operand1.length() < length){
			int subLength = length - operand1.length();
			for(int i = 0; i < subLength; i++){
				dividend = dividend + "0";
			}
		}
		
		String divisor = operand2;
		if(operand2.length() < length){
			int subLength = length - operand2.length();
			for(int i = 0; i < subLength; i++){
				divisor = divisor + "0";
			}
		}
		
		//�жϱ������Ƿ�Ϊ0
		String allZero ="";
		for(int i = 0; i < length; i++){
			allZero = "0" + allZero;
		}
		if(dividend.equals(allZero)) {
			result = "0" + allZero + allZero;
			return result;
		}
		
//		String remainder = "";
//		String quotient = "";
//		for(int i = 0; i < length; i++){
//			remainder = dividend.substring(0,1) + remainder; 
//		}
//		quotient = dividend;
		
		String remainder = dividend;
		String quotient = "";
		for(int i = 0; i < length; i++){
		quotient = "0" + quotient; 
	} 
		
		//��һ������
		if(dividend.substring(0,1).equals(divisor.substring(0,1))){
			remainder = integerSubtraction(remainder, divisor, length).substring(1);
		} else {
			remainder = integerAddition(remainder, divisor, length).substring(1);
		}
		
		if(remainder.substring(0,1).equals(divisor.substring(0,1))){
			quotient = quotient + "1";
		} else {
			quotient = quotient + "0";
		}
		
		//�м������
		for(int i = 0; i < length ;i++){
			remainder = leftShift(remainder + quotient,1).substring(0, length);
			quotient = leftShift(remainder + quotient,1).substring(length,2*length);
			
			//���ж�����Ϊ0�������������ʱ�����������������Ŵ���,�����ж������ͳ����Ƿ���ţ�����ȡ��ͬ�Ĵ���ʽ
			if(remainder.equals(allZero)){
				remainder = integerAddition(remainder, divisor, length).substring(1);
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				remainder = integerSubtraction(remainder, divisor, length).substring(1);
			} else {
				remainder = integerAddition(remainder, divisor, length).substring(1);
			}
			
			//���ж�����Ϊ0�������������ʱ�����������������Ŵ���,�����ж������ͳ����Ƿ���ţ�����ȡ��ͬ�Ĵ���ʽ
			if(remainder.equals(allZero)){
				quotient = quotient + "0";
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				quotient = quotient + "1";
			} else {
				quotient = quotient + "0";
			}
		}
		
		//�����ս����������
		quotient = leftShift(quotient,1).substring(0, length);
		if(quotient.substring(0,1).equals("1")){
			quotient = oneAdder(quotient).substring(1,length+1);
		} 
		
		if(!dividend.substring(0,1).equals(remainder.substring(0,1))){
			if(dividend.substring(0,1).equals(divisor.substring(0,1))){
				remainder = integerAddition(remainder, divisor, length).substring(1);
			} else {
				remainder = integerSubtraction(remainder, divisor, length).substring(1);
			}
		}
				
		result = "0" + quotient + remainder;
		
		return result;
	}
	
	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public String integerRepresentation (String number, int length) {
		int tempNumber = Integer.parseInt(number);
		String result = "";
		boolean isInvert = false;
		
		//���жϸ��ݵ�һλ�ж��Ƿ�Ϊ����
		if( number.charAt(0) == '-' ){
			tempNumber = Integer.parseInt(number.substring(1, number.length()));
			isInvert = true;
		}
		
		//����ȡ�෨
		while(true) {
			result =  (tempNumber % 2) + result;
			if( tempNumber / 2 == 0 ){
				break;
			}
			tempNumber = tempNumber / 2;
		} 
		
		//����Ǹ�������ȡ����һ
		if(isInvert) {
			
			char[] tempChar = result.toCharArray();
			
			//��λȡ��
			for(int num = 0; num < tempChar.length; num ++){
				tempChar[num] = notGate(tempChar[num]);
			}
			
			char carry = '1';
			
			//���м�һ
			for(int num = tempChar.length - 1; num >= 0; num --){
				if((carry == '1') && (tempChar[num] == '1') ){
					tempChar[num] = '0';
					carry = '1';
				} else if((carry == '1') && (tempChar[num] == '0') ){
					tempChar[num] = '1';
					carry = '0';
				} else if( carry == '0' ) {
					break;
				}
			}
			
			result = String.valueOf(tempChar);
		}
		
		//���λ�����㣬����в�λ
		while(result.length() != length){
			if(isInvert) {
				result = "1" + result; 
			} else {
				result = "0" +result;
			}
		}
	
		return result;
	}
	
	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {

		String sign = "";
		String result = "";
		String exponent = "";
		String significant = "";
		
		//�����ƫֵ
		int bias= (int)(Math.pow(2, eLength-1)) - 1;
		
		//�������Ӧ��ָ��ȫһ�ַ�����β��ȫ���ַ���
		String eAllOne = "";
		for(int i = 0; i < eLength; i++){
			eAllOne = "1" + eAllOne;
		}
		String sAllZero = "";
		for(int i = 0; i < sLength; i++){
			sAllZero = "0" + sAllZero;
		}
	
		//�����Ƿ�ΪNaN���ж�
		if(number.equals("NaN")) {
			result = "NaN";
			return result;
		}
	
		//�����Ƿ�Ϊ�����������ж�
		if(number.equals("+Inf") || number.equals("-Inf")){
			if(number.charAt(0) == '+'){
				result = "0" + result;
			} else {
				result = "1" + result;
			}
			for(int i = 0; i < eLength; i ++){
				result = result + "1";
			}
			for(int j = 0; j < sLength; j ++){
				result = result + "0";
			}
			return result; 
		}
		
		//ȷ������λ
		sign = "0";
		
		if(number.charAt(0) == '-'){
			sign = "1";
			number = number.substring(1,number.length());
		}
		
		//����ֵ���ַ�Ϊ�������ֺ�С������
		String[] tempNumber = number.split("\\.");
		int integer = Integer.parseInt(tempNumber[0]);
		int tempInteger = integer;
		String decimal = tempNumber[1];
				
		//�����Ƿ�Ϊ0���ж�
		if( tempInteger == 0 && Double.parseDouble("0." + decimal) == 0 ){
			for(int num = 0; num < (1+eLength+sLength); num ++){
				result = result + "0";
			}
			return result;
		}
		
		String binInteger = "";
		String binDecimal = "";
		
		//����ȡ�෨������������ֵĶ����Ʊ�ʾ
		while(true) {
			binInteger =  (tempInteger % 2) + binInteger;
			if( tempInteger / 2 == 0 ){
				break;				
			}
			tempInteger = tempInteger / 2;
		} 
		
		//�˶�ȡ���������С�����ֵĶ����Ʊ�ʾ
		int count = 0;
		
		double tempResult = Double.parseDouble("0." + decimal);
		
		while(count < bias*3 ) {
			
			tempResult = tempResult*2;

			if(tempResult >= 1.0){
				binDecimal = binDecimal + "1";
				tempResult = tempResult - 1.0;
			} else if(tempResult < 1.0) {
				binDecimal = binDecimal + "0";
			}
			
			//�ж�С�������Ƿ�Ϊ��
			if(tempResult == 0){
				break;
			}
			
			count ++;
		}
		
		//�ж����������Ƿ�Ϊ0������ȡ��ͬ�Ĺ�񻯷���
		int tempExponent = 0;
				
		if( integer != 0){
			//���������ֺ�С�����������һ�𣬽��й��
			significant = binInteger.substring(1,binInteger.length()) + binDecimal;
			
			//��ָ��+ƫֵ
			tempExponent = bias + (binInteger.length() - 1);
			
		} else {
			//�ҵ�С�������е�һ������0��λ��
			int location = 0;
			String tempStr = "";
			
			do {
				if(location == bias){
					break;
				}
				tempStr = binDecimal.substring(location,location + 1);
				location ++;
			} while(tempStr.equals("0"));
			
			//���ָ���Ѿ�Ϊ0������÷���񻯣���1ǰ��һλ��ȡ
			if(location != bias){
				significant = binDecimal.substring(location);
			} else {
				significant = binDecimal.substring(location-1);
			}
	
			//��ָ��+ƫֵ
			tempExponent = bias - location;
		}
		
		while(true) {
			exponent =  (tempExponent % 2) + exponent;
			if( tempExponent / 2 == 0 ){
				break;				
			}
			tempExponent = tempExponent / 2;
		}
		
		//�ж�β��λ�����پ���0��λ�������0��ȥ
		if(significant.length() <= sLength){
			int fillLength = sLength - significant.length();
			for(int i = 0; i < fillLength; i++){
				significant = significant + "0";
			} 
		} else {
			significant = significant.substring(0, sLength);
		}
		
		//�ж�ָ��λ�����پ���0��λ
		if(exponent.length() <= eLength){
			int fillLength = eLength - exponent.length();
			for(int i = 0; i < fillLength; i++){
				exponent = "0" + exponent ;
			} 
		} else {
			exponent = exponent.substring(0, sLength);
		}
					
		//����Ƿ����磬�������򷵻������
		if(exponent.equals(eAllOne)){
			if(sign.equals("0")){
				return "0" + eAllOne + sAllZero;
			} else {
				return "1" + eAllOne + sAllZero;
			}
		} 
				
		result = sign + exponent + significant;
		
		return result;
	}
	
	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		String result = "";
		
		if (length == 32) {
			result = this.floatRepresentation(number, 8, 23);
		} else if (length == 64) {
			result = this.floatRepresentation(number, 11,52);
		}
		
		return result;
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
		
		int tempNumber = 0;
		int location = operand.length()-1;
		char[] tempChar = operand.toCharArray();
		String result = "";
		
		//���ö����Ʋ���ת����ʽ����ת��
		for(int num = 0; num < tempChar.length; num ++){
			
			if(num == 0){
				if(tempChar[num] == '1') {
					tempNumber = tempNumber - (int)(Math.pow(2, location));
				}
			} else {
				if(tempChar[num] == '1') {
					tempNumber = tempNumber + (int)(Math.pow(2, location));
				}
			}
			
			location --;
		}
		
		result = String.valueOf(tempNumber);
		
		return result;
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		
		String result = "";
		double decimal = 0;
		int eValue = 0;
		String exponent = "";
		String mantissa = "";
		
		//�����ƫֵ��ȫһָ����ȫ��ָ����ȫ��β��
		int bias= (int)(Math.pow(2, eLength-1)) - 1;
		
		String eAllOne = "";
		for(int i = 0; i < eLength; i++){
			eAllOne = "1" + eAllOne;
		}
		
		String eAllZero = "";
		for(int i = 0; i < eLength; i++){
			eAllZero = "0" + eAllZero;
		}
		
		String sAllZero = "";
		for(int i = 0; i < sLength; i++){
			sAllZero = "0" + sAllZero;
		}
		
		String sign = "";
		
		if(operand.charAt(0) == '1'){
			sign ="-";
		}
		
		exponent = operand.substring(1,eLength+1);
		mantissa = operand.substring(eLength+1,operand.length());
		
		//�ж��Ƿ�Ϊ����������NaN
		if(exponent.equals(eAllOne)){
			if(Integer.parseInt(mantissa) == 0){
				if(operand.charAt(0) == '0'){
					result = "+Inf";
					return result;
				} else if(operand.charAt(0) == '1'){
					result = "-Inf";
					return result;
				}
			} else {
				result = "NaN";
				return result;
			}
		}
		
		//�ж��Ƿ�Ϊ0���߷������
		if(exponent.equals(eAllZero)){
			if(mantissa.equals(sAllZero)){
				return "0";
			} else {
				for(int location = 0; location < sLength; location ++){
					String tempNumber = mantissa.substring(location,location + 1);
					decimal = Integer.parseInt(tempNumber)*Math.pow(2,(-1)-location) + decimal;
				}
				result = String.valueOf(decimal*(Math.pow(2, 1 - bias)));
				return result;
			}
		}
		
		//����������д���
		//���С������
		for(int location = 0; location < sLength; location ++){
			String tempNumber = mantissa.substring(location,location + 1);
			decimal = Integer.parseInt(tempNumber)*Math.pow(2,(-1)-location) + decimal;
		}
		
		//���ָ������
		for(int location = eLength - 1; location >= 0; location --){
			String tempNumber = exponent.substring(location,location + 1);
			eValue = (int)(Integer.parseInt(tempNumber)*Math.pow(2,eLength - location -1)) + eValue;
		}
		
		//��1+С�����֣�* ָ��
		result = sign + String.valueOf((1 + decimal)*(Math.pow(2, eValue - bias)));
		return result;
		
	}
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	public String negation (String operand) {
		
		char[] input = operand.toCharArray();
		char[] output = operand.toCharArray();
		
		for(int num = 0; num < input.length; num ++){
			output[num] = notGate(input[num]);
		}
				
		String result = String.valueOf(output);
		
		return result;
	}
	
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		
		String result = operand;
		
		for(int num = 0; num < n; num ++){
			//����1λ��ĩβ��0����λ��ȥ
			result = result + "0";
			result = result.substring(1, result.length());
		}
		
		return result;
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		
		String result = operand;
		
		for(int num = 0; num < n; num ++){
			//����1λ��ĩλ��ȥ����λ��0
			result = "0" + result;
			result = result.substring(0, result.length()-1);
		}
		
		return result;
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		
		String result = operand;
		
		String sign = operand.substring(0,1);
		
		for(int num = 0; num < n; num ++){
			//����1λ��ĩλ��ȥ����λ������λ
			result = sign + result;
			result = result.substring(0, result.length()-1);
		}
		
		return result;
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		char carry ;
		char sum ;
		String result = "";
		
		carry = orGate(orGate(andGate(x,y),andGate(x,c)),andGate(y,c));
		sum = XorGate(XorGate(x,y),c);
		result = String.valueOf(carry) + String.valueOf(sum);
		
		return result;
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
	 */
	public String claAdder (String operand1, String operand2, char c) {		
		
		char[] X = new StringBuffer(operand1).reverse().toString().toCharArray();
		char[] Y = new StringBuffer(operand2).reverse().toString().toCharArray();
		char[] P = new char[4];
		char[] G = new char[4];
		char[] C = new char[4];
		
		for(int i = 0; i < 4; i++){
			P[i] = orGate( X[i], Y[i] );
			G[i] = andGate( X[i], Y[i] );
		}
		
	    C[0] = orGate( G[0], andGate(P[0],c));
		C[1] = orGate( orGate( G[1], andGate(P[1],G[0])), andGate(P[1],andGate(P[0],c)));
		C[2] = orGate( orGate( orGate( G[2], andGate(P[2], G[1])), andGate(P[2],andGate(P[1],G[0]))), andGate(P[2],andGate(P[1],andGate(P[0],c))));
		C[3] = orGate( orGate( orGate( orGate(G[3],andGate(P[3],G[2])), andGate(P[3],andGate(P[2], G[1]))), andGate(P[3],andGate(P[2],andGate(P[1],G[0])))), andGate(P[3],andGate(P[2],andGate(P[1],andGate(P[0],c)))));
		
		String result = String.valueOf(C[3]) + fullAdder(X[3],Y[3],C[2]).substring(1, 2) + fullAdder(X[2],Y[2],C[1]).substring(1,2) + fullAdder(X[1],Y[1],C[0]).substring(1,2) + fullAdder(X[0],Y[0],c).substring(1,2);
		
		return result;
	}
	
	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		char[] addend = new StringBuffer(operand).reverse().toString().toCharArray();
		char[] carry = new char[operand.length()];
		char[] sum = new char[operand.length()];
		String overflow = "";
		String result = "";
		
		carry[0] = andGate(addend[0], '1');
		sum[0] = XorGate(addend[0], '1');
		result = String.valueOf(sum[0]);
		
		for(int i = 1; i < operand.length(); i++) {
			carry[i] = andGate(addend[i],carry[i-1]);
			sum[i] = XorGate(addend[i],carry[i-1]);
			result = String.valueOf(sum[i]) + result;
		}
		
		overflow = String.valueOf(XorGate(carry[operand.length()-1], carry[operand.length()-2]));
		
		result = overflow + result;
		
		return result;
	}
	
	/**
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		int count = length/4;
		String result ="";
		char[] carry = new char[count+1];
		
		//���в�������ȫ
		if(operand1.length() < length){
			int subLength = length - operand1.length();
			for(int i = 0; i < subLength; i++){
				operand1 = operand1.substring(0, 1) + operand1;
			}
		}
		
		if(operand2.length() < length){
			int subLength = length - operand2.length();
			for(int i = 0; i < subLength; i++){
				operand2 = operand2.substring(0, 1) + operand2;
			}
		}
		
		//���ε�������4λ�ӷ�����������
		carry[0]= c;
		for(int i = count-1; i >= 0; i--) {
			int j = count - 1 - i;
			carry[j+1] = claAdder(operand1.substring(0+i*4,4+i*4), operand2.substring(0+i*4,4+i*4), carry[j]).substring(0,1).toCharArray()[0];
			result = claAdder(operand1.substring(0+i*4,4+i*4), operand2.substring(0+i*4,4+i*4), carry[j]).substring(1,5) + result;
		}
		
		//��Xn��Yn��Sn�����������
		char Xn = operand1.charAt(0);
		char Yn = operand2.charAt(0);
		char Sn = result.charAt(0);
		char overflow = orGate( andGate(andGate(Xn,Yn),notGate(Sn)), andGate(andGate(notGate(Xn),notGate(Yn)),Sn) );
		
		result = String.valueOf(overflow) + result;
		
		return result;
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		String result = adder(operand1, operand2, '0', length);
		return result;
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		String operand3 = negation(operand2);
		String result = adder(operand1, operand3, '1', length);
		return result;
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		
		//�ж��Ƿ���Ҫ�Խ����������(�жϱ������Ƿ�Ϊ�߽�)
		String boundNum = "1";
		boolean isCorrect =false;
		
		for(int i = 0; i< length-1 ; i++){
			boundNum = boundNum + "0";
		}
		
		if(operand1.equals(boundNum)){
			isCorrect = true;
		}
		
		String allOne = "";
		for(int i = 0; i < length; i++){
			allOne = "1" + allOne;
		}
		
		String allZero = "";
		for(int i = 0; i < length; i++){
			allZero = "0" + allZero;
		}
		
		//���в�������ȫ
		if(operand1.length() < length){
			int subLength = length - operand1.length();
			for(int i = 0; i < subLength; i++){
				operand1 = operand1.substring(0, 1) + operand1;
			}
		}
				
		if(operand2.length() < length){
			int subLength = length - operand2.length();
			for(int i = 0; i < subLength; i++){
				operand2 = operand2.substring(0, 1) + operand2;
			}
		}
		
		String X = operand1;
		String _X = oneAdder(negation(operand1)).substring(1);
		String Y = operand2 + "0";
		char[] Yn = new StringBuffer(Y).reverse().toString() .toCharArray();
		int initial = 0;
		
		String product = "";
		for(int i = 0; i < length; i++){
			product = product + "0";
		}
		
		String result = product + Y;
		
		for(int i=0; i < length; i++){
			initial = Yn[i] - Yn[i+1];
			if(initial == 0) {
				result = ariRightShift(result,1);
				Y = result.substring(length, result.length());
			} else if(initial == 1) {
				result = integerAddition(result.substring(0, product.length()), X, length).substring(1, length+1) + Y;
				result = ariRightShift(result,1);
				Y = result.substring(length, result.length());
			} else if(initial == -1){
				result = integerAddition(result.substring(0, product.length()), _X, length).substring(1, length+1) + Y;
				result = ariRightShift(result,1);
				Y = result.substring(length, result.length());
			}
		}
		
		result = result.substring(0,result.length()-1);
		
		if (isCorrect) {
			result = oneAdder(negation(result)).substring(1);
		}
		
		//�����λ���Ƿ����
		if(result.substring(length,length+1).equals("1")){
			if(result.substring(0,length).equals(allOne)){
				result = "0" + result.substring(length);
			} else {		
				result = "1" + result.substring(length);
			}
		} else if(result.substring(0,1).equals("0")) {
			if(result.substring(0,length).equals(allZero)){
				result = "0" + result.substring(length);
			} else {		
				result = "1" + result.substring(length);
			}
		}
		
		return result;
	}

	/**
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		
		String result = "";
		
		//���в�������ȫ
		String dividend = operand1;
		if(operand1.length() < length){
			int subLength = length - operand1.length();
			for(int i = 0; i < subLength; i++){
				dividend = operand1.substring(0, 1) + dividend;
			}
		}
		
		String divisor = operand2;
		if(operand2.length() < length){
			int subLength = length - operand2.length();
			for(int i = 0; i < subLength; i++){
				divisor = operand2.substring(0, 1) + divisor;
			}
		}
		
		//�жϱ������Ƿ�Ϊ0
		String allZero ="";
		for(int i = 0; i < length; i++){
			allZero = "0" + allZero;
		}
		if(dividend.equals(allZero)) {
			result = "0" + allZero + allZero;
			return result;
		}
		
		String remainder = "";
		String quotient = "";
		for(int i = 0; i < length; i++){
			remainder = dividend.substring(0,1) + remainder; 
		}
		quotient = dividend;
		
		//��һ������
		if(dividend.substring(0,1).equals(divisor.substring(0,1))){
			remainder = integerSubtraction(remainder, divisor, length).substring(1);
		} else {
			remainder = integerAddition(remainder, divisor, length).substring(1);
		}
		
		if(remainder.substring(0,1).equals(divisor.substring(0,1))){
			quotient = quotient + "1";
		} else {
			quotient = quotient + "0";
		}
		
		//�м������
		for(int i = 0; i < length ;i++){
			remainder = leftShift(remainder + quotient,1).substring(0, length);
			quotient = leftShift(remainder + quotient,1).substring(length,2*length);
			
			//���ж�����Ϊ0�������������ʱ�����������������Ŵ���,�����ж������ͳ����Ƿ���ţ�����ȡ��ͬ�Ĵ���ʽ
			if(remainder.equals(allZero)){
				remainder = integerAddition(remainder, divisor, length).substring(1);
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				remainder = integerSubtraction(remainder, divisor, length).substring(1);
			} else {
				remainder = integerAddition(remainder, divisor, length).substring(1);
			}
			
			//���ж�����Ϊ0�������������ʱ�����������������Ŵ���,�����ж������ͳ����Ƿ���ţ�����ȡ��ͬ�Ĵ���ʽ
			if(remainder.equals(allZero)){
				quotient = quotient + "0";
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				quotient = quotient + "1";
			} else {
				quotient = quotient + "0";
			}
		}
		
		//�����ս����������
		quotient = leftShift(quotient,1).substring(0, length);
		if(quotient.substring(0,1).equals("1")){
			quotient = oneAdder(quotient).substring(1,length+1);
		} 
		
		if(!dividend.substring(0,1).equals(remainder.substring(0,1))){
			if(dividend.substring(0,1).equals(divisor.substring(0,1))){
				remainder = integerAddition(remainder, divisor, length).substring(1);
			} else {
				remainder = integerSubtraction(remainder, divisor, length).substring(1);
			}
		}
				
		result = "0" + quotient + remainder;
		
		return result;
	}
	
	/**
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		
		String allZero = "";
		for(int i = 0; i < length; i++){
			allZero = "0" + allZero;
		}
		
		//���в�������ȫ
		if(operand1.length()-1 < length){
    		int subLength = length - operand1.length() + 1;
    		for(int i = 0; i < subLength; i++){
				operand1 = operand1.substring(0, 1) + "0" +operand1.substring(1);
			}
		}
				
		if(operand2.length()-1 < length){
    		int subLength = length - operand2.length() + 1;
    		for(int i = 0; i < subLength; i++){
				operand2 = operand2.substring(0, 1) + "0" +operand2.substring(1);
			}
		}
		
		//�Ը�����д���
		if(operand1.substring(0,1).equals("1") && operand1.substring(1).equals(allZero)) {
			operand1 = "0" + allZero;
		} 
		if(operand2.substring(0,1).equals("1") && operand2.substring(1).equals(allZero)) {
			operand2 = "0" + allZero;
		} 
		
		//��ֵ����
		char sign1 = operand1.charAt(0);
		String value1 = operand1.substring(1);
		char sign2 = operand2.charAt(0);
		String value2 = operand2.substring(1);
		
		String sign = "";
		String value = "";
		String overflow = "0";
		
		if(XorGate(sign1, sign2) == '0'){
			String tempResult = integerAddition("0000" + value1, "0000" + value2, length + 4);
			if(!tempResult.substring(0, 5).equals("00000")){
				overflow = "1";
			}
			sign = String.valueOf(sign1);
			value = tempResult.substring(5);
		} else {
			String value3 = oneAdder(negation(value2)).substring(1);
			String tempResult = integerAddition("0000" + value1, "0000" + value3, length + 4);
			if(!tempResult.substring(0,5).equals("00000")){
				sign = String.valueOf(sign1);
				value = tempResult.substring(5);
			} else {
				sign = String.valueOf(sign2);
				value = oneAdder(negation(tempResult.substring(5))).substring(1);
			}
		}
				
		if(value.equals(allZero)){
			sign = "0";
		}
		
		String result = overflow  + sign + value;
		
		return result;
	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		String Z = "";
		String overflow = "0";
		
		String X = operand1;
		String Xsign = X.substring(0, 1);
		String Xexponent = X.substring(1,eLength+1);
		String Xsignificant = X.substring(eLength+1);
		
		String Y = operand2;
		String Ysign = Y.substring(0, 1);
		String Yexponent = Y.substring(1,eLength+1);
		String Ysignificant = Y.substring(eLength+1);
		
		String eAllOne = "";
		for(int i = 0; i < eLength; i++){
			eAllOne = "1" + eAllOne;
		}
		
		String eAllZero = "";
		for(int i = 0; i < eLength; i++){
			eAllZero = "0" + eAllZero;
		}
		
		String sAllZero = "";
		for(int i = 0; i < sLength; i++){
			sAllZero = "0" + sAllZero;
		}
		
		String guard = "";
		for(int i = 0; i < gLength; i++){
			guard = "0" + guard;
		}
		
		//�ж�X�Ƿ�Ϊ0
		if(Xexponent.equals(eAllOne) && Xsignificant.equals(sAllZero)){
			Z = overflow + Y;
			return Z;
		}
		
		//�ж�Y�Ƿ�Ϊ0
		if(Yexponent.equals(eAllOne) && Ysignificant.equals(sAllZero)){
			Z = overflow + X;
			return Z;
		}

		boolean isXLarger = false;
		boolean isEqual = true;

		//�ж�X��Y�Ľ����Ƿ����
		boolean isHidden = true;
		while(!Xexponent.equals(Yexponent)){
			
			isEqual = false;
			String tempStr = "";
			
			if(Integer.parseInt(integerTrueValue(Xexponent)) > Integer.parseInt(integerTrueValue(Yexponent))){
				
				isXLarger = true;
				
				if(isHidden){
					if(Yexponent.equals(eAllZero)){
						tempStr = "0" + Ysignificant + guard.substring(0, gLength-1);
						isHidden = false;
					} else {
						tempStr = "1" + Ysignificant + guard.substring(0, gLength-1);
						isHidden = false;
					}
				} else {
					tempStr = logRightShift(Ysignificant + guard, 1);
				}
				
				Ysignificant = tempStr.substring(0, sLength);
				guard = tempStr.substring(sLength);
				Yexponent = oneAdder(Yexponent).substring(1);
				
				if(Integer.parseInt(integerTrueValue(Ysignificant)) == 0){
					Z = overflow + X;
					return Z;
				}
				
			} else {
				
				if(isHidden){
					if(Xexponent.equals(eAllZero)){
						tempStr = "0" + Xsignificant + guard.substring(0, gLength-1);
						isHidden = false;
					} else {
						tempStr = "1" + Xsignificant + guard.substring(0, gLength-1);
						isHidden = false;
					}
				} else {
					tempStr = logRightShift(Xsignificant + guard, 1);
				}
				
				Xsignificant = tempStr.substring(0, sLength);
				guard = tempStr.substring(sLength);
				Xexponent = oneAdder(Xexponent).substring(1);
				
				if(Integer.parseInt(integerTrueValue(Xsignificant)) == 0){
					Z = overflow + Y;
					return Z;
				}	
			}
			
		} 
				
		String tempStr;
		
		if(!isEqual){
			if(isXLarger){
				tempStr = signedAddition(Xsign + "1" + Xsignificant, Ysign + "0" + Ysignificant, 4*(sLength+1));
			} else {
				tempStr = signedAddition(Xsign + "0" + Xsignificant, Ysign + "1" + Ysignificant, 4*(sLength+1));
			}
		} else {
			tempStr = signedAddition(Xsign + "1" + Xsignificant, Ysign + "1" + Ysignificant, 4*(sLength+1));
		}
		
		String Zsignificant = "";
		if(tempStr.substring(2 + 3*(sLength+1) - 1).substring(0,1).equals("1")){
			overflow = "1";
			Zsignificant = tempStr.substring(2 + 3*(sLength+1) - 1);
		} else {
			overflow = "0";
			Zsignificant = tempStr.substring(2 + 3*(sLength+1));
		}
		String Zsign = tempStr.substring(1,2);
		String Zexponent = Xexponent;
		
		//����β���Ƿ�Ϊ0
		if(Integer.parseInt(integerTrueValue(Zsignificant)) == 0 ){
			Z = overflow + "0" + eAllZero + sAllZero;
			return Z;
		}
		
		//����β���Ƿ����
		if(overflow.equals("1")){
			tempStr = Zsignificant + guard.substring(0, gLength-1);
			Zsignificant = tempStr.substring(0, sLength+1);
			guard = tempStr.substring(sLength+1);
			String tempExponent = oneAdder(Xexponent);
			//��������Ƿ�����
			if(tempExponent.substring(0, 1).equals("1")){
				Z = overflow + Zsign + eAllOne + sAllZero;
				return Z;
			} else {
				Zexponent = tempExponent.substring(1);
				overflow = "0";
			}
		}
		
		if(Zexponent.equals(eAllOne)){
			Z = "1" + Zsign + eAllOne + sAllZero;
			return Z;
		}
		
		//�������Ƿ���
		if(Zsignificant.substring(0,1).equals("1")){
			Z = overflow + Zsign + Zexponent + (Zsignificant+guard).substring(1,sLength+1);
			return Z;
		} 
		
		//���й��
		Zsignificant = Zsignificant.substring(1,sLength+1);
		Zexponent = integerSubtraction("0000"+Zexponent, "01", eLength+4);
		Zexponent = Zexponent.substring(5);
				
		//����β������������
		while(Zsignificant.substring(0,1).equals("0")){
			
			//�жϽ�������
			if(Zexponent.equals(eAllZero)){
				Z = overflow + "0" + eAllZero + sAllZero;
				return Z;
			}
			
			tempStr = leftShift(Zsignificant + guard,1);
			Zsignificant = tempStr.substring(0,sLength);
			guard = tempStr.substring(sLength);
			Zexponent = integerSubtraction("0000"+Zexponent, "01", eLength+4);
			overflow = Zexponent.substring(0,1);
			Zexponent = Zexponent.substring(5);
			
		}
		
		if(Zexponent.equals(eAllZero)){
			Z = overflow + Zsign + Zexponent + (Zsignificant+guard).substring(0, sLength);
		} else {
			Z = overflow + Zsign + Zexponent + (Zsignificant+guard).substring(1, 1+sLength);
		}
		
		return Z;
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		String operand3 = negation(operand2.substring(0,1)) + operand2.substring(1);
		String result = floatAddition(operand1, operand3, eLength, sLength, gLength);
		return result;
	}
	
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
	
		String X = operand1;
		String Xsign = X.substring(0, 1);
		String Xexponent = X.substring(1,eLength+1);
		String Xsignificant = X.substring(eLength+1);
		
		String Y = operand2;
		String Ysign = Y.substring(0, 1);
		String Yexponent = Y.substring(1,eLength+1);
		String Ysignificant = Y.substring(eLength+1);
		
		String sign = String.valueOf(XorGate(Xsign.toCharArray()[0],Ysign.toCharArray()[0]));
		//�����ƫֵ��ȫһָ����ȫ��ָ����ȫ��β��
		int bias= (int)(Math.pow(2, eLength-1)) - 1;
		
		String eAllOne = "";
		for(int i = 0; i < eLength; i++){
			eAllOne = "1" + eAllOne;
		}
		
		String eAllZero = "";
		for(int i = 0; i < eLength; i++){
			eAllZero = "0" + eAllZero;
		}
		
		String sAllZero = "";
		for(int i = 0; i < sLength; i++){
			sAllZero = "0" + sAllZero;
		}
		
		String result = "";
		
		//�ж�X��Y�Ƿ�Ϊ0
		if(Xexponent.equals(eAllZero) && Xsignificant.equals(sAllZero)){
			result = "0" + "0" + eAllZero +sAllZero; 
			return result;
		} 
		
		if(Yexponent.equals(eAllZero) && Ysignificant.equals(sAllZero)){
			result = "0" + "0" + eAllZero +sAllZero; 
			return result;
		} 
		
		//������ӣ��ټ�ȥƫֵ
		String tempAddition = signedAddition("0"+Xexponent, "0"+Yexponent, 4*eLength).substring(2);
		String tempExponent = signedAddition("0" + tempAddition, "1"+integerRepresentation(String.valueOf(bias),eLength), 4*eLength);
	
		String overflow = "0";
		if(!integerTrueValue(tempExponent.substring(2,2 + 3*eLength)).equals("0")){
			overflow = "1";
		}
		String underflow = tempExponent.substring(1,2);
		String exponent = tempExponent.substring(2 + 3*eLength);

		//�������Ƿ�����
		if(overflow.equals("1")||exponent.equals(eAllOne)){
			result = "1" + sign + eAllOne + sAllZero;
			return result;
		}
		
		//�������Ƿ�����
		if(underflow.equals("1")){
			result = "0" + "0" + eAllZero + sAllZero;
			return result;
		}
		
		//��β�����г˷�
		String XHide = "";
		String YHide = "";
		
		if(Xexponent.equals(eAllZero)){
			XHide = "00";
		} else {
			XHide = "01";
		}
		
		if(Yexponent.equals(eAllZero)){
			YHide = "00";
		} else {
			YHide = "01";
		}
		
		String tempSignificant = integerMultiplication( XHide+Xsignificant, YHide+Ysignificant, 4*sLength);
		tempSignificant = tempSignificant.substring(2*sLength - 1);
		
		
		//���й��
		if(tempSignificant.substring(0,2).equals("01")){
			result = "0" + sign + exponent + tempSignificant.substring(2, 2+sLength);
		} else if(tempSignificant.substring(0, 2).equals("10")) {
			exponent = oneAdder("0" + exponent).substring(2);
			if(exponent.equals(eAllOne)){
				result = "1" + sign + eAllOne + sAllZero;
			} else {
				result = "0" + sign + exponent + tempSignificant.substring(2, 2+sLength);
			}
		} else {
			
			if(exponent.equals(eAllZero) && tempSignificant.substring(0,1).equals("0")){
				result = "0" + "0" + eAllZero + sAllZero;
				return result;
			}
			
			tempSignificant = tempSignificant.substring(2);
			exponent = integerSubtraction("0000"+exponent, "01", 4*eLength);
			exponent = exponent.substring(1+3*eLength);
			
			while(tempSignificant.substring(0,1).equals("0")){
				
				//�жϽ�������
				if(exponent.equals(eAllZero)){
					result = overflow + "0" + eAllZero + sAllZero;
				}
				
				String tempStr = leftShift(tempSignificant,1);
				tempSignificant = tempStr.substring(0,2*sLength);
				exponent = integerSubtraction("0000"+exponent, "01", 4*eLength);
				overflow = exponent.substring(1 + 3*eLength -1, 1 + 3*eLength);
				exponent = exponent.substring(1 + 3*eLength);
				
			}
			
			if(exponent.equals(eAllZero)){
				result = overflow + sign + exponent + tempSignificant.substring(0, sLength);
			} else {
				result = overflow + sign + exponent + tempSignificant.substring(1, sLength+1);
			}

		}
		
		return result;
	}
	
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		
		String X = operand1;
		String Xsign = X.substring(0, 1);
		String Xexponent = X.substring(1,eLength+1);
		String Xsignificant = X.substring(eLength+1);
		
		String Y = operand2;
		String Ysign = Y.substring(0, 1);
		String Yexponent = Y.substring(1,eLength+1);
		String Ysignificant = Y.substring(eLength+1);
		
		String sign = String.valueOf(XorGate(Xsign.toCharArray()[0],Ysign.toCharArray()[0]));
		
		//�����ƫֵ��ȫһָ����ȫ��ָ����ȫ��β��
		int bias= (int)(Math.pow(2, eLength-1)) - 1;
		
		String eAllOne = "";
		for(int i = 0; i < eLength; i++){
			eAllOne = "1" + eAllOne;
		}
		
		String eAllZero = "";
		for(int i = 0; i < eLength; i++){
			eAllZero = "0" + eAllZero;
		}
		
		String sAllZero = "";
		for(int i = 0; i < sLength; i++){
			sAllZero = "0" + sAllZero;
		}
		
		String result = "";
		
		//�жϱ�����X�Ƿ�Ϊ0
		if(Xexponent.equals(eAllZero) && Xsignificant.equals(sAllZero)){
			result = "0" + "0" + eAllZero +sAllZero; 
			return result;
		} 
		
		//����������ټ���ƫֵ
		String tempExponent = signedAddition(signedAddition("0"+Xexponent, "1"+Yexponent, eLength).substring(1), "0"+integerRepresentation(String.valueOf(bias),eLength), eLength);
	
		String overflow = tempExponent.substring(0,1);
		String underflow = tempExponent.substring(1,2);
		String exponent = tempExponent.substring(2);

		//�������Ƿ�����
		if(overflow.equals("1")||exponent.equals(eAllOne)){
			result = "1" + sign + eAllOne + sAllZero;
			return result;
		}
		
		//�������Ƿ�����
		if(underflow.equals("1")){
			result = "1" + "0" + eAllZero + sAllZero;
		}
				
		//��β�����г���
		String XHide = "";
		String YHide = "";
		
		if(Xexponent.equals(eAllZero)){
			XHide = "00";
		} else {
			XHide = "01";
		}
		
		if(Yexponent.equals(eAllZero)){
			YHide = "00";
		} else {
			YHide = "01";
		}
		
		String tempSignificant = integerDivision( XHide+Xsignificant, YHide+Ysignificant, sLength+5);
		tempSignificant = tempSignificant.substring(4+sLength,2*sLength+6);
		
		//���й��
		if(tempSignificant.substring(0,2).equals("01")){
			result = "0" + sign + exponent + tempSignificant.substring(2, 2+sLength);
		} else if(tempSignificant.substring(0, 2).equals("10")) {
			exponent = oneAdder("0" + exponent).substring(2);
			if(exponent.equals(eAllOne)){
				result = "1" + sign + eAllOne + sAllZero;
			} else {
				result = "0" + sign + exponent + tempSignificant.substring(2, 2+sLength);
			}
		} else {
			tempSignificant = tempSignificant.substring(2);
			exponent = integerSubtraction("0000"+exponent, "01", eLength+4);
			exponent = exponent.substring(5);
			
			while(tempSignificant.substring(0,1).equals("0")){
				
				//�жϽ�������
				if(exponent.equals(eAllZero)){
					result = overflow + "0" + eAllZero + sAllZero;
				}
				
				String tempStr = leftShift(tempSignificant,1);
				tempSignificant = tempStr.substring(0,sLength);
				exponent = integerSubtraction("0000"+exponent, "01", eLength+4);
				overflow = exponent.substring(0,1);
				exponent = exponent.substring(5);
				
			}
			
			if(exponent.equals(eAllZero)){
				result = overflow + sign + exponent + tempSignificant.substring(0, sLength);
			} else {
				result = overflow + sign + exponent + tempSignificant.substring(0, sLength);
			}

		}
		
		return result;
	}
}
