package com.enriquez.Personality;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalityRepository extends JpaRepository<Personality, Integer> {
    List<Personality> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrUrlContainingIgnoreCaseOrAltContainingIgnoreCase(String name, String description, String url, String alt);
}