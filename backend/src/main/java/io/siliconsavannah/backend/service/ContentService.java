package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.dto.ContentDto;
import io.siliconsavannah.backend.helper.Helper;
import io.siliconsavannah.backend.model.Content;
import io.siliconsavannah.backend.model.User;
import io.siliconsavannah.backend.repository.ContentRepository;
import io.siliconsavannah.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final Helper helper;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public Page<ContentDto> getAllContents(int page, int size) {
        // Fetch all content entities from the repository
        List<ContentDto> contents = contentRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()))
                .stream()
                .map(content -> toDto(content, content.getUser()))
                .toList();
        return new PageImpl<>(contents);
    }

    public ContentDto getContentById(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        return toDto(content, content.getUser());
    }


    public ContentDto createContent(ContentDto contentDto) {
        // Retrieve the authenticated user from the security context
        UserDetails userDetails = helper.getAuthenticatedUser();
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
        return toDto(content, content.getUser());
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
                .imageUrl(content.getImageUrl())
                .username(user.getUsername())
                .createdAt(content.getCreatedAt())
                .updatedAt(content.getUpdatedAt())
                .build();
    }

    public String uploadImage(Long id, MultipartFile file) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        String photoUrl = photoFunction.apply(id, file);
        content.setImageUrl(photoUrl);
        contentRepository.save(content);
        return photoUrl;
    }

    private Function<String, String> fileExtension = fileName -> Optional
            .of(fileName)
            .filter(name -> name.contains("."))
            .map(name -> "." + name.substring(fileName.lastIndexOf(".") + 1))
            .orElse(".png");


    private final BiFunction<Long, MultipartFile, String> photoFunction = (id, image) -> {
        try {
            Path fileStorageLocation = Paths
                    .get("src/main/resources/static/images/")
                    .toAbsolutePath()
                    .normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(id + ".png"), REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("src/main/resources/static/images/" + id + fileExtension.apply(image.getOriginalFilename()))
                    .toUriString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    };

}