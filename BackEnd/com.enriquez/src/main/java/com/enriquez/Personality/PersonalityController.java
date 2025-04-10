package com.enriquez.Personality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path="/enriquez")
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from your React app
public class PersonalityController {
    @Autowired
    private PersonalityRepository PersonalityRepository;

    @GetMapping(path = "/personalities")
    public @ResponseBody Iterable<Personality> getAllArtists() {
        return PersonalityRepository.findAll();
    }

    @PostMapping(path = "/personalities")
    public @ResponseBody Personality newArtist(@RequestBody Personality artist) {
        PersonalityRepository.save(artist);
        return artist;
    }

    @PostMapping(path = "/personalities/bulk")
    public @ResponseBody Iterable<Personality> newBulkPersonalities(@RequestBody List<Personality> personalities) {
        return PersonalityRepository.saveAll(personalities);
    }

    @PutMapping("/personalities/{id}")
    public @ResponseBody String updateArtist(@PathVariable int id, @RequestBody Personality updatedArtist) {
        Personality artist = PersonalityRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));

        artist.setName(updatedArtist.getName());
        artist.setDescription(updatedArtist.getDescription());
        artist.setUrl(updatedArtist.getUrl());
        artist.setAlt(updatedArtist.getAlt());

        PersonalityRepository.save(artist);

        return "Artist with id: " + id + " updated.";
    }

    @DeleteMapping("/personalities/{id}")
    public @ResponseBody String deleteArtist(@PathVariable int id) {
        if (!PersonalityRepository.existsById(id)) {
            return "Artist ID not found";
        }

        PersonalityRepository.deleteById(id);
        return "Artist with id: " + id + " deleted.";
    }

    @GetMapping("/personalities/{id}")
    public @ResponseBody Personality getArtist(@PathVariable int id) {
        return PersonalityRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
    }

    @GetMapping("/personalities/search/{key}")
    public @ResponseBody Iterable<Personality> search(@PathVariable String key) {
        return PersonalityRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrUrlContainingIgnoreCaseOrAltContainingIgnoreCase(key, key, key, key);
    }
}