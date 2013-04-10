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

	/**
	 * Calculates the levenshtein distance between s1 and s2.
	 * 
	 * @param s1
	 *            The first string to compare.
	 * @param s2
	 *            The second string to compare.
	 * @return levenshteinDistance(s1, s2).
	 */
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

	/**
	 * Retrieves the soundex code for {@code c}.
	 * 
	 * @param c
	 *            The character in question.
	 * @return soundex(c).
	 */
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

	/**
	 * Calculates the soundex code of s.
	 * 
	 * @param s
	 *            The string in question.
	 * @return soundex(s).
	 */
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

	/**
	 * Returns an ArrayList of the soundex strings for each word in s.
	 * 
	 * @param s
	 *            The string to soundexify
	 * @return An Array List of the soundex strings.
	 */
	protected static List<String> soundexify(String s) {
		List<String> tokens = tokenize(s);
		List<String> soundexTokens = new ArrayList<String>();
		for (String token : tokens) {
			soundexTokens.add(soundex(token));
		}

		return soundexTokens;
	}

	/**
	 * Tokenize s into its individual words.
	 * 
	 * @param s
	 *            The string to tokenize.
	 * @return An ArrayList of words in s.
	 */
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
