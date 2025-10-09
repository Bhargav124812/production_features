package LearningSpring.Security.utils;


import LearningSpring.Security.dto.PostDTO;
import LearningSpring.Security.entity.UserEntity;
import LearningSpring.Security.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean ownPost(Long id){
        UserEntity userEntity =(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO postDTO=postService.getPostById(id);
        return postDTO.getAuthor().getId().equals(userEntity.getId());
    }

}
