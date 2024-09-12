package br.com.ifdelivery.modelo.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  // obter cliente por id de usuario
  Cliente findByUsuarioId(Long id);
}
