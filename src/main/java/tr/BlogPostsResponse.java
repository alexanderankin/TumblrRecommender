package tr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogPostsResponse extends APIResponse<BlogPostsResponse.Response> {

    @Data
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        Blog blog;
        List<Post> posts;

        @SuppressWarnings("SpellCheckingInspection")
        @Data
        @Accessors(chain = true)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Post {
            Type type;
            Instant timestamp;
            List<TrailEntry> trail;

            @JsonProperty("reblogged_from_id")
            Long fromId;
            @JsonProperty("reblogged_from_url")
            String fromUrl;
            @JsonProperty("reblogged_from_name")
            String fromName;
            @JsonProperty("reblogged_from_title")
            String fromTitle;
            @JsonProperty("reblogged_from_uuid")
            String fromUuid;
            @JsonProperty("reblogged_from_can_message")
            Boolean fromCanMessage;

            @JsonProperty("reblogged_root_id")
            Long rootId;
            @JsonProperty("reblogged_root_url")
            String rootUrl;
            @JsonProperty("reblogged_root_name")
            String rootName;
            @JsonProperty("reblogged_root_title")
            String rootTitle;
            @JsonProperty("reblogged_root_uuid")
            String rootUuid;
            @JsonProperty("reblogged_root_can_message")
            Boolean rootCanMessage;

            public enum Type {
                text, quote, link, answer, video, audio, photo, chat
            }

            @Data
            @Accessors(chain = true)
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TrailEntry {
                Blog blog;
                @JsonIgnore
                Long id;

                @JsonProperty("post")
                public Map<String, Object> getPost() {
                    return id == null ? Map.of() : Map.of("id", id);
                }

                @JsonProperty("post")
                public void setPost(Map<String, Object> post) {
                    Object id = post.get("id");
                    if (id instanceof Number number) this.id = number.longValue();
                }
            }
        }
    }

    // todo reuse?
    @Data
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Blog {
        List<Avatar> avatar;
        @JsonProperty("name")
        String username;
        String title;

        @Data
        @Accessors(chain = true)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Avatar {
            Integer height;
            Integer width;
            String url;
        }
    }
}
