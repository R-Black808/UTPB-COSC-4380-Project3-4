
# Project 4 - AES Encryption

This project focuses on implementing the AES encryption algorithm using Java.

---



My goal was to implement this working version of AES that would support two different modes:

1. **ECB Mode**  
   - This mode encrypts each 16-byte block of data separately.
   - It’s simple, but not secure for patterns in real-world data.

2. **CBC Mode**  
   - This mode adds more security by using an initialization vector to chain the encryption of each block.
   - Each block gets XORed with the previous encrypted block before being encrypted again.

---

## What I Tested

- I used a 16-character string as my AES key (128-bit)
- I encrypted the message `"ThisIs16ByteMsg"`
- Then I decrypted it to make sure I got the original back

Both ECB and CBC decrypted back to the original message without any errors.

---

## Debug Output

I enabled a debug flag in the AES class (`AES.debug = true`) so I could see how each round of AES worked.

It printed out:
- Initial block before encryption
- Block state after each round
- Final encrypted block
- And then showed how it reversed all those steps during decryption

This helped me verify everything step-by-step.

---

## How to Run It

```bash
javac AES.java MixCols.java SBox.java Crypto.java Rand.java
java AES
```

You’ll see some debug output and the original message appear again after decryption at the bottom.

---

## Final Thoughts

This project helped me better understand how AES works behind the scenes. I saw how each round transformed the data and how modes like CBC add an extra layer of protection by chaining blocks together.

---

Roy Lujan  
COSC Cryptography – Project 4  
Spring 2025
OpenAI guidence ref link https://chatgpt.com/share/682177b5-16f0-8002-a1b4-caf440d1ba88