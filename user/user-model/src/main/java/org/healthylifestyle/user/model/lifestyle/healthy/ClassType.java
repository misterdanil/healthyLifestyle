package org.healthylifestyle.user.model.lifestyle.healthy;

public enum ClassType {
	STRING {

		@Override
		public boolean isType(String value) {
			return value != null && !value.isEmpty();
		}

	},
	SHORT {

		@Override
		public boolean isType(String value) {
			try {
				Short.parseShort(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

	},
	INTEGER {

		@Override
		public boolean isType(String value) {
			try {
				Integer.parseInt(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

	},
	LONG {

		@Override
		public boolean isType(String value) {
			try {
				Long.parseLong(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

	},
	FLOAT {

		@Override
		public boolean isType(String value) {
			try {
				Float.parseFloat(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

	},
	DOUBLE {

		@Override
		public boolean isType(String value) {
			try {
				Double.parseDouble(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

	};

	public abstract boolean isType(String value);
}