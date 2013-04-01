package com.marcrazysoftware.voicemessenger;

import java.util.ArrayList;
import java.util.List;

/**
 * A "Toolbox" of methods for comparing Strings with similar sounds.
 * 
 * @author Daniel Marchese
 * 
 */
public abstract class StringMethods {

	protected static int levenshteinDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	private static final String getCode(char c) {
		switch (c) {
		case 'B':
		case 'F':
		case 'P':
		case 'V':
			return "1";
		case 'C':
		case 'G':
		case 'J':
		case 'K':
		case 'Q':
		case 'S':
		case 'X':
		case 'Z':
			return "2";
		case 'D':
		case 'T':
			return "3";
		case 'L':
			return "4";
		case 'M':
		case 'N':
			return "5";
		case 'R':
			return "6";
		default:
			return "";
		}
	}

	protected static String soundex(String s) {
		String code, previous, soundex;
		code = s.toUpperCase().charAt(0) + "";
		previous = "7";
		for (int i = 1; i < s.length(); i++) {
			String current = getCode(s.toUpperCase().charAt(i));
			if (current.length() > 0 && !current.equals(previous)) {
				code = code + current;
			}
			previous = current;
		}
		soundex = (code + "0000").substring(0, 4);
		return soundex;
	}
	
	protected static List<String> tokenize(String s) {
		List<String> result = new ArrayList<String>();
		
		int counter = 0;
		while (counter < s.length()) {
			String word = "";
			while (s.charAt(counter) != ' ' && counter < s.length()) {
				word += s.charAt(counter);
				counter++;
			}
			result.add(word);
			counter++;
		}
		
		return result;
	}

}
