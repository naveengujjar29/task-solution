package com.task.solution.enums;

/**
 * Enum of various Publication type supported.
 * @author Naveen Kumar
 *
 */
public enum PublicationTypeEnum {

	magazines("magazines"), comics("comics"), books("books");

	private String type;

	PublicationTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	};

}
