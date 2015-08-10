package com.example.dongdong_weather.custom;

import com.example.dongdong_weather.model.AreaCode;

import java.util.Comparator;

public class PinyinComparator implements Comparator<AreaCode> {

	public int compare(AreaCode o1, AreaCode o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
