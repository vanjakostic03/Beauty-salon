package utils;

public class AppSettigs {
    private String fileKlijenti;
    private String fileRecepcioneri;
    private String fileKozmeticari;
    private String fileMenadzeri;
    private String fileCenovnik;
    private String fileTretmani;
    private String fileVrsteTretmana;
    private String fileTipoviTretmana;
    private String fileSalonInfo;

    public AppSettigs(String fileKlijenti, String fileRecepcioneri, String fileKozmeticari, String fileMenadzeri, String fileCenovnik, String fileTretmani, String fileVrsteTretmana, String fileTipoviTretmana, String fileSalonInfo){
        this.fileKlijenti = fileKlijenti;
        this.fileKozmeticari=fileKozmeticari;
        this.fileMenadzeri = fileMenadzeri;
        this.fileRecepcioneri = fileRecepcioneri;
        this.fileCenovnik = fileCenovnik;
        this.fileVrsteTretmana = fileVrsteTretmana;
        this.fileTretmani = fileTretmani;
        this.fileTipoviTretmana = fileTipoviTretmana;
        this.fileSalonInfo = fileSalonInfo;
    }

    //geteri

    public String getKlijenti(){ return fileKlijenti;}
    public String getKozmeticari(){ return fileKozmeticari;}
    public String getRecepcioneri(){ return fileRecepcioneri;}
    public String getMenadzeri(){ return fileMenadzeri;}
    public String getCenovnik(){ return fileCenovnik;}
    public String getVrsteTretmana(){ return fileVrsteTretmana;}
    public String getTretmani(){ return fileTretmani;}
    public String getTipoviTretmana(){ return fileTipoviTretmana;}
    public String getSalonInfo(){ return fileSalonInfo;}
    
}
