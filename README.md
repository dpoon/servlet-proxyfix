# Servlet Proxyfix

This servlet filter implements a solution to inform a web application of the
intended `serverName` and `serverPort` when the servlet engine sits behind a
reverse proxy.

If the servlet engine configured to listen on `localhost:8080`, for example,
then when the web application calls `request.getServerName()` and
`request.getServerPort()`, it will obtain `"localhost"` and `8080`.
This will often lead to the web application generating self-referential URLs
incorrectly.

Using this filter, `request.getServerName()` and `request.getServerPort()`
will return values based on an `X-Forwarded-Host` HTTP header, if it exists.

## Installing

Run `mvn package` to build `target/servlet-proxyfix-1.0.jar`.  Copy the JAR
file to the `WEB-INF/lib` directory if your web application.

Edit the `WEB-INF/web.xml` configuration file of the web application, inserting
the following elements within the top-level `<web-app>` element:

    <filter>
      <filter-name>servlet-proxyfix</filter-name>
      <filter-class>ca.ubc.ece.proxyfix.UrlRewriteFilter</filter-class>
    </filter>
    <filter-mapping>
      <filter-name>servlet-proxyfix</filter-name>
      <url-pattern>*</url-pattern>
    </filter-mapping>

The included `tools/web-xml.xslt` file contains an XSLT transformation to do
modify `web.xml` as specified above.

## Alternatives

An alternative solution to the reverse-proxy problem is to [configure Tomcat's
HTTP connector](https://tomcat.apache.org/tomcat-7.0-doc/proxy-howto.html) to
set the `proxyName` and `proxyPort` attributes in the `<Connector>`
element.  However, that would involve hard-coding values that may not hold
true for all requests.

## License

This project is licensed under the Apache 2.0 License — see the
[LICENSE.txt](LICENSE.txt) file for details.
