package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
