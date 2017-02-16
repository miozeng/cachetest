package com.mio.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class RandomNumStrUtil {
	
	private static String skey = "fwe4fFRETeV235fdfsd4frSDE564RGFDGTUVd4weFGRHTYJ";

	public static String createRandomAndStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		// key length
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// char or num
			if ("char".equalsIgnoreCase(charOrNum)) {
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				sb.append((char) (random.nextInt(26) + temp));
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				sb.append(String.valueOf(random.nextInt(10)));
			}
		}
		try {
			return new String(sb.toString().getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		if(skey.length() > length){
			return skey.substring(0, length);
		}
		return skey;
	}

	public static int createRandomNum(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
	


}
