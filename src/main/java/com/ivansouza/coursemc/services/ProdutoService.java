package com.ivansouza.coursemc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ivansouza.coursemc.domain.Categoria;
import com.ivansouza.coursemc.domain.Produto;
import com.ivansouza.coursemc.repositories.CategoriaRepository;
import com.ivansouza.coursemc.repositories.ProdutoRepository;
import com.ivansouza.coursemc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);

		//		Esta versão usa a query com base no padrão de nomes do método do Spring Data
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

//		Esta versão usa a query customizada
//		return repo.searchContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
