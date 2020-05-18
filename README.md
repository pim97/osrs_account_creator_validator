# Osrs account creator validator
Automatically creates osrs accounts on their website and validates the account on protonmail


# Config.java file
In the config.java some settings have to be modified before you will be able to create accounts.

- Add proxies
  - You can add proxies by adding a ProxyAccount to the proxies ArrayList, example
    ```java
        proxies.add(new ProxyAccount("ip", "port"));
        proxies.add(new ProxyAccount("ip", "port", "username, "password"));
    ```
- baseProtonEmailStart
  - The start of your protonmail account i.e: "accountgenerator101"
- baseProtonEmailEnding
  - The end of your protonmail account i.e: "@protonmail.ch"
- cookieStringFromProtonmail
  - Once you're logged on protonmail, get the cookies in the following way in the chrome developer by enetering CTRL + SHIFT + I on your keyboard:
   ```javascript
   document.cookie
   ```
   - And paste these cookies into here. These cookies will give you ACCESS to your account, keey them safe!
- privateKeyFileFromProtonmailPgpLocation
  - Private key location to decrypt your messages from protonmail with (PGP algorithm), may be found here https://mail.protonmail.com/keys
- privateKeyPassword
  - The private key password you've entered when creating the private key file (also https://mail.protonmail.com/keys)
- captchaSolverUrl
  - Your captcha service solver, integrated for 2captcha.com
- captchaApiKey
    - The api key for 2captcha.com
- accountsToCreate
  - The amount of accounts you want to create

When all configuration is set up, it will create accounts & validates them for you

The accounts will be set up in the accounts.txt file in the following way:

ip : port : email : password

Created by Pim
