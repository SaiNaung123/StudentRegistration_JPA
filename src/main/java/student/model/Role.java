package student.model;

public enum Role {
	ADMIN,USER,SUPER_ADMIN;

	public static Role fromString(String roleString) {
	    if (roleString != null) {
	        switch (roleString.toUpperCase()) {
	            case "ADMIN":
	                return ADMIN;
	            case "USER":
	                return USER;
	            case "SUPER_ADMIN":
	                return SUPER_ADMIN;
	        }
	    }
	    throw new IllegalArgumentException("Invalid role string: " + roleString);
	}

}
