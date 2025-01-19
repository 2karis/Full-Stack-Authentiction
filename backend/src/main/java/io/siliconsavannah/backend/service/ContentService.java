package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.dto.ContentDto;
import io.siliconsavannah.backend.model.Content;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.ContentRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentRepository contentRepository;

    public List<ContentDto> getAllContents() {
        // Fetch all content entities from the repository
        return contentRepository.findAll().stream()
                .map(content -> toDto( content, content.getUser()))
                .toList();
    }

    public ContentDto getContentById(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        return toDto(content, content.getUser());
    }


    public ContentDto createContent(ContentDto contentDto) {
        // Retrieve the authenticated user from the security context
        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authenticatedUser instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authenticatedUser;
            String email = userDetails.getUsername();
            // Do something with the username
            User contentUser = userRepository.findFirstByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Content content = Content.builder()
                    .title(contentDto.title())
                    .description(contentDto.description())
                    .content(contentDto.content())
                    .user(contentUser)
                    .build();

            contentRepository.save(content);

            return toDto( content, content.getUser());
        }else{
            throw new RuntimeException("error occurred creating content");
        }
    }


    public ContentDto updateContent(ContentDto contentDto) {

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Retrieve the content from the database
        Content content = contentRepository.findById(contentDto.id())
                .orElseThrow(() -> new RuntimeException("Content not found"));

        content.setTitle(contentDto.title());
        content.setDescription(contentDto.description());
        content.setContent(contentDto.content());

        contentRepository.save(content);

        return toDto(content, authenticatedUser);
    }

    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }

    public List<ContentDto> getContentsByUser(User user) {
        return contentRepository.findAllByUser(user)
                .stream()
                .map(content -> toDto(content, content.getUser()))
                .toList();
    }
    public List<ContentDto> getAllContentByAuthenticatedUser() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = userDetails.getUsername();
        // Do something with the username
        User contentUser = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return getContentsByUser(contentUser);
    }
    public ContentDto toDto(Content content, User user) {
        return ContentDto.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .content(content.getContent())
                .username(user.getUsername())
                .createdAt(content.getCreatedAt())
                .updatedAt(content.getUpdatedAt())
                .build();
    }
}
