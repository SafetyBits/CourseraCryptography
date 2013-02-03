CourseraCryptography
====================

Repo for Cryptography course on coursera (https://www.coursera.org/course/crypto)

Programming assignment for block ciphers (class week2.Prog2) are solved using JCE and Bouncycastle cryptoprovider.
Decryption of AES cipher in CBC mode passed fine using only JCE methods and AES/CBC/PKCS5Padding transformation. However i'm failed to decrypt AES cipher in CTR mode using AES/CTR/NoPadding transformation. So my solution is to first encrypt IV  using raw AES/ECB/NoPadding transformation and then manually XORing the result with ciphertext.
