const fs = require('fs/promises');
const path = require('path');
const CurrentDate = require('./Script.js')
function ClearDataFile () {
    const PathDataClearFile = path.join('/Applications', 'Roblox.app', 'Contents', 'MacOS', 'ClientSettings');

const viderDossier = async (FilePath) => {
    try {
        const contenus = await fs.readdir(FilePath);

        for (const element of contenus) {
            const PathDataClearFile = path.join(FilePath, element);
            const stats = await fs.stat(PathDataClearFile);

            if (stats.isDirectory()) {
                await viderDossier(PathDataClearFile);
                await fs.rmdir(PathDataClearFile);
                console.log(Currentdate()+ "  " +"Clearing data");
            } else {
                await fs.unlink(PathDataClearFile);

            }
        }
    } catch (err) {
        console.log("error");
        
    }
};
viderDossier(dossier);
}
module.exports = ClearDataFile;
