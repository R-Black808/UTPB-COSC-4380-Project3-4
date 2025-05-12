import java.math.BigInteger;
import java.security.SecureRandom;

public class DHE {

    private BigInteger generator;
    private BigInteger prime;

    public DHE(int gBits, int pBits) {
        this.generator = BigInteger.TWO; 
        this.prime = new BigInteger(pBits, 100, Rand.getRand()); 
    }

    public BigInteger getBase(int bits) {
        return new BigInteger(bits, Rand.getRand());
    }

    public BigInteger getExponent(BigInteger base) {
        return base.modPow(generator, prime);
    }

    public BigInteger getKey(BigInteger base, BigInteger exponent) {
        return base.modPow(exponent, prime);
    }

    public static void main(String[] args) {
        DHE d = new DHE(512, 2048);
        System.out.printf("g = %s%np = %s%n%n", d.generator, d.prime);

        BigInteger a = d.getBase(512);
        BigInteger b = d.getBase(512);

        System.out.printf("a = %s%nb = %s%n%n", a, b);

        BigInteger A = d.getExponent(a);
        BigInteger B = d.getExponent(b);

        System.out.printf("A = %s%nB = %s%n%n", A, B);

        BigInteger aKey = d.getKey(b, a);
        BigInteger bKey = d.getKey(a, b);

        System.out.printf("keys = %s%n%s%n%n", aKey, bKey);

        // Optional 3-party
        DHE e = new DHE(512, 2048);
        System.out.printf("g = %s%np = %s%n%n", e.generator, e.prime);

        BigInteger x = e.getBase(512);
        BigInteger y = e.getBase(512);
        BigInteger z = e.getBase(512);

        System.out.printf("x = %s%ny = %s%nz = %s%n%n", x, y, z);

        BigInteger X = e.getExponent(x);
        BigInteger Y = e.getExponent(y);
        BigInteger Z = e.getExponent(z);

        System.out.printf("X = %s%nY = %s%nZ = %s%n%n", X, Y, Z);

        BigInteger xKey = e.getKey(e.getKey(Z, y), x);
        BigInteger yKey = e.getKey(e.getKey(X, z), y);
        BigInteger zKey = e.getKey(e.getKey(Y, x), z);

        System.out.printf("keys = %s%n%s%n%s", xKey, yKey, zKey);
    }
}
