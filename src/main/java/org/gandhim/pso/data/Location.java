package org.gandhim.pso.data;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// bean class to represent location

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
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

    @Override
    public String toString() {
        return String.format("%s(loc=[%s])",
                getClass().getSimpleName(),
                Arrays.stream(loc)
                        .mapToObj(d -> String.format("%1.4f", d))
                        .collect(Collectors.joining(", "))
        );
    }
}
