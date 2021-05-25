#!/bin/bash -eu

# direcotry of THIS script
SELF_DIR="$(dirname "$(realpath "$0")")"
DEST_DIR="$SELF_DIR/app/.res/"
mkdir -p "$DEST_DIR"

# TODO clarify where to go
cd ../

#gradle --console=plain bootJar installDist
gradle --console=plain clean bootJar installDist

cp -R build/install/ "$DEST_DIR/"

cd docker/

docker-compose up --build --remove-orphans --force-recreate --abort-on-container-exit
