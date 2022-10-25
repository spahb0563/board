package com.example.board.service;

import com.example.board.config.redis.CacheKey;
import com.example.board.model.entity.Category;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.Pagination;
import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.post.*;
import com.example.board.repository.CategoryRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public PostEditResponseDto readEdit(Long id) { // 수정용 포스트 데이터 읽어오기
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
    public PaginationDto readAllByCategoryType(CategoryType categoryType, Pageable pageable) { //카테고리별 게시물 페이징
        Page<Post> postList = postRepository.findAllByCategoryTypeAndDeletedAtIsNull(categoryType, pageable);

        return pagination(postList);
    } //readAll() end

    @Transactional(readOnly = true)
    public PaginationDto readAllByUserId(Long userId, Pageable pageable) { // 유저별 포스트
        Page<Post> postList = postRepository.findAllByUsersIdAndDeletedAtIsNull(userId, pageable);

        return pagination(postList);
    }//readAllByUserId () end

    @Transactional(readOnly = true)
    public PaginationDto readAllByCategoryTypeAndKeyword(CategoryType categoryType, String keyword, Pageable pageable) { //카테고리별 검색 결과 페이징
        Page<Post> postList = postRepository.findAllByCategoryTypeAndKeyword(categoryType.name(), keyword, pageable);

        return pagination(postList);
    }//readAllByCategoryTypeAndKeyword() end

    @Transactional(readOnly = true)
    public PaginationDto readAllByKeyword(String keyword, Pageable pageable) {//전체 검색결과 페이징
        Page<Post> postList = postRepository.findAllByKeyword(keyword, pageable);

        return pagination(postList);
    }//readAllByKeyword end()

    @Transactional(readOnly = true)
    @Cacheable(value = CacheKey.DAILY_BEST, key="{#today}") // redis 캐싱 처리
    public List<PostBestListResponseDto> readTop40OfDay(LocalDateTime today) { //일일 탑 40(좋아요 기준)
        return postRepository
                .findTop40ByCreatedAtGreaterThanEqualAndDeletedAtIsNullOrderByLikeCountDescViewCountDesc(today)
                .stream().map(post -> new PostBestListResponseDto(post)).collect(Collectors.toList());
    }//readTop40 end()

    @Transactional(readOnly = true)
    @Cacheable(value = CacheKey.WEEKLY_BEST, key="{#week}")
    public List<PostBestListResponseDto> readTop40OfWeek(LocalDateTime week) { //주간 탑 40(좋아요 기준)
        return postRepository
                .findTop40ByCreatedAtGreaterThanEqualAndDeletedAtIsNullOrderByLikeCountDescViewCountDesc(week)
                .stream().map(post -> new PostBestListResponseDto(post)).collect(Collectors.toList());
    }//readTop40OfWeek end()

    @Transactional(readOnly = true)
    public PaginationDto readAll(Pageable pageable) { //전체 게시물 페이징
        Page<Post> postList = postRepository.findAllByDeletedAtIsNull(pageable);
        return pagination(postList);
    }//readAll end()

    public int like(Long postId, String userId) { // 좋아요 처리
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

    public int dislike(Long postId, String userId) { // 싫어요 처리
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

    private PaginationDto pagination(Page<Post> postList) {
        List<PostListResponseDto> postListResponseDto = postList.stream()
                .map(post -> new PostListResponseDto(post))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(postList.getTotalPages())
                .totalElements(postList.getTotalElements())
                .currentPage(postList.getNumber())
                .currentElements(postList.getNumberOfElements())
                .build();
        return new PaginationDto<List<PostListResponseDto>>(postListResponseDto, pagination);
    }
}
