
import java.util.Arrays;
import java.security.SecureRandom;

public class AES {
    private static final int Nb = 4;
    private static final int Nk = 4;
    private static final int Nr = 10;
    private static final int BLOCK_SIZE = 16;

    public static boolean debug = true;

    private byte[][] roundKeys;
    private byte[] keyBytes;

    public AES(String key) {
        if (key.length() != 16) throw new IllegalArgumentException("Key must be 16 characters (128 bits)");
        this.keyBytes = key.getBytes();
        this.roundKeys = keyExpansion(this.keyBytes);
    }

    public byte[] encrypt(byte[] plaintext) {
        byte[] block = Arrays.copyOf(plaintext, BLOCK_SIZE);
        if (debug) System.out.println("Initial Block: " + Arrays.toString(block));

        addRoundKey(block, 0);
        for (int round = 1; round < Nr; round++) {
            subBytes(block);
            shiftRows(block);
            MixCols.mixColumns(block);
            addRoundKey(block, round);
            if (debug) System.out.println("After Round " + round + ": " + Arrays.toString(block));
        }
        subBytes(block);
        shiftRows(block);
        addRoundKey(block, Nr);
        if (debug) System.out.println("Encrypted Block: " + Arrays.toString(block));
        return block;
    }

    public byte[] decrypt(byte[] ciphertext) {
        byte[] block = Arrays.copyOf(ciphertext, BLOCK_SIZE);

        addRoundKey(block, Nr);
        for (int round = Nr - 1; round > 0; round--) {
            invShiftRows(block);
            invSubBytes(block);
            addRoundKey(block, round);
            MixCols.invMixColumns(block);
        }
        invShiftRows(block);
        invSubBytes(block);
        addRoundKey(block, 0);

        return block;
    }

    public byte[] encryptCBC(byte[] plaintext, byte[] iv) {
        byte[] block = Arrays.copyOf(plaintext, BLOCK_SIZE);
        byte[] xored = xorBlocks(block, iv);
        return encrypt(xored);
    }

    public byte[] decryptCBC(byte[] ciphertext, byte[] iv) {
        byte[] decrypted = decrypt(ciphertext);
        return xorBlocks(decrypted, iv);
    }

    private byte[] xorBlocks(byte[] a, byte[] b) {
        byte[] out = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            out[i] = (byte) (a[i] ^ b[i]);
        }
        return out;
    }

    private void addRoundKey(byte[] state, int round) {
        for (int c = 0; c < Nb; c++) {
            for (int r = 0; r < 4; r++) {
                state[r + 4 * c] ^= roundKeys[round * Nb + c][r];
            }
        }
    }

    private void subBytes(byte[] state) {
        for (int i = 0; i < state.length; i++) {
            state[i] = SBox.getSubByte(state[i]);
        }
    }

    private void invSubBytes(byte[] state) {
        for (int i = 0; i < state.length; i++) {
            state[i] = SBox.getInvSubByte(state[i]);
        }
    }

    private void shiftRows(byte[] state) {
        byte[] temp = Arrays.copyOf(state, 16);
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                state[r + 4 * c] = temp[r + 4 * ((c + r) % 4)];
            }
        }
    }

    private void invShiftRows(byte[] state) {
        byte[] temp = Arrays.copyOf(state, 16);
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                state[r + 4 * c] = temp[r + 4 * ((c - r + 4) % 4)];
            }
        }
    }

    private byte[][] keyExpansion(byte[] key) {
        byte[][] w = new byte[Nb * (Nr + 1)][4];
        for (int i = 0; i < Nk; i++) {
            for (int j = 0; j < 4; j++) {
                w[i][j] = key[i * 4 + j];
            }
        }
        for (int i = Nk; i < Nb * (Nr + 1); i++) {
            byte[] temp = Arrays.copyOf(w[i - 1], 4);
            if (i % Nk == 0) {
                temp = subWord(rotWord(temp));
                temp[0] ^= Rcon[i / Nk];
            }
            for (int j = 0; j < 4; j++) {
                w[i][j] = (byte) (w[i - Nk][j] ^ temp[j]);
            }
        }
        return w;
    }

    private byte[] rotWord(byte[] word) {
        return new byte[]{word[1], word[2], word[3], word[0]};
    }

    private byte[] subWord(byte[] word) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = SBox.getSubByte(word[i]);
        }
        return result;
    }

    private static final byte[] Rcon = {
        0x00, 0x01, 0x02, 0x04, 0x08,
        0x10, 0x20, 0x40, (byte) 0x80, 0x1B, 0x36
    };

    public static void main(String[] args) {
        AES.debug = true;
        AES aes = new AES("ThisIsASecretKey");

        byte[] message = "ThisIs16ByteMsg".getBytes();
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        System.out.println("Original: " + new String(message));

        // ECB Mode
        byte[] encryptedECB = aes.encrypt(message);
        byte[] decryptedECB = aes.decrypt(encryptedECB);
        System.out.println("ECB Decrypted: " + new String(decryptedECB));

        // CBC Mode
        byte[] encryptedCBC = aes.encryptCBC(message, iv);
        byte[] decryptedCBC = aes.decryptCBC(encryptedCBC, iv);
        System.out.println("CBC Decrypted: " + new String(decryptedCBC));
    }
}
