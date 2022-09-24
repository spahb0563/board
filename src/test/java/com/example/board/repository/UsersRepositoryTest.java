package com.example.board.repository;

import com.example.board.config.JpaConfig;
import com.example.board.model.entity.Users;
import com.example.board.model.enumclass.UserRole;
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
@DisplayName("UserRepository 테스트")
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;


    @Test
    public void saveAndRead() {
        //given
        String name = "Test";
        String email = "Test@Test.com";
        String nickname = "Test";
        UserRole role = UserRole.USER;

        usersRepository.save(Users.builder()
                        .name(name)
                        .email(email)
                        .nickname(nickname)
                        .role(role)
                        .build());

        //when
        List<Users> usersList = usersRepository.findAll();

        //then
        Users users = usersList.get(0);
        assertThat(users.getName()).isEqualTo(name);
        assertThat(users.getEmail()).isEqualTo(email);
        assertThat(users.getNickname()).isEqualTo(nickname);
        assertThat(users.getRole()).isEqualTo(role);
    }//saveAndRead() end

    @Test
    public void saveAndUpdate() {
        //given
        String name = "Test";
        String email = "Test@Test.com";
        String nickname = "Test";
        UserRole role = UserRole.USER;

        usersRepository.save(Users.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .role(role)
                .build());

        List<Users> usersList = usersRepository.findAll();
        Users users = usersList.get(0);

        String newnickname = "newTest";
        String newPicture = "newPicture";

        //when
        users.update(newnickname, newPicture);

        //then
        List<Users> newUsersList = usersRepository.findAll();
        Users newUsers = newUsersList.get(0);
        assertThat(newUsers.getNickname()).isEqualTo(newnickname);
        assertThat(newUsers.getPicture()).isEqualTo(newPicture);
    }//saveAndUpdate() end

    @Test
    public void saveAndDelete() {
        //given
        String name = "Test";
        String email = "Test@Test.com";
        String nickname = "Test";
        UserRole role = UserRole.USER;

        usersRepository.save(Users.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .role(role)
                .build());

        //when
        usersRepository.deleteAll();

        //then

        assertThat(usersRepository.findAll()).isEmpty();
    }//saveAndDelete() end
}
