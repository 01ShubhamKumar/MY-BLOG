package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.payload.PostDto;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto CreatePost(PostDto postDto) {
      Post post =  mapToEntity( postDto);
       Post newPost=postRepository.save(post);
       PostDto newPostDto= mapToDto(newPost);

        return newPostDto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo,int pageSize,String sortBy) {

       Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
       Page<Post> posts= postRepository.findAll(pageable);
       List<Post>content=posts.getContent();
      return content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(long id) {
       Post post=  postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("post","id",id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
       Post post= postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",id)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatePost=postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletepostById( long id) {
       Post post= postRepository.deleteById(id).findById(id).orElseThrow
                (()-> new ResourceNotFoundException("post","id",id))  ;
       postRepository.deleteById(post);
    }


    private PostDto mapToDto(Post post) {
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getDescription());
        return  post;
    }
}
