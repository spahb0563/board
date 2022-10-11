package com.example.board.service;

import com.example.board.model.entity.Category;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.Pagination;
import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.post.*;
import com.example.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final UsersRepository usersRepository;

    private final CategoryRepository categoryRepository;

    private final RedisTemplate<String, String> redisTemplate;
    @Transactional
    public Long create(PostCreateRequestDto postCreateRequestDto) { //포스트 작성
        Users users = usersRepository.findById(postCreateRequestDto.getUsersId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. userID : " + postCreateRequestDto.getUsersId()));
        Category category = categoryRepository.findByType(CategoryType.valueOf(postCreateRequestDto.getCategory()))
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. category : " + postCreateRequestDto.getCategory()));
        return postRepository.save(postCreateRequestDto.toEntity(category, users)).getId();
    }// create() end

    @Transactional
    public PostResponseDto readAndPlusViewCount(Long id) { // 포스트 한 개 읽어오고 조회수 증가
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + id));

        post.plusViewCount();

        return new PostResponseDto(post);
    }// readAndPlusViewCount() end

    public PostResponseDto read(Long id) { // 포스트 한 개 읽어오기
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + id));
        return new PostResponseDto(post);
    }// read() end

    public PostEditResponseDto readEdit(Long id) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + id));

        return new PostEditResponseDto(post);
    }// readEdit() end

    @Transactional
    public Long update(PostUpdateRequestDto postUpdateRequestDto) { //포스트 한 개 업데이트
        Post post = postRepository.findByIdAndDeletedAtIsNull(postUpdateRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID: " + postUpdateRequestDto.getId()));

        post.update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());

        return post.getId();
    }//update() end

    @Transactional
    public Long delete(Long id) { // 포스트 한 개 삭제
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + id));
        post.delete();
        return id;
    }//delete() end

    @Transactional(readOnly = true)
    public PaginationDto<List<PostListResponseDto>> readAllByCategoryType(CategoryType categoryType, Pageable pageable) { //페이징 처리
        Page<Post> postList = postRepository.findAllByCategoryTypeAndDeletedAtIsNull(categoryType, pageable);

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

    public int like(Long postId, String userId) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + postId));

        String likeKey = "postLike:"+post.getId();
        boolean wasLiked = redisTemplate.opsForSet().isMember(likeKey, userId);

        String dislikeKey = "postDislike:"+post.getId();
        boolean wasDisliked = redisTemplate.opsForSet().isMember(dislikeKey, userId);

        if(wasLiked) {
            redisTemplate.opsForSet().remove(likeKey, userId);
            post.updateLikeCount(-1);
        }else if(!wasDisliked){
            redisTemplate.opsForSet().add(likeKey, userId);
            post.updateLikeCount(1);
        }

        return post.getLikeCount();
    }//like() end

    public int dislike(Long postId, String userId) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + postId));

        String likeKey = "postLike:"+post.getId();
        boolean wasLiked = redisTemplate.opsForSet().isMember(likeKey, userId);

        String dislikeKey = "postDislike:"+post.getId();
        boolean wasDisliked = redisTemplate.opsForSet().isMember(dislikeKey, userId);

        if(wasDisliked) {
            redisTemplate.opsForSet().remove(dislikeKey, userId);
            post.updateDislikeCount(-1);
        }else if(!wasLiked){
            redisTemplate.opsForSet().add(dislikeKey, userId);
            post.updateDislikeCount(1);
        }

        return post.getDislikeCount();
    }//dislike() end
}
