package io.github.projektmedinf.wifisdcryptolocker.service;

import io.github.projektmedinf.wifisdcryptolocker.model.Image;
import io.github.projektmedinf.wifisdcryptolocker.model.Session;

import java.util.List;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface ImageService {

    /**
     * Get {@code Image} by their {@code session}
     *
     * @param session session of the wanted image
     * @return all images corresponding to given session
     */
    List<Image> getImagesBySession(Session session);

    /**
     * Insert a new {@code Image} given a {@code image}
     *
     * @param image image to insert
     * @return the id of the newly inserted image
     */
    long insertImage(Image image);
}
