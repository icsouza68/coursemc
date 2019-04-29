package com.ivansouza.coursemc.enums;

public enum PerfilCliente {

	// O Spring exige que os papeis tenham o nome ROLE_XXXX
	ADMIN(1, "ROLE_ADMIN"), 
	CLIENTE(2, "ROLE_CLIENTE");

	private Integer cod;
	private String descricao;

	private PerfilCliente(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static PerfilCliente toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (PerfilCliente x : PerfilCliente.values()) {
			if (id.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}

}
