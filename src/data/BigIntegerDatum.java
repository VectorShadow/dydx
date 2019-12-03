package data;

import data.AbstractDatum;

import java.math.BigInteger;

public class BigIntegerDatum extends AbstractDatum {
    private BigInteger key;

    public BigIntegerDatum(BigInteger k) {
        key = k;
    }
    public BigInteger getKey() {
        return key;
    }
}
