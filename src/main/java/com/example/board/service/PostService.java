package com.example.board.service;

import com.example.board.model.entity.Category;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.Pagination;
import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.post.PostCreateRequestDto;
import com.example.board.model.network.dto.post.PostListResponseDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.model.network.dto.post.PostUpdateRequestDto;
import com.example.board.repository.CategoryRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UsersRepository;
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

    private final UsersRepository usersRepository;

    private final CategoryRepository categoryRepository;


    @Transactional
    public Long create(PostCreateRequestDto postCreateRequestDto) { //포스트 작성
        Users users = usersRepository.findById(postCreateRequestDto.getUsersId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. userID : " + postCreateRequestDto.getUsersId()));
        Category category = categoryRepository.findByType(CategoryType.valueOf(postCreateRequestDto.getCategory()))
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. category : " + postCreateRequestDto.getCategory()));
        return postRepository.save(postCreateRequestDto.toEntity(category, users)).getId();
    }// create() end

    @Transactional
    public PostResponseDto read(Long id) { // 포스트 한 개 읽어오기
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + id));

        post.plusViewCount();

        return new PostResponseDto(post);
    }// raed() end

    @Transactional
    public Long update(PostUpdateRequestDto postUpdateRequestDto) { //포스트 한 개 업데이트
        Post post = postRepository.findById(postUpdateRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID: " + postUpdateRequestDto.getId()));

        post.update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());

        return post.getId();
    }//update() end
    @Transactional
    public Long delete(Long id) { // 포스트 한 개 삭제
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + id));
        post.delete();
        return id;
    }//delete() end

    @Transactional(readOnly = true)
    public PaginationDto<List<PostListResponseDto>> readAllByCategoryType(CategoryType categoryType, Pageable pageable) { //페이징 처리
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
        return new PaginationDto(postListResponseDto, pagination);
    } //readAll() end

}
