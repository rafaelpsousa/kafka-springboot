package br.unicamp.prefeitura.springkafka.consumer;

import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.unicamp.prefeitura.springkafka.People;
import br.unicamp.prefeitura.springkafka.domain.Book;
import br.unicamp.prefeitura.springkafka.repository.PeopleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PeopleConsumer {
	
	@Autowired
	private PeopleRepository peopleRepository;

	@KafkaListener(topics = "${topic.name}")
	public void consumer(ConsumerRecord<String, People> record, Acknowledgment ack) {

		var people = record.value();

		log.info("Mensagem consumida: " + people.toString());

		var peopleEntity = br.unicamp.prefeitura.springkafka.domain.People.builder().build();
		peopleEntity.setId(people.getId().toString());
		peopleEntity.setName(people.getName().toString());
		peopleEntity.setCpf(people.getCpf().toString());
		peopleEntity.setBooks(people.getBooks().stream()
				.map(book -> Book.builder().people(peopleEntity).name(book.toString()).build())
				.collect(Collectors.toList()));
		
		peopleRepository.save(peopleEntity);

		ack.acknowledge();
	}
}
