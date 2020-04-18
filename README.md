1. Wymagane konfiguracje:
- dodanie skryptem add_user.sh użytkownika:
    Username: soa1
    Password: soa1
    Group: TrainsManager
    
- Dodanie security domain poprzez wklejenie poniższego tekstu  w tagu <security-domains> w pliku standalone.xml:

                <security-domain name="TrainsManagementDomain" cache-type="default">
                    <authentication>
                        <login-module code="UserRoles" flag="required">
                            <module-option name="usersProperties" value="user.properties" />
                            <module-option name="rolesProperties" value="roles.properties" />
                            <module-option name="unauthenticatedIdentity" value="nobody" />
                        </login-module>    
                    </authentication>
                </security-domain>
     
2. Adresy endpointów:
http://localhost:8080/soap-api/TrainService?wsdl

3. Sposób uruchomienia klienta:
Uruchomienie metody main() z klasy ApiConsumer.java

4. Dodatkowe informacje:
- Java 11
- Wildfly 19.0.0 