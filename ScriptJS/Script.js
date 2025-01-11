const { log } = require("console");
const fs = require("fs");
const path = require('path');
const readline = require('readline');
const ClearDataFileBeforImportPack = require('./Function.js');
//CurrentVersion
const Version = " v1.0"

//Full Open Source Project to help MacOS user on roblox to get more fps and enjoy the game ;)

// Created by Yop1,
// developed by Yop1,
// The future update will be soon, it depends how much user use my app
// Future update on ./ScriptJS/HowToInstall.txt

//BloxMac ASCII Font
const FontBloxMac = fs.readFileSync("./BloxMac.txt", "utf8", (err, data) => {
    console.log(data);
})
//

console.log("                                        BloxMac" + Version + "           "); console.log("             Welcome to BloxMac the first MacOS texture Editor for Roblox             ");console.log(FontBloxMac);

console.log("                                                                     ");
console.log("                                                                     ");
console.log("                                                                     ");


const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function Currentdate () {
    const date = new Date();
    const hours = date.getHours()
    const minutes = date.getMinutes()
    const secondes = date.getSeconds()
    const RenderDate =  "[" +hours + ":" + "" +minutes+ "" + ":" +secondes+ "]"
    return RenderDate;
}

module.exports = Currentdate

const PathClientSetting = "/Applications/Roblox.app/Contents/MacOS/ClientSettings";
const FuturUpdate = Currentdate()+ "FUTUR UPDATE\n" +" - Swift MacOS app for better view and management \n - Changing the writing background \n - Adding Mouse Cursor \n - API \n - And more \n - [Created by Yop1]"
rl.question('Select Type of Texture you need (1: Lowpack, 2: Midpack, 3: Highpack) =>', (answer) => {
    if(answer == 1) {

            if(!fs.existsSync(PathClientSetting)) {
                fs.mkdirSync(PathClientSetting)
                console.log(Currentdate()+ "  " +"Folder Created Succesfully" + Currentdate() + "[FILLING FILES]");
            } else {
                console.log(Currentdate()+ "  " +"Folder Already Created" + Currentdate() + "[FILLING FILES]");
            }
            console.log(Currentdate()+ "  " +"Clearing data");
                    
            setTimeout(() => {
                try {
                    ClearDataFileBeforImportPack();
                    console.log(Currentdate()+ "  " +"Clearing data");
                    
                } catch {
                    console.log("");
                    
                }
            },300)
            setTimeout(() => {
                console.log(Currentdate()+ "  " +"Fps Uncap +120");
            },900)
                setTimeout(() => {
                    try {
                        fs.readFile("./RobloxPack/Lowpack.json", "utf-8", (err, data) => {
                            const lowpack = data
                            fs.writeFile("/Applications/Roblox.app/Contents/MacOS/ClientSettings/ClientAppSettings.json", lowpack, (err, data) => {
                                const Clear = data
                            })
                        });
                        console.log(Currentdate()+ "  " +"Roblox Update Done" + Currentdate() + "[FILLING FILES DONE!]");
                        console.log(Currentdate()+ "  " +"You now have [Low texture] in all the roblox game enjoy");
                        setTimeout(() => {
                            console.log(FuturUpdate, "\n");
                            console.log("Close And Open Roblox If it Was Already Open \n CTRL C");
                
                        }, 1000)
                        
                    } catch {
                        console.log(Currentdate()+ "  " +"Uh error mh try do it again or restart your pc!");
                        
                    }
                },4000)
                
            }
            
    if(answer == 2) {

        if(!fs.existsSync(PathClientSetting)) {
            fs.mkdirSync(PathClientSetting)
            console.log(Currentdate()+ "  " +"Folder Created Succesfully"+ "              " + Currentdate() + "[FILLING FILES]");
        } else {
            console.log(Currentdate()+ "  " +"Folder Already Created" +"              "+ Currentdate() + "[FILLING FILES]");
        }
        console.log(Currentdate()+ "  " +"Clearing data");
        try {
            ClearDataFileBeforImportPack();
            console.log(Currentdate()+ "  " +"Clearing data");
        } catch {
            console.log(Currentdate()+ "  " +"");
            
        }
        setTimeout(() => {
            console.log(Currentdate()+ "  " +"Fps Uncap +120");
        },900)
        try {
            
            setTimeout(() => {
                const Midpack = fs.readFile("./RobloxPack/Midpack.json", "utf8", (err, data) => {
                    const DataMidPack = data
                    fs.writeFileSync("/Applications/Roblox.app/Contents/MacOS/ClientSettings/ClientAppSettings.json", (data), (err, data)=> {
                    })
                })
                console.log(Currentdate()+ "  " +"Roblox Update Done"+ "              " + Currentdate() + "[FILLING FILES DONE!]");
            }, 1500)
            console.log(Currentdate()+ "  " +"You now have [Mid texture] in all the roblox game enjoy");
            setTimeout(() => {
                console.log(FuturUpdate, "\n");
                console.log("Close And Open Roblox If it Was Already Open \n CTRL C");
                
            }, 1600)
        } catch {
            console.log(Currentdate()+ "  " +"Uh error mh try do it again or restart your pc!");
        }
    }

    if(answer == 3) {

        if(!fs.existsSync(PathClientSetting)) {
            fs.mkdirSync(PathClientSetting)
            console.log(Currentdate()+ "  " +"Folder Created Succesfully"+ "              " + Currentdate() + "[FILLING FILES]");
        } else {
            console.log(Currentdate()+ "  " +"Folder Already Created" +"              "+ Currentdate() + "[FILLING FILES]");
        }
        console.log(Currentdate()+ "  " +"Clearing data");
        try {
            ClearDataFileBeforImportPack();
            console.log(Currentdate()+ "  " +"Clearing data");
        } catch {
            console.log(Currentdate()+ "  " +"");
            
        }
        setTimeout(() => {
            console.log(Currentdate()+ "  " +"Fps Uncap +120");
        },1000)
        try {
            
            setTimeout(() => {
                const Highpack = fs.readFile("./RobloxPack/Highpack.json", "utf8", (err, data) => {
                    const DataHighPack = data
                    fs.writeFileSync("/Applications/Roblox.app/Contents/MacOS/ClientSettings/ClientAppSettings.json", (data), (err, data)=> {
                    })
                })
                console.log(Currentdate()+ "  " +"Roblox Update Done"+ "              " + Currentdate() + "[FILLING FILES DONE!]");
            }, 1500)
            console.log(Currentdate()+ "  " +"You now have [Mid texture] in all the roblox game enjoy");
            setTimeout(() => {
                console.log(FuturUpdate, "\n");
                console.log("Close And Open Roblox If it Was Already Open \n CTRL C");
                
            }, 1600)
        } catch {
            console.log(Currentdate()+ "  " +"Uh error mh try do it again or restart your pc!");
        }
    }
});



