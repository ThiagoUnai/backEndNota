package com.desafio.sonner.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafio.sonner.controller.dto.DetalhesDaNotaItemDto;
import com.desafio.sonner.controller.dto.NotaItemDto;
import com.desafio.sonner.controller.form.NotaItemForm;
import com.desafio.sonner.modelo.NotaItem;
import com.desafio.sonner.repository.NotaItemRepository;
import com.desafio.sonner.repository.NotaRepository;
import com.desafio.sonner.repository.ProdutoRepository;

@RestController
@RequestMapping(value="/notaItem")
public class NotaItemController {
	
	@Autowired
	private NotaItemRepository notaItemRepository;
	
	@Autowired
	private NotaRepository notaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public List<NotaItemDto> lista(){
		List<NotaItem> notaItem = notaItemRepository.findAll();	
		return NotaItemDto.converter(notaItem);
	}
	
	@PostMapping
	public ResponseEntity<NotaItemDto> cadastrar(@RequestBody NotaItemForm form, UriComponentsBuilder uriBuilder) {
		 NotaItem notaItem = form.converter(notaRepository, produtoRepository);
		 notaItemRepository.save(notaItem);
		 
		 URI uri = uriBuilder.path("/notaItem{id}").buildAndExpand(notaItem.getId()).toUri();
		 return ResponseEntity.created(uri).body(new NotaItemDto(notaItem));
	}

	@GetMapping("/{id}")
	public DetalhesDaNotaItemDto detalhar(@PathVariable Integer id) {
		NotaItem notaItem = notaItemRepository.getById(id);
		return new DetalhesDaNotaItemDto(notaItem);		
	}
}
