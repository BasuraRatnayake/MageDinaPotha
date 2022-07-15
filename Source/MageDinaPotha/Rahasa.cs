using System;
using System.IO;
using System.Security;
using System.Security.Cryptography;
using System.Text;

namespace MageDinaPotha {
    public sealed class Rahasa {
        private SecureString yathura;

        private const int GetSaltLength = 8;
        private const string alpha_numeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_#";

        public Rahasa() {}        

        public string generateRandom() {
            string ran_string = string.Empty;
            for(int i = 0; i < 30; i++) ran_string += alpha_numeric[new Random().Next(0, alpha_numeric.Length)];           
             
            return Convert.ToBase64String(SHA256Managed.Create().ComputeHash(Encoding.UTF8.GetBytes(ran_string)));
        }

        public SecureString generate_secure_yathura(string key = null) {
            SecureString skey = new SecureString();
            string ran = (key == null) ? generateRandom() : key;

            for (int i = 0; i < ran.Length; i++) skey.AppendChar(ran[i]);
            
            return skey;
        }

        public Rahasa(SecureString yathura) {
            this.yathura = yathura;
        }

        private byte[] GetRandomBytes() {
            int saltLength = GetSaltLength;
            byte[] ba = new byte[saltLength];
            RNGCryptoServiceProvider.Create().GetBytes(ba);
            return ba;
        }        

        public string Encrypt(string text) {
            byte[] baPwd = Encoding.UTF8.GetBytes(new System.Net.NetworkCredential(string.Empty, yathura).Password);

            // Hash the password with SHA256
            byte[] baPwdHash = SHA256Managed.Create().ComputeHash(baPwd);

            byte[] baText = Encoding.UTF8.GetBytes(text);

            byte[] baSalt = GetRandomBytes();
            byte[] baEncrypted = new byte[baSalt.Length + baText.Length];

            // Combine Salt + Text
            for (int i = 0; i < baSalt.Length; i++) baEncrypted[i] = baSalt[i];
            for (int i = 0; i < baText.Length; i++) baEncrypted[i + baSalt.Length] = baText[i];

            baEncrypted = AES_Encrypt(baEncrypted, baPwdHash);

            return Convert.ToBase64String(baEncrypted);
        }

        public string Decrypt(string text) {
            byte[] baPwd = Encoding.UTF8.GetBytes(new System.Net.NetworkCredential(string.Empty, yathura).Password);

            // Hash the password with SHA256
            byte[] baPwdHash = SHA256Managed.Create().ComputeHash(baPwd);

            byte[] baText = Convert.FromBase64String(text);

            byte[] baDecrypted = AES_Decrypt(baText, baPwdHash);

            // Remove salt
            int saltLength = GetSaltLength;
            byte[] baResult = new byte[baDecrypted.Length - saltLength];
            for (int i = 0; i < baResult.Length; i++) baResult[i] = baDecrypted[i + saltLength];

            return Encoding.UTF8.GetString(baResult);
        }

        public void EncryptFile(string file) {
            byte[] bytesToBeEncrypted = File.ReadAllBytes(file);
            byte[] passwordBytes = Encoding.UTF8.GetBytes(new System.Net.NetworkCredential(string.Empty, yathura).Password);

            // Hash the password with SHA256
            passwordBytes = SHA256.Create().ComputeHash(passwordBytes);

            byte[] bytesEncrypted = AES_Encrypt(bytesToBeEncrypted, passwordBytes);

            File.WriteAllBytes(file, bytesEncrypted);
        }

        public void DecryptFile(string file) {
            byte[] bytesToBeDecrypted = File.ReadAllBytes(file);
            byte[] passwordBytes = Encoding.UTF8.GetBytes(new System.Net.NetworkCredential(string.Empty, yathura).Password);
            passwordBytes = SHA256.Create().ComputeHash(passwordBytes);

            byte[] bytesDecrypted = AES_Decrypt(bytesToBeDecrypted, passwordBytes);

            File.WriteAllBytes(file, bytesDecrypted);
        }

        public MemoryStream DecryptStream(string file) {
            byte[] passwordBytes = Encoding.UTF8.GetBytes(new System.Net.NetworkCredential(string.Empty, yathura).Password);
            passwordBytes = SHA256.Create().ComputeHash(passwordBytes);

            byte[] bytes2BDecrypted = File.ReadAllBytes(file);
            byte[] bytesDecrypted = AES_Decrypt(bytes2BDecrypted, passwordBytes);

            return new MemoryStream(bytesDecrypted); 
        }

        private byte[] AES_Encrypt(byte[] bytesToBeEncrypted, byte[] passwordBytes) {
            byte[] encryptedBytes = null;

            byte[] saltBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };

            using (MemoryStream ms = new MemoryStream()) {
                using (RijndaelManaged AES = new RijndaelManaged()) {
                    AES.KeySize = 256;
                    AES.BlockSize = 128;

                    var key = new Rfc2898DeriveBytes(passwordBytes, saltBytes, 1000);
                    AES.Key = key.GetBytes(AES.KeySize / 8);
                    AES.IV = key.GetBytes(AES.BlockSize / 8);

                    AES.Mode = CipherMode.CBC;

                    using (var cs = new CryptoStream(ms, AES.CreateEncryptor(), CryptoStreamMode.Write)) {
                        cs.Write(bytesToBeEncrypted, 0, bytesToBeEncrypted.Length);
                        cs.Close();
                    }
                    encryptedBytes = ms.ToArray();
                }
            }

            return encryptedBytes;
        }

        private byte[] AES_Decrypt(byte[] bytesToBeDecrypted, byte[] passwordBytes) {
            byte[] decryptedBytes = null;

            byte[] saltBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };

            using (MemoryStream ms = new MemoryStream()) {
                using (RijndaelManaged AES = new RijndaelManaged()) {
                    AES.KeySize = 256;
                    AES.BlockSize = 128;

                    var key = new Rfc2898DeriveBytes(passwordBytes, saltBytes, 1000);
                    AES.Key = key.GetBytes(AES.KeySize / 8);
                    AES.IV = key.GetBytes(AES.BlockSize / 8);

                    AES.Mode = CipherMode.CBC;

                    using (var cs = new CryptoStream(ms, AES.CreateDecryptor(), CryptoStreamMode.Write)) {
                        cs.Write(bytesToBeDecrypted, 0, bytesToBeDecrypted.Length);
                        cs.Close();
                    }
                    decryptedBytes = ms.ToArray();
                }
            }

            return decryptedBytes;
        }
    }
}