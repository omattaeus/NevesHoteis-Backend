package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
