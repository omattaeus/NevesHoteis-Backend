package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
}
