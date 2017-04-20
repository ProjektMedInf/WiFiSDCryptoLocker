package io.github.projektmedinf.wifisdcryptolocker.service.impl;

import android.content.Context;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.ImageDao;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.impl.ImageDaoImpl;
import io.github.projektmedinf.wifisdcryptolocker.model.Image;
import io.github.projektmedinf.wifisdcryptolocker.model.Session;
import io.github.projektmedinf.wifisdcryptolocker.service.ImageService;

import java.util.List;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class ImageServiceImpl implements ImageService {

    private ImageDao imageDao;

    public ImageServiceImpl(Context context) {
        imageDao = new ImageDaoImpl(context);
    }

    @Override
    public List<Image> getImagesBySession(Session session) {
        return imageDao.getImagesBySession(session);
    }
}
