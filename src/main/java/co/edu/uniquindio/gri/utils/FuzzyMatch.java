package co.edu.uniquindio.gri.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

@Service
public class FuzzyMatch {

	public int getRatio(String s1, String s2) {
		
		if (s1.length() >= s2.length()) {		
			// We need to swap s1 and s2		
			String temp = s2;
			s2 = s1;
			s1 = temp;			
		}

		// Get alpha numeric characters
		
		s1 = replaceString(s1);
		s2 = replaceString(s2);
				
		s1 = escapeString(s1);
		s2 = escapeString(s2);
		
		s1 = StringUtils.stripAccents(s1);
		s2 = StringUtils.stripAccents(s2);
				
		
		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		
		//split the string by space and store words in sets
		StringTokenizer st1 = new StringTokenizer(s1);		
		while (st1.hasMoreTokens()) {
			set1.add(st1.nextToken());
		}

		StringTokenizer st2 = new StringTokenizer(s2);		
		while (st2.hasMoreTokens()) {
			set2.add(st2.nextToken());
		}
		
		SetView<String> intersection = Sets.intersection(set1, set2);
		
		TreeSet<String> sortedIntersection = Sets.newTreeSet(intersection);

		
		// Find out difference of sets set1 and intersection of set1,set2
		
		SetView<String> restOfSet1 = Sets.symmetricDifference(set1, intersection);
		
		// Sort it
		
		TreeSet<String> sortedRestOfSet1 = Sets.newTreeSet(restOfSet1);
		
		SetView<String> restOfSet2 = Sets.symmetricDifference(set2, intersection);
		TreeSet<String> sortedRestOfSet2 = Sets.newTreeSet(restOfSet2);
		
		String t0 = "";
		String t1 = "";
		String t2 = "";
		
		for (String s:sortedIntersection) {
			t0 = t0 + " " + s;			
		}
		t0 = t0.trim();
		
		Set<String> setT1 = Sets.union(sortedIntersection, sortedRestOfSet1);
		for (String s:setT1) {
			t1 = t1 + " " + s;			
		}
		t1 = t1.trim();
		
		Set<String> setT2 = Sets.union(intersection, sortedRestOfSet2);		
		for (String s:setT2) {
			t2 = t2 + " " + s;			
		}
		
		t2 = t2.trim();
		
		
		int amt1 = calculateLevensteinDistance(t0, t1);
		int amt2 = calculateLevensteinDistance(t0, t2);
		int amt3 = calculateLevensteinDistance(t1, t2);		
		
		int finalScore = Math.max(Math.max(amt1, amt2), amt3);
		return finalScore;	
	}
	
	public int calculateLevensteinDistance(String s1, String s2) {
		int distance = StringUtils.getLevenshteinDistance(s1, s2);
		double ratio = ((double) distance) / (Math.max(s1.length(), s2.length()));
		return 100 - new Double(ratio*100).intValue();		
	}
	
	public String escapeString(String token) {

		StringBuffer s = new StringBuffer(token.length());

		CharacterIterator it = new StringCharacterIterator(token);
		for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
			switch (ch) {
			case '\'':
			case '/':
			case '\\':
			case '-':
			case ',':
			case ')':
			case '(':
			case '!':
			case '`':
			case '\"':
			case ':':
			case ']':
			case '[':
			case '?':
			case ';':
			case '~':
			case '<':
			case '>':
				s.append(" ");
				break;
			default:
				s.append(ch);
				break;
			}
		}

		token = s.toString();
		return token;
	}
	
	public String replaceString(String token){
		
		token= token.replace("DISPONIBILIDAD:", "");
		token= token.replace("SITIO WEB:", "");
		token= token.replace("NOMBRE COMERCIAL:", "");
		token= token.replace("NOMBRE DEL PROYECTO:", "");
		token= token.replace("INSTITUCIÓN FINANCIADORA:", "");
		token= token.replace("&AMP;UTE;", "Á");
		token= token.replace("INSTITUCIÓN FINANCIADORA:", "Ó");
		
		if (token.contains("AUTORES:")) {
			token= token.substring(0, token.indexOf("AUTORES:"));
		}
				
		
		return token;
	}
}