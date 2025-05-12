
# Cryptography Projects 3 & 4

## ✅ Project 3: Diffie-Hellman & RSA

### 📌 Objective:
Implement two key cryptographic algorithms:
- **Diffie-Hellman Key Exchange (DHE.java)**
- **RSA Encryption & Decryption (RSA.java)**

Both implementations use:
- `Crypto.java`: for safe prime generation, random values, modular exponentiation
- `Rand.java`: secure randomness

### 🧪 How It Works

#### 🔐 Diffie-Hellman (DHE.java)
- Generates a safe prime `p` and generator `g`
- Each party selects a private secret `a`, computes `A = g^a mod p`
- Key is derived as `K = B^a mod p`
- Includes main() with both 2-party and 3-party key agreement examples

#### 🔐 RSA (RSA.java)
- Generates public-private key pairs (n, e, d)
- Encrypts with: `c = m^e mod n`
- Decrypts with: `m = c^d mod n`
- Includes support for digital signatures

---

## ✅ Project 4: AES (ECB & CBC Modes)

### 📌 Objective:
Implement AES block cipher using:
- `AES.java` — main cipher logic (encrypt/decrypt in ECB/CBC)
- `MixCols.java` — mix column transformation
- `SBox.java` — SubBytes lookup table

### 🧪 How It Works
- 128-bit block AES using 16-byte keys
- ECB mode encrypts each block individually
- CBC mode uses chaining: `C[i] = AES(P[i] ⊕ C[i-1])`
- When debug flag is enabled, the cipher prints internal state for each round

---

## 🧪 Testing

### 🔧 Compile:
```bash
javac *.java
```

### ▶️ Run Project 3:
```bash
java DHE
java RSA
```

### ▶️ Run Project 4:
```bash
java AES
```

Debug mode can be toggled with:
```java
AES.debug = true;
```

---

## 🛠 Errors Encountered and Fixes

### Project 3
- Missing exponentiation logic → fixed using Crypto.fastModPow()
- Ensured safe primes via Crypto.makePrime()

### Project 4
- Byte mixing in MixCols was incomplete → added full GF(2^8) multiply logic
- AES round key scheduling was implemented
- Matching debug steps verified using `AES Debug.txt`

---

## 👤 Author

Roy Lujan — COSC Cryptography  
Completed with OpenAI assistance

---

To test AES debug mode:
- Use test key and block from `AES Debug.txt`
- Enable `AES.debug = true`
- Output will match expected debug lines for each round
