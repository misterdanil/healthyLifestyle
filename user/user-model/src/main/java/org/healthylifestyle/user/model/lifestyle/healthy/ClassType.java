package org.healthylifestyle.user.model.lifestyle.healthy;

public enum ClassType {
	STRING, SHORT, INTEGER, LONG, FLOAT, DOUBLE;

	public Short toShort(String value) {
		return Short.valueOf(value);
	}

	public Integer toInteger(String value) {
		return Integer.valueOf(value);
	}

	public Long toLong(String value) {
		return Long.valueOf(value);
	}

	public Float toFloat(String value) {
		return Float.valueOf(value);
	}

	public Double toDouble(String value) {
		return Double.valueOf(value);
	}
}
