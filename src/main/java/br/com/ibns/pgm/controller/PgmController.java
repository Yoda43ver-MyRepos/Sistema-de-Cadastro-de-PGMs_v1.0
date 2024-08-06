package br.com.ibns.pgm.controller;

import br.com.ibns.pgm.pgm.DadosListagemPgms;
import br.com.ibns.pgm.pgm.DadosPgm;
import br.com.ibns.pgm.pgm.Pgm;
import br.com.ibns.pgm.pgm.PgmRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pgms")
public class PgmController {

    @Autowired
    private PgmRepository repository;

    @PostMapping
    @Transactional
    public void cadastrarPgm(@RequestBody @Valid DadosPgm dados) {
        repository.save(new Pgm(dados));
    }

    /* Este metodo foi modificado.
    Mantendo este compentário para consultas futuras.
    @GetMapping
    public List<DadosListagemPgms> listarPgms(){
        return repository.findAll().stream().map(DadosListagemPgms::new).toList();
    }
    */

    @GetMapping
    public Page<DadosListagemPgms> listarPgms(Pageable pagination) {
        return repository.findAllByAtivoTrue(pagination).map(DadosListagemPgms::new);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void tornarPgmInativo(@PathVariable Long id) {
        var pgm = repository.getReferenceById(id);
        pgm.inativarPgm();
    }

    @PostMapping("/activate/{id}")
    @Transactional
    public void tornarPgmAtivo(@PathVariable Long id) {
        var pgm = repository.getReferenceById(id);
        pgm.activatePgm();
    }


}
