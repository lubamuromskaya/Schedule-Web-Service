FROM openjdk:16.0.1-jdk

# TODO: "install" must be a subdirectory, located here in same directory
COPY .res/install/ /

# TODO:

ENTRYPOINT /Schedule/bin/Schedule org.springframework.boot.loader.JarLauncher
#./res/install/Schedule/bin/Schedule org.springframework.boot.loader.JarLauncher
