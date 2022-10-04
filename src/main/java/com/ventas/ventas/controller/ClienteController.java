package com.ventas.ventas.controller;


import com.ventas.ventas.model.Cliente;
import com.ventas.ventas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /** exponer datos a travéz de HTTP GET*/
    @GetMapping
    public ResponseEntity<List<Cliente>> findAll(){
        return ResponseEntity.ok(clienteService.findAll());
    }

   @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable("id") Integer idClientes){
        return clienteService.findById(idClientes)

                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteService.create(cliente), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cliente> update(@Valid @RequestBody Cliente cliente) {
        return clienteService.findById(cliente.getIdCliente())
                .map( c -> ResponseEntity.ok(clienteService.update(cliente)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable("id") Integer idCliente) {
        return clienteService.findById(idCliente)
                .map(c -> {
                    clienteService.delete(idCliente);
                    return ResponseEntity.ok(c);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
