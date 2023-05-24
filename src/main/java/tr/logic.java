package tr;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class logic {

	public static final String TUMBLR = ".tumblr.com";
	private final Configuration configuration;

	@SneakyThrows
	public static void main(String[] args) {
		File file = Path.of(System.getProperty("user.home"), ".tumblr-oauth-key.json").toFile();
		if (!file.exists()) throw new AssertionError("TumblrRecommender requires an api key");

		TumblrCredentials tumblrCredentials = new ObjectMapper()
				.readValue(file, TumblrCredentials.class);

		System.setProperty(Configuration.PREFIX + ".api-key",
				Objects.requireNonNull(tumblrCredentials.getKey(),
						"TumblrRecommender requires an api key"));

		Configuration configuration = Binder.get(new StandardEnvironment())
				.bind(Configuration.PREFIX, Configuration.class)
				.orElseGet(Configuration::new);

		// Read in Data from User
		String user = "";
		var logic = new logic(configuration);
		var recommendation = logic.recommendationByUser(user);
		log.info("recommendation: {}", recommendation);
	}

	@SneakyThrows
	public Object recommendationByUser(String user) {
		// Format the url to request, and Call API
		URI uri = UriComponentsBuilder.fromHttpUrl(configuration.getBaseUrl())
				.path("/v2/blog/{user}/posts")
				.queryParam("api_key", configuration.getApiKey())
				.queryParam("reblog_info", true)
				.build(user + TUMBLR);

		// Store response
		String response = apiCall(uri.toString());
		log.debug("abc");
		return response;
	}

	public static String apiCall(String url) {
		return WebClient.create().get().uri(url).retrieve().bodyToMono(String.class).block();
	}

	@Data
	@Accessors(chain = true)
	@SuppressWarnings("ConfigurationProperties")
	@ConfigurationProperties(Configuration.PREFIX)
	public static class Configuration {
		private static final String PREFIX = "tr.configuration";
		String baseUrl = "https://api.tumblr.com";
		@ToString.Exclude
		String apiKey;

		@SuppressWarnings("unused")
		@ToString.Include
		private String apiKey() {
			return apiKey == null ? null : "*";
		}
	}

	@Data
	@Accessors(chain = true)
	private static class TumblrCredentials {
		@ToString.Exclude
		String key;
		@ToString.Exclude
		String secret;
	}
}
