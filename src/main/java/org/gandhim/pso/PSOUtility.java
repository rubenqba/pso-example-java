package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// just a simple utility class to find a minimum position on a list

import java.util.stream.IntStream;

public class PSOUtility {
	public static int getMinPos(double[] list) {
		int pos = IntStream.range(0,list.length)
				.reduce((i,j) -> list[i] > list[j] ? j : i)
				.getAsInt();  // or throw
		return pos;
	}
}
