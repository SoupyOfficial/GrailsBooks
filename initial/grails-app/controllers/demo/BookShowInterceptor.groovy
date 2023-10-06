package demo

import com.budjb.rabbitmq.publisher.RabbitMessagePublisher
import groovy.transform.CompileStatic

@CompileStatic
class BookShowInterceptor {
    RabbitMessagePublisher rabbitMessagePublisher

    BookShowInterceptor() {
        match(controller:"book", action:"show")
    }

    boolean after() {
        final Book book = (Book) model.bookInstance

        rabbitMessagePublisher.send {
            routingKey = "bookQueue"
            body = [id: book.id, title: book.title]
        }
        true
    }
}