package com.example.study;

import com.example.domain.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyController {

    final StudyRepository repository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for '" + id + "'"));
    }

    @PostMapping("/study")
    public Study createStudy(@RequestBody Study study) {
        return repository.save(study);
    }
}
