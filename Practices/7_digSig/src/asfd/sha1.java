package asfd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha1 {
    public byte[] digest(byte[]plaintext) throws NoSuchAlgorithmException{
        byte[] md;
        MessageDigest sha= MessageDigest.getInstance("SHA1");
        md=sha.digest(plaintext);
        return md;
    }
}
