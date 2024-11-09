package com.example.project_cruise.service;

import com.example.project_cruise.entity.Tag;
import com.example.project_cruise.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    public List<Tag> getTagByType() {
        return tagRepository.findByType("cruise");
    }


}
