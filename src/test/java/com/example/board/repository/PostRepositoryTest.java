package com.example.board.repository;


import com.example.board.config.JpaConfig;
import com.example.board.model.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = JpaConfig.class))
@DisplayName("PostsRepository 테스트")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    public void save() {
        String title = "Test";
        String content = "Test";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());
    }

    @Test
    public void Read() {
        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo("Test");
        assertThat(post.getContent()).isEqualTo("Test");
    }//Read() end

    @Test
    public void Update() {
        //given
        String newTitle = "newTest";
        String newContent = "newTest";

        List<Post> postList = postRepository.findAll();
        Post post = postList.get(0);

        //when
        post.update(newTitle, newContent);

        //then
        List<Post> newPostList = postRepository.findAll();
        Post newPost = newPostList.get(0);
        assertThat(newPost.getTitle()).isEqualTo(newTitle);
        assertThat(newPost.getContent()).isEqualTo(newContent);
    }//Update() end

    @Test
    public void Delete() {
        //when
        postRepository.deleteAll();

        //then
        assertThat(postRepository.findAll()).isEmpty();
    }//Delete() end

    @Test
    public void readTodayTop10() {
        //given
        LocalDateTime today = LocalDateTime.now().with(LocalTime.MIN);
        //when
        List<Post> postList = postRepository.findTop40ByCreatedAtGreaterThanEqualAndDeletedAtIsNullOrderByLikeCountDescViewCountDesc(today);
        //then
        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo("Test");
        assertThat(post.getContent()).isEqualTo("Test");
    }

    @Test
    public void readWeeklyTop10() {
        //given
        LocalDateTime week = LocalDateTime.now().minusDays(6).with(LocalTime.MIN);
        //when
        List<Post> postList = postRepository.findTop40ByCreatedAtGreaterThanEqualAndDeletedAtIsNullOrderByLikeCountDescViewCountDesc(week);
        //then
        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo("Test");
        assertThat(post.getContent()).isEqualTo("Test");
    }
}
