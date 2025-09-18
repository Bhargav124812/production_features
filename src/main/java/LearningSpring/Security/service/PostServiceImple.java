package LearningSpring.Security.service;

import LearningSpring.Security.dto.PostDTO;
import LearningSpring.Security.entity.PostEntity;
import LearningSpring.Security.entity.UserEntity;
import LearningSpring.Security.exceptions.ResourceNotFoundException;
import LearningSpring.Security.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostServiceImple implements PostService{

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    @Override
    public List<PostDTO> getAllPosts() {
       return postRepo.findAll()
                .stream()
                .map(PostEntity -> modelMapper.map(PostEntity,PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity= modelMapper.map(inputPost,PostEntity.class);
        return modelMapper.map(postRepo.save(postEntity),PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        UserEntity userEntity =(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("The USer is {}",userEntity);
        PostEntity postEntity = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        return modelMapper.map(postEntity,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {
        PostEntity postEntity=postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        inputPost.setId(postId);
        modelMapper.map(inputPost, postEntity);
        PostEntity savedPostEntity = postRepo.save(postEntity);
        return modelMapper.map(savedPostEntity, PostDTO.class);
    }
}
