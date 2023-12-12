package com.linkextractor.backend.service;

import com.linkextractor.backend.models.Definitie;
import com.linkextractor.backend.models.Label;
import com.linkextractor.backend.respositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;


    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public Optional<Label> getLabelById(int definitionId) {
        return labelRepository.findById(definitionId);
    }

    public Label saveLabel(Label label) {
        return labelRepository.save(label);
    }

    public void deleteLabelById(int labelId) {
        labelRepository.deleteById(labelId);
    }
}
