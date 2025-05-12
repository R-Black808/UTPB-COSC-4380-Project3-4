import java.math.BigInteger;

public class RSA {
    private BigInteger p, q, n, phi, e, d;

    public RSA(int bits) {
        p = BigInteger.probablePrime(bits, Rand.getRand());
        q = BigInteger.probablePrime(bits, Rand.getRand());
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537); // Common choice
        d = e.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger msg, BigInteger pubKey, BigInteger modulus) {
        return msg.modPow(pubKey, modulus);
    }

    public BigInteger decrypt(BigInteger cipher) {
        return cipher.modPow(d, n);
    }

    public BigInteger sign(BigInteger msg) {
        return msg.modPow(d, n);
    }

    public BigInteger verify(BigInteger sig, BigInteger pubKey, BigInteger modulus) {
        return sig.modPow(pubKey, modulus);
    }

    public BigInteger getPublicKey() {
        return e;
    }

    public BigInteger getPrivateKey() {
        return d;
    }

    public BigInteger getModulus() {
        return n;
    }

    public static void main(String[] args) {
        RSA A = new RSA(512);
        RSA B = new RSA(512);

        System.out.printf("A's Public Key: (e=%s, n=%s)%n", A.e, A.n);
        System.out.printf("B's Public Key: (e=%s, n=%s)%n", B.e, B.n);

        BigInteger message = new BigInteger("42");

        // A sends message signed to B
        BigInteger signed = A.sign(message);
        BigInteger encrypted = B.encrypt(signed, B.e, B.n);

        System.out.printf("%nOriginal message: %s%n", message);
        System.out.printf("Signed by A: %s%n", signed);
        System.out.printf("Encrypted for B: %s%n", encrypted);

        BigInteger received = B.decrypt(encrypted);
        BigInteger verified = A.verify(received, A.e, A.n);

        System.out.printf("B receives: %s%n", received);
        System.out.printf("After verification (should match original): %s%n", verified);

        // B replies
        BigInteger response = new BigInteger("99");
        BigInteger encForA = A.encrypt(response, A.e, A.n);
        BigInteger signedByB = B.sign(encForA);
        BigInteger finalMsg = A.verify(signedByB, B.e, B.n);

        System.out.printf("%nB sends response: %s%n", response);
        System.out.printf("Encrypted for A: %s%n", encForA);
        System.out.printf("Signed by B: %s%n", signedByB);
        System.out.printf("Verified by A: %s%n", finalMsg);
    }
}
