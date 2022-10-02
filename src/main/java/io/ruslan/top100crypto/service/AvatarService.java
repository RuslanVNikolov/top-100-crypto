package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.Avatar;
import io.ruslan.top100crypto.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.bson.BsonBinarySubType.BINARY;

@RequiredArgsConstructor
@Service
public class AvatarService {


    private final AvatarRepository avatarRepository;

    public String addAvatar(String title, MultipartFile file) throws IOException {
        return avatarRepository.insert(Avatar.builder()
                .image(new Binary(BINARY, file.getBytes()))
                .title(title)
                .build()).getId();
    }

    public Avatar getAvatarById(String id) {
        return avatarRepository.findById(id).get();
    }
}
