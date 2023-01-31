package be.vinci.main;

import be.vinci.utils.ApplicationBinder;
import be.vinci.utils.Config;
import be.vinci.utils.WebExceptionMapper;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main class.
 */
public class Main {
    static {
        Config.load("dev.properties");
    }

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = Config.getProperty("BaseUri");

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {


        // create a resource config that scans for JAX-RS resources and providers
        // in vinci.be package
        String RESOURCES = "be.vinci.api";

        OpenApiResource openApiResource = new OpenApiResource();


        final ResourceConfig rc = new ResourceConfig().packages(RESOURCES)
                .register(JacksonFeature.class)
                .register(ApplicationBinder.class)
                .register(WebExceptionMapper.class);

        //.register(io.swagger.jaxrs.listing.ApiListingResource.class)
        //.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);


//        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setVersion("1.0.0");
//        beanConfig.setSchemes(new String[]{"http"});
//        beanConfig.setHost(BASE_URI);
//        beanConfig.setBasePath("/");
//        beanConfig.setResourcePackage(RESOURCES);
//        beanConfig.setScan(true);

        /*
        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("Swagger Sample App bootstrap code")
                .description("This is a sample server Petstore server.  You can find out more about Swagger " +
                        "at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, " +
                        "you can use the api key `special-key` to test the authorization filters.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact()
                        .email("apiteam@swagger.io"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        oas.info(info);
        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of(RESOURCES).collect(Collectors.toSet()));

        try {
            new JaxrsOpenApiContextBuilder()
                     //.servletConfig(servletConfig)
                    //.application(this)
                    .openApiConfiguration(oasConfig)
                    .buildContext(true);
        } catch (OpenApiConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }*/


        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

