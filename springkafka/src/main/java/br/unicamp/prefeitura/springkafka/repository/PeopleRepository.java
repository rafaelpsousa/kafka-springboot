package br.unicamp.prefeitura.springkafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unicamp.prefeitura.springkafka.domain.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, String> {

}
