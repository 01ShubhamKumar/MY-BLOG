package com.myblog.service;

import com.myblog.payload.PostDto;

import java.util.List;

public interface PostService {
   PostDto CreatePost(PostDto postDto);

   List<PostDto> getAllPost(int pageNo,int pageSize,String sortBy);


   PostDto getPostById(long id);


   PostDto updatePost(PostDto postDto, long id);

   void deletepostById(long id);
}
