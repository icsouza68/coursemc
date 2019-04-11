package com.ivansouza.coursemc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ivansouza.coursemc.domain.Cidade;
import com.ivansouza.coursemc.domain.Cliente;
import com.ivansouza.coursemc.domain.Endereco;
import com.ivansouza.coursemc.dto.ClienteDTO;
import com.ivansouza.coursemc.dto.ClienteNewDTO;
import com.ivansouza.coursemc.enums.TipoCliente;
import com.ivansouza.coursemc.repositories.CidadeRepository;
import com.ivansouza.coursemc.repositories.ClienteRepository;
import com.ivansouza.coursemc.repositories.EnderecoRepository;
import com.ivansouza.coursemc.services.exceptions.DataIntegrityException;
import com.ivansouza.coursemc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente obj) {
		// pra garantir que seja uma inclusao. Se o Id for diferente de nulo, ele vai atualizar, em vez de inserir
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		// o método do repositório é o mesmo que para inserir
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.seteMail(obj.geteMail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.geteMail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		//return new Cliente(objDto.getId(), objDto.getNome(), objDto.geteMail(), null, null);
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.geteMail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = cidadeRepository.getOne(objDto.getCidadeId());
		Endereco end = new Endereco(
				null, 
				objDto.getLogradouro(), 
				objDto.getNumero(), 
				objDto.getComplemento(), 
				objDto.getBairro(), 
				objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
		
	}

}
