package com.example.board.service;

import com.example.board.model.entity.Post;
import com.example.board.model.network.Pagination;
import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.post.PostCreateRequestDto;
import com.example.board.model.network.dto.post.PostListResponseDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.model.network.dto.post.PostUpdateRequestDto;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long create(PostCreateRequestDto postCreateRequestDto) { //포스트 작성
        return postRepository.save(postCreateRequestDto.toEntity()).getId();
    }// create() end

    public PostResponseDto read(Long id) { // 포스트 한 개 읽어오기
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));
        return new PostResponseDto(post);
    }// findById() end

    @Transactional
    public Long update(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));

        post.update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());

        return id;
    }
    @Transactional
    public Long delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));
        return null;
    }

    @Transactional(readOnly = true)
    public PaginationDto<PostListResponseDto> readAll(String categoryType, Pageable pageable) {
        Page<Post> postList = postRepository.findAllByCategoryType(categoryType, pageable);

        List<PostListResponseDto> postListResponseDto = postList.stream()
                .map(post -> new PostListResponseDto(post))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder() //페이징 처리
                .totalPages(postList.getTotalPages())  // 전체 페이지 수
                .totalElements(postList.getTotalElements())  // 전체 요소수
                .currentPage(postList.getNumber()) // 현재 페이지
                .currentElements(postList.getNumberOfElements()) // 현재 요소의 순서
                .build()
                ;
        return new PaginationDto<PostListResponseDto>(postListResponseDto, pagination);
    } // 페이징 처리
}
