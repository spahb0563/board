package com.example.board.repository;


import com.example.board.config.JpaConfig;
import com.example.board.model.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

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

    @Test
    public void saveAndRead() {
        //given
        String title = "Test";
        String content = "Test";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }//saveAndRead() end

    @Test
    public void saveAndUpdate() {
        //given
        String title = "Test";
        String content = "Test";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

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
    }//saveAndUpdate() end

    @Test
    public void saveAndDelete() {
        //given
        String title = "Test";
        String content = "Test";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        postRepository.deleteAll();

        //then
        assertThat(postRepository.findAll()).isEmpty();
    }//saveAndDelete() end
}
