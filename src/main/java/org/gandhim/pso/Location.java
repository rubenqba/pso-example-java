package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// bean class to represent location

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
public class Location {
	// store the Location in an array to accommodate multi-dimensional problem space
	private double[] loc;

	public Location(double[] loc) {
		super();
		this.loc = loc;
	}

	public Location(Location loc) {
		super();
		this.loc = Arrays.copyOf(loc.getLoc(), loc.getLoc().length);
    }


}
