package com.example.board.service;

import com.example.board.model.entity.Opinion;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import com.example.board.model.enumclass.NotificationType;
import com.example.board.model.network.Pagination;
import com.example.board.model.network.PaginationDto;
import com.example.board.model.network.dto.notification.NotificationCreateRequestDto;
import com.example.board.model.network.dto.opinion.OpinionCreateRequestDto;
import com.example.board.model.network.dto.opinion.OpinionListResponseDto;
import com.example.board.model.network.dto.opinion.OpinionResponseDto;
import com.example.board.model.network.dto.opinion.OpinionUpdateRequestDto;
import com.example.board.repository.OpinionRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OpinionService {

    private final OpinionRepository opinionRepository;

    private final UsersRepository usersRepository;

    private final PostRepository postRepository;

    private final RedisTemplate<String, String> redisTemplate;

    private final NotificationService notificationService;
    @Transactional
    public OpinionResponseDto create(OpinionCreateRequestDto opinionCreateRequestDto) {
        Post post = postRepository.findById(opinionCreateRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + opinionCreateRequestDto.getPostId()));

        Users users = usersRepository.findById(opinionCreateRequestDto.getUsersId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. userID : " + opinionCreateRequestDto.getUsersId()));

        Opinion opinion = null;
        NotificationType type;
        Users receiver;

        if(opinionCreateRequestDto.getParentId() != null) {
            opinion = opinionRepository.findById(opinionCreateRequestDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + opinionCreateRequestDto.getParentId()));
            type = NotificationType.REPLY;
            receiver = opinion.getUsers();
        }else {
            type = NotificationType.OPINION;
            receiver = post.getUsers();
        }

        Opinion savedOpinion = opinionRepository.save(opinionCreateRequestDto.toEntity(post, users, opinion));

        if(users.getId() != receiver.getId()) {
            notificationService.create(NotificationCreateRequestDto.builder()
                    .targetId(savedOpinion.getId())
                    .sender(users)
                    .receiver(receiver)
                    .post(post)
                    .type(type)
                    .build());
        }

        post.updateOpinionCount(1); // 게시물 댓글수 증가

        return new OpinionResponseDto(savedOpinion);
    }//create() end

    public OpinionResponseDto read(Long id) {
        Opinion opinion = opinionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + id));

        return new OpinionResponseDto(opinion);
    }//read() end

    @Transactional(readOnly = true)
    public PaginationDto readAllByUserId(Long userId, Pageable pageable) {//유저별 댓글
        Page<Opinion> opinionList = opinionRepository.findAllByUsersIdAndDeletedAtIsNull(userId, pageable);

        return pagination(opinionList);
    }//readAllByKeyword end()

    @Transactional
    public Long update(OpinionUpdateRequestDto opinionUpdateRequestDto) {
        Opinion opinion = opinionRepository.findById(opinionUpdateRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + opinionUpdateRequestDto.getId()));

        opinion.update(opinionUpdateRequestDto.getContent());

        return opinion.getId();
    }//update() end

    @Transactional
    public Long delete(Long id) {
        Opinion opinion = opinionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + id));

        Post post = opinion.getPost();

        opinion.delete();

        post.updateOpinionCount(-1); // 게시물 댓글수 감소
        return id;
    }//delete() end

    public int like(Long opinionId, String userId) {
        Opinion opinion = opinionRepository.findById(opinionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + opinionId));

        String likeKey = "opinionLike:"+opinion.getId();
        boolean wasLiked = redisTemplate.opsForSet().isMember(likeKey, userId);

        String dislikeKey = "opinionDislike:"+opinion.getId();
        boolean wasDisliked = redisTemplate.opsForSet().isMember(dislikeKey, userId);

        if(wasLiked) {
            redisTemplate.opsForSet().remove(likeKey, userId);
            opinion.updateLikeCount(-1);
        }else if(!wasDisliked){
            redisTemplate.opsForSet().add(likeKey, userId);
            opinion.updateLikeCount(1);
        }

        return opinion.getLikeCount();
    }//like() end

    public int dislike(Long opinionId, String userId) {
        Opinion opinion = opinionRepository.findById(opinionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + opinionId));

        String likeKey = "opinionLike:"+opinion.getId();
        boolean wasLiked = redisTemplate.opsForSet().isMember(likeKey, userId);

        String dislikeKey = "opinionDislike:"+opinion.getId();
        boolean wasDisliked = redisTemplate.opsForSet().isMember(dislikeKey, userId);

        if(wasDisliked) {
            redisTemplate.opsForSet().remove(dislikeKey, userId);
            opinion.updateDislikeCount(-1);
        }else if(!wasLiked){
            redisTemplate.opsForSet().add(dislikeKey, userId);
            opinion.updateDislikeCount(1);
        }

        return opinion.getDislikeCount();
    }//dislike() end

    private PaginationDto pagination(Page<Opinion> opinionList) {
        List<OpinionListResponseDto> opinionListResponseDto = opinionList.stream()
                .map(post -> new OpinionListResponseDto(post))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(opinionList.getTotalPages())
                .totalElements(opinionList.getTotalElements())
                .currentPage(opinionList.getNumber())
                .currentElements(opinionList.getNumberOfElements())
                .build();
        return new PaginationDto<List<OpinionListResponseDto>>(opinionListResponseDto, pagination);
    }
}
