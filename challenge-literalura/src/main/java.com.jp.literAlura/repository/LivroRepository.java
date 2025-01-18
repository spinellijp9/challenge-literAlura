package com.jp.literAlura.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jp.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    boolean existsByTitle(String title);
    Optional<Livro> findById(Long id);
    List<Livro> findByLanguagesContaining(String language);
}