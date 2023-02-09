# Spring security with JWT Authentication
* /auth/jwt - Allow the request without any authN.
Use the built-in **InMemoryUserDetailsManager** default method loadUserByUsername to check user-pwd entered against in-memory map. 
Once, it matches, generate JSON Web Token.

* Other requests like /auth/hello - Validate and use **JWT token** generated in previous step for Authentication.
Set up **custom filter** to add this step to authN filter chain.


