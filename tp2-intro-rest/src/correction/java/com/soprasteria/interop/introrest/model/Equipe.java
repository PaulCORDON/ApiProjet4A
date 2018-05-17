package com.soprasteria.interop.introrest.model;

import javax.validation.constraints.NotEmpty;

//@JsonIgnoreProperties(value = { "id" })
public class Equipe {
	private int id;

	@NotEmpty
	private String name;
	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Equipe [id=" + id + ", name=" + name + ", role=" + role + "]";
	}

}
