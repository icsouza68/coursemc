package com.ivansouza.coursemc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivansouza.coursemc.domain.Categoria;
import com.ivansouza.coursemc.repositories.CategoriaRepository;
import com.ivansouza.coursemc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		// pra garantir que seja uma inclusao. Se o Id for diferente de nulo, ele vai atualizar, em vez de inserir
		obj.setId(null);
		return repo.save(obj);
	}

}
