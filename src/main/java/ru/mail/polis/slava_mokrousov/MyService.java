package ru.mail.polis.slava_mokrousov;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;
import ru.mail.polis.KVService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

/**
 * Created by Slava on 16.01.2018.
 */
public class MyService implements KVService {
    private static final String PREFIX = "id=";

    @NotNull
    private final HttpServer server;

    @NotNull
    private static String extractId(@NotNull final String query) {
        if (!query.startsWith(PREFIX)) {
            throw new IllegalArgumentException("wrong string");
        }

        final String id = query.substring(PREFIX.length());
        if (id.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return id;
    }

    public MyService(int port,@NotNull final MyDAO dao) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port),0);


        this.server.createContext("/v0/status", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                final String response = "ONLINE";
                httpExchange.sendResponseHeaders(200, response.length());
                httpExchange.getResponseBody().write(response.getBytes());
                httpExchange.close();
            }
        });

        this.server.createContext("/v0/entity", new ErrorHandler(new HttpHandler() {

            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                final String id = extractId(httpExchange.getRequestURI().getQuery());

                switch (httpExchange.getRequestMethod()) {
                    case "GET":
                        final byte[] getValue = dao.get(id);
                        httpExchange.sendResponseHeaders(200, getValue.length);
                        httpExchange.getResponseBody().write(getValue);
                        break;
                    case "DELETE":
                        dao.delete(id);
                        httpExchange.sendResponseHeaders(202, 0);
                        break;
                    case "PUT":
                        final int contentlength = Integer.valueOf(httpExchange.getRequestHeaders().getFirst("Content-length"));
                        final byte[] putValue = new byte[contentlength];
                        if ((httpExchange.getRequestBody().read(putValue) != (putValue.length )) && (contentlength != 0)) {
                            throw new IOException("Can`t read at once");
                        }

                        dao.upsert(id,putValue);
                        httpExchange.sendResponseHeaders(201, 0);
                        break;

                    default:
                        httpExchange.sendResponseHeaders(405, 0);
                }

                httpExchange.close();

            }
        }));
    }

    @Override
    public void start() {
        this.server.start();
    }

    @Override
    public void stop() {
        this.server.stop(0);
    }

    private static class ErrorHandler implements HttpHandler {
        private final HttpHandler delegate;

        private ErrorHandler(HttpHandler delegate) {
            this.delegate = delegate;
        }


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                delegate.handle(httpExchange);
            } catch (NoSuchElementException e) {
                httpExchange.sendResponseHeaders(404, 0);
                httpExchange.close();
            } catch (IllegalArgumentException e) {
                httpExchange.sendResponseHeaders(400, 0);
                httpExchange.close();
            }
        }
    }

}
