sbt-swagger-meta
============================

[![CI](https://github.com/y-yu/sbt-swagger-meta/actions/workflows/ci.yml/badge.svg)](https://github.com/y-yu/sbt-swagger-meta/actions/workflows/ci.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.y-yu/sbt-swagger-meta/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.y-yu/sbt-swagger-meta)

This is a sbt plugin to generate a [Swagger](https://swagger.io/) YAML/JSON file based on annotations.

## How to Use

1. `./project/plugins.sbt`

    ```
    addSbtPlugin(com.github.y-yu" % "sbt-swagger-meta" % "0.2.0")
    ```
    - if you want to use a SNAPSHOT version, you write following.
        ```
        addSbtPlugin("com.github.y-yu" % "sbt-swagger-meta" % "0.1.8-SNAPSHOT")
        ```
    
2. `build.sbt`

    ```
    enablePlugins(SbtSwaggerMeta)
    ```

See also `./example` and `./src/sbt-test` directories.

## License

[MIT](https://github.com/y-yu/sbt-swagger-meta/blob/master/LICENSE)
