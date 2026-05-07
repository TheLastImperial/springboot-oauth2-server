# OAuth2 Authorization Server

Example of OAuth2 Authorization Server using Spring Boot with RegisteredClient persistence in
database.

## Database

The database have a default registered client schema and data from Spring Documentation.

Create the env vars:

    - `SPRING_DATASOURCE_URL`
    - `SPRING_DATASOURCE_USERNAME`
    - `SPRING_DATASOURCE_PASSWORD`

## KeyPairs

Create env vars for the public and private key as strings.

    - `COM_THELASTIMPERIAL_OAUTH2_SERVER_KEYPAIR_PUBLIC`
    - `COM_THELASTIMPERIAL_OAUTH2_SERVER_KEYPAIR_PRIVATE`
