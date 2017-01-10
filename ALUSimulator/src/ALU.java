/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author [151250077_李果]
 *
 */

public class ALU {
	
	//与门
	public char andGate (char number1, char number2) {
		if(number1 == '1' && number2 == '1') {
			return '1';
		} else {
			return '0';
		}
	}
	
	//或门
	public char orGate (char number1, char number2) {
		if(number1 == '1' || number2 == '1') {
			return '1';
		} else {
			return '0';
		}
	}

	//异或门
	public char XorGate (char number1, char number2) {
		if(number1 == '1' && number2 == '0') {
			return '1';
		} else if(number1 == '0' && number2 == '1') {
			return '1';
		} else {
			return '0';
		}
	}
	
	//非门
	public char notGate(char number) {
		if(number == '1') {
			return '0';
		} else {
			return '1';
		}
	}

	//带符号整数的乘法
	public String signedMultiplication (String operand1, String operand2, int length) {
		
		//进行操作数补全
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
			
		//符值分离
		char sign1 = operand1.substring(0,1).toCharArray()[0];
		char sign2 = operand2.substring(0,1).toCharArray()[0];			
			
		String value1 = operand1.substring(1);
		String value2 = operand2.substring(1);

		char sign = XorGate(sign1, sign2);
		String value = integerMultiplication(value1, value2, length).substring(1);
		
		String result = String.valueOf(sign) + value;
		
		return result;
	}

