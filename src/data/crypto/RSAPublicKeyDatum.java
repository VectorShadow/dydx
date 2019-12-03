package data.crypto;

import data.AbstractDatum;

import java.math.BigInteger;

public class RSAPublicKeyDatum extends AbstractDatum {
    private BigInteger key;

    public RSAPublicKeyDatum(BigInteger k) {
        key = k;
    }
    public BigInteger getKey() {
        return key;
    }
}
