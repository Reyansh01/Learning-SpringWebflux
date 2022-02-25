package com.WebFlux.WebfluxWithRSockets.Publisher;

import java.time.Instant;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import io.rsocket.AbstractRSocket;
import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.SocketAcceptor;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Component
public class PublisherClass implements Ordered, ApplicationListener<ApplicationEvent> {
	
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
	
	Flux<String> helloEveryone(String name) {
		return Flux.fromStream(Stream.generate(new Supplier<String>() {
			
			@Override
			public String get() {
				return "Hi " + name + " " + " @ " + Instant.now().toString();
			}
		}));
	}
	
//	TcpServerTransport transport = TcpServerTransport.create(7000);
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		SocketAcceptor socketAcceptor = new SocketAcceptor() {
			
			@Override
			public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
				
				AbstractRSocket abstractRSocket = new AbstractRSocket() {
					
					@Override
					public Flux<Payload> requestStream(Payload payload) {
						String name = payload.getDataUtf8();
						return helloEveryone(name)
								.map(greeting -> DefaultPayload.create(name));
//						return super.requestStream(payload);
					}
				};
				return Mono.just(abstractRSocket);
			}
		};
		
		TcpServerTransport transport = TcpServerTransport.create(7000);
		
		RSocketFactory
		.receive()
		.acceptor(socketAcceptor)
		.transport(transport)
		.start()
		.block();
	}

}
