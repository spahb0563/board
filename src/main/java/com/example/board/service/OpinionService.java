package com.example.board.service;

import com.example.board.model.entity.Opinion;
import com.example.board.model.entity.Post;
import com.example.board.model.entity.Users;
import com.example.board.model.network.dto.opinion.OpinionCreateRequestDto;
import com.example.board.model.network.dto.opinion.OpinionResponseDto;
import com.example.board.model.network.dto.opinion.OpinionUpdateRequestDto;
import com.example.board.model.network.dto.post.PostResponseDto;
import com.example.board.repository.OpinionRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OpinionService {

    private final OpinionRepository opinionRepository;

    private final UsersRepository usersRepository;

    private final PostRepository postRepository;

    @Transactional
    public OpinionResponseDto create(OpinionCreateRequestDto opinionCreateRequestDto) {

        Post post = postRepository.findById(opinionCreateRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postID : " + opinionCreateRequestDto.getPostId()));

        Users users = usersRepository.findById(opinionCreateRequestDto.getUsersId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. userID : " + opinionCreateRequestDto.getUsersId()));

        Opinion opinion = null;

        if(opinionCreateRequestDto.getParentId() != null) {
            opinion = opinionRepository.findById(opinionCreateRequestDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + opinionCreateRequestDto.getParentId()));
        }

        Opinion savedOpinion = opinionRepository.save(opinionCreateRequestDto.toEntity(post, users, opinion));

        post.updateOpinionCount(1); // 게시물 댓글수 증가

        return new OpinionResponseDto(savedOpinion);
    }//create() end

    public OpinionResponseDto read(Long id) {
        Opinion opinion = opinionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. opinionID : " + id));

        return new OpinionResponseDto(opinion);
    }//read() end

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

        opinion.getPost().updateOpinionCount(-1); // 게시물 댓글수 감소

        opinion.delete();
        return id;
    }//delete() end


}
