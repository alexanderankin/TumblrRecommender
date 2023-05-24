package tr;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlogPostsResponseTest {
    static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @SneakyThrows
    @Test
    void test() {
        var resource = getClass().getResourceAsStream("/tr/blogPosts-example.json");
        BlogPostsResponse blogPostsResponse = objectMapper.readValue(resource, BlogPostsResponse.class);
        assertNotNull(blogPostsResponse);
    }
}
