import static org.junit.Assert.*;

import org.junit.Test;

public class Demo {
	ALU t0 = new ALU();


	
	@Test
	public void test2_1() {
		assertEquals(t0.floatRepresentation("0.0", 4, 4), 
//				t2.floatRepresentation("0.0", 4, 4));
				"000000000");
	}
	
	@Test
	public void test2_2() {
		// Inf
		assertEquals(t0.floatRepresentation("300.0", 4, 4), 
				"011110000");
	}
	
	@Test
	public void test2_3() {
		// Inf
		assertEquals(t0.floatRepresentation("-11.875", 8, 11), 
				"11000001001111100000");
	}
	
	@Test
	public void test2_4() {
		// Inf
		assertEquals(t0.floatRepresentation("0.5", 8, 23), 
				"00111111000000000000000000000000");
	}
	
	@Test
	public void test2_5() {
		// Inf
		assertEquals(t0.floatRepresentation("0.125", 8, 23), 
				"00111110000000000000000000000000");
	}
	
	@Test
	public void test2_6() {
		// Inf
		assertEquals(t0.floatRepresentation("45.78", 8, 23), 
				"01000010001101110001111010111000");
	}
	
	@Test
	public void test2_7() {
		// Inf
		assertEquals(t0.floatRepresentation("789.187", 8, 23), 
				"01000100010001010100101111110111");
	}
	
	@Test
	public void test2_8() {
		// Inf
		assertEquals(t0.floatRepresentation("0.375", 8, 23), 
				"00111110110000000000000000000000");
	}
	
	@Test
	public void test2_9() {
		// Inf
		assertEquals(t0.floatRepresentation("0.1", 8, 23), 
				"00111101110011001100110011001100");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test2_10() {
		// Inf
		assertEquals(t0.floatRepresentation("0.33", 8, 23), 
				"00111110101010001111010111000010");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test2_11() {
		// Inf
		assertEquals(t0.floatRepresentation("0.015624", 4, 4), 
				"000001111");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test4_1() {
		assertEquals(t0.integerTrueValue("0000"), 
				"0");
		//       00111101110011001100110011001101
	}
	

	

	
	@Test
	public void test8_1() {
		assertEquals(t0.fullAdder('0', '0', '0'), 
				"00");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test8_2() {
		assertEquals(t0.fullAdder('0', '0', '1'), 
				"01");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test8_3() {
		assertEquals(t0.fullAdder('0', '1', '0'), 
				"01");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test8_4() {
//		System.out.println(t0.fullAdder('0', '1', '1'));
		assertEquals(t0.fullAdder('0', '1', '1'), 
				"10");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test8_5() {
//		System.out.println(t0.fullAdder('0', '1', '1'));
		assertEquals(t0.fullAdder('1', '0', '0'), 
				"01");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test8_6() {
//		System.out.println(t0.fullAdder('0', '1', '1'));
		assertEquals(t0.fullAdder('1', '0', '1'), 
				"10");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test8_7() {
//		System.out.println(t0.fullAdder('0', '1', '1'));
		assertEquals(t0.fullAdder('1', '1', '0'), 
				"10");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test8_8() {
//		System.out.println(t0.fullAdder('0', '1', '1'));
		assertEquals(t0.fullAdder('1', '1', '1'), 
				"11");
		//       00111101110011001100110011001101
	}
	
	@Test
	public void test9_1() {
//		System.out.println(t0.claAdder("1001", "0001", '1'));
		assertEquals(t0.claAdder("1001", "0001", '1'), 
				"01011");
	}
	
	@Test
	public void test9_2() {
		assertEquals(t0.claAdder("1001", "0001", '0'), 
				"01010");
	}
	
	@Test
	public void test9_3() {
		assertEquals(t0.claAdder("1101", "0001", '0'), 
				"01110");
	}
	
	@Test
	public void test9_4() {
		assertEquals(t0.claAdder("0011", "1111", '1'), 
				"10011");
	}
	
	@Test
	public void test10_1() {
//		System.out.println(t0.oneAdder("0000"));
		assertEquals(t0.oneAdder("0000"), 
				"00001");
	}
	
	@Test
	public void test10_2() {
		assertEquals(t0.oneAdder("0111"), 
				"11000");
	}
	
	@Test
	public void test10_3() {
		assertEquals(t0.oneAdder("1111"), 
				"00000");
	}
	
	@Test
	public void test11_1() {
		assertEquals(t0.adder("0000", "0000", '0', 8), 
				"000000000");
	}
	
	@Test
	public void test11_2() {
		assertEquals(t0.adder("1111", "0000", '0', 8), 
				"011111111");
	}
	
	@Test
	public void test11_3() {
		assertEquals(t0.adder("1111", "0001", '0', 8), 
				"000000000");
	}
	
	@Test
	public void test11_4() {
		assertEquals(t0.adder("1111", "1000", '0', 8), 
				"011110111");
	}
	
	@Test
	public void test12_1() {
		assertEquals(t0.integerMultiplication("1000", "1000", 8), 
				"001000000");
	}
	
	@Test
	public void test12_2() {
		assertEquals(t0.integerMultiplication("1000", "1000", 4), 
				"10000");
	}

	@Test
	public void test12_3() {
		assertEquals(t0.integerMultiplication("1001", "0111", 8), 
				"011001111");
	}
	
	@Test
	public void test12_4() {
		assertEquals(t0.integerMultiplication("11000", "010000", 8), 
				"010000000");
	}
	
	@Test
	public void test13_1() {
		assertEquals(t0.integerDivision("0000", "0100", 4), 
				"000000000");
	}
	
	@Test
	public void test13_4() {
		// -7 / 3 = -2 鈥︹�� -1
		assertEquals(t0.integerDivision("1001", "0011", 4), 
				"011101111");
		
	}

	@Test
	public void test14_1() {
		assertEquals(t0.signedAddition("0000", "0000", 4), 
				"000000");
		
	}
	
	@Test
	public void test14_2() {
		assertEquals(t0.signedAddition("0001", "0111", 4), 
				"001000");	
	}
	
	@Test
	public void test14_3() {
		assertEquals(t0.signedAddition("1111", "1111", 4), 
				"011110");		
	}
	
	
	@Test
	public void test14_4() {
		assertEquals(t0.signedAddition("1111", "1111", 8), 
				"0100001110");
	}
	
	@Test
	public void test14_5() {
		assertEquals(t0.signedAddition("1010", "0011", 8), 
				"0000000001");
	}
	
	@Test
	public void test14_6() {
		assertEquals(t0.signedAddition("0011", "1010", 8), 
				"0000000001");
	}
	
	@Test
	public void testFloatMultiplication() {
		assertEquals("000111110011000000",
				this.t0.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
		assertEquals("0011101111", this.t0.floatMultiplication("011101111", "001110000", 4, 4));
		assertEquals("0000000000", this.t0.floatMultiplication("011101111", "100000000", 4, 4));
		assertEquals("0000000000", this.t0.floatMultiplication("001111111", "000000001", 4, 4));
		assertEquals("001000001010100000101000111101010",
				this.t0.floatMultiplication(t0.ieee754("1.4", 32), t0.ieee754("9.3", 32), 8, 23));
		assertEquals("000111110011000000",
				this.t0.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
	}
	
	@Test
	public void test15_1() {
		assertEquals(t0.floatMultiplication("001000000", "000010000", 4, 4), 
				"0000000000");
	}

}
