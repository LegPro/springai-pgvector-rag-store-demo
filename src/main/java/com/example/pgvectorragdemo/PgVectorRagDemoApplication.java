package com.example.pgvectorragdemo;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
public class PgVectorRagDemoApplication implements CommandLineRunner {

	@Value("classpath:input.txt")
	Resource resourceFile;

	private final VectorStore vectorStore;
	private final JdbcClient jdbcClient;
	public PgVectorRagDemoApplication(VectorStore vectorStore, JdbcClient jdbcClient) {
		this.vectorStore = vectorStore;
		this.jdbcClient = jdbcClient;
	}
	public static void main(String[] args) {
		SpringApplication.run(PgVectorRagDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Integer count = jdbcClient.sql("select COUNT(*) from vector_store").query(Integer.class).single();
		System.out.println("No of Records : "+ count);
		if(count == 0) {
			List<Document> documents = Files.lines(resourceFile.getFile().toPath())
					.map(Document::new)
					.toList();
			TextSplitter textSplitter = new TokenTextSplitter();
			List<Document> splittedDocs = textSplitter.apply(documents);
			vectorStore.add(splittedDocs);
		}
	}
}
