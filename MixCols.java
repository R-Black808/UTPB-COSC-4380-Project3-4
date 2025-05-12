
public class MixCols {

    private static final int[] mul2 = new int[256];
    private static final int[] mul3 = new int[256];
    private static final int[] mul9 = new int[256];
    private static final int[] mul11 = new int[256];
    private static final int[] mul13 = new int[256];
    private static final int[] mul14 = new int[256];

    static {
        for (int i = 0; i < 256; i++) {
            mul2[i] = xtime(i);
            mul3[i] = mul2[i] ^ i;
            mul9[i] = mul(i, 9);
            mul11[i] = mul(i, 11);
            mul13[i] = mul(i, 13);
            mul14[i] = mul(i, 14);
        }
    }

    private static int xtime(int b) {
        return ((b << 1) ^ (((b >>> 7) & 1) * 0x1b)) & 0xff;
    }

    private static int mul(int b, int factor) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            if ((factor & (1 << i)) != 0) {
                int temp = b;
                for (int j = 0; j < i; j++) temp = xtime(temp);
                result ^= temp;
            }
        }
        return result;
    }

    public static void mixColumns(byte[] state) {
        for (int c = 0; c < 4; c++) {
            int i = c * 4;
            int s0 = state[i] & 0xff;
            int s1 = state[i + 1] & 0xff;
            int s2 = state[i + 2] & 0xff;
            int s3 = state[i + 3] & 0xff;

            state[i]     = (byte) (mul2[s0] ^ mul3[s1] ^ s2 ^ s3);
            state[i + 1] = (byte) (s0 ^ mul2[s1] ^ mul3[s2] ^ s3);
            state[i + 2] = (byte) (s0 ^ s1 ^ mul2[s2] ^ mul3[s3]);
            state[i + 3] = (byte) (mul3[s0] ^ s1 ^ s2 ^ mul2[s3]);
        }
    }

    public static void invMixColumns(byte[] state) {
        for (int c = 0; c < 4; c++) {
            int i = c * 4;
            int s0 = state[i] & 0xff;
            int s1 = state[i + 1] & 0xff;
            int s2 = state[i + 2] & 0xff;
            int s3 = state[i + 3] & 0xff;

            state[i]     = (byte) (mul14[s0] ^ mul11[s1] ^ mul13[s2] ^ mul9[s3]);
            state[i + 1] = (byte) (mul9[s0] ^ mul14[s1] ^ mul11[s2] ^ mul13[s3]);
            state[i + 2] = (byte) (mul13[s0] ^ mul9[s1] ^ mul14[s2] ^ mul11[s3]);
            state[i + 3] = (byte) (mul11[s0] ^ mul13[s1] ^ mul9[s2] ^ mul14[s3]);
        }
    }
}
