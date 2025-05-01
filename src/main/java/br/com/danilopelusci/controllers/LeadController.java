package br.com.danilopelusci.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danilopelusci.model.Lead;
import br.com.danilopelusci.services.LeadService;

@CrossOrigin
@RestController
@RequestMapping("/leads")
public class LeadController {
	@Autowired
	private LeadService leadService;

	@GetMapping
	public List<Lead> listarTodos() {
		return leadService.listarTodos();
	}

	@GetMapping("/{id}")
	public Optional<Lead> buscarPorId(@PathVariable Long id) {
		return leadService.buscarPorId(id);
	}

	@GetMapping("Usuario/{id}")
	public List<Lead> buscarPorIdUsuario(@PathVariable Long id) {
		return leadService.buscarPorIdUsuario(id);
	}
	@PostMapping
	public Lead salvar(@RequestBody Lead lead) {
		return leadService.salvar(lead);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Long id) {
		leadService.excluir(id);
	}
}
