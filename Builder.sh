#!/bin/bash

cp -R ./src ~/Desktop/BloxMac

rm -r out
rm -rf ./BloxMac-Intel.app ./BloxMac-ARM.app ./DMG
mkdir out
javac -d out $(find src -name "*.java")

jar cfm ./AppBuilder/input_jars/BloxMac.jar MANIFEST.MF -C out .

echo "BUIDING ..."

rm -rf ./AppBuilder/icons/BloxMac.icns

iconutil -c icns ./AppBuilder/icons/BloxMac.iconset


export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17_x64.jdk/Contents/Home
jpackage --name BloxMac-Intel \
  --input ./AppBuilder/input_jars \
  --main-jar BloxMac.jar \
  --main-class src.View \
  --type app-image \
  --icon ./AppBuilder/icons/BloxMac.icns


export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17_arm64.jdk/Contents/Home
jpackage --name BloxMac-ARM \
  --input ./AppBuilder/input_jars \
  --main-jar BloxMac.jar \
  --main-class src.View \
  --type app-image \
  --icon ./AppBuilder/icons/BloxMac.icns

mkdir -p ./DMG


cp -R ./BloxMac-Intel.app ./DMG
hdiutil create -volname "BloxMac-Intel" -srcfolder ./DMG/BloxMac-Intel.app -ov -format UDZO ./DMG/BloxMac-Intel.dmg
rm -rf ./DMG/BloxMac-Intel.app


cp -R ./BloxMac-ARM.app ./DMG
hdiutil create -volname "BloxMac-ARM" -srcfolder ./DMG/BloxMac-ARM.app -ov -format UDZO ./DMG/BloxMac-ARM.dmg
rm -rf ./DMG/BloxMac-ARM.app


rm -rf ~/Desktop/BloxMac/DMG
mkdir -p ~/Desktop/BloxMac/DMG
cp ./DMG/*.dmg ~/Desktop/BloxMac/DMG/

cp -R ./src ~/Desktop/BloxMac
cp -R ./Builder.sh ~/Desktop/BloxMac

rm -rf /Users/admin/Desktop/BloxMac/src/Utils/Bundle
rm -rf /Users/admin/Desktop/BloxMac/src/Update

TARGET_DIR="/Users/admin/Desktop/BloxMac"
cd "$TARGET_DIR" || exit 1

read -p "change ? : " input

git add .
git commit -m "Add Import && Export into FflagFlag page and Set a Beta on Bloxmac Config"
git push origin main --force
