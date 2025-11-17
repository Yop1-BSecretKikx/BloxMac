#!/bin/bash

cp -R ./src ~/Desktop/BloxMac

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

cp -R ./src ~/Desktop/BloxMac
cp -R ./Builder.sh ~/Desktop/BloxMac
cp -R ./DMG/BloxMac.dmg ~/Desktop/BloxMac/DMG

rm -rf /Users/admin/Desktop/BloxMac/src/Utils/Bundle
rm -rf /Users/admin/Desktop/BloxMac/src/Update

TARGET_DIR="/Users/admin/Desktop/BloxMac"
cd "$TARGET_DIR" || exit 1

read -p "change ? : " input

git add .
git commit -m "Update Popup + style && handle size"
git push origin main --force

#git filter-repo --path src/Update --invert-paths --force
####git remote add origin git@github.com:Yop1-BSecretKikx/BloxMac.git

###git add -A
##git commit -m "Update repository"
#git push origin main --force

