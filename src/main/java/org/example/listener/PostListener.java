package org.example.listener;

import org.example.mapper.ItemMapper;
import org.example.utils.AliOSSUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
public class PostListener {
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Autowired
    private ItemMapper itemMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "post.queue", durable = "true"),
            exchange = @Exchange(name = "post.direct", type = ExchangeTypes.DIRECT, durable = "true"),
            key = "post"
    ))
    public void post(Map<String, Object> message) throws IOException {
        String name = (String) message.get("name");
        String phone = (String) message.get("phone");
        String description = (String) message.get("description");
        String location = (String) message.get("location");
        Integer type = (Integer) message.get("type");
        String imageBase64 = (String) message.get("image");
        byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
        MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", imageBytes);
        String imageUrl = null;
        try {
            imageUrl = aliOSSUtils.upload(image);
            itemMapper.insertItem(name, phone, description, location, imageUrl, type);
        } catch (Exception e) {
            if (imageUrl != null) {
                aliOSSUtils.delete(imageUrl);
            }
            throw e;
        }
    }
}
