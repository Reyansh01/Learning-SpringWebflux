package com.WebFlux.WebfluxWithRSockets.Subscriber;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;


@Component
public class SubscriberClass implements Ordered, ApplicationListener<ApplicationEvent> {

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		RSocketFactory
		.connect()
		.transport(TcpClientTransport.create(7000))
		.start()
		.flatMapMany(sender -> sender.requestStream(DefaultPayload.create("Learning Spring Webflux"))
				.map(Payload::getDataUtf8)
			)
		.subscribe(); // result -> log.info("processing new value " + result)
	}

}
