package io.github.projektmedinf.wifisdcryptolocker.model;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class Image {

    private long id;
    private byte[] encryptedImageData;
    private byte[] initialisationVector;
    private int padding;
    private Session session;
    private byte[] decryptedImageData;

    public Image(long id, byte[] encryptedImageData, byte[] initialisationVector, int padding, Session session, byte[] decryptedImageData) {
        this.id = id;
        this.encryptedImageData = encryptedImageData;
        this.initialisationVector = initialisationVector;
        this.padding = padding;
        this.session = session;
        this.decryptedImageData = decryptedImageData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getEncryptedImageData() {
        return encryptedImageData;
    }

    public void setEncryptedImageData(byte[] encryptedImageData) {
        this.encryptedImageData = encryptedImageData;
    }

    public byte[] getInitialisationVector() {
        return initialisationVector;
    }

    public void setInitialisationVector(byte[] initialisationVector) {
        this.initialisationVector = initialisationVector;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public byte[] getDecryptedImageData() {
        return decryptedImageData;
    }

    public void setDecryptedImageData(byte[] decryptedImageData) {
        this.decryptedImageData = decryptedImageData;
    }
}
