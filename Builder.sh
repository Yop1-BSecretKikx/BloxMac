#!/bin/bash
rm -r out
rm -rf ./BloxMac.app
mkdir out
javac -d out $(find src -name "*.java")

jar cfm ./AppBuilder/input_jars/BloxMac.jar MANIFEST.MF -C out .

echo "BUIDING ..."

rm -rf ./AppBuilder/icons/BloxMac.icns

iconutil -c icns ./AppBuilder/icons/BloxMac.iconset

jpackage \
  --name BloxMac \
  --input ./AppBuilder/input_jars \
  --main-jar BloxMac.jar \
  --main-class src.View \
  --type app-image \
  --icon ./AppBuilder/icons/BloxMac.icns


cp -R ./BloxMac.app ./DMG
hdiutil create -volname "BloxMac" -srcfolder ./DMG -ov -format UDZO ./DMG/BloxMac.dmg

rm -rf ~/Desktop/BloxMac/DMG
mkdir ~/Desktop/BloxMac/DMG

#Update GitHub Repo
cp -R ./src ~/Desktop/BloxMac
cp -R ./Builder.sh ~/Desktop/BloxMac
cp -R ./DMG/BloxMac.dmg ~/Desktop/BloxMac/DMG
rm -rf /Users/admin/Desktop/BloxMac/src/Utils/Bundle

TARGET_DIR="/Users/admin/Desktop/BloxMac"
cd "$TARGET_DIR" || { echo "FIND"; exit 1; }

echo "What Change ? : "
read input

git rm --cached DMG/BloxMac.dmg
git pull origin main --no-rebase
git add .
git commit -m "test"
git push origin main