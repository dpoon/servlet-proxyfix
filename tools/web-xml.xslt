<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:javaee="http://java.sun.com/xml/ns/javaee"
                xmlns="http://java.sun.com/xml/ns/javaee">

    <xsl:output indent="yes"/>
    <xsl:preserve-space elements="*"/>

    <!-- Identity transform -->
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>
 
    <!-- Insert <filter> and <filter-mapping> entries -->
    <xsl:template match="/javaee:web-app">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:text>&#xa;</xsl:text>
            <xsl:comment> Filter for supporting reverse proxy </xsl:comment>
            <filter>
              <filter-name>servlet-proxyfix</filter-name>
              <filter-class>ca.ubc.ece.proxyfix.UrlRewriteFilter</filter-class>
            </filter>
            <filter-mapping>
              <filter-name>servlet-proxyfix</filter-name>
              <url-pattern>*</url-pattern>
            </filter-mapping>
            <xsl:text>&#xa;</xsl:text>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
