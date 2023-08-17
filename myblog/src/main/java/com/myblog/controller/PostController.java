package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    public ResponseEntity<PostDto>createPost(PostDto postDto){
       PostDto DTO= postService.CreatePost(postDto);
       return new ResponseEntity<>(DTO, HttpStatus.CREATED);
    }
    //http:localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title
    @GetMapping
  List<PostDto> getAllPost( @RequestParam(value = "pageNo",defaultValue = "0",required = false)
                            int pageNo, @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
    @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy){

        return postService.getAllPost(pageNo,pageSize,sortBy);
  }
  @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
      PostDto dto=  postService.getPostById();
      return new ResponseEntity<>(dto,HttpStatus.OK);
  }
  @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatepost(@RequestBody PostDto postDto,@PathVariable("id")long id){
       PostDto Dto= postService.updatePost(postDto, id);
       return new ResponseEntity<>(Dto,HttpStatus.OK);
  }
  @DeleteMapping("/{id}")
    public   ResponseEntity<String> deletepostById(@PathVariable("id"),long id){
        postService.deletepostById(id);
        return  new ResponseEntity<>("post entity deleted",HttpStatus.OK);
  }
}
