package spring.jsonlab.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spring.jsonlab.entities.Post;
import spring.jsonlab.gson.PostGsonDeserializer;
import spring.jsonlab.gson.PostGsonSerializer;
import spring.jsonlab.service.PostService;

@RestController
@RequestMapping("/api/gson/posts")
@Slf4j
public class GsonPostController {
    @Autowired
    private PostService postService;

    private Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .registerTypeAdapter(Post.class, new PostGsonSerializer())
            .registerTypeAdapter(Post.class, new PostGsonDeserializer())
            .create();

    @GetMapping(produces = "application/json")
    public String getPosts() {
        return gson.toJson(postService.getAllPosts());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public String getPosts(@PathVariable("id") Long id) {
        return gson.toJson(postService.getPostById(id));
    }
    @PostMapping(produces = "application/json")
    public ResponseEntity<String> addPost(@RequestBody String body) {
        log.info("Body received: {}", body);
        Post post = gson.fromJson(body, Post.class);
        log.info("Post deserialized: {}", post);
        Post created = postService.addPost(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .pathSegment("{id}")
                        .buildAndExpand(created.getId().toString())
                        .toUri()
        ).body(gson.toJson(created));
    }
}
