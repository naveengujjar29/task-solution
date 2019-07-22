package com.task.solution.enums;

public enum PublicationTypeEnum {

	MAGAZINES("magazines"), COMICS("comics"), BOOKS("books");

	private String type;

	PublicationTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	};
	
}