	//浮点数的有效数除法
	public String significantDivision (String operand1, String operand2, int length) {
		
		String result = "";
		
		//进行操作数补全
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
		
		//判断被除数是否为0
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
		
		//第一次运算
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
		
		//中间的运算
		for(int i = 0; i < length ;i++){
			remainder = leftShift(remainder + quotient,1).substring(0, length);
			quotient = leftShift(remainder + quotient,1).substring(length,2*length);
			
			//先判定余数为0的特殊情况，这时候视作余数与除数异号处理,否则，判断余数和除数是否异号，并采取不同的处理方式
			if(remainder.equals(allZero)){
				remainder = integerAddition(remainder, divisor, length).substring(1);
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				remainder = integerSubtraction(remainder, divisor, length).substring(1);
			} else {
				remainder = integerAddition(remainder, divisor, length).substring(1);
			}
			
			//先判定余数为0的特殊情况，这时候视作余数与除数异号处理,否则，判断余数和除数是否异号，并采取不同的处理方式
			if(remainder.equals(allZero)){
				quotient = quotient + "0";
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				quotient = quotient + "1";
			} else {
				quotient = quotient + "0";
			}
		}
		
		//对最终结果进行修正
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
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		int tempNumber = Integer.parseInt(number);
		String result = "";
		boolean isInvert = false;
		
		//先判断根据第一位判断是否为负数
		if( number.charAt(0) == '-' ){
			tempNumber = Integer.parseInt(number.substring(1, number.length()));
			isInvert = true;
		}
		
		//除二取余法
		while(true) {
			result =  (tempNumber % 2) + result;
			if( tempNumber / 2 == 0 ){
				break;
			}
			tempNumber = tempNumber / 2;
		} 
		
		//如果是负数，则取反加一
		if(isInvert) {
			
			char[] tempChar = result.toCharArray();
			
			//逐位取反
			for(int num = 0; num < tempChar.length; num ++){
				tempChar[num] = notGate(tempChar[num]);
			}
			
			char carry = '1';
			
			//进行加一
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
		
		//如果位数不足，则进行补位
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
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {

		String sign = "";
		String result = "";
		String exponent = "";
		String significant = "";
		
		//计算出偏值
		int bias= (int)(Math.pow(2, eLength-1)) - 1;
		
		//计算出对应的指数全一字符串和尾数全零字符串
		String eAllOne = "";
		for(int i = 0; i < eLength; i++){
			eAllOne = "1" + eAllOne;
		}
		String sAllZero = "";
		for(int i = 0; i < sLength; i++){
			sAllZero = "0" + sAllZero;
		}
	
		//进行是否为NaN的判断
		if(number.equals("NaN")) {
			result = "NaN";
			return result;
		}
	
		//进行是否为正负无穷大的判断
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
		
		//确定符号位
		sign = "0";
		
		if(number.charAt(0) == '-'){
			sign = "1";
			number = number.substring(1,number.length());
		}
		
		//将数值部分分为整数部分和小数部分
		String[] tempNumber = number.split("\\.");
		int integer = Integer.parseInt(tempNumber[0]);
		int tempInteger = integer;
		String decimal = tempNumber[1];
				
		//进行是否为0的判断
		if( tempInteger == 0 && Double.parseDouble("0." + decimal) == 0 ){
			for(int num = 0; num < (1+eLength+sLength); num ++){
				result = result + "0";
			}
			return result;
		}
		
		String binInteger = "";
		String binDecimal = "";
		
		//除二取余法计算出整数部分的二进制表示
		while(true) {
			binInteger =  (tempInteger % 2) + binInteger;
			if( tempInteger / 2 == 0 ){
				break;				
			}
			tempInteger = tempInteger / 2;
		} 
		
		//乘二取整法计算出小数部分的二进制表示
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
			
			//判断小数部分是否为零
			if(tempResult == 0){
				break;
			}
			
			count ++;
		}
		
		//判定整数部分是否为0，并采取不同的规格化方法
		int tempExponent = 0;
				
		if( integer != 0){
			//将整数部分和小数部分组合在一起，进行规格化
			significant = binInteger.substring(1,binInteger.length()) + binDecimal;
			
			//真指数+偏值
			tempExponent = bias + (binInteger.length() - 1);
			
		} else {
			//找到小数部分中第一个不是0的位数
			int location = 0;
			String tempStr = "";
			
			do {
				if(location == bias){
					break;
				}
				tempStr = binDecimal.substring(location,location + 1);
				location ++;
			} while(tempStr.equals("0"));
			
			//如果指数已经为0，则采用反规格化，从1前面一位截取
			if(location != bias){
				significant = binDecimal.substring(location);
			} else {
				significant = binDecimal.substring(location-1);
			}
	
			//真指数+偏值
			tempExponent = bias - location;
		}
		
		while(true) {
			exponent =  (tempExponent % 2) + exponent;
			if( tempExponent / 2 == 0 ){
				break;				
			}
			tempExponent = tempExponent / 2;
		}
		
		//判断尾数位数，少就用0补位，多就向0舍去
		if(significant.length() <= sLength){
			int fillLength = sLength - significant.length();
			for(int i = 0; i < fillLength; i++){
				significant = significant + "0";
			} 
		} else {
			significant = significant.substring(0, sLength);
		}
		
		//判断指数位数，少就用0补位
		if(exponent.length() <= eLength){
			int fillLength = eLength - exponent.length();
			for(int i = 0; i < fillLength; i++){
				exponent = "0" + exponent ;
			} 
		} else {
			exponent = exponent.substring(0, sLength);
		}
					
		//检测是否上溢，如果溢出则返回无穷大
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
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
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
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		
		int tempNumber = 0;
		int location = operand.length()-1;
		char[] tempChar = operand.toCharArray();
		String result = "";
		
		//利用二进制补码转换公式进行转换
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
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		
		String result = "";
		double decimal = 0;
		int eValue = 0;
		String exponent = "";
		String mantissa = "";
		
		//计算出偏值，全一指数，全零指数，全零尾数
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
		
		//判断是否为正负无穷大和NaN
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
		
		//判断是否为0或者反规格化数
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
		
		//按规格化数进行处理
		//算出小数部分
		for(int location = 0; location < sLength; location ++){
			String tempNumber = mantissa.substring(location,location + 1);
			decimal = Integer.parseInt(tempNumber)*Math.pow(2,(-1)-location) + decimal;
		}
		
		//算出指数部分
		for(int location = eLength - 1; location >= 0; location --){
			String tempNumber = exponent.substring(location,location + 1);
			eValue = (int)(Integer.parseInt(tempNumber)*Math.pow(2,eLength - location -1)) + eValue;
		}
		
		//（1+小数部分）* 指数
		result = sign + String.valueOf((1 + decimal)*(Math.pow(2, eValue - bias)));
		return result;
		
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
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
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		
		String result = operand;
		
		for(int num = 0; num < n; num ++){
			//左移1位，末尾补0，首位舍去
			result = result + "0";
			result = result.substring(1, result.length());
		}
		
		return result;
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		
		String result = operand;
		
		for(int num = 0; num < n; num ++){
			//右移1位，末位舍去，首位补0
			result = "0" + result;
			result = result.substring(0, result.length()-1);
		}
		
		return result;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		
		String result = operand;
		
		String sign = operand.substring(0,1);
		
		for(int num = 0; num < n; num ++){
			//右移1位，末位舍去，首位补符号位
			result = sign + result;
			result = result.substring(0, result.length()-1);
		}
		
		return result;
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
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
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
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
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
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
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		int count = length/4;
		String result ="";
		char[] carry = new char[count+1];
		
		//进行操作数补全
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
		
		//依次调用先行4位加法器进行运算
		carry[0]= c;
		for(int i = count-1; i >= 0; i--) {
			int j = count - 1 - i;
			carry[j+1] = claAdder(operand1.substring(0+i*4,4+i*4), operand2.substring(0+i*4,4+i*4), carry[j]).substring(0,1).toCharArray()[0];
			result = claAdder(operand1.substring(0+i*4,4+i*4), operand2.substring(0+i*4,4+i*4), carry[j]).substring(1,5) + result;
		}
		
		//用Xn，Yn，Sn进行溢出检验
		char Xn = operand1.charAt(0);
		char Yn = operand2.charAt(0);
		char Sn = result.charAt(0);
		char overflow = orGate( andGate(andGate(Xn,Yn),notGate(Sn)), andGate(andGate(notGate(Xn),notGate(Yn)),Sn) );
		
		result = String.valueOf(overflow) + result;
		
		return result;
	}
	
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		String result = adder(operand1, operand2, '0', length);
		return result;
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		String operand3 = negation(operand2);
		String result = adder(operand1, operand3, '1', length);
		return result;
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		
		//判断是否需要对结果进行修正(判断被乘数是否为边界)
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
		
		//进行操作数补全
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
		
		//检查结果位数是否溢出
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
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		
		String result = "";
		
		//进行操作数补全
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
		
		//判断被除数是否为0
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
		
		//第一次运算
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
		
		//中间的运算
		for(int i = 0; i < length ;i++){
			remainder = leftShift(remainder + quotient,1).substring(0, length);
			quotient = leftShift(remainder + quotient,1).substring(length,2*length);
			
			//先判定余数为0的特殊情况，这时候视作余数与除数异号处理,否则，判断余数和除数是否异号，并采取不同的处理方式
			if(remainder.equals(allZero)){
				remainder = integerAddition(remainder, divisor, length).substring(1);
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				remainder = integerSubtraction(remainder, divisor, length).substring(1);
			} else {
				remainder = integerAddition(remainder, divisor, length).substring(1);
			}
			
			//先判定余数为0的特殊情况，这时候视作余数与除数异号处理,否则，判断余数和除数是否异号，并采取不同的处理方式
			if(remainder.equals(allZero)){
				quotient = quotient + "0";
			} else if(remainder.substring(0,1).equals(divisor.substring(0,1))){
				quotient = quotient + "1";
			} else {
				quotient = quotient + "0";
			}
		}
		
		//对最终结果进行修正
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
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		
		String allZero = "";
		for(int i = 0; i < length; i++){
			allZero = "0" + allZero;
		}
		
		//进行操作数补全
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
		
		//对负零进行处理
		if(operand1.substring(0,1).equals("1") && operand1.substring(1).equals(allZero)) {
			operand1 = "0" + allZero;
		} 
		if(operand2.substring(0,1).equals("1") && operand2.substring(1).equals(allZero)) {
			operand2 = "0" + allZero;
		} 
		
		//符值分离
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
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
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
		
		//判断X是否为0
		if(Xexponent.equals(eAllOne) && Xsignificant.equals(sAllZero)){
			Z = overflow + Y;
			return Z;
		}
		
		//判断Y是否为0
		if(Yexponent.equals(eAllOne) && Ysignificant.equals(sAllZero)){
			Z = overflow + X;
			return Z;
		}

		boolean isXLarger = false;
		boolean isEqual = true;

		//判断X与Y的阶码是否相等
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
		
		//检验尾数是否为0
		if(Integer.parseInt(integerTrueValue(Zsignificant)) == 0 ){
			Z = overflow + "0" + eAllZero + sAllZero;
			return Z;
		}
		
		//检验尾数是否溢出
		if(overflow.equals("1")){
			tempStr = Zsignificant + guard.substring(0, gLength-1);
			Zsignificant = tempStr.substring(0, sLength+1);
			guard = tempStr.substring(sLength+1);
			String tempExponent = oneAdder(Xexponent);
			//检验阶码是否上溢
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
		
		//检验结果是否规格化
		if(Zsignificant.substring(0,1).equals("1")){
			Z = overflow + Zsign + Zexponent + (Zsignificant+guard).substring(1,sLength+1);
			return Z;
		} 
		
		//进行规格化
		Zsignificant = Zsignificant.substring(1,sLength+1);
		Zexponent = integerSubtraction("0000"+Zexponent, "01", eLength+4);
		Zexponent = Zexponent.substring(5);
				
		//左移尾数，减量阶码
		while(Zsignificant.substring(0,1).equals("0")){
			
			//判断阶码下溢
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
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		String operand3 = negation(operand2.substring(0,1)) + operand2.substring(1);
		String result = floatAddition(operand1, operand3, eLength, sLength, gLength);
		return result;
	}
	
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
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
		//计算出偏值，全一指数，全零指数，全零尾数
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
		
		//判断X，Y是否为0
		if(Xexponent.equals(eAllZero) && Xsignificant.equals(sAllZero)){
			result = "0" + "0" + eAllZero +sAllZero; 
			return result;
		} 
		
		if(Yexponent.equals(eAllZero) && Ysignificant.equals(sAllZero)){
			result = "0" + "0" + eAllZero +sAllZero; 
			return result;
		} 
		
		//阶码相加，再减去偏值
		String tempAddition = signedAddition("0"+Xexponent, "0"+Yexponent, 4*eLength).substring(2);
		String tempExponent = signedAddition("0" + tempAddition, "1"+integerRepresentation(String.valueOf(bias),eLength), 4*eLength);
	
		String overflow = "0";
		if(!integerTrueValue(tempExponent.substring(2,2 + 3*eLength)).equals("0")){
			overflow = "1";
		}
		String underflow = tempExponent.substring(1,2);
		String exponent = tempExponent.substring(2 + 3*eLength);

		//检测阶码是否上溢
		if(overflow.equals("1")||exponent.equals(eAllOne)){
			result = "1" + sign + eAllOne + sAllZero;
			return result;
		}
		
		//检测阶码是否下溢
		if(underflow.equals("1")){
			result = "0" + "0" + eAllZero + sAllZero;
			return result;
		}
		
		//对尾数进行乘法
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
		
		
		//进行规格化
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
				
				//判断阶码下溢
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
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
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
		
		//计算出偏值，全一指数，全零指数，全零尾数
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
		
		//判断被除数X是否为0
		if(Xexponent.equals(eAllZero) && Xsignificant.equals(sAllZero)){
			result = "0" + "0" + eAllZero +sAllZero; 
			return result;
		} 
		
		//阶码相减，再加上偏值
		String tempExponent = signedAddition(signedAddition("0"+Xexponent, "1"+Yexponent, eLength).substring(1), "0"+integerRepresentation(String.valueOf(bias),eLength), eLength);
	
		String overflow = tempExponent.substring(0,1);
		String underflow = tempExponent.substring(1,2);
		String exponent = tempExponent.substring(2);

		//检测阶码是否上溢
		if(overflow.equals("1")||exponent.equals(eAllOne)){
			result = "1" + sign + eAllOne + sAllZero;
			return result;
		}
		
		//检测阶码是否下溢
		if(underflow.equals("1")){
			result = "1" + "0" + eAllZero + sAllZero;
		}
				
		//对尾数进行除法
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
		
		//进行规格化
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
				
				//判断阶码下溢
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
