sbt-swagger-meta
============================

[![Build Status](https://travis-ci.org/y-yu/sbt-swagger-meta.svg?branch=master)](https://travis-ci.org/y-yu/sbt-swagger-meta)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.y-yu/sbt-swagger-meta/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.y-yu/sbt-swagger-meta)

This is a sbt plugin to generate a [Swagger](https://swagger.io/) YAML/JSON file based on annotations.

## How to Use

1. `./project/plugins.sbt`

    ```
    addSbtPlugin(com.github.y-yu" % "sbt-swagger-meta" % "0.1.7")
    ```
    - if you want to use a SNAPSHOT version, you write following.
        ```
        addSbtPlugin(com.github.y-yu" % "sbt-swagger-meta" % "0.1.7-SNAPSHOT")
        ```
    
2. `build.sbt`

    ```
    enablePlugins(SbtSwaggerMeta)
    ```

See also `./example` and `./src/sbt-test` directories.

## License

[MIT](https://github.com/y-yu/sbt-swagger-meta/blob/master/LICENSE)
