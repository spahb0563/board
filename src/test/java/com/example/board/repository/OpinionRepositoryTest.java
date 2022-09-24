package com.example.board.repository;

import com.example.board.config.JpaConfig;
import com.example.board.model.entity.Opinion;
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
@DisplayName("OpinionRepository 테스트")
public class OpinionRepositoryTest {

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void saveAndRead() {
        //given
        String content = "Test";

        opinionRepository.save(Opinion.builder()
                .content(content)
                .build());

        //when
        List<Opinion> opinionList = opinionRepository.findAll();

        //then
        Opinion opinion = opinionList.get(0);
        assertThat(opinion.getContent()).isEqualTo(content);
    }//saveAndRead() end

    @Test
    public void saveAndUpdate() {
        //given
        String content = "Test";

        opinionRepository.save(Opinion.builder()
                .content(content)
                .build());

        String newContent = "newTest";

        List<Opinion> opinionList = opinionRepository.findAll();
        Opinion opinion = opinionList.get(0);

        //when
        opinion.update(newContent);

        //then
        List<Opinion> newOpinionList = opinionRepository.findAll();
        Opinion newOpinion = newOpinionList.get(0);
        assertThat(newOpinion.getContent()).isEqualTo(newContent);
    }//saveAndUpdate() end

    @Test
    public void saveAndDelete() {
        //given
        String content = "Test";

        opinionRepository.save(Opinion.builder()
                .content(content)
                .build());

        //when
        opinionRepository.deleteAll();

        //then
        assertThat(postRepository.findAll()).isEmpty();
    }//saveAndDelete() end
}
