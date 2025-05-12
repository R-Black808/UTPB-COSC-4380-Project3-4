
# Project 3 - Diffie-Hellman and RSA

This project covers two basic cryptographic algorithms we studied: Diffie-Hellman key exchange and RSA encryption.

---

## Diffie-Hellman Key Exchange (DHE.java)

This part of the project simulates how two or more people can securely agree on a shared secret over an insecure channel.

### How it works:
- Each user generates a large random number (private key)
- They compute a public key using a shared generator `g` and prime `p`
- After exchanging public keys, each user computes the shared secret using modular exponentiation

### What I tested:
- Key generation using 2048-bit safe primes
- Both 2-party and 3-party versions of Diffie-Hellman
- Verified that all parties got matching shared keys

---

## RSA Encryption (RSA.java)

This part simulates secure message sending and verification using RSA.

### How it works:
- Each user generates a public-private key pair
- A message can be encrypted using someone’s public key and decrypted using their private key
- A message can also be signed with a private key and verified with the public key to prove authenticity

### What I tested:
- A sends a signed message to B (using A’s private key), then encrypts it with B’s public key
- B decrypts it and verifies the signature with A’s public key
- B responds with their own signed and encrypted message, and A verifies it

---

## How to Run It

You just need to compile and run like this:

```bash
javac DHE.java RSA.java Crypto.java Rand.java
java DHE
java RSA
```

You’ll see all the key values and the original messages being passed and verified.

---

## Notes

- I used 512-bit keys for testing speed
- Random values are securely generated using Java's SecureRandom class
- The code includes print statements for testing and understanding

---

## Author

Roy Lujan  
COSC Cryptography – Project 3  
Spring 2025
Guided OpenAI ref link https://chatgpt.com/share/682177b5-16f0-8002-a1b4-caf440d1ba88