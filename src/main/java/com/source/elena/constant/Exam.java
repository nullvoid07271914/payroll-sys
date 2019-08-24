package com.source.elena.constant;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exam {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[1]+");
		//Integer[] A = { 8, 3, 7, 11, 2, 5 };
		Integer[] A = { 1, 3, 2, 1, 2, 1, 5, 3, 3, 4, 2 };
		int TERMINATE = A.length;

		if (A.length >= 1 && A.length <= 100_000) {
			int count = 1;
			Set<Integer> set = new HashSet<>();
			while (TERMINATE > 0) {
				StringBuilder sb = new StringBuilder(1_000_000_000);
				for (int i = 0; i < A.length; i++) {
					if (A[i] != 0) A[i] = A[i] - 1;
					
					if (A[i] == 0 && !set.contains(i)) {
						TERMINATE--;
						set.add(i);
					}
					
					if (sb.length() == 1_000_000_000)
						break;
					
					if (A[i] != 0) sb.append(1);
					else sb.append(0);
				}
				
				Matcher matcher = pattern.matcher(sb.toString());
				while (matcher.find())
					count++;
			}

			System.out.println(count);
		}
	}
}
